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

public class MainActivity extends AppCompatActivity {
    private Button meni1, meni2, meni3;
    private List<TimelineFragment> timelines=new ArrayList<TimelineFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //createTimelines();

        meni1=(Button) findViewById(R.id.meni1);
        meni2=(Button) findViewById(R.id.meni2);
        meni3=(Button) findViewById(R.id.meni3);
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
                loadFragment(new ChannelFragment());
            }

        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

/*
    private void createTimelines(){
        int t=5;
        int[] p={3,4,5,6,7};
        for(int i=0;i<t;i++){
            TimelineFragment tf=new TimelineFragment(this.getApplicationContext());
            for(int j=0;j<p[i];j++){
                ProgramFragment pf=new ProgramFragment(this.getApplicationContext());
                TextView naziv=(TextView)pf.findViewById(R.id.naziv);
                naziv.setText("program"+j);
                TextView vreme=(TextView)pf.findViewById(R.id.vreme);
                naziv.setText("vreme"+j);

            }
        }
    }*/




}