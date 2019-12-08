package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class PantallaDatos extends AppCompatActivity {

    DatabaseReference db_reference;
    int cantidad;
    TextView value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_datos);
        value= (TextView) findViewById(R.id.valor);
        db_reference = FirebaseDatabase.getInstance().getReference().child("Registros");
        leerRegistros();


    }
    private void leerRegistros() {
        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                // mostrarRegistrosPorPantalla(snapshot); }
                mostrarRegistrosPorPantalla();
                cantidad+=1;
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.toException());
            }
        });

    }
    public void mostrarRegistrosPorPantalla(){
        LinearLayout contTemp = (LinearLayout) findViewById(R.id.ContenedorTemp);
        LinearLayout contAxis = (LinearLayout) findViewById(R.id.ContenedorAxis);
        //String tempVal = String.valueOf(snapshot.child("flag").getValue());
        TextView temp = new TextView(getApplicationContext());
        if (cantidad ==0){
            value.setText("vacio  " + cantidad);
        }
        else if(cantidad % 2 ==0){ //para cuadno el contador este impart
            value.setText("lleno  " + cantidad);
        }
        else if (cantidad % 2 !=0){ // para cuando el contador este par
            value.setText("vacio  " + cantidad);
        }
        contTemp.addView(temp);
        TextView axis = new TextView(getApplicationContext());
        contAxis.addView(axis);
    }


}
