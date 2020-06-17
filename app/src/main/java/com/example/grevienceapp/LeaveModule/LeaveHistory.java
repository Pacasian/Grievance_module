package com.example.grevienceapp.LeaveModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.grevienceapp.ConnectionClass;
import com.example.grevienceapp.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeaveHistory extends AppCompatActivity {
    String client_pf;
    int stEmpLevel;
    int approveLimit;
    private ArrayList<LeaveModelView> leaveItemArrayList;  //List items Array
    private LeaveHistory.MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_history);
        client_pf=getIntent().getExtras().getString("client_PF");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        leaveItemArrayList = new ArrayList<LeaveModelView>(); // Arraylist Initialization

        LeavePreview2 leavePreview2 =new LeavePreview2();
        leavePreview2.execute("");
    }
    private class LeavePreview2 extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(LeaveHistory.this, "Synchronising",
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
                     * same station and under the same ti
                     */
                    String query ="select * from LeaveTable where pfNumber='"+ client_pf +"' ORDER BY Lid DESC;";
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
            Toast.makeText(LeaveHistory.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new LeaveHistory.MyAppAdapter(leaveItemArrayList , LeaveHistory.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<LeaveHistory.MyAppAdapter.ViewHolder> {
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
        public LeaveHistory.MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.leave_list, parent, false);
            LeaveHistory.MyAppAdapter.ViewHolder vh = new LeaveHistory.MyAppAdapter.ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(LeaveHistory.MyAppAdapter.ViewHolder holder, final int position) {
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