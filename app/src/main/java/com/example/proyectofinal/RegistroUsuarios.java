package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroUsuarios extends AppCompatActivity {

    private EditText etEmail;
    private EditText etNombre;
    private EditText etApellidos;
    private EditText etPassword;
    private Button btnRegistro;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = (EditText)findViewById(R.id.etEmail);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etApellidos = (EditText)findViewById(R.id.etApellidos);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnRegistro = (Button)findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
    }

    private void registroUsuario(View v){
        final String email = etEmail.getText().toString().trim();
        final String nombre = etNombre.getText().toString().trim();
        final String apellidos = etApellidos.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final Context context = v.getContext();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un E-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Se debe ingresar un Nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(apellidos)){
            Toast.makeText(this, "Se debe ingresar los Apellidos", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Se debe ingresar una Contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registrando usuario...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegistroUsuarios.this, "Se ha registrado correctamente!", Toast.LENGTH_SHORT).show();
                            String uid = task.getResult().getUser().getUid().toString();
                            newUser(uid, email.toString(), nombre.toString(), apellidos.toString(), 0);
                        }
                        else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegistroUsuarios.this, "El usuario ya existe en la Base de Datos!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegistroUsuarios.this, "No se pudo registrar al usuario!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, LoginUsuarios.class);
                        startActivityForResult(intent, 0);
                    }
                });
    }

    public void onClick(View view){
        registroUsuario(view);
    }

    private void newUser(String userId, String email, String nombre, String apellidos, Integer puntuacion){
        Usuarios user = new Usuarios(userId, email, nombre, apellidos, puntuacion);
        mDatabase.child("usuarios").child(userId).setValue(user);
    }
}
