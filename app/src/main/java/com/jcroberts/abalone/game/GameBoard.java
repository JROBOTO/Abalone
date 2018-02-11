package com.jcroberts.abalone.game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Small class containing the game board ensuring that all information gained is legal
 * This also allows easy passing across the network to the other player.
 *
 * Author: Joshua Roberts
 */

public class GameBoard implements Serializable{
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

    public void makeMove(int[][] newGameBoard){
        gameBoard = newGameBoard;
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

    public ArrayList<Move> getPossibleMoves(int player){
        ArrayList<Move> possibleMoves = new ArrayList<>();

        for(GridSelections selections : getAllPossibleSelections(player)){
            for(GridSelections.Neighbour neighbour : selections.getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection(gameBoard)){
                if(player == 2){
                    if(neighbour.getIsInLine()){
                        possibleMoves.add(new Move(new GameBoard(gameBoard), selections, new MovementLogic(player, true, neighbour.getMovementDirection(), neighbour.getNumberOfCountersBeingPushed())));
                    }
                }
                else{
                    possibleMoves.add(new Move(new GameBoard(gameBoard), selections, new MovementLogic(player, true, neighbour.getMovementDirection(), neighbour.getNumberOfCountersBeingPushed())));
                }
            }
        }

        return possibleMoves;
    }

    private ArrayList<GridSelections> getAllPossibleSelections(int player){
        ArrayList<GridSelections> possibleSelections = new ArrayList<>();

        for(int[] counter : getCounters(player)){
            //TODO return all possible selections
        }

        return possibleSelections;
    }

    private ArrayList<int[]> getCounters(int player){
        ArrayList<int[]> counters = new ArrayList<>();

        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[i].length; j++){
                if(gameBoard[i][j] == player){
                    counters.add(new int[]{i, j});
                }
            }
        }

        return counters;
    }
}
