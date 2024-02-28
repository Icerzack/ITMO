package com.example.routes.converter;

import com.example.routes.dto.CoordinateDTO;
import com.example.routes.entity.CoordinateEntity;
import com.example.routes.soap.soapgen.soap.CoordinateDTOSoap;
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

    public CoordinateDTO convertFromSoapToDTO(CoordinateDTOSoap coordinateDTOSoap) {
        CoordinateDTO dto = new CoordinateDTO();
        dto.setX(coordinateDTOSoap.getX());
        dto.setY(coordinateDTOSoap.getY());
        return dto;
    }

    public CoordinateDTOSoap convertFromDTOToSoap(CoordinateDTO coordinateDTO) {
        CoordinateDTOSoap dto = new CoordinateDTOSoap();
        dto.setX(coordinateDTO.getX());
        dto.setY(coordinateDTO.getY());
        return dto;
    }
}
