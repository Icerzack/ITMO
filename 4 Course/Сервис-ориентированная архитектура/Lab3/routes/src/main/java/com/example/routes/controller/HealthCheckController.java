package com.example.routes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping("/health")
  public ResponseEntity<?> checkHealth() {
    return ResponseEntity.ok().build();
  }

}