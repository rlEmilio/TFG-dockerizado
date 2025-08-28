package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Mensaje;
import com.tfg.koeshiru.exceptions.MensajeException;
import com.tfg.koeshiru.repositories.MensajeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeServiceImpl implements MensajeService {

    private static final Logger logger = LoggerFactory.getLogger(MensajeServiceImpl.class);

    @Autowired
    private MensajeRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> findAll() {
        try {
            logger.info("Obteniendo todos los mensajes");
            return (List<Mensaje>) repository.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener los mensajes: {}", e.getMessage());
            throw new MensajeException("Error al obtener la lista de mensajes");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Mensaje> findById(Long id) {
        try {
            logger.info("Buscando mensaje con ID {}", id);
            return repository.findById(id);
        } catch (Exception e) {
            logger.error("Error al buscar mensaje con ID {}: {}", id, e.getMessage());
            throw new MensajeException("Error al buscar mensaje");
        }
    }

    @Override
    @Transactional
    public Mensaje save(Mensaje mensaje) {
        try {
            logger.info("Guardando mensaje de: {}", mensaje.getUsuario());
            return repository.save(mensaje);
        } catch (Exception e) {
            logger.error("Error al guardar mensaje: {}", e.getMessage());
            throw new MensajeException("Error al guardar mensaje");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            logger.info("Eliminando mensaje con ID {}", id);
            repository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar mensaje con ID {}: {}", id, e.getMessage());
            throw new MensajeException("Error al eliminar mensaje");
        }
    }
}
