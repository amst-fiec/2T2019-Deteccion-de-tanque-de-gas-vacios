package com.example.detecciontanquegas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class RegistroExitoso extends AppCompatActivity {
    private Button button, btnDatos;
    //cerrar sesion en Firebase
    private FirebaseAuth nAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_exitoso);
    //instanciando nAuth
        nAuth=FirebaseAuth.getInstance();
        btnDatos=(Button)findViewById(R.id.btnEstadoTanque);
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroExitoso.this, PantallaDatos.class));

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
