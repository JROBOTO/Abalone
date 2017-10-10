package com.jcroberts.abalone;

/**
 * Small class holding the selections whether made by player or computer
 * Author: Joshua Roberts
 */

class GridSelectionsObject {
    private int[][] selectionsMade;
    private int numberOfCountersSelected;
    private int direction;

    private final int LEFT_TO_RIGHT_DIRECTION = 0;
    private final int DOWN__TO_RIGHT_DIRECTION = 1;
    private final int DOWN__TO_LEFT_DIRECTION = 2;

    GridSelectionsObject(){
        selectionsMade = new int[1][];
        numberOfCountersSelected = 0;
    }

    /**
     * Add a new selection to the list and order highest to lowest where necessary
     * @param x The x value on the grid (left to right)
     * @param y The y value on the grid (top to bottom)
     */
    void add(int x, int y) {

        if(numberOfCountersSelected == 0){
            selectionsMade[numberOfCountersSelected] = new int[]{x, y};
        }
        else{
            int[][] newSelectionsArray = new int[numberOfCountersSelected + 1][];
            int i = 0;
            if(direction != LEFT_TO_RIGHT_DIRECTION) {
                while (x > selectionsMade[i][0]) {
                    newSelectionsArray[i] = new int[]{selectionsMade[i][0], selectionsMade[i][1]};
                    i++;
                }
            }
            else{
                while(y > selectionsMade[i][1]){
                    newSelectionsArray[i] = new int[]{selectionsMade[i][0], selectionsMade[i][1]};
                    i++;
                }
            }

            newSelectionsArray[i] = new int[]{x, y};
            i++;

            for(int j = i; j < numberOfCountersSelected + 1; j++){
                newSelectionsArray[j] = new int[]{selectionsMade[j][0], selectionsMade[j][1]};
            }

            selectionsMade = newSelectionsArray;

        }

        numberOfCountersSelected++;
    }

    /**
     * Set the direction of the selections to make checking the legality of the selections easier
     * @param dir
     */
    void setDirection(int dir){
        if(dir < 3) {
            direction = dir;
        }
    }

    /**
     * @return An int between 0 and 2 inclusively to tell the direction of the selections
     */
    int getDirection(){
        return direction;
    }

    /**
     * @return The number of counters that have already been selected
     */
    int getNumberOfCountersSelected(){
        return numberOfCountersSelected;
    }

    /**
     * @return The selections which have already been made
     */
    int[][] getSelectionsMade(){
        return selectionsMade;
    }
}
