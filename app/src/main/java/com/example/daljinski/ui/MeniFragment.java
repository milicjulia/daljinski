package com.example.daljinski.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.daljinski.MainActivity;
import com.example.daljinski.R;
import com.example.daljinski.baza.OmiljeniEntity;
import com.example.daljinski.entiteti.Channel;
import com.example.daljinski.entiteti.Program;
import com.example.daljinski.entiteti.Translator;
import java.util.ArrayList;

public class MeniFragment extends Fragment implements RecognitionListener{
    private Button chUp, chDown, volUp, volDown, S;
    private static int volume = 50, channel = 1;
    private TextView txt1, txt2, returnedText;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private static final int REQUEST_INTERNET = 200;
    View view;

    public static int getChannel() {
        return channel;
    }

    public static void setChannel(int channel) {
        MeniFragment.channel = channel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meni, container, false);

        dodajZaSpeech();
        dodajKomponente();

        return view;
    }

    public void dodajZaSpeech() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
        }
        speech = SpeechRecognizer.createSpeechRecognizer(getActivity());
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

    }

    public void dodajKomponente() {
        chUp = (Button) view.findViewById(R.id.chup);
        chDown = (Button) view.findViewById(R.id.chdown);
        volUp = (Button) view.findViewById(R.id.volup);
        volDown = (Button) view.findViewById(R.id.voldown);
        S = (Button) view.findViewById(R.id.speechButton);
        txt1 = (TextView) view.findViewById(R.id.txt1);
        txt2 = (TextView) view.findViewById(R.id.txt2);
        returnedText = (TextView) view.findViewById(R.id.returnedText);
        txt1.setText(String.valueOf(volume));
        txt2.setText(String.valueOf(channel));

        S.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    speech.startListening(recognizerIntent);
                    S.setScaleX((float) 1.5);
                    S.setScaleY((float) 1.5);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    speech.stopListening();
                    S.setScaleX(1);
                    S.setScaleY(1);
                }
                return true;
            }
        });

        chDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                channelDown();
            }
        });
        chUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                channelUp();
            }
        });
        volDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volumeDown();
            }
        });
        volUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volumeUp();
            }
        });
    }



    public void channelDown() {
        if (channel != 1)
            channel -= 1;
        txt2.setText(String.valueOf(channel));
    }

    public void channelUp() {
        channel += 1;
        txt2.setText(String.valueOf(channel));
    }

    public void volumeDown() {
        if (volume <= 100 && volume >= 5)
            volume -= 5;
        txt1.setText(String.valueOf(volume));
    }

    public void volumeUp() {
        if (volume <= 95 && volume >= 0)
            volume += 5;
        txt1.setText(String.valueOf(volume));
    }


    public static void setOmiljen(int k, int p) {
        Program program = MainActivity.getChannels().get(k).getPrograms().get(p);
        if (MainActivity.getLikes().size() == 0) {
            for (int i = 0; i < program.getGenres().size(); i++)
                MainActivity.getLikes().add(new OmiljeniEntity(program.getGenres().get(i)));
        } else {
            for (int i = 0; i < program.getGenres().size(); i++) {
                boolean found = false;
                for (OmiljeniEntity element : MainActivity.getLikes()) {
                    if (element.getTip().compareToIgnoreCase(program.getGenres().get(i)) == 0) {
                        found = true;
                        break;
                    }
                    if (found == false) {
                        MainActivity.getLikes().add(new OmiljeniEntity(program.getGenres().get(i)));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_INTERNET) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECORD_AUDIO)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("This permission is important to record audio.")
                            .setTitle("Important permission required");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
                        }
                    });
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
                }
            }
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        //Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        //Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        //Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        returnedText.setText(errorMessage);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        //Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
       // Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        //Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (matches.get(0).compareToIgnoreCase("channel up") == 0) channelUp();
        else if (matches.get(0).compareToIgnoreCase("channel down") == 0) channelDown();
        else if (matches.get(0).compareToIgnoreCase("volume up") == 0) volumeUp();
        else if (matches.get(0).compareToIgnoreCase("volume down") == 0) volumeDown();
        else {
            try {
                String akcija = Translator.translate(matches.get(0));
                Program pretraga = null;
                for (Channel ch : MainActivity.getChannels()) {
                    for (Program p : ch.getPrograms()) {
                        if (p.getName().contains(akcija)) {
                            pretraga = p;
                            break;
                        } else if (p.getGenres().contains(akcija)) {
                            pretraga = p;
                            break;
                        } else if (p.getDescription().contains(akcija)) {
                            pretraga = p;
                            break;
                        }
                    }
                    if (pretraga != null) break;
                }
                if (pretraga != null) {
                    MeniFragment.setChannel(pretraga.getIdKanala());
                    Log.d("prikazuje ", String.valueOf(pretraga.getIdKanala()));Log.d("prikazuje ", pretraga.getName());Log.d("prikazuje ", pretraga.getDescription());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        //Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MeniFragment", "start");
        txt2.setText(String.valueOf(channel));

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MeniFragment", "resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MeniFragment", "pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MeniFragment", "stop");
        TimelineFragment.setId(0);
        ProgramFragment.setId(0);
    }



}