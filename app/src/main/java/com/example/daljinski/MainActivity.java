package com.example.daljinski;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainActivity extends AppCompatActivity, STBTaskListenner, ComServiceListenner  {
    private Button meni1, meni2, meni3;
    private List<TimelineFragment> timelines=new ArrayList<TimelineFragment>();
	public final static int COMMUNICATION_PORT = 2000;
	//STBRemoteControlCommunication stbrcc;
	private ArrayList<Channel> channels = new ArrayList<>();
	private JSONObject user;
	
	private CommunicationServiceConnection serviceConnection;    
	private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		serviceConnection = new CommunicationServiceConnection(this);
		stbrcc = new STBRemoteControlCommunication(this);
	    //stbrcc.doBindService();
		
		ucitajJSON();

        meni1=(Button) findViewById(R.id.meni1);
        meni2=(Button) findViewById(R.id.meni2);
        meni3=(Button) findViewById(R.id.meni3);
        loadFragment(new MeniFragment());

        meni1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ChannelFragment());
				sendMessageToSTB("SHOWALL");
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
                loadFragment(new ChannelFragment());
				sendMessageToSTB("SHOWFAVE");
            }

        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


@Override
    public void onStop() {
        super.onStop();
        Log.d("mess","stop");
		if (serviceConnection.isBound()) {
    		unbindService(serviceConnection);
    		serviceConnection.setBound(false);
    	}
    }
	
	public static void sendMessageToSTB(String msg) {
		if (serviceConnection.isBound()) {
			new STBCommunicationTask(this, serviceConnection.getSTBDriver()).execute(STBCommunication.REQUEST_COMMAND, msg);
		}
	}
	public static void sendMessageToSTB(String msg, int extra) {
		if (serviceConnection.isBound()) {
			new STBCommunicationTask(this, serviceConnection.getSTBDriver()).execute(STBCommunication.REQUEST_COMMAND, msg, extra);
		}
	}
	
	@Override
	public void requestSucceed(String request, String message, String command) {
		if (STBCommunication.REQUEST_SCAN.equals(request)) {
			new STBCommunicationTask(this, serviceConnection.getSTBDriver()).execute(STBCommunication.REQUEST_CONNECT, message);
		} else if (STBCommunication.REQUEST_CONNECT.equals(request)) {
			connected = true;
			invalidateOptionsMenu();
		} else if (STBCommunication.REQUEST_DISCONNECT.equals(request)) {
		}
		
	}
	
	@Override
	public void requestFailed(String request, String message, String command) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
		if (STBCommunication.REQUEST_SCAN.equals(request)) {
		}
		
	}
	/* 	TREBA BLUETOOTH UMESTO WIFI
	public static String getLocalIpAddress() {
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		String ipBinary = null;
		try {
			ipBinary = Integer.toBinaryString(wm.getConnectionInfo().getIpAddress());
		} catch (Exception e) {}
		if (ipBinary != null) {
			while(ipBinary.length() < 32) {
				ipBinary = "0" + ipBinary;
			}
			String a = ipBinary.substring(0,8);
			String b = ipBinary.substring(8,16);
			String c = ipBinary.substring(16,24);
			String d = ipBinary.substring(24,32);
			String actualIpAddress = Integer.parseInt(d,2) + "." + Integer.parseInt(c,2) + "." + Integer.parseInt(b,2) + "." + Integer.parseInt(a,2);
			return actualIpAddress;
		} else {
			return null;
		}
	}*/

	public static void lauchScan() {
		new STBCommunicationTask(this, serviceConnection.getSTBDriver()).execute(STBCommunication.REQUEST_SCAN, getLocalIpAddress());
		
	}
	
	@Override
	public void serviceBound() {
		if (!serviceConnection.getSTBDriver().isConnected()) {
			lauchScan();
		}
	}
	
	
	@Override
	public void serviceUnbind() {
		connected = false;
	}
	
	public void ucitajJSON(String path) {
       String path="C:\\Users\\Julija\\Documents\\GitHub\\daljinski\\app\\src\\main\\res\\drawable\\channels.json";
      	JSONParser jsonParser = new JSONParser();
          
          try (FileReader reader = new FileReader(path))
          {
              Object obj = jsonParser.parse(reader);
              JSONArray channelsList = (JSONArray) obj;
              
              for(int i=0;i<channelsList.size();i++) {
              m1.channels.add(m1.parseChannelObject((JSONObject)channelsList.get(i)));
            
              }
              
   
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          } catch (ParseException e) {
              e.printStackTrace();
          }
    }
	
	public  Program parseProgramObject(JSONObject program) 
    {
		
		String objectType=(String)program.get("objectType");
		long createDate=(long)program.get("createDate");
		String description=(String)program.get("description");
		long endDate=(long)program.get("endDate");
		String externalId=(String)program.get("externalId");
		long id=(long)program.get("id");
		ArrayList<String> images= new ArrayList<>();
		JSONArray imagesArray = (JSONArray) program.get("images");
		for(int i=0;i<imagesArray.size();i++) images.add((String)((JSONObject)imagesArray.get(i)).get("url"));
		JSONObject metas= (JSONObject)program.get("metas");
		long rating=Integer.parseInt((String)((JSONObject)metas.get("rating")).get("value"));
		long year;
		if((JSONObject)metas.get("year")==null) year=0;
		else year=Integer.parseInt((String)((JSONObject)metas.get("year")).get("value"));
		String name=(String)program.get("name");
		long startDate=(long)program.get("startDate");
		JSONObject tags= (JSONObject)program.get("tags");
		ArrayList<String> country= new ArrayList<>();
		JSONObject countryObject= (JSONObject)tags.get("country");
		JSONArray objectArray;
		if(countryObject!=null) {
		objectArray = (JSONArray) countryObject.get("objects");
		for(int i=0;i<objectArray.size();i++) {
			country.add((String)((JSONObject)objectArray.get(i)).get("value"));
		}
		}
		ArrayList<String> category= new ArrayList<>();
		JSONObject categoryObject= (JSONObject)tags.get("category");
		objectArray = (JSONArray) categoryObject.get("objects");
		for(int i=0;i<objectArray.size();i++) {
			category.add((String)((JSONObject)objectArray.get(i)).get("value"));
		}
		ArrayList<String> genre= new ArrayList<>();
		JSONObject genreObject= (JSONObject)tags.get("genre");
		objectArray = (JSONArray) genreObject.get("objects");
		for(int i=0;i<objectArray.size();i++) {
			genre.add((String)((JSONObject)objectArray.get(i)).get("value"));
		}
		
        return new Program(objectType,createDate,description, endDate, externalId, id, images, rating, year, /*episode_number, season_number,series_id, series_name,*/ name, startDate, country, category, genre);
    }


	 public Channel parseChannelObject(JSONObject channel) 
	    {
	        JSONObject resultObject = (JSONObject) channel.get("result");
	        String objectType=(String)resultObject.get("objectType");
			long totalCount=(long) resultObject.get("totalCount");
	         JSONArray channelsArray = (JSONArray) resultObject.get("objects");
	         ArrayList<Program> programs= new ArrayList<>();
	         for(int i=0;i<channelsArray.size();i++) {
	        	 programs.add(this.parseProgramObject((JSONObject)channelsArray.get(i)));
	         }
	        
	        return new Channel(objectType,totalCount,programs);
	    }
	
	public ArrayList<JSONObject> getChannels(){
		return channels;
	}
	
	public JSONObject getUser(){
		return user;
	}

}