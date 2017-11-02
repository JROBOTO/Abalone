package com.jcroberts.abalone.game;


import android.app.Activity;

import com.jcroberts.abalone.game.GridSelectionsObject;
import com.jcroberts.abalone.game.MovementLogic;

/**
 * Class to check if a selection made on the game board is legal whether it be for selecting a counter
 * or an attempt to move counters
 * Author: Joshua Roberts
 */

class SelectionChecker {

    private final int LEFT_TO_RIGHT_DIRECTION = 0;
    private final int DOWN_TO_RIGHT_DIRECTION = 1;
    private final int DOWN_TO_LEFT_DIRECTION = 2;

    private final int NO_MOVEMENT = -1;
    private final int MOVEMENT_LEFT = 0;
    private final int MOVEMENT_RIGHT = 1;
    private final int MOVEMENT_UP_LEFT = 2;
    private final int MOVEMENT_UP_RIGHT = 3;
    private final int MOVEMENT_DOWN_LEFT = 4;
    private final int MOVEMENT_DOWN_RIGHT = 5;

    private Activity activity;

    SelectionChecker(Activity a){
        activity = a;
    }

    /**
     * Check if the counter selected is legal
     * @return Whether or not the counter selected is legal
     */
    boolean counterSelectionIsLegal(int[] gridLocation, GridSelectionsObject gridSelections){
        int[][] selectionsMade = gridSelections.getSelectionsMade();

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
     * @param gridSelections The GridSelectionsObject containing the selection that has already been made
     * @return If the selection is OK
     */
    private boolean compareSecondSelectionToFirst(int[] gridLocation, GridSelectionsObject gridSelections){
        int[][] selectionsMade = gridSelections.getSelectionsMade();

        //If the selections are all on the same line
        if(selectionsMade[0][0] == gridLocation[0] && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1] + 1)){
            //If the selections are left or right
            gridSelections.setDirection(LEFT_TO_RIGHT_DIRECTION);
            return true;
        }
        //If the selections are in the top half of the grid
        else if(gridLocation[0] < 4) {
            if((selectionsMade[0][0] == gridLocation[0] - 1 && selectionsMade[0][1] == gridLocation[1]) || (selectionsMade[0][0] == gridLocation[0] + 1 && selectionsMade[0][1] == gridLocation[1])) {
                //If the selections are in the up right direction
                gridSelections.setDirection(DOWN_TO_LEFT_DIRECTION);
                return true;
            }
            else if((selectionsMade[0][0] == gridLocation[0] + 1 && selectionsMade[0][1] == gridLocation[1] + 1) || (selectionsMade[0][0] == gridLocation[0] - 1 && selectionsMade[0][1] == gridLocation[1] - 1)){
                //If selection is in the up left direction
                gridSelections.setDirection(DOWN_TO_RIGHT_DIRECTION);
                return true;
            }
        }
        //If the new selection is in the middle of the grid
        else if(gridLocation[0] == 4){
            if((selectionsMade[0][0] == gridLocation[0] - 1 && selectionsMade[0][1] == gridLocation[1] - 1) || (selectionsMade[0][0] == gridLocation[0] + 1 && selectionsMade[0][1] == gridLocation[1])) {
                //If the selections are in the up left direction
                gridSelections.setDirection(DOWN_TO_RIGHT_DIRECTION);
                return true;
            }
            else if((selectionsMade[0][0] == gridLocation[0] + 1 && selectionsMade[0][1] == gridLocation[1] - 1) || (selectionsMade[0][0] == gridLocation[0] - 1 && selectionsMade[0][1] == gridLocation[1])){
                //If the selections are in the up right directions
                gridSelections.setDirection(DOWN_TO_LEFT_DIRECTION);
                return true;
            }
        }
        //If the selection is in the lower half of the grid
        else if(gridLocation[0] > 4){
            if((selectionsMade[0][0] == gridLocation[0] - 1 && selectionsMade[0][1] == gridLocation[1]) || (selectionsMade[0][0] == gridLocation[0] + 1 && selectionsMade[0][1] == gridLocation[1])){
                //If the selections are up left
                gridSelections.setDirection(DOWN_TO_RIGHT_DIRECTION);
                return true;
            }
            else if((selectionsMade[0][0] == gridLocation[0] - 1 && selectionsMade[0][1] == gridLocation[1] + 1) || (selectionsMade[0][0] == gridLocation[0] + 1 && selectionsMade[0][1] == gridLocation[1] - 1)){
                //If the selection is bottom left or bottom right
                gridSelections.setDirection(DOWN_TO_LEFT_DIRECTION);
                return true;
            }
        }

