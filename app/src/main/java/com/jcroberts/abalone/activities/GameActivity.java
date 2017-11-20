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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        player1CounterDrawable = getResources().getDrawable(R.drawable.grid_space_red);
        player1CounterDrawable = getResources().getDrawable(R.drawable.grid_space_red_selected);

        player2CounterDrawable = getResources().getDrawable(R.drawable.grid_space_blue);
        player2CounterDrawable = getResources().getDrawable(R.drawable.grid_space_blue_selected);

        neutralSpaceDrawable = getResources().getDrawable(R.drawable.grid_space_grey);

        game = new Game();

        System.out.println("ACTIVITY OPENED");

        setupGameBoard();

        Toast.makeText(this, "Player " + Integer.toString(game.getPlayerToTakeFirstTurn()) + " to go first", Toast.LENGTH_LONG).show();

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

        gameBoardView = new ImageView[9][9];

        gameBoardView[0] = row0;
        gameBoardView[1] = row1;
        gameBoardView[2] = row2;
        gameBoardView[3] = row3;
        gameBoardView[4] = row4;
        gameBoardView[5] = row5;
        gameBoardView[6] = row6;
        gameBoardView[7] = row7;
        gameBoardView[8] = row8;


    }

    /**
     * Update the game board on the screen based on the current state of the game in the Game class
     */
    private void updateGameBoard(){
        GameBoard.Cell[][] gameBoard = game.getGameBoard().getGameBoard();
        for(int i = 0; i < 9; i++){
            if(i <= 4) {
                for (int j = 0; j < i + 5; j++) {
                    if(gameBoard[i][j].getValue() == 0){
                        gameBoardView[i][j].setImageDrawable(neutralSpaceDrawable);
                    }
                    else if(gameBoard[i][j].getValue() == 1){
                        gameBoardView[i][j].setImageDrawable(player1CounterDrawable);
                    }
                    else if(gameBoard[i][j].getValue() == 2){
                        gameBoardView[i][j].setImageDrawable(player2CounterDrawable);
                    }
                }
            }
            else{
                for(int j = 0; j < 13 - i; j++){
                    if(gameBoard[i][j].getValue() == 0){
                        gameBoardView[i][j].setImageDrawable(neutralSpaceDrawable);
                    }
                    else if(gameBoard[i][j].getValue() == 1){
                        gameBoardView[i][j].setImageDrawable(player1CounterDrawable);
                    }
                    else if(gameBoard[i][j].getValue() == 2){
                        gameBoardView[i][j].setImageDrawable(player2CounterDrawable);
                    }
                }
            }
        }
    }

    public ImageView[][] getFullGameBoard(){
        return gameBoardView;
    }

    protected void playUserTurn(){
        for(int i = 0; i < 9; i++){
            if(i <= 4){
                for(int j = 0; j < i + 5; j++){
                    int[] gl = new int[2];
                    gl[0] = i;
                    gl[1] = j;
                    GridLocationClickListener glcl = new GridLocationClickListener(gl, this.getApplicationContext());
                    gameBoardView[i][j].setOnClickListener(glcl);
                }
            }
            else{
                for(int j = 0; j < 13 - i; j++){
                    int[] gl = new int[2];
                    gl[0] = i;
                    gl[1] = j;
                    GridLocationClickListener glcl = new GridLocationClickListener(gl, this.getApplicationContext());
                    gameBoardView[i][j].setOnClickListener(glcl);
                }
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
            int[][] selectionsMade = game.getGridSelections();
            if(player == 1){
                for(int i = 0; i < selectionsMade.length; i++){
                    gameBoardView[selectionsMade[i][0]][selectionsMade[i][1]].setImageDrawable(player1CounterDrawable);
                }
            }
            else if(player == 2){
                for(int i = 0; i < selectionsMade.length; i++){
                    gameBoardView[selectionsMade[i][0]][selectionsMade[i][1]].setImageDrawable(player1CounterDrawable);
                }
            }
            game.resetPlayerSelections();
        }

    }


}
