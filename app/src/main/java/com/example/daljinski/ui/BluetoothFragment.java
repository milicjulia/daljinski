package com.example.daljinski.ui;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.daljinski.MainActivity;
import com.example.daljinski.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

public class BluetoothFragment extends Fragment {
    public View view;
    public Button b1,b4;
    public ListView lv;
    public Set<BluetoothDevice> pairedDevices;
    public RelativeLayout rl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bluetooth, container, false);

        b1 = (Button) view.findViewById(R.id.button);
        lv = (ListView) view.findViewById(R.id.listView);
        rl = (RelativeLayout) view.findViewById(R.id.bt);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on();
                list();
            }
        });


        return view;
    }

    public void on(){
        if (MainActivity.bluetoothAdapter != null && !MainActivity.bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            Toast.makeText(view.getContext(), "Turned on",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(view.getContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("BluetoothFragment","start");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BluetoothFragment","resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("BluetoothFragment","pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("BluetoothFragment","stop");
    }

    public void list(){
        Set<BluetoothDevice> pairedDevices = MainActivity.bluetoothAdapter.getBondedDevices();
        Log.i("Bluetooth", "uslo list");
        Log.i("Bluetooth", String.valueOf(pairedDevices.size()));
        int pos=200;
        if (pairedDevices.size() > 0) {
            ArrayList list = new ArrayList();
            for(BluetoothDevice bt : pairedDevices){
                TextView txt=new TextView(view.getContext());
                txt.setText(bt.getName());
                txt.setPadding(10,pos,50,50);
                pos+=50;
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Log.i("Bluetooth", "clicked");
                        Log.e("Bluetooth", "" + bt.getName());
                        MainActivity.ConnectThread connect = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                            connect = new MainActivity.ConnectThread(bt, bt.getUuids()[0].getUuid());
                        }
                        connect.start();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, new MeniFragment());
                        fragmentTransaction.commit();
                    }
                });
                rl.addView(txt);

            }

        }
    }


}