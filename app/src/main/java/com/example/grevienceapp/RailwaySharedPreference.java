package com.example.grevienceapp;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
public class RailwaySharedPreference { //Did you remember to vote up my example?
        private static RailwaySharedPreference store;
        private SharedPreferences SP;
        private static String filename="Keys";

        private RailwaySharedPreference(Context context) {
            SP = context.getApplicationContext().getSharedPreferences(filename,0);
        }

        public static RailwaySharedPreference getInstance(Context context) {
            if (store == null) {
                Log.v("RailwaySharedPreference","NEW STORE");
                store = new RailwaySharedPreference(context);
            }
            return store;
        }


        public void put(String key, String value) {//Log.v("RailwaySharedPreference","PUT "+key+" "+value);
            Editor editor = SP.edit();
            editor.putString(key, value);
            editor.commit(); // Stop everything and do an immediate save!
            // editor.apply();//Keep going and save when you are not busy - Available only in APIs 9 and above.  This is the preferred way of saving.
        }

        public String get(String key) {//Log.v("RailwaySharedPreference","GET from "+key);
            return SP.getString(key, null);

        }

        public int getInt(String key) {//Log.v("RailwaySharedPreference","GET INT from "+key);
            return SP.getInt(key, 0);
        }

        public void putInt(String key, int num) {//Log.v("RailwaySharedPreference","PUT INT "+key+" "+String.valueOf(num));
            Editor editor = SP.edit();

            editor.putInt(key, num);
            editor.commit();
        }


        public void clear(){ // Delete all shared preferences
            Editor editor = SP.edit();

            editor.clear();
            editor.commit();
        }

        public void remove(){ // Delete only the shared preference that you want
            Editor editor = SP.edit();

            editor.remove(filename);
            editor.commit();
        }
    }

