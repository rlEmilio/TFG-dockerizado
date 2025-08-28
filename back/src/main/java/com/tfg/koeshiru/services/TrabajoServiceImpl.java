package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Trabajo;
import com.tfg.koeshiru.exceptions.TrabajoException;
import com.tfg.koeshiru.repositories.TrabajoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajoServiceImpl implements TrabajoService {

    private static final Logger logger = LoggerFactory.getLogger(TrabajoServiceImpl.class);

    @Autowired
    private TrabajoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findAll() {
        try {
            logger.info("Obteniendo todos los trabajos");
            return (List<Trabajo>) repository.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener trabajos: {}", e.getMessage());
            throw new TrabajoException("Error al obtener la lista de trabajos");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trabajo> findById(Long id) {
        try {
            logger.info("Buscando trabajo con ID {}", id);
            return repository.findById(id);
        } catch (Exception e) {
            logger.error("Error al buscar trabajo con ID {}: {}", id, e.getMessage());
            throw new TrabajoException("Error al buscar trabajo");
        }
    }

    @Override
    @Transactional
    public Trabajo save(Trabajo trabajo) {
        try {
            logger.info("Guardando trabajo con ID {}", trabajo.getId_trabajo());
            return repository.save(trabajo);
        } catch (Exception e) {
            logger.error("Error al guardar trabajo con ID {}: {}", trabajo.getId_trabajo(), e.getMessage());
            throw new TrabajoException("Error al guardar trabajo");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            logger.info("Eliminando trabajo con ID {}", id);
            repository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar trabajo con ID {}: {}", id, e.getMessage());
            throw new TrabajoException("Error al eliminar trabajo");
        }
    }
}
