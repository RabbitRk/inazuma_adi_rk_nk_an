package com.example.mahen.adibha2.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

public class otpreceiver extends BroadcastReceiver {

//    public static final String OTP_EXTRA = "otpreceriver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str ="";
        String otpOnly ="";

        if (bundle != null)
        {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i=0; i<smsm.length; i++){
                smsm[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                sms_str += "\r\nMessage: ";
                sms_str += smsm[i].getMessageBody();
                sms_str+= "\r\n";

                //getting the otp from the message automatically
                otpOnly = sms_str.replaceAll("[^0-9]", "");
                otpOnly = otpOnly.trim();

                String Sender = smsm[i].getOriginatingAddress();
                //Check here sender is yours
                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message",otpOnly);

                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);

            }
        }
    }
}
