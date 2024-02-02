package com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CoordinateDTO implements Serializable {
    @NotNull
    @JsonProperty("x")
    private int x;

    @NotNull
    @Max(value = 488, message = "Max y is 488")
    @JsonProperty("y")
    private float y;
}
