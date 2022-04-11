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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.daljinski.MainActivity;
import com.example.daljinski.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TimelineFragment extends Fragment {
    private ImageView channelPicture;
    View view;
    private LinearLayout sv, timeline;
    private HorizontalScrollView skrol;


    public static void setId(int id) {
        TimelineFragment.id = id;
    }

    private static int id = 0;
    int mojid = id++;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        skrol=(HorizontalScrollView) view.findViewById(R.id.skrol);
        sv=(LinearLayout) view.findViewById(R.id.kanalisvitimeline);
        timeline=(LinearLayout) view.findViewById(R.id.timeline);
        //Log.d("contructor","timeline");
        dodajSliku();
        dodajPrograme();
        return view;

    }

    public void dodajSliku() {
        ImageView iv = new ImageView(this.getContext());
        iv.setLayoutParams(new FrameLayout.LayoutParams(200, 200));
        try {
            InputStream istr = getContext().getAssets().open(mojid==0?"rts.jpg":"disney.jpg");
            iv.setImageDrawable(Drawable.createFromStream(istr, null));
            sv.addView(iv);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dodajPrograme() {
        int brprograma= MainActivity.getChannels().get(mojid).getPrograms().size();
            for(int k=0;k<brprograma;k++){
                FrameLayout f = new FrameLayout(this.getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(300,FrameLayout.LayoutParams.MATCH_PARENT);
                f.setClickable(true);
                f.setLayoutParams(params);
                f.setId(R.id.timelinefragment+mojid*brprograma+k);
                sv.addView(f);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.timelinefragment+mojid*brprograma+k, new ProgramFragment());
                ft.commit();
            }



    }

    private Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }


}