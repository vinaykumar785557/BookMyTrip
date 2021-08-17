package com.example.myapplication.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements LocationListener/*implements OnMapReadyCallback*/ {

    private GoogleMap mMap;

    String whetherApiKey = "9cb11273890f4f2b9e006d11c1969b09";
    protected LocationManager locationManager;
    boolean isLocationDetected=false;
    RequestQueue requestQueue;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;

    TextView tv_degrees,tv_city;
    //ProgressBar progressBar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        sharedPreferences=getSharedPreferences(getPackageName(),Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //
        progressDialog.setCancelable(false);

        tv_degrees=(TextView)findViewById(R.id.tv_degrees);
        tv_city=(TextView)findViewById(R.id.tv_city);
        //progressBar=(ProgressBar)findViewById(R.id.progressBar_cyclic);

        // Create map fragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);



        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
                showDialog();
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
                {
                    @Override
                    public void onMapClick(LatLng latLng)
                    {
                        map.clear(); // Removes all markers, overlays, and polylines from the map.
                        map.addMarker(new MarkerOptions().position(latLng));
                        String weather_url1 = "https://api.weatherbit.io/v2.0/current?" + "lat=" + latLng.latitude + "&lon=" + latLng.longitude + "&key=" + whetherApiKey ;
                        getTemp(weather_url1);
                        //initCameraIdle();
                    }
                });
                LatLng latLng=null;
                if(!sharedPreferences.getString("lattitude","").equals("")&&
                        !sharedPreferences.getString("longitide","").equals(""))
                {
                    Log.e("locationDetails:","yes latlng");
                     latLng = new LatLng(Double.parseDouble(sharedPreferences.getString("lattitude","")),
                             Double.parseDouble(sharedPreferences.getString("longitide","")));
                     map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3));
                     map.addMarker(new MarkerOptions().position(latLng));
                     initCameraIdle();
                }else
                {
                    Log.e("locationDetails:","no latlng");
                    latLng = new LatLng(20.5937, 78.9629);
                    map.addMarker(new MarkerOptions().position(latLng));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3));
                    initCameraIdle();
                }
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    private void initCameraIdle() {
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if(!sharedPreferences.getString("lattitude","").equals("")&&
                        !sharedPreferences.getString("longitide","").equals(""))
                {
                    center = map.getCameraPosition().target;
                    map.clear();

                    Log.e("locationDetails:","changed="+center.latitude+"  "+center.longitude);
                    String weather_url1 = "https://api.weatherbit.io/v2.0/current?" + "lat=" + center.latitude + "&lon=" + center.longitude + "&key=" + whetherApiKey ;
                    LatLng locationLatLng = new LatLng(center.latitude, center.longitude);
                    map.addMarker(new MarkerOptions().position(locationLatLng));
                    getTemp(weather_url1);
                }
            }
        });
    }

    void getTemp(String url) {

        Log.e("locationDetails:","request is="+url);

        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("data");
                    JSONObject obj2 = arr.getJSONObject(0);

                    tv_degrees.setText(obj2.getString("temp")+"\u2103");
                    tv_city.setText(" in " + obj2.getString("city_name"));


                    Log.e("locationDetails:","response is="+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(MapsActivity.this,"Whether can not detected at this location",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onLocationChanged(Location location) {


        if(isLocationDetected)
        {
            return;
        }
        isLocationDetected=false;
        Log.e("locationDetails:","detected inside");

        if(sharedPreferences.getString("lattitude","").equals("")&&
                sharedPreferences.getString("longitide","").equals(""))
        {
            Log.e("locationDetails:","detected"+location.getLatitude()+"  "+location.getLongitude());
            String weather_url1 = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&key=" + whetherApiKey ;
            editor.putString("lattitude",""+location.getLatitude());
            editor.putString("longitide",""+location.getLongitude());
            editor.apply();


            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3));
            map.addMarker(new MarkerOptions().position(latLng));
            initCameraIdle();

            //getTemp(weather_url1);
        }

        //https://api.weatherbit.io/v2.0/current?lat=35.7796&lon=-78.6382&key=API_KEY&include=minutely

    }


    void showDialog()
    {
        progressDialog.show();
    }

    void hideDialog()
    {
      progressDialog.dismiss();
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Allow Location")
                        .setMessage("Enable Location Permission Kindly")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}

