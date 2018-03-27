package com.jcroberts.abalone.game;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class to check if a selection made on the game board is legal whether it be for selecting a counter
 * or an attempt to move counters
 * Author: Joshua Roberts
 */

public class SelectionChecker implements Serializable{

    public SelectionChecker(){

    }

    /**
     * Check if the counter selected is legal
     * @return Whether or not the counter selected is legal
     */
    public boolean counterSelectionIsLegal(int[] gridLocation, GridSelections gridSelections){
        int xCoordinate = GridSelections.X_COORDINATE;
        int yCoordinate = GridSelections.Y_COORDINATE;
        //If this is the first selection
        if(gridSelections.getNumberOfCountersSelected() == 0) {
            return true;
        }
        //If this is the second selection
        else if(gridSelections.getNumberOfCountersSelected() == 1) {
            ArrayList<int[]> selectionsMade = gridSelections.getSelectionsMade();

            if(gridLocation[yCoordinate] == selectionsMade.get(0)[yCoordinate]){
                if(Math.abs(gridLocation[xCoordinate] - selectionsMade.get(0)[xCoordinate]) == 1){
                    return true;
                }
            }
            else if(Math.abs(gridLocation[yCoordinate] - selectionsMade.get(0)[yCoordinate]) == 1){
                if(gridLocation[xCoordinate] == selectionsMade.get(0)[xCoordinate]){
                    return true;
                }
                else if(gridLocation[yCoordinate] == selectionsMade.get(0)[yCoordinate] + 1){
                    if(gridLocation[xCoordinate] == selectionsMade.get(0)[xCoordinate] + 1){
                        return true;
                    }
                }
                else if(gridLocation[yCoordinate] == selectionsMade.get(0)[yCoordinate] - 1){
                    if(gridLocation[xCoordinate] == selectionsMade.get(0)[xCoordinate] - 1){
                        return true;
                    }
                }
            }
        }
        //If this is the third selection
        else if(gridSelections.getNumberOfCountersSelected() == 2){
            ArrayList<int[]> selectionsMade = gridSelections.getSelectionsMade();
            switch (gridSelections.getDirection()){
                case(GridSelections.LEFT_TO_RIGHT_DIRECTION):
                    if(selectionsMade.get(0)[yCoordinate] == gridLocation[yCoordinate]){
                        if(selectionsMade.get(1)[xCoordinate] - gridLocation[xCoordinate] == -1 || gridLocation[xCoordinate] - selectionsMade.get(0)[xCoordinate] == -1){
                            return true;
                        }
                    }

                    break;

                case(GridSelections.DOWN_TO_RIGHT_DIRECTION):
                    if(selectionsMade.get(0)[yCoordinate] - gridLocation[yCoordinate] == 1){
                        if(gridLocation[xCoordinate] - selectionsMade.get(0)[xCoordinate] == -1){
                            return true;
                        }
                    }
                    else if(gridLocation[yCoordinate] - selectionsMade.get(1)[yCoordinate] == 1){
                        if(selectionsMade.get(1)[xCoordinate] - gridLocation[xCoordinate] == -1){
                            return true;
                        }
                    }

                    break;

                case(GridSelections.DOWN_TO_LEFT_DIRECTION):
                    if(selectionsMade.get(0)[yCoordinate] - gridLocation[yCoordinate] == 1){
                        if(selectionsMade.get(0)[xCoordinate] == gridLocation[xCoordinate]){
                            return true;
                        }
                    }
                    else if(selectionsMade.get(1)[yCoordinate] - gridLocation[yCoordinate] == -1){
                        if(selectionsMade.get(1)[xCoordinate] == gridLocation[xCoordinate]){
                            return true;
                        }
                    }

                    break;
            }
        }
        return false;
    }

    /**
    * Find out if the selected move is legal
    * @return Whether or not the move is legal
    */
    public MovementLogic checkMoveSelectionIsLegal(int[] gridLocation, GridSelections gridSelections, GameBoard gameBoard) {
        ArrayList<int[]> selectionsMade = gridSelections.getSelectionsMade();
        ArrayList<GridSelections.Neighbour> neighbours = gridSelections.getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection(gameBoard.getGameBoard());
        int[][] board = gameBoard.getGameBoard();
        int player = board[selectionsMade.get(0)[GridSelections.Y_COORDINATE]][selectionsMade.get(0)[GridSelections.X_COORDINATE]];
        Iterator<GridSelections.Neighbour> iterator = neighbours.iterator();
        GridSelections.Neighbour applicableNeighbour;

        while(iterator.hasNext()){
            GridSelections.Neighbour nextNeighbour = iterator.next();

            if(nextNeighbour.getYCoordinate() == gridLocation[GridSelections.Y_COORDINATE] && nextNeighbour.getXCoordinate() == gridLocation[GridSelections.X_COORDINATE]){
                applicableNeighbour = nextNeighbour;
                return new MovementLogic(player, true, applicableNeighbour.getMovementDirection(), applicableNeighbour.getNumberOfCountersBeingPushed());
            }
        }
        System.out.println("NO APPLICABLE NEIGHBOUR FOUND");
        return new MovementLogic(-1, false, Move.NO_MOVEMENT, -1);
    }


}
