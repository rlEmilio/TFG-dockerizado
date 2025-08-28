package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.entities.Club;
import com.tfg.koeshiru.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("api/clubs")
@CrossOrigin
public class ClubController {

    @Autowired
    private ClubService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Error", "No se pudieron obtener los clubes"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            Optional<Club> clubOptional = service.findById(id);
            if (clubOptional.isPresent()) {
                return ResponseEntity.ok(clubOptional.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "El club no se encontró por el ID"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Error", "Error al buscar el club"));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Club club) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(club));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("Error", "No se pudo crear el club"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Club club) {
        try {
            Optional<Club> clubOptional = service.findById(id);
            if (clubOptional.isPresent()) {
                Club clubBD = clubOptional.get();
                clubBD.setNombre(club.getNombre());
                clubBD.setMiembros(club.getMiembros());
                clubBD.setFecha_creacion(club.getFecha_creacion());
                clubBD.setDescripcion(club.getDescripcion());
                clubBD.setImagen(club.getImagen());
                clubBD.setUsuarios(club.getUsuarios());

                return ResponseEntity.ok(service.save(clubBD));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "El club no se encontró por el ID"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Error", "Error al actualizar el club"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Club> clubOptional = service.findById(id);
            if (clubOptional.isPresent()) {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "El club no se encontró por el ID"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Error", "Error al eliminar el club"));
        }
    }


    @PostMapping("/agregar_usuario/{idClub}/{idUsuario}")
    public ResponseEntity<?> agregarUsuarioAClub(@PathVariable Long idClub, @PathVariable Long idUsuario) {
        try {
            service.agregarUsuarioAClub(idClub, idUsuario);
            return ResponseEntity.ok(Collections.singletonMap("Mensaje", "Usuario agregado al club correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Error", "Error al agregar usuario al club"));
        }
    }

}
