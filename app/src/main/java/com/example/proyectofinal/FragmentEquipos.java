package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FragmentEquipos extends Fragment {
    View view;
    int competicion;

    public FragmentEquipos() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.equipos_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle parametros = getActivity().getIntent().getExtras();
        if (parametros != null){
            int competicion = Integer.parseInt(parametros.getString("competicion"));
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
            databaseAccess.open();
            List<String> equipos = databaseAccess.getEquiposOrdenados(competicion);
            final List<String> idEquipos = databaseAccess.getIdEquiposOrdenados(competicion);
            List<String> iconEquipos = databaseAccess.getIconEquiposOrdenados(competicion);
            List<String> puntosEquipos = databaseAccess.getPuntosEquiposOrdenados(competicion);

            LinearLayout lequipos = (LinearLayout)getView().findViewById(R.id.lequipos);

            for (int i=0; i < equipos.size(); i++){
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView txtPosicion = new TextView(getActivity());
                txtPosicion.setLayoutParams(new LinearLayout.LayoutParams(120, 200));
                txtPosicion.setTextSize(25);
                int posicion=i+1;
                txtPosicion.setText(posicion+"º");
                ll.addView(txtPosicion);

                ImageView img = new ImageView(getActivity());
                String iconName = iconEquipos.get(i);
                String icon = iconName.substring(0, iconName.length()-4);
                int id = this.getResources().getIdentifier(icon, "mipmap", getActivity().getPackageName());
                img.setImageResource(id);
                img.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                ll.addView(img);

                Button btn = new Button(getActivity());
                btn.setLayoutParams(new LinearLayout.LayoutParams(600, 200));
                btn.setText(equipos.get(i).toString());

                final int pos = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), RosterEquipo.class);
                        intent.putExtra("idEquipo", idEquipos.get(pos));
                        startActivity(intent);
                    }
                });
                ll.addView(btn);

                TextView txtPuntos = new TextView(getActivity());
                txtPuntos.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                txtPuntos.setTextSize(20);

                txtPuntos.setText(puntosEquipos.get(i)+" pts");
                ll.addView(txtPuntos);

                lequipos.addView(ll);
            }
            databaseAccess.close();
        } else{
            Toast.makeText(getActivity(), "Error al obtener id de la competición", Toast.LENGTH_SHORT).show();
        }
    }
}
