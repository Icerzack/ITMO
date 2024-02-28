package com.example.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Accessors(chain = true)
@JsonPropertyOrder(value = {"routes", "page", "elementsCount"})
public class RoutesWithPagingDTO {
    @NotNull
    @JsonProperty("routes")
    private List<RouteDTO> routesDTO;
    @NotNull
    private int page;
    @NotNull
    private int elementsCount;

}
