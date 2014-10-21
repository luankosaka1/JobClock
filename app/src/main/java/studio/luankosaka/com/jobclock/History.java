package studio.luankosaka.com.jobclock;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import studio.luankosaka.com.jobclock.sql.DatabaseHelper;


public class History extends Activity {
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(getHistory());
    }

    private ArrayAdapter getHistory() {
        Cursor cursor = selectHistory();
        cursor.moveToFirst();

        ArrayList<String> list = new ArrayList<String>();
        for (int i=0; i < cursor.getCount(); ++i) {
            Integer user_id = cursor.getInt(1);
            String time_point = cursor.getString(2);
            list.add("#" + user_id + " : " + time_point);
            cursor.moveToNext();
        }
        cursor.close();

        return new ArrayAdapter<String>(this, R.layout.list_item_custom, list);
    }

    private Cursor selectHistory() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String now = date.format(new Date());
        helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.rawQuery(
                "SELECT _id, user_id, time_point FROM work WHERE user_id = ? AND time_point LIKE '%" + now + "%' ORDER BY time_point DESC",
                new String[] {"1"}
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
