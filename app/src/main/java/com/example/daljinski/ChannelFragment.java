package com.example.daljinski;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChannelFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_channel, container, false);

        view.findViewById(R.id.svikanali2).findViewById(R.id.gledaj).setOnClickListener(new View.OnClickListener() {
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
        });
        return view;
    }
}