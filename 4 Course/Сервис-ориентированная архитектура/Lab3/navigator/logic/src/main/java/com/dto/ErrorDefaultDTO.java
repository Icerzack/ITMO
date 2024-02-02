package com.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorDefaultDTO implements Serializable {
    private int code;
    private String message;
    private String time;
}
