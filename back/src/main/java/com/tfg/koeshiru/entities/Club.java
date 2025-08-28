package com.tfg.koeshiru.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_club;

    private String nombre;

    private int miembros;

    @Temporal(TemporalType.DATE)
    private Date fecha_creacion;

    private String descripcion;

    private String imagen;

    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
    @ManyToMany
    @JoinTable(name = "pertenece", joinColumns = @JoinColumn(name = "id_club"), inverseJoinColumns = @JoinColumn(name = "id_usuario"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "id_club", "id_usuario" }) })
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();

    public Club() {
    }

    public Club(long id_club, String nombre, int miembros, Date fecha_creacion, String descripcion, String imagen) {
        this.id_club = id_club;
        this.nombre = nombre;
        this.miembros = miembros;
        this.fecha_creacion = fecha_creacion;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public long getId_club() {
        return id_club;
    }

    public void setId_club(long id_club) {
        this.id_club = id_club;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMiembros() {
        return miembros;
    }

    public void setMiembros(int miembros) {
        this.miembros = miembros;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
