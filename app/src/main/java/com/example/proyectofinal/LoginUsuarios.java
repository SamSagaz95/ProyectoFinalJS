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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginUsuarios extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView btnRegistrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuarios);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegistrar = (TextView)findViewById(R.id.txtRegistro);
        progressDialog = new ProgressDialog(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.txtRegistro:
                Intent intent = new Intent(v.getContext(), RegistroUsuarios.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.btnLogin:
                loginUsuario(v);
                break;
        }
    }

    private void loginUsuario(View v){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        final Context context = v.getContext();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe introducir un correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Se debe introducir la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginUsuarios.this, "Bienvenido seas: "+ etEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginUsuarios.this, "Error en el correo o la contraseña introducida!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivityForResult(intent, 0);
                        }
                    }
                });
    }
}
