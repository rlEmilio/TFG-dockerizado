package com.tfg.koeshiru.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long id_mensaje;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario; // Relación directa con el usuario, si el mensaje pertenece a un usuario directamente.

    @Column(name = "texto", nullable = false)
    private String texto;

    public Mensaje() {
    }

    public Mensaje(Usuario usuario, String texto) {
        this.usuario = usuario;
        this.texto = texto;
    }

    public Long getIdMensaje() {
        return id_mensaje;
    }

    public void setIdMensaje(Long id_mensaje) {
        this.id_mensaje = id_mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @JsonProperty("id_usuario")
    public Long getIdUsuario() {
        return usuario != null ? usuario.getId_usuario() : null;
    }
}
