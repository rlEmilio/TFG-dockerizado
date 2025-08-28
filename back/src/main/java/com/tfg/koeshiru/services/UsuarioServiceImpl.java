package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Usuario;
import com.tfg.koeshiru.exceptions.UsuarioException;
import com.tfg.koeshiru.repositories.ActorDeVozRepository;
import com.tfg.koeshiru.repositories.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.koeshiru.entities.ActorDeVoz;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActorDeVozRepository actorDeVozRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        try {
            logger.info("Obteniendo todos los usuarios");
            return (List<Usuario>) usuarioRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener usuarios: {}", e.getMessage());
            throw new UsuarioException("Error al obtener la lista de usuarios");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        try {
            logger.info("Buscando usuario con ID {}", id);
            return usuarioRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error al buscar usuario con ID {}: {}", id, e.getMessage());
            throw new UsuarioException("Error al buscar usuario");
        }
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario, String http) {
        try {
            logger.info("Guardando usuario: {}", usuario.getUsername());
            if (http == "POST") {
                usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
            }
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            logger.error("Error al guardar usuario {}: {}", usuario.getUsername(), e.getMessage());
            throw new UsuarioException("Error al guardar usuario");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            logger.info("Eliminando usuario con ID {}", id);
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar usuario con ID {}: {}", id, e.getMessage());
            throw new UsuarioException("Error al eliminar usuario");
        }
    }

    @Override
    public void agregarActorFavorito(Long idUsuario, Long idActor) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ActorDeVoz actor = actorDeVozRepository.findById(idActor)
                .orElseThrow(() -> new RuntimeException("Actor no encontrado"));

        if (usuario.getActoresDeVoz().contains(actor)) {
            throw new RuntimeException("El actor ya está en favoritos");
        }

        usuario.getActoresDeVoz().add(actor);
        usuarioRepository.save(usuario);
    }


}
