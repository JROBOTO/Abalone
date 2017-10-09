package com.jcroberts.abalone;

import android.content.Context;
import android.widget.ImageView;

/**
 * Class to check if a selection made on the game board is legal whether it be for selecting a counter
 * or an attempt to move counters
 * Author: Joshua Roberts
 */

class LegalityChecker{

    LegalityChecker(){

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
            if(gridLocation[0] < 4) {
                if(selectionsMade[0][0] == gridLocation[0] - 1 && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1])) {
                    //If the selection is top left or top right
                    return true;
                }
                else if(selectionsMade[0][0] == gridLocation[0] && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1] + 1)){
                    //If the selection is left or right
                    return true;
                }
                else if(selectionsMade[0][0] == gridLocation[0] + 1 && (gridSelections.getSelectionsMade()[0][1] == gridLocation[1] || selectionsMade[0][1] == gridLocation[1] + 1)){
                    //If selection is bottom left or bottom right
                    return true;
                }
            }
            else if(gridLocation[0] == 4){
                if(selectionsMade[0][0] == gridLocation[0] - 1 && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1])) {
                    //If the selection is top left or top right
                    return true;
                }
                else if(selectionsMade[0][0] == gridLocation[0] && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1] + 1)){
                    //If the selection is left or right
                    return true;
                }
                else if(selectionsMade[0][0] == gridLocation[0] + 1 && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1])){
                    //If the selection is bottom left or bottom right
                    return true;
                }
            }
            else if(gridLocation[0] > 4){
                if(selectionsMade[0][0] == gridLocation[0] - 1 && (selectionsMade[0][1] == gridLocation[1] || selectionsMade[0][1] == gridLocation[1] + 1)){
                    //If the selection is top left or top right
                    return true;
                }
                else if(selectionsMade[0][0] == gridLocation[0] && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1] + 1)){
                    //If the selection is left or right
                    return true;
                }
                else if(selectionsMade[0][0] == gridLocation[0] + 1 && (selectionsMade[0][1] == gridLocation[1] - 1 || selectionsMade[0][1] == gridLocation[1])){
                    //If the selection is bottom left or bottom right
                    return true;
                }
            }
        }
        //If this is the third selection
        else if(gridSelections.getNumberOfCountersSelected() == 2){
            //If the selection is to the left or right
            if(selectionsMade[0][0] == selectionsMade[1][0]){
                if(gridLocation[0] == selectionsMade[0][0]){
                    if(gridLocation[1] == selectionsMade[0][1] - 1){
                        return true;
                    }
                    else if(gridLocation[1] == selectionsMade[0][1] + 1){
                        return true;
                    }
                    else if(gridLocation[1] == selectionsMade[1][1] - 1){
                        return true;
                    }
                    else if(gridLocation[1] == selectionsMade[1][1] + 1){
                        return true;
                    }
                }
            }
            //If the first selection is below the second
            else if(selectionsMade[0][0] == selectionsMade[1][0] + 1){
                //If the new selection is below the first
                if(gridLocation[0] == selectionsMade[0][0] + 1){
                    //If all selections are above or equal to the middle row
                    if(gridLocation[0] <= 4 && selectionsMade[0][0] <= 4 && selectionsMade[1][0] <= 4){
                        //If the selections are all in line
                        if(selectionsMade[0][1] == selectionsMade[1][1] && selectionsMade[0][1] == gridLocation[1]){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[0][1] + 1 && selectionsMade[0][1] == selectionsMade[1][1] + 1){
                            return true;
                        }
                    }
                    //If all selections are below or equal to the middle row
                    else if(gridLocation[0] >= 4 && selectionsMade[0][0] >= 4 && selectionsMade[1][0] >= 4){
                        //If the selections are all in line
                        if(selectionsMade[0][1] == selectionsMade[1][1] && selectionsMade[0][1] == gridLocation[1]){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[0][1] - 1 && selectionsMade[0][1] == selectionsMade[1][1] - 1){
                            return true;
                        }
                    }
                    //If the selections are both above and below the middle row
                    else if(gridLocation[0] == 5 && selectionsMade[0][0] == 4 && selectionsMade[1][0] == 3){
                        //If the selections are all in line
                        if(gridLocation[1] == selectionsMade[0][1] && selectionsMade[1][1] == selectionsMade[0][1] - 1){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[0][1] - 1 && selectionsMade[0][1] == selectionsMade[1][1]){
                            return true;
                        }
                    }
                }
                //If the new selection is above the second
                else if(gridLocation[0] == selectionsMade[1][0] - 1){
                    //If the selections are all above the middle row
                    if(gridLocation[0] <= 4 && selectionsMade[0][0] <= 4 && selectionsMade[1][0] <= 4){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[1][1] && gridLocation[1] == selectionsMade[0][1]){
                            return true;
                        }
                        else if(selectionsMade[0][1] == selectionsMade[1][1] + 1 && selectionsMade[1][1] == gridLocation[1] + 1){
                            return true;
                        }
                    }
                    //If the selections are all below the middle row
                    else if(gridLocation[0] >= 4 && selectionsMade[0][0] >= 4 && selectionsMade[1][0] >= 4){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[1][1] && selectionsMade[0][1] == selectionsMade[1][1]){
                            return true;
                        }
                        else if(selectionsMade[0][1] == selectionsMade[1][1] - 1 && selectionsMade[1][1] == gridLocation[1] - 1){
                            return true;
                        }
                    }
                    //If the selections are both above and below the middle row
                    else if(gridLocation[0] == 3 && selectionsMade[1][0] == 4 && selectionsMade[0][0] == 5){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[1][1] && selectionsMade[1][1] == selectionsMade[0][1] + 1){
                            return true;
                        }
                        else if(selectionsMade[0][1] == selectionsMade[1][1] && selectionsMade[1][1] == gridLocation[1] + 1){
                            return true;
                        }
                    }
                }
            }

            //If the second selection is below the first
            else if(selectionsMade[0][0] == selectionsMade[1][0] - 1){
                //If the new selection is above the first
                if(gridLocation[0] == selectionsMade[0][0] - 1){
                    //If all selections are above or equal to the middle row
                    if(gridLocation[0] <= 4 && selectionsMade[0][0] <= 4 && selectionsMade[1][0] <= 4){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[0][1] && gridLocation[1] == selectionsMade[1][1]){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[0][1] - 1 && selectionsMade[0][1] == selectionsMade[1][1] - 1){
                            return true;
                        }
                    }
                    //If all selections are below or equal to the middle row
                    else if(gridLocation[0] >= 4 && selectionsMade[0][0] >= 4 && selectionsMade[1][0] >= 4){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[0][1] && gridLocation[1] == selectionsMade[1][1]){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[0][1] + 1 && selectionsMade[0][1] == selectionsMade[1][1] + 1){
                            return true;
                        }
                    }
                    //If the selections are both above and below the middle row
                    else if(gridLocation[0] == 3 && selectionsMade[0][0] == 4 && selectionsMade[1][0] == 5){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[0][1] && selectionsMade[0][1] == selectionsMade[1][1] + 1){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[0][1] - 1 && selectionsMade[0][1] == selectionsMade[1][1]){
                            return true;
                        }
                    }
                }
                //If the new selection is below the second
                else if(gridLocation[0] == selectionsMade[1][0] + 1){
                    //If the selections are all above or in line with the middle row
                    if(gridLocation[0] <= 4 && selectionsMade[0][0] <= 4 && selectionsMade[1][0] <= 4){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[0][1] && gridLocation[1] == selectionsMade[1][1]){
                            return true;
                        }
                        else if(selectionsMade[0][1] == selectionsMade[1][1] - 1 && selectionsMade[1][1] == gridLocation[1] - 1){
                            return true;
                        }
                    }
                    //If all selections are below or in line with the middle row
                    else if(gridLocation[0] >= 4 && selectionsMade[0][0] >= 4 && selectionsMade[1][0] >= 4){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[0][1] && selectionsMade[0][1] == selectionsMade[1][1]){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[1][1] - 1 && selectionsMade[1][1] == selectionsMade[0][1] - 1){
                            return true;
                        }
                    }
                    //If the selections are both above and below the middle row
                    else if(gridLocation[0] == 5 && selectionsMade[1][0] == 4 && selectionsMade[0][0] == 3){
                        //If they are all in line
                        if(gridLocation[1] == selectionsMade[1][1] && selectionsMade[0][1] == selectionsMade[1][1] - 1){
                            return true;
                        }
                        else if(gridLocation[1] == selectionsMade[1][1] - 1 && selectionsMade[0][1] == selectionsMade[1][1]){
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }

    /**
    * Find out if the selected move is legal
    * @return Whether or not the move is legal
    */
    boolean checkMoveSelectionIsLegal(int[] gridLocation, GridSelectionsObject gridSelections){
        int numberOfCountersSelected = gridSelections.getNumberOfCountersSelected();
        int[][] selectionsMade = gridSelections.getSelectionsMade();

        //If only one counter has been selected to move
        if(numberOfCountersSelected == 1){
            return checkSingleCounterMovementSelection(gridLocation, selectionsMade);
        }
        //If two counters have been selected to move
        else if(numberOfCountersSelected == 2) {
            return checkDoubleCounterMovementSelection(gridLocation, selectionsMade);
        }
        else if(numberOfCountersSelected == 3){
            return checkTripleCounterMovementSelection(gridLocation, selectionsMade);
        }

        return false;
    }

    private boolean checkSingleCounterMovementSelection(int[] gridLocation, int[][] selectionsMade){
        //If the counter to move is in line with the counter
        if (gridLocation[0] == selectionsMade[0][0]) {
            if (gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[0][1] - 1) {
                return true;
            }
        }
        //If the movement selection is above the counter
        else if (gridLocation[0] == selectionsMade[0][0] - 1) {
            //If the movement selection is above the middle line
            if (gridLocation[0] < 4) {
                if (gridLocation[1] == selectionsMade[0][1] || gridLocation[1] == selectionsMade[0][1] - 1) {
                    return true;
                }
            }
            //If movement selection is below the middle line
            else if (gridLocation[0] >= 4) {
                if (gridLocation[1] == selectionsMade[0][1] || gridLocation[1] == selectionsMade[0][1] + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDoubleCounterMovementSelection(int[] gridLocation, int[][] selectionsMade){
        //If the selections to move are in the same line horizontally
        if (selectionsMade[0][0] == selectionsMade[1][0]) {
            //If the movement is all in line
            if (gridLocation[0] == selectionsMade[0][0]) {
                if (gridLocation[1] == selectionsMade[0][1] - 1 || gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[1][1] - 1 || gridLocation[1] == selectionsMade[1][1] + 1) {
                    return true;
                }
            }
            //If the movement is against the line
            //If the movement is all below the central line
            else if (selectionsMade[0][0] >= 4 && gridLocation[0] >= 4) {
                //If the movement is up
                if (gridLocation[0] == selectionsMade[0][0] - 1) {
                    if (selectionsMade[0][1] < selectionsMade[1][1]) {
                        if (gridLocation[1] <= selectionsMade[1][1] + 1 && gridLocation[1] >= selectionsMade[0][1]) {
                            return true;
                        }
                    } else if (selectionsMade[0][1] > selectionsMade[1][1]) {
                        if (gridLocation[1] <= selectionsMade[0][1] + 1 && gridLocation[1] >= selectionsMade[1][1]) {
                            return true;
                        }
                    }
                }
                //If the movement is down
                if (gridLocation[0] == selectionsMade[0][0] + 1) {
                    if (selectionsMade[0][1] < selectionsMade[1][1]) {
                        if (gridLocation[1] >= selectionsMade[0][1] - 1 && gridLocation[1] <= selectionsMade[1][1]) {
                            return true;
                        }
                    } else if (selectionsMade[0][1] > selectionsMade[1][1]) {
                        if (gridLocation[1] >= selectionsMade[1][1] - 1 && gridLocation[1] <= selectionsMade[0][1]) {
                            return true;
                        }
                    }
                }
            }
            //If all movement is above or equal to the middle line
            else if (gridLocation[0] <= 4 && selectionsMade[0][0] <= 4) {
                //If the movement is up
                if (gridLocation[0] < selectionsMade[0][0]) {
                    if (selectionsMade[0][1] > selectionsMade[1][1]) {
                        if (gridLocation[1] >= selectionsMade[1][1] - 1 && gridLocation[1] <= selectionsMade[0][1]) {
                            return true;
                        }
                    } else if (selectionsMade[1][1] > selectionsMade[0][1]) {
                        if (gridLocation[1] >= selectionsMade[0][1] - 1 && gridLocation[1] <= selectionsMade[1][1]) {
                            return true;
                        }
                    }
                }
                //If the movement is down
                else if (gridLocation[0] > selectionsMade[0][0]) {
                    if (selectionsMade[0][1] > selectionsMade[1][1]) {
                        if (gridLocation[1] >= selectionsMade[1][1] && gridLocation[1] <= selectionsMade[0][1] + 1) {
                            return true;
                        }
                    } else if (selectionsMade[1][1] > selectionsMade[0][1]) {
                        if (gridLocation[1] >= selectionsMade[0][1] && gridLocation[1] <= selectionsMade[1][1] + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        //If the selected counters are all on different lines
        //If the first selections is above the second
        else if (selectionsMade[0][0] < selectionsMade[1][0]) {
            //If the movement is all following the same line
            if (gridLocation[0] == selectionsMade[0][0] - 1 || gridLocation[0] == selectionsMade[1][0] + 1) {
                //If the movement is all above the middle line
                if (selectionsMade[0][0] <= 4 && selectionsMade[1][0] <= 4 && gridLocation[0] <= 4) {
                    if (selectionsMade[0][1] == selectionsMade[1][1] && selectionsMade[1][1] == gridLocation[1]) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[0][1] - 1 || gridLocation[1] == selectionsMade[1][1] + 1) {
                        return true;
                    }
                }
                //If the movement is all below the middle line
                else if (selectionsMade[0][0] >= 4 && selectionsMade[1][0] >= 4 && gridLocation[0] >= 4) {
                    if (selectionsMade[0][1] == selectionsMade[1][1] && selectionsMade[1][1] == gridLocation[1]) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[1][1] - 1) {
                        return true;
                    }
                }
                //If the movement is through the middle line
                //If the movement is down
                else if (selectionsMade[0][0] == 3 && selectionsMade[1][0] == 4 && gridLocation[0] == 5) {
                    if (gridLocation[1] == selectionsMade[1][1] - 1 && selectionsMade[0][1] == selectionsMade[1][1]) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[1][1] && selectionsMade[0][1] == selectionsMade[1][1] - 1) {
                        return true;
                    }
                }
                //If the movement is up
                else if (gridLocation[0] == 3 && selectionsMade[0][0] == 4 && selectionsMade[1][0] == 5) {
                    if (gridLocation[1] == selectionsMade[0][1] && selectionsMade[0][1] == selectionsMade[1][1] - 1) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[0][1] - 1 && selectionsMade[0][1] == selectionsMade[1][1]) {
                        return true;
                    }
                }
            }
            //If the movement is against the line
            else if (gridLocation[0] == selectionsMade[0][0] || gridLocation[0] == selectionsMade[1][0]) {
                if (gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[1][1] + 1) {
                    return true;
                } else if (gridLocation[1] == selectionsMade[0][1] - 1 || gridLocation[1] == selectionsMade[1][1] - 1) {
                    return true;
                }
            }
        }
        //If the second selection is above the first
        else if (selectionsMade[0][0] > selectionsMade[1][0]) {
            //If the movement is all along the same line
            if (gridLocation[0] == selectionsMade[1][0] - 1 || gridLocation[0] == selectionsMade[0][0] + 1) {
                //If the movement is all above the middle line
                if (selectionsMade[0][0] <= 4 && selectionsMade[1][0] <= 4 && gridLocation[0] <= 4) {
                    if (selectionsMade[0][1] == selectionsMade[1][1] && selectionsMade[1][1] == gridLocation[1]) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[1][1] + 1 || gridLocation[1] == selectionsMade[0][1] - 1) {
                        return true;
                    }
                }
                //If the movement is all below the middle line
                else if (selectionsMade[0][0] >= 4 && selectionsMade[1][0] >= 4 && gridLocation[0] >= 4) {
                    if (selectionsMade[0][1] == selectionsMade[1][1] && selectionsMade[1][1] == gridLocation[1]) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[1][1] + 1 || gridLocation[1] == selectionsMade[0][1] - 1) {
                        return true;
                    }
                }
                //If the movement is across the middle line
                //If the movement is down
                else if (gridLocation[0] == 3 && selectionsMade[1][0] == 4 && selectionsMade[0][0] == 5) {
                    if (gridLocation[1] == selectionsMade[0][1] - 1 && selectionsMade[1][1] == selectionsMade[0][1]) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[0][1] && selectionsMade[1][1] == selectionsMade[0][1] - 1) {
                        return true;
                    }
                }
                //If the movement is up
                else if (selectionsMade[1][0] == 3 && selectionsMade[0][0] == 4 && gridLocation[0] == 5) {
                    if (gridLocation[1] == selectionsMade[1][1] && selectionsMade[1][1] == selectionsMade[0][1] - 1) {
                        return true;
                    } else if (gridLocation[1] == selectionsMade[1][1] - 1 && selectionsMade[1][1] == selectionsMade[0][1]) {
                        return true;
                    }
                }
            }
            //If the movement is against the line
            else if (gridLocation[0] == selectionsMade[0][0] || gridLocation[0] == selectionsMade[1][0]) {
                if (gridLocation[1] == selectionsMade[0][1] + 1 || gridLocation[1] == selectionsMade[1][1] + 1) {
                    return true;
                } else if (gridLocation[1] == selectionsMade[0][1] - 1 || gridLocation[1] == selectionsMade[1][1] - 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkTripleCounterMovementSelection(int[] gridLocation, int[][] selectionsMade){

        return false;
    }
}
