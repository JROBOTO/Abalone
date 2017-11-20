package com.jcroberts.abalone.game;


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
        //If this is the first selection
        if(gridSelections.getNumberOfCountersSelected() == 0) {
            return true;
        }
        //If this is the second selection
        else if(gridSelections.getNumberOfCountersSelected() == 1) {
            return compareSecondSelectionToFirst(gridLocation, gridSelections);
        }
        //If this is the third selection
        else if(gridSelections.getNumberOfCountersSelected() == 2){
            return checkThirdCounterSelection(gridLocation, gridSelections);
        }

        return false;
    }

    /**
     * Check the new selection against the first and set the axis of direction the selections are following
     * @param gridLocation The int[] with the x and y coordinates of the new selection
     * @param gridSelections The GridSelections containing the selection that has already been made
     * @return If the selection is OK
     */
    private boolean compareSecondSelectionToFirst(int[] gridLocation, GridSelections gridSelections){
        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();

        //If the selections are all on the same line
        if(selectionsMade[0].getRow() == gridLocation[0] && (selectionsMade[0].getColumn() == gridLocation[1] - 1 || selectionsMade[0].getColumn() == gridLocation[1] + 1)){
            //If the selections are left or right
            gridSelections.setDirection(GridSelections.LEFT_TO_RIGHT_DIRECTION);
            return true;
        }
        //If the selections are in the top half of the grid
        else if(gridLocation[0] < 4) {
            if((selectionsMade[0].getRow() == gridLocation[0] - 1 && selectionsMade[0].getColumn() == gridLocation[1]) || (selectionsMade[0].getRow() == gridLocation[0] + 1 && selectionsMade[0].getColumn() == gridLocation[1])) {
                //If the selections are in the up right direction
                gridSelections.setDirection(GridSelections.DOWN_TO_LEFT_DIRECTION);
                return true;
            }
            else if((selectionsMade[0].getRow() == gridLocation[0] + 1 && selectionsMade[0].getColumn() == gridLocation[1] + 1) || (selectionsMade[0].getRow() == gridLocation[0] - 1 && selectionsMade[0].getColumn() == gridLocation[1] - 1)){
                //If selection is in the up left direction
                gridSelections.setDirection(GridSelections.DOWN_TO_RIGHT_DIRECTION);
                return true;
            }
        }
        //If the new selection is in the middle of the grid
        else if(gridLocation[0] == 4){
            if((selectionsMade[0].getRow() == gridLocation[0] - 1 && selectionsMade[0].getColumn() == gridLocation[1] - 1) || (selectionsMade[0].getRow() == gridLocation[0] + 1 && selectionsMade[0].getColumn() == gridLocation[1])) {
                //If the selections are in the up left direction
                gridSelections.setDirection(GridSelections.DOWN_TO_RIGHT_DIRECTION);
                return true;
            }
            else if((selectionsMade[0].getRow() == gridLocation[0] + 1 && selectionsMade[0].getColumn() == gridLocation[1] - 1) || (selectionsMade[0].getRow() == gridLocation[0] - 1 && selectionsMade[0].getColumn() == gridLocation[1])){
                //If the selections are in the up right directions
                gridSelections.setDirection(GridSelections.DOWN_TO_LEFT_DIRECTION);
                return true;
            }
        }
        //If the selection is in the lower half of the grid
        else if(gridLocation[0] > 4){
            if((selectionsMade[0].getRow() == gridLocation[0] - 1 && selectionsMade[0].getColumn() == gridLocation[1]) || (selectionsMade[0].getRow() == gridLocation[0] + 1 && selectionsMade[0].getColumn() == gridLocation[1])){
                //If the selections are up left
                gridSelections.setDirection(GridSelections.DOWN_TO_RIGHT_DIRECTION);
                return true;
            }
            else if((selectionsMade[0].getRow() == gridLocation[0] - 1 && selectionsMade[0].getColumn() == gridLocation[1] + 1) || (selectionsMade[0].getRow() == gridLocation[0] + 1 && selectionsMade[0].getColumn() == gridLocation[1] - 1)){
                //If the selection is bottom left or bottom right
                gridSelections.setDirection(GridSelections.DOWN_TO_LEFT_DIRECTION);
                return true;
            }
        }

        return false;
    }

    /**
     * Compare the location of the third selection with the locations and direction of the other two
     * @param gridLocation The x and y coordinates of the new selection
     * @param gridSelections The GridSelections of the current selections
     * @return Whether or not the selection is legal
     */
    private boolean checkThirdCounterSelection(int[] gridLocation, GridSelections gridSelections){
        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();
        int direction = gridSelections.getDirection();

        switch(direction){
            //Compare the selection based on the direction of the previous selections
            case(GridSelections.LEFT_TO_RIGHT_DIRECTION):
                if(gridLocation[0] == selectionsMade[0].getRow()){
                    if(gridLocation[1] == selectionsMade[0].getColumn() - 1 || gridLocation[1] == selectionsMade[1].getColumn() + 1){
                        return true;
                    }
                }
                break;

            case(GridSelections.DOWN_TO_RIGHT_DIRECTION):
                //If the new selection is immediately above the previous selections
                if(gridLocation[0] == selectionsMade[0].getRow() - 1){
                    //If the new selection is above the centre line
                    if(gridLocation[0] < 4){
                        if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
                            return true;
                        }
                    }
                    //If the new selection is on or below the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[0].getColumn()){
                            return true;
                        }
                    }
                }
                //If the new selection is immediately below the other selections
                else{
                    //If the new selection is above or equal to the centre line
                    if(gridLocation[0] <= 4){
                        if(gridLocation[1] == selectionsMade[1].getColumn() + 1){
                            return true;
                        }
                    }
                    //If the new selection is below the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[1].getColumn()){
                            return true;
                        }
                    }
                }

                break;

            case(GridSelections.DOWN_TO_LEFT_DIRECTION):
                //If the new selection is immediately above the previous ones
                if(gridLocation[0] == selectionsMade[0].getRow() - 1){
                    //If the new selection is above the centre line
                    if(gridLocation[0] < 4){
                        if(gridLocation[1] == selectionsMade[0].getColumn()){
                            return true;
                        }
                    }
                    //If the new selection is below or equal to the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[0].getColumn() + 1){
                            return true;
                        }
                    }
                }
                //If the new selection is immediately below the previous ones
                else if(gridLocation[0] == selectionsMade[1].getRow() + 1){
                    //If the new selection is above or equal to the centre line
                    if(gridLocation[0] <= 4){
                        if(gridLocation[1] == selectionsMade[1].getColumn()){
                            return true;
                        }
                    }
                    //If the new selection is below the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[1].getColumn() - 1){
                            return true;
                        }
                    }
                }

                break;
        }

        return false;
    }

    /**
    * Find out if the selected move is legal
    * @return Whether or not the move is legal
    */
    MovementLogic checkMoveSelectionIsLegal(int[] gridLocation, GridSelections gridSelections, GameBoard gameBoard, boolean isPushing){
        int numberOfCountersSelected = gridSelections.getNumberOfCountersSelected();
        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();
        GameBoard.Cell[][] board = gameBoard.getGameBoard();

        int player = selectionsMade[0].getValue();

        //If only one counter has been selected to move
        if(numberOfCountersSelected == 1){
            if(!isPushing) {
                return checkSingleCounterMovementSelection(gridLocation, selectionsMade);
            }
        }
        //If two counters have been selected to move
        else if(numberOfCountersSelected == 2) {
            return checkDoubleCounterMovementSelection(gridLocation, gridSelections, board, isPushing);
        }
        else if(numberOfCountersSelected == 3){
            return checkTripleCounterMovementSelection(gridLocation, gridSelections, board, isPushing);
        }

        return new MovementLogic(player, false, Move.NO_MOVEMENT);
    }

    /**
     * Check that the movement of the selected counter is allowed i.e. is the movement next to the selection
     * @param gridLocation The x and y coordinates on the grid of the new
     * @param selectionsMade The int[][] for the selection that have already been made
     * @return Whether or not the movement selections is within the reach of the counter
     */
    private MovementLogic checkSingleCounterMovementSelection(int[] gridLocation, GameBoard.Cell[] selectionsMade){
        int player = selectionsMade[0].getValue();

        //If the counter to move is in line with the counter
        if (gridLocation[0] == selectionsMade[0].getRow()) {
            if (gridLocation[1] == selectionsMade[0].getColumn() + 1) {
                return new MovementLogic(player, true, Move.MOVE_RIGHT);
            }
            else if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
                return new MovementLogic(player, true, Move.MOVE_LEFT);
            }
        }
        //If the movement selection is above the counter
        else if (gridLocation[0] == selectionsMade[0].getRow() - 1) {
            //If the movement selection is above the middle line
            if (gridLocation[0] < 4) {
                if (gridLocation[1] == selectionsMade[0].getColumn()){
                    return new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                } else if(gridLocation[1] == selectionsMade[0].getColumn() - 1) {
                    return new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                }
            }
            //If movement selection is below the middle line
            else {
                if (gridLocation[1] == selectionsMade[0].getColumn()){
                    return new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                } else if(gridLocation[1] == selectionsMade[0].getColumn() + 1) {
                    return new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                }
            }
        }
        //If the movement selection is below the counter
        else if(gridLocation[0] == selectionsMade[0].getRow() + 1){
            //If the movement selection is above or equal to the middle line
            if(gridLocation[0] <= 4){
                if(gridLocation[1] == selectionsMade[0].getColumn()){
                    return new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
                }
                else if(gridLocation[1] == selectionsMade[0].getColumn() + 1){
                    return new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                }
            }
            //If the movement selection is below the middle line
            else{
                if(gridLocation[1] == selectionsMade[0].getColumn()){
                    return new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                }
                else if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
                    return new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                }
            }
        }
        return new MovementLogic(player, false, Move.NO_MOVEMENT);
    }

    /**
     * Check that the movement of the two selected counters is allowed i.e. is the movement next to the selections
     * @param gridLocation An int[] for the x and y coordinates of the move
     * @param gridSelections The grid selections object holding the grid selections
     * @return Whether or not the movement is within reach of the selections
     */
    private MovementLogic checkDoubleCounterMovementSelection(int[] gridLocation, GridSelections gridSelections, GameBoard.Cell[][] board, boolean isPushing) throws ArrayIndexOutOfBoundsException{
        int direction = gridSelections.getDirection();
        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();
        int player = selectionsMade[0].getValue();
        MovementLogic movementLogic;

        switch (direction) {
            case (GridSelections.LEFT_TO_RIGHT_DIRECTION):
                //If movement is in line
                if (gridLocation[0] == selectionsMade[0].getRow()) {
                    //If the movement is left
                    if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
                        if(isPushing){
                            if(board[gridLocation[0]][gridLocation[1] - 1].getValue() == 0){
                                movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
                                movementLogic.setIsPushing(true);
                                movementLogic.setMovementIsFollowingLine(true);
                                return movementLogic;
                            }
                        }
                        else {
                            movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                    }
                    //If the movement is to the right
                    else if (gridLocation[1] == selectionsMade[1].getColumn() + 1) {
                        if(isPushing){
                            if(board[gridLocation[0]][gridLocation[1] + 1].getValue() == 0){
                                movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
                                movementLogic.setMovementIsFollowingLine(true);
                                movementLogic.setIsPushing(true);
                                return movementLogic;
                            }
                        }
                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
                        movementLogic.setMovementIsFollowingLine(true);
                        return movementLogic;
                    }
                }
                //If the movement is against the line and the movement is up
                else if (gridLocation[0] == selectionsMade[0].getRow() - 1) {
                    if(!isPushing) {

                        //If the movement selection is above the middle line
                        if (gridLocation[0] < 4) {
                            if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn()].getValue() == 0) {
                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                                    movementLogic.setMovementIsFollowingLine(false);
                                    return movementLogic;
                                }
                            } else if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn() - 1].getValue() == 0) {
                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                                    movementLogic.setMovementIsFollowingLine(false);
                                    return movementLogic;
                                }
                            }
                        }
                        //If the movement selection is below or equal to the middle line
                        else {
                            if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn()].getValue() == 0) {
                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                                    movementLogic.setMovementIsFollowingLine(false);
                                    return movementLogic;
                                }
                            } else if (gridLocation[1] == selectionsMade[1].getColumn() + 1) {
                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() + 1].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn() + 1].getValue() == 0) {
                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                                    movementLogic.setMovementIsFollowingLine(false);
                                    return movementLogic;
                                }
                            }
                        }
                    }
                }
                //If the movement is against the line and the movement is down
                else if (gridLocation[0] == selectionsMade[0].getRow() + 1) {
                    //If the movement selection is above or equal the middle line
                    if (gridLocation[0] <= 4) {
                        if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn()].getValue() == 0) {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
                                movementLogic.setMovementIsFollowingLine(false);
                                return movementLogic;
                            }
                        } else if (gridLocation[1] == selectionsMade[1].getColumn() + 1) {
                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn() + 1].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn()].getValue() == 0) {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                                movementLogic.setMovementIsFollowingLine(false);
                                return movementLogic;
                            }
                        }
                    } else {
                        if (gridLocation[1] == selectionsMade[0].getColumn() || gridLocation[1] == selectionsMade[1].getColumn()) {
                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn()].getValue() == 0) {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                                movementLogic.setMovementIsFollowingLine(false);
                                return movementLogic;
                            }
                        } else if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
                            if(board[selectionsMade[0].getRow() + 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow() + 1][selectionsMade[1].getColumn() - 1].getValue() == 0) {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
                                movementLogic.setMovementIsFollowingLine(false);
                                return movementLogic;
                            }
                        }
                    }
                }
                break;

            case (GridSelections.DOWN_TO_LEFT_DIRECTION):
                //If the movement is up
                if (gridLocation[0] == selectionsMade[0].getRow() - 1) {
                    //If the movement selection is above the middle line
                    if (gridLocation[0] < 4) {
                        //If the movement is following the same line
                        if (gridLocation[1] == selectionsMade[0].getColumn()) {
                            if(isPushing){
                                //TODO This among other checks will produce ArrayIndexOutOfBoundsException
                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0){
                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                                    movementLogic.setMovementIsFollowingLine(true);
                                    movementLogic.setIsPushing(true);
                                    return movementLogic;
                                }
                            }else {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                                movementLogic.setMovementIsFollowingLine(true);
                                return movementLogic;
                            }
                        }
                        //If the movement is not following the line
                        else if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
                            if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow()][selectionsMade[1].getColumn() - 1].getValue() == 0) {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                                movementLogic.setMovementIsFollowingLine(false);
                                return movementLogic;
                            }
                        }
                    }
                    //If the movement selection is below or equal to the middle line
                    else {
                        //If the movement is following the same line
                        if (gridLocation[1] == selectionsMade[0].getColumn() + 1) {
                            if(isPushing){
                                if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn() - 1].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn() - 1].getValue() == 0){
                                    movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                                    movementLogic.setMovementIsFollowingLine(true);
                                    movementLogic.setIsPushing(true);
                                    return movementLogic;
                                }
                            } else {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                                movementLogic.setMovementIsFollowingLine(true);
                                return movementLogic;
                            }
                        }
                        //If the movement is not following the same line
                        else if (gridLocation[1] == selectionsMade[0].getColumn()) {
                            if(board[selectionsMade[0].getRow() - 1][selectionsMade[0].getColumn()].getValue() == 0 && board[selectionsMade[1].getRow() - 1][selectionsMade[1].getColumn()].getValue() == 0) {
                                movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                                movementLogic.setMovementIsFollowingLine(false);
                                return movementLogic;
                            }
                        }
                    }
                }
                //TODO Where you got to at 12:30 on 20/11/2017 in the Computer Science Common Room
                //If the movement is down
                else if (gridLocation[0] == selectionsMade[1].getRow() + 1) {
                    //If the movement selection is above or equal to the middle line
                    if(gridLocation[0] <= 4){
                        //If the movement is following the same line
                        if(gridLocation[1] == selectionsMade[1].getColumn()){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1].getColumn() + 1){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    else{
                        //If the movement is following the same line
                        if(gridLocation[1] == selectionsMade[1].getColumn() - 1){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1].getColumn()){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is left or right
                else if(gridLocation[0] == selectionsMade[0].getRow() || gridLocation[0] == selectionsMade[1].getRow()){
                    //If the movement is left
                    if(gridLocation[1] == selectionsMade[0].getColumn() - 1 || gridLocation[1] == selectionsMade[1].getColumn() - 1){
                        movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                    //If the movement is right
                    else if(gridLocation[1] == selectionsMade[0].getColumn() + 1 || gridLocation[1] == selectionsMade[1].getColumn() + 1){
                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                }

                break;

            case(GridSelections.DOWN_TO_RIGHT_DIRECTION):
                //If the movement is up
                if(gridLocation[0] == selectionsMade[0].getRow() - 1){
                    //If the movement selection is above the middle line
                    if(gridLocation[0] < 4){
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[0].getColumn() - 1){

                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[0].getColumn()){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    else{
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[0].getColumn()){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[0].getColumn() + 1){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is down
                else if(gridLocation[0] == selectionsMade[1].getRow() + 1){
                    //If the movement selection is above or equal to the middle line
                    if(gridLocation[0] <= 4){
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[1].getColumn() + 1){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1].getColumn()){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    //If the movement selection is below the middle line
                    else{
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[1].getColumn()){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1].getColumn() - 1){
                            movementLogic = new MovementLogic(player, true, Move.MOVE_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is left or right
                else if(gridLocation[0] == selectionsMade[0].getRow() || gridLocation[0] == selectionsMade[1].getRow()){
                    //If the movement is left
                    if(gridLocation[1] == selectionsMade[0].getColumn() - 1 || gridLocation[1] == selectionsMade[1].getColumn() - 1){
                        movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                    //If the movement is right
                    else if(gridLocation[1] == selectionsMade[0].getColumn() + 1 || gridLocation[1] == selectionsMade[1].getColumn() + 1){
                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                }
                break;
        }

        movementLogic = new MovementLogic(player, false, Move.NO_MOVEMENT);
        return movementLogic;
    }

    /**
     * Check to see if the movement selection follows the rules of traditional Abalone when three
     * counters have been selected
     * @param gridLocation The x and y location on the grid
     * @param gridSelections The current cells that have been selected
     * @return The movement logic instance that allows the game board to be updated
     */
    private MovementLogic checkTripleCounterMovementSelection(int[] gridLocation, GridSelections gridSelections, GameBoard.Cell[][] board, boolean isPushing){
        GameBoard.Cell[] selectionsMade = gridSelections.getSelectionsMade();
        int selectionDirection = gridSelections.getDirection();
        int player = selectionsMade[0].getValue();
        MovementLogic movementLogic;

        switch(selectionDirection){
            case(GridSelections.LEFT_TO_RIGHT_DIRECTION):
                //If movement is in line
                if (gridLocation[0] == selectionsMade[0].getRow()) {
                    if (gridLocation[1] == selectionsMade[0].getColumn() - 1) {
                        if(isPushing){
                            //TODO A new branch of the decision tree to see if there is room to push
                            movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            movementLogic.setIsPushing(true);
                            return movementLogic;
                        } else {
                            movementLogic = new MovementLogic(player, true, Move.MOVE_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                    } else if (gridLocation[1] == selectionsMade[2].getColumn() + 1) {
                        movementLogic = new MovementLogic(player, true, Move.MOVE_RIGHT);
                        movementLogic.setMovementIsFollowingLine(true);
                        return movementLogic;
                    }
                }
                //If the movement is against the selection direction and the movement is up
                else if(gridLocation[0] == selectionsMade[0].getRow() + 1){
                    //If the movement is above the middle line
                    if(gridLocation[0] < 4){
                        //If the movement is up and left
                        if(gridLocation[1] == selectionsMade[0].getColumn() - 1){
                            boolean allClear = true;
                            for(int i = 0; i < 3; i++){
                                if(board[selectionsMade[i].getRow()][selectionsMade[i].getColumn() - 1].getValue() == 0){
                                    allClear = false;
                                }
                            }
                            if(allClear){
                                return new MovementLogic(player, true, Move.MOVE_UP_LEFT);
                            }
                        }
                    }
                }
                break;

        }

        return new MovementLogic(player, false, Move.NO_MOVEMENT);
    }
}
