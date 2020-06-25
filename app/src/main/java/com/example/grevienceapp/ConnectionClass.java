package com.example.grevienceapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionClass extends Activity {
    private RailwaySharedPreference sharedInfo;


    String ip="60.243.7.157";
    //String ip = "192.168.1.4";
    String Classes = "net.sourceforge.jtds.jdbc.Driver";
    String db = "testDatabase";
    String un = "test";
    String password = "test";
    @SuppressLint("NewApi")
    public Connection CONN() {
        try {
            sharedInfo = RailwaySharedPreference.getInstance(this);
            ip = sharedInfo.get("ip");
        }catch (NullPointerException e){
            System.out.println("Ex---------------------------------");
        }

        System.out.println("------------------------");
        System.out.println(ip);
        System.out.println("------------------------");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(Classes);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";" + "databaseName=" + db + ";user=" + un + ";password=" + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return conn;
    }
}