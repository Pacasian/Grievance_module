package com.example.grevienceapp.Rooster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
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

public class ViewRoosterActivity extends AppCompatActivity {
    TextView txt_Pf;
    TextView txtD11,txtD12,txtD13,txtD14,txtD15,txtD16,txtD17;
    Button btn_done;
    Connection con;
    private boolean success = false;
    ConnectionClass connectionClass; //
    String[] showList=new String[]{"","","","","","",""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooster);
        connectionClass = new ConnectionClass();
        txt_Pf=findViewById(R.id.txt_pf_1);

        btn_done=findViewById(R.id.btn_done_1);

        txtD11=findViewById(R.id.txtD11);
        txtD12=findViewById(R.id.txtD12);
        txtD13=findViewById(R.id.txtD13);
        txtD14=findViewById(R.id.txtD14);
        txtD15=findViewById(R.id.txtD15);
        txtD16=findViewById(R.id.txtD16);
        txtD17=findViewById(R.id.txtD17);
        con = new ConnectionClass().CONN();
        if (con != null) {
            Toast.makeText(ViewRoosterActivity.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ViewRoosterActivity.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }
        ShowData_R showData_r=new ShowData_R();
        showData_r.execute("");
    }
    private class ShowData_R extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(ViewRoosterActivity.this, "Synchronising",
                    "RecyclerView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try
            {
                System.out.println("---------------------------------");
                    // append the level segregation HERE --- need to do
                    String query = "select * from SXT_R_Table where pfNumber= "+ 457 +" ;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {

                                txt_Pf.setText(rs.getString("pfNumber"));
                                txtD11.setText(rs.getString("Day1"));
                                txtD12.setText(rs.getString("Day2"));
                                txtD13.setText(rs.getString("Day3"));
                                txtD14.setText(rs.getString("Day4"));
                                txtD15.setText(rs.getString("Day5"));
                                txtD16.setText(rs.getString("Day6"));
                                txtD17.setText(rs.getString("Day7"));


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
            Toast.makeText(ViewRoosterActivity.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {


                } catch (Exception ex)
                {

                }

            }
        }
    }
}