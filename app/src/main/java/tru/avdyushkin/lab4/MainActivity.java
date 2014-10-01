package tru.avdyushkin.lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.gosylvester.bestrides.util.KmlSummary";
    private final String menuData[] = {"Enter names", "Play game", "Standings"};
    ArrayList<String> list = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list.addAll(Arrays.asList(menuData));
        listView = (ListView)findViewById(R.id.listView);
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.list_view, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                String item = adapter.getItem(i);
                if(item.equals("Enter names"))
                    intent = new Intent(getBaseContext(), EnterNamesActivity.class);
                else if(item.equals("Play game"))
                    intent = new Intent(getBaseContext(), PlayGameActivity.class);
                else
                    intent = new Intent(getBaseContext(), ShowStandingsActivity.class); // scoreboard

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
