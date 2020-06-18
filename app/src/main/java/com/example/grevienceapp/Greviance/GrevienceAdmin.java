package com.example.grevienceapp.Greviance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grevienceapp.DatabaseConnection;
import com.example.grevienceapp.LeaveModule.IndividualLeavePreview;
import com.example.grevienceapp.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class GrevienceAdmin extends AppCompatActivity {
    String idNumber;
    String senderPfNumber;
    Connection con;
    String[] myList;
    TextView txtsender, txtType, txtPrority, txtMatterExplain;
    Button btnApprove, btnNot, btnResponseWhat, btnResponse;
    EditText edResponseTxt;
    LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grevience_admin);
        idNumber = getIntent().getExtras().getString("idNumber").trim();
        Toast.makeText(this, idNumber, LENGTH_LONG).show();
        txtsender = findViewById(R.id.idFrom);
        txtPrority = findViewById(R.id.idAdminPriority);
        txtType = findViewById(R.id.idAdminType);
        txtMatterExplain = findViewById(R.id.idMatterExplain);
        //btnApprove=findViewById(R.id.btnApprove);
        con = new DatabaseConnection().ConnectDB();
        if (con != null) {
            Toast.makeText(GrevienceAdmin.this, "Connection valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(GrevienceAdmin.this, "Connection invalid", Toast.LENGTH_SHORT).show();
        }
        // Setting up the function when button login is clicked
        CheckLogin checkLogin = new CheckLogin();// ShowAdmin.this is the Asynctask, which is used to process in background to reduce load on app process
        checkLogin.execute("");
        mlinearLayout = findViewById(R.id.responseLayout);
        btnResponseWhat = findViewById(R.id.respondQuestionMark);
        btnResponse = findViewById(R.id.sendResponse);
        edResponseTxt = findViewById(R.id.idResponseTXT);
        btnResponseWhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlinearLayout.setVisibility(View.VISIBLE);

                btnResponse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteAndRespond DandR = new DeleteAndRespond();// ShowAdmin.this is the Asynctask, which is used to process in background to reduce load on app process
                        DandR.execute("");
                    }
                });
            }
        });

    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(GrevienceAdmin.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(GrevienceAdmin.this, "Login Successful", LENGTH_LONG).show();

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
                    String query = "SELECT * FROM GTable  WHERE id=" + idNumber + ";";
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
                    if (rs.next()) {
                        //System.out.println(rs.getString("priority")+"----------------------------------");
                        myList = new String[]{rs.getString("type"), rs.getString("priority"), rs.getString("matter"), rs.getString("pfNumber")};
                        //System.out.println(myList[0]+myList[1]+myList[2]);
                        //a1.add("Zara");
                        //a1.add("Mahnaz");
                        //rs.getString("type")
                        //a1.add("Ayan");
                        //eidAddMatter.setText( rs.getString("what")+" "+rs.getString("additional")+"baldh    "+rs.getString("location"));
                        //eidAddMatter.setText(myList[1]+" "+myList[2]+" "+myList[0]);
                        //rs.getString("id");
                        //eidWhere.setText(rs.getString("location"));
                        txtsender.setText("From :" + myList[3]);
                        senderPfNumber=myList[3];
                        txtType.setText("Type : " + myList[0]);
                        txtPrority.setText("Priority : " + myList[1]);
                        txtMatterExplain.setText(myList[2]);
                        //spliting the string, here, it seperate the latitiude and longitude

                    } else {
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

    public class DeleteAndRespond extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(GrevienceAdmin.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(GrevienceAdmin.this, "Login Successful", LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            // @SuppressLint("WrongThread") String usernam = userTv.getText().toString();
            //@SuppressLint("WrongThread") String passwordd = passwordTv.getText().toString();
            String mBody = edResponseTxt.getText().toString();
            if (mBody != null) {

                /**
                 * if mBody which is the response text is empty just delete the grievance, no need to send the acknowledgement into the mailTable
                 * else send the response to the mailTable and delete the current grievance
                 */
                try {

                    String query = "DELETE FROM GTable WHERE id="+ idNumber +" ;";
                    Statement stmt = con.createStatement();
                    stmt.executeQuery(query);
                    /**
                     * Rollback to the listView
                     */
                    Intent mintent =new Intent(GrevienceAdmin.this,GrevienceListView.class);
                    startActivity(mintent);
                    finish();
                } catch (SQLException se) {
                    Log.e("ERROR", se.getMessage());
                }
            }
            else {
                try {

                    String query = "DELETE FROM GTable WHERE id="+ idNumber +" ;";
                    Statement stmt = con.createStatement();
                    stmt.executeQuery(query);

                    String receiverID=senderPfNumber;
                    /**
                     * Insert the response into mailTable with the pf number
                     */
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDateandTime = sdf.format(new Date());
                    /**
                     * The Admin pf number (or Current user pf number ), need to take the value from UserDataRepository
                     * String AdminSender=
                     */

                    //String AdminSender=UserDataRespository.getID();
                    /**
                     * The Admin level (or Current user level ), need to take the value from UserDataRepository
                     * String AdminLevelInfo=
                     */
                    //String AdminLevelInfo=UserDataRespository.getLevel();
                    String query2 = "insert into mailTable (senderPfNumber,senderLevel,receverPfNumber,mailBody,mtimeDate,read) values ('" + "AdminSender" + "','" + "AdminLevelInfo" + "','" + receiverID + "','" + mBody + "','" + currentDateandTime + "','" + "NO" + "')";
                    Statement stmt2 = con.createStatement();
                    stmt2.executeQuery(query2);
                } catch (SQLException se) {
                    Log.e("ERROR", se.getMessage());
                }

            }
                return z;


            }

        }
    }


