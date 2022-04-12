package com.example.daljinski.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.daljinski.MainActivity;
import com.example.daljinski.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChannelFragment extends Fragment {
    View view;
    private LinearLayout layout;
    static ArrayList<FrameLayout> frames = new ArrayList<>();
    static ImageView srceSlika;

    public static ArrayList<FrameLayout> getFrames() {
        return frames;
    }

    public static ImageView getSrceSlika() {
        return srceSlika;
    }

    public static void setSrceSlika(ImageView srceSlika) {
        ChannelFragment.srceSlika = srceSlika;
    }


    public void dodajSliku() {
        srceSlika = new ImageView(this.getContext());
        srceSlika.setLayoutParams(new FrameLayout.LayoutParams(25, 25,Gravity.LEFT));
        try {
            InputStream istr = getContext().getAssets().open("heart.png");
            srceSlika.setImageDrawable(Drawable.createFromStream(istr, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel, container, false);
        layout = (LinearLayout) view.findViewById(R.id.linearlayout1);
        dodajSliku();
        dodajKanale();
        return view;
    }

    public void dodajKanale() {
        int brkonala = MainActivity.getChannels().size();
        for (int i = 0; i < brkonala; i++) {
            FrameLayout f = new FrameLayout(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 250, Gravity.CENTER);
            f.setLayoutParams(params);
            f.setId(R.id.channelfragment+i);
            frames.add(f);
            layout.addView(f);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.channelfragment+i, new TimelineFragment());
            ft.commit();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("ChannelFragment","start");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ChannelFragment","resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("ChannelFragment","pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ChannelFragment","stop");
        TimelineFragment.setId(0);
        ProgramFragment.setId(0);
    }


}