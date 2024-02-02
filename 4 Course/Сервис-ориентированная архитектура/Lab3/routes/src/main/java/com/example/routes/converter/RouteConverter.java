package com.example.routes.converter;

import com.example.routes.dto.RouteDTO;
import com.example.routes.entity.RouteEntity;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class RouteConverter {
    private final LocationConverter locationConverter;

    public RouteConverter(LocationConverter locationConverter) {
        this.locationConverter = locationConverter;
    }

    public RouteEntity convertToEntity(RouteDTO dto) {
        RouteEntity entity = new RouteEntity();
        entity
                .setId(dto.getId())
                .setName(dto.getName())
                .setCreationDate(ZonedDateTime.now())
                .setLocationFrom(locationConverter.convertToEntity(dto.getFrom()))
                .setLocationTo(locationConverter.convertToEntity(dto.getTo()))
                .setDistance(dto.getDistance());
        return entity;
    }

    public RouteEntity convertToEntityWithoutCreationDate(RouteDTO dto) {
        RouteEntity entity = new RouteEntity();
        entity
                .setId(dto.getId())
                .setName(dto.getName())
                .setCreationDate(dto.getCreationDate())
                .setLocationFrom(locationConverter.convertToEntity(dto.getFrom()))
                .setLocationTo(locationConverter.convertToEntity(dto.getTo()))
                .setDistance(dto.getDistance());
        return entity;
    }

    public RouteDTO convertToDTO(RouteEntity entity) {
        RouteDTO dto = new RouteDTO();
        dto
                .setId(entity.getId())
                .setName(entity.getName())
                .setCreationDate(ZonedDateTime.parse(entity.getCreationDate().toString()))
                .setFrom(locationConverter.convertToDTO(entity.getLocationFrom()))
                .setTo(locationConverter.convertToDTO(entity.getLocationTo()))
                .setDistance(entity.getDistance());
        return dto;
    }
}
