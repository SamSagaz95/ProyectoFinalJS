package com.example.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Noticia extends AppCompatActivity {
    TextView tvTitulo, tvCuerpo;
    ImageView ivFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        tvCuerpo = (TextView)findViewById(R.id.tvCuerpo);
        ivFoto = (ImageView)findViewById(R.id.ivFoto);

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            int idNoticia = Integer.parseInt(parametros.getString("idNoticia"));
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            List<String> titulos = databaseAccess.getTituloNoticiaByID(idNoticia);
            List<String> cuerpos = databaseAccess.getCuerpoNoticiaByID(idNoticia);
            List<String> fotos = databaseAccess.getFotoNoticiaByID(idNoticia);

            String foto = fotos.get(0);
            String img = foto.substring(0, foto.length()-4);
            int id1 = this.getResources().getIdentifier(img, "mipmap", getPackageName());
            ivFoto.setImageResource(id1);
            tvTitulo.setText(titulos.get(0));
            tvCuerpo.setText(cuerpos.get(0));
        }
    }
}
