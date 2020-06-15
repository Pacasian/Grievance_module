package com.example.grevienceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grevienceapp.Greviance.G_Admin_LevelAuth;
import com.example.grevienceapp.Greviance.GrevienceUserEntry;

public class MainActivity extends AppCompatActivity {
Button btnSampleAdmin;
Button btnSampleEmpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSampleAdmin=findViewById(R.id.btnSAdmin);
        btnSampleEmpl=findViewById(R.id.btnSEmpl);
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
    }
}