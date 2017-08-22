package com.jcroberts.abalone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private GridSelectionsObject gridSelections;

    private int playerToTakeTurn;
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
        /* while(!gameEnded){
            runTurn(playerToTakeFirstTurn);
            runTerminalTest();

            runTurn(playerToTakeSecondTurn);
            runTerminalTest();
        }*/

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
                    if(counterSelectionIsLegal()){
                        gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player1counterselected);
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }
                else if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.neutralcounter).getConstantState())){
                    if(checkMoveIsLegal()){
                        //TODO

                        runTerminalTest();
                        if(!gameEnded) {
                            playerToTakeTurn = 2;
                        }
                    }
                    gridSelections = new GridSelectionsObject();
                }
                else{
                    resetPlayerSelections(1);
                }
            }
            else{
                if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.player2counter).getConstantState())){
                    if(counterSelectionIsLegal()){
                        gameBoard[gridLocation[0]][gridLocation[1]].setImageResource(R.drawable.player2counterselected);
                    }
                    else{
                        resetPlayerSelections(2);
                    }
                }
                else if(gameBoard[gridLocation[0]][gridLocation[1]].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.neutralcounter).getConstantState())){
                    if(checkMoveIsLegal()) {
                        //TODO make a move

                        runTerminalTest();
                        if(!gameEnded) {
                            playerToTakeTurn = 1;
                        }
                    }
                    gridSelections = new GridSelectionsObject();
                }
                else{
                    resetPlayerSelections(2);
                }
            }
        }

        /**
         * Check if the counter selected is legal
         * @return Whether or not the counter selected is legal
         */
        private boolean counterSelectionIsLegal(){
            //If this is the first selection
            if(gridSelections.numberOfCountersSelected == 0) {
                return true;
            }
            //If this is the second selection
            else if(gridSelections.numberOfCountersSelected == 1) {
                if(gridLocation[0] < 4) {
                    if(gridSelections.selectionsMade[0][0] == gridLocation[0] - 1 && (gridSelections.selectionsMade[0][1] == gridLocation[1] - 1 || gridSelections.selectionsMade[0][1] == gridLocation[1])) {
                        //If the selection is top left or top right
                        return true;
                    }
                    else if(gridSelections.selectionsMade[0][0] == gridLocation[0] && (gridSelections.selectionsMade[0][1] == gridLocation[1] - 1 || gridSelections.selectionsMade[0][1] == gridLocation[1] + 1)){
                        //If the selection is left or right
                        return true;
                    }
                    else if(gridSelections.selectionsMade[0][0] == gridLocation[0] + 1 && (gridSelections.selectionsMade[0][1] == gridLocation[1] || gridSelections.selectionsMade[0][1] == gridLocation[1] + 1)){
                        //If selection is bottom left or bottom right
                        return true;
                    }
                }
                else if(gridLocation[0] == 4){
                    if(gridSelections.selectionsMade[0][0] == gridLocation[0] - 1 && (gridSelections.selectionsMade[0][1] == gridLocation[1] - 1 || gridSelections.selectionsMade[0][1] == gridLocation[1])) {
                        //If the selection is top left or top right
                        return true;
                    }
                    else if(gridSelections.selectionsMade[0][0] == gridLocation[0] && (gridSelections.selectionsMade[0][1] == gridLocation[1] - 1 || gridSelections.selectionsMade[0][1] == gridLocation[1] + 1)){
                        //If the selection is left or right
                        return true;
                    }
                    else if(gridSelections.selectionsMade[0][0] == gridLocation[0] + 1 && (gridSelections.selectionsMade[0][1] == gridLocation[1] - 1 || gridSelections.selectionsMade[0][1] == gridLocation[1])){
                        //If the selection is bottom left or bottom right
                        return true;
                    }
                }
                else if(gridLocation[0] > 4){
                    if(gridSelections.selectionsMade[0][0] == gridLocation[0] - 1 && (gridSelections.selectionsMade[0][1] == gridLocation[1] || gridSelections.selectionsMade[0][1] == gridLocation[1] + 1)){
                        //If the selection is top left or top right
                        return true;
                    }
                    else if(gridSelections.selectionsMade[0][0] == gridLocation[0] && (gridSelections.selectionsMade[0][1] == gridLocation[1] - 1 || gridSelections.selectionsMade[0][1] == gridLocation[1] + 1)){
                        //If the selection is left or right
                        return true;
                    }
                    else if(gridSelections.selectionsMade[0][0] == gridLocation[0] + 1 && (gridSelections.selectionsMade[0][1] == gridLocation[1] - 1 || gridSelections.selectionsMade[0][1] == gridLocation[1])){
                        //If the selection is bottom left or bottom right
                        return true;
                    }
                }
            }
            //If this is the third selection
            else if(gridSelections.numberOfCountersSelected == 2){
                //If the selection is to the left or right
                if(gridSelections.selectionsMade[0][0] == gridSelections.selectionsMade[1][0]){
                    if(gridLocation[0] == gridSelections.selectionsMade[0][0]){
                        if(gridLocation[1] == gridSelections.selectionsMade[0][1] - 1){
                            return true;
                        }
                        else if(gridLocation[1] == gridSelections.selectionsMade[0][1] + 1){
                            return true;
                        }
                        else if(gridLocation[1] == gridSelections.selectionsMade[1][1] - 1){
                            return true;
                        }
                        else if(gridLocation[1] == gridSelections.selectionsMade[1][1] + 1){
                            return true;
                        }
                    }
                }
                //If the first selection is below the second
                else if(gridSelections.selectionsMade[0][0] == gridSelections.selectionsMade[1][0] + 1){
                    //If the new selection is below the first
                    if(gridLocation[0] == gridSelections.selectionsMade[0][0] + 1){
                        //If all selections are above or equal to the middle row
                        if(gridLocation[0] <= 4 && gridSelections.selectionsMade[0][0] <= 4 && gridSelections.selectionsMade[1][0] <= 4){
                            //If the selections are all in line
                            if(gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] && gridSelections.selectionsMade[0][1] == gridLocation[1]){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[0][1] + 1 && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] + 1){
                                return true;
                            }
                        }
                        //If all selections are below or equal to the middle row
                        else if(gridLocation[0] >= 4 && gridSelections.selectionsMade[0][0] >= 4 && gridSelections.selectionsMade[1][0] == gridLocation[0]){
                            //If the selections are all in line
                            if(gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] && gridSelections.selectionsMade[0][1] == gridLocation[1]){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[0][1] - 1 && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] - 1){
                                return true;
                            }
                        }
                        //If the selections are both above and below the middle row
                        else if(gridLocation[0] == 5 && gridSelections.selectionsMade[0][0] == 4 && gridSelections.selectionsMade[1][0] == 3){
                            //If the selections are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[0][1] && gridSelections.selectionsMade[1][1] == gridSelections.selectionsMade[0][1] - 1){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[0][1] - 1 && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                        }
                    }
                    //If the new selection is above the second
                    else if(gridLocation[0] == gridSelections.selectionsMade[1][0] - 1){
                        //If the selections are all above the middle row
                        if(gridLocation[0] <= 4 && gridSelections.selectionsMade[0][0] <= 4 && gridSelections.selectionsMade[1][0] <= 4){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[1][1] && gridLocation[1] == gridSelections.selectionsMade[0][1]){
                                return true;
                            }
                            else if(gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] + 1 && gridSelections.selectionsMade[1][1] == gridLocation[1] + 1){
                                return true;
                            }
                        }
                        //If the selections are all below the middle row
                        else if(gridLocation[0] >= 4 && gridSelections.selectionsMade[0][0] >= 4 && gridSelections.selectionsMade[1][0] >= 4){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[1][1] && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                            else if(gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] - 1 && gridSelections.selectionsMade[1][1] == gridLocation[1] - 1){
                                return true;
                            }
                        }
                        //If the selections are both above and below the middle row
                        else if(gridLocation[0] == 3 && gridSelections.selectionsMade[1][0] == 4 && gridSelections.selectionsMade[0][0] == 5){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[1][1] && gridSelections.selectionsMade[1][1] == gridSelections.selectionsMade[0][1] + 1){
                                return true;
                            }
                            else if(gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] && gridSelections.selectionsMade[1][1] == gridLocation[1] + 1){
                                return true;
                            }
                        }
                    }
                }
                //If the second selections is below the first
                else if(gridSelections.selectionsMade[0][0] == gridSelections.selectionsMade[1][0] + 1){
                    //If the new selection is above the first
                    if(gridLocation[0] == gridSelections.selectionsMade[0][0] - 1){
                        //If all selections are above or equal to the middle row
                        if(gridLocation[0] <= 4 && gridSelections.selectionsMade[0][0] <= 4 && gridSelections.selectionsMade[1][0] <= 4){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[0][1] && gridLocation[1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[0][1] - 1 && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] - 1){
                                return true;
                            }
                        }
                        //If all selections are below or equal to the middle row
                        else if(gridLocation[0] >= 4 && gridSelections.selectionsMade[0][0] >= 4 && gridSelections.selectionsMade[1][0] >= 4){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[0][1] && gridLocation[1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[0][1] + 1 && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] + 1){
                                return true;
                            }
                        }
                        //If the selections are both above and below the middle row
                        else if(gridLocation[0] == 3 && gridSelections.selectionsMade[0][0] == 4 && gridSelections.selectionsMade[1][0] == 5){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[0][1] && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] + 1){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[0][1] - 1 && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                        }
                    }
                    //If the new selection is below the second
                    else if(gridLocation[0] == gridSelections.selectionsMade[1][0] + 1){
                        //If the selections are all above or in line with the middle row
                        if(gridLocation[0] <= 4 && gridSelections.selectionsMade[0][0] <= 4 && gridSelections.selectionsMade[1][0] <= 4){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[0][1] && gridLocation[1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                            else if(gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] - 1 && gridSelections.selectionsMade[1][1] == gridLocation[1] - 1){
                                return true;
                            }
                        }
                        //If all selections are below or in line with the middle row
                        else if(gridLocation[0] >= 4 && gridSelections.selectionsMade[0][0] >= 4 && gridSelections.selectionsMade[1][0] >= 4){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[0][1] && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[1][1] - 1 && gridSelections.selectionsMade[1][1] == gridSelections.selectionsMade[0][1] - 1){
                                return true;
                            }
                        }
                        //If the selections are both above and below the middle row
                        else if(gridLocation[0] == 5 && gridSelections.selectionsMade[1][0] == 4 && gridSelections.selectionsMade[0][0] == 3){
                            //If they are all in line
                            if(gridLocation[1] == gridSelections.selectionsMade[1][1] && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1] - 1){
                                return true;
                            }
                            else if(gridLocation[1] == gridSelections.selectionsMade[1][1] - 1 && gridSelections.selectionsMade[0][1] == gridSelections.selectionsMade[1][1]){
                                return true;
                            }
                        }
                    }
                }

            }

            return false;
        }

        /**
         * Cancel the players current selections
         * @param player Which player is currently taking a turn
         */
        private void resetPlayerSelections(int player){
            if(player == 1){
                for(int i = 0; i < gridSelections.numberOfCountersSelected; i++){
                    gameBoard[gridSelections.selectionsMade[i][0]][gridSelections.selectionsMade[i][1]].setImageResource(R.drawable.player1counter);
                }
            }
            else if(player == 2){
                for(int i = 0; i < gridSelections.numberOfCountersSelected; i++){
                    gameBoard[gridSelections.selectionsMade[i][0]][gridSelections.selectionsMade[i][1]].setImageResource(R.drawable.player2counter);
                }
            }
            gridSelections = new GridSelectionsObject();
        }

        /**
         * Find out if the selected move is legal
         * @return Whether or not the move is legal
         */
        private boolean checkMoveIsLegal(){
            //TODO
            return false;
        }

        /**
         * Test if the game has ended
         */
        private void runTerminalTest(){
            if(numberOfPlayer1CountersTaken >= 6 || numberOfPlayer2CountersTaken >= 6){
                gameEnded = true;
            }
        }

    }

    /**
     * Private class to track what has been selected
     */
    private class GridSelectionsObject{
        private int numberOfCountersSelected;
        private int[][] selectionsMade;

        private GridSelectionsObject(){
            numberOfCountersSelected = 0;
        }

        /**
         * Add a new selection to the list
         * @param x The x value on the grid (left to right)
         * @param y The y value on the grid (top to bottom)
         */
        private void add(int x, int y){
            selectionsMade[numberOfCountersSelected] = new int[]{x, y};
            numberOfCountersSelected++;
        }

        /**
         * Remove a selection from the list
         * @param x The x value on the grid (left to right)
         * @param y The y value on the grid (top to bottom)
         */
        private void remove(int x, int y){
            for(int i = 0; i < numberOfCountersSelected; i++){
                if(selectionsMade[i][0] == x && selectionsMade[i][1] == y){
                    selectionsMade[i] = selectionsMade[i+1];
                    selectionsMade[i+1] = selectionsMade[i+2];
                    selectionsMade[i+2] = null;
                    break;
                }
            }
            numberOfCountersSelected--;
        }
    }
}
