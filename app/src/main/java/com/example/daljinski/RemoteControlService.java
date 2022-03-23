package com.example.daljinski;
import java.util.ArrayList;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import MainActivity;
import ServerRunnable;

public class RemoteControlService extends Service {
	
	public static final String TAG = "RemoteControlService" ;
	
	
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
    
   /* class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG__REGISTER_CLIENT:
                mClients.add(msg.replyTo);
                break;
            case MSG__UNREGISTER_CLIENT:
                mClients.remove(msg.replyTo);
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }*/
    
    public void sendMessageToUI(int msg_type, String msg_value) {
    	for (int i=mClients.size()-1; i>=0; i--) {
            try {
                Bundle b = new Bundle();
                b.putString("msg_value", ""+msg_value);
                Message msg = Message.obtain(null, msg_type);
                msg.setData(b);
            } catch (RemoteException e) {
            }
        }
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
}