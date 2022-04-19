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
    private String ConnectTag = "ConnectThread";
    private static  UUID myuuid= UUID.fromString("0000110a-0100-1000-8000-20805f9b34fb");

    public ConnectThread(BluetoothDevice device, UUID uuid) {
        Log.d(ConnectTag, "Started.");
        MainActivity.mmDevice = device;
        try {
            //   mmSocket = (BluetoothSocket) MainActivity.mmDevice.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class}).invoke(MainActivity.mmDevice, 1);
            mmSocket = MainActivity.mmDevice.createRfcommSocketToServiceRecord(myuuid);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Log.i(ConnectTag, "Run");

        try {
            MainActivity.bluetoothAdapter.cancelDiscovery();
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

