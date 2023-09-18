package com.example.demo.Dto.Responses;

import lombok.Data;

@Data
public class CheckPhoneResponse {
    private String phone;
    private boolean result;
}