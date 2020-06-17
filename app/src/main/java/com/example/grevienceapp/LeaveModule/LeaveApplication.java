package com.example.grevienceapp.LeaveModule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grevienceapp.DatabaseConnection;
import com.example.grevienceapp.R;
import com.example.grevienceapp.RailwaySharedPreference;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ADD A MENU BUTTON TO IMPLEMENT THE LEAVE HISTORY ---- NEED TO DO
 */
public class LeaveApplication extends AppCompatActivity {
    Button btnFDate, btnTDate, btnSubmit,btnUserHistory;
    TextView txtDaysCount;
    EditText edReason;
    String stFromDate, stToDate,stLeaveType;
    long noDays;
    private RailwaySharedPreference sharedInfo;
    Calendar myCalendar;
    Spinner LeaveType;
    Connection con;
    String pfNumber,station,sectionTI;
    int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);
        btnFDate = findViewById(R.id.btnFromDate);
        btnTDate = findViewById(R.id.btnToDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUserHistory= findViewById(R.id.btnUserHistory);
        LeaveType = findViewById(R.id.spLeaveType);
        txtDaysCount = findViewById(R.id.txtReturnDaysCount);
        edReason = findViewById(R.id.edReason);
        sharedInfo = RailwaySharedPreference.getInstance(getApplicationContext());
         pfNumber = sharedInfo.get("pfNumber");
         level=sharedInfo.getInt("empLevel");
         station = sharedInfo.get("StationCode");
        sectionTI=sharedInfo.get("TiCount");
        System.out.println("-------------------------------------");
        System.out.println(pfNumber);
        System.out.println("-------------------------------------");
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                FromDate();
            }

        };
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ToDate();
            }

        };
        btnUserHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LeaveApplication.this,LeaveHistory.class);
                intent.putExtra("client_PF",pfNumber);
                startActivity(intent);
            }
        });
        btnFDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LeaveApplication.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        btnTDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stFromDate == null) {
                    Toast.makeText(LeaveApplication.this, "Select the From Date first....", Toast.LENGTH_SHORT).show();
                } else {
                    new DatePickerDialog(LeaveApplication.this, date2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        con = new DatabaseConnection().ConnectDB();
        if (con != null) {
            Toast.makeText(LeaveApplication.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LeaveApplication.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stFromDate!=null){
                    if (!(LeaveType.getSelectedItem().toString()).equals("Select Your Leave Type :")){
                        AddLeave addLeave = new AddLeave();
                        addLeave.execute("");
                    }else{
                        Toast.makeText(LeaveApplication.this, "Select a valid leave type !", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LeaveApplication.this, "Select the dates properly !", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void FromDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        stFromDate=sdf.format(myCalendar.getTime());
        btnFDate.setText(stFromDate);
    }
    @SuppressLint("SetTextI18n")
    private void ToDate() {

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            stToDate = sdf.format(myCalendar.getTime());
            btnTDate.setText(stToDate);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            try {
                Date sdate = format.parse(stFromDate);
                Date edate= format.parse(stToDate);

                if ((edate != null)&&( sdate != null)) {
                    noDays=(edate.getTime() - sdate.getTime())/(24*60*60*1000);
                    txtDaysCount.setText("Number of Days : "+(edate.getTime() - sdate.getTime())/(24*60*60*1000));
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }

    }
    
    public class AddLeave extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(LeaveApplication.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(LeaveApplication.this, "Login Successful", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {


                stLeaveType=LeaveType.getSelectedItem().toString();
                String Reason=edReason.getText().toString();
                String name = sharedInfo.get("Name");
                String design =sharedInfo.get("designation");
                /**

                 *    System.out.println(currentDateandTime);
                 System.out.println(stMatter);
                 System.out.println(stPriority);
                 System.out.println(stType);
                 System.out.println(stSendTo);
                 *
                 */
                /**
                 * Sort the
                 */
                String query = "insert into LeaveTable (pfNumber,fromDate,toDate,noOfDates,empLevel,station,leaveType,sectionTI,name,reason,designation) values ('" + pfNumber + "','" + stFromDate + "','" + stToDate + "'," + noDays + "," + level + ",'" + station + "','" + stLeaveType + "','" + sectionTI + "','" + name + "','" + Reason + "','" + design + "')";

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
}
