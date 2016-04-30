package com.example.jonathan.ucmmentor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class ContactUs extends AppCompatActivity {

    private String facebookURL = "https://www.facebook.com/UCMSuccessMentorProgram#";
    private String instagramURL = "https://www.instagram.com/UCMSuccessMentors/";
    private String tweetSMP = "https://twitter.com/UCMSMP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public void gotoFB(View v)
    {
        Intent website = new Intent(Intent.ACTION_VIEW);
        website.setData(Uri.parse(facebookURL));
        startActivity(website);
    }

    public void gotoInstagram(View v)
    {
        Intent website = new Intent(Intent.ACTION_VIEW);
        website.setData(Uri.parse(instagramURL));
        startActivity(website);
    }

    public void gotoTwitter(View v)
    {
        Intent website = new Intent(Intent.ACTION_VIEW);
        website.setData(Uri.parse(tweetSMP));
        startActivity(website);
    }

}
