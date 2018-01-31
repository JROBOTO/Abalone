package com.jcroberts.abalone.game;

import java.util.ArrayList;

/**
 * Author: Joshua Roberts
 */

class Move {

    static final int NO_MOVEMENT = -1;
    static final int MOVE_LEFT = 0;
    static final int MOVE_RIGHT = 1;
    static final int MOVE_UP_LEFT = 2;
    static final int MOVE_UP_RIGHT = 3;
    static final int MOVE_DOWN_LEFT = 4;
    static final int MOVE_DOWN_RIGHT = 5;

    private GameBoard gameBoard;
    private GridSelections gridSelections;
    private MovementLogic movementLogic;
    private int[][] board;
    private ArrayList<int[]> selectionsMade;

    private boolean hasTakenACounter = false;

    Move(GameBoard gb, GridSelections gs, MovementLogic ml){
        gameBoard = gb;
        gridSelections = gs;
        movementLogic = ml;

        board = gameBoard.getGameBoard();
        selectionsMade = gridSelections.getSelectionsMade();
    }

    int[][] makeMove(){
        //The direction determines which order the counters must move in or it won't work
        int player = movementLogic.getPlayer();
        int opponent;

        if(player == 1){
            opponent = 2;
        }
        else{
            opponent = 1;
        }

        if(movementLogic.getNumberOfCountersBeingPushed() > 0){
            switch(movementLogic.getMovementDirection()){
                case MOVE_LEFT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed() - 1; i >= 0; i--){
                        moveOpponentCounter(i + 1, opponent, gridSelections.getNumberOfCountersSelected() - 1);
                    }
                    break;

                case MOVE_UP_LEFT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed() - 1; i >= 0; i--){
                        moveOpponentCounter(i + 1, opponent, gridSelections.getNumberOfCountersSelected() - 1);
                    }
                    break;

                case MOVE_UP_RIGHT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed() - 1; i >= 0; i--){
                        moveOpponentCounter(i + 1, opponent, gridSelections.getNumberOfCountersSelected() - 1);
                    }
                    break;

