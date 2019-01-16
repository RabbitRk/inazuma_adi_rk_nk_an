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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    Button ridenow, ridelater;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Boolean check_time = false, check_date = false;

    String rideLater_date, rideLater_time, pick_up_loc, v_type = "2", type, drop_loc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_container, container, false);
        ridenow = v.findViewById(R.id.ride_now2);
        ridelater = v.findViewById(R.id.button3);

        assert getArguments() != null;
        pick_up_loc = getArguments().getString("pickn");
        v_type = getArguments().getString("vehicle");
        type = getArguments().getString("travel_type");
        drop_loc = getArguments().getString("dropn");

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

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
                String rideNow_date = df.format(c.getTime());
                String rideNow_time = dft.format(c.getTime());

                rental_confirm(rideNow_date, rideNow_time);

            }
        });

        ridelater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                SimpleDateFormat dateof = new SimpleDateFormat("dd-MM-yyyy");
                                rideLater_date = dateof.format(c.getTime());
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

                        SimpleDateFormat timeof = new SimpleDateFormat("HH:mm");
                        rideLater_time = timeof.format(c.getTime());
                        rental_confirm(rideLater_date, rideLater_time);
                    }

                }, mHour, mMinute, false);
        timePickerDialog2.show();


    }

    private void rental_confirm(String rideLater_date, String rideLater_time) {

        Intent ren = new Intent(getContext(), rental.class);
        ren.putExtra("pick", pick_up_loc);
        ren.putExtra("date", rideLater_date);
        ren.putExtra("time", rideLater_time);
        ren.putExtra("v_type", v_type);
        startActivity(ren);

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

