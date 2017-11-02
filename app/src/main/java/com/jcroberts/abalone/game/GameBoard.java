package com.jcroberts.abalone.game;

/**
 * Small class containing the game board ensuring that all information gained is legal
 * This also allows easy passing across the network to the other player.
 *
 * Author: Joshua Roberts
 */

public class GameBoard {
    private int[][] gameBoard;

    private final int NUMBER_OF_ROWS = 9;

    /**
     * Initialise the game board
     */
    public GameBoard(){
        gameBoard = new int[NUMBER_OF_ROWS][];

        int[] row0 = new int[5];
        int[] row1 = new int[6];
        int[] row2 = new int[7];
        int[] row3 = new int[8];
        int[] row4 = new int[9];
        int[] row5 = new int[8];
        int[] row6 = new int[7];
        int[] row7 = new int[6];
        int[] row8 = new int[5];

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
                gameBoard[i][j] = 0;
            }
        }

        //Initialise the game board for player 2s counters
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < gameBoard[i].length; j++){
                gameBoard[i][j] = 2;
            }
        }

        for(int i = 0; i < 3; i++){
            gameBoard[2][i + 2] = 2;
        }

        //Initialise the game board for player 1s counters
        for(int i = NUMBER_OF_ROWS; i > NUMBER_OF_ROWS - 2; i--){
            for(int j = 0; j < gameBoard[i].length; j++){
                gameBoard[i][j] = 1;
            }
        }

        for(int i = 0; i < 3; i++){
            gameBoard[NUMBER_OF_ROWS - 2][i + 2] = 2;
        }
    }

    /**
     * @return gameBoard
     *      int[][] giving the full current game state
     */
    public int[][] getGameBoard(){
        return gameBoard;
    }
}
