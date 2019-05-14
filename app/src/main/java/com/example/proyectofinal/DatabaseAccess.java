package com.example.proyectofinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String icon = "";
        Cursor cursor = database.rawQuery("SELECT nombre From Equipos Where idEquipo = "+ equipo , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            icon =cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return icon;
    }
}
