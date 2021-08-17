package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.RecommendedPlacesModel;

import java.util.List;

public class RecommendedPlacesAdapter extends RecyclerView.Adapter<RecommendedPlacesAdapter.MyViewHolder>
{
        List<RecommendedPlacesModel> placesList;

        public RecommendedPlacesAdapter(List<RecommendedPlacesModel> placesList)
        {
        this.placesList = placesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recommended_places, viewGroup, false);
                return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i)
        {
                viewHolder.txt_place.setText(placesList.get(i).getPlaceName());
                viewHolder.img_place.setImageResource(placesList.get(i).getImageId());
        }


        @Override
        public int getItemCount()
        {
          return placesList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_place;
            ImageView img_place;
            public MyViewHolder(View itemView)
            {
                super(itemView);
                img_place = itemView.findViewById(R.id.img_place);
                txt_place = itemView.findViewById(R.id.txt_place);
            }
        }
}
