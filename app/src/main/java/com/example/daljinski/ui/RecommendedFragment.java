package com.example.daljinski.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.room.Room;
import com.example.daljinski.MainActivity;
import com.example.daljinski.R;
import com.example.daljinski.baza.BazaDatabase;
import com.example.daljinski.baza.OmiljeniEntity;
import com.example.daljinski.baza.ProgramEntity;
import com.example.daljinski.baza.ZanrProgramEntity;
import com.example.daljinski.entiteti.Program;
import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class RecommendedFragment extends Fragment {
    private ArrayList<TextView> naziv = new ArrayList<>();
    private ArrayList<TextView> kanal = new ArrayList<>();
    private ArrayList<TextView> opis = new ArrayList<>();
    private ArrayList<TextView> vreme = new ArrayList<>();
    private ArrayList<MaterialButton> gledaj = new ArrayList<>();
    private int ids[] = {R.id.naziv1,  R.id.vreme1, R.id.opis1, R.id.gledaj1, R.id.naziv2, R.id.vreme2, R.id.opis2, R.id.gledaj2, R.id.naziv3,  R.id.vreme3, R.id.opis3, R.id.gledaj3};
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommended, container, false);
        dodajKomponente(view);
        dodajPodatke(view);
        return view;
    }

    public void dodajKomponente(View v) {
        for (int i = 0; i < 3; i++) {
            naziv.add(view.findViewById(ids[i * 4]));
            vreme.add(view.findViewById(ids[i * 4 + 1]));
            opis.add(view.findViewById(ids[i * 4 + 2]));
            gledaj.add(view.findViewById(ids[i * 4+ 3]));
        }
    }

    public void dodajPodatke(View view) {
        int i = 0;
        Timestamp time;
        for (Program p : recommend()) {
            naziv.get(i).setText(p.getName());
            time = new Timestamp(p.getStartDate());
            vreme.get(i).setText("       "+time.getHours() + ":" + time.getMinutes());
            opis.get(i).setText(p.getDescription());
            int finalI = i;
            gledaj.get(i).setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        gledaj.get(finalI).setTextColor(Color.CYAN);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        MeniFragment.setChannel(p.getIdKanala());
                        gledaj.get(finalI).setTextColor(Color.rgb(30,128,115));
                        gledaj.get(finalI).setBackgroundColor(Color.WHITE);
                    }
                    return true;
                }
            });

            i++;
        }
    }

    ArrayList<Program> p = new ArrayList<>();

    public void addRandom(int size) {
        int s = 3 - size;
        int ch, pro;
        for (int i = 0; i < s; i++) {
            Random rand = new Random();
            ch = rand.nextInt(2);
            pro = rand.nextInt(MainActivity.getChannels().get(ch).getPrograms().size());
            p.add(MainActivity.getChannels().get(ch).getPrograms().get(pro));
        }
    }

    public static HashMap<Integer, Integer> sortByKol(HashMap<Integer, Integer> hm) {
        HashMap<Integer, Integer> temp = hm.entrySet().stream().sorted((i1, i2) -> i1.getValue().compareTo(i2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return temp;
    }

    public ArrayList<Program> recommend() {

        if (MainActivity.getLikes().size() == 0) addRandom(0);
        else {
            BazaDatabase db = Room.databaseBuilder(getContext(), BazaDatabase.class, "database-name").allowMainThreadQueries().build();
            ArrayList<OmiljeniEntity> o = MainActivity.getLikes();
            ArrayList<ProgramEntity> programEntities = new ArrayList<>();
            for (OmiljeniEntity oe : o) {
                if (oe.getKolicina() == 0) o.remove(oe);
            }
            ArrayList<ZanrProgramEntity> programsForGenre = new ArrayList<>(); //svi programi koji sadrze lajkovane zanrove
            for (OmiljeniEntity oe : o) {
                for (ZanrProgramEntity zpe : db.zanrProgramDAO().getProgramsForGenre(oe.getTip())) {
                    programsForGenre.add(zpe);
                }
            }
            HashMap<Integer, Integer> frequencymap = new HashMap<Integer, Integer>();
            for (ZanrProgramEntity zpe : programsForGenre) { //od svih programa sa lajkovanim zanrovima, pravim hash mapu sa koliko puta se koji program pojvaljuje
                if (frequencymap.containsKey(zpe.getIdPrograma())) {
                    frequencymap.put(zpe.getIdPrograma(), frequencymap.get(zpe.getIdPrograma()) + 1);
                } else frequencymap.put(zpe.getIdPrograma(), 1);
            }
            frequencymap = sortByKol(frequencymap);

            List<Integer> top = frequencymap.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
            if (top.size() >= 3) {
                for (int i = 0; i < 3; i++) { //nadjem id top 3 porgrama koja se najvise pojavljuju
                    ProgramEntity ppp = db.programDao().getProgram(top.get(i).intValue());
                    programEntities.add(ppp);
                }
            }

            for (ProgramEntity pe : programEntities) {
                Program pp = new Program(pe.getObjectType(), pe.getDescription(), pe.getEndDate(), pe.getId(), pe.getName(), pe.getStartDate(), new ArrayList<String>());
                pp.setIdKanala(pe.getIdKanala());
                p.add(pp);
            }
            while (p.size() < 3) {
                addRandom(p.size());
                removeDuplicates(p);
            }

        }


        return p;
    }

    public static void removeDuplicates(ArrayList<Program> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            boolean found = false;
            Program pomi = list.get(i);
            for (int j = 1; j < list.size(); j++) {
                Program pomj = list.get(j);
                if (pomi.getName().compareTo(pomj.getName()) == 0) {
                    found = true;
                    break;
                }
            }
            if (found == true) list.remove(pomi);

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("RecommenedFragment", "start");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("RecommenedFragment", "resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("RecommenedFragment", "pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("RecommenedFragment", "stop");
    }

}