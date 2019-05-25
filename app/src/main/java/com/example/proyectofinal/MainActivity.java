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

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //private ListView listView;
    private Button btnLol;
    private Button btnClash;
    private Button btnCsgo;
    private Button btnFifa;
    private TextView txtAcercaDe;
    private FirebaseAuth firebaseAuth;

    private String usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            usuarioActual = parametros.getString("actualUser");
        }

        firebaseAuth = FirebaseAuth.getInstance();

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
        intent.putExtra("user", usuarioActual);
        startActivity(intent);
    }

    public void btnCr_clicked(View v){
        Intent intent = new Intent(v.getContext(), ElegirCompeticion.class);
        intent.putExtra("juego", "cr");
        intent.putExtra("user", usuarioActual);
        startActivity(intent);
    }

    public void btnCsgo_clicked(View v){
        Intent intent = new Intent(v.getContext(), ElegirCompeticion.class);
        intent.putExtra("juego", "csgo");
        intent.putExtra("user", usuarioActual);
        startActivity(intent);
    }

    public void btnLol_clicked(View v){
        Intent intent = new Intent(v.getContext(), ElegirCompeticion.class);
        intent.putExtra("juego", "lol");
        intent.putExtra("user", usuarioActual);
        startActivity(intent);
    }

    public void btnSignOut_clicked(View v){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(), "Se ha cerrado la sesión de: "+ usuarioActual, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(v.getContext(), LoginUsuarios.class);
        startActivity(intent);
    }

    public void btnPerfil_clicked(View v){
        Intent intent = new Intent(v.getContext(), Perfil.class);
        intent.putExtra("actualUser", usuarioActual.toString());
        startActivity(intent);
    }
    public void btnCreative_clicked(View v) {
        Intent intent = new Intent(v.getContext(), Licencia.class);
        startActivity(intent);
    }

}