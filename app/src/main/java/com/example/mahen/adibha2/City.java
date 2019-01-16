package com.example.mahen.adibha2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mahen.adibha2.DBhelper.dbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_NAME;
import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_PREFS;

public class City extends AppCompatActivity {
//    RecyclerView packView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Boolean check_time = false, check_date = false;

    String pickupLocation, dateon, timeat;
//    ArrayList<String> base_fare_p = new ArrayList<>(Arrays.asList("399", "599", "899", "1299", "1699","1999", "2299"));

    String userid = "", v_type = "";
    String fare;
    String fare1;
    String fare2;

    TextView pickupLocTxt, dateonTxt, timeatTxt, changeval, fareTxt, per_kmTxt, per_hrTxt,dropLocTxt;
//    ListView listView;
    String packageid;

    dbHelper yourrides;
    String datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get tool bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //toolbar action to go back is any activity exists
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //textview initialization
        pickupLocTxt = findViewById(R.id.rentalpickup);
        dateonTxt = findViewById(R.id.dateon);
        timeatTxt = findViewById(R.id.timeat);
        changeval = findViewById(R.id.changedate);
        per_hrTxt = findViewById(R.id.per_hr);
        per_kmTxt = findViewById(R.id.per_km);
        fareTxt = findViewById(R.id.fare);
        dropLocTxt=findViewById(R.id.rentaldrop);

        //listview initial
//        listView = findViewById(R.id.packdetails);
//        listView.setScrollBarSize(8);
//        listView.setScrollContainer(false);

        //getting shared preferences
        SharedPreferences userpref;
        userpref = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        userid = userpref.getString(USER_NAME, "");

        //getting intent
        Intent intent = getIntent();
        pickupLocation = intent.getStringExtra("pick");
        dateon = intent.getStringExtra("date");
        timeat = intent.getStringExtra("time");
        v_type = intent.getStringExtra("v_type");

        //initializing textviews
        pickupLocTxt.setText(pickupLocation);
        dateonTxt.setText(dateon);
        timeatTxt.setText(timeat);
        dropLocTxt.setText(intent.getStringExtra("drop"));

        //initialiseing databse
        yourrides = new dbHelper(this);


        //recylerview components
//        packView = findViewById(R.id.packdetails);
//        packView.setHasFixedSize(true);

//        RecyclerView.LayoutManager rentalLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        packView.setLayoutManager(rentalLayout);
//
//        RecyclerView.Adapter cardAdapter = new cardAdapter(rental.this, packages);
//        packView.setAdapter(cardAdapter);


        switch (v_type)
        {
            case "Prime":
                v_type = "2";
                break;
            case "SUV":
                v_type = "3";
                break;
            default:
                break;
        }

    }


    public void confirmAlert(String fare, String fare1, String fare2) {
//        LayoutInflater inflater = getLayoutInflater();
//        View alertLayout = inflater.inflate(R.layout.rental_bottom_sheet, null);
//        TextView base_fare = alertLayout.findViewById(R.id.base_fare);
//        TextView exclusive_km = alertLayout.findViewById(R.id.exclusive_km);
//        TextView exclusive_hr = alertLayout.findViewById(R.id.exclusive_hr);
//
//        base_fare.setText(fare);
//        exclusive_km.setText(fare2);
//        exclusive_hr.setText(fare1);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Confirm Booking");
//        builder.setView(alertLayout);
//        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                confirmBooking(loc,datetime,packageid,vehicle_id);
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                Toast.makeText(getApplicationContext(), "Progress is " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.show();
        per_hrTxt.setText(fare1);
        per_kmTxt.setText(fare2);
        fareTxt.setText(fare);


    }

    private void confirmBooking(final String loc, final String datetime, final String packagei, final String traveltype) {

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CUSTOMER_RENTAL_BOOK, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.i("Responce......outside", response);
//
//                if (response.equalsIgnoreCase("success")) {
//                    Log.i("Responce....in", response);
//                    Toast.makeText(getApplicationContext(), "Your booking placed", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.i("Responce.............", response);
//                    Toast.makeText(getApplicationContext(), "Responce is  " + response, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("Error", "volley response error");
//                Toast.makeText(getApplicationContext(), "Responce error failed   " + error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("CUS_ID", userid);
//                params.put("BOOK_TIME", datetime);
//                params.put("ORIGIN", loc);
//                params.put("TRAVEL_TYPE", v_type);
//                params.put("PACKAGE_ID", packageid);
//                params.put("FARE", fare);
//
//                Log.i("LNG", traveltype);
//                Log.i("LNG", loc);
//                Log.i("LNG", datetime);
//                Log.i("LNG", packagei);
//                return params;
//
//            }
//        };
//
//        //inseting into  the iteluser table
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
    }

    // Opens Time and Date On Click
    public void timeChange(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        final TimePickerDialog timePickerDialog2 = new TimePickerDialog(City.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                    }

                }, mHour, mMinute, false);
        timePickerDialog2.show();
        final DatePickerDialog datePickerDialog2 = new DatePickerDialog(City.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//                                                datein.setText(String.format("%d-%d-%d", dayOfMonth, monthOfYear + 1, year));
//                                Toast.makeText(RentalView.this,dayOfMonth + "-" + (monthOfYear + 1) + "-" + year,Toast.LENGTH_SHORT).show();
                    }

                }, mYear, mMonth, mDay);


        datePickerDialog2.show();

    }

    public void getPackage(View view) {
        TextView value = view.findViewById(R.id.rentalpickup);//Changed for Temporary use   -Naveen
        String val = value.getText().toString();
        Toast.makeText(this, "hello toast..." + val, Toast.LENGTH_SHORT).show();
    }

    public void confirmBooking(View view) {

        datetime = dateon+" "+timeat;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CUSTOMER_RENTAL_BOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Responce......outside", response);

                if (response.equalsIgnoreCase("success")) {
                    Log.i("Responce....in", response);
                    Toast.makeText(getApplicationContext(), "Your booking placed", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Responce.............", response);
                    Toast.makeText(getApplicationContext(), "Responce is  " + response, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    yourRides();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", "volley response error");
                Toast.makeText(getApplicationContext(), "Responce error failed   " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("CUS_ID", userid);
                params.put("BOOK_TIME", datetime);
                params.put("ORIGIN", pickupLocation);
                params.put("TRAVEL_TYPE", v_type);
                params.put("PACKAGE_ID", packageid);
                params.put("FARE", fare);

//                Log.i("LNG", traveltype);
//                Log.i("LNG", loc);
//                Log.i("LNG", datetime);
//                Log.i("LNG", packagei);

                return params;

            }
        };

        //inseting into  the iteluser table
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void yourRides() {
        yourrides.insertdata("1",datetime, "Rental",v_type, pickupLocation,"");
        Log.i("value","inserted");
    }
}
