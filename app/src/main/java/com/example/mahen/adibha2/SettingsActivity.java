package com.example.mahen.adibha2;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mahen.adibha2.services.InternetBroadCast;
import com.maksim88.passwordedittext.PasswordEditText;
import com.subhrajyoti.passwordview.PasswordView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.mahen.adibha2.Forgot_password.isEmailValid;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting up toolbar
        getLayoutInflater().inflate(R.layout.action_bar_setting, (ViewGroup) findViewById(android.R.id.content));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //Checks Network is on or not
        InternetBroadCast receiver;
        IntentFilter filter;
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new InternetBroadCast();
        registerReceiver(receiver, filter);

//        setContentView(R.layout.action_bar_setting);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        //get tool bar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        //toolbar action to go back is any activity exists
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new GeneralPreferenceFragment()).commit();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            final EditTextPreference email = (EditTextPreference) findPreference("example_mail");
            email.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    if(!EmailValid(email.getText().toString())) {
//                        email.getEditText().setError("Please enter valid email");
//                        email.getEditText().requestFocus();
//                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if(!EmailValid(email.getText().toString())) {
//                        email.getEditText().setError("Please enter valid email");
//                        email.getEditText().requestFocus();
//                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!EmailValid(email.getText().toString())) {
                        email.getEditText().setError("Please enter valid email");
                        email.getEditText().requestFocus();
                    }
                }
            });





            final Preference password = (Preference) findPreference("example_password");
//

            password.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    passworddialog();
                    return false;
                }

                private void passworddialog() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View passview = getActivity().getLayoutInflater().inflate(R.layout.passworddialog, null);
                    PasswordView currentPass = passview.findViewById(R.id.current_password);

                    PasswordEditText newPass = passview.findViewById(R.id.new_password);
                    PasswordEditText confirmPass = passview.findViewById(R.id.confirm_password);
                    builder.setView(passview);
                    builder.setTitle("CHANGE PASSWORD").setCancelable(false);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "Password is not changed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changePassword();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }


        public static boolean EmailValid(String email) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

        private void changePassword() {

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

}
