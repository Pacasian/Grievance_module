package com.example.grevienceapp.Greviance;
/**
 * The Java file for User Employee Grevience:   Client Level
 * Part 2 of Grevience
 *
 *
 */

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grevienceapp.DatabaseConnection;
import com.example.grevienceapp.R;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GrevienceUserEntry extends AppCompatActivity {
    TextView actionTittle;
    ImageView actionIcon;
    Spinner sType,sPriority,sSendTo;
    String stType,stPriority,stSendTo,stMatter;
    EditText eidMatter;
    Button btnSendTo;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.NavGrey) );
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.colorPrimary) );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        actionTittle=findViewById(R.id.actionTitle);
        actionTittle.setText("Grievances");
        actionIcon=findViewById(R.id.actionIcon);
        actionIcon.setImageResource(R.drawable.grievances);
        setContentView(R.layout.activity_grevience_user_entry);
        sType=findViewById(R.id.idType);
        //stType=sType.getSelectedItem().toString();

        sPriority=findViewById(R.id.idPriority);
        //stPriority=sPriority.getSelectedItem().toString();

        sSendTo=findViewById(R.id.idSendTo);
        //stSendTo=sSendTo.getSelectedItem().toString();

        eidMatter=findViewById(R.id.eidMatter);
        btnSendTo=findViewById(R.id.btnGreUserSend);
        btnSendTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDatabase AlertDB = new AlertDatabase();
                AlertDB.execute("");
            }
        });
        con = new DatabaseConnection().ConnectDB();
        if (con != null) {
            Toast.makeText(GrevienceUserEntry.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
else{
            Toast.makeText(GrevienceUserEntry.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }
    }
    public class AlertDatabase extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(GrevienceUserEntry.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(GrevienceUserEntry.this, "Login Successful", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            /** @SuppressLint("WrongThread") String sendWhat = edWhat.getText().toString();
             @SuppressLint("WrongThread") String sendWhere = "10250540";
             @SuppressLint("WrongThread") String sendMore = "Nothing";

             if (sendWhat.trim().equals("") || sendWhere.trim().equals(""))
             z = "Please enter Username and Password";
             else {
             try {
             con = DatabaseConnection.ConnectDB(); // Connect to database
             if (con == null) {
             z = "Check Your Internet Access!";
             } else {
             String query = "insert into AlertInfo (what,location,additional) values ('" + sendWhat + "','" + sendWhere + "','" + sendMore + "');";
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             Toast.makeText(alert.this, rs+"", Toast.LENGTH_SHORT).show();
             if (rs.next()) {
             z = "Login successful";
             Toast.makeText(alert.this, "Successful", Toast.LENGTH_SHORT).show();
             isSuccess = true;
             con.close();
             } else {
             z = "Invalid Credentials!";
             Toast.makeText(alert.this, "Failed", Toast.LENGTH_SHORT).show();
             isSuccess = false;
             }
             }
             } catch (Exception ex) {
             isSuccess = false;
             z = ex.getMessage();
             }
             }
             return z;

             } **/

            try
            {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                stType=sType.getSelectedItem().toString();
                stPriority=sPriority.getSelectedItem().toString();
                stSendTo=sSendTo.getSelectedItem().toString();
                String Matter= eidMatter.getText().toString();
                //java.sql.Date sqlDate = new java.sql.Date(currentDateandTime);
                //@SuppressLint("WrongThread") String sendMore = edAdd.getText().toString();
                /**

                 *    System.out.println(currentDateandTime);
                System.out.println(stMatter);
                System.out.println(stPriority);
                System.out.println(stType);
                System.out.println(stSendTo);
                 *
                 */
                String query = "insert into GTable (type,priority,timeDate,matter,sendTo) values ('" + stType + "','" + stPriority + "','" + currentDateandTime + "','" + Matter + "','" + stSendTo + "')";

                Statement stmt = con.createStatement();


                stmt.executeQuery(query);
            }
            catch (SQLException se)
            {
                Log.e("ERROR", se.getMessage());
            }
            return "suuss";
        }
    }
}
