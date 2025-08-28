package com.tfg.koeshiru.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Se necesita tabla imtermedia
@Entity
@Table(name = "trabajos")
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_trabajo;

    private String personaje;

    private String anime;

    private Integer rol;

    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
    @ManyToMany
    @JoinTable(name = "tiene", joinColumns = @JoinColumn(name = "id_trabajo"), inverseJoinColumns = @JoinColumn(name = "id_actor"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "id_actor", "id_trabajo" }) })
    @JsonIgnore
    private List<ActorDeVoz> actoresDeVoz = new ArrayList<>();

    public Trabajo() {
    }

    public Trabajo(long id_trabajo, String personaje, String anime, Integer rol) {
        this.id_trabajo = id_trabajo;
        this.personaje = personaje;
        this.anime = anime;
        this.rol = rol;
    }

    public long getId_trabajo() {
        return id_trabajo;
    }

    public void setId_trabajo(long id_trabajo) {
        this.id_trabajo = id_trabajo;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public List<ActorDeVoz> getActoresDeVoz() {
        return actoresDeVoz;
    }

    public void setActoresDeVoz(List<ActorDeVoz> actoresDeVoz) {
        this.actoresDeVoz = actoresDeVoz;
    }
}
