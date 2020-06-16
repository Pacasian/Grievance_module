package com.example.grevienceapp.Quiz;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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

public class QuizActivity extends AppCompatActivity {
    private ArrayList<QuizPreviewModel> quizArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass;
    int[] ansArray=new int[5];
    String[] QnoArray=new String[5];
    int correct=0;
    TextView textos,txtQues;
TextView Timer;
    String Fanswer;
    RadioButton Ropt1,Ropt2,Ropt3,Ropt4;
    RadioGroup radioattend;
    LinearLayout showLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        quizArrayList = new ArrayList<QuizPreviewModel>(); // Arraylist Initialization
        textos= (TextView) findViewById(R.id.txtdisp);
        txtQues=(TextView)findViewById(R.id.txtques);
        Ropt1=(RadioButton) findViewById(R.id.radioButton);
        Ropt2=(RadioButton)findViewById(R.id.radioButton2);
        Ropt3=(RadioButton)findViewById(R.id.radioButton3);
        Ropt4=(RadioButton)findViewById(R.id.radioButton4);
        Timer=findViewById(R.id.timer);
        showLay=(LinearLayout) findViewById(R.id.showLayout);
        radioattend=(RadioGroup) findViewById(R.id.radio);
        Button btnFinish=(Button) findViewById(R.id.btnFinishQuiz);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    System.out.println(correct+" marks out of "+quizArrayList.size());
                Intent intent=new Intent(QuizActivity.this,ResultQuiz.class);
                intent.putExtra("marks",correct+"");
                intent.putExtra("overall",quizArrayList.size()+"");
                startActivity(intent);
                finish();

            }
        });
        QuizData quizData = new QuizData();
        quizData.execute("");


        Toast.makeText(this, "coming", Toast.LENGTH_SHORT).show();
        //ansArray=new int[quizArrayList.size()];
        //QnoArray=new String[quizArrayList.size()];

        // while pressing the button finish check the size of QnoArray and ansArray are same else ERROR !
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                Timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                Intent intent=new Intent(QuizActivity.this,ResultQuiz.class);
                intent.putExtra("marks",correct+"");
                intent.putExtra("overall",quizArrayList.size()+"");
                startActivity(intent);
                finish();
            }

        }.start();
    }



    private class QuizData extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(QuizActivity.this, "Synchronising",
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
                    String query = "SELECT * FROM QTable ;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                quizArrayList.add(new QuizPreviewModel(rs.getString("Qid"),rs.getString("Question"),rs.getString("opt1"),rs.getString("opt2"),rs.getString("opt3"),rs.getString("opt4"),rs.getString("answer")));
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
            Toast.makeText(QuizActivity.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new QuizActivity.MyAppAdapter(quizArrayList , QuizActivity.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<QuizPreviewModel> values;
        public Context context;
        public MyAppAdapter(List<QuizPreviewModel> myDataset, Context context)
        {
            values = myDataset;
            this.context = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            // public image title and image url
            public TextView showQuizID;
            public TextView showTD;
            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                v.setOnClickListener(this);
                layout = v;
                showQuizID = (TextView) v.findViewById(R.id.qno);

            }

            @Override
            public void onClick(View v) {

                change(quizArrayList.get(getLayoutPosition()).qid,quizArrayList.get(getLayoutPosition()).question,quizArrayList.get(getLayoutPosition()).opt1,quizArrayList.get(getLayoutPosition()).opt2,quizArrayList.get(getLayoutPosition()).opt3,quizArrayList.get(getLayoutPosition()).opt4,quizArrayList.get(getLayoutPosition()).ans);

            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.quiz_question_number, parent, false);
            QuizActivity.MyAppAdapter.ViewHolder vh = new QuizActivity.MyAppAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final QuizPreviewModel grevienceModel = values.get(position);
            holder.showQuizID.setText(grevienceModel.getQid());
            
        }

        @Override
        public int getItemCount() {
            return values.size();
        }


        
    }

    public void change(String txt, final String question, String op1, String op2, String op3, String op4, String ans) {
        /**
         * CONDITION NEED TO BE REMEMBERED
         * 1.WHEN USER CHECK A RADIO BUTTON AND SUBMIT (WHILE THE QUESTION IS NOT PRESENT IN QnoArray) -------
         * 2.USER FIRST TIME ENTERING THE PAGE ----
         * 3.USER WANT TO CHANGE THE EXISTING ENTER RADIO BUTTON VALUE, THEN CHANGE THE VALUE IN BOTH QnoArray and ansArray ------
         * 4.USER CHECK THE BUTTON SUBMIT WITHOUT SUBMITTING -----
         *
         */
        Ropt1.setChecked(false);
        Ropt2.setChecked(false);
        Ropt3.setChecked(false);
        Ropt4.setChecked(false);

        final String qNo = txt;
        int newEntry=0;
        final String[] answer = new String[1];
        RadioButton[] ra = new RadioButton[]{Ropt1, Ropt2, Ropt3, Ropt4};
        final int[] clickedStatus = {0};

            showLay.setVisibility(View.VISIBLE);
            //
            System.out.println("First Entry");
            System.out.println("---------------------------------------");
            textos.setText("Question no: " + txt);
            txtQues.setText(question);
            Ropt1.setText(op1);
            Ropt2.setText(op2);
            Ropt3.setText(op3);
            Ropt4.setText(op4);

            Fanswer = ans;

            radioattend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    String tempAns = null;
                    int click = 0;
                    switch (i) {
                        case R.id.radioButton:
                            tempAns = Ropt1.getText().toString();
                            click=1;
                            break;
                        case R.id.radioButton2:
                            tempAns = Ropt2.getText().toString();
                            click=2;
                            break;
                        case R.id.radioButton3:
                            tempAns = Ropt3.getText().toString();
                            click=3;
                            break;
                        case R.id.radioButton4:
                            tempAns = Ropt4.getText().toString();
                            click=4;
                            break;
                        default:
                            break;
                    }
                    if (tempAns.equals(Fanswer)) {
                        correct++;
                        System.out.println("---------------------------------------");
                        System.out.println(correct);
                        System.out.println("---------------------------------------");

                    }
                }

            });







        // get item count returns the list item count

    }

}

