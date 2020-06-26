package com.example.grevienceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=3000;
    private RailwaySharedPreference sharedInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedInfo=RailwaySharedPreference.getInstance(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                System.out.println("************************************");
                System.out.println("Cureent adta: "+currentDateandTime);
                System.out.println("PME adta: "+sharedInfo.get("PME"));
                 new JsonTask().execute("https://hits-rail.herokuapp.com/static/data.json");

                Intent i=new Intent(SplashScreen.this, MainActivity.class);
                if (currentDateandTime.equals(sharedInfo.get("PME"))){
                    i.putExtra("PME_Alert","pme_alert");
                    sharedInfo.put("PMECount","1");
                }
                startActivity(i);


                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            //txtJson.setText(result);
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                String aJsonString = json.getString("ip");
                System.out.println("-----------------------");
                System.out.println(aJsonString);
                sharedInfo=RailwaySharedPreference.getInstance(getApplicationContext());
                sharedInfo.put("ip",aJsonString);
                System.out.println("IP : "+sharedInfo.get("ip"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}