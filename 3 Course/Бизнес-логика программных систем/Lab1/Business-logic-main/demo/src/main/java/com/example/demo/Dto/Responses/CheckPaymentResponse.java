package com.example.demo.Dto.Responses;

import lombok.Data;

@Data
public class CheckPaymentResponse {
    private boolean result;
    private String cardNumber;
    private String cardDate;
    private String cardCvv;
}