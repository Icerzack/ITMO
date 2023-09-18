package com.example.demo.Server;

import com.example.demo.Dto.Responses.CheckSmsResponse;
import com.example.demo.Dto.Responses.CheckSumResponse;
import com.example.demo.Dto.Responses.PerformPaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

@Service
public class OrderService {

    public ResponseEntity<CheckSumResponse> checkSum(Long orderId, double sum) {
        CheckSumResponse checkSumResponse = new CheckSumResponse();
        if (sum > 100) {
            checkSumResponse.setResult(true);
            return ResponseEntity.ok(checkSumResponse);
        }
        checkSumResponse.setResult(false);
        return ResponseEntity.ok(checkSumResponse);
    }

    public ResponseEntity<CheckSmsResponse> checkSms(Integer orderId, String phoneNumber, String sms) {
        CheckSmsResponse checkSmsResponse = new CheckSmsResponse();
        String sha256sms = DigestUtils.sha256Hex(String.valueOf(orderId) + phoneNumber);
        sha256sms = sha256sms.replaceAll("[^0-9]", ""); // удалится все кроме букв и цифр
        if (sms.equals(StringUtils.right(sha256sms, 4))) {
            checkSmsResponse.setResult(true);
            return ResponseEntity.ok(checkSmsResponse);
        }
        checkSmsResponse.setResult(false);
        return ResponseEntity.ok(checkSmsResponse);
    }

    public ResponseEntity<PerformPaymentResponse> performPayment(String cardNum, String cardDate, String cardCVV) {
        PerformPaymentResponse performPaymentResponse = new PerformPaymentResponse();
        if(!cardNum.matches("[-+]?\\d+") || cardNum.length() < 13 || cardNum.length() > 19 || !cardCVV.matches("[-+]?\\d+") || cardCVV.length() != 3) {
            performPaymentResponse.setResult(false);
            return ResponseEntity.ok(performPaymentResponse);
        }
        performPaymentResponse.setResult(true);
        return ResponseEntity.ok(performPaymentResponse);
    }
}
