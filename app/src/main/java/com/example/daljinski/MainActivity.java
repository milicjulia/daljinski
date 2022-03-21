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

public class MainActivity extends AppCompatActivity, STBTaskListenner, ComServiceListenner  {
    private Button meni1, meni2, meni3;
    private List<TimelineFragment> timelines=new ArrayList<TimelineFragment>();
	public final static int COMMUNICATION_PORT = 2000;
	//STBRemoteControlCommunication stbrcc;
	
	private CommunicationServiceConnection serviceConnection;    
	private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		serviceConnection = new CommunicationServiceConnection(this);
		stbrcc = new STBRemoteControlCommunication(this);
	    //stbrcc.doBindService();

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

}