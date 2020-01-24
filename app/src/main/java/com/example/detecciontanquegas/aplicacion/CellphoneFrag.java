package com.example.detecciontanquegas.aplicacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.detecciontanquegas.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CellphoneFrag extends Fragment implements OnMapReadyCallback {

    GoogleMap map;

    SupportMapFragment mapFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view= inflater.inflate(R.layout.fragment_cellphone,container,false);
        return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        /*MarkerOptions option = new MarkerOptions();
        option.position(ll).title("RESTAURANTE");
        map.moveCamera(CameraUpdateFactory.newLatLng(ll));*/
        LatLng ll= new LatLng(-2.145794 ,-79.967028);
        map.addMarker(new MarkerOptions().position(ll).title("RESTAURANTE LAS DELICIAS"));
        LatLng abestecedor1= new LatLng(-2.146073,-79.966804);
        map.addMarker(new MarkerOptions().position(abestecedor1).title("TIENDA 1").snippet("SERVICIO A DOMICILIO LLAMAR AL 0956425847").icon(BitmapDescriptorFactory.fromResource(R.drawable.gas_cylinder2)));
        LatLng abastecedor2= new LatLng( -2.146884,-79.967174);
        map.addMarker(new MarkerOptions().position(abastecedor2).title("TIENDA 2").snippet("SERVICIO A DOMICILIO LLAMAR AL 0956452533").icon(BitmapDescriptorFactory.fromResource(R.drawable.gas_cylinder2)));


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,50));
    }
}

