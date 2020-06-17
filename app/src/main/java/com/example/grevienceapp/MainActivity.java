package com.example.grevienceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.grevienceapp.Greviance.G_Admin_LevelAuth;
import com.example.grevienceapp.Greviance.GrevienceUserEntry;
import com.example.grevienceapp.LeaveModule.LeaveApplication;
import com.example.grevienceapp.LeaveModule.LeaveViewerAdmin;
import com.example.grevienceapp.Quiz.QuizActivity;

public class MainActivity extends AppCompatActivity {
Button btnSampleAdmin;
Button btnSampleEmpl;
Button btnQuiz;
Button btnLeaveApp,btnLeaveViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSampleAdmin=findViewById(R.id.btnSAdmin);
        btnSampleEmpl=findViewById(R.id.btnSEmpl);
        btnLeaveApp=findViewById(R.id.btnLeaveApp);
        btnLeaveViewer=findViewById(R.id.btnLeaveView);

        btnSampleAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, G_Admin_LevelAuth.class));
                finish();
            }
        });
        btnSampleEmpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GrevienceUserEntry.class));
                finish();
            }
        });
        btnQuiz=findViewById(R.id.btnQuiz);
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
            }
        });
        btnLeaveApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LeaveApplication.class));
            }
        });
        btnLeaveViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog();


            }
        });
    }
    public void ViewDialog() {

            LayoutInflater factory = LayoutInflater.from(this);
            final View sectionTiDialogView = factory.inflate(R.layout.sectiont_ti_for_leave, null);
            final AlertDialog sectionTiDialog = new AlertDialog.Builder(this).create();
            sectionTiDialog.setView(sectionTiDialogView);
            final EditText editText=sectionTiDialogView.findViewById(R.id.ed_sectionTI);
            final EditText editText1=sectionTiDialogView.findViewById(R.id.ed_empLevel);
            sectionTiDialogView.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st=editText.getText().toString();
                String st1=editText1.getText().toString();
                Intent intent=new Intent(MainActivity.this, LeaveViewerAdmin.class);
                intent.putExtra("sectionTI",st);
                intent.putExtra("empLevel",st1);
                startActivity(intent);
                sectionTiDialog.dismiss();
                }
            });
            sectionTiDialogView.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionTiDialog.dismiss();
                }
            });

        sectionTiDialog.show();
        }
    }
