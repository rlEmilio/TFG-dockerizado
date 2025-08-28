package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.DTOS.EmailRegistroRequest;
import com.tfg.koeshiru.services.EmailRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/registro")
public class EmailRegistroController {

    @Autowired
    private EmailRegistroService emailRegistroService;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody EmailRegistroRequest request) {
        try {
            emailRegistroService.sendRegistrationNotification(request.getUsername());
            return ResponseEntity.ok("Notificación de registro enviada");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al enviar la notificación: " + e.getMessage()));
        }
    }
}



