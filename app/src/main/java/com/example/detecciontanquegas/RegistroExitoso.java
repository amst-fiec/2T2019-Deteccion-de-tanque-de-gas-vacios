package com.example.detecciontanquegas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroExitoso extends AppCompatActivity {
    private Button button, btnDatos;
    //cerrar sesion en Firebase
    private FirebaseAuth nAuth;
    private DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_exitoso);
        //instanciando nAuth
        nAuth=FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        btnDatos=(Button)findViewById(R.id.btnEstadoTanque);
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroExitoso.this, PantallaPrincipal.class));

            }
        });
        button=(Button)findViewById(R.id.btnCerrarSesion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nAuth.signOut();
                startActivity(new Intent(RegistroExitoso.this,MainActivity.class));
                finish();

            }
        });
    }
}