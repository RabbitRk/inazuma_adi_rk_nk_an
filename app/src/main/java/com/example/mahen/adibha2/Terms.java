package com.example.mahen.adibha2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        TextView mTitleWindow = findViewById(R.id.TitleWindow);
        TextView mMessageWindow = findViewById(R.id.MessageWindow);
        TextView mSubtitle = findViewById(R.id.Subtitle);
        TextView mSubtitle1 = findViewById(R.id.Subtitle1);
        TextView mMessageWindow1 =findViewById(R.id.MessageWindow1);
        TextView mSubtitle2 = findViewById(R.id.Subtitle2);
        TextView mMessageWindow2 = findViewById(R.id.MessageWindow2);
        TextView mSubtitle3 = findViewById(R.id.Subtitle3);
        TextView mMessageWindow3 = findViewById(R.id.MessageWindow3);
        TextView mSubtitle4 = findViewById(R.id.Subtitle4);
        TextView mMessageWindow4 = findViewById(R.id.MessageWindow4);

//        mTitleWindow.setText("TERMS AND CONDITIONS");
//        mSubtitle.setText("EFFECTIVE FROM JANUARY 15,2019");
//        StringBuilder stringBuilder = new StringBuilder();
//        String someMessage= "\nThese Terms of use constitutes of an agreement between you and the GMT. Use of the GMT indicates" +
//                "the unconditionnal acceptance of below mentioned terms and conditions." +
//                "The GMT may change these terms of use at any time by posting changes online.\n\n";
//        mSubtitle1.setText("BOOKING NORMS");
//        String someMessage1 = "\nYou must allow sufficient time while booking your taxi for any delay caused by the traffic or weather\n" +
//                "conditions. The GMT shall not be responsible for any delay caused by your failure to allow enough time\n" +
//                "to reach your destination or if the passenger/s are not ready at time of pickup. You must order\n" +
//                "the car as per your space requirements. The GMT cannot guarantee to carry luggage beyond the\n" +
//                "guidelines allowed. If agreed, extra charges will be charged.\n\n";
//        mSubtitle2.setText("FARE AND PAYMENTS");
//        String someMessage2= "\nThe payment plans have been listed on our website. The tariff includes Parking Charges and Toll Tax.\n" +
//                "Once destination arrived, we will send you the final quotation on your phone after getting all the details\n" +
//                "about the booking. The payments will be through cash only.\n" +
//                "Please check your booking confirmation carefully and inform GMT promptly of any errors like cancelling\n" +
//                "the trip or any lates in starting trip.\n\n";
//        mSubtitle3.setText("AT YOUR OWN RISK");
//        String someMessage3= "\nGMT shall not be responsible or liable for any loss or damage of your belongings.\n\n";
//        mSubtitle4.setText("SERVICES" );
//        String someMessage4="\nThe GMT will not carry more passengers than allowed by the norms.";
//        for(int i=0; i<1; i++){
//        stringBuilder.append(someMessage);
//        }
//        mMessageWindow4.setText(someMessage4);
//        mMessageWindow3.setText(someMessage3);
//        mMessageWindow2.setText(someMessage2);
//        mMessageWindow1.setText(someMessage1);
//        mMessageWindow.setText(someMessage);
    }
}
