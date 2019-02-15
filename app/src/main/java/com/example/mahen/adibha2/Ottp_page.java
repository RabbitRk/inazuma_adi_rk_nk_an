package com.example.mahen.adibha2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.mahen.adibha2.services.InternetBroadCast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Ottp_page extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static final String LOG_TAG = "MainActivity";
    String otpLocal = "", phoneTxt = "";
    EditText tv;
    ProgressDialog loading;
    //Volley RequestQueue
    private RequestQueue requestQueue;

    LoadingButton lb;
    Boolean succes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ottp_page);


        //Checks Network is on or not
        InternetBroadCast receiver;
        IntentFilter filter;
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new InternetBroadCast();
        registerReceiver(receiver, filter);

        //controls intialization
        tv = findViewById(R.id.otpTxt);
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tv.getText().length()<4) {
                    tv.setError("OTP contains 4 digits");
                    tv.requestFocus();

                }

            }
        });

        //Initializing the RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        //geting the phone number
        Intent intent = getIntent();
        phoneTxt = intent.getStringExtra(Main2Activity.PHONE_EXTRA);

        Log.i(LOG_TAG, "Phone checking........................" + phoneTxt);

        if (checkAndRequestPermissions()) {
            Log.i(LOG_TAG, "Inside the normal flow");
            // carry on the normal flow, as the case of  permissions  granted.
        }

        lb = findViewById(R.id.loading_btn);
        lb.setTypeface(Typeface.SERIF);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase("otp")) {

                final String message = intent.getStringExtra("message");
                //getting otp from the receiver
                tv.setText(message);
            }
        }
    };

    private boolean checkAndRequestPermissions() {
        Log.i(LOG_TAG, "Request checking........................");
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[0]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void otpVerification(View view) {

        //Getting the user entered otp from edittext
        otpLocal = tv.getText().toString().trim();

        //validations
        if (otpLocal.equals("") || otpLocal.length() < 4) {
            Toast.makeText(this, "Please enter the Got My Trip OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        //Displaying a progressbar
        loading = ProgressDialog.show(this, "Authenticating", "Please wait while we check the entered OTP", false, false);

        //Creating an string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.OTP_VERIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if the server response is success
                        Log.i(LOG_TAG, "Response........................" + response);
                        if (response.equalsIgnoreCase("success")) {
                            //dismissing the progressbar
                            loading.dismiss();
                            //Starting a new activity
                            lb.startLoading(); //start loading
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            //if details  is true
                                            lb.loadingSuccessful();
                                            succes = true;
                                            new android.os.Handler().postDelayed(
                                                    new Runnable() {
                                                        public void run() {
                                                            if (succes) {
                                                                lb.setEnabled(true);
                                                                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                                                            }
                                                        }
                                                    }, 1000);
                                        }
                                    }, 1500);
                        } else {
                            loading.dismiss();
                            //Displaying a toast if the otp entered is wrong
                            Toast.makeText(getApplicationContext(), "Wrong OTP Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();

                        Log.i(LOG_TAG, "Error checking........................" + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters otp and username
                params.put("cus_phone", phoneTxt);
                params.put("otp", otpLocal);
                return params;
            }
        };
        Log.i(LOG_TAG, "otp checking........................" + otpLocal);
        //Adding the request to the queue
        requestQueue.add(stringRequest);

    }
}
