package com.example.daljinski.entiteti;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.daljinski.MainActivity;
import com.example.daljinski.ui.MeniFragment;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;


public class Translator {
  String akcija="";
  String input;

  public Translator(String i){
    input=i;
    TranslateAPI translateAPI = new TranslateAPI(
            Language.ENGLISH,   //Source Language
            Language.RUSSIAN,         //Target Language
            input);
    translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
      @Override
      public void onSuccess(String translatedText) {
        Log.d("eee", "onSuccess: "+translatedText);
        akcija=translatedText;
        Log.d("EEEE2",akcija);
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

          try {
            MainActivity.mConnectedThread.queue.put(5 * 10 + pretraga.getIdKanala());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          //    Toast.makeText(getContext(), String.valueOf(pretraga.getIdKanala())+"  "+ pretraga.getName(),Toast.LENGTH_LONG).show();
          Log.d("EEEE3",pretraga.getIdKanala()+"  "+ pretraga.getName());

        }
      }

      @Override
      public void onFailure(String ErrorText) {
        Log.d("eee", "onFailure: "+ErrorText);
      }
    });

  }




}
/*

  private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
  private static final String CLIENT_SECRET = "PUBLIC_SECRET";
  private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
  private static String fromLang = "en";
  private static String toLang = "ru";


  public static String translate(String text) throws Exception {
    // TODO: Should have used a 3rd party library to make a JSON string from an object
    String jsonPayload = new StringBuilder()
      .append("{")
      .append("\"fromLang\":\"")
      .append(fromLang)
      .append("\",")
      .append("\"toLang\":\"")
      .append(toLang)
      .append("\",")
      .append("\"text\":\"")
      .append(text)
      .append("\"")
      .append("}")
      .toString();

    URL url = new URL(ENDPOINT);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(jsonPayload.getBytes());
    os.flush();
    os.close();

    int statusCode = conn.getResponseCode();
    //System.out.println("Status Code: " + statusCode);
    BufferedReader br = new BufferedReader(new InputStreamReader(
        (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
      ));
    String output="";
    int i=0;
   /* while ((output = br.readLine()) != null) {
        Log.d("line"+i,output);
        i++;
    }
    output = br.readLine();
    conn.disconnect();
    Log.d("prevod",output);
    return output;
  }*/

