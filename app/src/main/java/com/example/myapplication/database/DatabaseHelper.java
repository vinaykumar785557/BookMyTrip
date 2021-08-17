package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.models.BookingModel;
import com.example.myapplication.models.SignUpUserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Vinay.db";


    // booking table name
    private static final String TABLE_BOOKINGS = "bookings";
    //booking table constants
    private static final String COLUMN_BOOKING_ID = "booking_id";
    private static final String COLUMN_BOOKING_FROM = "booking_from";
    private static final String COLUMN_BOOKING_TO = "booking_to";
    private static final String COLUMN_BOOKING_DATE = "booking_date";
    private static final String COLUMN_BOOKING_TOTAL_AMOUNT = "booking_total_amount";
    private static final String COLUMN_BOOKING_NUMBER_OF_PERSONS = "booking_number_of_persons";
    private static final String COLUMN_BOOKING_USER_ID = "booking_user_id";


    //booking table creation
    private String CREATE_BOOKING_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
            + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_BOOKING_FROM + " TEXT,"
            + COLUMN_BOOKING_TOTAL_AMOUNT + " TEXT,"+ COLUMN_BOOKING_NUMBER_OF_PERSONS + " TEXT,"
            +COLUMN_BOOKING_USER_ID + " TEXT,"
            + COLUMN_BOOKING_TO + " TEXT," + COLUMN_BOOKING_DATE + " TEXT" + ")";



    // User table name
    private static final String TABLE_USER = "user";
    //user table constants
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    //creating user table
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addBooking(BookingModel booking)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(COLUMN_BOOKING_FROM, booking.getFrom());
        values.put(COLUMN_BOOKING_TO, booking.getTo());
        values.put(COLUMN_BOOKING_DATE, booking.getDate());
        values.put(COLUMN_BOOKING_TOTAL_AMOUNT, booking.getTotalAmount());
        values.put(COLUMN_BOOKING_NUMBER_OF_PERSONS, booking.getNumberOfPersons());
        values.put(COLUMN_BOOKING_USER_ID,booking.getUserId());
        // Inserting Row
        long rowId=db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return rowId;
    }

    public ArrayList<BookingModel> getAllBookings(String userId) {

        ArrayList<BookingModel> bookingList = new ArrayList<BookingModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_BOOKINGS+" WHERE "+COLUMN_BOOKING_USER_ID+" = ?",new String[]{userId}); //The sort order
        try
        {
            if (cursor.moveToFirst()) {
                do {
                    BookingModel booking = new BookingModel(
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_FROM)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TO)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_DATE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_NUMBER_OF_PERSONS)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TOTAL_AMOUNT)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_USER_ID))
                    );

                    bookingList.add(booking);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e)
        {
            cursor.close();
            db.close();
        }

        // array of columns to fetch

        // sorting orders



        // return user list
        return bookingList;
    }




    public long addUser(SignUpUserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        long rowId=db.insert(TABLE_USER, null, values);
        db.close();
        return rowId;
    }


    public boolean login(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    public boolean isUserExistsAlready(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
