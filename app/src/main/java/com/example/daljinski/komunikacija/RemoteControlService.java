package com.example.daljinski.komunikacija;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.daljinski.MainActivity;

public class RemoteControlService extends Service {
	
	public static final String TAG = "RemoteControlService" ;



    public void sendMessageToUI(String msg_type, String msg_value) {
        int type=0;
        switch (msg_type) {
            case "CMD__HOME": type=0;
                break;
            case "VOLDOWN": type=1;
                break;
            case "VOLUP": type=2;
                break;
            case "CHDOWN": type=3;
                break;
            case "CHUP": type=4;
                break;
            case "GOTOALL": type=5;
                break;
        }

        Bundle b = new Bundle();
        b.putString("msg_value", ""+type);
        Message msg = Message.obtain(null, type);
        msg.setData(b);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        ServerRunnable server_runnable = new ServerRunnable(this, MainActivity.COMMUNICATION_PORT);
        Thread server_thread = new Thread(server_runnable);
        server_thread.start();
        Log.i(TAG, "Service Started.");
    }
        
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Stopped.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}