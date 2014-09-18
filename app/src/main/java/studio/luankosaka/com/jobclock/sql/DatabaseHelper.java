package studio.luankosaka.com.jobclock.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "job_clock";
    private static int VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (_id INTEGER PRIMARY KEY, " +
                "username TEXT, " +
                "password TEXT, " +
                "name TEXT, " +
                "workday TIME);");

        db.execSQL("CREATE TABLE work (_id INTEGER PRIMARY KEY, " +
                "user_id INTEGER, " +
                "time_point DATETIME, " +
                "FOREIGN KEY(user_id) REFERENCES user(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
