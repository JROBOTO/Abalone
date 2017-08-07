package com.jcroberts.abalone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

/**
 * The main game activity which builds the game board and allows turns to be taken to play the
 * game. It runs a terminal test after each turn whether it is a human player or not.
 */

//TODO Restrict selection to 3 counters and all in a line
//TODO Create counter movement
public class GameActivity extends AppCompatActivity {
    protected ImageView[][] gameBoard;
    private ImageView[] row0;
    private ImageView[] row1;
    private ImageView[] row2;
    private ImageView[] row3;
    private ImageView[] row4;
    private ImageView[] row5;
    private ImageView[] row6;
    private ImageView[] row7;
    private ImageView[] row8;

    private int playerToTakeTurn;
    private int numberOfCountersSelected;
    private int numberOfPlayer1CountersTaken;
    private int numberOfPlayer2CountersTaken;

    private boolean legalMoveSelected;
    private boolean gameEnded;
    /**
     * Main creation method for the game to create the game board and run the main game loop
     * @param savedInstanceState The saved instance from the last activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("ACTIVITY OPENED");

        setupGameBoard();

        //Initialize game logic
        gameEnded = false;
        numberOfPlayer1CountersTaken = 0;
        numberOfPlayer2CountersTaken = 0;

        //randomize who the first player is
        Random rand = new Random();

        int playerToTakeFirstTurn = rand.nextInt(1) + 1;
        int playerToTakeSecondTurn;
        if(playerToTakeFirstTurn == 1){
            playerToTakeSecondTurn = 2;
        }
        else{
            playerToTakeSecondTurn = 1;
        }

        Toast.makeText(this, "Player " + Integer.toString(playerToTakeFirstTurn) + " to go first", Toast.LENGTH_LONG).show();
        //TODO The game loop is broken, fix this shit
        //Run main game loop
        //TODO while(!gameEnded){
            runTurn(playerToTakeFirstTurn);
            runTerminalTest();

            runTurn(playerToTakeSecondTurn);
            runTerminalTest();
        //TODO }

    }

    /**
     * Set up the game board
     */
    private void setupGameBoard(){
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
                    GridLocationDragListener gldl = new GridLocationDragListener(gl);
                    gameBoard[i][j].setOnDragListener(gldl);
                }
            }
            else{
                for(int j = 0; j < 13 - i; j++){
                    int[] gl = new int[2];
                    gl[0] = i;
                    gl[1] = j;
                    GridLocationDragListener gldl = new GridLocationDragListener(gl);
                    gameBoard[i][j].setOnDragListener(gldl);
                }
            }
        }
    }

    /**
     * Run turn for individual player
     * @param pTTT Player to take turn: 1 or 2
     */
    private void runTurn(int pTTT){
        playerToTakeTurn = pTTT;
        legalMoveSelected = false;

        while(!legalMoveSelected){

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

    /**
     * Drag listener for the spaces on the game board. Changes the state of counters if selectable
     * and selected
     */
    private class GridLocationDragListener implements View.OnDragListener{
        private int[] gridLocation;

        private GridLocationDragListener(int[] gL){
            gridLocation = gL;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            ImageView gridSelection = (ImageView)v;

            if(playerToTakeTurn == 1){
                //If it is player 1's turn
                if(gridSelection.getDrawable().toString().equals(Integer.toString(R.drawable.player1counter))){
                    //If this is the first counter selected
                    if(numberOfCountersSelected == 0) {
                        gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player1counterselected);
                        numberOfCountersSelected++;
                    }
                    //If this is the second counter selected
                    else if(numberOfCountersSelected == 1){
                        //If the first counter was to the left or right
                        if(gameBoard[gridLocation[0]][gridLocation[1] - 1].getDrawable().equals(R.drawable.player1counterselected)) {
                            gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player1counterselected);
                            numberOfCountersSelected++;
                        }
                        else if(gameBoard[gridLocation[0]][gridLocation[1] - 1].getDrawable().equals(R.drawable.player1counterselected)){
                            gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player1counterselected);
                            numberOfCountersSelected++;
                        }

                        //If the first counter was above or below

                    }
                }
            }
            else{
                if(gridSelection.getDrawable().toString().equals(Integer.toString(R.drawable.player2counter)) && numberOfCountersSelected < 3){
                    gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player2counterselected);
                    numberOfCountersSelected++;
                }
            }

            return false;
        }
    }

    /**
     * Click listener for when some counters have been selected to move and now a location needs to be selected to move to.
     */
    private class GridLocationClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            ImageView location = (ImageView)v;

            if(numberOfCountersSelected > 0){
                if(location.getDrawable().equals(R.drawable.neutralcounter)){
                    legalMoveSelected = true;
                }
            }
        }
    }
}
