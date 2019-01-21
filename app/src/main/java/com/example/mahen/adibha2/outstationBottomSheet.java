


package com.example.mahen.adibha2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class outstationBottomSheet extends BottomSheetDialogFragment {

    Button rideNow, rideLater;
    private BookBottomSheet.BottomSheetListener mListener;
    static String pick_up_loc = null;
    static String drop_loc = null;
    static String v_type = null;
    static String type = null;
    static String ori_lat = null;
    static String ori_lng = null;
    static String dest_lat = null;
    static String dest_lng = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.outstation_bottom_sheet, container, false);
        rideNow = v.findViewById(R.id.ride_now2);
        rideLater = v.findViewById(R.id.rideLater);

        assert getArguments() != null;
        pick_up_loc = getArguments().getString("pickn");
        v_type = getArguments().getString("vehicle");
        type = getArguments().getString("travel_type");
        drop_loc = getArguments().getString("dropn");
        ori_lat = getArguments().getString("ori_lat");
        ori_lng = getArguments().getString("ori_lng");
        dest_lat = getArguments().getString("dest_lat");
        dest_lng = getArguments().getString("dest_lng");

        rideNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
                String rideNow_date = df.format(c.getTime());
                String rideNow_time = dft.format(c.getTime());

                Intent tocity = new Intent(getContext(), outstation.class);
                tocity.putExtra("pick_up", pick_up_loc);
                tocity.putExtra("drop", drop_loc);
                tocity.putExtra("v_type", v_type);
                tocity.putExtra("travel_type", type);
                tocity.putExtra("ori_lat", ori_lat);
                tocity.putExtra("ori_lng", ori_lng);
                tocity.putExtra("dest_lat", dest_lat);
                tocity.putExtra("dest_lng", dest_lng);
                tocity.putExtra("date", rideNow_date);
                tocity.putExtra("time", rideNow_time);

                startActivity(tocity);
            }
        });

        rideLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = 0;
                int mMonth= 0;
                int mDay= 0;
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                SimpleDateFormat dateof = new SimpleDateFormat("dd-MM-yyyy");
                                String rideLater_date = dateof.format(c.getTime());
                                getTime(rideLater_date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        return v;
    }

    private void getTime(final String ridedate) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog2 = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        SimpleDateFormat timeof = new SimpleDateFormat("HH:mm");
                        String rideLater_time = timeof.format(c.getTime());
                        rideLaterIntent(ridedate, rideLater_time);
                    }

                }, mHour, mMinute, false);
        timePickerDialog2.show();
    }

    public void rideLaterIntent(String dateon, String timeat)
    {
        Intent tocity = new Intent(getContext(), outstation.class);
        tocity.putExtra("pick_up", pick_up_loc);
        tocity.putExtra("drop", drop_loc);
        tocity.putExtra("v_type", v_type);
        tocity.putExtra("travel_type", type);
        tocity.putExtra("ori_lat", ori_lat);
        tocity.putExtra("ori_lng", ori_lng);
        tocity.putExtra("dest_lat", dest_lat);
        tocity.putExtra("dest_lng", dest_lng);
        tocity.putExtra("date", dateon);
        tocity.putExtra("time", timeat);

        startActivity(tocity);
    }
    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BookBottomSheet.BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}


