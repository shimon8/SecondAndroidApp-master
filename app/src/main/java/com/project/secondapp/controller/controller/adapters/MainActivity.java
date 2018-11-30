package com.project.secondapp.controller.controller.adapters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.secondapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    //region init

    EditText userName;
    EditText password;
    Button newDriver;
    Button enterUser;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        enterUser = (Button) findViewById(R.id.signtbtn);
        enterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDriver = new Intent(MainActivity.this, AddDriver.class);
                startActivity(addDriver);
            }
        });
        newDriver = (Button) findViewById(R.id.ndriver);
        newDriver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent addDriver = new Intent(MainActivity.this, AddDriver.class);
                startActivity(addDriver);
            }
        });


    }
    public String enterUser() throws NoSuchAlgorithmException {
        MessageDigest digest = java.security.MessageDigest
                .getInstance("MD5");
        digest.update(password.getText().toString().getBytes());
        return digest.digest().toString();
    }

}
