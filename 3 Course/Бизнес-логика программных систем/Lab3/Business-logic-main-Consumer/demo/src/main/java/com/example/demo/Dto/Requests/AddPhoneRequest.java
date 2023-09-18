package com.example.demo.Dto.Requests;

import lombok.Data;

@Data
public class AddPhoneRequest {
    private long userId;
    private String phoneNumber;
}