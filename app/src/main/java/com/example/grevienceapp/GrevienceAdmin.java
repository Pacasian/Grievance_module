package com.example.grevienceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static android.widget.Toast.LENGTH_LONG;

public class GrevienceAdmin extends AppCompatActivity {
String idNumber;
    Connection con;
    String[] myList;
TextView txtsender,txtType,txtPrority,txtMatterExplain;
Button btnApprove,btnNot,btnResponseWhat,btnResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grevience_admin);
        idNumber = getIntent().getExtras().getString("idNumber").trim();
        Toast.makeText(this, idNumber, LENGTH_LONG).show();
        txtsender=findViewById(R.id.idFrom);
        txtPrority=findViewById(R.id.idAdminPriority);
        txtType=findViewById(R.id.idAdminType);
        txtMatterExplain=findViewById(R.id.idMatterExplain);
        //btnApprove=findViewById(R.id.btnApprove);
        con=DatabaseConnection.ConnectDB();
        if(con!=null)
        {

        }
        // Setting up the function when button login is clicked
        CheckLogin checkLogin = new CheckLogin();// ShowAdmin.this is the Asynctask, which is used to process in background to reduce load on app process
        checkLogin.execute("");

    }
    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String r)
        {
            Toast.makeText(GrevienceAdmin.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(GrevienceAdmin.this , "Login Successful" , LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            // @SuppressLint("WrongThread") String usernam = userTv.getText().toString();
            //@SuppressLint("WrongThread") String passwordd = passwordTv.getText().toString();

            try {
                con = DatabaseConnection.ConnectDB(); // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "SELECT * FROM GTable  WHERE id="+ idNumber +";";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    /**  while (rs.next()) {
                     edInfo.setText("hai");
                     String col1 = rs.getString("what");
                     String col2 = rs.getString("location");
                     String col3 = rs.getString("additional");
                     edInfo.setText(col1);
                     z = "Login successful";
                     }

                     rs.getString("what")

                     if (rs.next()) {
                     int i=1;
                     while (i<=2) {
                     eidWhat.setText(rs.getString("what"));
                     eidWhere.setText(rs.getString("ID"));
                     }
                     z = "Login successful";
                     //edInfo.setText((CharSequence) rs.getArray("what"));
                     //Toast.makeText(AlertAdmin.this, "Successful", Toast.LENGTH_SHORT).show();
                     isSuccess = true;
                     con.close();
                     } else {
                     z = "Invalid Credentials!";
                     //Toast.makeText(AlertAdmin.this, "Failed", Toast.LENGTH_SHORT).show();
                     isSuccess = false;
                     }
                     **/
                    if (rs.next()){
                        //System.out.println(rs.getString("priority")+"----------------------------------");
                        myList = new String[]{rs.getString("type"),rs.getString("priority"),rs.getString("matter")};
                        //System.out.println(myList[0]+myList[1]+myList[2]);
                        //a1.add("Zara");
                        //a1.add("Mahnaz");
                        //rs.getString("type")
                        //a1.add("Ayan");
                        //eidAddMatter.setText( rs.getString("what")+" "+rs.getString("additional")+"baldh    "+rs.getString("location"));
                        //eidAddMatter.setText(myList[1]+" "+myList[2]+" "+myList[0]);
                        //rs.getString("id");
                        //eidWhere.setText(rs.getString("location"));
                        txtsender.setText("From : Need to do");
                        txtType.setText("Type : "+myList[0]);
                        txtPrority.setText("Priority : "+myList[1]);
                        txtMatterExplain.setText(myList[2]);
                        //spliting the string, here, it seperate the latitiude and longitude

                    }else{
                        // you have no record

                    }


                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;


        }

    }



}