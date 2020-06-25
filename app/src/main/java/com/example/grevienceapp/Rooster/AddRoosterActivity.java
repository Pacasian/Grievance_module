package com.example.grevienceapp.Rooster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.grevienceapp.ConnectionClass;
import com.example.grevienceapp.LeaveModule.LeaveApplication;
import com.example.grevienceapp.MainActivity;
import com.example.grevienceapp.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddRoosterActivity extends AppCompatActivity {
    String SendClass;
    Button btn_OK_show,btn_next_show,btn_finish_show,btn_insert;
    Spinner spShow;
    TextView txtPf1,txtPf2,txtNameShow,txtCountShow;
    TextView txtD1,txtD2,txtD3,txtD4,txtD5,txtD6,txtD7;
    TextView[] listOfTV;
    private ArrayList<StationPF_Model> eachPfNumber;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //
    Connection con;
    String d1,d2,d3,d4,d5,d6,d7;
    String[] shift=new String[]{"Day","Night","Night Off","Rest"};
    String firstShiftValue;
    int currPf=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rooster1);
        SendClass=getIntent().getExtras().getString("classSend");
        Toast.makeText(this, SendClass, Toast.LENGTH_SHORT).show();
        spShow=findViewById(R.id.spinnerShift);
        txtPf1=findViewById(R.id.txt_pfNumber_show1);
        txtPf2=findViewById(R.id.txt_pf_show2);
        txtNameShow=findViewById(R.id.txt_name_show);
        txtCountShow=findViewById(R.id.txt_count_show1);

        txtD1=findViewById(R.id.txtD1);
        txtD2=findViewById(R.id.txtD2);
        txtD3=findViewById(R.id.txtD3);
        txtD4=findViewById(R.id.txtD4);
        txtD5=findViewById(R.id.txtD5);
        txtD6=findViewById(R.id.txtD6);
        txtD7=findViewById(R.id.txtD7);
        listOfTV=new TextView[]{txtD1,txtD2,txtD3,txtD4,txtD5,txtD6,txtD7};
        connectionClass = new ConnectionClass(); // Connection Class Initialization
        eachPfNumber = new ArrayList<StationPF_Model>(); // Arraylist Initialization
        con = new ConnectionClass().CONN();
        if (con != null) {
            Toast.makeText(AddRoosterActivity.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(AddRoosterActivity.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }
        GetPf getPf=new GetPf();
        getPf.execute("");
        btn_next_show=findViewById(R.id.btn_next_show);
        btn_OK_show=findViewById(R.id.btn_ok_shit);
        btn_finish_show=findViewById(R.id.btn_finish_show);
        btn_insert=findViewById(R.id.btn_insert_to_DB);
        btn_next_show.setEnabled(false);
        btn_insert.setEnabled(false);
        /**
         * The btn_ok_show help the first data to update into the days edittext
         */
        btn_OK_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_OK_show.setEnabled(false);
                String st = spShow.getSelectedItem().toString();
                if (st.equals(null) || st.equals("Shift :")) {
                    Toast.makeText(AddRoosterActivity.this, "Invalid Shift Type", Toast.LENGTH_SHORT).show();
                } else {
                    btn_insert.setEnabled(true);
                    firstShiftValue = st;
                   // Toast.makeText(AddRoosterActivity.this, st, Toast.LENGTH_SHORT).show();
                    setRoosterDays(st);

                }
            }
        });
        /**
         if (shift[0].equals(st)) {
         Toast.makeText(AddRoosterActivity.this, "wrong", Toast.LENGTH_SHORT).show();
         for(int i=0;i<7;i++){
         if(i%2!=0){
         listOfTV[i].setText(shift[1]);
         }else{

         listOfTV[i].setText(shift[0]);
         }
         }
         } else {
         String temp = shift[0];
         shift[0] = shift[1];
         shift[1] = temp;
         for(int i=0;i<7;i++){
         if(i%2!=0){
         listOfTV[i].setText(shift[1]);
         }else{

         listOfTV[i].setText(shift[0]);
         }
         }
         }
         ---------------------------------------------------------------
         if (shift[0].equals(firstShiftValue)) {
         String temp = shift[0];
         shift[0] = shift[1];
         shift[1] = temp;
         }

         ----------------------------------------------------------------
         for (int i = 0; i < 7; i++) {
         if (i % 2 != 0) {
         listOfTV[i].setText(shift[1]);
         } else {

         listOfTV[i].setText(shift[0]);
         }
         }




         */
        /**
         * btn_insert helps in extracting the data from the EditText and update it to the database
         */
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_next_show.setEnabled(true);
                btn_insert.setEnabled(false);
                d1=listOfTV[0].getText().toString();
                d2=listOfTV[1].getText().toString();
                d3=listOfTV[2].getText().toString();
                d4=listOfTV[3].getText().toString();
                d5=listOfTV[4].getText().toString();
                d6=listOfTV[5].getText().toString();
                d7=listOfTV[6].getText().toString();
                //Now create a variable for finding the pfNumber and then
                //for first iteration the currPf value will be 0
                //The increment operator will be initiated on click of btn_next_show button
                AddEachRooster addEachRooster=new AddEachRooster();
                addEachRooster.execute("");
            }
        });
        /**
         * First it is responsible for showing the next value fields and updating the timetable for next pfNumber
         */
        btn_next_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPf++;
                if (currPf<eachPfNumber.size()) {
                    btn_next_show.setEnabled(false);
                    btn_insert.setEnabled(true);
                    txtPf1.setText("PfNumber :" + eachPfNumber.get(currPf).id);
                    txtPf2.setText("PfNumber :" + eachPfNumber.get(currPf).id);
                    txtNameShow.setText("Name :" + eachPfNumber.get(currPf).name);
                    txtCountShow.setText("No. of Data added " + currPf + "");

                    String temp_firstName=null;
                    for (int i=0;i<shift.length;i++){
                        if (firstShiftValue.equals(shift[i])) {
                            Toast.makeText(AddRoosterActivity.this, "its happening", Toast.LENGTH_SHORT).show();
                            if (i == 3) {
                                setRoosterDays(shift[0]);
                                temp_firstName=shift[0];
                            } else {
                                setRoosterDays(shift[i + 1]);
                                temp_firstName=shift[i+1];
                            }
                        }
                    }
                    // Now storing the first shift array value to the firstShiftValue variable
                    firstShiftValue = temp_firstName;

                }else{
                    btn_next_show.setEnabled(false);
                    btn_insert.setEnabled(false);
                    btn_finish_show.setVisibility(View.VISIBLE);

                }
            }


});
        btn_finish_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(AddRoosterActivity.this,MainActivity.class));
                    finish();
                    }
                    });
    }
    private class GetPf extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(AddRoosterActivity.this, "Synchronising",
                    "RecyclerView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try
            {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null)
                {
                    success = false;
                }
                else {
                    // append the level segregation HERE --- need to do
                    String query = "SELECT pfNumber,name FROM eTable  WHERE currStationCode='"+ SendClass +"' ;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                eachPfNumber.add(new StationPF_Model(rs.getString("pfNumber"),rs.getString("name")));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            progress.dismiss();
            Toast.makeText(AddRoosterActivity.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    txtPf1.setText("PfNumber : "+eachPfNumber.get(0).id);
                    txtPf2.setText(("PfNumber : "+eachPfNumber.get(0).id));
                    txtNameShow.setText(("Name : "+eachPfNumber.get(0).name));
                    txtCountShow.setText(("No.of Data : "+"0"));

                } catch (Exception ex)
                {

                }

            }
        }
    }



    public class AddEachRooster extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(AddRoosterActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(AddRoosterActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                System.out.println("*********************");
                System.out.println("current pf: "+eachPfNumber.get(currPf).id);
                System.out.println("1 "+d1+" 2 "+d2+" 3 "+d3+" 4 "+d4+" 5 "+d5+" 6 "+d6+" 7 "+d7);

                System.out.println("*********************");
                String query="";
                //String query = "insert into "+SendClass+"_R_Table (pfNumber,Day1,Day2,Day3,Day4,Day5,Day6,Day7) values ('" + eachPfNumber.get(currPf).id + "','" + d1 + "','" + d2 + "','" + d3 + "','" + d4 + "','" + d5 + "','" + d6 + "','" + d7 + "') ;";

                Statement stmt = con.createStatement();


                stmt.executeQuery(query);
            }
            catch (SQLException se)
            {
                Log.e("ERROR", se.getMessage());
            }
            return "Done";
        }
    }
    public void setRoosterDays(String st){
        if (st.equals("Day")){
            listOfTV[0].setText("Day");
            listOfTV[1].setText("Night");
            listOfTV[2].setText("Night Off");
            listOfTV[3].setText("Rest");
            listOfTV[4].setText("Day");
            listOfTV[5].setText("Night");
            listOfTV[6].setText("Night Off");


        }
        else if (st.equals("Night")){

            listOfTV[0].setText("Night");
            listOfTV[1].setText("Night Off");
            listOfTV[2].setText("Rest");
            listOfTV[3].setText("Day");
            listOfTV[4].setText("Night");
            listOfTV[5].setText("Night Off");
            listOfTV[6].setText("Rest");
        }
        else if (st.equals("Night Off")){

            listOfTV[0].setText("Night Off");
            listOfTV[1].setText("Rest");
            listOfTV[2].setText("Day");
            listOfTV[3].setText("Night");
            listOfTV[4].setText("Night Off");
            listOfTV[5].setText("Rest");
            listOfTV[6].setText("Day");
        }
        else if (st.equals("Rest")){
            listOfTV[0].setText("Rest");
            listOfTV[1].setText("Day");
            listOfTV[2].setText("Night");
            listOfTV[3].setText("Night Off");
            listOfTV[4].setText("Rest");
            listOfTV[5].setText("Day");
            listOfTV[6].setText("Night");
        }
    }
}
/***


 System.out.println("-----------------"+"size of db"+eachPfNumber.size());
 System.out.println("-----------------"+"curr size"+eachPfNumber.get(j[0]).id);
 if (j[0] < eachPfNumber.size()-1) {
 txtPf1.setText("PfNumber :"+eachPfNumber.get(j[0]).id);
 txtPf2.setText("PfNumber :"+eachPfNumber.get(j[0]).id);
 txtNameShow.setText("Name :"+eachPfNumber.get(j[0]).name);
 txtCountShow.setText("No. of Data added "+j[0] + "");
 if (shift[0].equals(firstShiftValue)){
 String temp=shift[0];
 shift[0]=shift[1];
 shift[1]=temp;
 }
 firstShiftValue=shift[0];
 for (int i = 0; i < 7; i++) {
 if (i % 2 != 0) {
 listOfTV[i].setText(shift[1]);
 } else {
 listOfTV[i].setText(shift[0]);
 }
 }
 System.out.println(eachPfNumber.get(j[0]).id);
 AddEachRooster1 addEachRooster1=new AddEachRooster1();
 addEachRooster1.execute("");
 j[0]++;
 }else{
 btn_finish_show.setVisibility(View.VISIBLE);
 btn_OK_show.setEnabled(false);
 btn_next_show.setEnabled(false);
 }

 }
-----------------------------------------------------------------------------
 public void setRooster(){
 System.out.println("-----------------"+"size of db"+eachPfNumber.size());
 System.out.println("-----------------"+"curr size"+eachPfNumber.get(0).id);
 System.out.println("-----------------"+"curr size"+eachPfNumber.get(1).id);
 System.out.println("-----------------"+"curr size"+eachPfNumber.get(2).id);
 System.out.println("-----------------"+"curr size"+eachPfNumber.get(3).id);

 for(int i=0;i<7;i++){
 if(i%2!=0){
 listOfTV[i].setText(shift[1]);
 }else{

 listOfTV[i].setText(shift[0]);
 }
 }
 System.out.println(eachPfNumber.get(0).id);

 }

 **/