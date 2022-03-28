package com.example.daljinski;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class ChannelFragment extends Fragment {
    View view;


    public static ArrayList<FrameLayout> getFrames() {
        return frames;
    }

    public static void setFrames(ArrayList<FrameLayout> frames) {
        ChannelFragment.frames = frames;
    }

    static ArrayList<FrameLayout> frames=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_channel, container, false);
        ViewGroup vg=(ViewGroup) view;
        /*view.findViewById(R.id.svikanali2).findViewById(R.id.gledaj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeniFragment.setChannel(2500);
            }
        });
        view.findViewById(R.id.svikanali1).findViewById(R.id.gledaj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeniFragment.setChannel(1500);
            }
        });
        view.findViewById(R.id.svikanali).findViewById(R.id.gledaj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeniFragment.setChannel(3500);
            }
        });*/

        int brkonala=MainActivity.getChannels().size();
        for(int i=0;i<brkonala;i++){
            FrameLayout f=new FrameLayout(this.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,100,
                    Gravity.CENTER);
            f.setLayoutParams(params);
            frames.add(f);
            vg.addView(f);
        }



        return vg;
    }
	
	
}