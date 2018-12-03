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
    public byte[] enterUser() {
        byte[] x = password.getText().toString().getBytes();
        byte[] y = userName.getText().toString().getBytes();
        byte[] out = new byte[y.length];
        for (int i = 0; i < x.length; i++) {
            out[i] = (byte) (x[i] ^ y[i % x.length]);
        }
        return out;
    }
}
