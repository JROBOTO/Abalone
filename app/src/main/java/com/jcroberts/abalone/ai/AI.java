package com.jcroberts.abalone.ai;

import com.jcroberts.abalone.game.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: Joshua Roberts
 */

public class AI {

    private static int MAX_SCORE = 10000;
    private static int MAX_DEPTH = 2;
    private static int STARTING_GAME_TREE_DEPTH = 2;
    //http://www.cs.cornell.edu/~hn57/pdf/AbaloneFinalReport.pdf
    //This scientific paper legit tells you what to do

    //https://github.com/Te4ko/Abalone/blob/master/MiniMax.java
    //Another genuine solution
    private Game game;
    private GameBoard gameBoard;

    public AI(Game g){
        game = g;
        gameBoard = game.getGameBoard();
    }

    public AIMove chooseNextMove(){
        //TODO Somewhere here it makes like 235254 moves in one (not exact value)
        AIMove bestMove = new AIMove(gameBoard, new GridSelections(), new MovementLogic(0, false, Move.NO_MOVEMENT, 0), 0);

        ArrayList<Move> possibleMoves = gameBoard.getPossibleMoves(2);
        for(Move maxPlayerMove : possibleMoves){
            int[][] gameBoardArrayCopy = Arrays.copyOf(gameBoard.getGameBoard(), gameBoard.getGameBoard().length);
            GameBoard depth1GameBoard = new GameBoard(gameBoardArrayCopy);

//            System.out.println("--------------------------------");
//            System.out.println("-------------------------------");
//            for(int i = 0; i < gameBoard.getGameBoard().length; i++){
//                for(int j = 0; j < gameBoard.getGameBoard()[i].length; j++){
//                    System.out.print(gameBoard.getGameBoard()[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("-------------------------------");
//            System.out.println("Becomes");
//            System.out.println("-------------------------------");
            depth1GameBoard.makeMove(maxPlayerMove.makeMove());
            //int moveScore = assessMoveTree(depth1GameBoard, STARTING_GAME_TREE_DEPTH);
            int moveScore = checkMove(depth1GameBoard.getGameBoard());

            if(moveScore > bestMove.getScore()){
                bestMove = new AIMove(gameBoard, maxPlayerMove.getGridSelections(), maxPlayerMove.getMovementLogic(), moveScore);
            }

//            for(int i = 0; i < depth1GameBoard.getGameBoard().length; i++){
//                for(int j = 0; j < depth1GameBoard.getGameBoard()[i].length; j++){
//                    System.out.print(depth1GameBoard.getGameBoard()[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("-------------------------------");
//            System.out.println("--------------------------------");
//            System.out.println("---------------------------------");

            depth1GameBoard.revertGameBoard();
        }

        return bestMove;
    }

    private int assessMoveTree(GameBoard board, int depth){
        if(depth == MAX_DEPTH){
            int bestScore = 0;
            for(Move nextMove : board.getPossibleMoves(depth % 2)){
                int score = checkMove(nextMove.makeMove());

                if(depth % 2 == 1){
                    if(score > bestScore){
                        bestScore = score;
                    }
                }
                else{
                    if(score < bestScore){
                        bestScore = score;
                    }
                }

                board.revertGameBoard();
            }

            return bestScore;
        }
        else{
            int bestScore = 0;

            for(Move nextMove : board.getPossibleMoves(depth % 2 + 1)){
                board.makeMove(nextMove.makeMove());
                int score = assessMoveTree(new GameBoard(board.getGameBoard()), depth + 1);

                if(depth % 2 + 1 == 1){
                    if(score > bestScore){
                        bestScore = score;
                    }
                }
                else{
                    if(score < bestScore){
                        bestScore = score;
                    }
                }
                board.revertGameBoard();
            }

            return bestScore;
        }
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
        return isAtRisk;
    }
}