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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class formulario_registro extends AppCompatActivity {

     EditText editTextNAME, editTextEMAIL, editTextUSER, editTextPASSWORD;
    //variables de los datos a registrar

    Button buttonRegistrar;
    //variable para registro de usuario
    //ProgressBar progressBar;
    FirebaseAuth nAuth;
    DatabaseReference nDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);


        nAuth = FirebaseAuth.getInstance();
        //progressBar = findViewById(R.id.progressBar);

        if (nAuth.getCurrentUser() != null) {
            startActivity(new Intent(formulario_registro.this, RegistroExitoso.class));
            finish();

        }
        nDatabase = FirebaseDatabase.getInstance().getReference();
        editTextNAME = (EditText) findViewById(R.id.editNombre);
        editTextEMAIL = (EditText) findViewById(R.id.editEmail);
        editTextUSER = (EditText) findViewById(R.id.editUsuario);
        editTextPASSWORD = (EditText) findViewById(R.id.editClave);
        buttonRegistrar = (Button) findViewById(R.id.btnGrabaregistro);


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEMAIL.getText().toString();
                String password=editTextPASSWORD.getText().toString();

                if(TextUtils.isEmpty(email)){
                    editTextEMAIL.setError("SE REQUIERE CORREO ELECTRONICO");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    editTextPASSWORD.setError("SE REQUIERE SU CONTRASEÑA");
                    return;
                }
                if(password.length()<6){
                    editTextPASSWORD.setError("Se necesita contraseña >= 6 caracteres");
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);

                //INICIO DE SESION CON FIREBASE
                nAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(formulario_registro.this,"INGRESO DE USUARIO EXITOSO",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(formulario_registro.this,RegistroExitoso.class));

                        }else{
                            Toast.makeText(formulario_registro.this,"ERROR !"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });



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

