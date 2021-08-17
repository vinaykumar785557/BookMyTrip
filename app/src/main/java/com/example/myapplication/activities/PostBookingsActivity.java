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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.PostBookingsAdapter;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.BookingModel;

import java.util.ArrayList;

public class PostBookingsActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;

    ArrayList<BookingModel> allBookings;
    TextView txt_no_bookings;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bookings);

        sharedPreferences=getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        databaseHelper=new DatabaseHelper(PostBookingsActivity.this);
        recyclerView = findViewById(R.id.recy_bookings);
        txt_no_bookings=(TextView)findViewById(R.id.txt_no_bookings);

        recyclerView.setLayoutManager(new LinearLayoutManager(PostBookingsActivity.this));

        String userId=sharedPreferences.getString("userId","");
        allBookings=databaseHelper.getAllBookings(userId);

        PostBookingsAdapter postBookingsAdapter=new PostBookingsAdapter(allBookings,this);
        recyclerView.setAdapter(postBookingsAdapter);

        if (allBookings.size()>0)
        {
            // show bookings
            recyclerView.setVisibility(View.VISIBLE); //This view is invisible, but it still takes up space for layout purposes.
            txt_no_bookings.setVisibility(View.GONE); //This view is invisible, and it doesn't take any space for layout purposes.
        }else
        {

            recyclerView.setVisibility(View.GONE);
            txt_no_bookings.setVisibility(View.VISIBLE);
        }

    }
}

