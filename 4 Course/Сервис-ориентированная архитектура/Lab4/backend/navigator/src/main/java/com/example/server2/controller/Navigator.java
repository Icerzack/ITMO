package com.example.server2.controller;

import com.example.server2.exception.FirstServiceUnavailableException;
import com.example.server2.exception.NotValidParamsException;
import com.example.server2.model.ErrorDefaultDTO;
import com.example.server2.model.GetRoutesResponseDTO;
import com.example.server2.model.LocationDTO;
import com.example.server2.model.RouteDTO;
import com.example.server2.service.Dijkstra;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

@Path("/api/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Navigator {
    final String routeServiceURL = "https://localhost:8081/api";
    private Client createConfiguredClient() throws Exception {
        char[] password = "password".toCharArray();
        KeyStore keystore = KeyStore.getInstance("PKCS12");

        try (InputStream keystoreInputStream = getClass().getResourceAsStream("../../../../keystore.p12")) {
            keystore.load(keystoreInputStream, password);
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
            (hostname, sslSession) -> hostname.equals("localhost"));
        return ClientBuilder.newBuilder().sslContext(sslContext).hostnameVerifier((hostname, session) -> true).build();
    }

    @GET
    @Path("/route/{id-from}/{id-to}/shortest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findShortest(@PathParam("id-from") String idFrom, @PathParam("id-to") String idTo) {
        try {
            Long parsedIdFrom = Long.parseLong(idFrom);
            Long parsedIdTo = Long.parseLong(idTo);
            if (parsedIdFrom < 1 || parsedIdTo < 1) {
                throw new NotValidParamsException("невалидные входные данные");
            }
            float distance = findShortest(parsedIdFrom, parsedIdTo);
            if (distance == -1f){
                throw new FirstServiceUnavailableException("недоступен первый сервис");
            }
            if (distance == -2f){
                throw new NotValidParamsException("невалидные входные данные");
            }
            return Response.ok(distance).build();
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage("id should be Long Number");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (NotValidParamsException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage(e.getMessage());
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (FirstServiceUnavailableException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("ошибка на стороне routes, возможно, некорректные данные");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("Непредвиденная ошибка");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        }
    }

    @POST
    @Path("/route/add/{id-from}/{id-to}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoute(@PathParam("id-from") String idFrom, @PathParam("id-to") String idTo) {
        try {
            Long parsedIdFrom = Long.parseLong(idFrom);
            Long parsedIdTo = Long.parseLong(idTo);
            if (parsedIdFrom < 1 || parsedIdTo < 1) {
                throw new NotValidParamsException("невалидные входные данные");
            }
            RouteDTO dto = addRoute(parsedIdFrom, parsedIdTo);
            System.out.println(dto);
            if (dto == null) {
                throw new FirstServiceUnavailableException("проверьте корректную работоспособность первого сервиса");
            }
            if (dto.getId() == null) {
                throw new NotValidParamsException("невалидные входные данные");
            }
            return Response.ok().build();
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage("id should be Long Number");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (NotValidParamsException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage(e.getMessage());
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (FirstServiceUnavailableException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("проверьте корректную работоспособность первого сервиса");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("Непредвиденная ошибка");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        }
    }

    public Float findShortest(Long idFrom, Long idTo) {
        try {
            Client client = createConfiguredClient();
            String springServiceUrl = routeServiceURL + "/routes?elementsCount=999999";
            Response response = client.target(springServiceUrl)
                .request(MediaType.APPLICATION_JSON)
                .get();
            GetRoutesResponseDTO r = response.readEntity(GetRoutesResponseDTO.class);
            List<RouteDTO> routes = r.getRoutes();
            int numNodes = routes.size();
            List<List<Dijkstra.Edge>> graph = new ArrayList<>();
            for (int i = 0; i < numNodes; i++) {
                graph.add(new ArrayList<>());
            }

            for (RouteDTO rd : routes) {
                graph
                    .get(Math.toIntExact(rd.getFrom().getId()))
                    .add(new Dijkstra.Edge(rd.getTo().getId(), rd.getDistance()));
            }

            return Dijkstra.shortestPath(graph, Math.toIntExact(idFrom), Math.toIntExact(idTo));
        }  catch (jakarta.ws.rs.ProcessingException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            return -1f;
        }
        catch (Exception e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            return -2f;
        }
    }

    public RouteDTO addRoute(Long idFrom, Long idTo) {
        try {
            Client client = createConfiguredClient();
            String springServiceUrl = routeServiceURL + "/routes?elementsCount=999999";
            Response response = client.target(springServiceUrl)
                .request(MediaType.APPLICATION_JSON)
                .get();
            System.out.println(response.getStatus());
            GetRoutesResponseDTO r = response.readEntity(GetRoutesResponseDTO.class);
            List<RouteDTO> routes = r.getRoutes();
            LocationDTO locationFrom = null;
            LocationDTO locationTo = null;
            for (RouteDTO routeDTO : routes) {
                if (locationFrom != null && locationTo != null) {
                    break;
                }
                if (Objects.equals(routeDTO.getFrom().getId(), idFrom)) {
                    locationFrom = routeDTO.getFrom();
                }
                if (Objects.equals(routeDTO.getTo().getId(), idTo)) {
                    locationTo = routeDTO.getTo();
                }
            }
            RouteDTO newRoute = new RouteDTO();
            newRoute.setName("Route from " + locationFrom.getName() + " to " + locationTo.getName());
            newRoute.setFrom(locationFrom);
            newRoute.setTo(locationTo);
            newRoute.setDistance((float) calculateDistance(
                locationFrom.getCoordinates().getX(),
                locationFrom.getCoordinates().getY(),
                locationTo.getCoordinates().getX(),
                locationTo.getCoordinates().getY()));
            springServiceUrl = routeServiceURL + "/routes";
            response = client.target(springServiceUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newRoute, MediaType.APPLICATION_JSON));
          return response.readEntity(RouteDTO.class);
        } catch (jakarta.ws.rs.ProcessingException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            return new RouteDTO();
        }
    }

    public static double calculateDistance(int x1, Float y1, int x2, Float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
