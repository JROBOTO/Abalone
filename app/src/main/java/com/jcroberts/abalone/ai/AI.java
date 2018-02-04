package com.jcroberts.abalone.ai;

import com.jcroberts.abalone.game.*;

import java.util.ArrayList;

/**
 * Author: Joshua Roberts
 */

public class AI {
    public static int MAX_SCORE = 1000;
    public static int CUT_OFF_POINT = 4;
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

    private int checkMove(int[][] board){
        ArrayList<int[]> aiCounters = getAICounters(board);
        if(isAtRiskOfLosingCounter(aiCounters, board)){
            return 0;
        }
        else {
            return calculateClosenessToCentre(aiCounters) + calculateDistanceBetweenEachCounter(aiCounters);
        }
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

    private ArrayList<int[]> getAICounters(int[][] board){
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

    private boolean isAtRiskOfLosingCounter(ArrayList<int[]> aiCounters, int[][] board){
        boolean isAtRisk = false;

        for(int[] aiCounter: aiCounters){
            //Must all be separate if statements so that the counters in the middle row can be checked properly
            if(aiCounter[GridSelections.Y_COORDINATE] == 1 && (board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE]] == 1 || board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE] + 1] == 1)){
                isAtRisk = true;
            }
            if(aiCounter[GridSelections.Y_COORDINATE] == 9 && (board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE]] == 1 || board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE] - 1] == 1)){
                isAtRisk = true;
            }
            if(aiCounter[GridSelections.X_COORDINATE] == 1 && (board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 1] == 1 || board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE] + 1] == 1)){
                isAtRisk = true;
            }
            if(aiCounter[GridSelections.X_COORDINATE] == aiCounter[GridSelections.Y_COORDINATE] - 4 && (board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE]] == 1 || board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 1] == 1)){
                isAtRisk = true;
            }
            if(aiCounter[GridSelections.X_COORDINATE] == 9 && (board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE] - 1] == 1 || board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 1] == 1)){
                isAtRisk = true;
            }
            if(aiCounter[GridSelections.X_COORDINATE] == aiCounter[GridSelections.Y_COORDINATE] + 4 && (board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE]] == 1 || board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 1] == 1)){
                isAtRisk = true;
            }
        }
        // aiCounter[1] == 9 || aiCounter[1] == aiCounter[0] + 4){
        return isAtRisk;
    }
}
