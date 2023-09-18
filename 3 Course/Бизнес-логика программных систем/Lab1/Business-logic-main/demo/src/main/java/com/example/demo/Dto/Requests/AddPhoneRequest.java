package com.example.demo.Dto.Requests;

import lombok.Data;

@Data
public class AddPhoneRequest {
    private int userId;
    private String phoneNumber;
}