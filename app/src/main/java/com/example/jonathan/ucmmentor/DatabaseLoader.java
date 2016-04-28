package com.example.jonathan.ucmmentor;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Alex on 4/27/2016.
 */
public class DatabaseLoader {

    private String DB_PATH = "data/data/com.example.jonathan.ucmmentor/"; // path
    private String DBNAME = "ucm_mentor.db";
    String outFileName = DB_PATH + DBNAME;
    SQLiteDatabase db;

    public DatabaseLoader()
    {
    }

    public void copyDatabase(Context c)
    {
        try {
            InputStream is = c.getAssets().open(DBNAME);
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            is.close();
            is = new FileInputStream(DB_PATH + DBNAME);

            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkData(Context c)
    {
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);
        }

        catch (SQLException e) {
            e.printStackTrace();
            copyDatabase(c);
        }

        db.close();

    }


    public String[] QuerieDatabase(Context context, String nick, Boolean name){

       checkData(context);
        db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);

        String q1 = " ";
        String[] data;
        String column;

        ArrayList<String> arrTblNames = new ArrayList<String>();

        //True pulls Mentee name
        //False pulls Mentee email

        if (name == true) {
            q1 = "Select u_Name FROM Users, Mentees WHERE u_ID = mn_UserID and mn_Choice LIKE '" + nick + "'";
            column = "u_Name";
        }
        else
            {
                q1 = "Select u_email FROM Users, Mentees WHERE u_ID = mn_UserID and mn_Choice LIKE '" + nick + "'";
                column = "u_email";
            }



        try{
            Cursor c = db.rawQuery(q1 ,null);

            if (c.moveToFirst()){
                do{
                    arrTblNames.add(c.getString(c.getColumnIndex(column)));
                }while(c.moveToNext());
            }
            c.close();


        }catch (SQLException e){
            e.printStackTrace();
        }

        data = new String[arrTblNames.size()];
        arrTblNames.toArray(data);

        db.close();
        return data;
    }

    public String[] readMentors( Context context){

        //DATABASE

        String[] arr;

        checkData(context);
        db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);

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
        c = db.rawQuery("SELECT m_Nickname FROM Mentor", null);

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

        return arr;
    }

    public String readName(Context context, String email){

        checkData(context);
        db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);
        String data = " ";

        String q1 = "Select u_Name FROM Users WHERE u_email LIKE '" + email + "'";

        try{

            Cursor c = db.rawQuery(q1 ,null);

            if (c.moveToFirst()){
                do{
                    data = c.getString(c.getColumnIndex("u_Name"));
                    // do what ever you want here
                }while(c.moveToNext());
            }
            c.close();


        }catch (SQLException e){
            e.printStackTrace();
        }

        db.close();

        return data;
    }

    public String readNick(Context context, String email){

        checkData(context);
        db = SQLiteDatabase.openDatabase(DB_PATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);

        String data = " ";
        String q1 = "Select m_Nickname FROM Mentor, Users WHERE u_ID = m_UserID AND u_email LIKE '" + email + "'";

        try{

            Cursor c = db.rawQuery(q1 ,null);

            if (c.moveToFirst()){
                do{
                    data = c.getString(c.getColumnIndex("m_Nickname"));
                }while(c.moveToNext());
            }
            c.close();


        }catch (SQLException e){
            e.printStackTrace();
        }

        db.close();
        return data;
    }

}
