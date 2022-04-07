package com.example.daljinski;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.example.daljinski.entiteti.Channel;
import com.example.daljinski.komunikacija.CommunicationService;
import com.example.daljinski.komunikacija.CommunicationServiceConnection;
import com.example.daljinski.ui.ChannelFragment;
import com.example.daljinski.ui.MeniFragment;
import com.example.daljinski.entiteti.Program;
import com.example.daljinski.ui.RecommendedFragment;
import com.example.daljinski.ui.TimelineFragment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends Activity {
    private Button meni1, meni2, meni3;
    private List<TimelineFragment> timelines = new ArrayList<TimelineFragment>();
    public final static int COMMUNICATION_PORT = 2000;
    private static ArrayList<Channel> channels = new ArrayList<>();
    private static ArrayList<OmiljeniEntity> likes = new ArrayList<>();
    private boolean connected;
    private static String KEY_FIRST_RUN = "";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public BazaDatabase db;
    private ChannelDAO channelDao;
    private ProgramDAO programDao;
    private OmiljeniDAO omiljeniDAO;
    private ZanrDAO zanroviDAO;
    private ZanrProgramDAO zanrProgramDAO;
    private CommunicationServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceConnection = new CommunicationServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub

            }
        };
        db = Room.databaseBuilder(getApplicationContext(), BazaDatabase.class, "database-name").allowMainThreadQueries().build();
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ucitajJSONKanale(getApplicationContext());
        channelDao = db.channelDao();
        programDao = db.programDao();
        omiljeniDAO = db.omiljeniDAO();
        zanroviDAO = db.zanrDAO();
        zanrProgramDAO = db.zanrProgramDAO();
        for (int i=0;i<channels.size();i++) {
            channelDao.insertChannel(new ChannelEntity(i+1, channels.get(i).getObjectType(), channels.get(i).getTotalCount()));
            for(int j=0;j<channels.get(i).getPrograms().size();j++){
                channels.get(i).getPrograms().get(j).setIdKanala(i+1);
                Program p=channels.get(i).getPrograms().get(j);
                p.setId(i*24+j+1);
                programDao.insertProgram(new ProgramEntity(p));
                for(String s:p.getGenres()){
                    zanroviDAO.insertZanr(new ZanroviEntity(s));
                    zanrProgramDAO.insertZanrProgram(new ZanrProgramEntity(programDao.getIdProgram(p.getId()),s));
                }
            }
        }
        for (OmiljeniEntity like: omiljeniDAO.getOmiljene()) {
            likes.add(like);
        }

        meni1 = (Button) findViewById(R.id.meni1);
        meni2 = (Button) findViewById(R.id.meni2);
        meni3 = (Button) findViewById(R.id.meni3);
        loadFragment(new MeniFragment());

        meni1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ChannelFragment());
            }
        });
        meni2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MeniFragment());
            }
        });
        meni3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new RecommendedFragment());
            }

        });

    }

    public static void setChannels(ArrayList<Channel> channels) {
        MainActivity.channels = channels;
    }

    public static ArrayList<OmiljeniEntity> getLikes() {
        return likes;
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(getApplicationContext(), CommunicationService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        for(OmiljeniEntity like:likes){
            omiljeniDAO.insertOmiljen(like);
        }
        if (serviceConnection.isBound()) {
            unbindService(serviceConnection);
            serviceConnection.setBound(false);
        }

    }



    //TREBA BLUETOOTH UMESTO WIFI
    public String getLocalIpAddress() {
        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        String ipBinary = null;
        try {
            ipBinary = Integer.toBinaryString(wm.getConnectionInfo().getIpAddress());
        } catch (Exception e) {
        }
        if (ipBinary != null) {
            while (ipBinary.length() < 32) {
                ipBinary = "0" + ipBinary;
            }
            String a = ipBinary.substring(0, 8);
            String b = ipBinary.substring(8, 16);
            String c = ipBinary.substring(16, 24);
            String d = ipBinary.substring(24, 32);
            String actualIpAddress = Integer.parseInt(d, 2) + "." + Integer.parseInt(c, 2) + "." + Integer.parseInt(b, 2) + "." + Integer.parseInt(a, 2);
            return actualIpAddress;
        } else {
            return null;
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


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }



    public Program parseProgramObject(JSONObject program) {
        String objectType = null;
        try {
            objectType = (String) program.get("objectType");
            long createDate = (long) program.get("createDate");
            String description = (String) program.get("description");
            long endDate = (long) program.get("endDate");
            //String externalId = (String) program.get("externalId");
            long id = (long) program.get("id");
            String image;
            JSONObject metas = (JSONObject) program.get("metas");
            /*JSONArray imagesArray = (JSONArray) program.get("images");
            image=((String) ((JSONObject) imagesArray.get(0)).get("url"));
            long rating = Integer.parseInt((String) ((JSONObject) metas.get("rating")).get("value"));*/
            long year;
            if ((JSONObject) metas.get("year") == null) year = 0;
            else year = Integer.parseInt((String) ((JSONObject) metas.get("year")).get("value"));
            String name = (String) program.get("name");
            long startDate = (long) program.get("startDate");
            JSONObject tags = (JSONObject) program.get("tags");
            ArrayList<String> country = new ArrayList<>();
            JSONObject countryObject = (JSONObject) tags.get("country");
            JSONArray objectArray;
            /*if (countryObject != null) {
                objectArray = (JSONArray) countryObject.get("objects");
                for (int i = 0; i < objectArray.size(); i++) {
                    country.add(Translator.translate(fromLang, toLang,(String) ((JSONObject) objectArray.get(i)).get("value")));
                }
            }*/
            /*ArrayList<String> category = new ArrayList<>();
            JSONObject categoryObject = (JSONObject) tags.get("category");
            objectArray = (JSONArray) categoryObject.get("objects");
            for (int i = 0; i < objectArray.size(); i++) {
                category.add(Translator.translate(fromLang, toLang,(String) ((JSONObject) objectArray.get(i)).get("value")));
            }*/
            ArrayList<String> genre = new ArrayList<>();
            JSONObject genreObject = (JSONObject) tags.get("genre");
            objectArray = (JSONArray) genreObject.get("objects");
            for (int i = 0; i < objectArray.size(); i++) {
                genre.add((String) ((JSONObject) objectArray.get(i)).get("value"));
            }
            return new Program(objectType, createDate, description, endDate, "1000", id, "image", 1000, year, /*episode_number, season_number,series_id, series_name,*/ name, startDate, country, null, genre);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Channel parseChannelObject(JSONObject channel) {
        JSONObject resultObject = new JSONObject();
        try {
            resultObject = (JSONObject) channel.get("result");
            //String objectType = Translator.translate(fromLang, toLang,(String) resultObject.get("objectType"));
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

    public static ArrayList<Channel> getChannels() {
        return channels;
    }


}