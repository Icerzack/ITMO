package com.example.demo.Controller;

import com.example.demo.Dto.Requests.PerformPaymentRequest;
import com.example.demo.Dto.Responses.CheckSmsResponse;
import com.example.demo.Dto.Responses.CheckSumResponse;
import com.example.demo.Dto.Responses.PerformPaymentResponse;
import com.example.demo.Server.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Order", description = "Эндпоинты для взаимодействия с заказом")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/v1/order-management/check-sum")
    @ApiOperation(value = "Проверяет, что сумма больше 100")
    public ResponseEntity<CheckSumResponse> checkSum(@RequestParam int id, @RequestParam int sum) {
        return orderService.checkSum((long) id, sum);
    }

    @GetMapping("/v1/order-management/check-sms")
    @ApiOperation(value = "Проверяет, что введенный код смс верен для данного номера")
    public ResponseEntity<CheckSmsResponse> checkSms(@RequestParam int id, @RequestParam String phone, @RequestParam String sms) {
        return orderService.checkSms(id, phone, sms);
    }

    @PostMapping("/v1/order-management/perform-payment")
    @ApiOperation(value = "Осуществляет покупку с указанными данными карты")
    public ResponseEntity<PerformPaymentResponse> performPayment(@RequestBody PerformPaymentRequest request) {
        String cardNum = request.getCardNum();
        String cardDate = request.getCardDate();
        String cardCvv = request.getCardCvv();
        return orderService.performPayment(cardNum, cardDate, cardCvv);
    }

}