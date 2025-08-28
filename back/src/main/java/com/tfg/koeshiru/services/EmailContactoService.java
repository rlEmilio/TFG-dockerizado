package com.tfg.koeshiru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailContactoService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") // Correo de tu aplicación (será el "usuario" en el servidor SMTP)
    private String fromEmail; // Este es el correo configurado para enviar los mensajes

    public void sendEmail(String userEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        // Aquí configuramos el correo del usuario como remitente
        message.setFrom(fromEmail);  
        
        String recipientEmail = "koeshiru@gmail.com";  // El correo de destino (donde se recibe el mensaje)

        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText("Mensaje de: " + userEmail + "\n\n" + body);  // Incluir el correo del usuario en el mensaje
        message.setFrom(userEmail); // Confirmamos que el "From" es el correo del usuario
        message.setReplyTo(userEmail); // Esto permite que al responder el correo, el destinatario sea el usuario


        mailSender.send(message);
    }
}
