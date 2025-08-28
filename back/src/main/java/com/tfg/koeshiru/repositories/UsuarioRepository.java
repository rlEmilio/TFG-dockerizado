package com.tfg.koeshiru.repositories;

import com.tfg.koeshiru.entities.Usuario;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
