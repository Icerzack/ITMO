package com.example.routes.entity;

import com.example.routes.dto.RouteDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Accessors(chain = true)
public class RoutesWithPagingEntity {
    @NotNull
    private List<RouteEntity> routesEntity;
    @NotNull
    private int page;
    @NotNull
    private int elementsCount;

}
