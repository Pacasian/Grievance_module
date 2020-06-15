package com.example.grevienceapp.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.grevienceapp.R;

import java.util.Objects;

public class ResultQuiz extends AppCompatActivity {
TextView result;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_quiz);
        result=findViewById(R.id.resultQuiz);
        result.setText(Objects.requireNonNull(getIntent().getExtras()).getString("marks")+" marks out of "+(getIntent().getExtras().getString("overall")));
    }
}