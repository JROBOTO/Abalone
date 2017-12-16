package com.jcroberts.abalone.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcroberts.abalone.R;
import com.jcroberts.abalone.game.Game;
import com.jcroberts.abalone.game.GameBoard;

import java.util.ArrayList;
import java.util.Random;

/**
 * The main game activity which builds the game board and allows turns to be taken to play the
 * game. It runs a terminal test after each turn whether it is a human player or not.
 *
 * Author: Joshua Roberts
 */

//TODO Create counter movement
public class GameActivity extends AppCompatActivity {
    protected ImageView[][] gameBoardView;

    protected Drawable player1CounterDrawable;
    protected Drawable player1CounterSelectedDrawable;
    protected Drawable player2CounterDrawable;
    protected Drawable player2CounterSelectedDrawable;
    protected Drawable neutralSpaceDrawable;

    private Game game;

    protected boolean waitingForOtherPlayerToTakeTurn;

    /**
     * Main creation method for the game to create the game board and run the main game loop
     * @param savedInstanceState The saved instance from the last activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Opening GameActivity.java");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        player1CounterDrawable = getResources().getDrawable(R.drawable.grid_space_red);
        player1CounterSelectedDrawable = getResources().getDrawable(R.drawable.grid_space_red_selected);

        player2CounterDrawable = getResources().getDrawable(R.drawable.grid_space_blue);
        player2CounterSelectedDrawable = getResources().getDrawable(R.drawable.grid_space_blue_selected);

        neutralSpaceDrawable = getResources().getDrawable(R.drawable.grid_space_grey);
        System.out.println("Setting up back end");
        game = new Game();

        System.out.println("ACTIVITY OPENED");

        setupGameBoard();

        Toast.makeText(this, "Player " + Integer.toString(game.getPlayerToTakeFirstTurn()) + " to go first", Toast.LENGTH_LONG).show();
        playUserTurn();
    }

    /**
     * Set up the game board in terms of initializing each space in turn
     */
    private void setupGameBoard(){
        ImageView[] column0;
        ImageView[] column1;
        ImageView[] column2;
        ImageView[] column3;
        ImageView[] column4;
        ImageView[] column5;
        ImageView[] column6;
        ImageView[] column7;
        ImageView[] column8;

        column0 = new ImageView[11];

        column0[1] = (ImageView)findViewById(R.id.row0Column0);
        column0[2] = (ImageView)findViewById(R.id.row0Column1);
        column0[3] = (ImageView)findViewById(R.id.row0Column2);
        column0[4] = (ImageView)findViewById(R.id.row0Column3);
        column0[5] = (ImageView)findViewById(R.id.row0Column4);

        column1 = new ImageView[11];

        column1[1] = (ImageView)findViewById(R.id.row1Column0);
        column1[2] = (ImageView)findViewById(R.id.row1Column1);
        column1[3] = (ImageView)findViewById(R.id.row1Column2);
        column1[4] = (ImageView)findViewById(R.id.row1Column3);
        column1[5] = (ImageView)findViewById(R.id.row1Column4);
        column1[6] = (ImageView)findViewById(R.id.row1Column5);

        column2 = new ImageView[11];

        column2[1] = (ImageView)findViewById(R.id.row2Column0);
        column2[2] = (ImageView)findViewById(R.id.row2Column1);
        column2[3] = (ImageView)findViewById(R.id.row2Column2);
        column2[4] = (ImageView)findViewById(R.id.row2Column3);
        column2[5] = (ImageView)findViewById(R.id.row2Column4);
        column2[6] = (ImageView)findViewById(R.id.row2Column5);
        column2[7] = (ImageView)findViewById(R.id.row2Column6);

        column3 = new ImageView[11];

        column3[1] = (ImageView)findViewById(R.id.row3Column0);
        column3[2] = (ImageView)findViewById(R.id.row3Column1);
        column3[3] = (ImageView)findViewById(R.id.row3Column2);
        column3[4] = (ImageView)findViewById(R.id.row3Column3);
        column3[5] = (ImageView)findViewById(R.id.row3Column4);
        column3[6] = (ImageView)findViewById(R.id.row3Column5);
        column3[7] = (ImageView)findViewById(R.id.row3Column6);
        column3[8] = (ImageView)findViewById(R.id.row3Column7);

        column4 = new ImageView[11];

        column4[1] = (ImageView)findViewById(R.id.row4Column0);
        column4[2] = (ImageView)findViewById(R.id.row4Column1);
        column4[3] = (ImageView)findViewById(R.id.row4Column2);
        column4[4] = (ImageView)findViewById(R.id.row4Column3);
        column4[5] = (ImageView)findViewById(R.id.row4Column4);
        column4[6] = (ImageView)findViewById(R.id.row4Column5);
        column4[7] = (ImageView)findViewById(R.id.row4Column6);
        column4[8] = (ImageView)findViewById(R.id.row4Column7);
        column4[9] = (ImageView)findViewById(R.id.row4Column8);

        column5 = new ImageView[11];

        column5[2] = (ImageView)findViewById(R.id.row5Column0);
        column5[3] = (ImageView)findViewById(R.id.row5Column1);
        column5[4] = (ImageView)findViewById(R.id.row5Column2);
        column5[5] = (ImageView)findViewById(R.id.row5Column3);
        column5[6] = (ImageView)findViewById(R.id.row5Column4);
        column5[7] = (ImageView)findViewById(R.id.row5Column5);
        column5[8] = (ImageView)findViewById(R.id.row5Column6);
        column5[9] = (ImageView)findViewById(R.id.row5Column7);

        column6 = new ImageView[11];

        column6[3] = (ImageView)findViewById(R.id.row6Column0);
        column6[4] = (ImageView)findViewById(R.id.row6Column1);
        column6[5] = (ImageView)findViewById(R.id.row6Column2);
        column6[6] = (ImageView)findViewById(R.id.row6Column3);
        column6[7] = (ImageView)findViewById(R.id.row6Column4);
        column6[8] = (ImageView)findViewById(R.id.row6Column5);
        column6[9] = (ImageView)findViewById(R.id.row6Column6);

        column7 = new ImageView[11];

        column7[4] = (ImageView)findViewById(R.id.row7Column0);
        column7[5] = (ImageView)findViewById(R.id.row7Column1);
        column7[6] = (ImageView)findViewById(R.id.row7Column2);
        column7[7] = (ImageView)findViewById(R.id.row7Column3);
        column7[8] = (ImageView)findViewById(R.id.row7Column4);
        column7[9] = (ImageView)findViewById(R.id.row7Column5);

        column8 = new ImageView[11];

        column8[5] = (ImageView)findViewById(R.id.row8Column0);
        column8[6] = (ImageView)findViewById(R.id.row8Column1);
        column8[7] = (ImageView)findViewById(R.id.row8Column2);
        column8[8] = (ImageView)findViewById(R.id.row8Column3);
        column8[9] = (ImageView)findViewById(R.id.row8Column4);

        gameBoardView = new ImageView[11][11];

        gameBoardView[1] = column0;
        gameBoardView[2] = column1;
        gameBoardView[3] = column2;
        gameBoardView[4] = column3;
        gameBoardView[5] = column4;
        gameBoardView[6] = column5;
        gameBoardView[7] = column6;
        gameBoardView[8] = column7;
        gameBoardView[9] = column8;

        updateGameBoard();
    }
    //TODO The game board currently records as yx instead of xy I think

