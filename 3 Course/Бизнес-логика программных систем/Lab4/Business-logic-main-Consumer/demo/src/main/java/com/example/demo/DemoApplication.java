package com.example.demo;

import com.example.demo.Dto.Requests.PerformPaymentRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String allWork() {
        return "All works";
    }

//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, PerformPaymentRequest> kafkaTemplate){
//        PerformPaymentRequest request = new PerformPaymentRequest(123456789, "1234567890123456", "12/23", "123", 100.0, "Some address");
//        return args -> {
//            kafkaTemplate.send("perform-test3", request);
//        };
//    }
}
