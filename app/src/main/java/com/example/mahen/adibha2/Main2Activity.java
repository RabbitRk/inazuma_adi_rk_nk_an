package com.example.mahen.adibha2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.mahen.adibha2.Preferences.PrefsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    public static final String LOG_TAG = "MainActivity";
    public static final String PHONE_EXTRA = "PhoneNumber";
    private RequestQueue requestQueue;
    EditText username, email, password, c_password, phone_number;
    String userTxt, emailTxt, passTxt, c_passTxt, phoneTxt, getId;

    LoadingButton lb;
    Boolean succes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.i(LOG_TAG, "inside register page");

        requestQueue = Volley.newRequestQueue(this);

        username = findViewById(R.id.username);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.confirm_pass);
        phone_number = findViewById(R.id.phonenumber);

        lb = findViewById(R.id.loading_btn);
        lb.setTypeface(Typeface.SERIF);

    }


    public void register(View view) {

        //Getting user data
        userTxt = username.getText().toString().trim();
        emailTxt = email.getText().toString().trim();
        passTxt = password.getText().toString().trim();
        c_passTxt = c_password.getText().toString().trim();
        phoneTxt = phone_number.getText().toString().trim();

//        if (userTxt.equals("") || emailTxt.equals("") || passTxt.equals("") || passTxt.equals("") || c_passTxt.equals("") || phoneTxt.equals(""))
//        {
//            Toast.makeText(this, "Please enter the valid information", Toast.LENGTH_SHORT).show();
//            return;
//        }

        //validations
        if (TextUtils.isEmpty(userTxt)) {
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(emailTxt)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passTxt)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(c_passTxt)) {
            c_password.setError("Enter a password");
            c_password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phoneTxt)) {
            phone_number.setError("Enter a password");
            phone_number.requestFocus();
            return;
        }

        if (!passTxt.equals(c_passTxt)) {
            c_password.setError("Confirm password is not matching");
            c_password.requestFocus();
            return;
        }

        //Checking preferences

        //Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait...", false, false);

        //Again creating the string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.USER_REGISTRATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.i(LOG_TAG, "Responce.............." + response);
                        //If it is success
                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jb = arr.getJSONObject(0);
                            getId = jb.getString("id");

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
                                                                reg();
                                                            }
                                                        }
                                                    }, 1000);
                                        }
                                    }, 1500);

                        } catch (JSONException e) {
                            Log.i(LOG_TAG, "json error.............................." + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Mobile number already exists! Try Again...", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Username or Phone number already registered", Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put("userName", userTxt);
                params.put("passWord", passTxt);
                params.put("phoneNumber", phoneTxt);
                params.put("emailID", emailTxt);
                return params;
            }
        };

        //Adding request the the queue
        requestQueue.add(stringRequest);

    }

    private void reg() {
        Intent ottp_page = new Intent(getApplicationContext(), Ottp_page.class);
        ottp_page.putExtra(PHONE_EXTRA, phoneTxt);
        startActivity(ottp_page);
        finish();
        Log.i(LOG_TAG, "json success.............................." + getId);
    }

    @Override
    protected void onPause() {
        super.onPause();

        PrefsManager prefsManager = new PrefsManager(getApplicationContext());
        prefsManager.userPreferences(getId, userTxt, phoneTxt, emailTxt);

    }
    public void openTerms(View view) {
        Intent terms=new Intent(Main2Activity.this,Terms.class);
        startActivity(terms);

    }
}
