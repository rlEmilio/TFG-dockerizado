package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.entities.Trabajo;
import com.tfg.koeshiru.services.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/trabajos")
@CrossOrigin
public class TrabajoController {

    @Autowired
    private TrabajoService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            List<Trabajo> trabajos = service.findAll();
            return ResponseEntity.ok(trabajos);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al obtener los trabajos: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            Optional<Trabajo> trabajoOptional = service.findById(id);
            if (trabajoOptional.isPresent()) {
                return ResponseEntity.ok(trabajoOptional.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "El trabajo no se encontró por el ID"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al buscar el trabajo: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Trabajo trabajo) {
        try {
            Trabajo created = service.save(trabajo);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al crear el trabajo: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Trabajo trabajo) {
        try {
            Optional<Trabajo> trabajoOptional = service.findById(id);
            if (trabajoOptional.isPresent()) {
                Trabajo trabajoBD = trabajoOptional.get();
                trabajoBD.setPersonaje(trabajo.getPersonaje());
                trabajoBD.setAnime(trabajo.getAnime());
                trabajoBD.setRol(trabajo.getRol());

                return ResponseEntity.ok(service.save(trabajoBD));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Trabajo no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al actualizar el trabajo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Trabajo> trabajoOptional = service.findById(id);
            if (trabajoOptional.isPresent()) {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Trabajo no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al eliminar el trabajo: " + e.getMessage()));
        }
    }
}
