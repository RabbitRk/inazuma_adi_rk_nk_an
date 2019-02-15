package com.example.mahen.adibha2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.mahen.adibha2.DBhelper.dbHelper;
import com.example.mahen.adibha2.services.InternetBroadCast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_NAME;
import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_PREFS;

public class City extends AppCompatActivity {
    //    RecyclerView packView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Boolean check_time = false, check_date = false;

    String pickupLocation, dropLocation, dateon, timeat;
    String oriLat, oriLng, destLat, destLng, travel_type;
//    ArrayList<String> base_fare_p = new ArrayList<>(Arrays.asList("399", "599", "899", "1299", "1699","1999", "2299"));

    String userid = "", v_type = "";
    String base_fare;
    String distanceto;
    String duration;
    String user_id;

    TextView pickupLocTxt, dateonTxt, timeatTxt, changeval, fareTxt, distanceTxt, durationTxt, dropLocTxt;
    //    ListView listView;
    String packageid;

    dbHelper yourrides;
    String datetime;

    SharedPreferences userpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Checks Network is on or not
        InternetBroadCast receiver;
        IntentFilter filter;
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new InternetBroadCast();
        registerReceiver(receiver, filter);

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
        pickupLocTxt = findViewById(R.id.cityPickup);
        dropLocTxt = findViewById(R.id.cityDrop);
        dateonTxt = findViewById(R.id.dateon);
        timeatTxt = findViewById(R.id.timeat);
        changeval = findViewById(R.id.changedate);
        distanceTxt = findViewById(R.id.distance);
        durationTxt = findViewById(R.id.duration);
        fareTxt = findViewById(R.id.fare);

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
        pickupLocation = intent.getStringExtra("pick_up");
        dropLocation = intent.getStringExtra("drop");
        dateon = intent.getStringExtra("date");
        timeat = intent.getStringExtra("time");
        v_type = intent.getStringExtra("v_type");
        travel_type = intent.getStringExtra("travel_type");
        oriLat = intent.getStringExtra("ori_lat");
        oriLng = intent.getStringExtra("ori_lng");
        destLat = intent.getStringExtra("dest_lat");
        destLng = intent.getStringExtra("dest_lng");

        //initializing textviews
        pickupLocTxt.setText(pickupLocation);
        dropLocTxt.setText(dropLocation);
        dateonTxt.setText(dateon);
        timeatTxt.setText(timeat);

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


        switch (v_type) {
            case "Auto":
                v_type = "1";
                break;
            case "Prime":
                v_type = "2";
                break;
            case "SUV":
                v_type = "3";
                break;
            default:
                break;
        }

        getuserPrefs();
        getCurrentDateTime();
        getDetails();

    }

    private void getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
        String rideNow_date = df.format(c.getTime());
        String rideNow_time = dft.format(c.getTime());
        dateonTxt.setText(rideNow_date);
        timeatTxt.setText(rideNow_time);
    }

    private void getuserPrefs() {
        userpref = getSharedPreferences(USER_PREFS, MODE_PRIVATE);

        user_id = userpref.getString(USER_NAME, "");

        if ("".equals(user_id))
        {
            Toast.makeText(this, "User ID is not valid", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DISTANCE_CALC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.equals("")) {
                    Log.i("Responce.............", response);
                    try {
                        JSONArray arr = new JSONArray(response);
                        JSONObject jb = arr.getJSONObject(0);
                        distanceto = jb.getString("distance");
                        duration = jb.getString("duration");
                        base_fare = jb.getString("fare");

                        distanceTxt.setText(distanceto);
                        durationTxt.setText(duration);
                        fareTxt.setText(String.valueOf(base_fare));

                        Log.i("distance.......", distanceto);
                        Log.i("duration.......", duration);
                        Log.i("fare.......", base_fare);

//                        confirmAlert(base_fare, duration, distanceto);

                    } catch (JSONException e) {
                        Log.i("Error on catch.....", e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    Log.i("Responce.............", response);
                    Toast.makeText(getApplicationContext(), "Responce is  " + response, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("ORIGIN_LAT", oriLat);
                params.put("ORIGIN_LNG", oriLng);
                params.put("DESTINATION_LAT", destLat);
                params.put("DESTINATION_LNG", destLng);
                params.put("VEHICLE_TYPE", v_type);

                Log.i("ORIGIN_LAT", oriLat);
                Log.i("ORIGIN_LNG", oriLng);
                Log.i("DESTINATION_LAT", destLat);
                Log.i("DESTINATION_LNG", destLng);
                Log.i("VEHICLE_TYPE", v_type);
                return params;
            }
        };

        //inseting into  the iteluser table
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
                        timeatTxt.setText(hourOfDay+":"+minute);
                    }

                }, mHour, mMinute, false);
        timePickerDialog2.show();
        final DatePickerDialog datePickerDialog2 = new DatePickerDialog(City.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                                                dateonTxt.setText(String.format("%d-%d-%d", dayOfMonth, monthOfYear + 1, year));
//                                Toast.makeText(RentalView.this,dayOfMonth + "-" + (monthOfYear + 1) + "-" + year,Toast.LENGTH_SHORT).show();
                    }

                }, mYear, mMonth, mDay);


        datePickerDialog2.show();

    }

    public void confirmBooking(View view) {

        final LoadingButton confirm = findViewById(R.id.booking);
        confirm.startLoading(); //start loading
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        //if details  is true
                        confirm.loadingSuccessful();

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        confirm.setEnabled(true);
                                        reg();
                                    }
                                }, 1000);
                    }
                }, 1500);


    }

    private void reg() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.city_booking_confirm, null);
        final TextView basefareTxt = alertLayout.findViewById(R.id.baseFare);
        final TextView durationTxt = alertLayout.findViewById(R.id.duration);
        final TextView distanceTxt = alertLayout.findViewById(R.id.distance);

        basefareTxt.setText(base_fare);
        durationTxt.setText(duration);
        distanceTxt.setText(distanceto);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                citybooking();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void citybooking() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CUSTOMER_CITY_BOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Responce.............", response);

                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Booked Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Responce.............", response);
                    Toast.makeText(getApplicationContext(), "Responce is  " + response, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Failed..." + response, Toast.LENGTH_SHORT).show();
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

                params.put("CUS_ID", user_id);
                params.put("ORIGIN", pickupLocation);
                params.put("DESTINATION", dropLocation);
                params.put("BASE_FARE", base_fare);
                params.put("KMETER", distanceto);
                params.put("VEHICLE_ID", v_type);

                return params;
            }
        };

        //inseting into  the iteluser table
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void yourRides() {
        yourrides.insertdata(user_id,datetime, "City",v_type, pickupLocation,dropLocation);
        Log.i("value","inserted");
    }
}
