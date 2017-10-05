package com.jcroberts.abalone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {
    private final int ONE_PLAYER = 1;
    private final int TWO_PLAYERS = 2;

    private final int ONE_DEVICE = 1;
    private final int TWO_DEVICES = 2;

    private Button oneValueButton;
    private Button twoValuesButton;

    private boolean twoPlayersSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        oneValueButton = (Button)findViewById(R.id.oneValueButton);
        twoValuesButton = (Button)findViewById(R.id.twoValuesButton);


    }

    /**
     * Whenever the activity is visited, the app forgets that the number of players was selected
     */
    @Override
    protected void onResume(){
        super.onResume();

        twoPlayersSelected = false;
    }

    /**
     * Activated when one of the start game buttons are pressed. Starts the game activity with
     * the information of how many players will be playing and on how many devices.
     * @param v Which button is pressed.
     */
    public void playGame(View v){
        Intent intent = new Intent(this, GameActivity.class);

        if(v.getId() == R.id.twoValuesButton){
            if(!twoPlayersSelected) {
                oneValueButton.setText("1 Device");
                twoValuesButton.setText("2 Devices");
                twoPlayersSelected = true;
            }
            else{
                intent.putExtra("numberOfPlayers", TWO_PLAYERS);
                intent.putExtra("numberOfDevices", TWO_DEVICES);
                startActivity(intent);
            }
        }
        else{
            if(!twoPlayersSelected) {
                intent.putExtra("numberOfPlayers", ONE_PLAYER);
                intent.putExtra("numberOfDevices", ONE_DEVICE);
                startActivity(intent);
            }
            else{
                intent.putExtra("numberOfPlayers", TWO_PLAYERS);
                intent.putExtra("numberOfDevices", ONE_DEVICE);
                startActivity(intent);
            }
        }
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
