package com.example.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RosterEquipo extends AppCompatActivity {

    ImageView imgEquipo, imgPaisEquipo;
    TextView tvEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_equipo);

        imgEquipo = (ImageView)findViewById(R.id.imgEquipo);
        imgPaisEquipo = (ImageView)findViewById(R.id.imgPaisEquipo);
        tvEquipo = (TextView)findViewById(R.id.tvEquipo);

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            int idEquipo = Integer.parseInt(parametros.getString("idEquipo"));
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();

            String getIconEquipo = databaseAccess.getIconEquipo(idEquipo);
            String iconEquipo = getIconEquipo.substring(0, getIconEquipo.length()-4);
            int id1 = this.getResources().getIdentifier(iconEquipo, "mipmap", getPackageName());
            imgEquipo.setImageResource(id1);

            String getIconPaisEquipo = databaseAccess.getIconPaisEquipo(idEquipo);
            String iconPais = getIconPaisEquipo.substring(0, getIconPaisEquipo.length()-4);
            int id2 = this.getResources().getIdentifier(iconPais, "mipmap", getPackageName());
            imgPaisEquipo.setImageResource(id2);

            String nameEquipo = databaseAccess.getNameEquipo(idEquipo);
            tvEquipo.setText(nameEquipo.toString());
        }
    }
}
