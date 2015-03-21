package com.healthhelp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by bhargavsarvepalli on 21/03/15.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    EditText email, phoneNo, name, disease, password;
    Button sigin;

    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.email);
        phoneNo = (EditText) findViewById(R.id.phone);
        name = (EditText) findViewById(R.id.Name);
        disease = (EditText) findViewById(R.id.Disease);
        password = (EditText) findViewById(R.id.password);
        sigin = (Button) findViewById(R.id.SignIn);
        sigin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        System.out.println("Clicked");
        if(v.getId()==R.id.SignIn && validateEmail() && validatePhoneNo()){
            StringBuilder sql = new StringBuilder("insert into user (email, password, name, phoneNo) ")
                    .append(" values ('").append(email.getText().toString()).append("','")
                    .append(password.getText().toString()).append("','")
                    .append(name.getText().toString()).append("',")
                    .append(phoneNo.getText().toString()).append(")");
            System.out.println(sql.toString());
            new DBAsyncTask().execute(sql.toString());

        }else{
            System.out.println("Validation failed");
        }
    }



    public boolean validateEmail(){
        if (TextUtils.isEmpty(email.getText().toString())) {
            //.setError(getString(R.string.error_field_required));
            //focusView = mEmailView;
            return false;
        } else if (email.getText().toString().contains("@")){
            return true;
        }
        return false;
    }

    public boolean validatePhoneNo(){
        if(TextUtils.isEmpty(phoneNo.getText().toString())){
            return false;
        }else if(TextUtils.isDigitsOnly(phoneNo.getText().toString()) && phoneNo.getText().toString().length() == 10){
            return true;
        }

        return false;
    }
}
