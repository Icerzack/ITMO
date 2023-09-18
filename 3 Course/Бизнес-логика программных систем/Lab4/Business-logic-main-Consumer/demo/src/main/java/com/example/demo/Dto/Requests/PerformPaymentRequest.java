package com.example.demo.Dto.Requests;

import lombok.Data;

@Data
public class PerformPaymentRequest {
    private long userId;
    private String cardNum;
    private String cardDate;
    private String cardCVV;
    private Double cost;
    private String address;

    public PerformPaymentRequest(){

    }
    public PerformPaymentRequest(long userId, String cardNum, String cardDate, String cardCVV, Double cost, String address){
        this.userId = userId;
        this.cardNum = cardNum;
        this.cardDate = cardDate;
        this.cardCVV = cardCVV;
        this.cost = cost;
        this.address = address;
    }
}