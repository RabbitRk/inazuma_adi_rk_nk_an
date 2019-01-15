package com.example.mahen.adibha2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.mahen.adibha2.DBhelper.dbHelper;
import com.example.mahen.adibha2.DBhelper.recycleAdapter;

import java.util.List;

class YourRides extends AppCompatActivity {

    String val;
    dbHelper database;
    RecyclerView recyclerView;
    recycleAdapter recycler;
    List<recycleAdapter> datamodel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_rides);
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

        //code begins
        database = new dbHelper(this);
        datamodel=  database.getdata();
        //Changed for Temporary use
        //                         -Naveen
//        recycler =new recycleAdapter(datamodel);


        Log.i("HIteshdata",""+datamodel);
        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //Changed for Temporary use
        //                      -Naveen
//        recyclerView.setAdapter(recycler);
    }
}
