package com.example.routes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties
public class QueryDTO implements Serializable {
    private List<Long> id = null;
    private List<String> name = null;
    private List<String> creationDate = null;
    private List<Integer> locationFromId = null;
    private List<Integer> locationFromCoordinatesX = null;
    private List<Float> locationFromCoordinatesY = null;
    private List<String> locationFromName = null;
    private List<Integer> locationToId = null;
    private List<Integer> locationToCoordinatesX = null;
    private List<Float> locationToCoordinatesY = null;
    private List<String> locationToName = null;
    private List<Float> distance = null;
    private List<Sort.Direction> sortDirection;
    private List<String> sort;
    private List<String> filter;
    private Integer page;
    private Integer elementsCount;

}
