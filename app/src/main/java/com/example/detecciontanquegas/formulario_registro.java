package com.example.detecciontanquegas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class formulario_registro extends AppCompatActivity {

     EditText editTextNAME, editTextEMAIL, editTextUSER, editTextPASSWORD;
    //variables de los datos a registrar
    String email,nombre,password;
    Button buttonRegistrar;
    //variable para registro de usuario
    //ProgressBar progressBar;
    FirebaseAuth nAuth;
    FirebaseDatabase nDatabase;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);


        nAuth = FirebaseAuth.getInstance();
        //progressBar = findViewById(R.id.progressBar);


        nDatabase = FirebaseDatabase.getInstance();
        myref = nDatabase.getReference();

        editTextNAME = (EditText) findViewById(R.id.editNombre);
        editTextEMAIL = (EditText) findViewById(R.id.editEmail);

        editTextPASSWORD = (EditText) findViewById(R.id.editClave);
        buttonRegistrar = (Button) findViewById(R.id.btnGrabaregistro);


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEMAIL.getText().toString();
                password=editTextPASSWORD.getText().toString();
                nombre = editTextNAME.getText().toString();
                if(TextUtils.isEmpty(email)){
                    editTextEMAIL.setError("SE REQUIERE CORREO ELECTRONICO");
                    editTextEMAIL.setFocusable(true);
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEMAIL.setError("Email Invalido");
                    editTextEMAIL.setFocusable(true);
                }
                else if(TextUtils.isEmpty(password)){
                    editTextPASSWORD.setError("SE REQUIERE SU CONTRASEÑA");
                    editTextPASSWORD.setFocusable(true);
                }
                else if(password.length()<6){
                    editTextPASSWORD.setError("Se necesita contraseña >= 6 caracteres");
                    editTextPASSWORD.setFocusable(true);
                }
                //progressBar.setVisibility(View.VISIBLE);

                //INICIO DE SESION CON FIREBASE
                else{
                    registerUser();

                }
            }
        });



    }
    private void registerUser(){
        nAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Map<String,Object> map=new HashMap<>();
                            map.put("name",nombre );
                            map.put("email",email);
                            map.put("password",password);
                            String id= Objects.requireNonNull(nAuth.getCurrentUser()).getUid();
                            myref.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (!task2.isSuccessful()){
                                        Toast.makeText(formulario_registro.this,"ERROR !"+ task2.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                            FirebaseUser user = nAuth.getCurrentUser();
                            Toast.makeText(formulario_registro.this, "Registro exitoso",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(formulario_registro.this,PantallaPrincipal.class));

                            finish();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(formulario_registro.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(formulario_registro.this, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}

   /* //insertarpaciente
    public void insertarpaciente(View v) {
        addNotification();
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.error)
                        .setContentTitle("Se ha registrado con exito")
                        .setContentText("Revise su correo electronico en los proximos 2 dias")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent notificationIntent = new Intent(this, formulario_registro.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("message", "This is a notification message");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}*/

