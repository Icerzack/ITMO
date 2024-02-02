package com.example.server2.configuration;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class HttpsFilter implements ContainerResponseFilter {

//    @Override
//    public void filter(ContainerRequestContext requestContext) throws NotAcceptableException {
//        UriInfo uriInfo = requestContext.getUriInfo();
//
//        if (!uriInfo.getRequestUri().getScheme().equals("https")) {
//            throw new NotAcceptableException("");
//        }
//        if (isPreflightRequest(requestContext)) {
//            requestContext.abortWith(Response.ok().build());
//        }
//    }

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
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "X-Requested-With, Authorization, " +
                            "Accept-Version, Content-MD5, CSRF-Token, Content-Type");
        }

        response.getHeaders().add("Access-Control-Allow-Origin", "*");
    }

}
