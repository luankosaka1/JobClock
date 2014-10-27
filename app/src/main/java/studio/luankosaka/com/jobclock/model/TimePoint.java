package studio.luankosaka.com.jobclock.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import studio.luankosaka.com.jobclock.sql.DatabaseHelper;

/**
 * Created by root on 27/10/14.
 */
public class TimePoint {
    Context context;
    DatabaseHelper helper;

    public TimePoint(Context context) {
        this.context = context;
    }

    public Cursor getNow() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String now = date.format(new Date());
        helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Log.i("GET Now: ", "SELECT _id, user_id, time_point FROM work WHERE user_id = ? AND time_point LIKE '%" + now + "%'");

        return db.rawQuery(
            "SELECT _id, user_id, time_point FROM work WHERE user_id = ? AND time_point LIKE '%" + now + "%'",
            new String[] {"1"}
        );
    }

    public Cursor getMonth() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String now = date.format(new Date());
        helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Log.i("GET Month: ", "SELECT _id, user_id, time_point FROM work WHERE user_id = ? AND time_point LIKE '%" + now + "%'");

        return db.rawQuery(
                "SELECT _id, user_id, time_point FROM work WHERE user_id = ? AND time_point LIKE '%" + now + "%'",
                new String[] {"1"}
        );
    }
}
