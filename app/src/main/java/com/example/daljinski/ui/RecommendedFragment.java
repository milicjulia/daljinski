package com.example.daljinski.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daljinski.MainActivity;
import com.example.daljinski.R;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RecommendedFragment extends Fragment {
    private TextView naziv1, naziv2, naziv3, naziv4;
    private TextView opis1, opis2, opis3, opis4;
    private TextView vreme1, vreme2, vreme3, vreme4;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_recommended, container, false);
        dodajKomponente(view);
        return view;
    }

    public void dodajKomponente(View v){
        naziv1=view.findViewById(R.id.naziv1);
        naziv2=view.findViewById(R.id.naziv2);
        naziv3=view.findViewById(R.id.naziv3);
        naziv4=view.findViewById(R.id.naziv4);
        vreme1=view.findViewById(R.id.vreme1);
        vreme2=view.findViewById(R.id.vreme2);
        vreme3=view.findViewById(R.id.vreme3);
        vreme4=view.findViewById(R.id.vreme4);
        opis1=view.findViewById(R.id.opis1);
        opis2=view.findViewById(R.id.opis2);
        opis3=view.findViewById(R.id.opis3);
        opis4=view.findViewById(R.id.opis4);
        Program p=MainActivity.getChannels().get(0).getPrograms().get(0);
        naziv1.setText(p.getName());
        naziv2.setText(p.getName());
        naziv3.setText(p.getName());
        naziv4.setText(p.getName());
        opis1.setText(p.getDescription());
        opis2.setText(p.getDescription());
        opis3.setText(p.getDescription());
        opis4.setText(p.getDescription());
        Timestamp time = new Timestamp(p.getStartDate());
        vreme1.setText(time.getHours() + ":" + time.getMinutes());
        vreme2.setText(time.getHours() + ":" + time.getMinutes());
        vreme3.setText(time.getHours() + ":" + time.getMinutes());
        vreme4.setText(time.getHours() + ":" + time.getMinutes());

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("RecommenedFragment","start");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("RecommenedFragment","resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("RecommenedFragment","pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("RecommenedFragment","stop");
    }

}