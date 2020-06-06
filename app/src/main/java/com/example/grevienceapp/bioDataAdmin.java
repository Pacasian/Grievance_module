package com.example.grevienceapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class bioDataAdmin extends AppCompatActivity {
    TextView actionTittle;
    ImageView actionIcon;
    private RailwaySharedPreference sharedInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.NavGrey) );
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.Transparent) );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        actionTittle=findViewById(R.id.actionTitle);
        actionTittle.setText("BioData");
        actionIcon=findViewById(R.id.actionIcon);
        actionIcon.setImageResource(R.drawable.bio);
        setContentView(R.layout.biodata_admin);
        sharedInfo=RailwaySharedPreference.getInstance(getApplicationContext());
        String string = sharedInfo.get("pfNumber");
        String string1 = sharedInfo.get("Name");
        String string2 = sharedInfo.get("sex");
        String string3 = sharedInfo.get("designation");
        System.out.println(string+"\n"+string1+"\n"+string2+"\n"+string3);

        //
    }
}
   // insert into eTable (pfNumber,name,profilePic,sex,dob,bloodGroup,contactInfo,eContactInfo,state,qual,designation,currStationCode,stationSixMonths,sectionTI,DateofApp,awards,tiCount,currStationName,empLevel) values (stpfNumber,Name,IDrive,Sex,DOB,BG,Contact,EContact,State,Qual,Desg,sCode,WorkX,Section,DOApp,Award,sTiCount,Station,empLevel')
//(stPfNumber,Name,IDrive,Sex,DOB,BG,Contact,EContact,State,Qual,Desg,sCode,WorkX,Section,DOApp,Award,sTiCount,Station,empLevel);
//(Eid,name, IDrive,BG,DOB,Contact,EContact,Qual,stPfNumber,DOApp,Award,Sex,State,Desg,Section,Station,WorkX) {