package com.example.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginUsuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuarios);
    }

    public void onClick(View view){
        Intent intent = new Intent(view.getContext(), RegistroUsuarios.class);
        startActivityForResult(intent, 0);
    }
}
