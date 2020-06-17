package com.example.grevienceapp.LeaveModule;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grevienceapp.DatabaseConnection;
import com.example.grevienceapp.R;
import com.example.grevienceapp.RailwaySharedPreference;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IndividualLeavePreview extends AppCompatActivity {
String transPf,transFrom,transTo,transDays,transResponse,transID,transType;
TextView txt1,txt2,txt3,txt4,txt5,txt6;
Button btnViewHistory,btn_Approve,btn_Reject;
    Connection con;
    /**
     * @param savedInstanceState
     * THE STRING TRIGGER IS TO TRIGGER A SPECIFIC QUERY TO MACHINE
         * QueryTrigger=0 ----no query selected
         * QueryTrigger=1 ---- query to APPROVE
         * QueryTrigger=2 ---- query to REJECT
     */
    int QueryTrigger=0;
    private RailwaySharedPreference sharedInfo;
    String stID,stPF;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_leave_preview);
        sharedInfo = RailwaySharedPreference.getInstance(getApplicationContext());
        stID=getIntent().getExtras().getString("leaveID");
        stPF=getIntent().getExtras().getString("pfNumber");
        transPf=getIntent().getExtras().getString("pfNumber");
        transFrom=getIntent().getExtras().getString("fromDate");
        transTo=getIntent().getExtras().getString("toDate");
        transDays=getIntent().getExtras().getString("noDays");
        //transResponse=getIntent().getExtras().getString();
        transType=getIntent().getExtras().getString("leaveType");
        // Step 1
        txt1=findViewById(R.id.txt_request_pf);
        txt2=findViewById(R.id.txt_type_of_leave);
        txt3=findViewById(R.id.txt_from_date);
        txt4=findViewById(R.id.txt_to_date);
        txt5=findViewById(R.id.txt_no_of_days);
        txt6=findViewById(R.id.txt_reason);
        // Step 2
        txt1.setText("Pf Number "+transPf);
        txt2.setText("Leave Type "+transType);
        txt3.setText("From Date :"+transFrom);
        txt4.setText("To Date :"+transTo);
        txt5.setText("Leave Days :"+transDays);
        // Step 3

        btnViewHistory=findViewById(R.id.btn_view_history);
        btn_Approve=findViewById(R.id.btn_approve);
        btn_Reject=findViewById(R.id.btn_reject);
        con = new DatabaseConnection().ConnectDB();
        if (con != null) {
            Toast.makeText(IndividualLeavePreview.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(IndividualLeavePreview.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IndividualLeavePreview.this,LeaveHistory.class);
                intent.putExtra("client_PF",stPF);
                startActivity(intent);

            }
        });
        btn_Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryTrigger=1;
                IndQuery indQuery = new IndQuery();
                indQuery.execute("");
                startActivity(new Intent(IndividualLeavePreview.this,LeaveViewerAdmin.class));
                finish();
            }
        });
        btn_Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryTrigger=2;
                IndQuery indQuery = new IndQuery();
                indQuery.execute("");
                startActivity(new Intent(IndividualLeavePreview.this,LeaveViewerAdmin.class));
                finish();
            }
        });
    }

    public class IndQuery extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(IndividualLeavePreview.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(IndividualLeavePreview.this, "Login Successful", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = sdf.format(new Date());
                String query="";
               if (QueryTrigger==1){
                    query = "UPDATE LeaveTable SET verified=1,VerifiedDate='"+ currentDate +"',byRef='"+sharedInfo.get("designation")+"' where Lid="+stID+" ;";
               }else if (QueryTrigger==2){
                   query = "UPDATE LeaveTable SET verified=2,VerifiedDate='"+ currentDate +"',byRef='"+sharedInfo.get("designation")+"' where Lid="+stID+" ;";
               }else{
                   Toast.makeText(IndividualLeavePreview.this, "InValid Request", Toast.LENGTH_SHORT).show();
               }

                QueryTrigger=0;
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