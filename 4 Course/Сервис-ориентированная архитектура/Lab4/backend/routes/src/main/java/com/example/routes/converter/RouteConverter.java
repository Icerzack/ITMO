package com.example.routes.converter;

import com.example.routes.dto.RouteDTO;
import com.example.routes.entity.RouteEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import com.example.routes.soap.soapgen.soap.RouteDTOSoap;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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

    public RouteDTO convertFromSoapToDTO(RouteDTOSoap routeDTOSoap) {
        RouteDTO dto = new RouteDTO();
        if (routeDTOSoap.getId() != 0) {
            dto.setId(routeDTOSoap.getId());
        }
        dto.setName(routeDTOSoap.getName());
        dto.setFrom(locationConverter.convertFromSoapToDTO(routeDTOSoap.getFrom()));
        dto.setTo(locationConverter.convertFromSoapToDTO(routeDTOSoap.getTo()));
        dto.setDistance(routeDTOSoap.getDistance());
        dto.setCreationDate(toZonedDateTime(routeDTOSoap.getCreationDate()));
        return dto;
    }

    public RouteDTOSoap convertFromDTOToSoap(RouteDTO routeDTO) {
        RouteDTOSoap dto = new RouteDTOSoap();
        dto.setId(routeDTO.getId());
        dto.setName(routeDTO.getName());
        dto.setFrom(locationConverter.convertFromSoapToDTO(routeDTO.getFrom()));
        dto.setTo(locationConverter.convertFromSoapToDTO(routeDTO.getTo()));
        dto.setDistance(routeDTO.getDistance());
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(routeDTO.getCreationDate());
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        dto.setCreationDate(xmlGregorianCalendar);
        return dto;
    }

    public static ZonedDateTime toZonedDateTime(
            XMLGregorianCalendar calendar) {
        if (calendar != null) {
            return ZonedDateTime.of(toLocalDateTime(calendar),
                    ZoneId.systemDefault());
        }
        return null;
    }

    public static LocalDateTime toLocalDateTime(
            XMLGregorianCalendar calendar) {
        if (calendar != null) {
            ZonedDateTime zonedDateTime = calendar.toGregorianCalendar()
                    .toZonedDateTime();
            return ZonedDateTime.ofInstant(zonedDateTime.toInstant(),
                    ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }
}
