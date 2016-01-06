package be.ehb.dt.stripmuseum.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import be.ehb.dt.stripmuseum.assets.ComicContract;
import be.ehb.dt.stripmuseum.assets.ComicDBHelper;

/**
 * Created by rachouanrejeb on 06/01/16.
 */
public class ComicDAO {

    SQLiteOpenHelper mDbHelper;
    SQLiteDatabase db;

    public ComicDAO(Context context){
        mDbHelper = new ComicDBHelper(context, "comics");
    }

    public void open() throws SQLException {
        db = mDbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        db.close();
    }

    public void insertComic(String image){
        ContentValues values = new ContentValues();
        values.put(ComicContract.ComicEntry.COLUMN_IMAGE, image);

        long newRowId;
        newRowId = db.insert(
                ComicContract.ComicEntry.TABLE_NAME,
                null,
                values);
    }

    public void deleteComic(){
        db.delete("comics", null, null);
    }

    public ArrayList getComics(){
        ArrayList<String> rij = new ArrayList<String>();
        String image = "";
        int g_id = -1;

        Cursor cursor = db.rawQuery("SELECT * FROM comics",null);


        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                image =  cursor.getString(1);

                rij.add(image);
                Log.d("database: ","image: " + image);
            }
            while (cursor.moveToNext());
        }

        cursor.close();


        return rij;
    }

}
