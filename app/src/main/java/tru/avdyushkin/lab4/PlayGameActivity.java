package tru.avdyushkin.lab4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class PlayGameActivity extends Activity {
    SharedPreferences gameStats;
    int p1Stats, p2Stats;
    String p1Name, p2Name;
    TextView[][] cells = new TextView[3][3]; // game field 3x3
    TextView playerTurn;
    static int turn; // current turn, starts from 0
    static boolean flipTurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        gameStats =
                PreferenceManager.getDefaultSharedPreferences(this); // store and retrieve game stats
        p1Name = gameStats.getString("Player1Nick", "Player1");
        p2Name = gameStats.getString("Player2Nick", "Player2");
        p1Stats = gameStats.getInt("Player1Wins", 0);
        p2Stats = gameStats.getInt("Player2Wins", 0);

        playerTurn = (TextView)findViewById(R.id.playerTurn);

        // initialize all the text fields and bind them to layout
        cells[0][0] = (TextView)findViewById(R.id.textView1);
        cells[0][1] = (TextView)findViewById(R.id.textView2);
        cells[0][2] = (TextView)findViewById(R.id.textView3);
        cells[1][0] = (TextView)findViewById(R.id.textView4);
        cells[1][1] = (TextView)findViewById(R.id.textView5);
        cells[1][2] = (TextView)findViewById(R.id.textView6);
        cells[2][0] = (TextView)findViewById(R.id.textView7);
        cells[2][1] = (TextView)findViewById(R.id.textView8);
        cells[2][2] = (TextView)findViewById(R.id.textView9);

        resetField();
        flipTurns = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_game, menu);
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

    public void backToMenu(View v){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void placeSymbol(View v){ // handles a click on a cell
        Log.i("MyMSG", "p1 stat" + p1Stats + " p2 stat" + p2Stats);
        TextView cell = (TextView) v; // current cell being pressed
        if(cell.getText().toString().equals("")){
            // get which players turn and place X or O

            if(turn % 2 == 0) // player 1 turn
            {
                if(!flipTurns) {
                    cell.setText("X");
                    playerTurn.setText(p2Name + "'s turn");
                } else {
                    cell.setText("O");
                    playerTurn.setText(p1Name + "'s turn");
                }
            }
            else {
                if(!flipTurns){
                    cell.setText("O");
                    playerTurn.setText(p1Name + "'s turn");
                } else {
                    cell.setText("X");
                    playerTurn.setText(p2Name + "'s turn");
                }

            }

            // check for a winner
            if(!checkForWin())
                turn++;
        }

    }

    public void resetField(){
        /*
        Reset the whole field with all variables
         */
        for(int row = 0; row < 3; row++)
            for(int col = 0; col < 3; col++)
                cells[row][col].setText("");
        turn = 0;

        if(flipTurns)
            playerTurn.setText(p2Name + "'s turn");
        else
            playerTurn.setText(p1Name + "'s turn");
    }

    public boolean checkForWin(){
        /*
        00 01 02
        10 11 12
        20 21 22
         */

        if(turn > 3) // the earliest win
        {
         for(int i =0; i < 3; i++) {
             if (checkRow(i) || checkCol(i)) {
                 endGame();
                 return true;
             }
         }
         if(checkDiagonals()) {
             endGame();
             return true;
         }
         if(turn == 8) {
             endGame();
         }
        }
        return false;
    }

    private boolean checkRow(int row){
        /*
        Check a row to find a winner
         */
        return (cells[row][0].getText() != "" && cells[row][0].getText() == cells[row][1].getText() &&
                cells[row][1].getText() == cells[row][2].getText());

    }

    private boolean checkCol(int col){
         /*
        Check a col to find a winner
         */
        return (cells[0][col].getText() != "" && cells[0][col].getText() == cells[1][col].getText() &&
                cells[1][col].getText() == cells[2][col].getText());
    }

    private boolean checkDiagonals(){
        /*
        Check diagonals to find a winner
         */
        return ((cells[0][0].getText() != "" && cells[0][0].getText() == cells[1][1].getText() &&
                cells[1][1].getText() == cells[2][2].getText()) ||
                (cells[0][2].getText() != "" && cells[0][2].getText() == cells[1][1].getText() &&
                        cells[1][1].getText() == cells[2][0].getText()));
    }

    private void endGame(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(turn == 8){
            builder.setMessage("It is a draw");
        } else if(turn % 2 == 0 && !flipTurns) // player 1 win
        {
            builder.setMessage(p1Name + " has won!");
            p1Stats++;

        } else {
            builder.setMessage(p2Name + " has won!");
            p2Stats++;
        }

        builder.setCancelable(false);
        builder.setNegativeButton("Play One More",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        resetField();
                    }
                });

        builder.setPositiveButton("Back to Menu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        flipTurns = !flipTurns; // change turns
    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = gameStats.edit();
        editor.putInt("Player1Wins", p1Stats);
        editor.putInt("Player2Wins", p2Stats);
        editor.apply(); // save stats to pref storage
        super.onPause();
    }
}
