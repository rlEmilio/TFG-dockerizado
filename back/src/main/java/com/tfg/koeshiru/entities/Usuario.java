package com.tfg.koeshiru.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import jakarta.persistence.UniqueConstraint;

import jakarta.persistence.Table;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;

    private String username;

    private String nombre;

    private String apellidos;

    private String correo;

    private String contrasenia;

    private Date fecha_nacimiento;

    private String genero;

    private boolean premium;

    private String imagen;

    @OneToMany(mappedBy = "usuario") // Un usuario puede escribir muchos mensajes
    private List<Mensaje> mensajes;

    /*
     * @OneToMany(mappedBy = "usuario") //Un usuario puede pertenecer a muchas
     * comunidades
     * private List<Comunidad> comunidades;
     */

    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
    @ManyToMany
    @JoinTable(name = "pertenece", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_club"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "id_usuario", "id_club" }) })
    private List<Club> clubs;

    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
    @ManyToMany
    @JoinTable(name = "favoritos", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_actor"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "id_usuario", "id_actor" }) })
    private List<ActorDeVoz> actoresDeVoz;

    public Usuario() {
    }

    public Usuario(long id_usuario, String username, String nombre, String apellidos, String correo, String contrasenia,
            Date fecha_nacimiento, String genero, boolean premium, String imagen) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.premium = premium;
        this.imagen = imagen;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public List<ActorDeVoz> getActoresDeVoz() {
        return actoresDeVoz;
    }

    public void setActoresDeVoz(List<ActorDeVoz> actoresDeVoz) {
        this.actoresDeVoz = actoresDeVoz;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean getPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isPremium() {
        return premium;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
}
