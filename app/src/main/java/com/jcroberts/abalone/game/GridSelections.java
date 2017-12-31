package com.jcroberts.abalone.game;

import java.util.ArrayList;

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

    /**
     * I'm not 100% sure why but the x and y coordinates were reversed
     */
    static final int X_COORDINATE = 1;
    static final int Y_COORDINATE = 0;

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

    ArrayList<Neighbour> getLegalNeighbourCellsOfSelectionsAsXCoordinateYCoordinateAndMovementDirection(int[][] gameBoard){
        ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();
        boolean canMove;
        if(numberOfCountersSelected == 1){
            if(gameBoard[selectionsMade.get(0)[X_COORDINATE] - 1][selectionsMade.get(0)[Y_COORDINATE] - 1] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, false, 0));
            }
            if(gameBoard[selectionsMade.get(0)[X_COORDINATE]][selectionsMade.get(0)[Y_COORDINATE] - 1] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE], selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_RIGHT, false, 0));
            }
            if(gameBoard[selectionsMade.get(0)[X_COORDINATE] - 1][selectionsMade.get(0)[Y_COORDINATE]] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE], Move.MOVE_LEFT, false, 0));
            }
            if(gameBoard[selectionsMade.get(0)[X_COORDINATE] + 1][selectionsMade.get(0)[Y_COORDINATE]] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] + 1, selectionsMade.get(0)[Y_COORDINATE], Move.MOVE_RIGHT, false, 0));
            }
            if(gameBoard[selectionsMade.get(0)[X_COORDINATE]][selectionsMade.get(0)[Y_COORDINATE] + 1] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE], selectionsMade.get(0)[Y_COORDINATE] + 1, Move.MOVE_DOWN_LEFT, false, 0));
            }
            if(gameBoard[selectionsMade.get(0)[X_COORDINATE] + 1][selectionsMade.get(0)[Y_COORDINATE]] == 0) {
                neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] + 1, selectionsMade.get(0)[Y_COORDINATE], Move.MOVE_DOWN_RIGHT, false, 0));
            }
        }
        else{
            switch(direction){
                case LEFT_TO_RIGHT_DIRECTION:
                    //Add above
                    boolean canMoveUpLeft = true;
                    for(int i = 0; i < numberOfCountersSelected; i++) {
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE] - 1][selectionsMade.get(i)[Y_COORDINATE] - 1] != 0){
                            canMoveUpLeft = false;
                        }
                    }
                    if(canMoveUpLeft) {
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, false, 0));
                    }

                    canMove = true;
                    for(int j = 0; j < numberOfCountersSelected; j++) {
                        if(gameBoard[selectionsMade.get(j)[X_COORDINATE]][selectionsMade.get(j)[Y_COORDINATE] - 1] != 0){
                            canMove = false;
                        }
                    }

                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 0 && numberOfCountersSelected == 3){
                            if(canMoveUpLeft) {
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE], selectionsMade.get(i)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, false, 0));
                            }
                        }
                        else{
                            if(canMove) {
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE], selectionsMade.get(i)[Y_COORDINATE] - 1, Move.MOVE_UP_RIGHT, false, 0));
                            }
                        }
                    }

                    //Add left
                    if(gameBoard[selectionsMade.get(0)[X_COORDINATE - 1]][selectionsMade.get(0)[Y_COORDINATE]] == 0) {
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE], Move.MOVE_LEFT, true, 0));
                    }
                    else if(gameBoard[selectionsMade.get(0)[X_COORDINATE - 2]][selectionsMade.get(0)[Y_COORDINATE]] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE], Move.MOVE_LEFT, true, 1));
                    }
                    else if(numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(0)[X_COORDINATE - 3]][selectionsMade.get(0)[Y_COORDINATE]] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE], Move.MOVE_LEFT, true, 2));
                    }

                    //Add right
                    //TODO this creates ArrayIndexOutOfBoundsException for some reason
                    if(gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE + 1]][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]] == 0) {
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE], Move.MOVE_RIGHT, true, 0));
                    }
                    else if(gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 2][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE], Move.MOVE_RIGHT, true, 1));
                    }
                    else if(numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 3][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE]] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE], Move.MOVE_RIGHT, true, 2));
                    }

                    //Add below
                    boolean canMoveDownRight = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE] + 1][selectionsMade.get(i)[Y_COORDINATE] + 1] != 0){
                            canMoveDownRight = false;
                        }
                    }
                    if(canMoveDownRight) {
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, false, 0));
                    }

                    canMove = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE]][selectionsMade.get(i)[Y_COORDINATE] + 1] != 0){
                            canMove = false;
                        }
                    }
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 2 && numberOfCountersSelected == 3){
                            if(canMoveDownRight) {
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE], selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_DOWN_RIGHT, false, 0));
                            }
                        }
                        else{
                            if(canMove) {
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE], selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_DOWN_LEFT, false, 0));
                            }
                        }
                    }

                    break;

                case DOWN_TO_LEFT_DIRECTION:
                    //Check above and in line
                    if(gameBoard[selectionsMade.get(0)[X_COORDINATE]][selectionsMade.get(0)[Y_COORDINATE] - 1] == 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE], selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_RIGHT, true, 0));
                    }
                    else if(gameBoard[selectionsMade.get(0)[X_COORDINATE]][selectionsMade.get(0)[Y_COORDINATE] - 2] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE], selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_RIGHT, true, 1));
                    }
                    else if(numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(0)[X_COORDINATE]][selectionsMade.get(0)[Y_COORDINATE] - 3] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE], selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_RIGHT, true, 2));
                    }

                    //Check right lateral movement
                    boolean canMoveDown = true;
                    for(int i = 0; i <  numberOfCountersSelected; i++){
                        if (gameBoard[selectionsMade.get(i)[X_COORDINATE] + 1][selectionsMade.get(i)[Y_COORDINATE] + 1] != 0) {
                            canMoveDown = false;
                        }
                    }
                    canMove = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE] + 1][selectionsMade.get(i)[Y_COORDINATE]] != 0){
                            canMove = false;
                        }
                    }

                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 2 && numberOfCountersSelected == 3){
                            if(canMoveDown){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE] + 1, selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_DOWN_RIGHT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE] + 1,selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_RIGHT, false, 0));
                            }
                        }
                    }

                    if (canMoveDown) {
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, false, 0));
                    }

                    //Check in line
                    if(gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] - 1] == 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE], selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_LEFT, true, 0));
                    }
                    else if(gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] - 2] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE], selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_LEFT, true, 1));
                    }
                    else if(numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE]][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 3] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE], selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_LEFT, true, 2));
                    }

                    //Check left lateral movement
                    canMove = true;
                    for(int j = 0; j < numberOfCountersSelected; j++){
                        if(gameBoard[selectionsMade.get(j)[X_COORDINATE] - 1][selectionsMade.get(j)[Y_COORDINATE]] != 0){
                            canMove = false;
                        }
                    }

                    boolean canMoveUp = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE] - 1][selectionsMade.get(i)[Y_COORDINATE] - 1] != 0){
                            canMoveUp = false;
                        }
                    }
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(numberOfCountersSelected == 3 && i == 0){
                            if(canMoveUp){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE] - 1, selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_UP_LEFT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE] - 1, selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_LEFT, false, 0));
                            }
                        }
                    }


                    if(canMoveUp){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, false, 0));
                    }

                    break;

                case DOWN_TO_RIGHT_DIRECTION:
                    //Check above in line
                    if(gameBoard[selectionsMade.get(0)[X_COORDINATE] - 1][selectionsMade.get(0)[Y_COORDINATE] - 1] == 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1,selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, true, 0));
                    }
                    else if(gameBoard[selectionsMade.get(0)[X_COORDINATE] - 2][selectionsMade.get(0)[Y_COORDINATE] - 2] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1,selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, true, 1));
                    }
                    else if(numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(0)[X_COORDINATE] - 3][selectionsMade.get(0)[Y_COORDINATE] - 3] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1,selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, true, 2));
                    }

                    boolean canMoveUpRight = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE]][selectionsMade.get(i)[Y_COORDINATE] - 1] != 0){
                            canMoveUpRight = false;
                        }
                    }
                    if(canMoveUpRight){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE], selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_RIGHT, false, 0));
                    }

                    canMove = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE] + 1][selectionsMade.get(i)[Y_COORDINATE]] != 0){
                            canMove = false;
                        }
                    }
                    //Check the lateral right
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 0 && numberOfCountersSelected == 3){
                            if(canMoveUpRight){
                                neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] + 1, selectionsMade.get(0)[Y_COORDINATE], Move.MOVE_UP_RIGHT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE] + 1, selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_RIGHT, false, 0));
                            }
                        }
                    }

                    //Check in line underneath
                    if(gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1] == 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, true, 0));
                    }
                    else if(gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 2][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 2] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, true, 1));
                    }
                    else if(numberOfCountersSelected == 3 && gameBoard[selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 3][selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 3] <= 0){
                        neighbours.add(new Neighbour(selectionsMade.get(numberOfCountersSelected - 1)[X_COORDINATE] + 1, selectionsMade.get(numberOfCountersSelected - 1)[Y_COORDINATE] + 1, Move.MOVE_DOWN_RIGHT, true, 2));
                    }

                    //Check left
                    canMoveUpLeft = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE] - 1][selectionsMade.get(i)[Y_COORDINATE] - 1] != 0){
                            canMoveUpLeft = false;
                        }
                    }

                    canMove = true;
                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(gameBoard[selectionsMade.get(i)[X_COORDINATE] - 1][selectionsMade.get(i)[Y_COORDINATE]] != 0){
                            canMove = false;
                        }
                    }

                    for(int i = 0; i < numberOfCountersSelected; i++){
                        if(i == 0 && numberOfCountersSelected == 3){
                            if(canMoveUpLeft){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE] - 1, selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_UP_LEFT, false, 0));
                            }
                        }
                        else{
                            if(canMove){
                                neighbours.add(new Neighbour(selectionsMade.get(i)[X_COORDINATE] - 1, selectionsMade.get(i)[Y_COORDINATE], Move.MOVE_LEFT, false, 0));
                            }
                        }
                    }

                    if(canMoveUpLeft){
                        neighbours.add(new Neighbour(selectionsMade.get(0)[X_COORDINATE] - 1, selectionsMade.get(0)[Y_COORDINATE] - 1, Move.MOVE_UP_LEFT, false, 0));
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

        Neighbour(int x, int y, int dir, boolean inLine, int countersPushed){
            xCoordinate = x;
            yCoordinate = y;
            movementDirection = dir;
            isInLine = inLine;
        }

        boolean isAtLocation(int[] cell){
            if(this.xCoordinate == cell[X_COORDINATE] && this.yCoordinate == cell[Y_COORDINATE]){
                return true;
            }
            else{
                return false;
            }
        }

        void setNumberOfCountersPushing(int numberPushed){
            numberOfCountersBeingPushed = numberPushed;
        }

        int getNumberOfCountersBeingPushed(){
            return numberOfCountersBeingPushed;
        }

        int getXCoordinate(){
            return xCoordinate;
        }

        int getYCoordinate(){
            return yCoordinate;
        }

        int getMovementDirection(){
            return movementDirection;
        }

        boolean getIsInLine(){
            return isInLine;
        }
    }
}
