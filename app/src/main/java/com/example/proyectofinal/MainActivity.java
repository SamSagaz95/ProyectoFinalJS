package com.example.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //private ListView listView;
    private Button btnLol;
    private Button btnClash;
    private Button btnCsgo;
    private Button btnFifa;
    private TextView txtAcercaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLol = (Button)findViewById(R.id.btnLol);
        btnClash = (Button)findViewById(R.id.btnClash);
        btnCsgo = (Button)findViewById(R.id.btnCsgo);
        btnFifa = (Button)findViewById(R.id.btnFifa);
        txtAcercaDe = (TextView)findViewById(R.id.txtAcercaDe);
    }

    public void btnAcercaDe_clicked(View v){
        Toast.makeText(getApplicationContext(), "Proyecto desarrollado por José Ramón Atienzar y Samuel Alameda", Toast.LENGTH_SHORT).show();
    }

    public void btnFifa_clicked(View v){
        Intent intent = new Intent(v.getContext(), ElegirCompeticion.class);
        intent.putExtra("juego", "fifa");
        startActivity(intent);
    }

    public void btnCr_clicked(View v){
        Intent intent = new Intent(v.getContext(), ElegirCompeticion.class);
        intent.putExtra("juego", "cr");
        startActivity(intent);
    }

    public void btnCsgo_clicked(View v){
        Intent intent = new Intent(v.getContext(), ElegirCompeticion.class);
        intent.putExtra("juego", "csgo");
        startActivity(intent);
    }

    public void btnLol_clicked(View v){
        Intent intent = new Intent(v.getContext(), ElegirCompeticion.class);
        intent.putExtra("juego", "lol");
        startActivity(intent);
    }
}