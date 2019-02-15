package com.example.mahen.adibha2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahen.adibha2.services.InternetBroadCast;
import com.maksim88.passwordedittext.PasswordEditText;
import com.subhrajyoti.passwordview.PasswordView;

import java.util.Objects;

import static com.example.mahen.adibha2.Forgot_password.isEmailValid;

public class MySettingsActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    TextView name;
    TextView email;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name=findViewById(R.id.username);
        email=findViewById(R.id.mailaddress);

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

//        name = (TextView) findViewById(R.id.etName);
//        email = (TextView) findViewById(R.id.etEmail);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Name)) {
            name.setText(sharedpreferences.getString(Name, ""));
        }
        if (sharedpreferences.contains(Email)) {
            email.setText(sharedpreferences.getString(Email, ""));

        }

    }

//    public void Save(View view) {
//        String e = email.getText().toString();
//        String n = name.getText().toString();
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(Name, n);
//        editor.putString(Email, e);
//        editor.commit();
//    }
//
//    public void clear(View view) {
//        name = (TextView) findViewById(R.id.etName);
//        email = (TextView) findViewById(R.id.etEmail);
//        name.setText("");
//        email.setText("");
//
//    }
//
//    public void Get(View view) {
//        name = (TextView) findViewById(R.id.etName);
//        email = (TextView) findViewById(R.id.etEmail);
//        sharedpreferences = getSharedPreferences(mypreference,
//                Context.MODE_PRIVATE);
//
//        if (sharedpreferences.contains(Name)) {
//            name.setText(sharedpreferences.getString(Name, ""));
//        }
//        if (sharedpreferences.contains(Email)) {
//            email.setText(sharedpreferences.getString(Email, ""));
//
//        }
//    }

    public void passworddialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View passview =this.getLayoutInflater().inflate(R.layout.passworddialog, null);
        final PasswordView currentPass = passview.findViewById(R.id.current_password);
        final PasswordView newPass = passview.findViewById(R.id.new_password);
        final PasswordView confirmPass = passview.findViewById(R.id.confirm_password);
        builder.setView(passview);

        //validations
        currentPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(currentPass.getText().length()<6) {
                    currentPass.setError("Password must contain minimum 6 characters");
                    currentPass.requestFocus();
                }

            }
        });

        newPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Objects.requireNonNull(newPass.getText()).length()<6) {
                    newPass.setError("Password must contain minimum 6 characters");
                    newPass.requestFocus();

                }

            }
        });

        confirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (Objects.requireNonNull(confirmPass.getText()).length()<6) {
                    confirmPass.setError("Password must contain minimum 6 characters");
                    confirmPass.requestFocus();

                }

            }
        });
        builder.setTitle("Change Password").setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MySettingsActivity.this, "Password is not changed", Toast.LENGTH_SHORT).show();
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

    private void changePassword() {
        //Say What to do when password changed

    }

    public void emaildialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View passview =this.getLayoutInflater().inflate(R.layout.maildialog, null);
        final EditText currentPass = passview.findViewById(R.id.mailaddress);
        builder.setView(passview);
        currentPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isEmailValid(currentPass.getText().toString())){//isEmailValid is given in forgot_password page as public
                    currentPass.setError("Please enter valid email");
                    currentPass.requestFocus();

                }

            }
        });
        builder.setTitle("Change Email id").setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MySettingsActivity.this, "Mail id remains Same", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String e = currentPass.getText().toString();
                if (TextUtils.isEmpty(e)) {
                    //Do Whatever you want to do when it is empty
                }
                else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Email, e);
                    editor.commit();
                    if (sharedpreferences.contains(Email)) {
                        email.setText(sharedpreferences.getString(Email, ""));
                    }
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void userdialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View passview =this.getLayoutInflater().inflate(R.layout.namediaog, null);
        final EditText currentName = passview.findViewById(R.id.username);
        builder.setView(passview);
        builder.setTitle("Change Username").setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MySettingsActivity.this, "UserName remains Same", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String n = currentName.getText().toString();
                if (TextUtils.isEmpty(n)) {

                }
                else {

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, n);
                    editor.commit();
                    if (sharedpreferences.contains(Name)) {
                        name.setText(sharedpreferences.getString(Name, ""));
                    }
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

}
