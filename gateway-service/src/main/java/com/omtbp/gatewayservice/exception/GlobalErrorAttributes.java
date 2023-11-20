package com.omtbp.gatewayservice.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> errorResponse = super.getErrorAttributes(request, options);

        //extract the status and put custom error message on the map
        HttpStatus status = HttpStatus.valueOf((Integer) errorResponse.get("status"));

        switch (status) {
            case BAD_REQUEST:
                errorResponse.put("message", "Missing authorization header");
                break;
            case UNAUTHORIZED:
                errorResponse.put("message", "Unauthorized user access. Invalid Token");
                break;
            default:
                errorResponse.put("message", "Something went wrong!");
        }

        return errorResponse;
    }
}