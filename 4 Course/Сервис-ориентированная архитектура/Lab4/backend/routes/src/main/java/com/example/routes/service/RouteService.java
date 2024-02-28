package com.example.routes.service;

import com.example.routes.converter.RouteConverter;
import com.example.routes.dto.QueryDTO;
import com.example.routes.dto.RouteDTO;
import com.example.routes.dto.RoutesWithPagingDTO;
import com.example.routes.dto.SpecialOfferQueryDTO;
import com.example.routes.entity.LocationEntity;
import com.example.routes.entity.RouteEntity;
import com.example.routes.entity.RoutesWithPagingEntity;
import com.example.routes.repository.LocationRepository;
import com.example.routes.repository.RouteRepository;
import com.example.routes.soap.exception.ServiceFault;
import com.example.routes.soap.exception.ServiceFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class RouteService {
    private static final double EPSILON = 1e-6;
    private static final Logger logger = LoggerFactory.getLogger(RouteService.class);
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private final RouteConverter routeConverter;
    @Autowired
    private PageService pageService;
    @Autowired
    Jaxb2Marshaller jaxb2Marshaller;

    public RouteService(RouteConverter routeConverter) {
        this.routeConverter = routeConverter;
    }

    public List<RouteEntity> filterRoutes(QueryDTO query, List<RouteEntity> routeEntities) {
        List<RouteDTO> routeDTOs = routeEntities.stream()
                .map(routeConverter::convertToDTO)
                .collect(Collectors.toList());

        return routeDTOs.stream()
                .filter(route -> checkConditions(route, query))
                .map(routeConverter::convertToEntityWithoutCreationDate)
                .collect(Collectors.toList());
    }

    private boolean checkConditions(RouteDTO route, QueryDTO query) {
        if (query.getFilter() != null) {
            String regex = "^(id|name|creationDate|locationFromId|locationFromCoordinatesX|locationFromCoordinatesY|locationFromName|locationToId|locationToCoordinatesX|locationToCoordinatesY|locationToName|distance)(=|!=|>|<|>=|<=)([0-9a-zA-Zа-яА-Я\\s?!,.'Ёё]+)$";
            Pattern pattern = Pattern.compile(regex);

            return query.getFilter().stream()
                    .map(pattern::matcher)
                    .filter(Matcher::matches)
                    .allMatch(matcher -> {
                        String field = matcher.group(1);
                        String operator = matcher.group(2);
                        String value = matcher.group(3);

                        switch (operator) {
                            case "=":
                                return checkEqual(route, field, value);
                            case "!=":
                                return checkNotEqual(route, field, value);
                            case ">":
                                return checkGreaterThan(route, field, value);
                            case "<":
                                return checkLessThan(route, field, value);
                            case ">=":
                                return checkGreaterThanOrEqual(route, field, value);
                            case "<=":
                                return checkLessThanOrEqual(route, field, value);
                            default:
                                return true;
                        }
                    });
        }

        return true;
    }

    private boolean checkEqual(RouteDTO route, String field, String value) {
        switch (field) {
            case "id":
                return route.getId().equals(Long.valueOf(value));
            case "name":
                return route.getName().equals(value);
            case "creationDate":
                return route.getCreationDate().equals(value);
            case "locationFromId":
                return route.getFrom().getId().equals(Long.valueOf(value));
            case "locationFromCoordinatesX":
                return Math.abs(route.getFrom().getCoordinates().getX() - Float.parseFloat(value)) < EPSILON;
            case "locationFromCoordinatesY":
                return Math.abs(route.getFrom().getCoordinates().getY() - Float.parseFloat(value)) < EPSILON;
            case "locationFromName":
                return route.getFrom().getName().equals(value);
            case "locationToId":
                return route.getTo().getId().equals(Long.valueOf(value));
            case "locationToCoordinatesX":
                return Math.abs(route.getTo().getCoordinates().getX() - Float.parseFloat(value)) < EPSILON;
            case "locationToCoordinatesY":
                return Math.abs(route.getTo().getCoordinates().getY() - Float.parseFloat(value)) < EPSILON;
            case "locationToName":
                return route.getTo().getName().equals(value);
            case "distance":
                return Math.abs(route.getDistance() - Float.parseFloat(value)) < EPSILON;
            default:
                return false;
        }
    }

    private boolean checkNotEqual(RouteDTO route, String field, String value) {
        return !checkEqual(route, field, value);
    }

    private boolean checkGreaterThan(RouteDTO route, String field, String value) {
        switch (field) {
            case "id":
                return compareGreaterThan(route.getId(), Long.valueOf(value));
            case "name":
                return compareGreaterThan(route.getName(), value);
            case "creationDate":
                return compareGreaterThan(route.getCreationDate().toString(), value);
            case "locationFromId":
                return compareGreaterThan(route.getFrom().getId(), Long.valueOf(value));
            case "locationFromCoordinatesX":
                return compareGreaterThan(route.getFrom().getCoordinates().getX(), Integer.parseInt(value));
            case "locationFromCoordinatesY":
                return compareGreaterThan(route.getFrom().getCoordinates().getY(), Float.parseFloat(value));
            case "locationFromName":
                return compareGreaterThan(route.getFrom().getName(), value);
            case "locationToId":
                return compareGreaterThan(route.getTo().getId(), Long.valueOf(value));
            case "locationToCoordinatesX":
                return compareGreaterThan(route.getTo().getCoordinates().getX(), Integer.parseInt(value));
            case "locationToCoordinatesY":
                return compareGreaterThan(route.getTo().getCoordinates().getY(), Float.parseFloat(value));
            case "locationToName":
                return compareGreaterThan(route.getTo().getName(), value);
            case "distance":
                return compareGreaterThan(route.getDistance(), Float.parseFloat(value));
            default:
                return false;
        }
    }

    private <T extends Comparable<T>> boolean compareGreaterThan(T fieldValue, T compareValue) {
        return fieldValue.compareTo(compareValue) > 0;
    }

    private boolean checkLessThan(RouteDTO route, String field, String value) {
        return !(checkGreaterThan(route, field, value) || checkEqual(route, field, value));
    }

    private boolean checkGreaterThanOrEqual(RouteDTO route, String field, String value) {
        return (checkGreaterThan(route, field, value) || checkEqual(route, field, value));
    }

    private boolean checkLessThanOrEqual(RouteDTO route, String field, String value) {
        return checkLessThan(route, field, value) || checkEqual(route, field, value);
    }

    public RoutesWithPagingEntity getPaginatedRoutes(Integer page, Integer elementsCount) {
        Pageable pageable = pageService.createPageRequest(page, elementsCount);
        RoutesWithPagingEntity routesWithPaging = new RoutesWithPagingEntity();
        routesWithPaging.setRoutesEntity(routeRepository.findByPageAndElementsCount(pageable));
        routesWithPaging.setPage(pageable.getPageNumber()+1);
        routesWithPaging.setElementsCount(pageable.getPageSize());
        return routesWithPaging;
    }

    @Transactional(readOnly = true)
    public RoutesWithPagingDTO getAllRoutes(@Valid QueryDTO dto) {
        FilterService.isValidRequestParams(dto);
        RoutesWithPagingEntity routesWithPagingEntity = getPaginatedRoutes(dto.getPage(), dto.getElementsCount());
        List<RouteEntity> allRoutes = routesWithPagingEntity.getRoutesEntity();
        List<RouteEntity> allFilteredRoutes = filterRoutes(dto, allRoutes);

        List<String> sortFields = dto.getSort() != null && !dto.getSort().isEmpty() ? dto.getSort() : Collections.emptyList();
        List<Sort.Direction> sortDirections = dto.getSortDirection() != null ? dto.getSortDirection() : Collections.emptyList();

        List<RouteEntity> sortedRoutes = SortService.sortElements(allFilteredRoutes, sortFields, sortDirections);

        List<RouteDTO> allRoutesResponse = new ArrayList<>();
        for (RouteEntity routeEntity : sortedRoutes) {
            allRoutesResponse.add(routeConverter.convertToDTO(routeEntity));
        }

        RoutesWithPagingDTO routesWithPagingDTO = new RoutesWithPagingDTO();
        routesWithPagingDTO.setRoutesDTO(allRoutesResponse);
        routesWithPagingDTO.setPage(routesWithPagingEntity.getPage());
        routesWithPagingDTO.setElementsCount(routesWithPagingEntity.getElementsCount());

        return routesWithPagingDTO;
    }

    @Transactional
    @Validated
    public RouteDTO addNewRoute(@Valid RouteDTO dto) {
        RouteEntity entity = routeConverter.convertToEntity(dto);
        LocationEntity newLocationFromEntity = entity.getLocationFrom();
        LocationEntity newLocationToEntity = entity.getLocationTo();
        Optional<LocationEntity> locationFromEntity = locationRepository.findLocationEntityByCoordinates(newLocationFromEntity.getCoordinates().getX(), newLocationFromEntity.getCoordinates().getY());
        Optional<LocationEntity> locationToEntity = locationRepository.findLocationEntityByCoordinates(newLocationToEntity.getCoordinates().getX(), newLocationToEntity.getCoordinates().getY());
        if (entity.getDistance() < 1) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "distance must be > 1", getFormattedDate()));
        }
        if (!locationFromEntity.isPresent()) {
            saveToLocationRepository(newLocationFromEntity);
        } else {
            entity.getLocationFrom().setId(locationFromEntity.get().getId());
        }
        if (!locationToEntity.isPresent()) {
            saveToLocationRepository(newLocationToEntity);
        } else {
            entity.getLocationTo().setId(locationToEntity.get().getId());
        }
        routeRepository.save(entity);
        return routeConverter.convertToDTO(entity);
    }

    @Transactional(readOnly = true)
    public RouteDTO getRouteById(Long id) {
        if (id < 1) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "id must be positive", getFormattedDate()));
        }
        Optional<RouteEntity> entity = routeRepository.findRouteEntityById(id);
        if (entity.isPresent()) {
            return routeConverter.convertToDTO(entity.get());
        } else {
            throw new ServiceFaultException("Error", new ServiceFault(400, String.format("Can't find route with id=%d", id), getFormattedDate()));
        }
    }

    @Transactional
    public RouteDTO updateRouteById(Long id, @Valid RouteDTO dto) {
        if (id < 1) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "id must be positive", getFormattedDate()));
        }
        Optional<RouteEntity> entity = routeRepository.findRouteEntityById(id);
        if (entity.isPresent()) {
            RouteEntity newEntity = routeConverter.convertToEntity(dto);
            Optional<LocationEntity> locationFromEntity = locationRepository.findLocationEntityByCoordinates(newEntity.getLocationFrom().getCoordinates().getX(), newEntity.getLocationFrom().getCoordinates().getY());
            Optional<LocationEntity> locationToEntity = locationRepository.findLocationEntityByCoordinates(newEntity.getLocationTo().getCoordinates().getX(), newEntity.getLocationTo().getCoordinates().getY());
            if (!locationFromEntity.isPresent()) {
                saveToLocationRepository(newEntity.getLocationFrom());
            } else {
                newEntity.getLocationFrom().setId(locationFromEntity.get().getId());
            }
            if (!locationToEntity.isPresent()) {
                saveToLocationRepository(newEntity.getLocationTo());
            } else {
                newEntity.getLocationTo().setId(locationToEntity.get().getId());
            }
            newEntity.setId(id);
            newEntity = saveToRouteRepository(newEntity);
            return routeConverter.convertToDTO(newEntity);
        } else {
            throw new ServiceFaultException("Error", new ServiceFault(404, String.format("Can't find route with id=%d", id), getFormattedDate()));
        }
    }

    @Transactional
    public void deleteRouteById(Long id) {
        if (id < 1) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "id must be positive", getFormattedDate()));
        }
        routeRepository.deleteRouteEntityById(id);
    }

    @Transactional
    public void deleteRoutesByDistance(float distance) {
        if (distance < 0) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "distance mustn't be negative", getFormattedDate()));
        }
        routeRepository.deleteRoutesByDistance(distance);
    }

    @Transactional(readOnly = true)
    public Float getSumAllDistances() {
        List<RouteEntity> allRoutes = routeRepository.findAllRoutesEntity();
        float sumDistances = 0;
        for (RouteEntity routeEntity: allRoutes) {
            sumDistances += routeEntity.getDistance();
        }
        return sumDistances;
    }

    @Transactional(readOnly = true)
    public int getCountRoutesWithGreaterDistance(float distance) {
        if (distance < 0) {
            throw new ServiceFaultException("Error", new ServiceFault(400, "distance mustn't be negative", getFormattedDate()));
        }
        List<RouteEntity> allRoutes = routeRepository.findAllRoutesEntity();
        int countRoutesWithGreaterDistance = 0;
        for (RouteEntity routeEntity: allRoutes) {
            if (routeEntity.getDistance() > distance) {
                countRoutesWithGreaterDistance++;
            }
        }
        return countRoutesWithGreaterDistance;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSpecialOffers(@Valid SpecialOfferQueryDTO dto) {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            Map<String, String> envs = System.getenv();
            String aviasalesApiUrl = envs.get("AVIASALES_API_URL");
            URL url = new URL(aviasalesApiUrl + "?" + buildQueryString(dto));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                responseMap.put("responseCode", responseCode);
                responseMap.put("responseData", response.toString());
            } else {
                responseMap.put("responseCode", responseCode);
                responseMap.put("error", "Failed to get special offers");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            responseMap.put("error", "An exception occurred: " + e.getMessage());
        }

        return responseMap;
    }

    private String buildQueryString(SpecialOfferQueryDTO dto) {
        String query = "";
        if (dto.getOrigin() != null && !dto.getOrigin().equals("")) {
            query+= "origin=" + dto.getOrigin() + "&";
        }
        if (dto.getDestination() != null && !dto.getDestination().equals("")) {
            query+= "destination=" + dto.getDestination() + "&";
        }
        if (dto.getCurrency() != null && !dto.getCurrency().equals("")) {
            query+= "currency=" + dto.getCurrency() + "&";
        }
        if (dto.getLocale() != null && !dto.getLocale().equals("")) {
            query+= "locale=" + dto.getLocale();
        }

        return query;
    }

    @Transactional
    public RouteEntity saveToRouteRepository(RouteEntity routeEntity) {
        return routeRepository.save(routeEntity);
    }

    @Transactional
    public LocationEntity saveToLocationRepository(LocationEntity locationEntity) {
        return locationRepository.save(locationEntity);
    }

    public static String getFormattedDate(){
        ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
        String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        int pos = formattedCurrentDateTime.indexOf(".");
        formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
        formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
        return formattedCurrentDateTime;
    }
}
