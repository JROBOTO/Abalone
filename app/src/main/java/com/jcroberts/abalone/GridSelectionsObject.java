package com.jcroberts.abalone;

/**
 * Small class holding the selections whether made by player or computer
 * Author: Joshua Roberts
 */

public class GridSelectionsObject {
    private int[][] selectionsMade;
    private int numberOfCountersSelected;

    public GridSelectionsObject(){
        selectionsMade = new int[3][];
        numberOfCountersSelected = 0;
    }

    /**
     * Add a new selection to the list
     * @param x The x value on the grid (left to right)
     * @param y The y value on the grid (top to bottom)
     */
    public void add(int x, int y){
        selectionsMade[numberOfCountersSelected] = new int[]{x, y};
        numberOfCountersSelected++;
    }

    /**
     * Remove a selection from the list
     * @param x The x value on the grid (left to right)
     * @param y The y value on the grid (top to bottom)
     */
    public void remove(int x, int y){
        for(int i = 0; i < numberOfCountersSelected; i++){
            if(selectionsMade[i][0] == x && selectionsMade[i][1] == y){
                selectionsMade[i] = selectionsMade[i+1];
                selectionsMade[i+1] = selectionsMade[i+2];
                selectionsMade[i+2] = null;
                break;
            }
        }
        numberOfCountersSelected--;
    }

    /**
     * @return The number of counters that have already been selected
     */
    public int getNumberOfCountersSelected(){
        return numberOfCountersSelected;
    }

    /**
     * @return selectionsMade
     */
    public int[][] getSelectionsMade(){
        return selectionsMade;
    }
}
