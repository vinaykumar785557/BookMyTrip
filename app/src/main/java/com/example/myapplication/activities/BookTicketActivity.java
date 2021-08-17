package com.example.myapplication.activities;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.FromAndToAdapter;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.BookingModel;
import com.example.myapplication.models.FromAndToLocations;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class BookTicketActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    ArrayList<FromAndToLocations> fromLocations,toLocations;
    Spinner spinner_from,spinner_to;
    String selectedDate="";

    LinearLayout lyt_date,lyt_number;
    EditText edt_number_of_persons;
    TextView txt_per_ticket_price,txt_total_price,txt_date;
    Button btn_payment;
    int day,month,year;

    DatabaseHelper databaseHelper;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        sharedPreferences=getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        databaseHelper = new DatabaseHelper(BookTicketActivity.this);

        fromLocations=addLocarions(true);
        toLocations=addLocarions((false));

        spinner_from= (Spinner) findViewById(R.id.spinner_from);
        spinner_to= (Spinner) findViewById(R.id.spinner_to);

        lyt_date= (LinearLayout) findViewById(R.id.lyt_date);
        edt_number_of_persons= (EditText) findViewById(R.id.edt_number_of_persons);
        txt_per_ticket_price= (TextView) findViewById(R.id.txt_per_ticket_price);
        txt_total_price= (TextView) findViewById(R.id.txt_total_price);
        btn_payment= (Button) findViewById(R.id.btn_payment);
        txt_date=(TextView)findViewById(R.id.txt_date);
        lyt_number=(LinearLayout)findViewById(R.id.lyt_number);

        txt_per_ticket_price.setText("Per Ticket Price: 0 "+getString(R.string.currency));
        txt_total_price.setText("Total Price: 0 "+getString(R.string.currency));

        lyt_date.setOnClickListener(this);
        btn_payment.setOnClickListener(this);

        //edt_number_of_persons.addTextChangedListener(new Text);

        FromAndToAdapter customAdapter=new FromAndToAdapter(getApplicationContext(),fromLocations);
        spinner_from.setAdapter(customAdapter);

        FromAndToAdapter customAdapter1=new FromAndToAdapter(getApplicationContext(),toLocations);
        spinner_to.setAdapter(customAdapter1);

        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                visibilityControl();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                visibilityControl();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        // adding no of persons
        edt_number_of_persons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
               visibilityControl();
            }
        });

    }

    @Override
    public void onClick(View v)
    {
       switch (v.getId())
       {
           case R.id.lyt_date: // if clicked on date picker
               showDatePicker();
                   break;
           case R.id.btn_payment: // if clicked on book ticket
               validatePayment();
               break;
       }
    }

    private void validatePayment() {
        if (spinner_from.getSelectedItemPosition()>0)
        {
            if (spinner_to.getSelectedItemPosition()>0)
            {
                if (!TextUtils.isEmpty(selectedDate))
                {
                    if (!TextUtils.isEmpty(edt_number_of_persons.getText().toString()))
                    {
                        bookTicket(); // calling book ticket method if all fields are selected
                    }else
                    {
                        // calling custom toast method
                        showCustomToast("Please Enter Number of persons "); // no of persons not selected
                    }
                }else
                {
                    showCustomToast("Please select Date "); // if date is not selected
                }
            }else
            {
                showCustomToast("Please select To "); // if To location is not selected
            }
        }else
        {
            showCustomToast("Please select From "); // from location is not selected
        }
    }

    private void bookTicket()
    {
        String from=fromLocations.get(spinner_from.getSelectedItemPosition()).getLocationName();
        String to=toLocations.get(spinner_to.getSelectedItemPosition()).getLocationName();

        if (from.equalsIgnoreCase(to)) // if both from and to locations are same
        {
            showCustomToast("From and To Selections are same.Kindly change it");
            return;
        }

        int charge=Integer.parseInt(fromLocations.get(spinner_from.getSelectedItemPosition()).getCharge());
        int personNumber=Integer.parseInt(edt_number_of_persons.getText().toString());

        int totalCharge=(charge*personNumber);

        String userId=sharedPreferences.getString("userId","");
        BookingModel bookingModel=new BookingModel(from,to,selectedDate,""+personNumber,""+totalCharge,userId);
        long rowiD=databaseHelper.addBooking(bookingModel);
        if (rowiD>-1)
        {
          //  showCustomToast("Booked Successfully");
            startActivity(new Intent(BookTicketActivity.this,BookingSuccessActivity.class));
            finish();
        }

    }

    void showDatePicker() // date picker dialog
    {

        Calendar now = Calendar.getInstance(); // get current date

        DatePickerDialog dpd=null;

        if (selectedDate.equals(""))
        {
            //Get the DatePicker instance from DatePickerDialog
             dpd = DatePickerDialog.newInstance(
                    BookTicketActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }else
        {
            dpd = DatePickerDialog.newInstance(
                    BookTicketActivity.this,
                    year,
                    month,
                    day
            );
        }


        dpd.setMinDate(now); //Set the DatePicker minimum date selection to current date
        // If you're calling this from a support Fragment
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");

        // If you're calling this from an AppCompatActivity
        // dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }


    ArrayList<FromAndToLocations> addLocarions(boolean iSfrom)
    {
        ArrayList<FromAndToLocations> fromAndToLocations=new ArrayList<>();

        fromAndToLocations.add(new FromAndToLocations(iSfrom?"Select From":"Select To","100"));
        fromAndToLocations.add(new FromAndToLocations("London","100"));
        fromAndToLocations.add(new FromAndToLocations("Birmingham","200"));
        fromAndToLocations.add(new FromAndToLocations("Bristol","300"));
        fromAndToLocations.add(new FromAndToLocations("Southampton","400"));
        fromAndToLocations.add(new FromAndToLocations("Nottingham","500"));
        fromAndToLocations.add(new FromAndToLocations("Mumbai","600"));
        fromAndToLocations.add(new FromAndToLocations("Pune","100"));
        fromAndToLocations.add(new FromAndToLocations("Nagpur","200"));
        fromAndToLocations.add(new FromAndToLocations("Ranchi","300"));
        fromAndToLocations.add(new FromAndToLocations("Chennai","400"));
        fromAndToLocations.add(new FromAndToLocations("Trivendram","500"));
        fromAndToLocations.add(new FromAndToLocations("Kochi","600"));
        fromAndToLocations.add(new FromAndToLocations("Munnar","100"));
        fromAndToLocations.add(new FromAndToLocations("Hyderabad","200"));
        fromAndToLocations.add(new FromAndToLocations("Amaravathi","300"));
        fromAndToLocations.add(new FromAndToLocations("Bangalore","400"));
        fromAndToLocations.add(new FromAndToLocations("Goa","500"));
        fromAndToLocations.add(new FromAndToLocations("patna","600"));
        fromAndToLocations.add(new FromAndToLocations("munnar","700"));

        return  fromAndToLocations;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        month=monthOfYear;
        this.year=year;
        day=dayOfMonth;

        selectedDate=""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        txt_date.setText(selectedDate);

        Log.e("selectedDatee",selectedDate); // see the selected date on Logcat
    }

    void visibilityControl() //  show no of persons selection option when from and to selected
    {
        if (spinner_from.getSelectedItemPosition()>0&&spinner_to.getSelectedItemPosition()>0)
        {

            lyt_number.setVisibility(View.VISIBLE);

            int charge=Integer.parseInt(fromLocations.get(spinner_from.getSelectedItemPosition()).getCharge());
            String personNumber=edt_number_of_persons.getText().toString();

            int totalNumber=0;
            if (!personNumber.isEmpty())
            {
                totalNumber=Integer.parseInt(personNumber);
            }

            txt_per_ticket_price.setText("Per Ticket Price: "+charge+" "+getString(R.string.currency));
            txt_total_price.setText("Total Price: "+(charge*totalNumber)+" "+getString(R.string.currency));
        }else
        {
            lyt_number.setVisibility(View.GONE);
            txt_per_ticket_price.setText("Per Ticket Price: 0 "+getString(R.string.currency));
            txt_total_price.setText("Total Price: 0 "+getString(R.string.currency));
        }
    }

    void showCustomToast(String message)
    {
        Toast.makeText(BookTicketActivity.this,message,Toast.LENGTH_LONG).show();
    }
}

