package com.example.routes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteDTO implements Serializable {
    @Digits(message = "id should be Long number", integer = 15, fraction = 0)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private ZonedDateTime creationDate;
    @NotNull
    @NotBlank(message = "The name should not be empty")
    private String name;
    @NotNull
    @Valid
    private LocationDTO from;

    @NotNull
    @Valid
    private LocationDTO to;
    @NotNull
    @Min(value = 1, message = "The distance must be no less than 1")
    private Float distance;
}