package com.example.daljinski.ui;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import com.example.daljinski.MainActivity;
import com.example.daljinski.R;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import com.google.android.material.button.MaterialButton;

public class ProgramFragment extends Fragment {
    private LinearLayout sv, ly;
    FrameLayout.LayoutParams paramsSlike,paramsTxt;
    private static int id = 0;
    private int myid = id++;
    View view;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  Log.d("ProgramFragment","onCreateView");
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ly = new LinearLayout(getContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
                        ly.setLayoutParams(layoutParams);
                        layoutParams.gravity = Gravity.RIGHT;
                        dodajDugmeGledaj(j);
                        MainActivity.dodajSliku(j,k);
                        dodajDugmeOmiljen(j, k);

                        sv.addView(ly);
                    }

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
        TextView nazivt = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nazivt = new TextView(this.getContext());
            nazivt.setLayoutParams(paramsTxt);
            nazivt.setTextSize(12);
            nazivt.setText(MainActivity.getChannels().get(k).getPrograms().get(p).getName());
            sv.addView(nazivt);
        }

    }

    public void dodajVreme(int k, int p) {
        TextView vremet = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            vremet = new TextView(this.getContext());
            vremet.setLayoutParams(paramsTxt);
            vremet.setTextSize(10);
            Timestamp time = new Timestamp(MainActivity.getChannels().get(k).getPrograms().get(p).getStartDate());
            vremet.setText(time.getHours() + ":" + time.getMinutes());
            sv.addView(vremet);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void dodajDugmeGledaj(int k) {
       // Log.d("ProgramFragment","dodajDugmeGledaj");
            MaterialButton gledaj = (MaterialButton) new MaterialButton(this.getContext());
            gledaj.setText("Gledaj");
            gledaj.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {

                    try {
                        MainActivity.mConnectedThread.queue.put(5*10+k+1);
                        MeniFragment.setChannel(k + 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            ly.addView(gledaj);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dodajDugmeOmiljen(int k, int p) {
        Log.d("ProgramFragment","dodajDugmeOmiljen");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Button o = (Button) new Button(this.getContext());
            o.setLayoutParams(paramsSlike);
            o.setBackground(MainActivity.getSrceSlika()[k*24+p].getDrawable());
            o.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                  //  Log.d("ProgramFragment","onclick "+k+" "+p);
                  if(MainActivity.getChannels().get(k).getPrograms().get(p).getOmiljen()==false){
                      Log.d("ProgramFragment","omiljen false");
                      MeniFragment.setOmiljen(k, p);
                      MainActivity.setSrceSlikaColor(k,p,Color.RED);

                      MainActivity.getChannels().get(k).getPrograms().get(p).setOmiljen(true);
                      MainActivity.programDAO.updateProgramOmiljen(MainActivity.getChannels().get(k).getPrograms().get(p).getId(),true);
                    }
                    else{
                      Log.d("ProgramFragment","omiljen true");
                      MainActivity.setSrceSlikaColor(k,p,Color.BLACK);
                      MainActivity.getChannels().get(k).getPrograms().get(p).setOmiljen(false);
                      MeniFragment.removeOmiljen(k,p);
                      MainActivity.programDAO.updateProgramOmiljen(MainActivity.getChannels().get(k).getPrograms().get(p).getId(),false);

                  }
                }
            });
            ly.addView(o);
        }

    }

    public boolean omiljenJe(int k, int p){
        return true;
    }



}