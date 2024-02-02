package com.example.server2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    @Digits(message = "id should be Long number", integer = 15, fraction = 0)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String creationDate;
    @NotNull
    @NotBlank(message = "The name should not be empty")
    @JsonProperty("name")
    private String name;
    @NotNull
    @Valid
    @JsonProperty("from")
    private LocationDTO from;

    @NotNull
    @Valid
    @JsonProperty("to")
    private LocationDTO to;
    @NotNull
    @Min(value = 1, message = "The distance must be no less than 1")
    @JsonProperty("distance")
    private Float distance;
}