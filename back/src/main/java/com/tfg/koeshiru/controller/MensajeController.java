package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.DTOS.MensajeDTO;
import com.tfg.koeshiru.entities.Mensaje;
import com.tfg.koeshiru.entities.Usuario;
import com.tfg.koeshiru.services.MensajeService;
import com.tfg.koeshiru.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("api/mensajes")
@CrossOrigin
public class MensajeController {

    @Autowired
    private MensajeService service;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al obtener los mensajes: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            Optional<Mensaje> mensajeOptional = service.findById(id);
            if (mensajeOptional.isPresent()) {
                return ResponseEntity.ok(mensajeOptional.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "El mensaje no se encontró por el ID"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al buscar el mensaje: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MensajeDTO dto) {
        try {
            Optional<Usuario> usuarioOptional = usuarioService.findById(dto.getId_usuario());
            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("Error", "Usuario no encontrado"));
            }

            Mensaje mensaje = new Mensaje();
            mensaje.setTexto(dto.getTexto());
            mensaje.setUsuario(usuarioOptional.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(mensaje));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al crear el mensaje: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MensajeDTO mensajeDTO) {
        try {
            Optional<Mensaje> mensajeOptional = service.findById(id);
            if (mensajeOptional.isPresent()) {
                Mensaje mensajeBD = mensajeOptional.get();
                mensajeBD.setTexto(mensajeDTO.getTexto());

                Optional<Usuario> usuarioOptional = usuarioService.findById(mensajeDTO.getId_usuario());
                if (usuarioOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Collections.singletonMap("Error", "Usuario no encontrado"));
                }

                mensajeBD.setUsuario(usuarioOptional.get());
                return ResponseEntity.ok(service.save(mensajeBD));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Mensaje no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al actualizar el mensaje: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Mensaje> mensajeOptional = service.findById(id);
            if (mensajeOptional.isPresent()) {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Mensaje no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al eliminar el mensaje: " + e.getMessage()));
        }
    }
}
