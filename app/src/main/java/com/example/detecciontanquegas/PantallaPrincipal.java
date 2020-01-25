package com.example.detecciontanquegas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.detecciontanquegas.aplicacion.CellphoneFrag;
import com.example.detecciontanquegas.aplicacion.GasFrag;
import com.example.detecciontanquegas.aplicacion.StaticFrag;
import com.example.detecciontanquegas.aplicacion.UserFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaPrincipal extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navlistener);
        firebaseAuth = FirebaseAuth.getInstance();

        //UserFrag userFrag = new UserFrag(user);
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_container,userFrag,"");
        //fragmentTransaction.commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("10", "Nuevo estado", importance);
            channel.setDescription("Alertas de nuevo dato");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedfragmente = new UserFrag(user);// para que empiece en USER
                    switch (menuItem.getItemId()){
                        case R.id.nav_user:
                            selectedfragmente = new UserFrag(user);
                            break;
                        case R.id.nav_gas:
                            selectedfragmente = new GasFrag(user, getBaseContext());


                            break;
                        case R.id.nav_estadistic:
                            selectedfragmente = new StaticFrag();
                            break;
                        case R.id.nav_phone:
                            selectedfragmente = new CellphoneFrag();
                            break;
                    }
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.fragment_container,selectedfragmente).commit();
                    return true;
                }
            };
    private void checkUserStatus(){
        user= firebaseAuth.getCurrentUser();
        if (user!= null){
            // el usuario ha iniciado sesion
        }
        else{
            // no ha iniciado sesion
            startActivity(new Intent(PantallaPrincipal.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() { // chequea el inicio de sesion
        checkUserStatus();
        super.onStart();
    }
}