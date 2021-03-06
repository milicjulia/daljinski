package com.example.daljinski.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.daljinski.MainActivity;
import com.example.daljinski.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChannelFragment extends Fragment {
    View view;
    private LinearLayout layout;
    static ArrayList<FrameLayout> frames = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel, container, false);
        layout = (LinearLayout) view.findViewById(R.id.linearlayout1);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 250, Gravity.CENTER);
        dodajKanale(params);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dodajKanale(ViewGroup.LayoutParams params) {
        int brkonala = MainActivity.getChannels().size();
        for (int i = 0; i < brkonala; i++) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                FrameLayout f = new FrameLayout(getContext());
                f.setLayoutParams(params);
                f.setId(R.id.channelfragment + i);
                frames.add(f);
                layout.addView(f);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.replace(R.id.channelfragment + i, new TimelineFragment());
                    ft.commit();
                }
            }


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("ChannelFragment", "start");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ChannelFragment", "resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("ChannelFragment", "pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ChannelFragment", "stop");
        TimelineFragment.setId(0);
        ProgramFragment.setId(0);
    }


}