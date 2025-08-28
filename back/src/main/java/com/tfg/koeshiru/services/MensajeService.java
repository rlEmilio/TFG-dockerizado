package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Mensaje;

import java.util.List;
import java.util.Optional;

public interface MensajeService {

    List<Mensaje> findAll();

    Optional<Mensaje> findById(Long id);

    Mensaje save(Mensaje mensaje);

    void deleteById(Long id);
}