    /**
     * Update the game board on the screen based on the current state of the game in the Game class
     */
    private void updateGameBoard(){
        int[][] gameBoard = game.getGameBoard().getGameBoard();
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                try {
                    if (gameBoard[i][j] == 0) {
                        gameBoardView[i][j].setImageDrawable(neutralSpaceDrawable);
                    } else if (gameBoard[i][j] == 1) {
                        gameBoardView[i][j].setImageDrawable(player1CounterDrawable);
                    } else if (gameBoard[i][j] == 2) {
                        gameBoardView[i][j].setImageDrawable(player2CounterDrawable);
                    }
                }
                catch(NullPointerException npe){}
                catch(ArrayIndexOutOfBoundsException aioobe){}
            }
        }
    }

    public ImageView[][] getFullGameBoard(){
        return gameBoardView;
    }

    protected void playUserTurn(){
        for(int row = 0; row < 11; row++){
            for(int column = 0; column < 11; column++){
                GridLocationClickListener glcl = new GridLocationClickListener(new int[]{column, row}, this.getApplicationContext());
                try {
                    gameBoardView[column][row].setOnClickListener(glcl);
                } catch(NullPointerException npe){}
            }
        }
    }

    protected void waitForOtherPlayerToTakeTurn(){
        waitingForOtherPlayerToTakeTurn = true;

        WaitingRunnable waitingRunnable = new WaitingRunnable();
        Thread waitingThread = new Thread(waitingRunnable);
        waitingThread.start();
        while(waitingForOtherPlayerToTakeTurn);
        waitingThread.stop();
    }

    private class WaitingRunnable implements Runnable{

        @Override
        public void run(){
            AlertDialog.Builder waitingDialog = new AlertDialog.Builder(getApplicationContext());
            waitingDialog.setMessage("Waiting...");

            waitingDialog.create();
        }
    }

    /**
     * Click listener for when some counters have been selected to move and now a location needs to be selected to move to.
     */
    private class GridLocationClickListener implements View.OnClickListener{
        private int[] gridLocation;
        private Context context;

        private GridLocationClickListener(int[] gl, Context c){
            gridLocation = gl;
            context = c;
        }

        @Override
        public void onClick(View v) {
            ImageView location = (ImageView)v;

            //TODO if the counter selected is on the side of the player then add it to the list of selections. If it is a neutral counter in line, move. Else cancel selections
            if(game.getCurrentPlayer() == 1){
                if(location.getDrawable().getConstantState().equals(player1CounterDrawable.getConstantState())){
                    if(game.counterSelectionIsLegal(gridLocation)){
                        gameBoardView[gridLocation[0]][gridLocation[1]].setImageDrawable(player1CounterSelectedDrawable);
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }
                else if(location.getDrawable().getConstantState().equals(neutralSpaceDrawable.getConstantState())){
                    if(game.isMovementLegal(gridLocation, false)){
                        //TODO move
                        Toast.makeText(getApplicationContext(), "Selection is fine", Toast.LENGTH_LONG).show();
                        updateGameBoard();
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }
                else if(location.getDrawable().getConstantState().equals(player2CounterDrawable.getConstantState())){
                    if(game.isMovementLegal(gridLocation, true)){
                        //TODO
                        Toast.makeText(getApplicationContext(), "Selection is fine", Toast.LENGTH_LONG).show();
                        updateGameBoard();
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }

            }
            else{
                if(location.getDrawable().getConstantState().equals(player2CounterDrawable.getConstantState())){
                    if(game.counterSelectionIsLegal(gridLocation)){
                        gameBoardView[gridLocation[0]][gridLocation[1]].setImageDrawable(player2CounterSelectedDrawable);
                    }
                    else{
                        resetPlayerSelections(2);
                    }
                }
                else if(location.getDrawable().getConstantState().equals(neutralSpaceDrawable.getConstantState())){
                    if(game.isMovementLegal(gridLocation, false)) {
                        //TODO make a move
                        updateGameBoard();
                    }
                    else{
                        resetPlayerSelections(2);
                    }

                }
                else if(location.getDrawable().getConstantState().equals(player1CounterDrawable.getConstantState())){
                    if(game.isMovementLegal(gridLocation, true)){
                        //TODO
                        updateGameBoard();
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
            ArrayList<int[]> selectionsMade = game.getGridSelections().getSelectionsMade();
            if(player == 1){
                for(int i = 0; i < selectionsMade.size(); i++){
                    gameBoardView[selectionsMade.get(i)[0]][selectionsMade.get(i)[1]].setImageDrawable(player1CounterDrawable);
                }
            }
            else if(player == 2){
                for(int i = 0; i < selectionsMade.size(); i++){
                    gameBoardView[selectionsMade.get(i)[0]][selectionsMade.get(i)[1]].setImageDrawable(player2CounterDrawable);
                }
            }
            game.resetPlayerSelections();
        }

    }


}
