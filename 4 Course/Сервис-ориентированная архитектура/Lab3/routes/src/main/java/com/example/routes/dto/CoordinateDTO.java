package com.example.routes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class CoordinateDTO implements Serializable {
    @NotNull
    private int x;

    @NotNull
    @Max(value = 488, message = "Max y is 488")
    private float y;
}
