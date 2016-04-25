package com.example.jonathan.ucmmentor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MentorPage extends AppCompatActivity {

    private String mentorEmail;
    private String[] arrEmail;
    private String[] arrName;
    private String[] arrNick;
    private String[] arrMentees;
    private String[] arrMenEmail;
    private String[] arrMenSelect;
    private String DB_PATH = "data/data/com.example.jonathan.ucmmentor/"; // path
    private String DBNAME = "ucm_mentor(2).db";
    private String MentorName;
    private String MentorNick;
    String[] queryEmail = {"m_email"};
    String[] queryName = {"m_name"};
    String[] queryNick = {"m_Nickname"};
    String[] queryMenEmail = {"mn_email"};
    String[] queryMenName = {"mn_name"};
    String[] queryMenChoice = {"mn_MentorNick"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String mentorEmail = intent.getStringExtra("mentorEmail");
        // mentoremail = LoginActivity.getEmail();
        Log.d("Email is ", mentorEmail);
        arrEmail = readDATABASE(queryEmail, arrEmail);
        arrName = readDATABASE(queryName, arrName);
        arrNick = readDATABASE(queryNick, arrNick);
        for (int i = 0; i < arrEmail.length; i++) {
            Log.d("Check Email", arrEmail[i] + " to " + mentorEmail);

            if (mentorEmail.equals(arrEmail[i])) {
                MentorName = arrName[i];
                MentorNick = arrNick[i];
                Log.d("Mentor is", MentorName);
                TextView mentorname = (TextView) findViewById(R.id.MentorName);
                mentorname.setText(MentorName);

                break;
            }

        }
    //insert into for loop when fixed
        arrMentees = readMenteeDATABASE(queryMenName, arrMentees);
        arrMenEmail = readMenteeDATABASE(queryMenEmail, arrMenEmail);
        arrMenSelect = readMenteeDATABASE(queryMenChoice, arrMenSelect);
        ArrayList<String> MenteesName = new ArrayList<String>();
        ArrayList<String> MenteesEmail = new ArrayList<String>();

        if(MentorName != null) {

            for(int i = 0; i < arrMentees.length; i++) {
                if (MentorNick.equals(arrMenSelect[i])) {

                    MenteesName.add(arrMentees[i]);
                    MenteesEmail.add(arrMenEmail[i]);
                }
            }

            arrMentees = new String[MenteesName.size()];
            MenteesName.toArray(arrMentees);
            arrMenEmail = new String[MenteesEmail.size()];
            MenteesEmail.toArray(arrMenEmail);

            ListView teelist = (ListView) findViewById(R.id.menteeList);
            ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrMentees);
            teelist.setAdapter(adapterName);
            ListView emailList = (ListView) findViewById(R.id.menEmailList);
            ArrayAdapter<String> adapterEmail = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrMenEmail);
            emailList.setAdapter(adapterEmail);
        }
        else{
            TextView mentorname = (TextView) findViewById(R.id.MentorName);
            mentorname.setText("Verification Failed");
        }

    }

    public String[] readDATABASE(String[] query, String[] arr) {

        //String value = " ";
        //DATABASE
        //SQLiteDatabase db = mDBHelper.getReadableDatabase();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);

        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }

        arr = new String[arrTblNames.size()];
        arrTblNames.toArray(arr);
        for (int x = 0; x < arrTblNames.size(); x++) {
        }
        String[] projection = query;
        c = db.query("Mentor", projection, null, null, null, null, null);

        arrTblNames = new ArrayList<String>();

        int itemId;
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getInt(
                        c.getColumnIndexOrThrow(projection[0])
                )));
                c.moveToNext();
            }
        }
        arr = new String[arrTblNames.size()];
        arrTblNames.toArray(arr);
        for (int x = 0; x < arrTblNames.size(); x++) {
            Log.d("db", "Name " + (x + 1) + " is " + arr[x]);

        }

        db.close();
        // return value;
        return arr;
    }

    public String[] readMenteeDATABASE(String[] query, String[] arr) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);

        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }

        arr = new String[arrTblNames.size()];
        arrTblNames.toArray(arr);
        for (int x = 0; x < arrTblNames.size(); x++) {
        }
        String[] projection = query;
        c = db.query("Mentees", projection, null, null, null, null, null);

        arrTblNames = new ArrayList<String>();

        int itemId;
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                    arrTblNames.add(c.getString(c.getInt(c.getColumnIndexOrThrow(projection[0]))));
                c.moveToNext();
            }
        }
        arr = new String[arrTblNames.size()];
        arrTblNames.toArray(arr);
        for (int x = 0; x < arrTblNames.size(); x++) {
            Log.d("db", "Name " + (x + 1) + " is " + arr[x]);

        }

        db.close();
        // return value;
        return arr;
    }
};