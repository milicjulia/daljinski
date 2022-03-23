package com.example.daljinski;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class ProgramFragment extends Fragment {
    private TextView vreme, naziv;
    private Button gledaj;
    private static int id=0;
    private int myid=id;
View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_meni, container, false);
        Log.d("mess","uslo");
        getActivity().setContentView(R.layout.fragment_program);
        gledaj=(Button) getActivity().findViewById(R.id.gledaj);

        gledaj.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d("mess","menja");
                MeniFragment.setChannel(1);
				MainActivity.sendMessageToSTB("GOTOCH",myid);
            }
        });
        return view;

    }
}