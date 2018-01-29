package com.jcroberts.abalone.game;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class to check if a selection made on the game board is legal whether it be for selecting a counter
 * or an attempt to move counters
 * Author: Joshua Roberts
 */

class SelectionChecker {

    SelectionChecker(){

    }



    /**
     * Check if the counter selected is legal
     * @return Whether or not the counter selected is legal
     */
    boolean counterSelectionIsLegal(int[] gridLocation, GridSelections gridSelections){
        int xCoordinate = GridSelections.Y_COORDINATE;
        int yCoordinate = GridSelections.X_COORDINATE;
        //If this is the first selection
        if(gridSelections.getNumberOfCountersSelected() == 0) {
            return true;
        }
        //If this is the second selection
        else if(gridSelections.getNumberOfCountersSelected() == 1) {
            ArrayList<int[]> selectionsMade = gridSelections.getSelectionsMade();

            if(gridLocation[yCoordinate] == selectionsMade.get(0)[yCoordinate]){
                if(Math.abs(gridLocation[0] - selectionsMade.get(0)[0]) <= 1){
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
                    System.out.println("Horizontal selection");
                    if(selectionsMade.get(0)[yCoordinate] == gridLocation[yCoordinate]){
                        if(Math.abs(selectionsMade.get(0)[xCoordinate] - gridLocation[xCoordinate]) == 1 || Math.abs(gridLocation[xCoordinate] - selectionsMade.get(1)[xCoordinate]) == 1){
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
        System.out.println("Selection was illegal");
        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
            System.out.println("(" + gridSelections.getSelectionsMade().get(i)[xCoordinate] + ", " + gridSelections.getSelectionsMade().get(i)[yCoordinate] + ")");
        }
        System.out.println("Tried to add (" + gridLocation[xCoordinate] + ", " + gridLocation[yCoordinate] + ")");
        return false;
    }

    /**
    * Find out if the selected move is legal
    * @return Whether or not the move is legal
    */
    MovementLogic checkMoveSelectionIsLegal(int[] gridLocation, GridSelections gridSelections, GameBoard gameBoard, boolean isPushing) {
        ArrayList<int[]> selectionsMade = gridSelections.getSelectionsMade();
        ArrayList<GridSelections.Neighbour> neighbours = gridSelections.getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection(gameBoard.getGameBoard());
        int[][] board = gameBoard.getGameBoard();
        int player = board[selectionsMade.get(0)[GridSelections.X_COORDINATE]][selectionsMade.get(0)[GridSelections.Y_COORDINATE]];
        Iterator<GridSelections.Neighbour> iterator = neighbours.iterator();
        GridSelections.Neighbour applicableNeighbour;

        while(iterator.hasNext()){
            GridSelections.Neighbour nextNeighbour = iterator.next();

            if(nextNeighbour.getXCoordinate() == gridLocation[GridSelections.X_COORDINATE] && nextNeighbour.getYCoordinate() == gridLocation[GridSelections.Y_COORDINATE]){
                applicableNeighbour = nextNeighbour;
                return new MovementLogic(player, true, applicableNeighbour.getMovementDirection(), applicableNeighbour.getNumberOfCountersBeingPushed());
            }
        }
        System.out.println("NO APPLICABLE NEIGHBOUR FOUND");
        return new MovementLogic(-1, false, Move.NO_MOVEMENT, -1);
    }


}
