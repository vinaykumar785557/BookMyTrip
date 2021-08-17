package com.example.myapplication.activities;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_bookTicket,btn_postBooking,btn_places,btn_whether;
    TextView txt_logout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        btn_bookTicket=(Button)findViewById(R.id.btn_bookTicket);
        btn_postBooking=(Button)findViewById(R.id.btn_postBooking);
        btn_places=(Button)findViewById(R.id.btn_places);
        btn_whether=(Button)findViewById(R.id.btn_whether);
        txt_logout=(TextView)findViewById(R.id.txt_logout);

        btn_bookTicket.setOnClickListener(this);
        btn_postBooking.setOnClickListener(this);
        btn_places.setOnClickListener(this);
        btn_whether.setOnClickListener(this);
        txt_logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_bookTicket:
                startActivity(new Intent(HomeActivity.this,BookTicketActivity.class));
                break;
            case R.id.btn_postBooking:
                startActivity(new Intent(HomeActivity.this,PostBookingsActivity.class));
                break;
            case R.id.btn_places:
                startActivity(new Intent(HomeActivity.this,RecommendedPlacesActivity.class));
                break;
            case R.id.btn_whether:
                startActivity(new Intent(HomeActivity.this,WhetherActivity.class));
                break;
            case R.id.txt_logout:
                createLogoutDialog();
                break;

        }
    }

    public void createLogoutDialog()
    {
        // creating object using alert dialog class
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        // setting the title of alert dialog
        builder.setTitle("Do you want to logout?");

        //set the positive button for alertdialog and we can implement click event of a positive button.
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
               logout(); // calling logout method
            }
        });
        //set the negative button for alertdialog and we can implement click event of a positive button.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // close the dialog
            }
        });

        AlertDialog alert11 = builder.create(); // creating dialog box
        alert11.show(); // showing alert message
    }

    private void logout()
    {
        // Storing the key and its value as the data fetched from edittext
        editor.putBoolean("isLoggedIn",false);
        editor .putString("userId","");
        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        editor.commit();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class)); // return to login screen
        finish(); //
    }

}

