package com.project.secondapp.controller.controller.adapters;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import com.project.secondapp.R;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.entities.Driver;


public class AddDriver extends AppCompatActivity implements View.OnClickListener {

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
        userName =  findViewById(R.id.userName);
        password =  findViewById(R.id.password);
        Fname =  findViewById(R.id.fname);
        Lname =  findViewById(R.id.lname);
        email =  findViewById(R.id.email);
        phone =  findViewById(R.id.phone);
        id =  findViewById(R.id.id);
        passwordCheck =  findViewById(R.id.passwordcheck);
        enterUser =(Button) findViewById(R.id.add);
        enterUser.setOnClickListener(this);
           
        
    }
    public int enterUser() {
        byte[] x = password.getText().toString().getBytes();

        int out=0;
        for (int i = 0; i < x.length; i++) {
            out+= (int) ((byte) (x[i] ^ userName.length()));
        }
        return  out*out;
    }

    @Override
    public void onClick(View v) {
        driver.setEmail(email.getText().toString());
        driver.setPhone(phone.getText().toString());
        driver.setFirstname(Fname.getText().toString());
        driver.setLastName(Lname.getText().toString());
        driver.setPassword(enterUser());
        driver.setId(id.getText().toString());
        driver.setUserName(userName.getText().toString());
        try {
            if (!phone.getText().toString().equals("") && !password.getText().toString().equals("") && !id.getText().toString().equals("") && !Lname.getText().toString().equals("") && !Fname.getText().toString().equals("") && !userName.getText().toString().equals("")) {
                if (!(password.getText().toString().equals(passwordCheck.getText().toString()))) {
                    passwordCheck.setError("סיסמא אינה תואמת");
                    return;
                }
                final Backend backend = BackendFactory.getBackend();
                //backend.addDriver(driver,AddDriver.this);
                new AsyncTask<Context, Void, Void>() {
                    @Override
                    protected Void doInBackground(Context... contexts) {
                        backend.addDriver(driver, contexts[0]);
                        return null;
                    }
                }.execute(this);
            }

            else{
                Toast.makeText(AddDriver.this, "פרטים חסרים, נא למלא את השדות הריקים", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {

        }
    }
}

