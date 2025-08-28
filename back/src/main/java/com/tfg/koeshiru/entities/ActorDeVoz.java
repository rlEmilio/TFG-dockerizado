package com.tfg.koeshiru.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Table;

@Entity
@Table(name = "actores_de_voz")
public class ActorDeVoz {

    @Id
    private long id_actor;

    private String nombre;
    // consultar conversion fecha bbdd/java
    private String fecha_nacimiento;
    private long popularidad;
    private String imagen;

    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
    @ManyToMany
    @JoinTable(name = "favoritos", joinColumns = @JoinColumn(name = "id_actor"), inverseJoinColumns = @JoinColumn(name = "id_usuario"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "id_usuario", "id_actor" }) })
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();


    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
    @ManyToMany
    @JoinTable(name = "tiene", joinColumns = @JoinColumn(name = "id_actor"), inverseJoinColumns = @JoinColumn(name = "id_trabajo"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "id_actor", "id_trabajo" }) })
    private List<Trabajo> trabajos = new ArrayList<>();

    public ActorDeVoz() {
    }

    public ActorDeVoz(long id_actor, String nombre, String fecha_nacimiento, long popularidad, String imagen) {
        this.id_actor = id_actor;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.popularidad = popularidad;
        this.imagen = imagen;
    }

    public long getId_actor() {
        return id_actor;
    }

    public void setId_actor(long id_actor) {
        this.id_actor = id_actor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public long getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(long popularidad) {
        this.popularidad = popularidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }
}
