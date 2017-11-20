package com.jcroberts.abalone.game;

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

    private GameBoard oldGameBoard;
    private GameBoard newGameBoard;
    private GridSelections gridSelections;
    private MovementLogic movementLogic;

    Move(GameBoard gb, GridSelections gs, MovementLogic ml){
        oldGameBoard = gb;
        gridSelections = gs;
        movementLogic = ml;

        makeMove();
    }

    GameBoard makeMove(){
        newGameBoard = oldGameBoard;

        for(int i = 0; i < gridSelections.getNumberOfCountersSelected(); i++){
            moveCounter(i);
        }

        return newGameBoard;
    }

    void moveCounter(int selection){
        cell.setValue(0);

        switch(movementLogic.getMovementDirection()){
            case(MOVE_LEFT):
                gameBoard.getGameBoard()[cell.getXCoordinate()][cell.getYCoordinate() - 1].setValue(movementLogic.player);

                break;
        }
    }
}
