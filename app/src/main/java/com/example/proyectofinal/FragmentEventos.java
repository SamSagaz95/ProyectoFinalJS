package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FragmentEventos extends Fragment {

    View view;
    int competicion;
    String usuarioActual;
    public FragmentEventos() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eventos_fragment, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle parametros = getActivity().getIntent().getExtras();
        if (parametros != null) {
            competicion = Integer.parseInt(parametros.getString("competicion"));
            usuarioActual = parametros.getString("user");
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
            databaseAccess.open();

            List<String> jornadas = databaseAccess.getProximaJornada(competicion);
            int jornada = Integer.parseInt(jornadas.get(0));

            final List<String> idPartidos = databaseAccess.getIdEvento(jornada, competicion);

            List<String> locales = databaseAccess.getLocalEvento(jornada, competicion);
            List<String> visitantes = databaseAccess.getVisitanteEvento(jornada, competicion);
            List<String> fechas = databaseAccess.getFechaEvento(jornada, competicion);

            LinearLayout lpartidos = (LinearLayout)getView().findViewById(R.id.lpartidos);

           for (int i=0; i < idPartidos.size(); i++){

                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                List<String> imagenLocal = databaseAccess.getIcono(Integer.parseInt(locales.get(i)));
                ImageView img = new ImageView(getActivity());
                String iconName = imagenLocal.get(0);
                String icon = iconName.substring(0, iconName.length()-4);
                int id = this.getResources().getIdentifier(icon, "mipmap", getActivity().getPackageName());
                img.setImageResource(id);
                img.setLayoutParams(new LinearLayout.LayoutParams(300, 200));
                ll.addView(img);

               TextView txt = new TextView(getActivity());
               txt.setLayoutParams(new LinearLayout.LayoutParams(120, 200));
               txt.setTextSize(30);

               txt.setText(" VS ");
               ll.addView(txt);

                List<String> imagenVisitante = databaseAccess.getIcono(Integer.parseInt(visitantes.get(i)));
                ImageView img2 = new ImageView(getActivity());
                String iconName2 = imagenVisitante.get(0);
                String icon2 = iconName2.substring(0, iconName2.length()-4);
                int id2 = this.getResources().getIdentifier(icon2, "mipmap", getActivity().getPackageName());
                img2.setImageResource(id2);
                img2.setLayoutParams(new LinearLayout.LayoutParams(300, 200));
                ll.addView(img2);

               TextView txtFecha = new TextView(getActivity());
               txtFecha.setLayoutParams(new LinearLayout.LayoutParams(250, 200));
               txtFecha.setTextSize(18);

               txtFecha.setText(fechas.get(i));
               ll.addView(txtFecha);
               final int pos = i;
               ll.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(v.getContext(), Eventos.class);
                       intent.putExtra("idPartido", idPartidos.get(pos));
                       intent.putExtra("user", usuarioActual);
                       startActivity(intent);
                   }
               });
                lpartidos.addView(ll);
            }

            databaseAccess.close();
        }else{
            Toast.makeText(getActivity(), "Error al obtener id de la competición", Toast.LENGTH_SHORT).show();
        }
    }
}
