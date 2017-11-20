package com.jcroberts.abalone.game;

/**
 * Small class containing all the movement logic for each turn
 * Author: Joshua Roberts
 */

class MovementLogic {

    private boolean movementIsLegal;
    private boolean movementIsFollowingLine;
    private boolean isPushing;

    private int movementDirection;
    private int player;

    /**
     * Constructor
     * @param movementLegal If the movement is legal
     * @param moveDir An int to describe the direction of movement. -1 if movement is not legal
     */
    MovementLogic(int p, boolean movementLegal, int moveDir){
        player = p;
        movementIsLegal = movementLegal;
        movementDirection = moveDir;
    }

    MovementLogic(int p){
        player = p;
    }

    /**
     * @return The direction of movement as an int
     */
    int getMovementDirection(){
        return movementDirection;
    }

    void setMovementDirection(int movdir){
        movementDirection = movdir;
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

    void setMovementIsLegal(boolean legal){
        movementIsLegal = legal;
    }

}
