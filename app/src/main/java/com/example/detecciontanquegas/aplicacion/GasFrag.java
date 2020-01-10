package com.example.detecciontanquegas.aplicacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    ArrayList<Tanque> tanques;
    String [] numberWord= {"One","Two","Three","For","Five","six","seven","eight","nine","ten","eleven","twelve"};
    int imagengas = R.drawable.gaspic;
    int gasrojo = R.drawable.gasrojo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_gas,container,false);
        gridView = (GridView) v.findViewById(R.id.grid_view);
        db_reference = FirebaseDatabase.getInstance().getReference();

        getuserMap();



        return v;
    }
    public GasFrag(FirebaseUser user){
        this.user=user;
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

