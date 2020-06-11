package com.example.grevienceapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class bioDataAdmin extends AppCompatActivity {
    TextView actionTittle;
    ImageView actionIcon;
    TextView name,sex,pfnumber,designation,station,section,qual,dob,bg,contact,Econtact,state,workx,award;
    Button btnR,btnOK;
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

        name=findViewById(R.id.bioName);
        sex=findViewById(R.id.idSex);
        state=findViewById(R.id.idState);
        designation=findViewById(R.id.idDesg);
        section=findViewById(R.id.idSection);
        workx=findViewById(R.id.bioWorkExp);
        station=findViewById(R.id.bioStation);
        award=findViewById(R.id.bioAward);
        pfnumber=findViewById(R.id.pfNumber);
        qual=findViewById(R.id.bioQual);
        Econtact=findViewById(R.id.bioeContact);
        contact=findViewById(R.id.bioContact);
        dob=findViewById(R.id.bioDOB);
        bg=findViewById(R.id.bioBG);
        btnR=findViewById(R.id.btnReject);
        btnOK=findViewById(R.id.btnOK);


        sharedInfo=RailwaySharedPreference.getInstance(getApplicationContext());

        String string = sharedInfo.get("pfNumber");
        String string1 = sharedInfo.get("Name");
        String string2 = sharedInfo.get("sex");
        String string3 = sharedInfo.get("designation");
        String string4 = sharedInfo.get("Qual");
        String string5 = sharedInfo.get("DOB");
        String string6 = sharedInfo.get("BG");
        String string7 =  sharedInfo.get("Contact");
        String string8 = sharedInfo.get("station");
        String string9 = sharedInfo.get("EContact");
        String string10 = sharedInfo.get("Section");
        String string11 = sharedInfo.get("State");
        String string12 = sharedInfo.get("WorkX");
        String string13 = sharedInfo.get("Award");


        System.out.println(string+"\n"+string1+"\n"+string2+"\n"+string3);
        name.setText(string1);
        pfnumber.setText(string);
        sex.setText(string2);
        designation.setText(string3);
        qual.setText(string4);
        dob.setText(string5);
        bg.setText(string6);
        contact.setText(string7);
        station.setText(string8);
        Econtact.setText(string9);
        section.setText(string10);
        state.setText(string11);
        workx.setText(string12);
        award.setText(string13);
btnOK.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(bioDataAdmin.this, "Approved", Toast.LENGTH_SHORT).show();
    }
});
btnR.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(bioDataAdmin.this, "Reject request need to be done!.", Toast.LENGTH_SHORT).show();
    }
});
        //
    }
}
   // insert into eTable (pfNumber,name,profilePic,sex,dob,bloodGroup,contactInfo,eContactInfo,state,qual,designation,currStationCode,stationSixMonths,sectionTI,DateofApp,awards,tiCount,currStationName,empLevel) values (stpfNumber,Name,IDrive,Sex,DOB,BG,Contact,EContact,State,Qual,Desg,sCode,WorkX,Section,DOApp,Award,sTiCount,Station,empLevel')
//(stPfNumber,Name,IDrive,Sex,DOB,BG,Contact,EContact,State,Qual,Desg,sCode,WorkX,Section,DOApp,Award,sTiCount,Station,empLevel);
//(Eid,name, IDrive,BG,DOB,Contact,EContact,Qual,stPfNumber,DOApp,Award,Sex,State,Desg,Section,Station,WorkX) {