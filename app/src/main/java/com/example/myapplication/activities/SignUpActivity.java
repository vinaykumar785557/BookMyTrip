package com.example.myapplication.activities;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.SignUpUserModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_signup;
    TextView txt_loin;
    EditText edt_email,edt_password,edt_userName;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txt_loin=(TextView)findViewById(R.id.txt_login);
        edt_email=(EditText) findViewById(R.id.edt_email);
        edt_password=(EditText) findViewById(R.id.edt_password);
        edt_userName=(EditText) findViewById(R.id.edt_userName);
        btn_signup=(Button) findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(this);
        txt_loin.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(SignUpActivity.this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.txt_login:
                navigateToLoginScreen();
                break;
            case R.id.btn_signup:
                signUpProcess();
                break;
        }
    }

    private void signUpProcess()
    {
            String name=edt_userName.getText().toString();
            String email=edt_email.getText().toString();
            String password=edt_password.getText().toString();
// validing email, password,usernames
        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(email)) {

                if (isValidEmail(email))
                {
                    if (!TextUtils.isEmpty(password)) {

                        if(isValidPassword(password))
                        {
                            signUp(name,email,password);
                        }else
                        {
                            Toast.makeText(SignUpActivity.this, "Password should have atleast 6 letters", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    }
                }else
                {
                    Toast.makeText(SignUpActivity.this, "Please enter user valid emailID", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(SignUpActivity.this, "Please enter email Id", Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(SignUpActivity.this, "Please enter user name", Toast.LENGTH_LONG).show();
        }
    }

    private void signUp(String name,String email,String password)
    {
        if (!databaseHelper.isUserExistsAlready(email)) // if email is not in database
        {
            SignUpUserModel signUpUserModel=new SignUpUserModel(name,email,password); // using signup user modal class
            long isadded=databaseHelper.addUser(signUpUserModel);
            if (isadded>-1)
            {
                Toast.makeText(SignUpActivity.this, "Registered Successfully..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
            }
        }else
        {
            // if email is already in use
            Toast.makeText(SignUpActivity.this, "Email Id Alreay exists", Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(this,LoginActivity.class)); // redirecting to login after signup
        finish();
    }

// email address validation
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
// password validation
    public static boolean isValidPassword(String target) {
        return (!TextUtils.isEmpty(target) && target.length()>5);
    }
}

