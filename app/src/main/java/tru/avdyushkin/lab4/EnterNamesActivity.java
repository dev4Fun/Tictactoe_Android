package tru.avdyushkin.lab4;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;


public class EnterNamesActivity extends Activity {
    String p1Name, p2Name;
    SharedPreferences gameStats;
    EditText editTextP1, editTextP2;
    Button saveBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameStats =
                PreferenceManager.getDefaultSharedPreferences(this);
        p1Name = gameStats.getString("Player1Nick", "Player1");
        p2Name = gameStats.getString("Player2Nick", "Player2");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);

        editTextP1 = (EditText)findViewById(R.id.editTextP1);
        editTextP2 = (EditText)findViewById(R.id.editTextP2);
        saveBtn = (Button)findViewById(R.id.button);
        backBtn = (Button)findViewById(R.id.button2);

        if(!p1Name.equals("Player1"))
            editTextP1.setText(p1Name);
        if(!p2Name.equals("Player2"))
            editTextP2.setText(p2Name);

        if(editTextP1.getText().toString().equals("") || editTextP2.getText().toString().equals(""))
            saveBtn.setEnabled(false);
        else saveBtn.setEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enter_names, menu);
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

    public void save(View v){
        if(!editTextP1.getText().toString().equals("") && !editTextP2.getText().toString().equals("") &&
                editTextP1.getText().toString().length() < 21 && editTextP2.getText().toString().length() < 21)
        {
            SharedPreferences.Editor editor = gameStats.edit();
            editor.putString("Player1Nick", editTextP1.getText().toString());
            editor.putString("Player2Nick", editTextP2.getText().toString());
            editor.apply();
            Toast toast = Toast.makeText(getApplicationContext(), "Names have been saved", Toast.LENGTH_SHORT);
            toast.show();

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Name fields can not be empty and max 20 characters", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void back(View v){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        finish();
        super.onPause();
    }
}
