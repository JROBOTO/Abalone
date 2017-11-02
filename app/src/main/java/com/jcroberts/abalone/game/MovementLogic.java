package com.jcroberts.abalone.game;

/**
 * Small class containing all the movement logic for each turn
 * Author: Joshua Roberts
 */

class MovementLogic {

    private final int MOVEMENT_LEFT = 0;
    private final int MOVEMENT_RIGHT = 1;
    private final int MOVEMENT_UP_LEFT = 2;
    private final int MOVEMENT_UP_RIGHT = 3;
    private final int MOVEMENT_DOWN_LEFT = 4;
    private final int MOVEMENT_DOWN_RIGHT = 5;

    private boolean movementIsLegal;
    private boolean movementIsFollowingLine;
    private boolean isPushing;

    private int movementDirection;

    /**
     * Constructor
     * @param movementLegal If the movement is legal
     * @param moveDir An int to describe the direction of movement. -1 if movement is not legal
     */
    MovementLogic(boolean movementLegal, int moveDir){
        movementIsLegal = movementLegal;
        movementDirection = moveDir;
    }

    /**
     * @return The direction of movement as an int
     */
    int getMovementDirection(){
        return movementDirection;
    }

    /**
     * Set whether or not the movement selection was in the same line as the counter selections
     * @param isFollowingLine If the movement selection is in the same line as the counter selections
     */
    void setMovementIsFollowingLine(boolean isFollowingLine){
        movementIsFollowingLine = isFollowingLine;
    }

    /**
     * Set whether or not the counters are attempting to push an opponents counter
     * @param pushing If the movement involves pushing an opposing counter
     */
    void setIsPushing(boolean pushing){
        isPushing = pushing;
    }

    /**
     * @return If the movement is against the line of selected counters as a boolean
     */
    boolean getMovementIsFollowingLine(){
        return movementIsFollowingLine;
    }

    /**
     * @return If the movement is in an attempt to push
     */
    boolean getIsPushing(){
        return isPushing;
    }

    boolean isMovementLegal(){
        return movementIsLegal;
    }

}
