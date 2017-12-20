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
    private ArrayList<int[]> selectionsMade;
    private int numberOfCountersSelected;
    private int direction;

    static final int LEFT_TO_RIGHT_DIRECTION = 0;
    static final int DOWN_TO_RIGHT_DIRECTION = 1;
    static final int DOWN_TO_LEFT_DIRECTION = 2;

    static final int X_COORDINATE = 0;
    static final int Y_COORDINATE = 1;

    GridSelections(){
        selectionsMade = new ArrayList<int[]>();
        numberOfCountersSelected = 0;
    }

    /**
     * Add a new selection to the list and order highest to lowest where necessary
     * @param cell The cell to add
     */
    void add(int[] cell) {

        if(numberOfCountersSelected == 0){
            selectionsMade.add(cell);
        }
        else{
            ArrayList<int[]> newSelectionsArray = new ArrayList<int[]>();
            int i = 0;
            if((direction == LEFT_TO_RIGHT_DIRECTION && numberOfCountersSelected == 2) || (selectionsMade.get(0)[Y_COORDINATE] == cell[Y_COORDINATE] && numberOfCountersSelected == 1)) {
                while (i < numberOfCountersSelected && cell[X_COORDINATE] > selectionsMade.get(i)[X_COORDINATE]) {
                    newSelectionsArray.add(selectionsMade.get(i));
                    i++;
                }
            }
            else{
                while(i < numberOfCountersSelected && cell[Y_COORDINATE] > selectionsMade.get(i)[Y_COORDINATE]){
                    newSelectionsArray.add(selectionsMade.get(i));
                    i++;
                }
            }
            //TODO I don't think this is correct
            newSelectionsArray.add(cell);

            for(int j = i; j < numberOfCountersSelected; j++){
                newSelectionsArray.add(selectionsMade.get(j));
            }

            selectionsMade = newSelectionsArray;
            setDirection();

        }

        numberOfCountersSelected++;
    }

    /**
     * Set the direction of the selections to make checking the legality of the selections easier
     */
    void setDirection(){
        if(selectionsMade.get(0)[0] == selectionsMade.get(1)[0]){
            direction = DOWN_TO_LEFT_DIRECTION;
        }
        else if( selectionsMade.get(0)[1] == selectionsMade.get(1)[1]){
            direction = LEFT_TO_RIGHT_DIRECTION;
        }
        else{
            direction = DOWN_TO_RIGHT_DIRECTION;
        }
    }
