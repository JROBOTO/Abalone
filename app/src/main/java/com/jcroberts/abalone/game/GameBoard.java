package com.jcroberts.abalone.game;

import java.util.ArrayList;

/**
 * Small class containing the game board ensuring that all information gained is legal
 * This also allows easy passing across the network to the other player.
 *
 * Author: Joshua Roberts
 */

public class GameBoard {
    public static final int NUMBER_OF_ROWS = 11;
    public static final int NUMBER_OF_COLUMNS = 11;

    private int[][] gameBoard;

    public static final int[][] TRADITIONAL_SETUP = {
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, 2, 2, 2, 2, 2, -1, -1, -1, -1, -1},
            {-1, 2, 2, 2, 2, 2, 2, -1, -1, -1, -1},
            {-1, 0, 0, 2, 2, 2, 0, 0, -1, -1, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, 0, 0, 1, 1, 1, 0, 0, -1},
            {-1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1},
            {-1, -1, -1, -1, -1, 1, 1, 1, 1, 1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}};

    /**
     * Initialise the game board
     */
    public GameBoard(int[][] setup){
        gameBoard = setup;


    }

    /**
     * @return gameBoard An int[][] giving the full current game state
     */
    public int[][] getGameBoard(){
        return gameBoard;
    }

    public int getValue(int x, int y){
        return gameBoard[x][y];
    }

    public void setValue(int x, int y, int value){
        gameBoard[x][y] = value;
    }

    /**
     * The class to represent each individual location on the game board
     */
    public class Cell{
        public static final int NO_VALUE = -1;
        public static final int EMPTY = 0;
        public static final int PLAYER_1 = 1;
        public static final int PLAYER_2 = 2;

        private int xCoordinate;
        private int yCoordinate;

        private int value;

        private ArrayList<Cell> neighbours;

        public Cell(int x, int y){
            xCoordinate = x;
            yCoordinate = y;

            value = NO_VALUE;

        }

        public void setValue(int value){
            value = value;
        }

        public int getValue(){
            return value;
        }
        public int getXCoordinate(){
            return xCoordinate;
        }
        public int getYCoordinate(){
            return yCoordinate;
        }

    }

    //TODO create a getPossibleMoves(int player) method to return all possible moves for a player

}
