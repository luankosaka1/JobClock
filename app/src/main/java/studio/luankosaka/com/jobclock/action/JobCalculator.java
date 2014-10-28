package studio.luankosaka.com.jobclock.action;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import studio.luankosaka.com.jobclock.R;
import studio.luankosaka.com.jobclock.model.TimePoint;
import studio.luankosaka.com.jobclock.sql.DatabaseHelper;

/**
 * Created by root on 28/10/14.
 */
public class JobCalculator {
    Context context;

    public JobCalculator(Context context) {
        this.context = context;
    }

    /**
     * Exibe (Toast) o total de horas trabalhados no mês
     */
    public void showTimeMonth() {
        Cursor cursor = new TimePoint(context).getMonth();
        long diffAll = countTime(cursor);

        Toast.makeText(context, "Total de horás trabalhadas no mês: " + calculatorTime(diffAll), Toast.LENGTH_SHORT).show();
    }

    /**
     * Exibe (Toast) o total de horas trabalhados no dia
     */
    public void showTimeNow() {
        Cursor cursor = new TimePoint(context).getNow();
        long diffAll = countTime(cursor);

        Toast.makeText(context, "Total de horás trabalhadas no dia: " + calculatorTime(diffAll) , Toast.LENGTH_SHORT).show();
    }

    /**
     * Calcula o tempo gasto para melhor visualização
     * @param time
     * @return
     */
    public String getCalculatorTime(long time) {
        if (time == 0) {
            return "Não é possível efetuar o calculo no momento. Registre mais pontos.";
        }

        Long diffSeconds = (time / 1000) % 60;
        long diffMinutes = (time / (60 * 1000)) % 60;
        long diffHours = time / (60 * 60 * 1000);

        return String.format("%03d:%02d:%02d", diffHours, diffMinutes, diffSeconds);
    }

    /**
     * Calcula o tempo gasto para melhor visualização
     * @param diff
     * @return
     */
    private String calculatorTime(long diff) {
        if (diff == 0) {
            return "Não é possível efetuar o calculo no momento. Registre mais pontos.";
        }

        long diffSeconds = (diff / 1000) % 60;
        long diffMinutes = (diff / (60 * 1000)) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        return String.format("%03d:%02d:%02d", diffHours, diffMinutes, diffSeconds);
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
        int countRegister = listOut.size();

        if (countRegister < 1) {
            return Long.valueOf(0);
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
}