        return false;
    }

    /**
     * Compare the location of the third selection with the locations and direction of the other two
     * @param gridLocation The x and y coordinates of the new selection
     * @param gridSelections The GridSelectionsObject of the current selections
     * @return Whether or not the selection is legal
     */
    private boolean checkThirdCounterSelection(int[] gridLocation, GridSelectionsObject gridSelections){
        int[][] selectionsMade = gridSelections.getSelectionsMade();
        int direction = gridSelections.getDirection();

        switch(direction){
            //Compare the selection based on the direction of the previous selections
            case(LEFT_TO_RIGHT_DIRECTION):
                if(gridLocation[0] == selectionsMade[0][0]){
                    if(gridLocation[1] == selectionsMade[0][1] - 1 || gridLocation[1] == selectionsMade[1][1] + 1){
                        return true;
                    }
                }
                break;

            case(DOWN_TO_RIGHT_DIRECTION):
                //If the new selection is immediately above the previous selections
                if(gridLocation[0] == selectionsMade[0][0] - 1){
                    //If the new selection is above the centre line
                    if(gridLocation[0] < 4){
                        if(gridLocation[1] == selectionsMade[0][1] - 1){
                            return true;
                        }
                    }
                    //If the new selection is on or below the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[0][1]){
                            return true;
                        }
                    }
                }
                //If the new selection is immediately below the other selections
                else{
                    //If the new selection is above or equal to the centre line
                    if(gridLocation[0] <= 4){
                        if(gridLocation[1] == selectionsMade[1][1] + 1){
                            return true;
                        }
                    }
                    //If the new selection is below the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[1][1]){
                            return true;
                        }
                    }
                }

                break;

            case(DOWN_TO_LEFT_DIRECTION):
                //If the new selection is immediately above the previous ones
                if(gridLocation[0] == selectionsMade[0][0] - 1){
                    //If the new selection is above the centre line
                    if(gridLocation[0] < 4){
                        if(gridLocation[1] == selectionsMade[0][1]){
                            return true;
                        }
                    }
                    //If the new selection is below or equal to the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[0][1] + 1){
                            return true;
                        }
                    }
                }
                //If the new selection is immediately below the previous ones
                else if(gridLocation[0] == selectionsMade[1][0] + 1){
                    //If the new selection is above or equal to the centre line
                    if(gridLocation[0] <= 4){
                        if(gridLocation[1] == selectionsMade[1][1]){
                            return true;
                        }
                    }
                    //If the new selection is below the centre line
                    else{
                        if(gridLocation[1] == selectionsMade[1][1] - 1){
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
    MovementLogic checkMoveSelectionIsLegal(int[] gridLocation, GridSelectionsObject gridSelections, boolean isPushing){
        int numberOfCountersSelected = gridSelections.getNumberOfCountersSelected();
        int[][] selectionsMade = gridSelections.getSelectionsMade();

        //If only one counter has been selected to move
        if(numberOfCountersSelected == 1){
            if(!isPushing) {
                return checkSingleCounterMovementSelection(gridLocation, selectionsMade);
            }
        }
        //If two counters have been selected to move
        else if(numberOfCountersSelected == 2) {
            return checkDoubleCounterMovementSelection(gridLocation, gridSelections);
        }
        else if(numberOfCountersSelected == 3){
            //TODO return checkTripleCounterMovementSelection(gridLocation, selectionsMade);
        }

        return new MovementLogic(false, NO_MOVEMENT);
    }

    /**
     * Check that the movement of the selected counter is allowed i.e. is the movement next to the selection
     * @param gridLocation The x and y coordinates on the grid of the new
     * @param selectionsMade The int[][] for the selection that have already been made
     * @return Whether or not the movement selections is within the reach of the counter
     */
    private MovementLogic checkSingleCounterMovementSelection(int[] gridLocation, int[][] selectionsMade){
        MovementLogic movementLogic;

        //If the counter to move is in line with the counter
        if (gridLocation[0] == selectionsMade[0][0]) {
            if (gridLocation[1] == selectionsMade[0][1] + 1) {
                movementLogic = new MovementLogic(true, MOVEMENT_RIGHT);
                return movementLogic;
            }
            else if(gridLocation[1] == selectionsMade[0][1] - 1){
                movementLogic = new MovementLogic(true, MOVEMENT_LEFT);
                return movementLogic;
            }
        }
        //If the movement selection is above the counter
        else if (gridLocation[0] == selectionsMade[0][0] - 1) {
            //If the movement selection is above the middle line
            if (gridLocation[0] < 4) {
                if (gridLocation[1] == selectionsMade[0][1]){
                    movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                    return movementLogic;
                } else if(gridLocation[1] == selectionsMade[0][1] - 1) {
                    movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                    return movementLogic;
                }
            }
            //If movement selection is below the middle line
            else {
                if (gridLocation[1] == selectionsMade[0][1]){
                    movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                    return movementLogic;
                } else if(gridLocation[1] == selectionsMade[0][1] + 1) {
                    movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                    return movementLogic;
                }
            }
        }
        //If the movement selection is below the counter
        else if(gridLocation[0] == selectionsMade[0][0] + 1){
            //If the movement selection is above or equal to the middle line
            if(gridLocation[0] <= 4){
                if(gridLocation[1] == selectionsMade[0][1]){
                    movementLogic = new MovementLogic(true, MOVEMENT_DOWN_LEFT);
                    return movementLogic;
                }
                else if(gridLocation[1] == selectionsMade[0][1] + 1){
                    movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                    return movementLogic;
                }
            }
            //If the movement selection is below the middle line
            else{
                if(gridLocation[1] == selectionsMade[0][1]){
                    movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                    return movementLogic;
                }
                else if(gridLocation[1] == selectionsMade[0][1] - 1){
                    movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                }
            }
        }
        return new MovementLogic(false, NO_MOVEMENT);
    }

    /**
     * Check that the movement of the two selected counters is allowed i.e. is the movement next to the selections
     * @param gridLocation An int[] for the x and y coordinates of the move
     * @param gridSelections The grid selections object holding the grid selections
     * @return Whether or not the movement is within reach of the selections
     */
    private MovementLogic checkDoubleCounterMovementSelection(int[] gridLocation, GridSelectionsObject gridSelections) {
        int direction = gridSelections.getDirection();
        int[][] selectionsMade = gridSelections.getSelectionsMade();

        MovementLogic movementLogic;

        switch (direction) {
            case (LEFT_TO_RIGHT_DIRECTION):
                //If movement is in line
                if (gridLocation[0] == selectionsMade[0][0]) {
                    if (gridLocation[1] == selectionsMade[0][1] - 1) {
                        movementLogic = new MovementLogic(true, MOVEMENT_LEFT);
                        movementLogic.setMovementIsFollowingLine(true);
                        return movementLogic;
                    } else if (gridLocation[1] == selectionsMade[1][1] + 1) {
                        movementLogic = new MovementLogic(true, MOVEMENT_RIGHT);
                        movementLogic.setMovementIsFollowingLine(true);
                        return movementLogic;
                    }
                }
                //If the movement is against the line and the movement is up
                else if (gridLocation[0] == selectionsMade[0][0] - 1) {
                    //If the movement selection is above the middle line
                    if (gridLocation[0] < 4) {
                        if (gridLocation[1] == selectionsMade[0][1] || gridLocation[1] == selectionsMade[1][1]) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        } else if (gridLocation[1] == selectionsMade[0][1] - 1) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    //If the movement selection is below or equal to the middle line
                    else {
                        if (gridLocation[1] == selectionsMade[0][1] || gridLocation[1] == selectionsMade[1][1]) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        } else if (gridLocation[1] == selectionsMade[1][1] + 1) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is against the line and the movement is down
                else if (gridLocation[0] == selectionsMade[0][0] + 1) {
                    //If the movement selection is above or equal the middle line
                    if (gridLocation[0] <= 4) {
                        if (gridLocation[1] == selectionsMade[0][1] || gridLocation[1] == selectionsMade[1][1]) {
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        } else if (gridLocation[1] == selectionsMade[1][1] + 1) {
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    } else {
                        if (gridLocation[1] == selectionsMade[0][1] || gridLocation[1] == selectionsMade[1][1]) {
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        } else if (gridLocation[1] == selectionsMade[0][1] - 1) {
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                break;

            case (DOWN_TO_LEFT_DIRECTION):
                //If the movement is up
                if (gridLocation[0] == selectionsMade[0][0] - 1) {
                    //If the movement selection is above the middle line
                    if (gridLocation[0] < 4) {
                        //If the movement is following the same line
                        if (gridLocation[1] == selectionsMade[0][1]) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if (gridLocation[1] == selectionsMade[0][1] - 1) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    //If the movement selection is below or equal to the middle line
                    else {
                        //If the movement is following the same line
                        if (gridLocation[1] == selectionsMade[0][1] + 1) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the same line
                        else if (gridLocation[1] == selectionsMade[0][1]) {
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                            movementLogic.setIsPushing(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is down
                else if (gridLocation[0] == selectionsMade[1][0] + 1) {
                    //If the movement selection is above or equal to the middle line
                    if(gridLocation[0] <= 4){
                        //If the movement is following the same line
                        if(gridLocation[1] == selectionsMade[1][1]){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1][1] + 1){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    else{
                        //If the movement is following the same line
                        if(gridLocation[1] == selectionsMade[1][1] - 1){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1][1]){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is left or right
                else if(gridLocation[0] == selectionsMade[0][0] || gridLocation[0] == selectionsMade[1][0]){
                    //If the movement is left
                    if(gridLocation[1] == selectionsMade[0][1] - 1 || gridLocation[1] == selectionsMade[1][1] - 1){
                        movementLogic = new MovementLogic(true, MOVEMENT_LEFT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                    //If the movement is right
                    else if(gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[1][1] + 1){
                        movementLogic = new MovementLogic(true, MOVEMENT_RIGHT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                }

                break;

            case(DOWN_TO_RIGHT_DIRECTION):
                //If the movement is up
                if(gridLocation[0] == selectionsMade[0][0] - 1){
                    //If the movement selection is above the middle line
                    if(gridLocation[0] < 4){
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[0][1] - 1){
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[0][1]){
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    else{
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[0][1]){
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_LEFT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[0][1] + 1){
                            movementLogic = new MovementLogic(true, MOVEMENT_UP_RIGHT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is down
                else if(gridLocation[0] == selectionsMade[1][0] + 1){
                    //If the movement selection is above or equal to the middle line
                    if(gridLocation[0] <= 4){
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[1][1] + 1){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1][1]){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                    //If the movement selection is below the middle line
                    else{
                        //If the movement is following the line
                        if(gridLocation[1] == selectionsMade[1][1]){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_RIGHT);
                            movementLogic.setMovementIsFollowingLine(true);
                            return movementLogic;
                        }
                        //If the movement is not following the line
                        else if(gridLocation[1] == selectionsMade[1][1] - 1){
                            movementLogic = new MovementLogic(true, MOVEMENT_DOWN_LEFT);
                            movementLogic.setMovementIsFollowingLine(false);
                            return movementLogic;
                        }
                    }
                }
                //If the movement is left or right
                else if(gridLocation[0] == selectionsMade[0][0] || gridLocation[0] == selectionsMade[1][0]){
                    //If the movement is left
                    if(gridLocation[1] == selectionsMade[0][1] - 1 || gridLocation[1] == selectionsMade[1][1] - 1){
                        movementLogic = new MovementLogic(true, MOVEMENT_LEFT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                    //If the movement is right
                    else if(gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[1][1] + 1){
                        movementLogic = new MovementLogic(true, MOVEMENT_RIGHT);
                        movementLogic.setMovementIsFollowingLine(false);
                        return movementLogic;
                    }
                }
                break;
        }

        movementLogic = new MovementLogic(false, NO_MOVEMENT);
        return movementLogic;
    }

    private boolean checkTripleCounterMovementSelection(int[] gridLocation, int[][] selectionsMade){
        //If the selections are all in a line horizontally
        if(selectionsMade[0][0] == selectionsMade[1][0] && selectionsMade[1][0] == selectionsMade[2][0]){
            //If the movement is on the same horizontally
            if(gridLocation[0] == selectionsMade[0][0]){
                if(gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[0][1] - 1){
                    return true;
                }
                else if(gridLocation[1] == selectionsMade[1][1] + 1 || gridLocation[1] == selectionsMade[1][1] - 1){
                    return true;
                }
                else if(gridLocation[1] == selectionsMade[2][1] + 1 || gridLocation[1] == selectionsMade[2][1] - 1){
                    return true;
                }
            }
            //If the movement is against the line
            else if(gridLocation[0] == selectionsMade[0][0] + 1 || gridLocation[0] == selectionsMade[0][0] - 1){

            }
        }
        return false;
    }
}
