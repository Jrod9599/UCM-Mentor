package com.example.jonathan.ucmmentor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MentorPage extends AppCompatActivity {

    private String[] arrMentees;
    private String[] arrMenEmail;
    private String MentorName;
    private String MentorNick;
    private String mentorEmail;
    private DatabaseLoader database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        database = new DatabaseLoader();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        mentorEmail = intent.getStringExtra("mentorEmail");
        MentorName = database.readName(getApplicationContext(), mentorEmail);
        MentorNick = database.readNick(getApplicationContext(),mentorEmail);

        arrMenEmail = database.QuerieDatabase(getApplicationContext(), MentorNick, false);
        arrMentees = database.QuerieDatabase(getApplicationContext(), MentorNick, true);

        TextView myName = (TextView)findViewById(R.id.MentorName);
        myName.setText(MentorName);

        ListView teelist = (ListView) findViewById(R.id.menteeList);
        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrMentees);
        teelist.setAdapter(adapterName);
        ListView emailList = (ListView) findViewById(R.id.menEmailList);
        ArrayAdapter<String> adapterEmail = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrMenEmail);
        emailList.setAdapter(adapterEmail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPassword(View v){
        Intent i = new Intent(this, PasswordActivity.class);
        i.putExtra("mentorEmail", mentorEmail);
        startActivity(i);
    }

};