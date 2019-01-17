package com.example.mahen.adibha2.DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    public static String DATABASE = "your_rides.db";
    public static String TABLE = "rides";
    public static String timeat = "timeat";
    public static String bookid = "bookid";
    public static String travel_type = "travel_type";
    public static String v_type = "v_type";
    public static String ori = "ori";
    public static String dest = "dest";
    String br = "";

    public dbHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        br = "CREATE TABLE " + TABLE + "(" + bookid + " text, " + timeat + " Text, " + travel_type + " Text, " + v_type + " Text, " + ori + " Text, " + dest + " Text);";
        db.execSQL(br);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + " ;");
    }

    public void insertdata(String bookid, String timeat, String travel_type, String v_type, String ori, String dest) {
        System.out.print("Hello " + br);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(bookid, bookid);
        contentValues.put(timeat, timeat);
        contentValues.put(travel_type, travel_type);
        contentValues.put(v_type, v_type);
        contentValues.put(ori, ori);
        contentValues.put(dest, dest);
        db.insert(TABLE, null, contentValues);
    }

    public List<recycleAdapter> getdata(){
        // DataModel dataModel = new DataModel();
        List<recycleAdapter> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        recycleAdapter dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new recycleAdapter();

            String bookid = cursor.getString(cursor.getColumnIndexOrThrow("bookid"));
            String travel_type = cursor.getString(cursor.getColumnIndexOrThrow("travel_type"));
            String v_type = cursor.getString(cursor.getColumnIndexOrThrow("v_type"));
            String timeat = cursor.getString(cursor.getColumnIndexOrThrow("timeat"));
            String dest = cursor.getString(cursor.getColumnIndexOrThrow("dest"));
            String ori = cursor.getString(cursor.getColumnIndexOrThrow("ori"));

            dataModel.setBook_id(bookid);
            dataModel.setOrigin(ori);
            dataModel.setDestination(dest);
            dataModel.setTimeat(timeat);
            dataModel.setTravel_type(travel_type);
            dataModel.setV_type(v_type);

            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (recycleAdapter mo:data ) {

            Log.i("bookID",""+mo.getBook_id());
        }

        //

        return data;
    }

}
