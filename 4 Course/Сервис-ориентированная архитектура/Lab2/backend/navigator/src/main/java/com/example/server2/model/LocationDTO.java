package com.example.server2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class LocationDTO {
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Valid
    @JsonProperty("coordinates")
    private CoordinateDTO coordinates;

    @NotBlank(message = "The name should not be empty")
    @JsonProperty("name")
    private String name;
}

