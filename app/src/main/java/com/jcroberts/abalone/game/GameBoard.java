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

    public static final int NO_VALUE = -1;

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

        for(int[] firstSelection : getCounters(player)){
            GridSelections nextSingleSelection = new GridSelections();
            nextSingleSelection.add(firstSelection);
            possibleSelections.add(nextSingleSelection);

            for(GridSelections.Neighbour secondSelection : nextSingleSelection.getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection(this.gameBoard)){
                if(gameBoard[secondSelection.getXCoordinate()][secondSelection.getYCoordinate()] == player) {
                    GridSelections nextDoubleSelection = new GridSelections();
                    nextDoubleSelection.add(firstSelection);
                    nextDoubleSelection.add(secondSelection.getCoordinates());

                    boolean selectionRepeated = false;

                    for (int i = 0; i < possibleSelections.size(); i++) {
                        if (nextDoubleSelection.equals(possibleSelections.get(i))) {
                            selectionRepeated = false;
                        }
                    }

                    if (!selectionRepeated) {
                        possibleSelections.add(nextDoubleSelection);

                        for(GridSelections.Neighbour thirdSelection : nextDoubleSelection.getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection(this.getGameBoard())){
                            if(thirdSelection.getIsInLine() && gameBoard[thirdSelection.getXCoordinate()][thirdSelection.getYCoordinate()] == player){
                                GridSelections nextTripleSelection = new GridSelections();
                                nextTripleSelection.add(firstSelection);
                                nextTripleSelection.add(secondSelection.getCoordinates());
                                nextTripleSelection.add(thirdSelection.getCoordinates());

                                boolean tripleSelectionRepeated = false;

                                for(int i = 0; i < possibleSelections.size(); i++){
                                    if(nextTripleSelection.equals(possibleSelections.get(i))){
                                        tripleSelectionRepeated = true;
                                    }
                                }

                                if(!tripleSelectionRepeated){
                                    possibleSelections.add(nextTripleSelection);
                                }
                            }
                        }
                    }
                }
            }
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
