package com.project.secondapp.controller.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
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
        TextView name = findViewById(R.id.userName);
        TextView sourceAddress = findViewById(R.id.sourceAddress);
        TextView endAddress = findViewById(R.id.endAddress);
        TextView number = findViewById(R.id.number);
        TextView email = findViewById(R.id.email);
        Button TakeDrive = findViewById(R.id.TakeDrive);
        time.setText("זמן הנסיעה: " + getIntent().getStringExtra("TimeOfTravel"));
        name.setText("שם נוסע: " + getIntent().getStringExtra("name"));
        sourceAddress.setText("מאיפה: " + getIntent().getStringExtra("startDriving"));
        endAddress.setText("לאן: " + getIntent().getStringExtra("endDriving"));
        number.setText("טלפון: " + getIntent().getStringExtra("number"));
        email.setText("מייל: " + getIntent().getStringExtra("email"));

        TakeDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Backend backend = BackendFactory.getBackend();
                backend.TakeDrive(getIntent().getStringExtra("id"));


//                Intent mailIntent = new Intent(Intent.ACTION_SEND).
//                        putExtra(Intent.EXTRA_EMAIL, email.getText().toString()).
//                        putExtra(Intent.EXTRA_SUBJECT, "הנהג בדרך אלייך").
//                        putExtra(Intent.EXTRA_TEXT, "שלום רב, יצאתי אלייך");
//                mailIntent.setType("message/rfc822");
//                startActivity(mailIntent.createChooser(mailIntent, "בחר באפליקציית המייל הרצויה"));

                String[] mails = {email.getText().toString().replace("מייל:", "")};
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "שלום רב, יצאתי אלייך");
                sendIntent.putExtra(Intent.EXTRA_USER, mails);
                sendIntent.putExtra(Intent.EXTRA_EMAIL, mails);
                sendIntent.putExtra("sms_body", "שלום רב, בדרך אלייך");
                sendIntent.putExtra("address", number.getText().toString().replace("טלפון:", ""));
                sendIntent.putExtra("subject", "הזמנת מונית SMART TAXI");
                sendIntent.setType("text/plain");
                String uri = "waze://?ll=" + sourceAddress.getText().toString() + "&navigate=yes";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));
                startActivity(sendIntent);

            }
        });

    }
}
