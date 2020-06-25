package com.example.grevienceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grevienceapp.Greviance.G_Admin_LevelAuth;
import com.example.grevienceapp.Greviance.GrevienceUserEntry;
import com.example.grevienceapp.LeaveModule.LeaveApplication;
import com.example.grevienceapp.LeaveModule.LeaveViewerAdmin;
import com.example.grevienceapp.Quiz.QuizActivity;
import com.example.grevienceapp.Rooster.AddRoosterActivity;
import com.example.grevienceapp.Rooster.AddRoosterActivity1;
import com.example.grevienceapp.Rooster.ViewRoosterActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
Button btnSampleAdmin;
Button btnSampleEmpl;
Button btnQuiz;
Button btnLeaveApp,btnLeaveViewer,btn_delete_r;
Button btnR_View,btnR_admin;
    Connection con;
String stationCodeName,EachStationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSampleAdmin=findViewById(R.id.btnSAdmin);
        btnSampleEmpl=findViewById(R.id.btnSEmpl);
        btnLeaveApp=findViewById(R.id.btnLeaveApp);
        btnLeaveViewer=findViewById(R.id.btnLeaveView);
        btnR_admin=findViewById(R.id.R_Admin);
        btnR_View=findViewById(R.id.R_View);
        btn_delete_r=findViewById(R.id.btn_delete_R);
        con = new ConnectionClass().CONN();
        if (con != null) {
            Toast.makeText(MainActivity.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }

        btnSampleAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, G_Admin_LevelAuth.class));
                finish();
            }
        });
        btnSampleEmpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GrevienceUserEntry.class));
                finish();
            }
        });
        btnQuiz=findViewById(R.id.btnQuiz);
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
            }
        });
        btnLeaveApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LeaveApplication.class));
            }
        });
        btnLeaveViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog();

            }
        });
        btnR_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_R_Dialog();
            }
        });
        btnR_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewRoosterActivity.class));
                finish();
            }
        });
        btn_delete_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_R_Delete_Dialog();
            }
        });
    }
    public void ViewDialog() {

            LayoutInflater factory = LayoutInflater.from(this);
            final View sectionTiDialogView = factory.inflate(R.layout.sectiont_ti_for_leave, null);
            final AlertDialog sectionTiDialog = new AlertDialog.Builder(this).create();
            sectionTiDialog.setView(sectionTiDialogView);
            final EditText editText=sectionTiDialogView.findViewById(R.id.ed_sectionTI);
            final EditText editText1=sectionTiDialogView.findViewById(R.id.ed_empLevel);
            sectionTiDialogView.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st=editText.getText().toString();
                String st1=editText1.getText().toString();
                Intent intent=new Intent(MainActivity.this, LeaveViewerAdmin.class);
                intent.putExtra("sectionTI",st);
                intent.putExtra("empLevel",st1);
                startActivity(intent);
                sectionTiDialog.dismiss();
                }
            });
            sectionTiDialogView.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionTiDialog.dismiss();
                }
            });

        sectionTiDialog.show();
        }
        public void View_R_Dialog(){

            LayoutInflater factory = LayoutInflater.from(this);
            final View sectionTiDialogView = factory.inflate(R.layout.rooster_verify, null);
            final AlertDialog sectionTiDialog = new AlertDialog.Builder(this).create();
            sectionTiDialog.setView(sectionTiDialogView);
            final EditText stationCode=sectionTiDialogView.findViewById(R.id.ed_station_code);
            sectionTiDialogView.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String st1=(stationCode.getText().toString());
                    EachStationCode=st1.toUpperCase();
                    String st=(stationCode.getText().toString().toUpperCase())+"_R_Table";
                    stationCodeName=st;
                    System.out.println("5-------------------------------------");
                    System.out.println(st);
                    System.out.println("-------------------------------------");
                    CheckTableExes checkTableExes =new CheckTableExes();
                    checkTableExes.execute("");
                    //Intent intent=new Intent(MainActivity.this, LeaveViewerAdmin.class);
                    //intent.putExtra("Station",st);
                    //startActivity(intent);
                    sectionTiDialog.dismiss();
                }
            });
            sectionTiDialogView.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sectionTiDialog.dismiss();
                }
            });

            sectionTiDialog.show();
        }
    public void View_R_Delete_Dialog(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View sectionTiDialogView = factory.inflate(R.layout.rooster_verify, null);
        final AlertDialog sectionTiDialog = new AlertDialog.Builder(this).create();
        sectionTiDialog.setView(sectionTiDialogView);
        final EditText stationCode=sectionTiDialogView.findViewById(R.id.ed_station_code);
        sectionTiDialogView.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st1=(stationCode.getText().toString());
                EachStationCode=st1.toUpperCase();
                String st=(stationCode.getText().toString().toUpperCase())+"_R_Table";
                stationCodeName=st;
                System.out.println("5-------------------------------------");
                System.out.println(st);
                System.out.println("-------------------------------------");
                CheckTableExes1 checkTableExes1 =new CheckTableExes1();
                checkTableExes1.execute("");
                //Intent intent=new Intent(MainActivity.this, LeaveViewerAdmin.class);
                //intent.putExtra("Station",st);
                //startActivity(intent);
                sectionTiDialog.dismiss();
            }
        });
        sectionTiDialogView.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionTiDialog.dismiss();
            }
        });

        sectionTiDialog.show();
    }
    
    public class CheckTableExes extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(MainActivity.this, "Login Successful", LENGTH_LONG).show();

            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected String doInBackground(String... params) {
            // @SuppressLint("WrongThread") String usernam = userTv.getText().toString();
            //@SuppressLint("WrongThread") String passwordd = passwordTv.getText().toString();

            try {
                // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "SELECT * FROM "+stationCodeName+" ;";
                    //String query = "SELECT * FROM ipo ;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    ResultSetMetaData metaData=rs.getMetaData();
                    int rowCount=metaData.getColumnCount();
                    /**
                     * start for development purpose delete it
                     */
                    System.out.println("4-------------------------------------");
                    System.out.println(query);
                    System.out.println(stationCodeName+"");
                    System.out.println(rowCount+"");
                    System.out.println("-------------------------------------");
                    /**
                     * End for development purpose delete it
                     */
                    if(rowCount!=10){
                        Toast.makeText(MainActivity.this, "Please clear the previous Rooster table! ", LENGTH_LONG).show();
                    }
                    else{
                        Intent intent=new Intent(MainActivity.this, AddRoosterActivity.class);
                        intent.putExtra("classSend",EachStationCode);
                        startActivity(intent);
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;


        }

    }
    public class CheckTableExes1 extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            System.out.println(z);
            if (isSuccess) {
                Toast.makeText(MainActivity.this, "Login Successful", LENGTH_LONG).show();

            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected String doInBackground(String... params) {
            // @SuppressLint("WrongThread") String usernam = userTv.getText().toString();
            //@SuppressLint("WrongThread") String passwordd = passwordTv.getText().toString();

            try {
                // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "DELETE FROM "+stationCodeName+" WHERE Rig is not null ;";
                    //String query = "SELECT * FROM ipo ;";
                    Statement stmt = con.createStatement();
                    stmt.executeQuery(query);


                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;


        }

    }

}
