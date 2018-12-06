package com.project.secondapp.controller.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.secondapp.R;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;

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
              try{
                  checkLogin(userName.getText().toString(),password.getText().toString());
              }
              catch (Exception e)
              {
                  e.getMessage();
              }

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
    public void checkLogin(String Username, String Password) {
        final Backend backend = BackendFactory.getBackend();
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                backend.checkLogin(Username , enterUser(), MainActivity.this);
                return null;
            }
        }.execute(this);
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
