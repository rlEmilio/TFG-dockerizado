package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.DTOS.EmailContactoRequest;
import com.tfg.koeshiru.services.EmailContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/contacto")
public class EmailContactoController {

    @Autowired
    private EmailContactoService emailService;

    @PostMapping
    public ResponseEntity<?> receiveEmail(@RequestBody EmailContactoRequest emailRequest) {
        try {
            emailService.sendEmail(emailRequest.getUserEmail(), emailRequest.getSubject(), emailRequest.getBody());
            return ResponseEntity.ok("Correo enviado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al enviar el correo: " + e.getMessage());
        }
    }
}


	

