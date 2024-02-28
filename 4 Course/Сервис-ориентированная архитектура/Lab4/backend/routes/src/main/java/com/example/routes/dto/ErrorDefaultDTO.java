package com.example.routes.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorDefaultDTO {
    private int code;
    private String message;
    private String time;

}
