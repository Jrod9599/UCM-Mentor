package com.example.jonathan.ucmmentor;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;

public class MentorMatch extends AppCompatActivity {

    private String url = "http://mentoring.ucmerced.edu/sites/mentoring.ucmerced.edu/files/documents/Matching_Docs/smp_mentor_profiles_v4.pdf";
    String[] arr; //mentor list
    String[] mentee;//
    String data;
    private String DB_PATH = "data/data/com.example.jonathan.ucmmentor/"; // path
    private String DBNAME = "ucm_mentor.db";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseLoader database = new DatabaseLoader();
        String[] arr = database.readMentors(getApplicationContext());

        Spinner spinner = (Spinner) findViewById(R.id.mentor_spinner);
        int[] meh = {android.R.id.text1};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);

            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void registerMentor(View v){
        Spinner mySpinner = (Spinner) findViewById(R.id.mentor_spinner);
        String mentorNick = mySpinner.getSelectedItem().toString();
        EditText email = (EditText) findViewById(R.id.menteeEmail);
        String  menteeEmail = email.getText().toString();

        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH+DBNAME, null, SQLiteDatabase.OPEN_READWRITE);

        ArrayList<String> arrTblNames = new ArrayList<String>();

        String query1 = "Select mn_ID FROM Mentees, Users WHERE u_email LIKE '" + menteeEmail + "' AND mn_UserID = u_ID";
        Cursor c = db.rawQuery(query1, null);

        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("mn_ID"));
            }while(c.moveToNext());
        }
        c.close();

        db.rawQuery("UPDATE Mentees SET mn_Choice = '" + mentorNick + "' WHERE mn_ID = " + data, null );

        TextView text = (TextView) findViewById(R.id.submitFeedback);
        text.setText("Mentor Selection Successful");

        db.close();



    }

    public void gotoSite(View v)
    {
        Intent website = new Intent(Intent.ACTION_VIEW);
        website.setData(Uri.parse(url));
        startActivity(website);
    }


}