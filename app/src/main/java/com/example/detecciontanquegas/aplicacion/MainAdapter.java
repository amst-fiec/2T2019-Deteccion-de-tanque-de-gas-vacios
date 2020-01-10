package com.example.detecciontanquegas.aplicacion;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.detecciontanquegas.R;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private int imagengas,gasrojo;
    private ArrayList<Tanque>tanques;

    public MainAdapter(Context c, int imagengas,int gasrojo,ArrayList<Tanque>tanques){
        this.context = c;
        this.gasrojo = gasrojo;
        this.imagengas = imagengas;
        this.tanques = tanques;
    }


    @Override
    public int getCount() {
        return tanques.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_item,null);
        }
        ImageView imageView = convertView.findViewById(R.id.image_view);
        TextView textView = convertView.findViewById(R.id.text_view);
        String status = tanques.get(position).getEstado();
        int contador = tanques.get(position).getContador();
        if (contador>1){
            if (contador % 2!=0){
                imageView.setImageResource(imagengas);
            }
            if (contador % 2==0){
                imageView.setImageResource(gasrojo);
            }
        }
        else {
            imageView.setImageResource(gasrojo);
        }
        textView.setText(tanques.get(position).getId());
        return convertView;
    }
}