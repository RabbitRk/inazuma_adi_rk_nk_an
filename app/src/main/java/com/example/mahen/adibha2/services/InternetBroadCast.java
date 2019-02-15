package com.example.mahen.adibha2.services;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.Toast;


import com.example.mahen.adibha2.R;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;


public class InternetBroadCast extends BroadcastReceiver implements Destroyable {
    String LOG_TAG = "NetworkChangeReceiver";
    public boolean isConnected = false;
    private SharedPreferences.Editor edit;
    private Boolean status;
    Context c;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        c=context;
        Log.v(LOG_TAG, "Receieved notification about network status");
        status = isNetworkAvailable(context);
        Dialogbox();
//        if (!status) {
//
//            final Dialog dialog = new Dialog(context);
//            dialog.setContentView(R.layout.activity_network_dialog);
//            dialog.setTitle("No Internet Connection...");
//
//            Button dialogButton = (Button) dialog.findViewById(R.id.ok_button);
//            dialogButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
////                    dialog.dismiss();
////                    try {
////                        destroy();
////                    } catch (DestroyFailedException e) {
////                        e.printStackTrace();
////                    }
//                }
//            });
//            try {
//                dialog.show();
//            }
//            catch (WindowManager.BadTokenException e)
//            {
//                e.printStackTrace();
//            }
//
//        }
    }

    private void Dialogbox() {

        status = isNetworkAvailable(c);

        if (!status) {

            final Dialog dialog = new Dialog(c);
            dialog.setContentView(R.layout.activity_network_dialog);
            dialog.setTitle("No Internet Connection...");
            dialog.setCanceledOnTouchOutside(false);
            Button dialogButton = (Button) dialog.findViewById(R.id.ok_button);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    dialog.dismiss();

                    if (!status) {
                        
                        dialog.getWindow()
                                .getDecorView()
                                .animate()
                                .translationX(16f)
                                .setInterpolator(new CycleInterpolator(7f));
//                        new android.os.Handler().postDelayed(new Runnable() {
//                            public void run() {
//                                Dialogbox();
//                            }
//                        }, 2000);

                    }
                    else
                        dialog.dismiss();



//                    try {
//                        destroy();
//                    } catch (DestroyFailedException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            try {
                dialog.show();
            }
            catch (WindowManager.BadTokenException e)
            {
                e.printStackTrace();
            }

        }

    }


    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if(!isConnected){
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
//                            Toast.makeText(context, "Now you are connected to Internet!", Toast.LENGTH_SHORT).show();
                            isConnected = true;
                        }
                        return true;
                    }
                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!");
//        Toast.makeText(context, "You are not connected to Internet!", Toast.LENGTH_SHORT).show();
        isConnected = false;
        return false;
    }


//    @Override
//    public void onReceive(final Context context, Intent intent) {
//        try
//        {
//            if (isOnline(context))
//            {
//                Log.e("keshav", "Online Connect Intenet ");
//                Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                NetworkDialog ob=new NetworkDialog();
//                ob.dialog();
//
//
//
//                Log.e("keshav", "Conectivity Failure !!! ");
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isOnline(Context context) {
//        try {
//            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo netInfo = cm.getActiveNetworkInfo();
//            //should check null because in airplane mode it will be null
//            return (netInfo != null && netInfo.isConnected());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}