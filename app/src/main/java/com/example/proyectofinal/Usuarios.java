package com.example.proyectofinal;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("email", email);
        result.put("nombre", nombre);
        result.put("apellidos", apellidos);
        result.put("puntuacion", puntuacion);

        return result;
    }
}
