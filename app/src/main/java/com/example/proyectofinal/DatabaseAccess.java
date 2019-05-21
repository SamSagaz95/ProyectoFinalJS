package com.example.proyectofinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<String> getJuegos() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Juegos", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getEquipos(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT nombre FROM Equipos WHERE competicion = "+ competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getEquiposOrdenados(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT nombre FROM Equipos WHERE competicion = "+ competicion +" ORDER BY puntuacion  DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getIdEquipos(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT idEquipo FROM Equipos WHERE competicion = "+ competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getIdEquiposOrdenados(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT idEquipo FROM Equipos WHERE competicion = "+ competicion +" ORDER BY puntuacion DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getIconEquipos(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT icono FROM Equipos WHERE competicion = "+ competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getIconEquiposOrdenados(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT icono FROM Equipos WHERE competicion = "+ competicion  +" ORDER BY puntuacion DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getPuntosEquiposOrdenados(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT puntuacion FROM Equipos WHERE competicion = "+ competicion  +" ORDER BY puntuacion DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getProximaJornada(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT jornada FROM Resultados WHERE competicion = "+ competicion +" AND disputado = 0 ORDER BY jornada", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getIdEvento(int jornada, int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT idPartido FROM Resultados WHERE jornada = "+ jornada +" AND competicion = "+ competicion +" AND disputado = 0 ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getLocalEvento(int jornada, int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT local FROM Resultados WHERE jornada = "+ jornada +" AND competicion = "+ competicion+" AND disputado = 0 ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getVisitanteEvento(int jornada, int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT visitante FROM Resultados WHERE jornada = "+ jornada +" AND competicion = "+ competicion+" AND disputado = 0 ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getFechaEvento(int jornada , int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT fecha FROM Resultados WHERE jornada = "+ jornada +" AND competicion = "+ competicion +" AND disputado = 0 ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getIcono(int id) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT icono FROM Equipos WHERE idEquipo = " +id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getIdNoticia(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT IdNoticia FROM Noticias WHERE competicion = " +competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getTituloNoticia(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Titulo FROM Noticias WHERE competicion = " +competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getCuerpoNoticia(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Cuerpo FROM Noticias WHERE competicion = " +competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
}
    public List<String> getFotoNoticia(int competicion) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Foto FROM Noticias WHERE competicion = " +competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getTituloNoticiaByID(int idNoticia) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Titulo FROM Noticias WHERE IdNoticia = " +idNoticia, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getCuerpoNoticiaByID(int idNoticia) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Cuerpo FROM Noticias WHERE IdNoticia = " +idNoticia, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> getFotoNoticiaByID(int idNoticia) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Foto FROM Noticias WHERE IdNoticia = " +idNoticia, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getCompeticiones(int juego) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT nombre FROM Competiciones WHERE juego = "+ juego, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getIconCompeticiones(int juego) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT icono FROM Competiciones WHERE juego = "+ juego, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getIdCompeticiones(int juego) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT idCompeticion FROM Competiciones WHERE juego = "+ juego, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getIconCompeticion(int competicion) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT icono FROM Competiciones WHERE idCompeticion = "+ competicion, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getIconEquipo(int equipo) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT icono FROM Equipos WHERE idEquipo = "+ equipo, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getIconPaisEquipo(int equipo) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT bandera From Paises Where idPais = (SELECT pais FROM Equipos Where idEquipo = "+ equipo +")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getNameEquipo(int equipo) {
        String name = "";
        Cursor cursor = database.rawQuery("SELECT nombre From Equipos Where idEquipo = "+ equipo , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return name;
    }

    public List<String> getJugadores(int equipo) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT nombre From Jugadores Where equipo = "+ equipo , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getIconJugadores(int equipo) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT foto From Jugadores Where equipo = "+ equipo , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getIdJugadores(int equipo) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT idJugador From Jugadores Where equipo = "+ equipo , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getIconPaisJugador(int jugador) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT bandera From Paises Where idPais = (SELECT pais FROM Jugadores Where idJugador = "+ jugador +")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getIconJugador(int jugador) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT foto From Jugadores Where idJugador = "+ jugador, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getIdEquipoJugador(int jugador) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT equipo From Jugadores Where idJugador = "+ jugador, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getNombreJugador(int jugador) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT nombre From Jugadores Where idJugador = "+ jugador, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getEdadJugador(int jugador) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT edad From Jugadores Where idJugador = "+ jugador, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getPosicionJugador(int jugador) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT rol From Jugadores Where idJugador = "+ jugador, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }

    public String getPuntuacionJugador(int jugador) {
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT puntuacion From Jugadores Where idJugador = "+ jugador, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }
}
