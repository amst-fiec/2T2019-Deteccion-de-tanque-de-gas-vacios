package com.example.detecciontanquegas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.detecciontanquegas.aplicacion.CellphoneFrag;
import com.example.detecciontanquegas.aplicacion.GasFrag;
import com.example.detecciontanquegas.aplicacion.StaticFrag;
import com.example.detecciontanquegas.aplicacion.UserFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navlistener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedfragmente = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_user:
                            selectedfragmente = new UserFrag();
                            break;
                        case R.id.nav_gas:
                            selectedfragmente = new GasFrag();
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
}
