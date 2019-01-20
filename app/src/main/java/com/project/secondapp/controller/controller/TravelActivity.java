package com.project.secondapp.controller.controller;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.secondapp.R;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.entities.Drivingstatus;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;

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
        Button addContact = findViewById(R.id.AddContact);
        TakeDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Backend backend = BackendFactory.getBackend();
                backend.TakeDrive(getIntent().getStringExtra("id"));

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number.getText().toString().replace("טלפון:", "")));
                intent.putExtra("sms_body", "שלום רב, בדרך אלייך");

                Intent mailIntent = new Intent(Intent.ACTION_SEND).
                        putExtra(Intent.EXTRA_EMAIL, email.getText().toString()).
                        putExtra(Intent.EXTRA_SUBJECT, "הנהג בדרך אלייך").
                        putExtra(Intent.EXTRA_TEXT, "שלום רב, יצאתי אלייך");
                mailIntent.setType("message/rfc822");
                startActivity(mailIntent.createChooser(mailIntent, "בחר באפליקציית המייל הרצויה"));

                String uri = "waze://?ll=" + sourceAddress.getText().toString() + "&navigate=yes";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));
                startActivity(intent);
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Backend backend = BackendFactory.getBackend();
                backend.AddContact(getIntent().getStringExtra("id"));

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.
                        putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText().toString()).
                        putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK).
                        putExtra(ContactsContract.Intents.Insert.PHONE, number.getText().toString()).
                        putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK).
                        putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString());
                startActivity(intent);
            }
        });
        time.setText("זמן הנסיעה: " + getIntent().getStringExtra("TimeOfTravel"));
        name.setText("שם נוסע: " + getIntent().getStringExtra("name"));
        sourceAddress.setText("מאיפה: " + getIntent().getStringExtra("startDriving"));
        endAddress.setText("לאן: " + getIntent().getStringExtra("endDriving"));
        number.setText("טלפון: " + getIntent().getStringExtra("number"));
        email.setText("מייל: " + getIntent().getStringExtra("email"));
    }
}
