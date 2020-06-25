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

public class AddRoosterActivity1 extends AppCompatActivity {
    String SendClass;
    Button btn_OK_show,btn_next_show,btn_finish_show;
    Spinner spShow;
    TextView txtPf1,txtPf2,txtNameShow,txtCountShow;
    TextView txtD1,txtD2,txtD3,txtD4,txtD5,txtD6,txtD7;
    TextView[] listOfTV;
    String d1,d2,d3,d4,d5,d6,d7;
    private ArrayList<StationPF_Model> eachPfNumber;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //
    Connection con;
    String[] shift=new String[]{"Day","Night"};
    String firstShiftValue;
     int j = 0;
     int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rooster);
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
            Toast.makeText(AddRoosterActivity1.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(AddRoosterActivity1.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }
        GetPf getPf=new GetPf();
        getPf.execute("");
        btn_next_show=findViewById(R.id.btn_next_show);
        btn_OK_show=findViewById(R.id.btn_ok_shit);
        btn_finish_show=findViewById(R.id.btn_finish_show);
        btn_next_show.setEnabled(false);


        btn_OK_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st = spShow.getSelectedItem().toString();
                if (st.equals(null) || st.equals("Shift :")) {
                    Toast.makeText(AddRoosterActivity1.this, "Invalid Shift Type", Toast.LENGTH_SHORT).show();
                } else {
                    firstShiftValue = st;
                    Toast.makeText(AddRoosterActivity1.this, st, Toast.LENGTH_SHORT).show();
                    if (shift[0].equals(st)) {
                        Toast.makeText(AddRoosterActivity1.this, "wrong", Toast.LENGTH_SHORT).show();
                        setRooster();
                    } else {
                        String temp = shift[0];
                        shift[0] = shift[1];
                        shift[1] = temp;
                        setRooster();
                    }
                    btn_next_show.setEnabled(true);
                    btn_OK_show.setEnabled(false);
                }
            }
        });
        btn_next_show.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                i=j;
                //System.out.println("-----------------"+"size of db"+eachPfNumber.size());
                //System.out.println("-----------------"+"curr size"+eachPfNumber.get(j).id);
                //System.out.println("-----------------"+firstShiftValue);



                setTimeTable();
                setRooster();

                if (shift[0].equals(firstShiftValue)){
                    String temp=shift[0];
                    shift[0]=shift[1];
                    shift[1]=temp;
                }
                firstShiftValue=shift[0];

                if (j < eachPfNumber.size()) {
                    txtPf1.setText("PfNumber :"+eachPfNumber.get(j).id);
                    txtPf2.setText("PfNumber :"+eachPfNumber.get(j).id);
                    txtNameShow.setText("Name :"+eachPfNumber.get(j).name);
                    txtCountShow.setText("No. of Data added "+j + "");




                }else{
                    btn_finish_show.setVisibility(View.VISIBLE);
                    btn_OK_show.setEnabled(false);
                    btn_next_show.setEnabled(false);
                }

                /*
                  insert the previous record into the database

                System.out.println("2*********************");
                System.out.println("the pf number 1 : "+eachPfNumber.get(j).id);
                System.out.println(listOfTV[0].getText()+" "+listOfTV[1].getText()+" "+listOfTV[2].getText()+" "+listOfTV[3].getText()+" "+listOfTV[4].getText()+" "+listOfTV[5].getText()+" "+listOfTV[6].getText());

                System.out.println("*********************");


                AddEachRooster1 addEachRooster1=new AddEachRooster1();
                addEachRooster1.execute("");

                for (int i = 0; i < 7; i++) {
                    if (i % 2 != 0) {
                        listOfTV[i].setText(shift[1]);
                    } else {
                        listOfTV[i].setText(shift[0]);
                    }
                }

                  end -- insert the previous record into the database
                 */


            }
        });
        btn_finish_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddRoosterActivity1.this,MainActivity.class));
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
            progress = ProgressDialog.show(AddRoosterActivity1.this, "Synchronising",
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
            Toast.makeText(AddRoosterActivity1.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    txtPf1.setText("PfNumber : "+eachPfNumber.get(0).id);
                    txtPf2.setText(("PfNumber : "+eachPfNumber.get(0).id));
                    txtNameShow.setText(("Name : "+eachPfNumber.get(0).name));




                } catch (Exception ex)
                {

                }

            }
        }
    }

    public void setRooster(){

        for(int i=0;i<7;i++){
            if(i%2!=0){
                listOfTV[i].setText(shift[1]);
            }else{

                listOfTV[i].setText(shift[0]);
            }

        }
        AddEachRooster1 addEachRooster1=new AddEachRooster1();
        addEachRooster1.execute("");



    }
    public class AddEachRooster1 extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(AddRoosterActivity1.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(AddRoosterActivity1.this, "Login Successful", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                /**
                 * check why the j value is incrementing automatically
                 * that's the reason for implementing jk
                 */
                int i=j;
                j++;


                System.out.println("2*********************");
                System.out.println(i);
                //System.out.println("the pf number 2 : "+eachPfNumber.get(j).id);
                System.out.println(d1+" "+d2+" "+d3+" "+d4+" "+d5+" "+d6+" "+d7);

                System.out.println("*********************");

                /**


                 System.out.println(stType);
                 System.out.println(stSendTo);
                 *
                 */
                /**
                 * Sort the


                String query = "insert into "+SendClass+"_R_Table (pfNumber,Day1,Day2,Day3,Day4,Day5,Day6,Day7) values ('" + eachPfNumber.get(i).id + "','" + d1 + "','" + d2 + "','" + d3 + "','" + d4 + "','" + d5 + "','" + d6 + "','" + d7 + "') ;";
                 */
                Statement stmt = con.createStatement();


                stmt.executeQuery("");


            }
            catch (SQLException se)
            {
                Log.e("ERROR", se.getMessage());
            }
            return "Done";
        }
    }
    public void setTimeTable(){

    }

}
