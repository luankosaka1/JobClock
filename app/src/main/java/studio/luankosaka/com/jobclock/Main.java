package studio.luankosaka.com.jobclock;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import studio.luankosaka.com.jobclock.sql.DatabaseHelper;


public class Main extends Activity {

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void login(View v) {
        startActivity(new Intent(this, Home.class));

        /*
        EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
        EditText inputPassword = (EditText) findViewById(R.id.inputPassword);
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha os campos em branco.", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase db = helper.getReadableDatabase();

            String[] args = {username, password};
            Cursor cursor = db.rawQuery("SELECT _id FROM user WHERE username = ? AND password = ?", args);

            if (cursor.getCount() == 1) {
                db.close();
                startActivity(new Intent(this, Home.class));
            } else {
                Toast.makeText(this, "Usuario ou senha incorreta.", Toast.LENGTH_SHORT).show();
            }
        }
        */
    }
}
