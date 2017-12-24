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
                    System.out.println("Horiozontal selection");
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
        int numberOfCountersSelected = gridSelections.getNumberOfCountersSelected();
        ArrayList<int[]> selectionsMade = gridSelections.getSelectionsMade();
        ArrayList<GridSelections.Neighbour> neighbours = gridSelections.getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection();
        int[][] board = gameBoard.getGameBoard();

        Iterator<GridSelections.Neighbour> iterator = neighbours.iterator();

        return new MovementLogic(-1, false, Move.NO_MOVEMENT);
    }
//
//        GridSelections.Neighbour applicableNeighbour = null;
//        boolean found = false;
//
//        while(iterator.hasNext() && !found){
//            GridSelections.Neighbour nextCheck = iterator.next();
//            if(nextCheck.isAtLocation(gridLocation)){
//                applicableNeighbour = nextCheck;
//                found = true;
//            }
//        }
//
//        if(applicableNeighbour != null){
//            int movementDirection = applicableNeighbour.getMovementDirection();
//
//            if(numberOfCountersSelected == 1 && !isPushing){
//                return new MovementLogic(player, true, movementDirection);
//            }
//
//            else if(numberOfCountersSelected == 2){
//                if(isPushing && applicableNeighbour.getIsInLine()){
//                    return checkCanPushOneCounter(player, board, applicableNeighbour);
//                }
//                else if(applicableNeighbour.getIsInLine()){
//                    return new MovementLogic(player, true, applicableNeighbour.getMovementDirection());
//                }
//                else{
//                    checkLateralMovement(player, board, gridSelections, applicableNeighbour);
//                }
//            }
//        }
//
//        return new MovementLogic(player, false, Move.NO_MOVEMENT);
//    }
//
//    private MovementLogic checkLateralMovement(int player, GameBoard.Cell[][] board, GridSelections gridSelections, GridSelections.Neighbour selection){
//        MovementLogic newMovementLogic = new MovementLogic(player, false, Move.NO_MOVEMENT);
//        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();
//        boolean movementIsSound = true;
//        switch(gridSelections.getDirection()){
//            case(GridSelections.LEFT_TO_RIGHT_DIRECTION):
//                switch(selection.getMovementDirection()){
//                    case(Move.MOVE_UP_LEFT):
//                        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
//                            if(selectionsMade[i].getRow() <= 4) {
//                                if(selectionsMade[i].getColumn() == 0 || board[selectionsMade[i].getRow() - 1][selectionsMade[i].getColumn() - 1].getValue() != 0) {
//                                    movementIsSound = false;
//                                }
//                            }
//                            else{
//                                if(board[selectionsMade[i].getRow() - 1][selectionsMade[i].getColumn()].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                        }
//
//                        break;
//
//                    case(Move.MOVE_UP_RIGHT):
//                        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
//                            if(selectionsMade[i].getRow() <= 4){
//                                if(selectionsMade[i].getColumn() == selectionsMade[i].getRow() + 4 || board[selectionsMade[i].getRow() - 1][selectionsMade[i].getColumn()].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                            else{
//                                if(board[selectionsMade[i].getRow() - 1][selectionsMade[i].getColumn() + 1].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                        }
//
//                        break;
//
//                    case(Move.MOVE_DOWN_LEFT):
//                        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
//                            if(selectionsMade[i].getRow() < 4){
//                                if(board[selectionsMade[i].getRow() + 1][selectionsMade[i].getColumn()].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                            else{
//                                if(selectionsMade[i].getColumn() == 0 || board[selectionsMade[i].getRow() + 1][selectionsMade[i].getColumn() - 1].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                        }
//
//                        break;
//
//                    case(Move.MOVE_DOWN_RIGHT):
//                        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
//                            if(selectionsMade[i].getRow() < 4){
//                                if(board[selectionsMade[i].getRow() + 1][selectionsMade[i].getColumn() + 1].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                            else{
//                                if(selectionsMade[i].getColumn() == 13 - selectionsMade[i].getRow() || board[selectionsMade[i].getRow() + 1][selectionsMade[i].getColumn() - 1].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                        }
//
//                        break;
//                }
//
//                break;
//
//            case(GridSelections.DOWN_TO_LEFT_DIRECTION):
//                switch(selection.getMovementDirection()){
//                    case(Move.MOVE_DOWN_RIGHT):
//                        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
//                            if(selectionsMade[i].getRow() < 4){
//                                if(board[selectionsMade[i].getRow() + 1][selectionsMade[i].getColumn() + 1].getValue() != 0){
//                                    movementIsSound = false;
//                                }
//                            }
//                            else{
//                                if(selectionsMade[i].getRow() == 8 || board[selectionsMade[i].getRow() + 1][selectionsMade[i].getColumn()].getValue() != 0){
//                                    movementIsSound = false
//                                }
//                            }
//                        }
//
//                        break;
//
//                    case(Move.MOVE_LEFT):
//                        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
//                            if(selectionsMade[i].getRow() < 4){
//
//                            }
//                        }
//                        break;
//                }
//
//                break;
//        }
//        //TODO new idea to record the neighbours of each individual cell and check the direction of each one
//        if(movementIsSound){
//            newMovementLogic = new MovementLogic(player, true, selection.getMovementDirection());
//        }
//        return newMovementLogic;
//    }
//
//    private MovementLogic checkCanPushOneCounter(int player, GameBoard.Cell[][] board, GridSelections.Neighbour selection){
//        MovementLogic newMovementLogic = new MovementLogic(player, false, Move.NO_MOVEMENT);
//        switch(selection.getMovementDirection()){
//            case(Move.MOVE_DOWN_LEFT):
//                if(selection.getRow() < 4){
//                    if(board[selection.getRow() + 1][selection.getColumn()].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                    }
//                }
//                else{
//                    if(selection.getRow() == 8 || selection.getColumn() == 0){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                        newMovementLogic.setIsTaking(true);
//                    }
//                    else if(board[selection.getRow() + 1][selection.getColumn() - 1].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                    }
//                }
//
//                break;
//
//            case(Move.MOVE_DOWN_RIGHT):
//                if(selection.getRow() < 4){
//                    if(board[selection.getRow() + 1][selection.getColumn() + 1].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                    }
//                }
//                else{
//                    if(selection.getRow() == 8 || selection.getColumn() == 13 - selection.getRow()){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                        newMovementLogic.setIsTaking(true);
//                    }
//                    else if(board[selection.getRow() + 1][selection.getColumn()].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                    }
//                }
//
//                break;
//
//            case(Move.MOVE_UP_LEFT):
//                if(selection.getRow() <= 4){
//                    if(selection.getRow() == 0 || selection.getColumn() == 0){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                        newMovementLogic.setIsTaking(true);
//                    }
//                    else if(board[selection.getRow() - 1][selection.getColumn() - 1].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                    }
//                }
//                else{
//                    if(board[selection.getRow() - 1][selection.getColumn()].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                    }
//                }
//
//                break;
//
//            case(Move.MOVE_UP_RIGHT):
//                if(selection.getRow() <= 4){
//                    if(selection.getRow() == 0 || selection.getColumn() == selection.getRow() + 4){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                        newMovementLogic.setIsTaking(true);
//                    }
//                    else if(board[selection.getRow() - 1][selection.getColumn()].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                    }
//                }
//                else{
//                    if(board[selection.getRow() - 1][selection.getColumn() + 1].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                    }
//                }
//
//                break;
//
//            case(Move.MOVE_LEFT):
//                if(selection.getColumn() == 0){
//                    newMovementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                    newMovementLogic.setIsTaking(true);
//                }
//                else if(board[selection.getRow()][selection.getColumn() - 1].getValue() == player){
//                    newMovementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                }
//
//                break;
//
//            case(Move.MOVE_RIGHT):
//                if(selection.getRow() <= 4){
//                    if(selection.getColumn() == selection.getRow() + 4){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                        newMovementLogic.setIsTaking(true);
//                    }
//                    else if(board[selection.getRow()][selection.getColumn() + 1].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                    }
//                }
//                else{
//                    if(selection.getColumn() == 13 - selection.getRow()){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                        newMovementLogic.setIsTaking(true);
//                    }
//                    else if(board[selection.getRow()][selection.getColumn() + 1].getValue() == player){
//                        newMovementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                    }
//                }
//
//                break;
//        }
//        newMovementLogic.setIsPushing(true);
//        return newMovementLogic;
//    }
//
//    /**
//     * Check that the movement of the selected counter is allowed i.e. is the movement next to the selection
//     * @param gridLocation The x and y coordinates on the grid of the new
//     * @param selectionsMade The int[][] for the selection that have already been made
//     * @return Whether or not the movement selections is within the reach of the counter
//     */
//    private MovementLogic checkSingleCounterMovementSelection(int[] gridLocation, GameBoard.Cell[] selectionsMade){
//        int player = selectionsMade[0].getValue();
//
//        //If the counter to move is in line with the counter
//        if (gridLocation[0] == selectionsMade[0].getRow()) {
//            if (gridLocation[1] == selectionsMade[0].getColumn() + 1) {
//                return new MovementLogic(player, true, Move.MOVE_RIGHT);
//            }
//            else if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
//                return new MovementLogic(player, true, Move.MOVE_LEFT);
//            }
//        }
//        //If the movement selection is above the counter
//        else if (gridLocation[0] == selectionsMade[0].getRow() - 1) {
//            //If the movement selection is above the middle line
//            if (gridLocation[0] < 4) {
//                if (gridLocation[1] == selectionsMade[0].getColumn()){
//                    return new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                } else if(gridLocation[1] == selectionsMade[0].getColumn() - 1) {
//                    return new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                }
//            }
//            //If movement selection is below the middle line
//            else {
//                if (gridLocation[1] == selectionsMade[0].getColumn()){
//                    return new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                } else if(gridLocation[1] == selectionsMade[0].getColumn() + 1) {
//                    return new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                }
//            }
//        }
//        //If the movement selection is below the counter
//        else if(gridLocation[0] == selectionsMade[0].getRow() + 1){
//            //If the movement selection is above or equal to the middle line
//            if(gridLocation[0] <= 4){
//                if(gridLocation[1] == selectionsMade[0].getColumn()){
//                    return new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                }
//                else if(gridLocation[1] == selectionsMade[0].getColumn() + 1){
//                    return new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                }
//            }
//            //If the movement selection is below the middle line
//            else{
//                if(gridLocation[1] == selectionsMade[0].getColumn()){
//                    return new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                }
//                else if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
//                    return new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                }
//            }
//        }
//        return new MovementLogic(player, false, Move.NO_MOVEMENT);
//    }
//
//    /**
//     * Check that the movement of the two selected counters is allowed i.e. is the movement next to the selections
//     * @param gridLocation An int[] for the x and y coordinates of the move
//     * @param gridSelections The grid selections object holding the grid selections
//     * @return Whether or not the movement is within reach of the selections
//     */
//    private MovementLogic checkDoubleCounterMovementSelection(int[] gridLocation, GridSelections gridSelections, GameBoard.Cell[][] board, boolean isPushing) throws ArrayIndexOutOfBoundsException{
//        int direction = gridSelections.getDirection();
//        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();
//        int player = selectionsMade[0].getValue();
//        MovementLogic movementLogic;
//
//        switch (direction) {
//            case (GridSelections.LEFT_TO_RIGHT_DIRECTION):
//                //If movement is in line
//                if (gridLocation[0] == selectionsMade[0].getRow()) {
//                    //If the movement is left
//                    if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
//                        if(isPushing){
//                            if(board[gridLocation[0]][gridLocation[1] - 1].getValue() == 0){
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                                movementLogic.setIsPushing(true);
//                                movementLogic.setMovementIsFollowingLine(true);
//                                return movementLogic;
//                            }
//                        }
//                        else {
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                    }
//                    //If the movement is to the right
//                    else if (gridLocation[1] == selectionsMade[1].getColumn() + 1) {
//                        if(isPushing){
//                            if(board[gridLocation[0]][gridLocation[1] + 1].getValue() == 0){
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                                movementLogic.setMovementIsFollowingLine(true);
//                                movementLogic.setIsPushing(true);
//                                return movementLogic;
//                            }
//                        }
//                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                        movementLogic.setMovementIsFollowingLine(true);
//                        return movementLogic;
//                    }
//                }
//                //If the movement is against the line and the movement is up
//                else if (gridLocation[0] == selectionsMade[0].getRow() - 1) {
//                    if(!isPushing) {
//
//                        //If the movement selection is above the middle line
//                        if (gridLocation[0] < 4) {
//                            if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
//                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn()].getValue() == 0) {
//                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                                    movementLogic.setMovementIsFollowingLine(false);
//                                    return movementLogic;
//                                }
//                            } else if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
//                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn() - 1].getValue() == 0) {
//                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                                    movementLogic.setMovementIsFollowingLine(false);
//                                    return movementLogic;
//                                }
//                            }
//                        }
//                        //If the movement selection is below or equal to the middle line
//                        else {
//                            if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
//                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn()].getValue() == 0) {
//                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                                    movementLogic.setMovementIsFollowingLine(false);
//                                    return movementLogic;
//                                }
//                            } else if (gridLocation[1] == selectionsMade[1].getColumn() + 1) {
//                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() + 1].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn() + 1].getValue() == 0) {
//                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                                    movementLogic.setMovementIsFollowingLine(false);
//                                    return movementLogic;
//                                }
//                            }
//                        }
//                    }
//                }
//                //If the movement is against the line and the movement is down
//                else if (gridLocation[0] == selectionsMade[0].getRow() + 1) {
//                    //If the movement selection is above or equal the middle line
//                    if (gridLocation[0] <= 4) {
//                        if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
//                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn()].getValue() == 0) {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                                movementLogic.setMovementIsFollowingLine(false);
//                                return movementLogic;
//                            }
//                        } else if (gridLocation[1] == selectionsMade[1].getColumn() + 1) {
//                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn() + 1].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn()].getValue() == 0) {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                                movementLogic.setMovementIsFollowingLine(false);
//                                return movementLogic;
//                            }
//                        }
//                    } else {
//                        if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
//                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn()].getValue() == 0) {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                                movementLogic.setMovementIsFollowingLine(false);
//                                return movementLogic;
//                            }
//                        } else if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
//                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn() - 1].getValue() == 0) {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                                movementLogic.setMovementIsFollowingLine(false);
//                                return movementLogic;
//                            }
//                        }
//                    }
//                }
//                break;
//
//            case (GridSelections.DOWN_TO_LEFT_DIRECTION):
//                //If the movement is up
//                if (gridLocation[0] == selectionsMade[0].getRow() - 1) {
//                    //If the movement selection is above the middle line
//                    if (gridLocation[0] < 4) {
//                        //If the movement is following the same line
//                        if (gridLocation[1] == selectionsMade[0].getColumn()) {
//                            if(isPushing){
//                                //TODO This among other checks will produce ArrayIndexOutOfBoundsException
//                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0){
//                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                                    movementLogic.setMovementIsFollowingLine(true);
//                                    movementLogic.setIsPushing(true);
//                                    return movementLogic;
//                                }
//                            }else {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                                movementLogic.setMovementIsFollowingLine(true);
//                                return movementLogic;
//                            }
//                        }
//                        //If the movement is not following the line
//                        else if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
//                            if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow()][selectionsMade[1].getColumn() - 1].getValue() == 0) {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                                movementLogic.setMovementIsFollowingLine(false);
//                                return movementLogic;
//                            }
//                        }
//                    }
//                    //If the movement selection is below or equal to the middle line
//                    else {
//                        //If the movement is following the same line
//                        if (gridLocation[1] == selectionsMade[0].getColumn() + 1) {
//                            if(isPushing){
//                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn() - 1].getValue() == 0){
//                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                                    movementLogic.setMovementIsFollowingLine(true);
//                                    movementLogic.setIsPushing(true);
//                                    return movementLogic;
//                                }
//                            } else {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                                movementLogic.setMovementIsFollowingLine(true);
//                                return movementLogic;
//                            }
//                        }
//                        //If the movement is not following the same line
//                        else if (gridLocation[1] == selectionsMade[0].getColumn()) {
//                            if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn()].getValue() == 0) {
//                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                                movementLogic.setMovementIsFollowingLine(false);
//                                return movementLogic;
//                            }
//                        }
//                    }
//                }
//                //TODO Where you got to at 12:30 on 20/11/2017 in the Computer Science Common Room
//                //If the movement is down
//                else if (gridLocation[0] == selectionsMade[1].getRow() + 1) {
//                    //If the movement selection is above or equal to the middle line
//                    if(gridLocation[0] <= 4){
//                        //If the movement is following the same line
//                        if(gridLocation[1] == selectionsMade[1].getColumn()){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                        //If the movement is not following the line
//                        else if(gridLocation[1] == selectionsMade[1].getColumn() + 1){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                            movementLogic.setMovementIsFollowingLine(false);
//                            return movementLogic;
//                        }
//                    }
//                    else{
//                        //If the movement is following the same line
//                        if(gridLocation[1] == selectionsMade[1].getColumn() - 1){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                        //If the movement is not following the line
//                        else if(gridLocation[1] == selectionsMade[1].getColumn()){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                            movementLogic.setMovementIsFollowingLine(false);
//                            return movementLogic;
//                        }
//                    }
//                }
//                //If the movement is left or right
//                else if(gridLocation[0] == selectionsMade[0].getRow() || gridLocation[0] == selectionsMade[1].getRow()){
//                    //If the movement is left
//                    if(gridLocation[1] == selectionsMade[0].getColumn() - 1 || gridLocation[1] == selectionsMade[1].getColumn() - 1){
//                        movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                        movementLogic.setMovementIsFollowingLine(false);
//                        return movementLogic;
//                    }
//                    //If the movement is right
//                    else if(gridLocation[1] == selectionsMade[0].getColumn() + 1 || gridLocation[1] == selectionsMade[1].getColumn() + 1){
//                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                        movementLogic.setMovementIsFollowingLine(false);
//                        return movementLogic;
//                    }
//                }
//
//                break;
//
//            case(GridSelections.DOWN_TO_RIGHT_DIRECTION):
//                //If the movement is up
//                if(gridLocation[0] == selectionsMade[0].getRow() - 1){
//                    //If the movement selection is above the middle line
//                    if(gridLocation[0] < 4){
//                        //If the movement is following the line
//                        if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
//
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                        //If the movement is not following the line
//                        else if(gridLocation[1] == selectionsMade[0].getColumn()){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                            movementLogic.setMovementIsFollowingLine(false);
//                            return movementLogic;
//                        }
//                    }
//                    else{
//                        //If the movement is following the line
//                        if(gridLocation[1] == selectionsMade[0].getColumn()){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                        //If the movement is not following the line
//                        else if(gridLocation[1] == selectionsMade[0].getColumn() + 1){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
//                            movementLogic.setMovementIsFollowingLine(false);
//                            return movementLogic;
//                        }
//                    }
//                }
//                //If the movement is down
//                else if(gridLocation[0] == selectionsMade[1].getRow() + 1){
//                    //If the movement selection is above or equal to the middle line
//                    if(gridLocation[0] <= 4){
//                        //If the movement is following the line
//                        if(gridLocation[1] == selectionsMade[1].getColumn() + 1){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                        //If the movement is not following the line
//                        else if(gridLocation[1] == selectionsMade[1].getColumn()){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                            movementLogic.setMovementIsFollowingLine(false);
//                            return movementLogic;
//                        }
//                    }
//                    //If the movement selection is below the middle line
//                    else{
//                        //If the movement is following the line
//                        if(gridLocation[1] == selectionsMade[1].getColumn()){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                        //If the movement is not following the line
//                        else if(gridLocation[1] == selectionsMade[1].getColumn() - 1){
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
//                            movementLogic.setMovementIsFollowingLine(false);
//                            return movementLogic;
//                        }
//                    }
//                }
//                //If the movement is left or right
//                else if(gridLocation[0] == selectionsMade[0].getRow() || gridLocation[0] == selectionsMade[1].getRow()){
//                    //If the movement is left
//                    if(gridLocation[1] == selectionsMade[0].getColumn() - 1 || gridLocation[1] == selectionsMade[1].getColumn() - 1){
//                        movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                        movementLogic.setMovementIsFollowingLine(false);
//                        return movementLogic;
//                    }
//                    //If the movement is right
//                    else if(gridLocation[1] == selectionsMade[0].getColumn() + 1 || gridLocation[1] == selectionsMade[1].getColumn() + 1){
//                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                        movementLogic.setMovementIsFollowingLine(false);
//                        return movementLogic;
//                    }
//                }
//                break;
//        }
//
//        movementLogic = new MovementLogic(player, false, Move.NO_MOVEMENT);
//        return movementLogic;
//    }
//
//    /**
//     * Check to see if the movement selection follows the rules of traditional Abalone when three
//     * counters have been selected
//     * @param gridLocation The x and y location on the grid
//     * @param gridSelections The current cells that have been selected
//     * @return The movement logic instance that allows the game board to be updated
//     */
//    private MovementLogic checkTripleCounterMovementSelection(int[] gridLocation, GridSelections gridSelections, GameBoard.Cell[][] board, boolean isPushing){
//        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();
//        int selectionDirection = gridSelections.getDirection();
//        int player = selectionsMade[0].getValue();
//        MovementLogic movementLogic;
//
//        switch(selectionDirection){
//            case(GridSelections.LEFT_TO_RIGHT_DIRECTION):
//                //If movement is in line
//                if (gridLocation[0] == selectionsMade[0].getRow()) {
//                    if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
//                        if(isPushing){
//                            //TODO A new branch of the decision tree to see if there is room to push
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            movementLogic.setIsPushing(true);
//                            return movementLogic;
//                        } else {
//                            movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
//                            movementLogic.setMovementIsFollowingLine(true);
//                            return movementLogic;
//                        }
//                    } else if (gridLocation[1] == selectionsMade[2].getColumn() + 1) {
//                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
//                        movementLogic.setMovementIsFollowingLine(true);
//                        return movementLogic;
//                    }
//                }
//                //If the movement is against the selection direction and the movement is up
//                else if(gridLocation[0] == selectionsMade[0].getRow() + 1){
//                    //If the movement is above the middle line
//                    if(gridLocation[0] < 4){
//                        //If the movement is up and left
//                        if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
//                            boolean allClear = true;
//                            for(int i = 0; i < 3; i++){
//                                if(board[selectionsMade[i].getRow()][selectionsMade[i].getColumn() - 1].getValue() == 0){
//                                    allClear = false;
//                                }
//                            }
//                            if(allClear){
//                                return new MovementLogic(player, true, Move.MOVE_UP_LEFT);
//                            }
//                        }
//                    }
//                }
//                break;
//
//        }
//
//        return new MovementLogic(player, false, Move.NO_MOVEMENT);
//    }

}
