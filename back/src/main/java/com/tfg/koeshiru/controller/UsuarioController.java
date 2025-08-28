package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.DTOS.UsuarioDTO;
import com.tfg.koeshiru.entities.Usuario;
// import com.tfg.koeshiru.services.ActorDeVozService;
import com.tfg.koeshiru.services.ImageService;
import com.tfg.koeshiru.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService service;
    // @Autowired
    // private ActorDeVozService actorService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            List<Usuario> usuarios = (List<Usuario>) service.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al obtener usuarios: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioOptional = service.findById(id);
            if (usuarioOptional.isPresent()) {
                return ResponseEntity.ok(usuarioOptional.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "El usuario no se encontró por el ID"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al buscar usuario: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        try {
            Usuario created = service.save(usuario, "POST");
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al crear usuario: " + e.getMessage()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestPart("username") String nombre,
            @RequestPart("contrasenia") String contrasenia,
            @RequestPart("genero") String genero,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            Optional<Usuario> usuarioOptional = service.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuarioBD = usuarioOptional.get();

                usuarioBD.setNombre(nombre);
                usuarioBD.setGenero(genero);
                if (contrasenia != null && !contrasenia.isEmpty()) {
                    usuarioBD.setContrasenia(passwordEncoder.encode(contrasenia));
                }



                // Guardar nueva imagen si viene
                if (imagen != null && !imagen.isEmpty()) {
                    imageService.saveImageForUser(imagen, id);
                    // La entidad se actualiza internamente en saveImageForUser
                }

                Usuario updated = service.save(usuarioBD, "PUT");
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Usuario no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Error", "Error al actualizar usuario: " + e.getMessage()));
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioOptional = service.findById(id);
            if (usuarioOptional.isPresent()) {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Usuario no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al eliminar usuario: " + e.getMessage()));
        }
    }


    @PostMapping("/agregar_actor_favorito/{idUsuario}/{idActor}")
    public ResponseEntity<?> agregarActorFavorito(
            @PathVariable Long idUsuario,
            @PathVariable Long idActor) {
        try {
            service.agregarActorFavorito(idUsuario, idActor);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Actor agregado a favoritos correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al agregar actor favorito"));
        }
    }

}
