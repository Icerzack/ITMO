package com.example.server2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CoordinateDTO {
    @NotNull
    @JsonProperty("x")
    private int x;

    @NotNull
    @Max(value = 488, message = "Max y is 488")
    @JsonProperty("y")
    private float y;
}
