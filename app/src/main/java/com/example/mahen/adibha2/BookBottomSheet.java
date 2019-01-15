package com.example.mahen.adibha2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class BookBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    Button ridenow, ridelater;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Boolean check_time = false, check_date = false;

    String rideLater_date, rideLater_time, pick_up_loc, v_type = "2",type,drop_loc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_container, container, false);
        ridenow = v.findViewById(R.id.ride_now2);
        ridelater = v.findViewById(R.id.button3);

        assert getArguments() != null;
        pick_up_loc = getArguments().getString("pickn");
        v_type = getArguments().getString("vehicle");
        type=getArguments().getString("travel_type");
        drop_loc=getArguments().getString("dropn");

        assert v_type != null;
        switch (v_type) {
            case "Prime":
                v_type = "2";
                break;
            case "SUV":
                v_type = "3";
                break;
            default:
                v_type = "2";
        }


        ridenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String month, day, year, dateof;

                Calendar c = Calendar.getInstance();
                year = String.valueOf(c.get(Calendar.YEAR));
                month = String.valueOf(c.get(Calendar.MONTH));
                day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

                dateof = day+"-"+month+"-"+year;
                Toast.makeText(getContext(), "date of "+dateof, Toast.LENGTH_SHORT).show();

//                Intent ren = new Intent(getContext(), rental.class);
//                ren.putExtra("pick",pick_up_loc);
//                ren.putExtra("date",rideLater_date);
//                ren.putExtra("time",rideLater_time);
//                startActivity(ren);

            }
        });

        ridelater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                rideLater_date = String.format("%d-%d-%d", dayOfMonth, monthOfYear + 1, year);
                                getTime();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        return v;
    }

    private void getTime() {

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog2 = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        rideLater_time = mHour + ":" + mMinute;
                        rental_confirm(rideLater_date, rideLater_time);
                    }

                }, mHour, mMinute, false);
        timePickerDialog2.show();


    }

    private void rental_confirm(String rideLater_date, String rideLater_time) {
        if (type.equals("rental")) {
            Intent ren = new Intent(getContext(), rental.class);
            ren.putExtra("pick", pick_up_loc);
            ren.putExtra("date", rideLater_date);
            ren.putExtra("time", rideLater_time);
            ren.putExtra("v_type", v_type);
            startActivity(ren);
        }
        if (type.equals("city")) {
            Intent ren = new Intent(getContext(), City.class);
            ren.putExtra("pick", pick_up_loc);
            ren.putExtra("date", rideLater_date);
            ren.putExtra("time", rideLater_time);
            ren.putExtra("v_type", v_type);
            ren.putExtra("drop",drop_loc);
            startActivity(ren);
        }
        if (type.equals("outstation")) {
            Intent ren = new Intent(getContext(), City.class);
            ren.putExtra("pick", pick_up_loc);
            ren.putExtra("date", rideLater_date);
            ren.putExtra("time", rideLater_time);
            ren.putExtra("v_type", v_type);
            ren.putExtra("drop",drop_loc);
            startActivity(ren);
        }
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}

