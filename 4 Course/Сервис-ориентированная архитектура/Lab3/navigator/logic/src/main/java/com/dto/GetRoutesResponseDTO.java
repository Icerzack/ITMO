package com.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetRoutesResponseDTO implements Serializable {
    @NotNull
    private List<RouteDTO> routes;
    @JsonIgnore
    private int page;
    @JsonIgnore
    private int elementsCount;

}
