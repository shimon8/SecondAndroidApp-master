package com.project.secondapp.controller.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.secondapp.R;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;

public class TravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        getIncomingIntent();
    }

    public void getIncomingIntent() {
        TextView time = findViewById(R.id.item_time);
        TextView name =findViewById(R.id.userName);
        TextView sourceAddress =findViewById(R.id.sourceAddress);
        TextView endAddress =findViewById(R.id.endAddress);
        TextView number =findViewById(R.id.number);
        TextView email =findViewById(R.id.email);
        Button TakeDrive =findViewById(R.id.TakeDrive);
        TakeDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Backend backend = BackendFactory.getBackend();
                backend.TakeDrive(getIntent().getStringExtra("id"));
                String uri = "waze://?ll="+sourceAddress.getText().toString()+"&navigate=yes";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number.getText().toString().replace("טלפון:", "")));
                intent.putExtra("sms_body", "שלום רב,בדרך אלייך");
                startActivity(intent);
            }
        });
        time.setText("זמן הנסיעה: " + getIntent().getStringExtra("Ti meOfTravel") );
        name.setText("שם נוסע: " + getIntent().getStringExtra("name") );
        sourceAddress.setText("מאיפה: " + getIntent().getStringExtra("startDriving") );
        endAddress.setText("לאן: " + getIntent().getStringExtra("endDriving") );
        number.setText("טלפון: " + getIntent().getStringExtra("number") );
        email.setText("מייל: " + getIntent().getStringExtra("email") );
    }
}
