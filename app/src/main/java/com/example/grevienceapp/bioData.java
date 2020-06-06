package com.example.grevienceapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class bioData extends AppCompatActivity {
    UserDataRespository udr;
    String[] stationCode={
            "TPT", "KEY", "SLY", "DST", "DPI", "MAP", "BDY", "BQI", "LCR", "DSPT", "TNT", "KPPR",
            "MGSJ", "SA", "VRPD", "DC", "MVPM", "SGE", "ANU", "CV", "ED", "TPM", "PY", "IGR", "VZ", "UKL", "TPM", "VNJ",
            "SNO", "SUU", "IGU", "PLMD", "CBF", "CBE", "PTJ", "KAY", "MTP", "QLR", "HLG", "ONR", "WEL", "AVK", "KXT", "LOV",
            "UAM", "CVD", "PAS", "URL", "KMD", "PGR", "MPLM", "KRR", "VRQ", "MYU", "MMH", "LP", "KLT", "PLI", "PGN", "EL", "MTNL",
            "SAMT", "SXT", "MPLI", "ETP", "ATU", "CHSM", "PRV", "MKSP", "MALR", "RASP", "KLGN", "NMKL", "MONR", "VEI","PALM", "EDU", "OML", "MCRD", "MTDM"};
    String[] stationName={"TIRUPATTUR","KAGANKARAI","SAMALPATTI", "DASAMPATTI", "DODDAMPATTI", "MORAPPUR", "BUDDIREDIPATTI", "BOMMIDI", "LOKUR", "DANISHPET",
            "TINNAPATTI", "KARUPPUR", "MAGANESITE Jn.", "SALEM Jn.", "VIRAPANDYROAD", "MAGUDANCHAVADI","MAVELIPALAYAM","SANKARI DRUG", "ANANGUR", "CAUVERY",
            "ERODE Jn", "TOTTIPALAYAM", "PERUNDURAI", "INGUR", "VIZAYAMANGALAM", "UTHTHUKKULI", "TIRUPPUR", "VANJIPALAYAM", "SOMANUR", "SULUR ROAD", "IRUGUR",
            "PILAMEDU", "COIMBATORE NORTH", "COIMBATORE", "PODANUR Junction", "KARAIMADI", "METTUPALAYAM", "KALLAR", "HILGROVE", "COONOOR", "WELLINGTON", "ARAVANKADU",
            "KETTI", "LOVDALE","UDAGAMANDALAM","CHAVADIPALAYAM", "PASUR", "UNJALUR", "KODUMUDI", "PUGALUR","MURTHIPALAYM", "KARUR Jn.", "VIRARAKKIYAM", "MAYANUR",
            "MAHADHANAPURAM", "LALAPET", "KULITHALAI","PETTAVATHALAI", "PERUGAMANI", "ELAMANUR", "MUTHARASANALLUR", "SALEM MARKET", "SALEM TOWN", "MINNAMPALLI","ETTAPUR",
            "ATTUR", "CHINNASALEM", "PUKKIRAVARI", "MUKHASAPARUR", "MALLUR", "RASIPURAM", "KALANGANI", "NAMAKKAL", "MOHANUR", "VELLIYANAI", "PALAYAM", "ERIODU", "OMALUR Jn.",
            "MECHERIROAD", "METTUR DAM"};
    String[] tiCode={"TI/MAP", "TI/I/SA","TI/ED", "TI/TUP", "TI/CBE", "SS/ONR", "SS/UAM", "TI/ED", "TI/KRR", "TI/KLT", "TI/ATU", "TI/II/SA","TI/KRR", "TI/II/SA"};
    int[] tiCount={1,2,3,4,5,6,7,8,9,10,11,12,13,14};
    String[] designation ={"Station master" ,"Points man" ,"Section TI","Guard","sr.DOM","Admin"};
    int[] desLevel={2,1,4,1,4,5};
    TextView actionTittle;
    ImageView actionIcon;
    EditText bioName,bioDrive,bioBG,bioDOB,bioContact,bioEContact,bioQual,pfNumber,bioDOApp,bioDJS,bioPME,bioRC,bioAward;
    Spinner idSex,idState,idDesg,idSection;
    AutoCompleteTextView bioStation,bioWorkX;
    Button submit;
    Connection con;
    private RailwaySharedPreference sharedInfo;
    /**
     * Local Values but Globally initialised
     * @param savedInstanceState
     */
    String Name,IDrive,BG,DOB,Contact,EContact,Qual,stPfNumber,DOApp,DJS,PME,RC,Award,Sex,State,Desg,Section,Station,WorkX;
    String sCode,sTiCount;
    int empLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.NavGrey));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.Transparent));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        actionTittle=findViewById(R.id.actionTitle);
        actionTittle.setText("BioData");
        actionIcon=findViewById(R.id.actionIcon);
        actionIcon.setImageResource(R.drawable.bio);

        setContentView(R.layout.bio_data);
        /**
         * UI elements declaration Start
         *
         *
         */
         submit=findViewById(R.id.bioDataSubmit);

         bioName=findViewById(R.id.bioName);
        bioDrive=findViewById(R.id.bioDrive);
        bioBG=findViewById(R.id.bioBG);

        bioDOB=findViewById(R.id.bioDOB);

        bioContact=findViewById(R.id.bioContact);

        bioEContact=findViewById(R.id.bioeContact);

        bioQual=findViewById(R.id.bioQual);

        pfNumber=findViewById(R.id.pfNumber);

        bioDOApp=findViewById(R.id.bioDOApp);

        bioDJS=findViewById(R.id.bioDJS);

        bioPME=findViewById(R.id.bioPME);

        bioRC=findViewById(R.id.bioRC);

        bioAward=findViewById(R.id.bioAward);

        idSex=findViewById(R.id.idSex);

        idState=findViewById(R.id.idState);

        idDesg=findViewById(R.id.idDesg);

        idSection=findViewById(R.id.idSection);

        bioStation=findViewById(R.id.bioStation);

        bioWorkX=findViewById(R.id.bioWorkExp);

        /**
         * UI elements declaration End
         */

        // AutoCompleteText view for current station

        StringBuilder sbStation= new StringBuilder();
        sbStation.append("Input Value:").append("\n");
        for (String value: stationName)
        {
            sbStation.append(value).append(",");
        }
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stationName);
        bioStation.setAdapter(adapter1);
        bioStation.setThreshold(1);//start searching from 1 character
        bioStation.setAdapter(adapter1);

        // AutoCompleteText view for 6 months station

        StringBuilder sbWorkX= new StringBuilder();
        sbWorkX.append("Input Value:").append("\n");
        for (String value2: stationName)
        {
            sbWorkX.append(value2).append(",");
        }
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stationName);
        bioWorkX.setAdapter(adapter2);
        bioWorkX.setThreshold(1);//start searching from 1 character
        bioWorkX.setAdapter(adapter2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean status=validate();
                if (!status){
                   // Toast.makeText(bioData.this, status+"", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(bioData.this);
                    builder1.setTitle("Please fill all the entries...!!!");

                    builder1.setMessage("Are you sure,You filled to entries.?"+"\n"+"Check Again...");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

                else {


                    startActivity(new Intent(bioData.this, bioDataAdmin.class));
                }
                //System.out.println("----------------------------------------------------");
                //System.out.println(stationCode.length+" "+stationName.length+" "+tiCode.length+" "+designation.length);
                //System.out.println("----------------------------------------------------");
            }
        });
    }
    public Boolean validate(){
       // String Name,IDrive,BG,DOB,Contact,EContact,Qual,stPfNumber,DOApp,DJS,PME,RC,Award,Sex,State,Desg,Section,Station,WorkX;
        //String sCode,sTiCount;
        Boolean Biostatus=false;
        int ErrorCount=0;
        Name=bioName.getText().toString();
        IDrive=bioDrive.getText().toString();
        BG=bioBG.getText().toString();
        DOB=bioDOB.getText().toString();
        Contact=bioContact.getText().toString();
        EContact=bioEContact.getText().toString();
        Qual=bioQual.getText().toString();
        stPfNumber=pfNumber.getText().toString();
        DOApp=bioDOApp.getText().toString();
        DJS=bioDJS.getText().toString();
        PME=bioPME.getText().toString();
        RC=bioRC.getText().toString();
        Award=bioAward.getText().toString();

        Sex=idSex.getSelectedItem().toString();
        State=idState.getSelectedItem().toString();
        Desg=idDesg.getSelectedItem().toString();
        Section=idSection.getSelectedItem().toString();

        Station=bioStation.getText().toString();
        WorkX=bioWorkX.getText().toString();
        if ((Name.isEmpty())){
            ErrorCount++;
        }
       if( IDrive.isEmpty()){
           ErrorCount++;
       }
       if( BG.isEmpty()){
           ErrorCount++;
       }
       if( DOB.isEmpty()){
           ErrorCount++;
       }
       if( Contact.isEmpty()){
           ErrorCount++;
       }
       if( EContact.isEmpty()){
           ErrorCount++;
       }
       if( Qual.isEmpty()){
           ErrorCount++;
       }
       if( stPfNumber.isEmpty()){
           ErrorCount++;
       }
       if( DOApp.isEmpty()){
           ErrorCount++;
       }
       if( DJS.isEmpty()){
           ErrorCount++;
       }
       if( PME.isEmpty()){
           ErrorCount++;
       }
       if( RC.isEmpty()){
           ErrorCount++;
       }
       if( Award.isEmpty()  ){
           ErrorCount++;
       }
       if( Station.isEmpty()){
           ErrorCount++;
       }
       if( WorkX.isEmpty()){
                ErrorCount++;
            }
       if(Section.equals("Select the Section TI")){
           ErrorCount++;
       }
        if(Desg.equals("Select Your Designation")){
            ErrorCount++;
        }
        else
        {
            for(int i=0;i<stationName.length;i++){
                if(Station.equals(stationName[i])){
                    sCode=stationCode[i];
                    break;
                }
            }
            for (int i=0;i<tiCode.length;i++){
                if (Section.equals(tiCode[i])){
                    sTiCount=tiCount[i]+"";
                }
            }
            for (int i=0;i<designation.length;i++){
                if (Desg.equals(designation[i])){
                    empLevel=desLevel[i];
                }
            }
            UploadInfo uploadDB = new UploadInfo();
            uploadDB.execute("");

            if (con != null) {
                Toast.makeText(bioData.this, "Connection valid", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(bioData.this, "Connection invalid", Toast.LENGTH_SHORT).show();
            }

        }
        Biostatus= ErrorCount == 0;
        //Toast.makeText(this, Biostatus+"", Toast.LENGTH_SHORT).show();
        return Biostatus;
    }
    public class UploadInfo extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(bioData.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(bioData.this, "Login Successful", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            sharedInfo=RailwaySharedPreference.getInstance(getApplicationContext());

            sharedInfo.put("pfNumber",stPfNumber);
            sharedInfo.put("Name",Name);
            sharedInfo.put("sex",Sex);
            sharedInfo.put("designation",Desg);
            sharedInfo.put("station",Station);
            sharedInfo.put("StationCode",sCode);
            sharedInfo.put("Section",Section);
            sharedInfo.put("TiCount",sTiCount);
            sharedInfo.putInt("empLevel",empLevel);
            sharedInfo.put("Qual",Qual);
            sharedInfo.put("DOB",DOB);
            sharedInfo.put("BG",BG);
            sharedInfo.put("Contact",Contact);
            sharedInfo.put("EContact",EContact);
            sharedInfo.put("State",State);
            sharedInfo.put("WorkX",WorkX);
            sharedInfo.put("Award",Award);

            System.out.println("---------------------------------------------");
            System.out.println(stPfNumber +"\n" + Name+"\n" + IDrive+"\n" + Sex+"\n" + DOB +"\n" + BG +"\n" + Contact +"\n" + EContact +"\n" + State +"\n" +Qual+"\n" +Desg +"\n" + sCode+"\n" +WorkX+"\n" + Section +"\n" +DOApp+"\n"  + Award+"\n" +sTiCount+"\n" + Station+"\n" +empLevel);
            System.out.println("---------------------------------------------");
             try
            {

                con = new ConnectionClass().CONN();

                String query = "insert into eTable (pfNumber,name,profilePic,sex,dob,bloodGroup,contactInfo,eContactInfo,state,qual,designation,currStationCode,stationSixMonths,sectionTI,DateofApp,DoJoinStation,tiCount,currStationName,empLevel) values ("+stPfNumber+",'"+Name+"','"+IDrive+"','"+Sex+"','"+DOB+"','"+BG+"','"+Contact+"','"+EContact+"','"+State+"','"+Qual+"','"+Desg+"','"+sCode+"','"+WorkX+"','"+Section+"','"+DOApp+"','"+Award+"','"+sTiCount+"','"+Station+"','"+empLevel+ "') ;";
            Statement stmt = con.createStatement();

            stmt.executeQuery(query);

                //udr=new UserDataRespository(stPfNumber,Name,IDrive,Sex,DOB,BG,Contact,EContact,State,Qual,Desg,sCode,WorkX,Section,DOApp,Award,sTiCount,Station,empLevel);

            }
           catch (SQLException e) {
                e.printStackTrace();
            }


            return "Success";
        }
    }
}
/**
 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(bioData.this);
 // Setting Alert Dialog Title
 alertDialogBuilder.setTitle("Please fill all the entries...!!!");
 // Icon Of Alert Dialog
 //alertDialogBuilder.setIcon(R.drawable.alertdialog);
 // Setting Alert Dialog Message
 alertDialogBuilder.setMessage("Are you sure,You filled to entries.?"+"\n"+"Check Again...");
 alertDialogBuilder.setCancelable(false);


 //yes
 alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

@Override
public void onClick(DialogInterface arg0, int arg1) {
finish();
}
});
 //no
 alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
Toast.makeText(getApplicationContext(),"You clicked on No",Toast.LENGTH_SHORT).show();
}
});
 //Cancel
 alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
}
});



 */