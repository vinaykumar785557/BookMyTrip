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

public class BookingSuccessActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_goToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        btn_goToHome=(Button)findViewById(R.id.btn_goToHome);
        btn_goToHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
       switch (v.getId())
       {
           case R.id.btn_goToHome:
               finish();
               break;
       }
    }

}

