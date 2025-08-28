package com.tfg.koeshiru.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    private String nombre;

    private String enlace;

    private String imagen;

    public Producto() {
    }

    public Producto(Long id_producto, String nombre, String enlace, String imagen) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.enlace = enlace;
        this.imagen = imagen;
    }

    public Long getId() {
        return id_producto;
    }

    public void setId(Long id) {
        this.id_producto = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
