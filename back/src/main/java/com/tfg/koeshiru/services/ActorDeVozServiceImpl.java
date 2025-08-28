package com.tfg.koeshiru.services;

import java.util.List;
import java.util.Optional;

import com.tfg.koeshiru.exceptions.ActorDeVozException;
import com.tfg.koeshiru.entities.ActorDeVoz;
import com.tfg.koeshiru.repositories.ActorDeVozRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActorDeVozServiceImpl implements ActorDeVozService {

    private static final Logger logger = LoggerFactory.getLogger(ActorDeVozServiceImpl.class);

    @Autowired
    private ActorDeVozRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ActorDeVoz> findAll() {
        try {
            logger.info("Obteniendo todos los actores de voz");
            return (List<ActorDeVoz>) repository.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener los actores de voz: {}", e.getMessage());
            throw new ActorDeVozException("Error al obtener la lista de actores de voz");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActorDeVoz> findById(Long id) {
        try {
            logger.info("Buscando actor de voz con ID {}", id);
            return repository.findById(id);
        } catch (Exception e) {
            logger.error("Error al buscar actor de voz con ID {}: {}", id, e.getMessage());
            throw new ActorDeVozException("Error al buscar actor de voz");
        }
    }

    @Override
    @Transactional
    public ActorDeVoz save(ActorDeVoz actorDeVoz) {
        try {
            logger.info("Guardando actor de voz: {}", actorDeVoz.getNombre());
            return repository.save(actorDeVoz);
        } catch (Exception e) {
            logger.error("Error al guardar actor de voz {}: {}", actorDeVoz.getNombre(), e.getMessage());
            throw new ActorDeVozException("Error al guardar actor de voz");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            logger.info("Eliminando actor de voz con ID {}", id);
            repository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar actor de voz con ID {}: {}", id, e.getMessage());
            throw new ActorDeVozException("Error al eliminar actor de voz");
        }
    }
}
