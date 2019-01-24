package com.example.mahen.adibha2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class cityBottomsheet extends BottomSheetDialogFragment {

    Button rideNow, rideLater;

    static String pick_up_loc= null;
    static String drop_loc= null;
    static String v_type= null;
    static String type= null;
    static String ori_lat= null;
    static String ori_lng = null;
    static String dest_lat = null;
    static String dest_lng = null;
    ImageView vehicle_icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.city_bottom_sheet, container, false);

        rideNow = v.findViewById(R.id.ride_now);
        vehicle_icon=v.findViewById(R.id.iconcard);

        assert getArguments() != null;
        pick_up_loc = getArguments().getString("pickn");
        v_type = getArguments().getString("vehicle");
        type = getArguments().getString("travel_type");
        drop_loc = getArguments().getString("dropn");
        ori_lat = getArguments().getString("ori_lat");
        ori_lng = getArguments().getString("ori_lng");
        dest_lat = getArguments().getString("dest_lat");
        dest_lng = getArguments().getString("dest_lng");

        switch (v_type){
            case "Auto":
                vehicle_icon.setImageResource(R.drawable.ic_auto_rickshaw);
                break;
            case "Prime":
                vehicle_icon.setImageResource(R.drawable.ic_taxi);
                break;
            case "SUV":
                vehicle_icon.setImageResource(R.drawable.ic_booking_car_model);
                break;
        }

        rideNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pick_up_loc.equals("")&&!pick_up_loc.equals("")) {
                    Intent tocity = new Intent(getContext(), City.class);
                    tocity.putExtra("pick_up", pick_up_loc);
                    tocity.putExtra("drop", drop_loc);
                    tocity.putExtra("v_type", v_type);
                    tocity.putExtra("travel_type", type);
                    tocity.putExtra("ori_lat", ori_lat);
                    tocity.putExtra("ori_lng", ori_lng);
                    tocity.putExtra("dest_lat", dest_lat);
                    tocity.putExtra("dest_lng", dest_lng);
                    startActivity(tocity);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("No Location Found");
                    alertDialog.setMessage("Please Select Valid Location");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

            }
        });

        return v;
    }
}
