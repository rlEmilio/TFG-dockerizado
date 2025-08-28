package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Producto;
import com.tfg.koeshiru.exceptions.ProductoException;
import com.tfg.koeshiru.repositories.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private ProductoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        try {
            logger.info("Obteniendo todos los productos");
            return (List<Producto>) repository.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener productos: {}", e.getMessage());
            throw new ProductoException("Error al obtener la lista de productos");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        try {
            logger.info("Buscando producto con ID {}", id);
            return repository.findById(id);
        } catch (Exception e) {
            logger.error("Error al buscar producto con ID {}: {}", id, e.getMessage());
            throw new ProductoException("Error al buscar producto");
        }
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        try {
            logger.info("Guardando producto con ID {}", producto.getId());
            return repository.save(producto);
        } catch (Exception e) {
            logger.error("Error al guardar producto con ID {}: {}", producto.getId(), e.getMessage());
            throw new ProductoException("Error al guardar producto");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            logger.info("Eliminando producto con ID {}", id);
            repository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar producto con ID {}: {}", id, e.getMessage());
            throw new ProductoException("Error al eliminar producto");
        }
    }
}
