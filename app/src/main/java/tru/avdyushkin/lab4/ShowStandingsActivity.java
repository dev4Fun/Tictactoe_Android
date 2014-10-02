package tru.avdyushkin.lab4;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ShowStandingsActivity extends Activity {
    SharedPreferences gameStats;
    String p1Name, p2Name, appendStrP1, appendStrP2;
    int p1Stats, p2Stats;
    TextView p1standing, p2standing;
    Button reset, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_standings);

        gameStats =
                PreferenceManager.getDefaultSharedPreferences(this);
        p1Stats = gameStats.getInt("Player1Wins", 0);
        p2Stats = gameStats.getInt("Player2Wins", 0);
        p1Name = gameStats.getString("Player1Nick", "Player1");
        p2Name = gameStats.getString("Player2Nick", "Player2");
        p1standing = (TextView)findViewById(R.id.p1standing);
        p2standing = (TextView)findViewById(R.id.p2standing);

        if(p1Name.equals("Player1"))
            appendStrP1 = "";
        else appendStrP1 = " (" + p1Name  + ")";

        if(p2Name.equals("Player2"))
            appendStrP2 = "";
        else appendStrP2 = " (" + p2Name + ")";

       p1standing.setText("Player 1" + appendStrP1  + ": " + p1Stats + " wins");
       p2standing.setText("Player 2" + appendStrP2  + ": " + p2Stats + " wins");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_standings, menu);
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

    public void back(View v){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void reset_stats(View v){
        p1Stats = 0;
        p2Stats = 0;
        p1standing.setText("Player 1" + appendStrP1  + ": " + p1Stats + " wins");
        p2standing.setText("Player 2" + appendStrP2  + ": " + p2Stats + " wins");
        SharedPreferences.Editor editor = gameStats.edit();
        editor.putInt("Player1Wins", p1Stats);
        editor.putInt("Player2Wins", p2Stats);
        editor.apply(); // save stats to pref storage
    }
}
