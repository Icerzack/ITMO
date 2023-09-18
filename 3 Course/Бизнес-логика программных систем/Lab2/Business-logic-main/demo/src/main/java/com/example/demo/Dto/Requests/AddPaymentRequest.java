package com.example.demo.Dto.Requests;

import lombok.Data;

@Data
public class AddPaymentRequest {
    private int userId;
    private String cardNum;
    private String cardDate;
    private String cardCvv;
}