//
//    ArrayList<Neighbour> getNeighbourCellsOfSelectionsAsRowColumnAndMovementDirection(){
//        ArrayList<Neighbour> selectionNeighbours = new ArrayList<Neighbour>();
//        switch(direction){
//            case LEFT_TO_RIGHT_DIRECTION:
//                //Check top
//                if(selectionsMade[0].getRow() > 0){
//                    if(selectionsMade[0].getRow() < 4) {
//                        if (selectionsMade[0].getColumn() > 0) {
//                            selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() - 1, Move.MOVE_UP_LEFT, false));
//                        }
//                    }
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if(selectionsMade[i].getRow() < 4){
//                            if(selectionsMade[i].getColumn() < selectionsMade[i].getRow() + 5){
//                                if(i == 0 && numberOfCountersSelected == 3) {
//                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_LEFT, false));
//                                }
//                                else{
//                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_RIGHT, false));
//                                }
//                            }
//                        }
//                        else{
//                            if((i == 0 || i == 1) && numberOfCountersSelected == 3) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_LEFT, false));
//                            }
//                            else{
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() - 1, selectionsMade[i].getColumn(), Move.MOVE_UP_RIGHT, false));
//                            }
//                        }
//                    }
//                    if(selectionsMade[0].getRow() > 4){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() - 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_UP_RIGHT, false));
//                    }
//                }
//                //Check middle right
//                if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
//                    if(selectionsMade[numberOfCountersSelected - 1].getColumn() < selectionsMade[numberOfCountersSelected - 1].getRow() + 5){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow(), selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_RIGHT, true));
//                    }
//                }
//                else{
//                    if(selectionsMade[numberOfCountersSelected - 1].getColumn() < 13 - selectionsMade[numberOfCountersSelected - 1].getRow()){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow(), selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_RIGHT, true));
//                    }
//                }
//                //Check the bottom
//                if(selectionsMade[0].getRow() < 8){
//                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_DOWN_RIGHT, false));
//                    }
//                    for(int i = numberOfCountersSelected - 1; i >= 0; i--){
//                        if(selectionsMade[i].getRow() < 4) {
//                            if((i == 0 || i == 1) && numberOfCountersSelected == 3) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_LEFT, false));
//                            }
//                            else{
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_RIGHT, false));
//                            }
//                        }
//                        else{
//                            if(selectionsMade[i].getColumn() < 13 - selectionsMade[i].getRow()){
//                                if((i == 0) && numberOfCountersSelected == 3) {
//                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_LEFT, false));
//                                }
//                                else{
//                                    selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow() + 1, selectionsMade[i].getColumn(), Move.MOVE_DOWN_RIGHT, false));
//                                }
//                            }
//                        }
//                    }
//
//                    if(selectionsMade[0].getRow() >= 4){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() + 1, selectionsMade[0].getColumn() - 1, Move.MOVE_DOWN_LEFT, false));
//                    }
//                }
//                //Check the middle left
//                if(selectionsMade[0].getColumn() > 0){
//                    selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow(), selectionsMade[0].getColumn() - 1, Move.MOVE_LEFT, true));
//                }
//
//                break;
//
//            case DOWN_TO_LEFT_DIRECTION:
//                //Check the left hand side
//                boolean canTravelHorizontally = true;
//                for(int i = 0; i < numberOfCountersSelected; i++){
//                    if(selectionsMade[i].getColumn() == 0){
//                        canTravelHorizontally = false;
//                    }
//                }
//
//                if(canTravelHorizontally){
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if(i == 0 && numberOfCountersSelected == 3){
//                            selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_UP_LEFT, false));
//                        }
//
//                        selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_LEFT, false));
//                    }
//                }
//                else{
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if(selectionsMade[i].getColumn() != 0){
//                            selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_UP_LEFT, false));
//                        }
//                    }
//                }
//
//                //Check above
//                if(selectionsMade[0].getRow() > 0){
//                    //Check left
//                    if(selectionsMade[0].getRow() <= 4){
//                        if(selectionsMade[0].getColumn() > 0){
//                            selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() - 1, Move.MOVE_UP_LEFT, false));
//                        }
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_LEFT, false));
//                    }
//
//                    //Check right
//                    if(selectionsMade[0].getRow() <= 4){
//                        if(selectionsMade[0].getColumn() < selectionsMade[0].getRow() + 4) {
//                            selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_RIGHT, true));
//                        }
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() + 1, Move.MOVE_UP_RIGHT, true));
//                    }
//                }
//
//                //Check horizontally right
//                canTravelHorizontally = true;
//                for(int i = 0; i < numberOfCountersSelected; i++){
//                    if(selectionsMade[i].getRow() <= 4){
//                        if(selectionsMade[i].getColumn() == selectionsMade[i].getRow() + 4){
//                            canTravelHorizontally = false;
//                        }
//                    }else{
//                        if(selectionsMade[i].getColumn() == 13 - selectionsMade[i].getRow()){
//                            canTravelHorizontally = false;
//                        }
//                    }
//                }
//
//                if(canTravelHorizontally){
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if(i == 2 && numberOfCountersSelected == 3){
//                            if(selectionsMade[i].getRow() < 8) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT, false));
//                            }
//                        }
//                        else {
//                            if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_RIGHT, false));
//                            }
//                        }
//                    }
//                }
//                else{
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if (selectionsMade[i].getRow() < 4) {
//                            if (selectionsMade[i].getColumn() < selectionsMade[i].getRow() + 4) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT, false));
//                            }
//                        } else {
//                            if (selectionsMade[i].getColumn() < 13 - selectionsMade[i].getRow() && selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT, false));
//                            }
//                        }
//                    }
//                }
//
//                //Check below
//                if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8){
//                    //Check down right
//                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_DOWN_RIGHT, false));
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_RIGHT, false));
//                    }
//
//                    //Check down left
//                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_LEFT, true));
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() - 1, Move.MOVE_DOWN_LEFT, true));
//                    }
//                }
//
//                break;
//
//            case DOWN_TO_RIGHT_DIRECTION:
//                //Check horizontally left
//                canTravelHorizontally = true;
//                for(int i = 0; i < numberOfCountersSelected; i++){
//                    if(selectionsMade[i].getColumn() == 0){
//                        canTravelHorizontally = false;
//                    }
//                }
//
//                if(canTravelHorizontally){
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if(i == 2 && numberOfCountersSelected == 3){
//                            selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_DOWN_LEFT, false));
//                        }
//                        selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_LEFT, false));
//                    }
//                }
//                else{
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if(selectionsMade[i].getColumn() > 0){
//                            selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() - 1, Move.MOVE_DOWN_LEFT, false));
//                        }
//                    }
//                }
//
//                //Check above
//                if(selectionsMade[0].getRow() > 0){
//                    //Check left
//                    if(selectionsMade[0].getRow() <= 4){
//                        if(selectionsMade[0].getColumn() > 0){
//                            selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() - 1, Move.MOVE_UP_LEFT, true));
//                        }
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_LEFT, true));
//                    }
//
//                    //Check right
//                    if(selectionsMade[0].getRow() <= 4){
//                        if(selectionsMade[0].getColumn() < selectionsMade[0].getRow() + 4) {
//                            selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn(), Move.MOVE_UP_RIGHT, false));
//                        }
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[0].getRow() - 1, selectionsMade[0].getColumn() + 1, Move.MOVE_UP_RIGHT, false));
//                    }
//                }
//
//                //Check horizontally right
//                canTravelHorizontally = true;
//                for(int i = 0; i < numberOfCountersSelected; i++){
//                    if(selectionsMade[i].getRow() <= 4){
//                        if(selectionsMade[i].getColumn() == selectionsMade[i].getRow() + 4){
//                            canTravelHorizontally = false;
//                        }
//                    }
//                    else{
//                        if(selectionsMade[i].getColumn() == 13 - selectionsMade[i].getRow()){
//                            canTravelHorizontally = false;
//                        }
//                    }
//                }
//
//                if(canTravelHorizontally){
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if(i == 2 && numberOfCountersSelected == 3){
//                            if(selectionsMade[i].getRow() < 8) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_RIGHT, false));
//                            }
//                        }
//                        else {
//                            if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_RIGHT, false));
//                            }
//                        }
//                    }
//                }
//                else{
//                    for(int i = 0; i < numberOfCountersSelected; i++){
//                        if (selectionsMade[i].getRow() < 4) {
//                            if (selectionsMade[i].getColumn() < selectionsMade[i].getRow() + 4) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_RIGHT, false));
//                            }
//                        } else {
//                            if (selectionsMade[i].getColumn() < 13 - selectionsMade[i].getRow() && selectionsMade[numberOfCountersSelected - 1].getRow() < 8) {
//                                selectionNeighbours.add(new Neighbour(selectionsMade[i].getRow(), selectionsMade[i].getColumn() + 1, Move.MOVE_DOWN_RIGHT, false));
//                            }
//                        }
//                    }
//                }
//
//                //Check below
//                if(selectionsMade[numberOfCountersSelected - 1].getRow() < 8){
//                    //Check down right
//                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() + 1, Move.MOVE_DOWN_RIGHT, true));
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_RIGHT, true));
//                    }
//
//                    //Check down left
//                    if(selectionsMade[numberOfCountersSelected - 1].getRow() < 4){
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn(), Move.MOVE_DOWN_LEFT, false));
//                    }
//                    else{
//                        selectionNeighbours.add(new Neighbour(selectionsMade[numberOfCountersSelected - 1].getRow() + 1, selectionsMade[numberOfCountersSelected - 1].getColumn() - 1, Move.MOVE_DOWN_LEFT, false));
//                    }
//                }
//
//                break;
//        }
//
//        return selectionNeighbours;
//    }

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
    public ArrayList<int[]> getSelectionsMade(){
        return selectionsMade;
    }

//    class Neighbour{
//        private int row;
//        private int column;
//        private int movementDirection;
//        private boolean isInLine = false;
//
//        Neighbour(int r, int c, int d, boolean is){
//            row = r;
//            column = c;
//            movementDirection = d;
//            isInLine = is;
//        }
//
//        boolean isAtLocation(int[] cell){
//            if(this.row == cell[0] && this.column == cell[1]){
//                return true;
//            }
//            else{
//                return false;
//            }
//        }
//
//        int getRow(){
//            return row;
//        }
//
//        int getColumn(){
//            return column;
//        }
//
//        int getMovementDirection(){
//            return movementDirection;
//        }
//
//        boolean getIsInLine(){
//            return isInLine;
//        }
//    }
}
