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

    //https://github.com/Te4ko/Abalone/blob/master/MiniMax.java
    //Another genuine solution
    private Game game;

    public AI(Game g){
        game = g;
    }

    public AIMove chooseNextMove(GameBoard gameBoard){
        //TODO Somewhere here it makes like 235254 moves in one (not exact value)
        AIMove bestMove = new AIMove(gameBoard, new GridSelections(), new MovementLogic(0, false, Move.NO_MOVEMENT, 0), 0);
        for(Move move: gameBoard.getPossibleMoves(2)){
            int moveScore = checkMove(move.makeMove());

            if(moveScore > bestMove.getScore()){
                bestMove = new AIMove(move.getGameBoard(), move.getGridSelections(), move.getMovementLogic(), moveScore);
            }
        }

        return bestMove;
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
        ArrayList<int[]> aiCounters = new ArrayList<>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
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
            //TODO Check two counters at risk of three
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
