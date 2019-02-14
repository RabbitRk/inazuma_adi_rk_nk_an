package com.example.mahen.adibha2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.mahen.adibha2.Preferences.PrefsManager;
import com.example.mahen.adibha2.services.InternetBroadCast;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.mahen.adibha2.Main2Activity.LOG_TAG;


public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    EditText password, phone_number;
    String passTxt, phoneTxt;
    String PuserTxt, PemailTxt, getId;
    LoadingButton lb;
    Boolean succes,ed=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Checks Network is on or not
        InternetBroadCast receiver;
        IntentFilter filter;
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new InternetBroadCast();
        registerReceiver(receiver, filter);

        //volley requestqueue initialization
        requestQueue = Volley.newRequestQueue(this);

        password = findViewById(R.id.confirm_pass);
        phone_number = findViewById(R.id.phonenumber);

        lb = findViewById(R.id.loading_btn);
        lb.setTypeface(Typeface.SERIF);
        phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phone_number.getText().length()<10) {
                    phone_number.setError("Please enter valid Phone Number");
                    phone_number.requestFocus();

                }

            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.getText().length()<6) {
                    password.setError("Enter the password");
                    password.requestFocus();

                }

            }
        });

    }

    public void create_account(View view) {
        Intent register = new Intent(this, Main2Activity.class);
        startActivity(register);
    }


    public void forgot(View view) {
        Intent forgot = new Intent(this,Forgot_password.class);
        startActivity(forgot);
//        Intent shortCut = new Intent(this, HomeScreen.class);
//        startActivity(shortCut);

    }

    public void login(View view) {

        passTxt = password.getText().toString().trim();
        phoneTxt = phone_number.getText().toString().trim();

        if (TextUtils.isEmpty(phoneTxt)) {
            phone_number.setError("Enter the phone number");
            phone_number.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passTxt)) {
            password.setError("Enter the password");
            password.requestFocus();
            return;
        }

        //Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait...", false, false);

        //Again creating the string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.USER_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.i(LOG_TAG, "Responce.............." + response);

                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jb = arr.getJSONObject(0);
                            getId = jb.getString("id");
                            PuserTxt = jb.getString("name");
                            PemailTxt = jb.getString("email");

                            setPrefsdetails();

                            lb.startLoading(); //start loading
                            new android.os.Handler().postDelayed(new Runnable() {
                                public void run() {
                                    //if details  is true
                                    lb.loadingSuccessful();
                                    succes = true;
                                    new android.os.Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            if (succes) {
                                                lb.setEnabled(true);
                                                loginto();
                                            }
                                        }
                                    }, 1000);
                                }
                            }, 1500);

                        } catch (JSONException e) {
                            Log.i(LOG_TAG, "Json error.............." + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.i(LOG_TAG, "volley error.............................." + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Username or Phone number not found", Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put("passWord", passTxt);
                params.put("phoneNumber", phoneTxt);
                return params;
            }
        };

        //Adding request the the queue
        requestQueue.add(stringRequest);


    }

    private void loginto() {
        Intent mainA = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(mainA);
        finish();
        Log.i(LOG_TAG, "Hid.............." + getId);
    }

    private void setPrefsdetails() {

        PrefsManager prefsManager = new PrefsManager(this);
        prefsManager.userPreferences(getId, PuserTxt, phoneTxt, PemailTxt);
        Log.i(LOG_TAG, "set preference Hid.............." + getId);
    }
}
