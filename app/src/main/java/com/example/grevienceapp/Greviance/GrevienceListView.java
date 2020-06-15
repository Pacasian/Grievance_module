package com.example.grevienceapp.Greviance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class GrevienceListView extends AppCompatActivity {
    String AdminLevel;
    private ArrayList<GrevienceModel> itemArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grevience_list_view);
        AdminLevel=getIntent().getExtras().getString("level");


        Toast.makeText(this, AdminLevel, Toast.LENGTH_SHORT).show();

        TextView txtLeveLNot=findViewById(R.id.Textvv);
        txtLeveLNot.setText(" Viewing Grievance Under "+AdminLevel);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        itemArrayList = new ArrayList<GrevienceModel>(); // Arraylist Initialization

        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");
    }

    // Async Task has three overrided methods,
    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(GrevienceListView.this, "Synchronising",
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
                    // Change below query according to your own database.
                    String query = "SELECT id,type,timeDate FROM GTable  WHERE sendTo='"+ AdminLevel +"' ;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                itemArrayList.add(new GrevienceModel(rs.getString("type"),rs.getString("timeDate"),rs.getString("id")));
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
            Toast.makeText(GrevienceListView.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new MyAppAdapter(itemArrayList , GrevienceListView.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<GrevienceModel> values;
        public Context context;



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            // public image title and image url
            public TextView showType;
            public TextView showTD;
            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                v.setOnClickListener(this);
                layout = v;
                showType = (TextView) v.findViewById(R.id.txtType);
                showTD = (TextView) v.findViewById(R.id.showTimeDate);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "position = " + itemArrayList.get(getLayoutPosition()).number, Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(GrevienceListView.this,GrevienceAdmin.class);
                intent.putExtra("idNumber",itemArrayList.get(getLayoutPosition()).number+"");
                startActivity(intent);
                //System.out.println(itemArrayList.getClass());
                //String wh,loca,add;
                if(itemArrayList.contains(3)){//check if the list contains the element
                    System.out.println(itemArrayList.get(itemArrayList.indexOf(3)));//get the element by passing the index of the element
                }
                //go through each item if you have few items within recycler view
                if(getLayoutPosition()==0){
                    //Do whatever you want here

                }else if(getLayoutPosition()==1){
                    //Do whatever you want here

                }else if(getLayoutPosition()==2){

                }else if(getLayoutPosition()==3){

                }else if(getLayoutPosition()==4){

                }else if(getLayoutPosition()==5){

                }

                //or you can use For loop if you have long list of items. Use its length or size of the list as
                for(int i = 0; i<itemArrayList.size(); i++){
                    //itemArrayList.set(1)
                    //System.out.println(itemArrayList.get(i));
                    //System.out.println("itemArrayList.get(i)");
                   // System.out.println(());
                   /**

                    */
                }
            }
        }

        // Constructor
        public MyAppAdapter(List<GrevienceModel> myDataset, Context context)
        {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.grevience_list, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final GrevienceModel grevienceModel = values.get(position);
            holder.showType.setText(grevienceModel.getName());
            // Seperate the time and date and only show the date
            String[] dateTime = grevienceModel.getImg().split("\\s+");
            //System.out.println();
            holder.showTD.setText(dateTime[0]);

            //Picasso.with(context).load("http://"+classListItems.getImg()).into(holder.imageView);
        }


        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }

    }
}
