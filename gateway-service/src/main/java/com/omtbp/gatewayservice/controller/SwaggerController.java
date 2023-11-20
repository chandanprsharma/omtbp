package com.omtbp.gatewayservice.controller;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
public class SwaggerController {

    private final DiscoveryClient discoveryClient;

    public SwaggerController(final DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/v3/api-docs/swagger-config")
    public Map<String, Object> swaggerConfig(ServerHttpRequest serverHttpRequest) throws URISyntaxException {
        URI uri = serverHttpRequest.getURI();
        String url = new URI(uri.getScheme(), uri.getAuthority(), null, null, null).toString();
        Map<String, Object> swaggerConfig = new LinkedHashMap<>();
        List<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = new LinkedList<>();
        for(String serviceName:discoveryClient.getServices()) {
            AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
            swaggerUrl.setName(serviceName);
            swaggerUrl.setDisplayName(serviceName);
            swaggerUrl.setUrl(url + "/" + serviceName + "/v3/api-docs");
            swaggerUrls.add(swaggerUrl);
        }
        swaggerConfig.put("urls", swaggerUrls);
        return swaggerConfig;
    }
}