package com.example.grevienceapp.Greviance;
/**
 * This page must be removed during the integration of the project
 * This page will be replaced with automatic user level auth
 * IMPORTANT
 * ONLY FOR DEVELOPMENT
 *  String query = "insert into empTable ( pfNumber,name,profilePic,sex,dob,bloodGroup,contactInfo,eContactInfo,state,qual,designation,currStationCode,stationSixMonths,sectionTI,DateofApp,DoJoinStation,lastPME,lastRCDate,awards,tiCount,currStationName,empLevel) values ("+ stPfNumber + ",'" + Name + "','" + IDrive + "','" + Sex + "','" + DOB + "','"+ BG +"', '"+ Contact +"', '"+ EContact + "','"+ State +"' ,'"+Qual+"' ,'"+Desg +"' ,'"+ sCode+"','"+WorkX+"' ,'"+ Section +"','"+DOApp+"' ,'"+DJS+"' ,'"+PME+"','"+RC+"' ,'"+ Award+"' ,"+sTiCount+" ,'"+ Station+"',"+empLevel+") ;";
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.grevienceapp.R;

public class G_Admin_LevelAuth extends AppCompatActivity {
    Button btnLevel;
    Spinner spinner2;
    String AdminLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g__admin__level_auth);
        spinner2=findViewById(R.id.idLevel);

        btnLevel=findViewById(R.id.btnAdminLevel);
        btnLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(G_Admin_LevelAuth.this,GrevienceListView.class);
                AdminLevel=spinner2.getSelectedItem().toString();
                intent.putExtra("level",AdminLevel);
                startActivity(intent);

            }
        });
    }
}