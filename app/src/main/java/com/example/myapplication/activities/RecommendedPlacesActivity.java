package com.example.myapplication.activities;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.RecommendedPlacesAdapter;
import com.example.myapplication.models.RecommendedPlacesModel;

import java.util.ArrayList;

public class RecommendedPlacesActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_places);
        recyclerView = findViewById(R.id.recy_places);

        ArrayList<RecommendedPlacesModel> placesList=getPlaces();

        RecommendedPlacesAdapter placesAdapter= new RecommendedPlacesAdapter(placesList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(placesAdapter);
    }

    ArrayList<RecommendedPlacesModel> getPlaces()
    {
        ArrayList<RecommendedPlacesModel> places=new ArrayList<>();

        places.add(new RecommendedPlacesModel("Hyderabad",R.drawable.hyderabad));
        places.add(new RecommendedPlacesModel("Mumbai",R.drawable.mumbai));
        places.add(new RecommendedPlacesModel("Delhi",R.drawable.delhi));
        places.add(new RecommendedPlacesModel("Chennai",R.drawable.chennai));
        places.add(new RecommendedPlacesModel("Bangalore",R.drawable.bangalore));
        places.add(new RecommendedPlacesModel("vizag",R.drawable.vizag));
        places.add(new RecommendedPlacesModel("Agra",R.drawable.agra));
        places.add(new RecommendedPlacesModel("Kashmir",R.drawable.kashmir));

       return places;

    }

}

