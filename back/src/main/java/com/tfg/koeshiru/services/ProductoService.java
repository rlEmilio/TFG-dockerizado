package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);
}
