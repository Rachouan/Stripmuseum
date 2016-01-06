package be.ehb.dt.stripmuseum.assets;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by rachouanrejeb on 06/01/16.
 */
public class ComicDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Comics.db";
    private String contract;

    public ComicDBHelper(Context context, String contract) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.contract = contract;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ComicContract.SQL_CREATE_ENTRIES);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ComicContract.SQL_DELETE_ENTRIES);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

