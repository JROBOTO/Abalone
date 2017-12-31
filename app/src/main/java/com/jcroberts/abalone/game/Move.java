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

    Move(GameBoard gb, GridSelections gs, MovementLogic ml){
        gameBoard = gb;
        gridSelections = gs;
        movementLogic = ml;

        board = gameBoard.getGameBoard();
        selectionsMade = gridSelections.getSelectionsMade();
    }

    GameBoard makeMove(){
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
                    for(int i = movementLogic.getNumberOfCountersBeingPushed(); i > 0; i--){
                        moveOpponentCounter(i, opponent);
                    }
                    break;

                case MOVE_UP_LEFT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed(); i > 0; i--){
                        moveCounter(i, opponent);
                    }
                    break;

                case MOVE_UP_RIGHT:
                    for(int i = movementLogic.getNumberOfCountersBeingPushed(); i > 0; i--){
                        moveCounter(i, opponent);
                    }
                    break;

                case MOVE_RIGHT:
                    for(int i = 0; i < movementLogic.getNumberOfCountersBeingPushed(); i++){
                        moveCounter(i, opponent);
                    }
                    break;

                case MOVE_DOWN_LEFT:
                    for(int i = 0; i < movementLogic.getNumberOfCountersBeingPushed(); i++){
                        moveCounter(i, opponent);
                    }
                    break;

                case MOVE_DOWN_RIGHT:
                    for(int i = 0; i < movementLogic.getNumberOfCountersBeingPushed(); i++){
                        moveCounter(i, opponent);
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
                for(int i = gridSelections.getNumberOfCountersSelected(); i > 0; i--){
                    moveCounter(i, player);
                }
                break;

            case MOVE_DOWN_LEFT:
                for(int i = gridSelections.getNumberOfCountersSelected(); i > 0; i--){
                    moveCounter(i, player);
                }
                break;

            case MOVE_DOWN_RIGHT:
                for(int i = gridSelections.getNumberOfCountersSelected(); i > 0; i--){
                    moveCounter(i, player);
                }
                break;
        }

        resetOffBoardValuesInArray();

        return gameBoard;
    }

    void moveCounter(int selection, int player){

        switch(movementLogic.getMovementDirection()){
            case MOVE_LEFT:
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE] - 1][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = player;

                break;

            case MOVE_RIGHT:
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE] + 1][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = player;

                break;

            case MOVE_UP_LEFT:
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE] - 1][selectionsMade.get(selection)[GridSelections.Y_COORDINATE] - 1] = player;

                break;

            case MOVE_UP_RIGHT:
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE] - 1] = player;

                break;

            case MOVE_DOWN_LEFT:
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE] + 1] = player;

                break;

            case MOVE_DOWN_RIGHT:
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE]][selectionsMade.get(selection)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(selection)[GridSelections.X_COORDINATE] + 1][selectionsMade.get(selection)[GridSelections.Y_COORDINATE] + 1] = player;

                break;
        }
    }

    private void moveOpponentCounter(int count, int opponent){
        switch(movementLogic.getMovementDirection()){
            case MOVE_LEFT:
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] - count][selectionsMade.get(0)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] - (count + 1)][selectionsMade.get(0)[GridSelections.Y_COORDINATE]] = opponent;

                break;

            case MOVE_UP_LEFT:
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] - count][selectionsMade.get(0)[GridSelections.Y_COORDINATE - count]] = 0;
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] - (count + 1)][selectionsMade.get(0)[GridSelections.Y_COORDINATE] - (count + 1)] = opponent;

                break;

            case MOVE_UP_RIGHT:
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE]][selectionsMade.get(0)[GridSelections.Y_COORDINATE - count]] = 0;
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE]][selectionsMade.get(0)[GridSelections.Y_COORDINATE] - (count + 1)] = opponent;

                break;

            case MOVE_RIGHT:
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] + count][selectionsMade.get(0)[GridSelections.Y_COORDINATE]] = 0;
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] + (count + 1)][selectionsMade.get(0)[GridSelections.Y_COORDINATE]] = opponent;

                break;

            case MOVE_DOWN_RIGHT:
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] + count][selectionsMade.get(0)[GridSelections.Y_COORDINATE + count]] = 0;
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE] + (count + 1)][selectionsMade.get(0)[GridSelections.Y_COORDINATE] + (count + 1)] = opponent;

                break;

            case MOVE_DOWN_LEFT:
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE]][selectionsMade.get(0)[GridSelections.Y_COORDINATE + count]] = 0;
                board[selectionsMade.get(0)[GridSelections.X_COORDINATE]][selectionsMade.get(0)[GridSelections.Y_COORDINATE] + (count + 1)] = opponent;

                break;
        }
    }


    private void resetOffBoardValuesInArray(){
        for(int y = 0; y < GameBoard.NUMBER_OF_ROWS; y++){
            for(int x = 0; x < GameBoard.NUMBER_OF_COLUMNS; x++){
                if(x == 0 || x == GameBoard.NUMBER_OF_COLUMNS - 1 || y == 0 || y == GameBoard.NUMBER_OF_ROWS - 1 || x > y + 5 || x < y - 5){
                    board[x][y] = -1;
                }
            }
        }
    }
}
