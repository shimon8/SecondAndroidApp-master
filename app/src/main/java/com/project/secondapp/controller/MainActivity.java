package com.project.secondapp.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.secondapp.R;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //region init
    String pass;
    String name;
    EditText userName;
    EditText password;
    Button enterUser;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=(EditText) findViewById(R.id.user_name);
        password=(EditText) findViewById(R.id.password);
        enterUser=(Button) findViewById(R.id.signtbtn);
        enterUser.setOnClickListener(this);
    }
    public String enterUser() throws NoSuchAlgorithmException {
        MessageDigest digest = java.security.MessageDigest
                .getInstance("MD5");digest.update(password.getText().toString().getBytes());
        return digest.digest().toString();
    }

    @Override
    public void onClick(View v) {
        try {
         if(userName.getText().toString()!="") {
             name=userName.getText().toString();
             pass = enterUser();
             //TODO GO TO DB
         }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
