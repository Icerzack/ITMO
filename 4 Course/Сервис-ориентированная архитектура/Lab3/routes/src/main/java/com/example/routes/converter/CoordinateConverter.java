package com.example.routes.converter;

import com.example.routes.dto.CoordinateDTO;
import com.example.routes.entity.CoordinateEntity;
import org.springframework.stereotype.Component;

@Component
public class CoordinateConverter {
    public CoordinateEntity convertToEntity(CoordinateDTO dto) {
        CoordinateEntity entity = new CoordinateEntity();
        entity
                .setX(dto.getX())
                .setY(dto.getY());
        return entity;
    }

    public CoordinateDTO convertToDTO(CoordinateEntity entity) {
        CoordinateDTO dto = new CoordinateDTO();
        dto
                .setX(entity.getX())
                .setY(entity.getY());
        return dto;
    }
}
