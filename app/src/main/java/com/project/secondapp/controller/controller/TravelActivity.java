package com.project.secondapp.controller.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.project.secondapp.R;

public class TravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        getIncomingIntent();
    }

    public void getIncomingIntent() {
        TextView time = findViewById(R.id.time);
        time.setText("זמן הנסיעה: " + getIntent().getStringExtra("TimeOfTravel") );
    }
}
