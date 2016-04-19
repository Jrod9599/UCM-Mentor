package com.example.jonathan.ucmmentor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MentorMatch extends AppCompatActivity {

    private String url = "http://mentoring.ucmerced.edu/sites/mentoring.ucmerced.edu/files/documents/Matching_Docs/smp_mentor_profiles_v4.pdf";
    String[] arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DATABASE();
        //DATABASE
        Spinner spinner = (Spinner) findViewById(R.id.mentor_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arr);
        //CursorAdapter adapter = new CursorAdapter(this, c);
        int[] meh = {android.R.id.text1};
        //   SimpleCursorAdapter adapter= new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, projection, meh,0);
//SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, projection, meh);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);
        Log.d("db", "Adapter successfully created" + arr.length );
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Log.d("db", "Adapter successfully linked");
//db.close();
    }

    public void gotoSite(View v)
    {
        Intent website = new Intent(Intent.ACTION_VIEW);
        website.setData(Uri.parse(url));
        startActivity(website);
    }

    public void DATABASE(){
        //DATABASE
        String DB_PATH = "data/data/com.example.jonathan.ucmmentor/"; // path
        String DBNAME = "ucm_mentor.db";
        String outFileName = DB_PATH + DBNAME;
        try {
            InputStream is = getAssets().open(DBNAME);

            Log.d("db", "database file exists: " + is.available());


            OutputStream myOutput = new FileOutputStream(outFileName);

//transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

//Close the streams
            myOutput.flush();
            myOutput.close();
            is.close();
            is = new FileInputStream(DB_PATH + DBNAME);
            Log.d("db", "output database file exists: " + is.available());
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //SQLiteDatabase db = mDBHelper.getReadableDatabase();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH+DBNAME, null, SQLiteDatabase.OPEN_READONLY);

        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }

        arr = new String[arrTblNames.size()];
        arrTblNames.toArray(arr);
        for(int x=0; x<arrTblNames.size();x++){
            Log.d("db", "Table " + (x+1) + " is " + arr[x]);
        }
        String[] projection = {
                "m_Nickname"
        };
        c = db.query("Mentor", projection, null, null, null,null, null);

        arrTblNames = new ArrayList<String>();

        int itemId;
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString(c.getInt(
                        c.getColumnIndexOrThrow(projection[0])
                )) );
                c.moveToNext();
            }
        }
        arr = new String[arrTblNames.size()];
        arrTblNames.toArray(arr);
        for(int x=0; x<arrTblNames.size();x++){
            Log.d("db", "Name " + (x+1) + " is " + arr[x]);
        }

        db.close();
    }}