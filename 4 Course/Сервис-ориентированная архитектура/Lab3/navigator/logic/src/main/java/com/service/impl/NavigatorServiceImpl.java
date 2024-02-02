package com.service.impl;

import com.dto.ErrorDefaultDTO;
import com.dto.GetRoutesResponseDTO;
import com.dto.LocationDTO;
import com.dto.RouteDTO;
import com.exception.FirstServiceUnavailableException;
import com.exception.NotValidParamsException;
import com.service.Dijkstra;
import com.service.NavigatorService;
import java.io.InputStream;
import java.security.KeyStore;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.ejb3.annotation.Pool;

@Stateless
@Pool(value = "navigatorServicePool")
public class NavigatorServiceImpl implements NavigatorService {
  private final String routeServiceURL = "http://haproxy:1111/api/v1";

  public float findShortest(String idFrom, String idTo) {
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
    return distance;
  }
  public RouteDTO addRoute(String idFrom, String idTo) {
    Long parsedIdFrom = Long.parseLong(idFrom);
    Long parsedIdTo = Long.parseLong(idTo);
    if (parsedIdFrom < 1 || parsedIdTo < 1) {
      throw new NotValidParamsException("невалидные входные данные");
    }
    RouteDTO dto = addRoute(parsedIdFrom, parsedIdTo);
    if (dto == null) {
      throw new FirstServiceUnavailableException("проверьте корректную работоспособность первого сервиса");
    }
    if (dto.getId() == null) {
      throw new NotValidParamsException("невалидные входные данные");
    }
    return new RouteDTO();
  }
  public Float findShortest(Long idFrom, Long idTo) {
    try {
//      Client client = createConfiguredClient();
      Client client = ClientBuilder.newClient();
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
    }  catch (ProcessingException e) {
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
//      Client client = createConfiguredClient();
      Client client = ClientBuilder.newClient();
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
        if (routeDTO.getFrom().getId() == idFrom) {
          locationFrom = routeDTO.getFrom();
        }
        if (routeDTO.getTo().getId() == idTo) {
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
    } catch (ProcessingException e) {
      System.out.println(e.getClass() + ": " + e.getMessage());
      return null;
    }
    catch (Exception e) {
      System.out.println(e.getClass() + ": " + e.getMessage());
      return new RouteDTO();
    }
  }

  public double calculateDistance(int x1, Float y1, int x2, Float y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }

//  private Client createConfiguredClient() throws Exception {
//    char[] password = "password".toCharArray();
//    KeyStore keystore = KeyStore.getInstance("PKCS12");
//
//    try (InputStream keystoreInputStream = getClass().getResourceAsStream("../../../../../keystore.p12")) {
//      keystore.load(keystoreInputStream, password);
//    }
//
//    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//    trustManagerFactory.init(keystore);
//
//    SSLContext sslContext = SSLContext.getInstance("TLS");
//    sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
//    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
//        (hostname, sslSession) -> true);
//    return ClientBuilder.newBuilder().sslContext(sslContext).hostnameVerifier((hostname, session) -> true).build();
//  }
}
