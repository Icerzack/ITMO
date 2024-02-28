package com.example.server2.configuration;

import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
public class HttpsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws NotAcceptableException {
        UriInfo uriInfo = requestContext.getUriInfo();

        if (!uriInfo.getRequestUri().getScheme().equals("https")) {
            throw new NotAcceptableException("");
        }
        if (isPreflightRequest(requestContext)) {
            requestContext.abortWith(Response.ok().build());
        }
    }

    private static boolean isPreflightRequest(ContainerRequestContext request) {
        return request.getHeaderString("Origin") != null
                && request.getMethod().equalsIgnoreCase("OPTIONS");
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {

        if (request.getHeaderString("Origin") == null) {
            return;
        }

        if (isPreflightRequest(request)) {
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.getHeaders().add("Access-Control-Allow-Methods",
                    "*");
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "*");
        }

        response.getHeaders().add("Access-Control-Allow-Origin", "*");
    }

}
