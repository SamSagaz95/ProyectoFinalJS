package com.example.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Jugador extends AppCompatActivity {

    TextView tvNombre, tvEdad, tvPosicion, tvPuntuacion, textView8;
    ImageView ivJugador, ivEquipo, ivPais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        tvNombre = (TextView)findViewById(R.id.tvNombre);
        tvEdad = (TextView)findViewById(R.id.tvEdad);
        tvPosicion = (TextView)findViewById(R.id.tvPosicion);
        tvPuntuacion = (TextView)findViewById(R.id.tvPuntuacion);
        textView8 = (TextView)findViewById(R.id.textView8);

        ivJugador = (ImageView)findViewById(R.id.ivJugador);
        ivEquipo = (ImageView)findViewById(R.id.ivEquipo);
        ivPais = (ImageView)findViewById(R.id.ivPais);

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            int idJugador = Integer.parseInt(parametros.getString("idJugador"));
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();

            int idEquipo = Integer.parseInt(databaseAccess.getIdEquipoJugador(idJugador));

            String getIconEquipo = databaseAccess.getIconEquipo(idEquipo);
            String iconEquipo = getIconEquipo.substring(0, getIconEquipo.length()-4);
            int id1 = this.getResources().getIdentifier(iconEquipo, "mipmap", getPackageName());
            ivEquipo.setImageResource(id1);

            String getIconPaisJugador = databaseAccess.getIconPaisJugador(idJugador);
            String iconPais = getIconPaisJugador.substring(0, getIconPaisJugador.length()-4);
            int id2 = this.getResources().getIdentifier(iconPais, "mipmap", getPackageName());
            ivPais.setImageResource(id2);

            String getIconJugador = databaseAccess.getIconJugador(idJugador);
            String iconJugador = getIconJugador.substring(0, getIconJugador.length()-4);
            int id3 = this.getResources().getIdentifier(iconJugador, "mipmap", getPackageName());
            ivJugador.setImageResource(id3);

            String nombreJugador = databaseAccess.getNombreJugador(idJugador);
            tvNombre.setText(nombreJugador.toString());

            String edadJugador = databaseAccess.getEdadJugador(idJugador);
            tvEdad.setText(edadJugador.toString());

            String posicionJugador = databaseAccess.getPosicionJugador(idJugador);
            tvPosicion.setText(posicionJugador.toString());

            if (posicionJugador.equals("Coach")) {
                textView8.setVisibility(View.INVISIBLE);
                tvPuntuacion.setVisibility(View.INVISIBLE);
            }
            else{
                String puntuacionJugador = databaseAccess.getPuntuacionJugador(idJugador);
                tvPuntuacion.setText(puntuacionJugador.toString());
            }
        }
    }
}
