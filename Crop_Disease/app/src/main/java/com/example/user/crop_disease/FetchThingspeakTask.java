package com.example.user.crop_disease;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

class FetchThingspeakTask extends AsyncTask<Void, Void, Void> {
    // static final String TAG = "UsingThingspeakAPI";
    private static final String THINGSPEAK_CHANNEL_ID = "000000"; //Enter 6 digit channel id here
    private static final String THINGSPEAK_API_KEY = "api_key"; //GARBAGE KEY
    private static final String THINGSPEAK_API_KEY_STRING = "AAAAAAAAAAAAAAAA"; //Enter 16 digit hexadecimal API key
    /* Be sure to use the correct fields for your own app*/
    private static final String THINGSPEAK_FIELD1 = "field1";
    private static final String THINGSPEAK_FIELD2 = "field2";
    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update?";
    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
    private static final String THINGSPEAK_FEEDS_LAST = "/feeds/.json?";

    String diseasedata,line,curedata,usage,field;
    String data1=" ";
    String data2=" ";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL( "https://api.thingspeak.com/channels/000000/feeds.json?api_key=AAAAAAAAAAAAAAAA&results=2"); //Replace cannel id and API key
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer=new StringBuffer();
            while((line=bufferedReader.readLine())!=null)
            {
                buffer.append(line);
            }

            field=buffer.toString();
            JSONObject jsonObject=new JSONObject(field);
            JSONArray jsonArray=jsonObject.getJSONArray("feeds");
                JSONObject jsonObject1 = jsonArray.getJSONObject(1);
                diseasedata = jsonObject1.getString("field1");
               // curedata = jsonObject1.getString("field2");

            Map< String,String> hm =
                    new HashMap< String,String>();
            hm.put("Tomato_Bacterial_spot","To avoid bacterial spot, cultivators should buy certified disease-free tomato seeds and use sterilized soil or a mix that is commercially rendered. If it is not possible to acquire disease-free tomato seeds, your seeds should be submerged for one minute in 1.3% sodium hypochlorite, which helps eliminate bacteria on their surface." );
            hm.put("Tomato_Early_blight","Make sure to disinfect your pruning shears (one part bleach to 4 parts water) after each cut.\n" +
                    "Keep the soil under plants clean and free of garden debris. \n" +
                    "Add a layer of organic compost to prevent the spores from splashing back up onto vegetation.\n" +
                    "Drip irrigation and soaker hoses can be used to help keep the foliage dry.\n");
            hm.put("Tomato_Late_blight","Plant resistant cultivars when available.\n" +
                    "Remove volunteers from the garden prior to planting and space plants far enough apart to allow for plenty of air circulation.\n" +
                    "Water in the early morning hours, or use soaker hoses, to give plants time to dry out during the day — avoid overhead irrigation.\n" );
            hm.put("Tomato_Leaf_Mold","When you notice the infected areas, the first thing you can do is let the plants air out. If they are being grown in a green house, air exposure is a must, because the humidity that the fungus needs to survive is dried up. Another option for treatment is fungacide sprays. When using these sprays, be sure to cover each part of the plant that is above ground, paying special attention to the underside of the leaf." );
            hm.put("Tomato_Septoria_leaf_spot","Once blight is present and progresses, it becomes more resistant to biofungicide and fungicide. Treat it as soon as possible and on a schedule.\n" +
                    "Organic fungicides. Treat organically with copper spray, which you can purchase online, at the hardware store, or home improvement center." );
            hm.put("Tomato_Spider_mites_Two_spotted_spider_mite","Use the Bug Blaster to wash plants with a strong stream of water and reduce pest numbers.\n" +
                    "Commercially available beneficial insects, such as ladybugs, lacewing and predatory mites are important natural enemies. For best results, make releases when pest levels are low to medium.\n" );
            hm.put("Tomato__Target_Spot","A timely fungicide application prior to infection is the best way to control the disease. Fungicide provides excellent control of target spot in tomatoes and other fruiting vegetables." );
            hm.put("Tomato__Tomato_YellowLeaf__Curl_Virus","If symptomatic plants have no obvious whiteflies on the lower leaf surface, these plants can be cut from the garden and BURIED in the compost.\n" +
                    "Inspect plants for whitefly infestations two times per week. If whiteflies are beginning to appear, spray with azadirachtin (Neem), pyrethrin or insecticidal soap." );
            hm.put("Tomato__Tomato_mosaic_virus","The only treatment is prevention. No chemical products are available to cure or protect plants. The best factor in controlling and reducing infection is to practice sanitation. Remove any infected plants, including the roots. Remove Also, discard any plants near those affected." );
            hm.put("Pepper__bell___Bacterial_spot","Apply sulfur sprays or copper-based fungicides weekly at first sign of disease to prevent its spread. These organic fungicides will not kill leaf spot, but prevent the spores from germinating. Safely treat most fungal and bacterial diseases with SERENADE Garden." );
            hm.put("Potato___healthy","The plant is healthy." );
            hm.put("Potato___Early_blight","Prune or stake plants to improve air circulation and reduce fungal problems. Make sure to disinfect your pruning shears (one part bleach to 4 parts water) after each cut. Keep the soil under plants clean and free of garden debris. Drip irrigation and soaker hoses can be used to help keep the foliage dry." );
            hm.put("Potato___Late_blight","Apply a copper based fungicide (2 oz/ gallon of water) every 7 days or less, following heavy rain or when the amount of disease is increasing rapidly. If possible, time applications so that at least 12 hours of dry weather follows application." );
            hm.put("Pepper__bell___healthy","The plant is healthy" );

            // Returns Set view
            Set< Map.Entry< String,String> > st = hm.entrySet();

            for (Map.Entry< String,String> me:st)
            {
                if(diseasedata.equals(me.getKey()))
                curedata = me.getValue();
            }

        } catch (IOException e) {
            e.printStackTrace();
            field=e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.Disease.setText(diseasedata);
        MainActivity.Cure.setText(curedata);

    }
}
