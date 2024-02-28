package com.example.routes.service;

import com.example.routes.entity.RouteEntity;
import com.example.routes.soap.exception.ServiceFault;
import com.example.routes.soap.exception.ServiceFaultException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SortService {
    private static final Pattern SORT_FIELD_PATTERN = Pattern.compile("^<{0,1}(id|name|creationDate|locationFrom\\.id|locationFrom\\.coordinates\\.x|locationFrom\\.coordinates\\.y|locationFrom\\.name|locationTo\\.id|locationTo\\.coordinates\\.x|locationTo\\.coordinates\\.y|locationTo\\.name|distance)$");

    public static Sort.Direction getSortDirection(String sortField) {
        Matcher matcher = SORT_FIELD_PATTERN.matcher(sortField);
        if (matcher.matches()) {
            return sortField.startsWith("<") ? Sort.Direction.DESC : Sort.Direction.ASC;
        } else {
            throw new ServiceFaultException("Error", new ServiceFault(400, "Invalid sort field: " + sortField,  com.example.routes.service.RouteService.getFormattedDate()));
        }
    }

    public static List<RouteEntity> sortElements(List<RouteEntity> allRoutes, List<String> sortFields, List<Sort.Direction> sortDirections) {
        if ((sortFields).size() == 0) {
            return allRoutes;
        }
        Comparator<RouteEntity> comparator = getComparator(sortFields, sortDirections);

        return allRoutes.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private static Comparator<RouteEntity> getComparator(List<String> sortFields, List<Sort.Direction> sortDirections) {
        List<Comparator<RouteEntity>> comparators = new ArrayList<>();

        for (int i = 0; i < sortFields.size(); i++) {
            Comparator<RouteEntity> fieldComparator = getFieldComparator(sortFields.get(i));
            comparators.add(sortDirections.get(i) == Sort.Direction.DESC ? fieldComparator.reversed() : fieldComparator);
        }

        Comparator<RouteEntity> comparator = comparators.get(0);
        for (int i = 1; i < comparators.size(); i++) {
            comparator = comparator.thenComparing(comparators.get(i));
        }

        return comparator;
    }

    private static Comparator<RouteEntity> getFieldComparator(String sortField) {
        sortField = sortField.startsWith("<") ? sortField.substring(1) : sortField;

        switch (sortField) {
            case "id":
                return Comparator.comparing(RouteEntity::getId);
            case "name":
                return Comparator.comparing(RouteEntity::getName);
            case "creationDate":
                return Comparator.comparing(RouteEntity::getCreationDate);
            case "locationFrom.id":
                return Comparator.comparing(route -> route.getLocationFrom().getId());
            case "locationFrom.coordinates.x":
                return Comparator.comparing(route -> route.getLocationFrom().getCoordinates().getX());
            case "locationFrom.coordinates.y":
                return Comparator.comparing(route -> route.getLocationFrom().getCoordinates().getY());
            case "locationFrom.name":
                return Comparator.comparing(route -> route.getLocationFrom().getName());
            case "locationTo.id":
                return Comparator.comparing(route -> route.getLocationTo().getId());
            case "locationTo.coordinates.x":
                return Comparator.comparing(route -> route.getLocationTo().getCoordinates().getX());
            case "locationTo.coordinates.y":
                return Comparator.comparing(route -> route.getLocationTo().getCoordinates().getY());
            case "locationTo.name":
                return Comparator.comparing(route -> route.getLocationTo().getName());
            case "distance":
                return Comparator.comparing(RouteEntity::getDistance);
            default:
                throw new ServiceFaultException("Error", new ServiceFault(400, "Invalid sort field: " + sortField,  com.example.routes.service.RouteService.getFormattedDate()));
        }
    }
}
