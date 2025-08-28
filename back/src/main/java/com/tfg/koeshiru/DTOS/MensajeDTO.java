package com.tfg.koeshiru.DTOS;

import java.io.Serializable;

public class MensajeDTO implements Serializable {

    private Long id_usuario;
    private String texto;

    public MensajeDTO() {}

    public MensajeDTO(Long id_usuario, String texto) {
        this.id_usuario = id_usuario;
        this.texto = texto;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
