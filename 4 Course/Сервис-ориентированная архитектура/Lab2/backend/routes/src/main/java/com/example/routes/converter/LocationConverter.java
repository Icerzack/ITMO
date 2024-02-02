package com.example.routes.converter;

import com.example.routes.dto.LocationDTO;
import com.example.routes.entity.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {
    private final CoordinateConverter coordinateConverter;

    public LocationConverter(CoordinateConverter coordinateConverter) {
        this.coordinateConverter = coordinateConverter;
    }

    public LocationEntity convertToEntity(LocationDTO dto) {
        LocationEntity entity = new LocationEntity();
        entity
                .setId(dto.getId())
                .setName(dto.getName())
                .setCoordinates(coordinateConverter.convertToEntity(dto.getCoordinates()));
        return entity;
    }

    public LocationDTO convertToDTO(LocationEntity entity) {
        LocationDTO dto = new LocationDTO();
        dto
                .setId(entity.getId())
                .setName(entity.getName())
                .setCoordinates(coordinateConverter.convertToDTO(entity.getCoordinates()));
        return dto;
    }
}
