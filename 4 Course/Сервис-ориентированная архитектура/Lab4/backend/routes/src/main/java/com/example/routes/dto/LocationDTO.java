package com.example.routes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class LocationDTO {
    private Long id;

    @NotNull
    @Valid
    private CoordinateDTO coordinates;

    @NotBlank(message = "The name should not be empty")
    private String name;
}
