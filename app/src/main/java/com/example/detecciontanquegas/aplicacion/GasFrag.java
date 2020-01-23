package com.example.detecciontanquegas.aplicacion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.content.Context;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.detecciontanquegas.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GasFrag extends Fragment {
    FirebaseUser user;
    DatabaseReference db_reference;
    GridView gridView;
    Context context;
    ArrayList<Tanque> tanques;
    String [] numberWord= {"One","Two","Three","For","Five","six","seven","eight","nine","ten","eleven","twelve"};
    int imagengas = R.drawable.gaspic;
    int gasrojo = R.drawable.gasrojo;
    NotificationManagerCompat notificationManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_gas,container,false);
        gridView = (GridView) v.findViewById(R.id.grid_view);
        db_reference = FirebaseDatabase.getInstance().getReference();

        getuserMap();
        notificationManager = NotificationManagerCompat.from(context);
        return v;
    }
    public GasFrag(FirebaseUser user, Context context){
        this.user=user;
        this.context=context;
    }

    public void getuserMap(){
        db_reference.child("Tanques").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tanques = new ArrayList<>();
                for (DataSnapshot dataSnap :dataSnapshot.getChildren()) {
                    Tanque valortanque = dataSnap.getValue(Tanque.class);
                    int contador = 0;
                    for(DataSnapshot dataSnapshot1: dataSnap.child("registros").getChildren()){
                        contador++;
                    }
                    valortanque.setContador(contador);
                    valortanque.setId(dataSnap.getKey());
                    tanques.add(valortanque);
                    String mensaje = "Tanque: "+ valortanque.getId() + " Estado: ";
                    if (contador>1){
                        if (contador % 2!=0){
                            mensaje += "lleno";
                        }
                        if (contador % 2==0){
                            mensaje += "vacio";
                        }
                    } else {
                        mensaje += "vacio";
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "10")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Aviso nuevo.")
                            .setAutoCancel(false)    //swipe for delete
                            .setContentText(mensaje);
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    notificationManager.notify(Integer.valueOf(valortanque.getId()), builder.build());
                }

                MainAdapter adapter = new MainAdapter(getActivity(),imagengas,gasrojo,tanques);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "you click"+numberWord[+position],
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

