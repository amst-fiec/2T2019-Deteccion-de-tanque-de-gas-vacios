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

public class GasFrag extends Fragment {
    GridView gridView;
    String [] numberWord= {"One","Two","Three","For","Five","six","seven","eight","nine","ten","eleven","twelve"};
    int imagengas = R.drawable.gaspic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_gas,container,false);
        gridView = (GridView) v.findViewById(R.id.grid_view);
        MainAdapter adapter = new MainAdapter(getActivity(),numberWord,imagengas);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "you click"+numberWord[+position],
                        Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}

