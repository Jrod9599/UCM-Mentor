package com.example.jonathan.ucmmentor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PasswordActivity extends AppCompatActivity {

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        email = intent.getStringExtra("mentorEmail");
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

    public void updatePassword(View v){
        TextView feed = (TextView)findViewById(R.id.passwordFeedback);
        EditText p1 = (EditText)findViewById(R.id.password1);
        EditText p2 = (EditText)findViewById(R.id.password2);
        String valp1 = p1.getText().toString();
        String valp2 = p2.getText().toString();
        DatabaseLoader db;

        if(valp1.equals(valp2)){
            db = new DatabaseLoader();
            db.PasswordUpdate(email, valp1);
            feed.setText("Update Successful");
        }

        else
        {

            feed.setText("Passwords do not match");
        }


    }


}
