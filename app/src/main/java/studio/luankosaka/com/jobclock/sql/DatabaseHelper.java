package studio.luankosaka.com.jobclock.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "job_clock";
    private static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "password TEXT, " +
                "name TEXT, " +
                "workday TIME);");

        db.execSQL("INSERT INTO user (username, password, name, workday) VALUES('luan', 'luan', 'Luan', '08:00:00');");

        db.execSQL("CREATE TABLE work (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "time_point DATETIME  DEFAULT current_timestamp, " +
                "FOREIGN KEY(user_id) REFERENCES user(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
