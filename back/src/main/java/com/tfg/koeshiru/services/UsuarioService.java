package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario, String http);

    void deleteById(Long id);

    void agregarActorFavorito(Long idUsuario, Long idActor);


}
