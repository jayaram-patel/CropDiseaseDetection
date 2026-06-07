package com.example.user.crop_disease;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SendThingspeakTask extends AsyncTask<Void, Void, Void> {
    private static final String THINGSPEAK_CHANNEL_ID = "000000";
    private static final String THINGSPEAK_API_KEY = "api_key"; //GARBAGE KEY
    private static final String THINGSPEAK_API_KEY_STRING = "BBBBBBBBBBBBBBBB"; //Enter your own api key
    /* Be sure to use the correct fields for your own app*/
    private static final String THINGSPEAK_FIELD3 = "field3";

    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update?";
    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
    private static final String THINGSPEAK_FEEDS_LAST = "/feeds/.json?";

    String diseasedata,line,curedata,usage;
    int field;
    String data1=" ";
    String data2=" ";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL( "https://api.thingspeak.com/update?api_key=BBBBBBBBBBBBBBBB&field3=0");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).execute();


        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (JSONException e) {
            e.printStackTrace();
        }*/

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //MainActivity.Disease.setText(diseasedata);
        //MainActivity.Cure.setText(curedata);

    }
}
