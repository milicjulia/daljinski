package com.example.daljinski.ui;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.daljinski.MainActivity;
import com.example.daljinski.R;
import java.sql.Timestamp;
import com.google.android.material.button.MaterialButton;

public class ProgramFragment extends Fragment {
    private LinearLayout sv, ly;
    private MaterialButton gledaj;
    private Button o;
    FrameLayout.LayoutParams paramsSlike,paramsTxt;
    private static int id = 0;
    private int myid = id++;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_program, container, false);
        sv = (LinearLayout) view.findViewById(R.id.programlayout);
        paramsSlike=new FrameLayout.LayoutParams(90, 90, Gravity.RIGHT);
        paramsTxt = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        int dosad = 0;
        int brkanala = MainActivity.getChannels().size();
        for (int j = 0; j < brkanala; j++) {
            int brprograma = MainActivity.getChannels().get(j).getPrograms().size();
            for (int k = 0; k < brprograma; k++) {
                if (myid == dosad) {
                    dodajNaziv(j, k);
                    dodajVreme(j, k);
                    ly = new LinearLayout(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
                    ly.setLayoutParams(layoutParams);
                    layoutParams.gravity = Gravity.RIGHT;
                    dodajDugmeGledaj(j);
                    dodajDugmeOmiljen(j, k);
                    sv.addView(ly);
                }
                dosad += 1;
            }

        }
        return view;

    }

    public static void setId(int id) {
        ProgramFragment.id = id;
    }

    public void dodajNaziv(int k, int p) {

        TextView nazivt = new TextView(this.getContext());
        nazivt.setLayoutParams(paramsTxt);
        nazivt.setTextSize(12);
        nazivt.setText(MainActivity.getChannels().get(k).getPrograms().get(p).getName());
        sv.addView(nazivt);
    }

    public void dodajVreme(int k, int p) {
        TextView vremet = new TextView(this.getContext());
        vremet.setLayoutParams(paramsTxt);
        vremet.setTextSize(10);
        Timestamp time = new Timestamp(MainActivity.getChannels().get(k).getPrograms().get(p).getStartDate());
        vremet.setText(time.getHours() + ":" + time.getMinutes());
        sv.addView(vremet);
    }

    public void dodajDugmeGledaj(int k) {
        gledaj = (MaterialButton) new MaterialButton(this.getContext());
        gledaj.setText("Gledaj");
        gledaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeniFragment.setChannel(k + 1);
            }
        });
        ly.addView(gledaj);
    }

    public void dodajDugmeOmiljen(int k, int p) {
        o = (Button) new Button(this.getContext());
        o.setLayoutParams(paramsSlike);
        o.setBackground(ChannelFragment.getSrceSlika().getDrawable());
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeniFragment.setOmiljen(k, p);

            }
        });
        ly.addView(o);
    }



}