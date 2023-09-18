package com.example.demo.Service;

import com.example.demo.Dto.Requests.PerformPaymentRequest;
import com.example.demo.Dto.Responses.CheckSmsResponse;
import com.example.demo.Dto.Responses.CheckSumResponse;
import com.example.demo.Dto.Responses.PerformPaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

@Service
public class OrderService {
    private KafkaTemplate<String, PerformPaymentRequest> kafkaTemplate;
    public OrderService(KafkaTemplate<String, PerformPaymentRequest> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public CheckSumResponse checkSum(Long orderId, double sum) {
        CheckSumResponse checkSumResponse = new CheckSumResponse();
        if (sum > 500) {
            checkSumResponse.setResult(true);
            return checkSumResponse;
        }
        checkSumResponse.setResult(false);
        return checkSumResponse;
    }

    public CheckSmsResponse checkSms(Long orderId, String phoneNumber, String sms) {
        CheckSmsResponse checkSmsResponse = new CheckSmsResponse();
        String sha256sms = DigestUtils.sha256Hex(orderId + phoneNumber);
        sha256sms = sha256sms.replaceAll("[^0-9]", ""); // удалится все кроме цифр
        if (sms.equals(StringUtils.right(sha256sms, 4))) {
            checkSmsResponse.setResult(true);
            return checkSmsResponse;
        }
        checkSmsResponse.setResult(false);
        return checkSmsResponse;
    }

    public PerformPaymentResponse performPayment(long userId, String cardNum, String cardDate, String cardCVV, Double cost, String address) {
        PerformPaymentResponse performPaymentResponse = new PerformPaymentResponse();
        if(!cardNum.matches("[-+]?\\d+") || cardNum.length() < 13 || cardNum.length() > 19 || !cardCVV.matches("[-+]?\\d+") || cardCVV.length() != 3) {
            performPaymentResponse.setResult(false);
            return performPaymentResponse;
        }
        PerformPaymentRequest performPaymentRequest = new PerformPaymentRequest(userId, cardNum, cardDate, cardCVV, cost, address);
//        kafkaTemplate.send("perform-test3", performPaymentRequest);
        performPaymentResponse.setResult(true);
        return performPaymentResponse;
    }
}
