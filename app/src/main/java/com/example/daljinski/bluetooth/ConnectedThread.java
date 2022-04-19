package com.example.daljinski.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectedThread extends Thread {
    public  LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    private final BluetoothSocket mmSocket;
    private final OutputStream mmOutStream;
    private String ConnectedTag = "ConnectedThread";

    public ConnectedThread(BluetoothSocket socket) {
        Log.d(ConnectedTag, "Starting");
        mmSocket = socket;
        OutputStream tmpOut = null;

        try {
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmOutStream = tmpOut;

    }


    public void chUp() {
        Log.d(ConnectedTag, "chup");
        try {
            mmOutStream.write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chDown() {
        Log.d(ConnectedTag, "chdown");
        try {
            mmOutStream.write(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void volUp() {
        Log.d(ConnectedTag, "volup");
        try {
            mmOutStream.write(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void volDown() {
        Log.d(ConnectedTag, "voldown");
        try {
            mmOutStream.write(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(int command) {
        Log.d(ConnectedTag, "play");
        try {
            mmOutStream.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (mmSocket.isConnected()) {
            try {
                final int incomingMessage = queue.take();
                switch(incomingMessage){
                    case 1: chUp();break;
                    case 2:chDown();break;
                    case 3: volUp(); break;
                    case 4: volDown(); break;
                    default: play(incomingMessage);break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
