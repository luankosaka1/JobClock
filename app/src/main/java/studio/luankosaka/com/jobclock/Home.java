package studio.luankosaka.com.jobclock;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import studio.luankosaka.com.jobclock.action.JobCalculator;
import studio.luankosaka.com.jobclock.sql.DatabaseHelper;


public class Home extends Activity {

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helper = new DatabaseHelper(this);
    }

    public void clickInfo(View view) {
        JobCalculator calculator = new JobCalculator(this);

        switch (view.getId()) {
            case R.id.btn_day :
                startActivity(new Intent(this, History.class));
                break;
            case R.id.btn_listMonth :
                startActivity(new Intent(this, ListMonth.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkPointer(View v) {
        ImageView pointer = (ImageView) findViewById(R.id.imagePointer);
        pointer.setImageResource(R.drawable.one_finger_click_512);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        // time point
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        values.put("time_point", now.format(new Date()));

        // user id
        values.put("user_id", 1);

        Log.i("INSERT DB", "TIME: " + now.format(new Date()));

        long result = db.insert("work", null, values);

        if (result != -1) {
            //Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, History.class));
        } else {
            Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ImageView pointer = (ImageView) findViewById(R.id.imagePointer);
        pointer.setImageResource(R.drawable.one_finger_double_tap_512);
    }
}