                case MOVE_RIGHT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed() - 1; i >= 0; i--){
                        moveOpponentCounter(i + 1, opponent, gridSelections.getNumberOfCountersSelected() - 1);
                    }
                    break;

                case MOVE_DOWN_LEFT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed() - 1; i >= 0; i--){
                        moveOpponentCounter(i + 1, opponent, gridSelections.getNumberOfCountersSelected() - 1);
                    }
                    break;

                case MOVE_DOWN_RIGHT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed() - 1; i >= 0; i--){
                        moveOpponentCounter(i + 1, opponent, gridSelections.getNumberOfCountersSelected() - 1);
                    }
                    break;
            }
        }

        switch(movementLogic.getMovementDirection()){
            case MOVE_LEFT:
                for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
                    moveCounter(i, player);
                }
                break;

            case MOVE_UP_LEFT:
                for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
                    moveCounter(i, player);
                }
                break;

            case MOVE_UP_RIGHT:
                for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
                    moveCounter(i, player);
                }
                break;

            case MOVE_RIGHT:
                for(int i = gridSelections.getNumberOfCountersSelected() - 1; i >= 0; i--){
                    moveCounter(i, player);
                }
                break;

            case MOVE_DOWN_LEFT:
                for(int i = gridSelections.getNumberOfCountersSelected() - 1; i >= 0; i--){
                    moveCounter(i, player);
                }
                break;

            case MOVE_DOWN_RIGHT:
                for(int i = gridSelections.getNumberOfCountersSelected() - 1; i >= 0; i--){
                    moveCounter(i, player);
                }
                break;
        }

        resetOffBoardValuesInArray();

        return board;
    }

    void moveCounter(int selection, int player){

        switch(movementLogic.getMovementDirection()){
            case MOVE_LEFT:
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE] - 1] = player;

                break;

            case MOVE_RIGHT:
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE] + 1] = player;

                break;

            case MOVE_UP_LEFT:
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE] - 1][selectionsMade.get(selection)[GridSelections.X_COORDINATE] - 1] = player;

                break;

            case MOVE_UP_RIGHT:
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE] - 1][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = player;

                break;

            case MOVE_DOWN_LEFT:
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE] + 1][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = player;

                break;

            case MOVE_DOWN_RIGHT:
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE]][selectionsMade.get(selection)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.Y_COORDINATE] + 1][selectionsMade.get(selection)[GridSelections.X_COORDINATE] + 1] = player;

                break;
        }
    }


    private void moveOpponentCounter(int count, int opponent, int numberOfSelectionsMade){
        System.out.println(selectionsMade.get(0)[GridSelections.X_COORDINATE] + ", " + selectionsMade.get(0)[GridSelections.Y_COORDINATE]);
        switch(movementLogic.getMovementDirection()){

            case MOVE_LEFT:
                board[selectionsMade.get(0)[GridSelections.Y_COORDINATE]][selectionsMade.get(0)[GridSelections.X_COORDINATE] - count] = 0;
                board[selectionsMade.get(0)[GridSelections.Y_COORDINATE]][selectionsMade.get(0)[GridSelections.X_COORDINATE] - (count + 1)] = opponent;

                break;

            case MOVE_UP_LEFT:
                board[selectionsMade.get(0)[GridSelections.Y_COORDINATE] - count][selectionsMade.get(0)[GridSelections.X_COORDINATE] - count] = 0;
                board[selectionsMade.get(0)[GridSelections.Y_COORDINATE] - (count + 1)][selectionsMade.get(0)[GridSelections.X_COORDINATE] - (count + 1)] = opponent;

                break;

            case MOVE_UP_RIGHT:
                board[selectionsMade.get(0)[GridSelections.Y_COORDINATE] - count][selectionsMade.get(0)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(0)[GridSelections.Y_COORDINATE] - (count + 1)][selectionsMade.get(0)[GridSelections.X_COORDINATE]] = opponent;

                break;

            case MOVE_RIGHT:
                board[selectionsMade.get(numberOfSelectionsMade)[GridSelections.Y_COORDINATE]][selectionsMade.get(numberOfSelectionsMade)[GridSelections.X_COORDINATE] + count] = 0;
                board[selectionsMade.get(numberOfSelectionsMade)[GridSelections.Y_COORDINATE]][selectionsMade.get(numberOfSelectionsMade)[GridSelections.X_COORDINATE] + (count + 1)] = opponent;

                break;

            case MOVE_DOWN_RIGHT:
                board[selectionsMade.get(numberOfSelectionsMade)[GridSelections.Y_COORDINATE] + count][selectionsMade.get(numberOfSelectionsMade)[GridSelections.X_COORDINATE] + count] = 0;
                board[selectionsMade.get(numberOfSelectionsMade)[GridSelections.Y_COORDINATE] + (count + 1)][selectionsMade.get(numberOfSelectionsMade)[GridSelections.X_COORDINATE] + (count + 1)] = opponent;

                break;

            case MOVE_DOWN_LEFT:
                board[selectionsMade.get(numberOfSelectionsMade)[GridSelections.Y_COORDINATE] + count][selectionsMade.get(numberOfSelectionsMade)[GridSelections.X_COORDINATE]] = 0;
                board[selectionsMade.get(numberOfSelectionsMade)[GridSelections.Y_COORDINATE] + (count + 1)][selectionsMade.get(numberOfSelectionsMade)[GridSelections.X_COORDINATE]] = opponent;

                break;
        }
    }


    private void resetOffBoardValuesInArray(){
        hasTakenACounter = false;

        for (int y = 0; y < GameBoard.NUMBER_OF_ROWS - 1; y++) {
            for (int x = 0; x < GameBoard.NUMBER_OF_COLUMNS - 1; x++) {
                if (x == 0 || x == GameBoard.NUMBER_OF_COLUMNS - 1 || y == 0 || y == GameBoard.NUMBER_OF_ROWS - 1 || x > y + 5 || x < y - 5) {
                    try {
                        if(board[x][y] > 0){
                            hasTakenACounter = true;
                        }
                        board[x][y] = -1;

                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        System.out.println(x + " " + y);
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    boolean getHasTakenACounter(){
        return hasTakenACounter;
    }
}
