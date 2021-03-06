package com.example.proyectofinal;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class FragmentNoticias extends Fragment {

    View view;

    public FragmentNoticias() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.noticias_fragment, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle parametros = getActivity().getIntent().getExtras();
        if (parametros != null) {
            int competicion = Integer.parseInt(parametros.getString("competicion"));
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
            databaseAccess.open();
            final List<String> idNoticias = databaseAccess.getIdNoticia(competicion);

            List<String> titulos = databaseAccess.getTituloNoticia(competicion);
            List<String> cuerpos = databaseAccess.getCuerpoNoticia(competicion);
            List<String> fotos = databaseAccess.getFotoNoticia(competicion);

            LinearLayout lnoticias = (LinearLayout)getView().findViewById(R.id.lnoticias);

            for (int i=0; i < idNoticias.size(); i++){
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                ImageView img = new ImageView(getActivity());
                String iconName = fotos.get(i);
                String icon = iconName.substring(0, iconName.length()-4);
                int id = this.getResources().getIdentifier(icon, "mipmap", getActivity().getPackageName());
                img.setImageResource(id);
                img.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                ll.addView(img);

                String cuerpo = cuerpos.get(i).substring(0,59);
                TextView txtNoticia = new TextView(getActivity());
                txtNoticia.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
                txtNoticia.setTextSize(18);

                txtNoticia.setText(Html.fromHtml("<b>"+titulos.get(i)+"</b><br>"+cuerpo+"<b>...Seguir Leyendo</b>"));
                ll.addView(txtNoticia);


                final int pos = i;
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), Noticia.class);
                        intent.putExtra("idNoticia", idNoticias.get(pos));
                        startActivity(intent);
                    }
                });
                lnoticias.addView(ll);
            }
        }
    }
}
