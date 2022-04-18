package com.example.daljinski.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.Log;

import com.example.daljinski.MainActivity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public  class ConnectThread extends Thread {
    private BluetoothSocket mmSocket;
    private String ConnectTag = "ConnectedThread";

    public ConnectThread(BluetoothDevice device, UUID uuid) {
        Log.d(ConnectTag, "Started.");
        MainActivity.mmDevice = device;
    }

    public void run() {
        Log.i(ConnectTag, "Run");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                mmSocket = (BluetoothSocket) MainActivity.mmDevice.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class}).invoke(MainActivity.mmDevice, 1);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        try {

            mmSocket.connect();
        } catch (IOException e) {
            try {
                mmSocket.close();
                Log.d(ConnectTag, "Closed Socket");
            } catch (IOException e1) {
                Log.e(ConnectTag, "Unable to close socket connection");
            }
            e.printStackTrace();
            return;
        }
        Log.d("Connected", "connected: Starting");
        MainActivity.mConnectedThread = new ConnectedThread(mmSocket);
        MainActivity.mConnectedThread.start();
    }

}

