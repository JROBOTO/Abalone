package com.jcroberts.abalone.game;

/**
 * Small class containing the game board ensuring that all information gained is legal
 * This also allows easy passing across the network to the other player.
 *
 * Author: Joshua Roberts
 */

public class GameBoard {
    private Cell[][] gameBoard;

    private final int NUMBER_OF_ROWS = 9;

    /**
     * Initialise the game board
     */
    public GameBoard(){
        gameBoard = new Cell[NUMBER_OF_ROWS][];

        Cell[] row0 = new Cell[5];
        Cell[] row1 = new Cell[6];
        Cell[] row2 = new Cell[7];
        Cell[] row3 = new Cell[8];
        Cell[] row4 = new Cell[9];
        Cell[] row5 = new Cell[8];
        Cell[] row6 = new Cell[7];
        Cell[] row7 = new Cell[6];
        Cell[] row8 = new Cell[5];

        gameBoard[0] = row0;
        gameBoard[1] = row1;
        gameBoard[2] = row2;
        gameBoard[3] = row3;
        gameBoard[4] = row4;
        gameBoard[5] = row5;
        gameBoard[6] = row6;
        gameBoard[7] = row7;
        gameBoard[8] = row8;

        //Set all the counters to a neutral value of 0
        for(int i = 0; i < NUMBER_OF_ROWS; i++){
            for(int j = 0; j < gameBoard[i].length; j++){
                gameBoard[i][j] = new Cell(i, j);
            }
        }

        //Initialise the game board for player 2s counters
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < gameBoard[i].length; j++){
                gameBoard[i][j].setFilledValue(2);
            }
        }

        for(int i = 0; i < 3; i++){
            gameBoard[2][i + 2].setFilledValue(2);
        }

        //Initialise the game board for player 1s counters
        for(int i = NUMBER_OF_ROWS; i > NUMBER_OF_ROWS - 2; i--){
            for(int j = 0; j < gameBoard[i].length; j++){
                gameBoard[i][j].setFilledValue(1);
            }
        }

        for(int i = 0; i < 3; i++){
            gameBoard[NUMBER_OF_ROWS - 2][i + 2].setFilledValue(1);
        }
    }

    /**
     * @return gameBoard An int[][] giving the full current game state
     */
    public Cell[][] getGameBoard(){
        return gameBoard;
    }

    /**
     * The class to represent each individual location on the game board
     */
    public class Cell{
        private int xCoordinate;
        private int yCoordinate;

        private int filledValue;

        public Cell(int x, int y){
            xCoordinate = x;
            yCoordinate = y;

            filledValue = 0;
        }

        public void setFilledValue(int value){
            filledValue = value;
        }

        public int getValue(){
            return filledValue;
        }
        public int getXCoordinate(){
            return xCoordinate;
        }
        public int getYCoordinate(){
            return yCoordinate;
        }
    }
}
