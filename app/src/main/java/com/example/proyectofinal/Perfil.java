package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.sleep;

public class Perfil extends AppCompatActivity {

    private String usuarioActual;
    private EditText etEmail, etNombre, etApellidos;
    private TextView tvPuntuacion;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private String uidUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = (EditText)findViewById(R.id.etEmail);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etApellidos = (EditText)findViewById(R.id.etApellidos);
        tvPuntuacion = (TextView)findViewById(R.id.tvPuntuacion);

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            usuarioActual = parametros.getString("actualUser");
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uidUser = user.getUid();
        updateUI(user);
    }

    private void updateUI(FirebaseUser user){
        if (user != null){

            String uid = user.getUid().toString();

            mDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("usuarios").child(uid);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot cs: dataSnapshot.getChildren()){
                        String uid = cs.getKey();
                        String name = cs.getValue().toString();
                        list.add(name);
                    }
                    etApellidos.setText(list.get(0));
                    etEmail.setText(list.get(1));
                    etNombre.setText(list.get(2));
                    tvPuntuacion.setText(list.get(3) +" puntos");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error al obtener datos de Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }
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

    public void btnModificar_clicked(View v){
        String uid = uidUser.toString();
        String email = etEmail.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellidos = etApellidos.getText().toString();
        String puntuacion = tvPuntuacion.getText().toString();
        String[] partes = puntuacion.split(" ");
        int puntos = Integer.parseInt(partes[0]);

        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("usuarios");
        Usuarios user = new Usuarios(uid, email, nombre, apellidos, puntos);
        Map<String, Object> userValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(uid, userValues);
        mDatabase.updateChildren(childUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al modificar datos del usuario", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void btnRanking_clicked(View v){
        Intent intent = new Intent(v.getContext(), Ranking_Usuarios.class);
        intent.putExtra("actualUser", usuarioActual.toString());
        startActivity(intent);
    }
}
