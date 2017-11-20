package com.jcroberts.abalone.game;

/**
 * Small class holding the selections whether made by player or computer
 * Author: Joshua Roberts
 */

class GridSelections {
    private GameBoard.Cell[] selectionsMade;
    private int numberOfCountersSelected;
    private int direction;

    static final int LEFT_TO_RIGHT_DIRECTION = 0;
    static final int DOWN_TO_RIGHT_DIRECTION = 1;
    static final int DOWN_TO_LEFT_DIRECTION = 2;

    GridSelections(){
        selectionsMade = new GameBoard.Cell[1];
        numberOfCountersSelected = 0;
    }

    /**
     * Add a new selection to the list and order highest to lowest where necessary
     * @param cell The cell to add
     */
    void add(GameBoard.Cell cell) {

        if(numberOfCountersSelected == 0){
            selectionsMade[numberOfCountersSelected] = cell;
        }
        else{
            GameBoard.Cell[] newSelectionsArray = new GameBoard.Cell[numberOfCountersSelected + 1];
            int i = 0;
            //TODO This is probably wrong
            if(direction != LEFT_TO_RIGHT_DIRECTION) {
                while (cell.getRow() > selectionsMade[i].getRow()) {
                    newSelectionsArray[i] = selectionsMade[i];
                    i++;
                }
            }
            else{
                while(cell.getColumn() > selectionsMade[i].getColumn()){
                    newSelectionsArray[i] = selectionsMade[i];
                    i++;
                }
            }

            newSelectionsArray[i] = cell;
            i++;

            for(int j = i; j < numberOfCountersSelected + 1; j++){
                newSelectionsArray[j] = selectionsMade[j];
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
    GameBoard.Cell[] getSelectionsMade(){
        return selectionsMade;
    }
}
