package com.example.server2.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorDefaultDTO {
    private int code;
    private String message;
    private String time;
}
