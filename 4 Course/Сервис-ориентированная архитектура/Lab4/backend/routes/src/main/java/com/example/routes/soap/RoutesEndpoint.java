package com.example.routes.soap;

import com.example.routes.converter.CoordinateConverter;
import com.example.routes.converter.LocationConverter;
import com.example.routes.converter.RouteConverter;
import com.example.routes.dto.QueryDTO;
import com.example.routes.dto.RouteDTO;
import com.example.routes.dto.RoutesWithPagingDTO;
import com.example.routes.service.RouteService;
import com.example.routes.soap.exception.ServiceFault;
import com.example.routes.soap.exception.ServiceFaultException;
import com.example.routes.soap.soapgen.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

import static com.example.routes.service.RouteService.getFormattedDate;

@Endpoint
public class RoutesEndpoint {
    private static final String NAMESPACE_URI = "http://soap";

    @Autowired
    private RouteService routeService;
    @Autowired
    private final RouteConverter routeConverter;
    @Autowired
    Jaxb2Marshaller jaxb2Marshaller;
    private WebServiceTemplate template;

    public RoutesEndpoint() {
        routeConverter = new RouteConverter(new LocationConverter(new CoordinateConverter()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRoutesRequest")
    @ResponsePayload
    public GetRoutesResponse getRoutes(@RequestPayload GetRoutesRequest request) {
        GetRoutesResponse response = new GetRoutesResponse();
        try {
            response.setPage(Integer.valueOf(request.getPage()));
        } catch (Exception e) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Not valid param 'page'=" + request.getPage(), getFormattedDate()));
        }
        try {
            response.setElementsCount(Integer.valueOf(request.getElementsCount()));
        } catch (Exception e) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Not valid param 'elementsCount'=" + request.getElementsCount(), getFormattedDate()));
        }
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.setSort(request.getSort());
        queryDTO.setFilter(request.getFilter());
        queryDTO.setPage(Integer.valueOf(request.getPage()));
        queryDTO.setElementsCount(Integer.valueOf(request.getElementsCount()));
        RoutesWithPagingDTO routesWithPagingDTO = routeService.getAllRoutes(queryDTO);
        List<RouteDTOSoap> routes = new ArrayList<>();
        for (RouteDTO r : routesWithPagingDTO.getRoutesDTO()) {
            routes.add(routeConverter.convertFromDTOToSoap(r));
        }
        response.setRoutes(routes);
        response.setPage(routesWithPagingDTO.getPage());
        response.setElementsCount(routesWithPagingDTO.getElementsCount());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postRouteRequest")
    @ResponsePayload
    public PostRouteResponse postRoutes(@RequestPayload PostRouteRequest request) throws DatatypeConfigurationException {
        PostRouteResponse response = new PostRouteResponse();
        if (request.getRoute() == null) {
            System.out.println("error postRouteRequest, because of null request value");
            return response;
        }
        RouteDTOSoap routeDTOSoap = new RouteDTOSoap();
        routeDTOSoap.setName(request.getRoute().getName());
        LocationDTOSoap locationDTOSoapFrom = new LocationDTOSoap();
        LocationDTOSoap locationDTOSoapTo = new LocationDTOSoap();
        locationDTOSoapFrom.setName(request.getRoute().getFrom().getName());
        locationDTOSoapFrom.setCoordinates(request.getRoute().getFrom().getCoordinates());
        locationDTOSoapTo.setName(request.getRoute().getTo().getName());
        locationDTOSoapTo.setCoordinates(request.getRoute().getTo().getCoordinates());
        routeDTOSoap.setFrom(locationDTOSoapFrom);
        routeDTOSoap.setTo(locationDTOSoapTo);
        routeDTOSoap.setDistance(request.getRoute().getDistance());
        RouteDTO dto = routeConverter.convertFromSoapToDTO(routeDTOSoap);
        RouteDTO addedDTO = routeService.addNewRoute(dto);
        RouteDTOSoap responseDTOSoap = routeConverter.convertFromDTOToSoap(addedDTO);
        response.setRoute(responseDTOSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRouteRequest")
    @ResponsePayload
    public GetRouteResponse getRoute(@RequestPayload GetRouteRequest request) {
        GetRouteResponse response = new GetRouteResponse();
        RouteDTO routeDTO;
        long id;
        try {
             id = Long.parseLong(request.getId());
        } catch (Exception e) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Not valid param 'id'=" + request.getId(), getFormattedDate()));
        }
        routeDTO = routeService.getRouteById(id);
        response.setRoute(routeConverter.convertFromDTOToSoap(routeDTO));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "putRouteRequest")
    @ResponsePayload
    public PutRouteResponse putRoute(@RequestPayload PutRouteRequest request) {
        PutRouteResponse response = new PutRouteResponse();
        if (request.getRoute() == null) {
            System.out.println("error postRouteRequest, because of null request value");
            return response;
        }
        RouteDTOSoap routeDTOSoap = new RouteDTOSoap();
        routeDTOSoap.setName(request.getRoute().getName());
        LocationDTOSoap locationDTOSoapFrom = new LocationDTOSoap();
        LocationDTOSoap locationDTOSoapTo = new LocationDTOSoap();
        locationDTOSoapFrom.setName(request.getRoute().getFrom().getName());
        locationDTOSoapFrom.setCoordinates(request.getRoute().getFrom().getCoordinates());
        locationDTOSoapTo.setName(request.getRoute().getTo().getName());
        locationDTOSoapTo.setCoordinates(request.getRoute().getTo().getCoordinates());
        routeDTOSoap.setFrom(locationDTOSoapFrom);
        routeDTOSoap.setTo(locationDTOSoapTo);
        routeDTOSoap.setDistance(request.getRoute().getDistance());
        RouteDTO dto = routeConverter.convertFromSoapToDTO(routeDTOSoap);
        RouteDTO updatedDTO;
        long id;
        try {
            id = Long.parseLong(request.getId());
        } catch (Exception e) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Not valid param 'id'=" + request.getId(), getFormattedDate()));
        }
        updatedDTO = routeService.updateRouteById(id, dto);
        RouteDTOSoap responseDTOSoap = routeConverter.convertFromDTOToSoap(updatedDTO);
        response.setRoute(responseDTOSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteRouteRequest")
    @ResponsePayload
    public DeleteRouteResponse deleteRoute(@RequestPayload DeleteRouteRequest request) {
        DeleteRouteResponse response = new DeleteRouteResponse();
        long id;
        try {
            id = Long.parseLong(request.getId());
        } catch (Exception e) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Not valid param 'id'=" + request.getId(), getFormattedDate()));
        }
        routeService.deleteRouteById(id);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteDistancesRequest")
    @ResponsePayload
    public DeleteDistancesResponse deleteDistances (@RequestPayload DeleteDistancesRequest request) {
        DeleteDistancesResponse response = new DeleteDistancesResponse();
        float distance;
        try {
            distance = Float.parseFloat(request.getDistance());
        } catch (Exception e) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Not valid param 'distance'=" + request.getDistance(), getFormattedDate()));
        }
        routeService.deleteRoutesByDistance(distance);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDistancesSumRequest")
    @ResponsePayload
    public GetDistancesSumResponse getDistancesSum (@RequestPayload GetDistancesSumRequest request) {
        GetDistancesSumResponse response = new GetDistancesSumResponse();
        float sum = routeService.getSumAllDistances();
        response.setDistance(sum);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRoutesWithGreaterDistanceRequest")
    @ResponsePayload
    public GetRoutesWithGreaterDistanceResponse getRoutesWithGreaterDistance (@RequestPayload GetRoutesWithGreaterDistanceRequest request) {
        GetRoutesWithGreaterDistanceResponse response = new GetRoutesWithGreaterDistanceResponse();
        int count;
        float distance;
        try {
            distance = Float.parseFloat(request.getDistance());
        } catch (Exception e) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Not valid param 'distance'=" + request.getDistance(), getFormattedDate()));
        }
        count = routeService.getCountRoutesWithGreaterDistance(distance);
        response.setCount(count);
        return response;
    }
}