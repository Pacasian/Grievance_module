package com.example.grevienceapp.LeaveModule;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grevienceapp.ConnectionClass;

import com.example.grevienceapp.R;
import com.example.grevienceapp.RailwaySharedPreference;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * FIRST READ ALL THE DATA UNDER THE SAME SECTION TI AND VALUES BELOW EMPLEVEL FROM THE TABLE
 * WHEN USER CLICK A SPECIFIC DATA, THEN CHECK FOR THE HIERARCHY OF USER IS RESONATING WITH THE POWER TO APPROVE LEAVE (CRITERIA IS .. NUMBER OF DAYS )
 * IF THE USER CANT APPROVE THEN APPROVE BUTTON MUST BE DISABLED
 * IF THE USER CAN APPROVE THEN THE USER CAN CHECK THE CLIENT PREVIOUS LEAVE HISTORY
 * IF APPROVED THEN STORE THE DATE OF APPROVAL AND FILL THE VERIFIED COLUMN .
 *
 *
 */
public class LeaveViewerAdmin extends AppCompatActivity {
    String stSectionTI;
    int stEmpLevel;
    int approveLimit;
    private ArrayList<LeaveModelView> leaveItemArrayList;  //List items Array
    private LeaveViewerAdmin.MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable
    private RailwaySharedPreference sharedInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_viewer_admin);
        stSectionTI= Objects.requireNonNull(getIntent().getExtras()).getString("sectionTI");
        stEmpLevel= Integer.parseInt(Objects.requireNonNull((getIntent().getExtras()).getString("empLevel")));
        if(stEmpLevel==2){
            approveLimit=3;
        }else if(stEmpLevel==3){
            approveLimit=5;
        }else if ((stEmpLevel==4)||(stEmpLevel==5)){
            approveLimit=100;
        }
        Toast.makeText(this, stSectionTI, Toast.LENGTH_SHORT).show();
        sharedInfo = RailwaySharedPreference.getInstance(getApplicationContext());
        System.out.println("------------------");
        System.out.println(sharedInfo.getInt("empLevel"));
        System.out.println("-------------------");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        leaveItemArrayList = new ArrayList<LeaveModelView>(); // Arraylist Initialization

        LeavePreview leavePreview =new LeavePreview();
        leavePreview.execute("");

    }


    private class LeavePreview extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(LeaveViewerAdmin.this, "Synchronising",
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
                    /**
                     * Replace the default empLevel with the data from shared preference
                     * same station
                     */
                    String query ="select * from LeaveTable where empLevel<3 and sectionTI='"+ stSectionTI +"' and verified is NULL ORDER BY Lid DESC;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                leaveItemArrayList.add(new LeaveModelView(rs.getString("Lid"),rs.getString("pfNumber"),rs.getString("noOfDates"),rs.getString("empLevel"),rs.getString("leaveType"),rs.getString("fromDate"),rs.getString("toDate"),rs.getString("name"),rs.getString("reason"),rs.getString("designation"),rs.getString("station"),rs.getString("verified")));
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
            Toast.makeText(LeaveViewerAdmin.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new LeaveViewerAdmin.MyAppAdapter(leaveItemArrayList , LeaveViewerAdmin.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<LeaveViewerAdmin.MyAppAdapter.ViewHolder> {
        private List<LeaveModelView> values;
        public Context context;



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            // public image title and image url
            public TextView txt_pfNumber;
            public TextView txt_Days;
            public TextView txt_type;
            public TextView txt_status;
            public TextView txt_From;
            public TextView txt_To;
            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                v.setOnClickListener(this);
                layout = v;
                txt_pfNumber = (TextView) v.findViewById(R.id.txt_pf_View);
                txt_Days = (TextView) v.findViewById(R.id.txtDays);
                txt_type = (TextView) v.findViewById(R.id.txt_leave_type);
                txt_status = (TextView) v.findViewById(R.id.txt_status);
                txt_From = (TextView) v.findViewById(R.id.txt_from);
                txt_To = (TextView) v.findViewById(R.id.txt_to);
            }

            @Override
            public void onClick(View v) {
                /**
                 * IF THE APPROVE_LIMIT IS 4 THEN DAYS LESS THAN 4 CAN BE APPROVED
                 * IF THE APPROVE_LIMIT IS 40 THEN DAYS LESS THAN 40 CAN BE APPROVED
                 * IF THE APPROVE_LIMIT IS 100 THEN THE USER CAN APPROVE ALL DATA AS THE USER IS SR.DOM OR ADMIN
                 */
                if((Integer.parseInt(leaveItemArrayList.get(getLayoutPosition()).noDays)<approveLimit)||(approveLimit==100)){


                    Intent intent =new Intent(LeaveViewerAdmin.this, IndividualLeavePreview.class);
                    intent.putExtra("leaveID",leaveItemArrayList.get(getLayoutPosition()).Lid+"");
                    intent.putExtra("pfNumber",leaveItemArrayList.get(getLayoutPosition()).pfNumber+"");
                    intent.putExtra("fromDate",leaveItemArrayList.get(getLayoutPosition()).fromDate+"");
                    intent.putExtra("toDate",leaveItemArrayList.get(getLayoutPosition()).toDate+"");
                    intent.putExtra("noDays",leaveItemArrayList.get(getLayoutPosition()).noDays+"");
                    intent.putExtra("leaveType",leaveItemArrayList.get(getLayoutPosition()).leaveType+"");

                    intent.putExtra("name4",leaveItemArrayList.get(getLayoutPosition()).name4+"");
                    intent.putExtra("reason",leaveItemArrayList.get(getLayoutPosition()).reason+"");
                    intent.putExtra("design4",leaveItemArrayList.get(getLayoutPosition()).design4+"");
                    intent.putExtra("station4",leaveItemArrayList.get(getLayoutPosition()).station4+"");

                    /**
                     *                  NEED TO DO
                     * also pass the STATION and employee DESIGNATION LATER
                     * intent.putExtra("station",leaveItemArrayList.get(getLayoutPosition()).station+"");
                     *intent.putExtra("designation",leaveItemArrayList.get(getLayoutPosition()).designation+"");
                     */

                    startActivity(intent);
                }else{
                    Toast.makeText(context, "No authority to approve you can only View the data", Toast.LENGTH_LONG).show();
                }
            }
        }

        // Constructor
        public MyAppAdapter(List<LeaveModelView> myDataset, Context context)
        {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public LeaveViewerAdmin.MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.leave_list, parent, false);
            LeaveViewerAdmin.MyAppAdapter.ViewHolder vh = new LeaveViewerAdmin.MyAppAdapter.ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(LeaveViewerAdmin.MyAppAdapter.ViewHolder holder, final int position) {
            String tempStatus="not assigned";
            final LeaveModelView leaveModelView = values.get(position);
            holder.txt_pfNumber.setText("PF Number: "+leaveModelView.getPfNumber());
            System.out.println("leave Type: "+leaveModelView.getLeaveType());
            if(leaveModelView.getLeaveType()==null){
                holder.txt_type.setText("Leave Type : null");
            }else {
                holder.txt_type.setText("Leave Type : "+leaveModelView.getLeaveType());
            }
            holder.txt_Days.setText("Leave Days : "+leaveModelView.getNoDays());
            if (leaveModelView.getVerified()==null){
                tempStatus="pending..";
                holder.txt_status.setTextColor(ResourcesCompat.getColor(getResources(), R.color.Yellow, null));
            }else if((leaveModelView.getVerified()).equals("1")){
                tempStatus="Approved !";
                holder.txt_status.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
            }else if((leaveModelView.getVerified()).equals("2")){
                tempStatus="Rejected..!";
                holder.txt_status.setTextColor(ResourcesCompat.getColor(getResources(), R.color.Red, null));

            }else{
                tempStatus="pending..";
                holder.txt_status.setTextColor(ResourcesCompat.getColor(getResources(), R.color.Yellow, null));
            }
            holder.txt_status.setText("Status : "+tempStatus);
            holder.txt_From.setText("From : "+leaveModelView.getFromDate());
            holder.txt_To.setText("To : "+leaveModelView.getToDate());
        }


        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }

    }
}
/**
 Toast.makeText(v.getContext(), "position = " + leaveItemArrayList.get(getLayoutPosition()).Lid, Toast.LENGTH_SHORT).show();
 Intent intent =new Intent(LeaveViewerAdmin.this, IndividualLeavePreview.class);
 intent.putExtra("idNumber",leaveItemArrayList.get(getLayoutPosition()).Lid+"");
 startActivity(intent);
 //System.out.println(leaveItemArrayList.getClass());
 //String wh,loca,add;


 */