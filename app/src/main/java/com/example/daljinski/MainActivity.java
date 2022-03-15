package com.example.daljinski;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button chUp, chDown, volUp, volDown, S;
    private static int volume=50, channel=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chUp=(Button) findViewById(R.id.chup);
        chDown=(Button) findViewById(R.id.chdown);
        volUp=(Button) findViewById(R.id.volup);
        volDown=(Button) findViewById(R.id.voldown);
        S=(Button) findViewById(R.id.speechButton);
        S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        chUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                channel+=1;

            }
        });
        chDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(channel!=1)
                channel-=1;

            }
        });
        volUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(volume<=95 && volume>=0)
                    volume+=5;

            }
        });
        volDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(volume<=100 && volume>=5)
                    volume-=5;
            }
        });

    }
}