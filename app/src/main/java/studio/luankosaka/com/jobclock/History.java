package studio.luankosaka.com.jobclock;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import studio.luankosaka.com.jobclock.action.JobCalculator;
import studio.luankosaka.com.jobclock.model.TimePoint;
import studio.luankosaka.com.jobclock.sql.DatabaseHelper;

public class History extends Activity {
    DatabaseHelper helper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(getHistory());
    }

    public void clickInfo(View view) {
        JobCalculator calculator = new JobCalculator(this);

        switch (view.getId()) {
            case R.id.btn_now :
                calculator.showTimeNow();
                break;
            case R.id.btn_month :
                calculator.showTimeMonth();
                break;
            case R.id.btn_back :
                startActivity(new Intent(this, Home.class));
                break;
        }
    }

    /**
     * Adiciona os registros na lista
     * @return
     */
    private ArrayAdapter getHistory() {
        Cursor cursor = new TimePoint(this).getNow();
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
}
