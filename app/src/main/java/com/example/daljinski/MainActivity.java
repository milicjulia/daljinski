package com.example.daljinski;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.daljinski.baza.BazaDatabase;
import com.example.daljinski.baza.ChannelDAO;
import com.example.daljinski.baza.ChannelEntity;
import com.example.daljinski.baza.OmiljeniDAO;
import com.example.daljinski.baza.OmiljeniEntity;
import com.example.daljinski.baza.ProgramDAO;
import com.example.daljinski.baza.ProgramEntity;
import com.example.daljinski.baza.ZanrDAO;
import com.example.daljinski.baza.ZanrProgramDAO;
import com.example.daljinski.baza.ZanrProgramEntity;
import com.example.daljinski.baza.ZanroviEntity;
import com.example.daljinski.bluetooth.ConnectedThread;
import com.example.daljinski.entiteti.Channel;
import com.example.daljinski.ui.BluetoothFragment;
import com.example.daljinski.ui.ChannelFragment;
import com.example.daljinski.ui.MeniFragment;
import com.example.daljinski.entiteti.Program;
import com.example.daljinski.ui.RecommendedFragment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    public static TabLayout tab;
    private static ArrayList<Channel> channels = new ArrayList<>();
    private static ArrayList<OmiljeniEntity> likes = new ArrayList<>();
    private BazaDatabase db;
    private OmiljeniDAO omiljeniDAO;
    private ZanrDAO zanroviDAO;
    private ZanrProgramDAO zanrProgramDAO;
    public static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public static BluetoothDevice mmDevice;
    public static ConnectedThread mConnectedThread;
    private String TAG = "MainActivity";
    public static ChannelFragment channelFragment;
    public static BluetoothFragment bluetoothFragment;
    public static MeniFragment meniFragment;
    public static RecommendedFragment recommendedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), BazaDatabase.class, "database-name").allowMainThreadQueries().build();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ucitajJSONKanale(getApplicationContext());
        poveziSaDAO();
        dodajKomponenteMeni();

    }

    public void off() {
        MainActivity.bluetoothAdapter.disable();
        Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame, bluetoothFragment);
        fragmentTransaction.commit();
        MainActivity.tab.setVisibility(View.INVISIBLE);

    }

    public void poveziSaDAO() {
        ChannelDAO channelDao = db.channelDao();
        ProgramDAO programDao = db.programDao();
        omiljeniDAO = db.omiljeniDAO();
        zanroviDAO = db.zanrDAO();
        zanrProgramDAO = db.zanrProgramDAO();
        for (int i = 0; i < channels.size(); i++) {
            Channel chi = channels.get(i);
            channelDao.insertChannel(new ChannelEntity(i + 1, chi.getObjectType(), chi.getTotalCount()));
            for (int j = 0; j < chi.getPrograms().size(); j++) {
                Program pj = chi.getPrograms().get(j);
                channels.get(i).getPrograms().get(j).setIdKanala(i + 1);
                channels.get(i).getPrograms().get(j).setOmiljen(pj.getOmiljen());
                pj.setId(i * 24 + j + 1);
                programDao.insertProgram(new ProgramEntity(pj));
                for (String s : pj.getGenres()) {
                    zanroviDAO.insertZanr(new ZanroviEntity(s));
                    zanrProgramDAO.insertZanrProgram(new ZanrProgramEntity(programDao.getIdProgram(pj.getId()), s));
                }
            }
        }
        for (OmiljeniEntity like : omiljeniDAO.getOmiljene()) {
            likes.add(like);
        }
    }

    public void dodajKomponenteMeni() {
        channelFragment=new ChannelFragment();
        meniFragment=new MeniFragment();
        bluetoothFragment=new BluetoothFragment();
        recommendedFragment=new RecommendedFragment();

        TabItem meni1 = (TabItem) findViewById(R.id.meni1);
        TabItem meni2 = (TabItem) findViewById(R.id.meni2);
        TabItem meni3 = (TabItem) findViewById(R.id.meni3);
        tab = (TabLayout) findViewById(R.id.tab);
        tab.getTabAt(1).select();
        loadFragment(meniFragment);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab t) {
                int selectedTab = tab.getSelectedTabPosition();
                switch (selectedTab) {
                    case 0:
                        loadFragment(channelFragment);
                        break;
                    case 1:
                        loadFragment(meniFragment);
                        break;
                    case 2:
                        loadFragment(recommendedFragment);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        loadFragment(bluetoothFragment);
        tab.setVisibility(View.INVISIBLE);
    }


    public static ArrayList<OmiljeniEntity> getLikes() {
        return likes;
    }

    public static ArrayList<Channel> getChannels() {
        return channels;
    }


    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        off();
        for (OmiljeniEntity like : likes) {
            omiljeniDAO.insertOmiljen(like);
        }
    }


    public void ucitajJSONKanale(Context context) {
        JSONParser jsonParser = new JSONParser();
        AssetManager manager = context.getAssets();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open("channels.json"), "UTF-8"))) {
            Object obj = jsonParser.parse(reader);
            JSONArray channelsList = (JSONArray) obj;
            for (int i = 0; i < channelsList.size(); i++) {
                channels.add(parseChannelObject((JSONObject) channelsList.get(i)));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public Program parseProgramObject(JSONObject program) {
        String objectType = null;
        try {
            objectType = (String) program.get("objectType");
            String description = (String) program.get("description");
            long endDate = (long) program.get("endDate");
            long id = (long) program.get("id");
            String name = (String) program.get("name");
            long startDate = (long) program.get("startDate");
            JSONObject tags = (JSONObject) program.get("tags");
            JSONArray objectArray;
            ArrayList<String> genre = new ArrayList<>();
            JSONObject genreObject = (JSONObject) tags.get("genre");
            objectArray = (JSONArray) genreObject.get("objects");
            for (int i = 0; i < objectArray.size(); i++) {
                genre.add((String) ((JSONObject) objectArray.get(i)).get("value"));
            }
            return new Program(objectType, description, endDate, id, name, startDate, genre, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Channel parseChannelObject(JSONObject channel) {
        JSONObject resultObject = new JSONObject();
        try {
            resultObject = (JSONObject) channel.get("result");
            long totalCount = (long) resultObject.get("totalCount");
            JSONArray channelsArray = (JSONArray) resultObject.get("objects");
            ArrayList<Program> programs = new ArrayList<>();
            for (int i = 0; i < channelsArray.size(); i++) {
                programs.add(this.parseProgramObject((JSONObject) channelsArray.get(i)));
            }

            return new Channel("objectType", totalCount, programs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}