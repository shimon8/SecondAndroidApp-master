package com.project.secondapp.controller.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.secondapp.R;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    //region init
    final Backend backend1 = BackendFactory.getBackend();
    EditText userName;
    EditText password;
    Button newDriver;
    Button enterUser;
    SharedPreferences sharedPref;
    private static final String PREFS_NAME = "driverSharedPreferences";

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        enterUser = (Button) findViewById(R.id.signtbtn);
        enterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try{
                  enterUser.setVisibility(View.GONE);
                  checkLogin(userName.getText().toString(),password.getText().toString());
              }
              catch (Exception e)
              {
                  e.getMessage();
                  enterUser.setVisibility(View.VISIBLE);
              }
            }
        });
        newDriver = (Button) findViewById(R.id.ndriver);
        newDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent addDriver = new Intent(Login.this, AddDriver.class);
                    startActivity(addDriver);
            }
        });
        //      shared preferences
        userName.setText(sharedPref.getString("username",""));
        password.setText(sharedPref.getString("password",""));
    }
    public void checkLogin(String Username, String Password) {
        final Backend backend = BackendFactory.getBackend();
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                backend.checkLogin(Username , enterUser(), Login.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", userName.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.commit();
                return null;
            }
        }.execute(this);
        enterUser.setVisibility(View.VISIBLE);
    }
    public int enterUser() {
        byte[] x = password.getText().toString().getBytes();
        int out=0;
        for (int i = 0; i < x.length; i++) {
            out+= (int) ((byte) (x[i] ^ userName.length()));
        }
        return  out*out;
    }
}
