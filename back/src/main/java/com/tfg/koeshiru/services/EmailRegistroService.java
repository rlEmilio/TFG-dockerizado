package com.tfg.koeshiru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailRegistroService {

        @Autowired
        private JavaMailSender mailSender;

        @Value("${spring.mail.username}") // Correo de tu aplicación (será el "usuario" en el servidor SMTP)
        private String fromEmail; // Este es el correo configurado para enviar los mensajes

    public void sendRegistrationNotification(String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo("koeshiru@gmail.com"); // destino
        message.setSubject("Nuevo usuario registrado");
        message.setText("Usuario " + username + " se ha registrado en la página.");
        mailSender.send(message);
    }

}



