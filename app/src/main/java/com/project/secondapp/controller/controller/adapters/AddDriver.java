package com.project.secondapp.controller.controller.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.project.secondapp.controller.model.entities.Driver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AddDriver extends AppCompatActivity {

    Driver driver = new Driver();
    EditText Fname;
    EditText Lname;
    EditText userName;
    EditText password;
    EditText email;
    EditText passwordCheck;
    EditText id;
    EditText phone;
    Button enterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        Fname = (EditText) findViewById(R.id.fname);
        Lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        id = (EditText) findViewById(R.id.id);
        passwordCheck = (EditText) findViewById(R.id.passwordcheck);
        enterUser = (Button) findViewById(R.id.add);
        enterUser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                try {
                    if (phone.getText().toString() != "" && password.getText().toString() != "" && id.getText().toString() != "" && Lname.getText().toString() != "" && Fname.getText().toString() != "" && userName.getText().toString() != "") {
                        if (!(password.getText().toString().equals(passwordCheck.getText().toString()))) {
                            passwordCheck.setError("סיסמא אינה תואמת");
                            return;
                        }
                        driver.setEmail(email.getText().toString());
                        driver.setPhone(phone.getText().toString());
                        driver.setFirstName(Fname.getText().toString());
                        driver.setLastName(Lname.getText().toString());
                        driver.setPassword(enterUser());
                        driver.setId(id.getText().toString());
                        driver.setUserName(userName.getText().toString());
                        Backend backend = BackendFactory.getBackend();
                        //backend.addDriver(driver, getApplicationContext());

                        new AsyncTask<Context, Void, Void>() {

                            @Override
                            protected Void doInBackground(Context... contexts) {
                                backend.addDriver(driver, contexts[0]);
                                return null;
                            }
                        };
                    } else {
                        Toast.makeText(getApplicationContext(), "פרטים חסרים, נא למלא את השדות הריקים", Toast.LENGTH_LONG).show();
                    }

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
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