/***
 System.out.println("---------------------------------------");
 if(QnoArray!=null){
 System.out.println("OK either the question is present in the array or new Entry");
 System.out.println(QnoArray.length+"");
 System.out.println("---------------------------------------");
 int added=0;
 for(int i=1;i<QnoArray.length-1;i++){

 if (((QnoArray[i])!=null)&&QnoArray[i].equals(txt)){
 //run a loop to find which option is clicked

 for(int j=1;j<=4;j++){
 if (j==ansArray[i]){
 // then set the corresponding radioButton checked
 ra[j+1].setChecked(true);
 System.out.println("---------------------------------------");
 System.out.println("OK  the question is present in the array ");
 System.out.println("---------------------------------------");
 }
 added=1;
 final int finalI = i;
 btnSubmit.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
//Change the value in the ansArray
System.out.println("---------------------------------------");
System.out.println("Update Question");
System.out.println("---------------------------------------");
int radioButtonID = radioattend.getCheckedRadioButtonId();
View radioButton = radioattend.findViewById(radioButtonID);
int idx = radioattend.indexOfChild(radioButton);
// Then update the index value in ansArray
//ansArray[finalI]=(radioattend.getCheckedRadioButtonId());
System.out.println("---------------------------------------");
System.out.println("index "+idx);
System.out.println("---------------------------------------");

// Check the updated value is correct or not
RadioButton r = (RadioButton) radioattend.getChildAt(idx);
String selectedtext = r.getText().toString();
if (selectedtext.equals(Fanswer)) {
correct++;
System.out.println("---------------------------------------");
System.out.println(correct);
System.out.println("---------------------------------------");

}
}
});
 }
 }
 }//loop close
 if(added==0){
 // Here if the element does not exists in the QnoArray, then add the element

 }
 }
 // for the first time entering ght page
 else {

 }


 }
**/