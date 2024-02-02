package com.example.routes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties
public class SpecialOfferQueryDTO implements Serializable {
    private String origin;
    private String destination;
    private String currency;
    private String locale;
}
