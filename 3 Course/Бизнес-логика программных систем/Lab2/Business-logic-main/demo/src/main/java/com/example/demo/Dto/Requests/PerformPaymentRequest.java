package com.example.demo.Dto.Requests;

import lombok.Data;

@Data
public class PerformPaymentRequest {
    private String cardNum;
    private String cardDate;
    private String cardCvv;
}