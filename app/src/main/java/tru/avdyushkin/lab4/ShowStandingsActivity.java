package tru.avdyushkin.lab4;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class ShowStandingsActivity extends Activity {
    SharedPreferences gameStats;
    String p1Name, p2Name;
    int p1Stats, p2Stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        gameStats =
                PreferenceManager.getDefaultSharedPreferences(this);
        p1Stats = gameStats.getInt("Player1Wins", 0);
        p2Stats = gameStats.getInt("Player2Wins", 0);
        p1Name = gameStats.getString("Player1Nick", "Player1");
        p2Name = gameStats.getString("Player2Nick", "Player2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_standings);
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
}
