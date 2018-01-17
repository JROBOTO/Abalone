package com.jcroberts.abalone.ai;

import com.jcroberts.abalone.game.*;

import java.util.ArrayList;

/**
 * Author: Joshua Roberts
 */

public class AI {
    public static int MAX_SCORE = 1000;
    //http://www.cs.cornell.edu/~hn57/pdf/AbaloneFinalReport.pdf
    //This scientific paper legit tells you what to do

    //https://stackoverflow.com/questions/23225540/java-minimax-abalone-implementation
    //Legit solution

    //https://github.com/Te4ko/Abalone/blob/master/MiniMax.java
    //Another genuine solution
    private Game game;

    private int manhattanDistanceClosenessToCentre;
    private int manhattanDistanceBetweenEachCounter;
    private int riskOfLosingCounter;
    private int chanceToTakeCounter;

    public AI(Game g){
        game = g;
    }

    public void chooseNextMove(int[][] board){


    }

    private void checkMove(int[][] board){
        ArrayList<int[]> aiCounters = getAICounters(board);

        int score = calculateClosenessToCentre(aiCounters) + calculateDistanceBetweenEachCounter(aiCounters);
    }

    private int calculateClosenessToCentre(ArrayList<int[]> aiCounters){
        int closenessToCentre = 0;

        for(int i = 0; i < aiCounters.size(); i++){
            closenessToCentre += Math.abs(aiCounters.get(i)[0] - 5) + Math.abs(aiCounters.get(i)[1] - 5);
        }

        return MAX_SCORE - closenessToCentre;
    }

    private int calculateDistanceBetweenEachCounter(ArrayList<int[]> aiCounters){
        int distanceApart = 0;

        for(int i = 0; i < aiCounters.size(); i++){
            for(int j = 0; j < aiCounters.size(); j++){
                if(i != j) {
                    distanceApart += Math.abs(aiCounters.get(i)[0] - aiCounters.get(j)[1]) + Math.abs(aiCounters.get(i)[1] - aiCounters.get(j)[1]);
                }
            }
        }

        return MAX_SCORE - distanceApart;
    }

    ArrayList<int[]> getAICounters(int[][] board){
        ArrayList<int[]> aiCounters = new ArrayList<int[]>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; i++){
                if(board[i][j] == 2){
                    aiCounters.add(new int[]{i, j});
                }
            }
        }

        return aiCounters;
    }
}
