package com.example.detecciontanquegas;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
     EditText txtUsuario, txtPasswd;
     Button btnLogin, btnRegistro;
     ProgressBar progressBar;
    //datos para iniciar sesion
    String email;
    String password;
    //obtener informacion del usuario
    FirebaseAuth nAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Referencias a los controles

        nAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(nAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,RegistroExitoso.class));
            finish();

        }
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPasswd = (EditText) findViewById(R.id.txtPasswd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, formulario_registro.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtUsuario.getText().toString();
                String password=txtPasswd.getText().toString();

                if(TextUtils.isEmpty(email)){
                    txtUsuario.setError("SE REQUIERE CORREO ELECTRONICO");
                }
                if(TextUtils.isEmpty(password)){
                    txtPasswd.setError("SE REQUIERE SU CONTRASEÑA");
                    return;
                }
                if(password.length()<6){
                    txtPasswd.setError("Se necesita contraseña >= 6 caracteres");
                }
                progressBar.setVisibility(View.VISIBLE);

                //INICIO DE SESION CON FIREBASE
                nAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"INGRESO DE USUARIO EXITOSO",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,RegistroExitoso.class));

                        }else{
                            Toast.makeText(MainActivity.this,"ERROR !"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
}
/*
    public void login(View view){
        String username= txtUsuario.getText().toString();
        String password=txtPasswd.getText().toString();

        nAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Hubo un error",Toast.LENGTH_LONG).show();

                }else{

                }
            }
        });
    }*/



    /* public void registrarse(View view) {
        Intent intent = new Intent(this, formulario_registro.class);
        startActivity(intent);
    }
    /*public void login(View view) {
        Toast toast=Toast.makeText(getApplicationContext(),"Usted no cuenta con un usuario",Toast.LENGTH_SHORT);
        toast.show();
    }*/



   /* public void onClick(View v) {
        if(v.getId() == R.id.btnLogin){
            Log.d("mensaje","ïngreso");
        }else if(v.getId() == R.id.btnRegistro) {
        }
    }*/
