package com.jcroberts.abalone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {
    private final int ONE_PLAYER = 1;
    private final int TWO_PLAYERS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * Activated when one of the start game buttons are pressed. Starts the game activity with
     * the information of how many players will be playing.
     * @param v Which button is pressed.
     */
    public void playGame(View v){
        Intent intent = new Intent(this, GameActivity.class);

        if(v.getId() == R.id.twoPlayerOptionButton){
            intent.putExtra("numberOfPlayers", TWO_PLAYERS);
        }
        else{
            intent.putExtra("numberOfPlayers", ONE_PLAYER);
        }

        startActivity(intent);
    }

    /**
     * Activated when the help button is pressed. Starts the help activity.
     * @param v Which button is pressed.
     */
    public void goToHelp(View v){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
