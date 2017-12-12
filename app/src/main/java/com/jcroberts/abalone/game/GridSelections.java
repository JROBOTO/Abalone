package com.jcroberts.abalone.game;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Small class holding the selections whether made by player or computer
 * Author: Joshua Roberts
 */

public class GridSelections {
    private GameBoard.Cell[] selectionsMade;
    private int numberOfCountersSelected;
    private int direction;

    static final int LEFT_TO_RIGHT_DIRECTION = 0;
    static final int DOWN_TO_RIGHT_DIRECTION = 1;
    static final int DOWN_TO_LEFT_DIRECTION = 2;

    static final int ROW = 0;
    static final int COLUMN = 1;

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

    ArrayList<Neighbour> getNeighbourCellsOfSelectionsAsRowColumnAndMovementDirection(){
        ArrayList<Neighbour> selectionNeighbours = new ArrayList<int[]>();
        switch(direction){
            case LEFT_TO_RIGHT_DIRECTION:
                //Check top
                if(selectionsMade[0].getRow() > 0){
                    if(selectionsMade[0].getRow() < 4) {
                        if (selectionsMade[0].getColumn() > 0) {
                            selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() - 1, Move.MOVE_UP_LEFT, false));
                        }
                    }
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(selectionsMade[i].getRow() < 4){
                            if(selectionsMade[i].getColumn() < selectionsMade[i].getRow() + 5){
                                if(i == 0 && numberOfCountersSelected == 3) {
                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_LEFT, false));
                                }
                                else{
                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_RIGHT, false));
                                }
                            }
                        }
                        else{
                            if((i == 0 || i == 1) && numberOfCountersSelected == 3) {
                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_LEFT, false));
                            }
                            else{
                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_RIGHT, false));
                            }
                        }
                    }
                    if(selectionsMade[0].getRow() > 4){
                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() - 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_UP_RIGHT, false));
                    }
                }
                //Check middle right
                if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
                    if(selectionsMade[numberOfCountersSelected - 1].getColumn() < selectionsMade[numberOfCountersSelected - 1].getRow() + 5){
                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow(), selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_RIGHT, true));
                    }
                }
                else{
                    if(selectionsMade[numberOfCountersSelected - 1].getColumn() < 13 - selectionsMade[numberOfCountersSelected - 1].getRow()){
                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow(), selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_RIGHT, true));
                    }
                }
                //Check the bottom
                if(selectionsMade[0].getRow() < 8){
                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_DOWN_RIGHT, false));
                    }
                    for(int i = numberOfCountersSelected - 1; i >= 0; i--){
                        if(selectionsMade[i].getRow() < 4) {
                            if((i == 0 || i == 1) && numberOfCountersSelected == 3) {
                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_LEFT, false));
                            }
                            else{
                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_RIGHT, false));
                            }
                        }
                        else{
                            if(selectionsMade[i].getColumn() < 13 - selectionsMade[i].getRow()){
                                if((i == 0) && numberOfCountersSelected == 3) {
                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_LEFT, false));
                                }
                                else{
                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_RIGHT, false));
                                }
                            }
                        }
                    }

                    if(selectionsMade[0].getRow() >= 4){
                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() + 1, selectionsMade[0].getColumn() - 1, Move.MOVE_DOWN_LEFT, false));
                    }
                }
                //Check the middle left
                if(selectionsMade[0].getColumn() > 0){
                    selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow(), selectionsMade[0].getColumn() - 1, Move.MOVE_LEFT, true));
                }

                break;

            case DOWN_TO_LEFT_DIRECTION:
                //Check the left hand side
                boolean canTravelHorizontally = true;
                for(int i = 0; i < numberOfCountersSelected; i++){
                    if(selectionsMade[i].getColumn() == 0){
                        canTravelHorizontally = false;
                    }
                }

                if(canTravelHorizontally){
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 0 && numberOfCountersSelected == 3){
                            selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_UP_LEFT, false));
                        }

                        //TODO This is where we got to with the change over. Yes we includes the strange voices in my head
                        selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_LEFT});
                    }
                }
                else{
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(selectionsMade[i].getColumn() != 0){
                            selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_UP_LEFT});
                        }
                    }
                }

                //Check above
                if(selectionsMade[0].getRow() > 0){
                    //Check left
                    if(selectionsMade[0].getRow() <= 4){
                        if(selectionsMade[0].getColumn() > 0){
                            selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() - 1, Move.MOVE_UP_LEFT});
                        }
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_LEFT});
                    }

                    //Check right
                    if(selectionsMade[0].getRow() <= 4){
                        if(selectionsMade[0].getColumn() < selectionsMade[0].getRow() + 4) {
                            selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_RIGHT});
                        }
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() + 1, Move.MOVE_UP_RIGHT});
                    }
                }

                //Check horizontally right
                canTravelHorizontally = true;
                for(int i = 0; i < numberOfCountersSelected; i++){
                    if(selectionsMade[i].getRow() <= 4){
                        if(selectionsMade[i].getColumn() == selectionsMade[i].getRow() + 4){
                            canTravelHorizontally = false;
                        }
                    }else{
                        if(selectionsMade[i].getColumn() == 13 - selectionsMade[i].getRow()){
                            canTravelHorizontally = false;
                        }
                    }
                }

                if(canTravelHorizontally){
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 2 && numberOfCountersSelected == 3){
                            if(selectionsMade[i].getRow() < 8) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                            }
                        }
                        else {
                            if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_RIGHT});
                            }
                        }
                    }
                }
                else{
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if (selectionsMade[i].getRow() < 4) {
                            if (selectionsMade[i].getColumn() < selectionsMade[i].getRow() + 4) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                            }
                        } else {
                            if (selectionsMade[i].getColumn() < 13 - selectionsMade[i].getRow() && selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                            }
                        }
                    }
                }

                //Check below
                if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8){
                    //Check down right
                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_RIGHT});
                    }

                    //Check down left
                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_LEFT});
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() - 1, Move.MOVE_DOWN_LEFT});
                    }
                }

                break;

            case DOWN_TO_RIGHT_DIRECTION:
                //Check horizontally left
                canTravelHorizontally = true;
                for(int i = 0; i < numberOfCountersSelected; i++){
                    if(selectionsMade[i].getColumn() == 0){
                        canTravelHorizontally = false;
                    }
                }

                if(canTravelHorizontally){
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 2 && numberOfCountersSelected == 3){
                            selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_DOWN_LEFT});
                        }
                        selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_LEFT});
                    }
                }
                else{
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(selectionsMade[i].getColumn() > 0){
                            selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_DOWN_LEFT});
                        }
                    }
                }

                //Check above
                if(selectionsMade[0].getRow() > 0){
                    //Check left
                    if(selectionsMade[0].getRow() <= 4){
                        if(selectionsMade[0].getColumn() > 0){
                            selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() - 1, Move.MOVE_UP_LEFT});
                        }
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_LEFT});
                    }

                    //Check right
                    if(selectionsMade[0].getRow() <= 4){
                        if(selectionsMade[0].getColumn() < selectionsMade[0].getRow() + 4) {
                            selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_RIGHT});
                        }
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() + 1, Move.MOVE_UP_RIGHT});
                    }
                }

                //Check horizontally right
                canTravelHorizontally = true;
                for(int i = 0; i < numberOfCountersSelected; i++){
                    if(selectionsMade[i].getRow() <= 4){
                        if(selectionsMade[i].getColumn() == selectionsMade[i].getRow() + 4){
                            canTravelHorizontally = false;
                        }
                    }
                    else{
                        if(selectionsMade[i].getColumn() == 13 - selectionsMade[i].getRow()){
                            canTravelHorizontally = false;
                        }
                    }
                }

                if(canTravelHorizontally){
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 2 && numberOfCountersSelected == 3){
                            if(selectionsMade[i].getRow() < 8) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                            }
                        }
                        else {
                            if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_RIGHT});
                            }
                        }
                    }
                }
                else{
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if (selectionsMade[i].getRow() < 4) {
                            if (selectionsMade[i].getColumn() < selectionsMade[i].getRow() + 4) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                            }
                        } else {
                            if (selectionsMade[i].getColumn() < 13 - selectionsMade[i].getRow() && selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
                                selectionNeighbours.add(new int[]{selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                            }
                        }
                    }
                }

                //Check below
                if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8){
                    //Check down right
                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_DOWN_RIGHT});
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_RIGHT});
                    }

                    //Check down left
                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_LEFT});
                    }
                    else{
                        selectionNeighbours.add(new int[]{selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() - 1, Move.MOVE_DOWN_LEFT});
                    }
                }

                break;
        }

        return selectionNeighbours;
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
    public GameBoard.Cell[] getSelectionsMade(){
        return selectionsMade;
    }

    class Neighbour{
        private int row;
        private int column;
        private int movementDirection;
        private boolean isInLine = false;

        Neighbour(int r, int c, int d, boolean is){
            row = r;
            column = c;
            movementDirection = d;
            isInLine = is;
        }

        int getRow(){
            return row;
        }

        int getColumn(){
            return column;
        }

        int getMovementDirection(){
            return movementDirection;
        }

        boolean getIsInLine(){
            return isInLine;
        }
    }
}
