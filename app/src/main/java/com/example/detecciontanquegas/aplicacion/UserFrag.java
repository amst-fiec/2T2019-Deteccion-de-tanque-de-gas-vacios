package com.example.detecciontanquegas.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.detecciontanquegas.MainActivity;
import com.example.detecciontanquegas.R;
import com.google.firebase.auth.FirebaseUser;

public class UserFrag extends Fragment {

    FirebaseUser user;

    TextView username, name;

    Button logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user,container,false);
        username = (TextView) v.findViewById(R.id.username);
        name=(TextView) v.findViewById(R.id.name);
        //name.setText(user);
        username.setText(user.getEmail());
        logout = (Button) v.findViewById(R.id.logout_b);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        return v;
    }

    public UserFrag(FirebaseUser user){
        this.user=user;
    }
}

