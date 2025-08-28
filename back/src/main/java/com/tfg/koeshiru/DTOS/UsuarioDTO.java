package com.tfg.koeshiru.DTOS;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.koeshiru.entities.ActorDeVoz;
import com.tfg.koeshiru.entities.Mensaje;
import com.tfg.koeshiru.entities.Usuario;

public class UsuarioDTO implements Serializable {

    private String nombre;

    private String genero;

    private String apellidos;

    private String correo;


    public UsuarioDTO(String nombre, String genero, String apellidos, String correo) {
        this.nombre = nombre;
        this.genero = genero;
        this.apellidos = apellidos;
        this.correo = correo;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

