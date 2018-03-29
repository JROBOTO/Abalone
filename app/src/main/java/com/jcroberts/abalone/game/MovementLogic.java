package com.jcroberts.abalone.game;

import java.io.Serializable;

/**
 * Small class containing all the movement logic for each turn
 * Author: Joshua Roberts
 */

public class MovementLogic implements Serializable{

    private boolean movementIsLegal;
    private int movementDirection;
    private int player;
    private int numberOfCountersBeingPushed;

    /**
     * Constructor
     * @param movementLegal If the movement is legal
     * @param moveDir An int to describe the direction of movement. -1 if movement is not legal
     */
    public MovementLogic(int p, boolean movementLegal, int moveDir, int countersPushed){
        player = p;
        movementIsLegal = movementLegal;
        movementDirection = moveDir;
        numberOfCountersBeingPushed = countersPushed;
    }

    /**
     * @return The direction of movement as an int
     */
    public int getMovementDirection(){
        return movementDirection;
    }

    public boolean getIsMovementLegal(){
        return movementIsLegal;
    }

    int getPlayer(){
        return player;
    }

    public int getNumberOfCountersBeingPushed(){
        return numberOfCountersBeingPushed;
    }

}
