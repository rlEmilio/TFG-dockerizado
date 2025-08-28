package com.tfg.koeshiru.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.tfg.koeshiru.services.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    private final PayPalService payPalService;

    @Autowired
    public PayPalController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    //endpoint crear orden
    @PostMapping("/create-order")
    public ResponseEntity<Map<String, String>> createOrder() {
        try {
            String orderId = payPalService.createOrder();
            Map<String, String> response = new HashMap<>();
            response.put("orderId", orderId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //endpoint capturar orden
    @PostMapping("/capture-order")
    public ResponseEntity<Map<String, String>> captureOrder(@RequestBody Map<String, String> body) {
        String orderId = body.get("orderId");
        String userId = body.get("userId"); // 👈 lo recibes

        try {
            Map<String, String> response = payPalService.captureOrder(orderId, userId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}