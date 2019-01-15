package com.example.mahen.adibha2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class CustomDialogClass extends Dialog implements
        View.OnClickListener {


    CustomDialogClass(Activity a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_now_later);

        //button declaration
        Button yes = findViewById(R.id.ride_now2);
        Button no = findViewById(R.id.button3);
        //
        RecyclerView recyclerView;

        ArrayList<String> packages = new ArrayList<>(Arrays.asList("1 hr - 15 km", "2 hrs - 30 km", "4 hrs - 40 km", "6 hrs - 60 km", "8 hrs - 80 km", "10 hrs - 100 km",
                "12 hrs - 120 km"));

        //ArrayList<String> base_fare_p = new ArrayList<>(Arrays.asList("399", "599", "899", "1299", "1699","1999", "2299"));


        recyclerView = findViewById(R.id.mycard);
        recyclerView.setHasFixedSize(true);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        RecyclerView.LayoutManager rentalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(rentalLayout);
        RecyclerView.Adapter cardAdapter1 = new cardAdapter(getContext(), packages);
        recyclerView.setAdapter(cardAdapter1);

    }

    @Override
    public void onClick(View v) {

        int mYear;
        int mDay;
        int mHour;
        int mMinute;

        switch (v.getId()) {
            case R.id.ride_now2:
                dismiss();
                break;
            case R.id.button3:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                            }

                        }, mHour, mMinute, false);
                timePickerDialog2.show();


                DatePickerDialog datePickerDialog2 = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();
                break;
            default:
                break;
        }
        dismiss();
    }
}
