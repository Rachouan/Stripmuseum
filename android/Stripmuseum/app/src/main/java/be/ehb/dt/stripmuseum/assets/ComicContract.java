package be.ehb.dt.stripmuseum.assets;

import android.provider.BaseColumns;

/**
 * Created by rachouanrejeb on 06/01/16.
 */
public class ComicContract {

    private static final String TEXT_TYPE = " TEXT";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ComicEntry.TABLE_NAME + " (" +
                    ComicEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ComicEntry.COLUMN_IMAGE + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ComicEntry.TABLE_NAME;

    public static abstract class ComicEntry implements BaseColumns {
        public static final String TABLE_NAME ="comics";
        public static final String _ID ="id";
        public static final String COLUMN_IMAGE ="image";
    }
}
