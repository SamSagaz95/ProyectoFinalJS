package com.example.proyectofinal;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Votos {
    public String uid;
    public Integer idPartido;
    public Integer apuesta;



    public Votos(){

    }

    public Votos(String uid, Integer idPartido, Integer apuesta) {
        this.uid = uid;
        this.idPartido = idPartido;
        this.apuesta = apuesta;


    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("idPartido", idPartido);
        result.put("apuesta", apuesta);

        return result;
    }
}
