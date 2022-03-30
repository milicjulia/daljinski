package com.example.daljinski;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.Image;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class TimelineFragment extends Fragment {
    private ImageView channelPicture;
    View view;
    private LinearLayout sv, timeline;
    private HorizontalScrollView skrol;
    private static int id = 0;
    int mojid = id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        id++;
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        skrol=(HorizontalScrollView) view.findViewById(R.id.skrol);
        sv=(LinearLayout) view.findViewById(R.id.kanalisvitimeline);
        timeline=(LinearLayout) view.findViewById(R.id.timeline);
        dodajSliku();
        dodajPrograme();
        Picasso.get().setLoggingEnabled(true);
        return view;

    }

    public void dodajSliku() {
        ImageView iv = new ImageView(this.getContext());
        iv.setLayoutParams(new FrameLayout.LayoutParams(90, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        try {
            InputStream istr = getContext().getAssets().open(mojid==0?"rts.jpg":"disney.jpg");
            iv.setImageDrawable(Drawable.createFromStream(istr, null));
            sv.addView(iv);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dodajPrograme() {
        int brprograma=MainActivity.getChannels().get(mojid).getPrograms().size();
            for(int k=0;k<brprograma;k++){
                FrameLayout f = new FrameLayout(this.getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(300,FrameLayout.LayoutParams.MATCH_PARENT , Gravity.CENTER);
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