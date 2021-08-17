package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.BookingModel;
import com.example.myapplication.models.BookingModel;

import java.util.List;

public class PostBookingsAdapter extends RecyclerView.Adapter<PostBookingsAdapter.MyViewHolder>
{
        List<BookingModel> bookings;
        Context context;
// constructor
        public PostBookingsAdapter(List<BookingModel> bookings,Context context)
        {
        this.bookings = bookings;
        this.context=context;
        }
// in this method create view for the user
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_bookings, viewGroup, false);
                return new MyViewHolder(itemView);
        }
// this method bind the data with list of items
        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i)
        {
                viewHolder.txt_from_and_to.setText(bookings.get(i).getFrom()+" -> "+bookings.get(i).getTo());
                viewHolder.txt_date.setText("Date: "+bookings.get(i).getDate());
                viewHolder.txt_number_of_persons.setText("Number of persons: "+bookings.get(i).getNumberOfPersons());
                viewHolder.txt_total_price.setText("Total Amount: "+bookings.get(i).getTotalAmount()+" "+context.getString(R.string.currency));
        }

// this method indicates how many items or rows in the data set that will be presented in the adapter view
        @Override
        public int getItemCount()
        {
          return bookings.size();
        }

// define view and get text or image from xml from their id
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_from_and_to,txt_date,txt_number_of_persons,txt_total_price;
            public MyViewHolder(View itemView)
            {
                super(itemView);
                txt_from_and_to = itemView.findViewById(R.id.txt_from_and_to);
                txt_date = itemView.findViewById(R.id.txt_date);
                txt_number_of_persons = itemView.findViewById(R.id.txt_number_of_persons);
                txt_total_price = itemView.findViewById(R.id.txt_total_price);
            }
        }
}
