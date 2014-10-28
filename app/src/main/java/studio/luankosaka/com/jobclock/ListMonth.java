package studio.luankosaka.com.jobclock;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import studio.luankosaka.com.jobclock.action.JobCalculator;
import studio.luankosaka.com.jobclock.model.TimePoint;


public class ListMonth extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_month);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(getHistory());
    }

    public void clickInfo(View view) {
        JobCalculator calculator = new JobCalculator(this);

        switch (view.getId()) {
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
        // resgata os registros no DB
        Cursor cursor = new TimePoint(this).getListMonth();
        cursor.moveToFirst();

        // guarda o tempo anterior
        Long timeOld = Long.valueOf(0);

        // armazena o tempo do mês
        Map<String, Long> map = new HashMap<String, Long>();

        for (int i=0; i < cursor.getCount(); ++i) {
            SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                String date = cursor.getString(0);
                Long time = format.parse(date).getTime();

                // inscremento
                if (map.containsKey(formatMonth.format(time))) {
                    Long timeIncrement = map.get(formatMonth.format(time));
                    Long timeSum = (time - timeOld) + timeIncrement;

                    map.put(formatMonth.format(time), timeSum);

                    Log.i("Subtração", time+" - "+timeOld+" = "+timeSum);
                } else {
                    map.put(formatMonth.format(time), Long.valueOf(0));
                }

                timeOld = time;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();

        ArrayList<String> list = new ArrayList<String>();
        if (map.size() > 0) {
            for (Object time : map.keySet()) {
                list.add(time.toString() +": " + new JobCalculator(this).getCalculatorTime(map.get(time)));
            }
        }

        Collections.sort(list, Collections.reverseOrder());

        return new ArrayAdapter<String>(this, R.layout.list_item_custom, list);
    }
}
