package com.jcroberts.abalone;

/**
 * Class to check if a selection made on the game board is legal whether it be for selecting a counter
 * or an attempt to move counters
 * Author: Joshua Roberts
 */

public class LegalityChecker{

    public LegalityChecker(){

    }

    /**
     * Check if the counter selected is legal
     * @return Whether or not the counter selected is legal
     */
    public boolean counterSelectionIsLegal(int[] gridLocation, GridSelectionsObject gridSelections){
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
                    else if(gridLocation[0] >= 4 && selectionsMade[0][0] >= 4 && selectionsMade[1][0] == gridLocation[0]){
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
            //If the second selections is below the first
            else if(selectionsMade[0][0] == selectionsMade[1][0] + 1){
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
    public boolean checkMoveIsLegal(int[] gridLocation, GridSelectionsObject gridSelections, boolean isPushingOpponent){
        int numberOfCountersSelected = gridSelections.getNumberOfCountersSelected();
        int[][] selectionsMade = gridSelections.getSelectionsMade();

        //If only one counter has been selected to move
        if(numberOfCountersSelected == 1){
            //Single counters can't push anything
            if(!isPushingOpponent) {
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
            }
        }
        //If two counters have been selected to move
        if(numberOfCountersSelected == 1){
            //If the play is not trying to push an opponents counter
            if(!isPushingOpponent){

            }
            //TODO Finish this
        }
        return false;
    }
}
