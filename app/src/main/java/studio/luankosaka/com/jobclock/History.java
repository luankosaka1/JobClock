package studio.luankosaka.com.jobclock;

import android.app.Activity;
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

        switch (view.getId()) {
            case R.id.btn_now :
                showTimeNow();
                break;
            case R.id.btn_month :
                showTimeMonth();
                break;
        }
    }

    /**
     * Exibe (Toast) o total de horas trabalhados no mês
     */
    private void showTimeMonth() {
        Cursor cursor = new TimePoint(this).getMonth();
        long diffAll = countTime(cursor);

        Toast.makeText(this, "Total de horás trabalhadas no mês: " + calculatorTime(diffAll) , Toast.LENGTH_LONG).show();
    }

    /**
     * Exibe (Toast) o total de horas trabalhados no dia
     */
    private void showTimeNow() {
        Cursor cursor = new TimePoint(this).getNow();
        long diffAll = countTime(cursor);

        Toast.makeText(this, "Total de horás trabalhadas no dia: " + calculatorTime(diffAll) , Toast.LENGTH_LONG).show();
    }

    /**
     * Calcula o tempo gasto para melhor visualização
     * @param diff
     * @return
     */
    private String calculatorTime(long diff) {
        Long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);

        String jobTime = diffHours + " horas " + diffMinutes + " minutos " + diffSeconds.toString().substring(0,2) + " segundos";
        return jobTime;
    }

    /**
     * Consulta e separa o tempo de entrada e saída
     * @return
     */
    private Long countTime(Cursor cursor) {
        cursor.moveToFirst();

        ArrayList<String> listIn = new ArrayList<String>();
        ArrayList<String> listOut = new ArrayList<String>();

        for (int i=0; i < cursor.getCount(); ++i) {
            if (i % 2 == 0) {
                listIn.add(cursor.getString(2));
            } else {
                listOut.add(cursor.getString(2));
            }
            cursor.moveToNext();
        }

        cursor.close();

        return diffTime(listIn, listOut);
    }

    /**
     * Soma o horário de entrada e saída
     * @param listIn
     * @param listOut
     * @return
     */
    private Long diffTime(ArrayList<String> listIn, ArrayList<String> listOut) {
        int countRegister = 0;

        if (listIn.size() > listOut.size()) {
            countRegister = listOut.size();
        } else {
            countRegister = listIn.size();
        }

        long diffAll = 0;
        for (int i=0; i < countRegister; i++) {
            String dateStart = listIn.get(i).toString();
            String dateStop = listOut.get(i).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = null;
            Date d2 = null;

            try {
                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            diffAll += d2.getTime() - d1.getTime();
        }

        return diffAll;
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
