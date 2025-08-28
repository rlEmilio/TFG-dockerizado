package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Club;

import java.util.List;
import java.util.Optional;

public interface ClubService {

    List<Club> findAll();

    Optional<Club> findById(Long id);

    Club save(Club club);

    void deleteById(Long id);

   public void agregarUsuarioAClub(Long idClub, Long idUsuario);

}
