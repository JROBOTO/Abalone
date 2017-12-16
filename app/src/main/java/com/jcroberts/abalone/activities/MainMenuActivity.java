package com.jcroberts.abalone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jcroberts.abalone.R;

public class MainMenuActivity extends AppCompatActivity {
    private Intent intent;

    private Button oneValueButton;
    private Button twoValuesButton;

    private String oneDeviceString;
    private String twoDevicesString;
    private String onePlayerString;
    private String twoPlayersString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        oneValueButton = (Button)findViewById(R.id.oneValueButton);
        twoValuesButton = (Button)findViewById(R.id.twoValuesButton);

        oneDeviceString = getResources().getString(R.string.oneDevice);
        twoDevicesString = getResources().getString(R.string.twoDevices);

        onePlayerString = getResources().getString(R.string.onePlayer);
        twoPlayersString = getResources().getString(R.string.twoPlayers);
    }

    /**
     * Whenever the activity is visited, the app forgets that the number of players was selected
     */
    @Override
    protected void onResume(){
        super.onResume();

        resetButtons();
    }

    /**
     * Activated when one of the start game buttons are pressed. Starts the game activity with
     * the information of how many players will be playing and on how many devices.
     * @param v Which button is pressed.
     */
    public void twoPlayersButtonSelected(View v){
        showSecondOptions();
    }

    public void playMultiDeviceGame(View v){
        intent = new Intent(this, MultiDeviceGameActivity.class);
        startActivity(intent);
    }

    public void play2Player1DeviceGame(View v){
        intent = new Intent(this, TwoPlayersOneDeviceGameActivity.class);
        startActivity(intent);
    }

    public void playSinglePlayerGame(View v){
        intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void resetButtons(){
        oneValueButton.setText(onePlayerString);
        twoValuesButton.setText(twoPlayersString);

        oneValueButton.setOnClickListener(new OnePlayerOnClickListener());
        twoValuesButton.setOnClickListener(new TwoDevicesOnClickListener());
    }

    private void showSecondOptions(){
        oneValueButton.setText(oneDeviceString);
        twoValuesButton.setText(twoDevicesString);

        oneValueButton.setOnClickListener(new TwoPlayerOneDeviceOnClickListener());
        twoValuesButton.setOnClickListener(new TwoPlayersTwoDevicesOnClickListener());
    }

    /**
     * Activated when the help button is pressed. Starts the help activity.
     * @param v Which button is pressed.
     */
    public void goToHelp(View v){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private class TwoPlayerOneDeviceOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            intent = new Intent(v.getContext(), TwoPlayersOneDeviceGameActivity.class);
            startActivity(intent);
        }
    }

    private class TwoPlayersTwoDevicesOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            intent = new Intent(v.getContext(), MultiDeviceGameActivity.class);
            startActivity(intent);
        }
    }

    private class TwoDevicesOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            showSecondOptions();
        }
    }

    private class OnePlayerOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            intent = new Intent(v.getContext(), SinglePlayerGameActivity.class);
            startActivity(intent);
        }
    }
}
