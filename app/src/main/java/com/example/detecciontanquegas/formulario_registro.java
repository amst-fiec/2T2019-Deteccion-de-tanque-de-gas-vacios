package com.example.detecciontanquegas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText editTextNAME, editTextEMAIL, editTextUSER, editTextPASSWORD;
    //variables de los datos a registrar
    private String name="";
    private String email="";
    private String user="";
    private String password="";
    //variable para registro de usuario
    FirebaseAuth nAuth;
    DatabaseReference nDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);



        nAuth=FirebaseAuth.getInstance();
        nDatabase= FirebaseDatabase.getInstance().getReference();
        editTextNAME=(EditText)findViewById(R.id.editNombre);
        editTextEMAIL=(EditText)findViewById(R.id.editEmail);
        editTextUSER=(EditText)findViewById(R.id.editUsuario);
        editTextPASSWORD=(EditText)findViewById(R.id.editClave);
        Button buttonRegistrar = (Button) findViewById(R.id.btnGrabaregistro);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editTextNAME.getText().toString();
                email=editTextEMAIL.getText().toString();
                user=editTextUSER.getText().toString();
                password=editTextPASSWORD.getText().toString();

                if(!name.isEmpty()&&!email.isEmpty()&&!user.isEmpty()&&!password.isEmpty()){
                    //Firebase al menos 6 caracteres
                    if(password.length()>=6){
                        registrarUsuario();

                    }else{
                        Toast.makeText(formulario_registro.this,"Password al menos 6 caracteres ",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(formulario_registro.this,"Complete los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void registrarUsuario(){
        nAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String,Object> map=new HashMap<>();
                    map.put("name",name );
                    map.put("email",email);
                    map.put("user",user);
                    map.put("password",password);
                    String id= Objects.requireNonNull(nAuth.getCurrentUser()).getUid();
                    nDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(formulario_registro.this,RegistroExitoso.class));
                                finish();

                           }else{
                                Toast.makeText(formulario_registro.this,"No se pudo registrar datos correctamente",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else{
                    Toast.makeText(formulario_registro.this,"No se pudo registrar usuario",Toast.LENGTH_SHORT).show();

                }
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

