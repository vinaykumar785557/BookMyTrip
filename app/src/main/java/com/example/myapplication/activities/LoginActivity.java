package com.example.myapplication.activities;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_login;
    TextView txt_signUp;
    EditText edt_email,edt_password;
    DatabaseHelper databaseHelper;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences=getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        txt_signUp=(TextView)findViewById(R.id.txt_signUp);
        edt_email=(EditText) findViewById(R.id.edt_email);
        edt_password=(EditText) findViewById(R.id.edt_password);
        btn_login=(Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
        txt_signUp.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(LoginActivity.this);

    }

    @Override
    public void onClick(View v)
    {
       switch (v.getId())
       {
           case R.id.txt_signUp:
               navigateToSignUpScreen(); // calling signup method here
               break;
           case R.id.btn_login:
               loginProcess(); // calling login method here
               break;
       }
    }

    private void loginProcess()
    {
        String email= edt_email.getText().toString();
        String password=edt_password.getText().toString();

        if (!TextUtils.isEmpty(email)) // email field not empty
        {
            if (!TextUtils.isEmpty(password)) // password is not empty
            {
                Login(email,password); // calling login method
            }else
            {
                // if password not entered
                Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            // if email id is not entered this condition executes
            Toast.makeText(LoginActivity.this,"Please enter email Id",Toast.LENGTH_LONG).show();
        }
    }

    private void Login(String email,String password)
    {
      if (databaseHelper.login(email,password))
      {
          //putting loggedin as true

          editor.putBoolean("isLoggedIn",true);
          editor .putString("userId",email);
          editor.commit();

          startActivity(new Intent(this, HomeActivity.class)); //redirecting from login to home screen

          finish(); //finish the activity

      }else
      {
          Toast.makeText(LoginActivity.this,"Entered wrong credentials..",Toast.LENGTH_LONG).show();
      }
    }

    private void navigateToSignUpScreen()
    {
        //redirecting from login to signup screen
        startActivity(new Intent(this,SignUpActivity.class));
        finish();
    }
}

