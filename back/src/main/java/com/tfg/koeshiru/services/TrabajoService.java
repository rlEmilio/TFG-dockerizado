package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Trabajo;

import java.util.List;
import java.util.Optional;

public interface TrabajoService {

    List<Trabajo> findAll();

    Optional<Trabajo> findById(Long id);

    Trabajo save(Trabajo Trabajo);

    void deleteById(Long id);
}
