package com.example.mahen.adibha2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView mTitleWindow = findViewById(R.id.TitleWindow);
        TextView mMessageWindow = findViewById(R.id.MessageWindow);
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

        mTitleWindow.setText("Got My Trip");
        StringBuilder stringBuilder = new StringBuilder();
        String someMessage = "Got My Trip is a mobile app for transportation that mingles customers and driver partners in to a mobile\n" +
                "technology platform. It is a convenient, easy and quick service that fulfills customers requirements on\n" +
                "travelling.\n\n" +
                "GMT ensures to offer AC cabs at affordable fares for customers to travel conveniently in GMT cabs. It is\n" +
                "easy and fast to book cabs in GMT without any lose of time.\n\n" +
                "GMT have mingled with driver - partners which given an earning opportunity for drivers to grow\n" +
                "professionally and personally\n";
        for (int i = 0; i < 1; i++) {
            stringBuilder.append(someMessage);
        }
        mMessageWindow.setText(someMessage);
    }
}
