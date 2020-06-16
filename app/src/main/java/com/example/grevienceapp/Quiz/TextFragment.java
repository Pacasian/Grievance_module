package com.example.grevienceapp.Quiz;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.grevienceapp.R;

public class TextFragment extends Fragment {
    TextView textos,txtQues;
    Button btnSubmit;
    String Fanswer;
    RadioButton Ropt1,Ropt2,Ropt3,Ropt4;
    RadioGroup radioattend;
    LinearLayout showLay;
int correct=0;
String ans;
String[] array;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.textview, container, false);
        textos= (TextView) view.findViewById(R.id.txtdisp);
        txtQues=(TextView)view.findViewById(R.id.txtques);
        Ropt1=(RadioButton) view.findViewById(R.id.radioButton);
        Ropt2=(RadioButton)view.findViewById(R.id.radioButton2);
        Ropt3=(RadioButton)view.findViewById(R.id.radioButton3);
        Ropt4=(RadioButton)view.findViewById(R.id.radioButton4);
        btnSubmit=(Button) view.findViewById(R.id.btnSubmitQuiz);
        showLay=(LinearLayout) view.findViewById(R.id.showLayout);
        radioattend=(RadioGroup) view.findViewById(R.id.radio);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), correct+"", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
    public void change(String txt,String question,String op1,String op2,String op3,String op4,String ans){
        showLay.setVisibility(View.VISIBLE);
        textos.setText("Question no: "+txt);
        txtQues.setText(question);
        Ropt1.setText(op1);
        Ropt2.setText(op2);
        Ropt3.setText(op3);
        Ropt4.setText(op4);

        Fanswer=ans;
     
        radioattend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String  tempAns =null;
                switch (i){
                    case R.id.radioButton:
                         tempAns=Ropt1.getText().toString();
                        break;
                    case R.id.radioButton2:
                         tempAns=Ropt2.getText().toString();
                        break;
                    case R.id.radioButton3:
                         tempAns=Ropt3.getText().toString();
                        break;
                    case R.id.radioButton4:
                         tempAns=Ropt4.getText().toString();
                        break;
                    default:
                        break;
                }

                if (tempAns.equals(Fanswer)){
                    correct++;
                    System.out.println("---------------------------------------");
                    System.out.println(correct);
                    System.out.println("---------------------------------------");

                }
            }
        });

    }

}