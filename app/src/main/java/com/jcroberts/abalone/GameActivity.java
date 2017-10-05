package com.jcroberts.abalone;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

/**
 * The main game activity which builds the game board and allows turns to be taken to play the
 * game. It runs a terminal test after each turn whether it is a human player or not.
 *
 * Author: Joshua Roberts
 */

//TODO Create counter movement
public class GameActivity extends AppCompatActivity {
    protected ImageView[][] gameBoard;

    protected GridSelectionsObject gridSelections;
    protected LegalityChecker legChecker;

    private int playerToTakeTurn;
    private int numberOfPlayers;
    private int numberOfDevices;
    private int numberOfPlayer1CountersTaken;
    private int numberOfPlayer2CountersTaken;

    private boolean gameEnded;

    private MultiDevice multiDevice;
    private IntentFilter intentFilter;
    private WifiBroadcastReceiver wifiBroadcastReceiver;


    private AI computerPlayer;
    /**
     * Main creation method for the game to create the game board and run the main game loop
     * @param savedInstanceState The saved instance from the last activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 1);
        numberOfDevices = getIntent().getIntExtra("numberOfDevices", 1);

        if(numberOfPlayers == 2 && numberOfDevices == 2){
            setupMultiDevice();
        }
        else if(numberOfPlayers == 1){
            computerPlayer = new AI();
        }

        System.out.println("ACTIVITY OPENED");

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        setupGameBoard();

        //Initialize game logic
        gameEnded = false;
        numberOfPlayer1CountersTaken = 0;
        numberOfPlayer2CountersTaken = 0;

        legChecker = new LegalityChecker();
        gridSelections = new GridSelectionsObject();

        //randomize who the first player is
        Random rand = new Random();

        int playerToTakeFirstTurn = rand.nextInt(1) + 1;
        if(playerToTakeFirstTurn == 1){
            playerToTakeTurn = 1;
        }
        else{
            playerToTakeTurn = 2;
        }

        Toast.makeText(this, "Player " + Integer.toString(playerToTakeFirstTurn) + " to go first", Toast.LENGTH_LONG).show();

    }

    /**
     * Overridden method
     * Register the receiver
     */
    @Override
    protected void onResume(){
        super.onResume();
        if(numberOfPlayers == 2 && numberOfDevices == 2) {
            registerReceiver(wifiBroadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(numberOfPlayers == 2 && numberOfDevices == 2) {
            unregisterReceiver(wifiBroadcastReceiver);
        }
    }

    /**
     * Set up the game board in terms of initializing each space in turn
     */
    private void setupGameBoard(){
        ImageView[] row0;
        ImageView[] row1;
        ImageView[] row2;
        ImageView[] row3;
        ImageView[] row4;
        ImageView[] row5;
        ImageView[] row6;
        ImageView[] row7;
        ImageView[] row8;

        row0 = new ImageView[5];

        row0[0] = (ImageView)findViewById(R.id.row0Column0);
        row0[1] = (ImageView)findViewById(R.id.row0Column1);
        row0[2] = (ImageView)findViewById(R.id.row0Column2);
        row0[3] = (ImageView)findViewById(R.id.row0Column3);
        row0[4] = (ImageView)findViewById(R.id.row0Column4);

        row1 = new ImageView[6];

        row1[0] = (ImageView)findViewById(R.id.row1Column0);
        row1[1] = (ImageView)findViewById(R.id.row1Column1);
        row1[2] = (ImageView)findViewById(R.id.row1Column2);
        row1[3] = (ImageView)findViewById(R.id.row1Column3);
        row1[4] = (ImageView)findViewById(R.id.row1Column4);
        row1[5] = (ImageView)findViewById(R.id.row1Column5);

        row2 = new ImageView[7];

        row2[0] = (ImageView)findViewById(R.id.row2Column0);
        row2[1] = (ImageView)findViewById(R.id.row2Column1);
        row2[2] = (ImageView)findViewById(R.id.row2Column2);
        row2[3] = (ImageView)findViewById(R.id.row2Column3);
        row2[4] = (ImageView)findViewById(R.id.row2Column4);
        row2[5] = (ImageView)findViewById(R.id.row2Column5);
        row2[6] = (ImageView)findViewById(R.id.row2Column6);

        row3 = new ImageView[8];

        row3[0] = (ImageView)findViewById(R.id.row3Column0);
        row3[1] = (ImageView)findViewById(R.id.row3Column1);
        row3[2] = (ImageView)findViewById(R.id.row3Column2);
        row3[3] = (ImageView)findViewById(R.id.row3Column3);
        row3[4] = (ImageView)findViewById(R.id.row3Column4);
        row3[5] = (ImageView)findViewById(R.id.row3Column5);
        row3[6] = (ImageView)findViewById(R.id.row3Column6);
        row3[7] = (ImageView)findViewById(R.id.row3Column7);

        row4 = new ImageView[9];

        row4[0] = (ImageView)findViewById(R.id.row4Column0);
        row4[1] = (ImageView)findViewById(R.id.row4Column1);
        row4[2] = (ImageView)findViewById(R.id.row4Column2);
        row4[3] = (ImageView)findViewById(R.id.row4Column3);
        row4[4] = (ImageView)findViewById(R.id.row4Column4);
        row4[5] = (ImageView)findViewById(R.id.row4Column5);
        row4[6] = (ImageView)findViewById(R.id.row4Column6);
        row4[7] = (ImageView)findViewById(R.id.row4Column7);
        row4[8] = (ImageView)findViewById(R.id.row4Column8);

        row5 = new ImageView[8];

        row5[0] = (ImageView)findViewById(R.id.row5Column0);
        row5[1] = (ImageView)findViewById(R.id.row5Column1);
        row5[2] = (ImageView)findViewById(R.id.row5Column2);
        row5[3] = (ImageView)findViewById(R.id.row5Column3);
        row5[4] = (ImageView)findViewById(R.id.row5Column4);
        row5[5] = (ImageView)findViewById(R.id.row5Column5);
        row5[6] = (ImageView)findViewById(R.id.row5Column6);
        row5[7] = (ImageView)findViewById(R.id.row5Column7);

        row6 = new ImageView[7];

        row6[0] = (ImageView)findViewById(R.id.row6Column0);
        row6[1] = (ImageView)findViewById(R.id.row6Column1);
        row6[2] = (ImageView)findViewById(R.id.row6Column2);
        row6[3] = (ImageView)findViewById(R.id.row6Column3);
        row6[4] = (ImageView)findViewById(R.id.row6Column4);
        row6[5] = (ImageView)findViewById(R.id.row6Column5);
        row6[6] = (ImageView)findViewById(R.id.row6Column6);

        row7 = new ImageView[6];

        row7[0] = (ImageView)findViewById(R.id.row7Column0);
        row7[1] = (ImageView)findViewById(R.id.row7Column1);
        row7[2] = (ImageView)findViewById(R.id.row7Column2);
        row7[3] = (ImageView)findViewById(R.id.row7Column3);
        row7[4] = (ImageView)findViewById(R.id.row7Column4);
        row7[5] = (ImageView)findViewById(R.id.row7Column5);

        row8 = new ImageView[5];

        row8[0] = (ImageView)findViewById(R.id.row8Column0);
        row8[1] = (ImageView)findViewById(R.id.row8Column1);
        row8[2] = (ImageView)findViewById(R.id.row8Column2);
        row8[3] = (ImageView)findViewById(R.id.row8Column3);
        row8[4] = (ImageView)findViewById(R.id.row8Column4);

        gameBoard = new ImageView[9][9];

        gameBoard[0] = row0;
        gameBoard[1] = row1;
        gameBoard[2] = row2;
        gameBoard[3] = row3;
        gameBoard[4] = row4;
        gameBoard[5] = row5;
        gameBoard[6] = row6;
        gameBoard[7] = row7;
        gameBoard[8] = row8;

        for(int i = 0; i < 9; i++){
            if(i <= 4){
                for(int j = 0; j < i + 5; j++){
                    int[] gl = new int[2];
                    gl[0] = i;
                    gl[1] = j;
                    GridLocationClickListener glcl = new GridLocationClickListener(gl);
                    gameBoard[i][j].setOnClickListener(glcl);
                }
            }
            else{
                for(int j = 0; j < 13 - i; j++){
                    int[] gl = new int[2];
                    gl[0] = i;
                    gl[1] = j;
                    GridLocationClickListener glcl = new GridLocationClickListener(gl);
                    gameBoard[i][j].setOnClickListener(glcl);
                }
            }
        }
    }

    /**
     * Test if the game has ended
     */
    private void runTerminalTest(){
        if(numberOfPlayer1CountersTaken >= 6 || numberOfPlayer2CountersTaken >= 6){
            gameEnded = true;
        }
    }

    private void setupMultiDevice(){
        WifiP2pManager someP2pManager = (WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel channel = someP2pManager.initialize(this, getMainLooper(), null);
        wifiBroadcastReceiver = new WifiBroadcastReceiver(someP2pManager, channel);
        multiDevice = new MultiDevice(someP2pManager, channel, wifiBroadcastReceiver);
    }

    /**
     * Click listener for when some counters have been selected to move and now a location needs to be selected to move to.
     */
    private class GridLocationClickListener implements View.OnClickListener{
        private int[] gridLocation;

        private GridLocationClickListener(int[] gl){
            gridLocation = gl;
        }

        @Override
        public void onClick(View v) {
            ImageView location = (ImageView)v;

            //TODO if the counter selected is on the side of the player then add it to the list of selections. If it is a neutral counter in line, move. Else cancel selections
            if(playerToTakeTurn == 1){
                if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.player1counter).getConstantState())){
                    if(legChecker.counterSelectionIsLegal(gridLocation, gridSelections)){
                        gridSelections.add(gridLocation[0], gridLocation[1]);
                        gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player1counterselected);
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }
                else if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.neutralcounter).getConstantState())){
                    if(legChecker.checkMoveIsLegal(gridLocation, gridSelections, false)){
                        //TODO move

                        runTerminalTest();
                        if(!gameEnded) {
                            gridSelections = new GridSelectionsObject();
                            playerToTakeTurn = 2;
                        }
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }
                else if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.player2counter).getConstantState())){
                    if(legChecker.checkMoveIsLegal(gridLocation, gridSelections, true)){
                        //TODO

                        runTerminalTest();
                        if(!gameEnded) {
                            gridSelections = new GridSelectionsObject();
                            playerToTakeTurn = 2;
                        }
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }

            }
            else{
                if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.player2counter).getConstantState())){
                    if(legChecker.counterSelectionIsLegal(gridLocation, gridSelections)){
                        gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player2counterselected);
                    }
                    else{
                        resetPlayerSelections(2);
                    }
                }
                else if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.neutralcounter).getConstantState())){
                    if(legChecker.checkMoveIsLegal(gridLocation, gridSelections, false)) {
                        //TODO make a move

                        runTerminalTest();
                        if(!gameEnded) {
                            playerToTakeTurn = 1;
                        }
                    }
                    else{
                        resetPlayerSelections(2);
                    }

                }
                else if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.player1counter).getConstantState())){
                    if(legChecker.checkMoveIsLegal(gridLocation, gridSelections, true)){
                        //TODO

                        runTerminalTest();
                        if(!gameEnded) {
                            playerToTakeTurn = 1;
                        }
                    }
                    else{
                        resetPlayerSelections(2);
                    }
                }
            }
        }



        /**
         * Cancel the players current selections
         * @param player Which player is currently taking a turn
         */
        private void resetPlayerSelections(int player){
            int numberOfCountersSelected = gridSelections.getNumberOfCountersSelected();
            int[][] selectionsMade = gridSelections.getSelectionsMade();
            if(player == 1){
                for(int i = 0; i < numberOfCountersSelected; i++){
                    gameBoard[selectionsMade[i][0]][selectionsMade[i][1]].setImageResource(R.drawable.player1counter);
                }
            }
            else if(player == 2){
                for(int i = 0; i < numberOfCountersSelected; i++){
                    gameBoard[selectionsMade[i][0]][selectionsMade[i][1]].setImageResource(R.drawable.player2counter);
                }
            }
            gridSelections = new GridSelectionsObject();
        }

    }


}
