package com.jcroberts.abalone.game;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Small class holding the selections whether made by player or computer
 * Author: Joshua Roberts
 */

public class GridSelections implements Serializable{
    private ArrayList<int[]> selectionsMade;
    private int numberOfCountersSelected;
    private int direction;

    static final int LEFT_TO_RIGHT_DIRECTION = 0;
    static final int DOWN_TO_RIGHT_DIRECTION = 1;
    static final int DOWN_TO_LEFT_DIRECTION = 2;

    public static final int Y_COORDINATE = 0;
    public static final int X_COORDINATE = 1;

    public GridSelections(){
        selectionsMade = new ArrayList<>();
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
            ArrayList<int[]> newSelectionsArray = new ArrayList<>();
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

            newSelectionsArray.add(cell);

            for(int j = i; j < numberOfCountersSelected; j++){
                newSelectionsArray.add(selectionsMade.get(j));
            }

            selectionsMade = newSelectionsArray;
            setDirection();

        }

        numberOfCountersSelected++;
    }

    @Override
    public boolean equals(Object object){
        if(object.getClass() != this.getClass()){
            return false;
        }

        boolean isEqual = true;
        try {
            GridSelections selections = (GridSelections)object;

            for (int i = 0; i < selectionsMade.size(); i++) {
                if(selectionsMade.get(i) != selections.getSelectionsMade().get(i)){
                    isEqual = false;
                }
            }

            return isEqual;
        }
        catch(IndexOutOfBoundsException ioobe){
            return false;
        }
    }

    /**
     * Set the direction of the selections to make checking the legality of the selections easier
     */
    private void setDirection(){
        if( selectionsMade.get(0)[Y_COORDINATE] == selectionsMade.get(1)[Y_COORDINATE]){
            direction = LEFT_TO_RIGHT_DIRECTION;
        }
        else if(selectionsMade.get(0)[X_COORDINATE] == selectionsMade.get(1)[X_COORDINATE]){
            direction = DOWN_TO_LEFT_DIRECTION;
        }
        else{
            direction = DOWN_TO_RIGHT_DIRECTION;
        }
    }

    ArrayList<int[]> getLegalNeighbourCountersOfPlayer(int[][] gameBoard, int player){
        ArrayList<int[]> neighbourCounters = new ArrayList<>();

        if(getNumberOfCountersSelected() == 1){
            if(gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE] - 1] == player){
                neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1});
            }
            if(gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE]] == player){
                neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE]});
            }
            if(gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 1] == player){
                neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] - 1});
            }
            if(gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] + 1] == player){
                neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] + 1});
            }
            if(gameBoard[selectionsMade.get(0)[Y_COORDINATE] + 1][selectionsMade.get(0)[X_COORDINATE]] == player){
                neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE] + 1, selectionsMade.get(0)[X_COORDINATE]});
            }
            if(gameBoard[selectionsMade.get(0)[Y_COORDINATE] + 1][selectionsMade.get(0)[X_COORDINATE] + 1] == player){
                neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE] + 1, selectionsMade.get(0)[X_COORDINATE] + 1});
            }
        }
        else if(numberOfCountersSelected == 2){
            switch(direction){
                case LEFT_TO_RIGHT_DIRECTION:
                    if(gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 1] == player){
                        neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] - 1});
                    }
                    if(gameBoard[selectionsMade.get(1)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] + 1] == player){
                        neighbourCounters.add(new int[]{selectionsMade.get(1)[Y_COORDINATE], selectionsMade.get(1)[X_COORDINATE] + 1});
                    }

                    break;

                case DOWN_TO_RIGHT_DIRECTION:
                    if(gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE] - 1] == player){
                        neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1});
                    }
                    if(gameBoard[selectionsMade.get(1)[Y_COORDINATE] + 1][selectionsMade.get(1)[X_COORDINATE] + 1] == player){
                        neighbourCounters.add(new int[]{selectionsMade.get(1)[Y_COORDINATE] + 1, selectionsMade.get(1)[X_COORDINATE] + 1});
                    }

                    break;

                case DOWN_TO_LEFT_DIRECTION:
                    if(gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE]] == player){
                        neighbourCounters.add(new int[]{selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE]});
                    }
                    if(gameBoard[selectionsMade.get(1)[Y_COORDINATE] + 1][selectionsMade.get(1)[X_COORDINATE]] == player){
                        neighbourCounters.add(new int[]{selectionsMade.get(1)[Y_COORDINATE] + 1, selectionsMade.get(1)[X_COORDINATE]});
                    }

                    break;
            }
        }

        return neighbourCounters;
    }

    ArrayList<Neighbour> getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection(int[][] gameBoard){
        ArrayList<Neighbour> neighbours = new ArrayList<>();
        boolean canMove;
        int opposition;
        if(gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE]] == 1){
            opposition = 2;
        }
        else{
            opposition = 1;
        }
        if(numberOfCountersSelected == 1){
            if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE] - 1] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_UP_LEFT, true, 0));
            }
            if (gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 1] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_LEFT, true, 0));
            }
            if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE]] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE], Move.MOVE_UP_RIGHT, true, 0));
            }
            if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] + 1][selectionsMade.get(0)[X_COORDINATE]] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] + 1, selectionsMade.get(0)[X_COORDINATE], Move.MOVE_DOWN_LEFT, true, 0));
            }
            if (gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] + 1] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] + 1, Move.MOVE_RIGHT, true, 0));
            }
            if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] + 1][selectionsMade.get(0)[X_COORDINATE] + 1] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] + 1, selectionsMade.get(0)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, true, 0));
            }
        }
        else if(numberOfCountersSelected > 1){
            switch(direction){
                case LEFT_TO_RIGHT_DIRECTION:
                    //Add above
                    boolean canMoveUpLeft = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE] - 1][selectionsMade.get(i)[X_COORDINATE] - 1] != 0) {
                                canMoveUpLeft = false;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException aioobe){
                        canMoveUpLeft = false;
                    }
                    if(canMoveUpLeft) {
                        neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_UP_LEFT, false, 0));
                    }

                    canMove = true;
                    try {
                        for (int j = 0; j < numberOfCountersSelected; j++) {
                            if (gameBoard[selectionsMade.get(j)[Y_COORDINATE] - 1][selectionsMade.get(j)[X_COORDINATE]] != 0) {
                                canMove = false;
                            }
                        }

                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (i == 0 && numberOfCountersSelected == 3) {
                                if (canMoveUpLeft) {
                                    neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] - 1, selectionsMade.get(i)[X_COORDINATE], Move.MOVE_UP_LEFT, false, 0));
                                }
                            } else {
                                if (canMove) {
                                    neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] - 1, selectionsMade.get(i)[X_COORDINATE], Move.MOVE_UP_RIGHT, false, 0));
                                }
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException aioobe){
                        aioobe.getMessage();
                    }

                    //Add left
                    try {
                        if (gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 1] == 0) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_LEFT, true, 0));
                        } else if (gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 2] <= 0 && gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_LEFT, true, 1));
                        } else if (numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 3] <= 0 && gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 2] == opposition && gameBoard[selectionsMade.get(0)[Y_COORDINATE]][selectionsMade.get(0)[X_COORDINATE] - 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_LEFT, true, 2));
                        }
                    }
                    catch(IndexOutOfBoundsException ioobe){
                        ioobe.getMessage();
                    }

                    //Add right
                    try {
                        if (gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1] == 0) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE], selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_RIGHT, true, 0));
                        } else if (gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 2] <= 0 &&gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE], selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_RIGHT, true, 1));
                        } else if (numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 3] <= 0 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 2] == opposition && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE], selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_RIGHT, true, 2));
                        }
                    }
                    catch(IndexOutOfBoundsException ioobe){
                        ioobe.getMessage();
                    }

                    //Add below
                    boolean canMoveDownRight = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE] + 1][selectionsMade.get(i)[X_COORDINATE] + 1] != 0) {
                                canMoveDownRight = false;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException aioobe){
                        canMoveDownRight = false;
                    }
                    if(canMoveDownRight) {
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, false, 0));
                    }

                    canMove = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE] + 1][selectionsMade.get(i)[X_COORDINATE]] != 0) {
                                canMove = false;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe){
                        canMove = false;
                    }
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 2 && numberOfCountersSelected == 3){
                            if(canMoveDownRight) {
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] + 1, selectionsMade.get(i)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, false, 0));
                            }
                        }
                        else{
                            if(canMove) {
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] + 1, selectionsMade.get(i)[X_COORDINATE], Move.MOVE_DOWN_LEFT, false, 0));
                            }
                        }
                    }

                    break;

                case DOWN_TO_LEFT_DIRECTION:
                    //Check above and in line
                    try {
                        if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE]] == 0) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE], Move.MOVE_UP_RIGHT, true, 0));
                        } else if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 2][selectionsMade.get(0)[X_COORDINATE]] <= 0 && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE]] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE], Move.MOVE_UP_RIGHT, true, 1));
                        } else if (numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 3][selectionsMade.get(0)[X_COORDINATE]] <= 0 && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 2][selectionsMade.get(0)[X_COORDINATE]] == opposition && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE]] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE], Move.MOVE_UP_RIGHT, true, 2));
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException aioobe) {
                        aioobe.getMessage();
                    }

                    //Check right lateral movement
                    boolean canMoveDown = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE] + 1][selectionsMade.get(i)[X_COORDINATE] + 1] != 0) {
                                canMoveDown = false;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException aioobe){
                        canMoveDown = false;
                    }
                    canMove = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE]][selectionsMade.get(i)[X_COORDINATE] + 1] != 0) {
                                canMove = false;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe){
                        canMove = false;
                    }

                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 2 && numberOfCountersSelected == 3){
                            if(canMoveDown){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] + 1, selectionsMade.get(i)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE],selectionsMade.get(i)[X_COORDINATE] + 1, Move.MOVE_RIGHT, false, 0));
                            }
                        }
                    }

                    if (canMoveDown) {
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, false, 0));
                    }

                    //Check below in line
                    try {
                        if (gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]] == 0) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE], Move.MOVE_DOWN_LEFT, true, 0));
                        } else if (gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 2][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]] <= 0 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE], Move.MOVE_DOWN_LEFT, true, 1));
                        } else if (numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 3][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]] <= 0 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 2][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]] == opposition && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE], Move.MOVE_DOWN_LEFT, true, 2));
                        }
                    }
                    catch(IndexOutOfBoundsException ioobe){
                        ioobe.getMessage();
                    }

                    //Check left lateral movement
                    canMove = true;
                    try {
                        for (int j = 0; j < numberOfCountersSelected; j++) {
                            if (gameBoard[selectionsMade.get(j)[Y_COORDINATE]][selectionsMade.get(j)[X_COORDINATE] - 1] != 0) {
                                canMove = false;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe){
                        canMove = false;
                    }
                    boolean canMoveUp = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE] - 1][selectionsMade.get(i)[X_COORDINATE] - 1] != 0) {
                                canMoveUp = false;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe){
                        canMoveUp = false;
                    }
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(numberOfCountersSelected == 3 && i == 0){
                            if(canMoveUp){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] - 1, selectionsMade.get(i)[X_COORDINATE], Move.MOVE_UP_LEFT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] - 1, selectionsMade.get(i)[X_COORDINATE], Move.MOVE_LEFT, false, 0));
                            }
                        }
                    }


                    if(canMoveUp){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_UP_LEFT, false, 0));
                    }

                    break;

                case DOWN_TO_RIGHT_DIRECTION:
                    //Check above in line
                    try {
                        if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE] - 1] == 0) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_UP_LEFT, true, 0));
                        } else if (gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 2][selectionsMade.get(0)[X_COORDINATE] - 2] <= 0 && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE] - 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_UP_LEFT, true, 1));
                        } else if (numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 3][selectionsMade.get(0)[X_COORDINATE] - 3] <= 0 && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 2][selectionsMade.get(0)[X_COORDINATE] - 2] == opposition && gameBoard[selectionsMade.get(0)[Y_COORDINATE] - 1][selectionsMade.get(0)[X_COORDINATE] - 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE] - 1, Move.MOVE_UP_LEFT, true, 2));
                        }
                    }
                    catch(IndexOutOfBoundsException ioobe){
                        ioobe.getMessage();
                    }
                    boolean canMoveUpRight = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE] - 1][selectionsMade.get(i)[X_COORDINATE]] != 0) {
                                canMoveUpRight = false;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe){
                        canMoveUpRight = false;
                    }
                    if(canMoveUpRight){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] - 1, selectionsMade.get(0)[X_COORDINATE], Move.MOVE_UP_RIGHT, false, 0));
                    }

                    canMove = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE]][selectionsMade.get(i)[X_COORDINATE] + 1] != 0) {
                                canMove = false;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe){
                        canMove = false;
                    }
                    //Check the lateral right
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 0 && numberOfCountersSelected == 3){
                            if(canMoveUpRight){
                                neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE], selectionsMade.get(0)[X_COORDINATE] + 1, Move.MOVE_UP_RIGHT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE], selectionsMade.get(i)[X_COORDINATE] + 1, Move.MOVE_RIGHT, false, 0));
                            }
                        }
                    }

                    //Check in line underneath
                    try {
                        if (gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1] == 0) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, true, 0));
                        } else if (gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 2][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 2] <= 0 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, true, 1));
                        } else if (numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 3][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 3] <= 0 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 2][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 2] == opposition && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1][selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1] == opposition) {
                            neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, true, 2));
                        }
                    }
                    catch(IndexOutOfBoundsException ioobe){
                        ioobe.getMessage();
                    }

                    //Check left

                    boolean canMoveDownLeft = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE] + 1][selectionsMade.get(i)[X_COORDINATE]] != 0) {
                                canMoveDownLeft = false;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException aioobe){
                        canMoveUpLeft = false;
                    }

                    canMove = true;
                    try {
                        for (int i = 0; i < numberOfCountersSelected; i++) {
                            if (gameBoard[selectionsMade.get(i)[Y_COORDINATE]][selectionsMade.get(i)[X_COORDINATE] - 1] != 0) {
                                canMove = false;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe){
                        canMove = false;
                    }

                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 0 && numberOfCountersSelected == 3){
                            if(canMoveDownLeft){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE] + 1, selectionsMade.get(i)[X_COORDINATE], Move.MOVE_DOWN_LEFT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[Y_COORDINATE], selectionsMade.get(i)[X_COORDINATE] - 1, Move.MOVE_LEFT, false, 0));
                            }
                        }
                    }

                    if(canMoveDownLeft){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[Y_COORDINATE] + 1, selectionsMade.get(0)[X_COORDINATE], Move.MOVE_DOWN_LEFT, false, 0));
                    }
                    break;
            }
        }

        return neighbours;
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
    public ArrayList<int[]> getSelectionsMade(){
        return selectionsMade;
    }

    class Neighbour{
        private int xCoordinate;
        private int yCoordinate;
        private int movementDirection;
        private boolean isInLine = false;
        private int numberOfCountersBeingPushed;

        Neighbour(int y, int x, int dir, boolean inLine, int countersPushed){
            xCoordinate = x;
            yCoordinate = y;
            movementDirection = dir;
            isInLine = inLine;
            numberOfCountersBeingPushed = countersPushed;
        }

        int[] getCoordinates(){
            return new int[]{xCoordinate, yCoordinate};
        }

        int getNumberOfCountersBeingPushed(){
            return numberOfCountersBeingPushed;
        }

        int getYCoordinate(){
            return yCoordinate;
        }

        int getXCoordinate(){
            return xCoordinate;
        }

        int getMovementDirection(){
            return movementDirection;
        }

        boolean getIsInLine(){
            return isInLine;
        }
    }
}
