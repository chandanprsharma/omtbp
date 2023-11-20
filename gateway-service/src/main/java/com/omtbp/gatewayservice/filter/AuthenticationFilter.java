package com.omtbp.gatewayservice.filter;

import com.omtbp.gatewayservice.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private RestTemplate template;

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    @Autowired
    private ErrorWebExceptionHandler errorWebExceptionHandler;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //Header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new InvalidTokenException(HttpStatus.BAD_REQUEST);
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    //REST call to AUTH service
                    ResponseEntity<String> resp = template.getForEntity("http://localhost:8081/auth/validate?token=" + authHeader, String.class);
                    //ResponseEntity<String> resp = template.getForEntity("http://USER-SERVICE/auth/validate?token=" + authHeader, String.class);

                    if(resp.getStatusCode().value() != 200) {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    logger.info("Invalid access...!");
                    throw new InvalidTokenException(HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
