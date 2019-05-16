package com.example.proyectofinal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RosterEquipo extends AppCompatActivity {

    ImageView imgEquipo, imgPaisEquipo;
    TextView tvEquipo;
    LinearLayout lmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_equipo);

        imgEquipo = (ImageView)findViewById(R.id.imgEquipo);
        imgPaisEquipo = (ImageView)findViewById(R.id.imgPaisEquipo);
        tvEquipo = (TextView)findViewById(R.id.tvEquipo);
        lmain = (LinearLayout)findViewById(R.id.lmain);

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            final int idEquipo = Integer.parseInt(parametros.getString("idEquipo"));
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

            List<String> jugadores = databaseAccess.getJugadores(idEquipo);
            List<String> iconJugadores = databaseAccess.getIconJugadores(idEquipo);
            final List<String> idJugadores = databaseAccess.getIdJugadores(idEquipo);

            for (int i=0; i < jugadores.size(); i++){
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                ImageView img = new ImageView(this);
                String iconName = iconJugadores.get(i);
                String icon = iconName.substring(0, iconName.length()-4);
                int id = getResources().getIdentifier(icon, "mipmap", getPackageName());
                img.setImageResource(id);
                img.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                img.setBackgroundColor(Color.rgb(255, 255, 255));
                ll.addView(img);

                Button btn = new Button(this);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
                btn.setText(jugadores.get(i).toString());

                final int pos = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), Jugador.class);
                        intent.putExtra("idJugador", idJugadores.get(pos));
                        startActivity(intent);
                    }
                });
                ll.addView(btn);
                lmain.addView(ll);
            }
            databaseAccess.close();
        }
    }
}
