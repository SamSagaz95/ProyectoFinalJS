package com.example.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class ElegirCompeticion extends AppCompatActivity {

    ImageView imgJuego;
    List<String> competiciones, iconCompeticiones, idCompeticiones;
    LinearLayout lCompeticiones;
    String usuarioActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_competicion);

        imgJuego = (ImageView)findViewById(R.id.imgJuego);
        lCompeticiones = (LinearLayout)findViewById(R.id.lCompeticiones);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null){
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

            databaseAccess.open();

            String juego = parametros.getString("juego");
            usuarioActual = parametros.getString("user");
            switch (juego){
                case "lol":
                    imgJuego.setImageResource(R.mipmap.lolbanner);
                    competiciones = databaseAccess.getCompeticiones(1);
                    iconCompeticiones = databaseAccess.getIconCompeticiones(1);
                    idCompeticiones = databaseAccess.getIdCompeticiones(1);
                break;
                case "fifa":
                    imgJuego.setImageResource(R.mipmap.fifabanner);
                    competiciones = databaseAccess.getCompeticiones(2);
                    iconCompeticiones = databaseAccess.getIconCompeticiones(2);
                    idCompeticiones = databaseAccess.getIdCompeticiones(2);
                break;
                case "cr":
                    imgJuego.setImageResource(R.mipmap.crbanner);
                    competiciones = databaseAccess.getCompeticiones(3);
                    iconCompeticiones = databaseAccess.getIconCompeticiones(3);
                    idCompeticiones = databaseAccess.getIdCompeticiones(3);
                break;
                case "csgo":
                    imgJuego.setImageResource(R.mipmap.csgobanner);
                    competiciones = databaseAccess.getCompeticiones(4);
                    iconCompeticiones = databaseAccess.getIconCompeticiones(4);
                    idCompeticiones = databaseAccess.getIdCompeticiones(4);
                break;
            }
            databaseAccess.close();

            for (int i=0; i < competiciones.size(); i++){
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                ImageView img = new ImageView(this);
                String iconName = iconCompeticiones.get(i);
                String icon = iconName.substring(0, iconName.length()-4);
                int id = this.getResources().getIdentifier(icon, "mipmap", getPackageName());
                img.setImageResource(id);
                img.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                ll.addView(img);

                Button btn = new Button(this);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
                btn.setText(competiciones.get(i).toString());

                final int pos = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), Competicion.class);
                        intent.putExtra("competicion", idCompeticiones.get(pos));
                        intent.putExtra("user", usuarioActual);
                        startActivity(intent);
                    }
                });
                ll.addView(btn);
                lCompeticiones.addView(ll);

            }
        }
    }
}
