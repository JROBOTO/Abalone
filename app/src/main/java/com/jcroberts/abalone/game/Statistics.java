package com.jcroberts.abalone.game;

import java.io.Serializable;

/**
 * Created by jcrob on 09/04/2018.
 */

public class Statistics implements Serializable {
    private int numberOfMovesMade;
    private int[] numberOfMovesPushingForPlayer = new int[2];
    private int[] numberOfCountersTaken = new int[2];
    private int[] numberOfCountersPushed = new int[2];
    private int winningPlayer;

    Statistics(){
        numberOfMovesMade = 0;
        numberOfMovesPushingForPlayer[0] = 0;
        numberOfMovesPushingForPlayer[1] = 0;
        numberOfCountersTaken[0] = 0;
        numberOfCountersTaken[1] = 0;
        numberOfCountersPushed[0] = 0;
        numberOfCountersPushed[1] = 0;
        winningPlayer = 0;
    }

    void update(int player, int numberOfCountersPushedThisTurn, boolean hasTakenACounter){
        numberOfMovesMade++;
        numberOfCountersPushed[player - 1] += numberOfCountersPushedThisTurn;
        if(numberOfCountersPushedThisTurn > 0){
            numberOfMovesPushingForPlayer[player - 1]++;
            if(hasTakenACounter){
                numberOfCountersTaken[player - 1]++;
                if(numberOfCountersTaken[player - 1] == 6){
                    winningPlayer = player;
                }
            }
        }
    }

    public int getWinningPlayer(){
        return winningPlayer;
    }
    public int getNumberOfMovesMade(){
        return numberOfMovesMade;
    }
    public int[] getNumberOfCountersTaken(){
        return numberOfCountersTaken;
    }
    public int[] getNumberOfMovesPushingForPlayer(){
        return numberOfMovesPushingForPlayer;
    }
    public int[] getNumberOfCountersPushed(){
        return numberOfCountersPushed;
    }
}
