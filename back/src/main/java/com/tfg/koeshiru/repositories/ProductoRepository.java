package com.tfg.koeshiru.repositories;

import com.tfg.koeshiru.entities.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Long> {
}
