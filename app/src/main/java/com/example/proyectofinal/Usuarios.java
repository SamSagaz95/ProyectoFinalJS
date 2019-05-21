package com.example.proyectofinal;

public class Usuarios {

    public String uid;
    public String email;
    public String nombre;
    public String apellidos;
    public Integer puntuacion;

    public Usuarios(){

    }

    public Usuarios(String uid, String email, String nombre, String apellidos, Integer puntuacion) {
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puntuacion = puntuacion;
    }
}
