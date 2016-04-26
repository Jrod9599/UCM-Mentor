package com.example.jonathan.ucmmentor;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MentorMatch extends AppCompatActivity {

    private String url = "http://mentoring.ucmerced.edu/sites/mentoring.ucmerced.edu/files/documents/Matching_Docs/smp_mentor_profiles_v4.pdf";
    String[] arr; //mentor list
    String[] mentee;//
    private String DB_PATH = "data/data/com.example.jonathan.ucmmentor/"; // path
    private String DBNAME = "ucm_mentor(2).db";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readDATABASE();
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
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

//db.close();
    }

    public void registerMentor(View v){
        Spinner mySpinner = (Spinner) findViewById(R.id.mentor_spinner);
        String mentorNick = mySpinner.getSelectedItem().toString();
        EditText email = (EditText) findViewById(R.id.menteeEmail);
        String  menteeEmail = email.getText().toString();

        //SQLiteDatabase db = mDBHelper.getReadableDatabase();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH+DBNAME, null, SQLiteDatabase.OPEN_READWRITE);

        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }

        mentee = new String[arrTblNames.size()];
        arrTblNames.toArray(mentee);
        String[] projection = {"mn_email"};
        c = db.query("Mentees", projection, null, null, null,null, null);

        arrTblNames = new ArrayList<String>();

        int itemId;
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString(c.getInt(c.getColumnIndexOrThrow(projection[0]))) );
                c.moveToNext();
            }
        }
        mentee = new String[arrTblNames.size()];
        arrTblNames.toArray(mentee);

        for(int i = 0; i < mentee.length; i++)
        {
            if(menteeEmail.equals(mentee[i]))
            {
                ContentValues values = new ContentValues();
                values.put("mn_MentorNick", mentorNick);

                db.update("Mentees", values, "mn_ID=" + (double) (1+i), null);
                TextView feedback = (TextView)findViewById(R.id.submitFeedback);
                feedback.setText("Mentor Selection Successful");
                break;
            }
        }

        db.close();



    }
    public void readDATABASE(){
        //DATABASE

        String outFileName = DB_PATH + DBNAME;
        /*
        try {
            InputStream is = getAssets().open(DBNAME);

            //Log.d("db", "database file exists: " + is.available());


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

            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        //SQLiteDatabase db = mDBHelper.getReadableDatabase();
        SQLiteDatabase db;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);
        }

        catch (SQLException e) {
            e.printStackTrace();
            try {
                InputStream is = getAssets().open(DBNAME);

                //Log.d("db", "database file exists: " + is.available());

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

                is.close();

            } catch (IOException e2) {
                e2.printStackTrace();
            }

            db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);
        }

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

        db.close();
    }

    public void gotoSite(View v)
    {
        Intent website = new Intent(Intent.ACTION_VIEW);
        website.setData(Uri.parse(url));
        startActivity(website);
    }


}