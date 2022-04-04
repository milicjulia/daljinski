package com.example.daljinski.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class RecommendedFragment extends Fragment {
    private ArrayList<TextView> naziv=new ArrayList<>();
    private ArrayList<TextView> opis=new ArrayList<>();
    private ArrayList<TextView> vreme=new ArrayList<>();
    private ArrayList<Button> gledaj=new ArrayList<>();
    private int ids[]={R.id.naziv1,R.id.vreme1,R.id.opis1,R.id.gledaj1, R.id.naziv2,R.id.vreme2,R.id.opis2,R.id.gledaj2,R.id.naziv3,R.id.vreme3,R.id.opis3,R.id.gledaj3};
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_recommended, container, false);
        dodajKomponente(view);
        dodajPodatke(view);
        return view;
    }

    public void dodajKomponente(View v){
        for(int i=0;i<3;i++){
            naziv.add(view.findViewById(ids[i*4]));
            vreme.add(view.findViewById(ids[i*4+1]));
            opis.add(view.findViewById(ids[i*4+2]));
            gledaj.add(view.findViewById(ids[i*4+3]));
        }
        /*
        Program p=MainActivity.getChannels().get(0).getPrograms().get(0);
        naziv1.setText(p.getName());
        naziv2.setText(p.getName());
        naziv3.setText(p.getName());
        opis1.setText(p.getDescription());
        opis2.setText(p.getDescription());
        opis3.setText(p.getDescription());
        Timestamp time = new Timestamp(p.getStartDate());
        vreme1.setText(time.getHours() + ":" + time.getMinutes());
        vreme2.setText(time.getHours() + ":" + time.getMinutes());
        vreme3.setText(time.getHours() + ":" + time.getMinutes());*/
    }

    public void dodajPodatke(View view){
        int i=0;
        Timestamp time;
        for(Program p:recommend()){
            naziv.get(i).setText(p.getName());
            time = new Timestamp(p.getStartDate());
            vreme.get(i).setText(time.getHours() + ":" + time.getMinutes());
            opis.get(i).setText(p.getDescription());
            gledaj.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MeniFragment.setChannel(p.getIdKanala());
                }
            });
            i++;
        }
    }


    public ArrayList<Program> recommend(){
        ArrayList<Program> p=new ArrayList<>();
        if(MainActivity.getLikes().size()==0){
            int ch, pro;
            for(int i=0;i<3;i++){
                Random rand=new Random();
                ch= rand.nextInt(2);
                pro=rand.nextInt(MainActivity.getChannels().get(ch).getPrograms().size());
                p.add(MainActivity.getChannels().get(ch).getPrograms().get(pro));
            }
        }
        else{
            //likes je lista zanrova koje sam lajkovala i koliko puta npr (crime,3), (history,1)
            BazaDatabase db = Room.databaseBuilder(getContext(), BazaDatabase.class, "database-name").allowMainThreadQueries().build();
            ArrayList<OmiljeniEntity> o= MainActivity.getLikes();
            ArrayList<ProgramEntity> programEntities=new ArrayList<>();
            o.sort((o1, o2) -> o1.getKolicina()-o2.getKolicina());
            ArrayList<ZanrProgramEntity> programsForGenre=new ArrayList<>();
            int j=0;
            for(OmiljeniEntity oe:o){
                for(ZanrProgramEntity oe1:db.zanrProgramDAO().getProgramsForGenre(oe.getTip())){
                    programsForGenre.add(oe1);
                }
            }
            programsForGenre.sort((o1, o2) -> o1.getIdPrograma()-o2.getIdPrograma());
            HashMap<Integer, Integer> frequencymap = new HashMap<Integer, Integer>();
            for(ZanrProgramEntity zpe:programsForGenre) {
                if(frequencymap.containsKey(zpe.getIdPrograma())) {
                    frequencymap.put(zpe.getIdPrograma(), frequencymap.get(zpe.getIdPrograma())+1);
                }
                else{ frequencymap.put(zpe.getIdPrograma(), 1); }
            }
            List<Integer> top3 = frequencymap.entrySet().stream().sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
            for(int num:top3){
                ProgramEntity ppp=new ProgramEntity();
                ppp=db.programDao().getProgram(num);
                Log.d("greska", String.valueOf(ppp.getName()));
                programEntities.add(ppp);
            }
            Log.d("greska", String.valueOf(programEntities.size()));
            for(ProgramEntity pe:programEntities){
                Log.d("greska",pe.getName());
                Program pp=new Program(pe.getObjectType(),pe.getCreateDate(), pe.getDescription()
                        , pe.getEndDate(), pe.getExternalId(), pe.getId(), pe.getUrl(), pe.getRating(), pe.getYear(), pe.getName(), pe.getStartDate(), null, null, null);
                p.add(pp);
            }
            int ch, pro;
            if(programEntities.size()==1){
                for(int i=0;i<2;i++){
                    Random rand=new Random();
                    ch= rand.nextInt(2);
                    pro=rand.nextInt(MainActivity.getChannels().get(ch).getPrograms().size());
                    p.add(MainActivity.getChannels().get(ch).getPrograms().get(pro));
                }
            }
            else if(programEntities.size()==2){
                Random rand=new Random();
                ch= rand.nextInt(2);
                pro=rand.nextInt(MainActivity.getChannels().get(ch).getPrograms().size());
                p.add(MainActivity.getChannels().get(ch).getPrograms().get(pro));
            }

        }


        return p;
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