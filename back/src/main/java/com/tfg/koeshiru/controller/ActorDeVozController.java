package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.entities.ActorDeVoz;
import com.tfg.koeshiru.services.ActorDeVozService;
// import com.tfg.koeshiru.services.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/actores")
@CrossOrigin
public class ActorDeVozController {

    @Autowired
    private ActorDeVozService service;

    // @Autowired
    // private TrabajoService trabajoService;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            List<ActorDeVoz> actores = service.findAll();
            return ResponseEntity.ok(actores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "No se pudo obtener la lista de actores."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            Optional<ActorDeVoz> actorOptional = service.findById(id);
            return actorOptional.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body((ActorDeVoz) Collections.singletonMap("error",
                                    "El actor de voz no se encontró por el ID")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al buscar el actor por ID."));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ActorDeVoz actorDeVoz) {
        try {
            ActorDeVoz nuevo = service.save(actorDeVoz);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "No se pudo crear el actor de voz."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ActorDeVoz actorDeVoz) {
        try {
            Optional<ActorDeVoz> actorOptional = service.findById(id);
            if (actorOptional.isPresent()) {
                ActorDeVoz actorBD = actorOptional.get();
                actorBD.setNombre(actorDeVoz.getNombre());
                actorBD.setFecha_nacimiento(actorDeVoz.getFecha_nacimiento());
                actorBD.setPopularidad(actorDeVoz.getPopularidad());
                actorBD.setImagen(actorDeVoz.getImagen());
                actorBD.setUsuarios(actorDeVoz.getUsuarios());

                return ResponseEntity.ok(service.save(actorBD));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Actor de voz no encontrado para actualizar."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "No se pudo actualizar el actor de voz."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<ActorDeVoz> actorOptional = service.findById(id);
            if (actorOptional.isPresent()) {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Actor de voz no encontrado para eliminar."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "No se pudo eliminar el actor de voz."));
        }
    }
}
