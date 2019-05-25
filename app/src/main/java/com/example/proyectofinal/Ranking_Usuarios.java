package com.example.proyectofinal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ranking_Usuarios extends AppCompatActivity {
    View view;
    private String usuarioActual;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private String uidUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking__usuarios);

        firebaseAuth = FirebaseAuth.getInstance();

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            usuarioActual = parametros.getString("actualUser");
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uidUser = user.getUid();
        updateUI(user);


    }




    private void updateUI(FirebaseUser user) {
        if (user != null) {

            String uid = user.getUid().toString();


            mDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("usuarios").child(uid);


            mDatabase.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> list = new ArrayList<>();
                    int i=0;
                    for (DataSnapshot cs : dataSnapshot.getChildren()) {

                        String uid = cs.getKey();
                        String name = cs.getValue().toString();
                        list.add(name);

                        LinearLayout lusuarios = (LinearLayout) findViewById(R.id.lusuarios);
                        LinearLayout ll = new LinearLayout(getApplicationContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        TextView txtPosicion = new TextView(getApplicationContext());
                        txtPosicion.setLayoutParams(new LinearLayout.LayoutParams(120, 200));
                        txtPosicion.setTextSize(25);

                        txtPosicion.setText(list.get(i));
                        ll.addView(txtPosicion);


                        lusuarios.addView(ll);
                        i++;
                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error al obtener datos de Firebase", Toast.LENGTH_SHORT).show();
                }


            });
        }
    }
}
