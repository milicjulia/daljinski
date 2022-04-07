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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class RecommendedFragment extends Fragment {
    private ArrayList<TextView> naziv=new ArrayList<>();
    private ArrayList<TextView> kanal=new ArrayList<>();
    private ArrayList<TextView> opis=new ArrayList<>();
    private ArrayList<TextView> vreme=new ArrayList<>();
    private ArrayList<Button> gledaj=new ArrayList<>();
    private int ids[]={R.id.naziv1,R.id.kanal1,R.id.vreme1,R.id.opis1,R.id.gledaj1, R.id.naziv2,R.id.kanal2,R.id.vreme2,R.id.opis2,R.id.gledaj2,R.id.naziv3,R.id.kanal3,R.id.vreme3,R.id.opis3,R.id.gledaj3};
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
            naziv.add(view.findViewById(ids[i*5]));
            kanal.add(view.findViewById(ids[i*5+1]));
            vreme.add(view.findViewById(ids[i*5+2]));
            opis.add(view.findViewById(ids[i*5+3]));
            gledaj.add(view.findViewById(ids[i*5+4]));
        }
    }

    public void dodajPodatke(View view){
        int i=0;
        Timestamp time;
        for(Program p:recommend()){
            naziv.get(i).setText(p.getName());
            kanal.get(i).setText("Kanal "+(p.getIdKanala()));
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
    ArrayList<Program> p=new ArrayList<>();
    public void addRandom(int size){
        int s=3-size;
        int ch, pro;
        for(int i=0;i<s;i++){
            Random rand=new Random();
            ch= rand.nextInt(2);
            pro=rand.nextInt(MainActivity.getChannels().get(ch).getPrograms().size());
            p.add(MainActivity.getChannels().get(ch).getPrograms().get(pro));
        }
    }

    public ArrayList<Program> recommend(){

        if(MainActivity.getLikes().size()==0) addRandom(0);
        else{
            BazaDatabase db = Room.databaseBuilder(getContext(), BazaDatabase.class, "database-name").allowMainThreadQueries().build();
            ArrayList<OmiljeniEntity> o= MainActivity.getLikes();
            ArrayList<ProgramEntity> programEntities=new ArrayList<>();
            o.sort(Comparator.comparingInt(OmiljeniEntity::getKolicina));
            ArrayList<ZanrProgramEntity> programsForGenre=new ArrayList<>();
            for(OmiljeniEntity oe:o){
                for(ZanrProgramEntity oe1:db.zanrProgramDAO().getProgramsForGenre(oe.getTip())){
                    programsForGenre.add(oe1);
                }
            }
            programsForGenre.sort(Comparator.comparingInt(ZanrProgramEntity::getIdPrograma));
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
                programEntities.add(ppp);
            }
            programEntities=removeDuplicates(programEntities);
            for(ProgramEntity pe:programEntities){
                Program pp=new Program(pe.getObjectType(),pe.getCreateDate(), pe.getDescription()
                        , pe.getEndDate(), pe.getExternalId(), pe.getId(), pe.getUrl(), pe.getRating(), pe.getYear(), pe.getName(), pe.getStartDate(), null, null, null);
                pp.setIdKanala(pe.getIdKanala());
                p.add(pp);
            }

            addRandom(p.size());
            }


        return p;
    }

    public static ArrayList<ProgramEntity> removeDuplicates(ArrayList<ProgramEntity> list) {

        ArrayList<ProgramEntity> newList = new ArrayList<ProgramEntity>();

        for (int i=0;i<list.size()-1;i++) {
            boolean found=false;
            ProgramEntity pom=list.get(i);
            for (int j=1;j<list.size();j++) {
                if(list.get(i).getName().compareTo(list.get(j).getName())==0 && list.get(i).getIdKanala()==list.get(j).getIdKanala()){
                    found=true;
                    break;
                }
            }
            if (found==false) {
                newList.add(pom);
            }
        }
        return newList;
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