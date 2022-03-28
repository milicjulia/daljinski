package com.example.daljinski;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ProgramFragment extends Fragment {
    private TextView vreme, naziv;
    private Button gledaj;
    private static int id=0;
    private int myid=id;
View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_meni, container, false);
        ViewGroup vg=(ViewGroup) view;
        getActivity().setContentView(R.layout.fragment_program);

        TextView nazivt=new TextView(this.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        nazivt.setLayoutParams(params);
        nazivt.setText(MainActivity.getChannels().get(0).getPrograms().get(myid).getName());

        TextView vremet=new TextView(this.getContext());
        vremet.setLayoutParams(params);
        vremet.setText(Long.toString(MainActivity.getChannels().get(0).getPrograms().get(myid).getStartDate()));

        gledaj=(Button) new Button(this.getContext());
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(92,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        gledaj.setLayoutParams(params2);
        gledaj.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                MeniFragment.setChannel(myid);
            }
        });

        vg.addView(nazivt);
        vg.addView(vremet);
        vg.addView(gledaj);
        return vg;

    }

    public void sendMessageToSTB(String msg) {
        if (MainActivity.getServiceConnection().isBound()) {
            new STBCommunicationTask((STBCommunicationTask.STBTaskListenner) this, MainActivity.getServiceConnection().getSTBDriver()).execute(STBCommunication.REQUEST_COMMAND, msg);
        }
    }
    public void sendMessageToSTB(String msg, String extra) {
        if (MainActivity.getServiceConnection().isBound()) {
            new STBCommunicationTask((STBCommunicationTask.STBTaskListenner) this, MainActivity.getServiceConnection().getSTBDriver()).execute(STBCommunication.REQUEST_COMMAND, msg, extra);
        }
    }
}