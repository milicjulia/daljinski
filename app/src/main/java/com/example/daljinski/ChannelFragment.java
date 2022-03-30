package com.example.daljinski;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChannelFragment extends Fragment {
    View view;
    private LinearLayout layout;
    static ArrayList<FrameLayout> frames = new ArrayList<>();

    public static ArrayList<FrameLayout> getFrames() {
        return frames;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel, container, false);
        layout = (LinearLayout) view.findViewById(R.id.linearlayout1);

        dodajKanale();
        return view;
    }

    public void dodajKanale() {
        int brkonala = MainActivity.getChannels().size();
        for (int i = 0; i < brkonala; i++) {
            FrameLayout f = new FrameLayout(this.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 300, Gravity.CENTER);
            f.setLayoutParams(params);
            f.setId(R.id.channelfragment+i);
            frames.add(f);
            layout.addView(f);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.channelfragment+i, new TimelineFragment());
            ft.commit();


        }
    }


}