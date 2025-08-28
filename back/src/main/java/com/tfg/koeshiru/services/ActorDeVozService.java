package com.tfg.koeshiru.services;

import java.util.List;
import java.util.Optional;

import com.tfg.koeshiru.entities.ActorDeVoz;

public interface ActorDeVozService {
    List<ActorDeVoz> findAll();

    Optional<ActorDeVoz> findById(Long id);

    ActorDeVoz save(ActorDeVoz actorDeVoz);

    void deleteById(Long id);
}
