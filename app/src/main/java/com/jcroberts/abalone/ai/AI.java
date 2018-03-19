package com.jcroberts.abalone.ai;

import android.graphics.YuvImage;

import com.jcroberts.abalone.game.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: Joshua Roberts
 */

public class AI {

    private static int MAX_SCORE = 1000;
    private static int WORST_POSSIBLE_MIN_PLAYER_SCORE = 10000000;
    private static int MAX_DEPTH = 2;
    private static int RISK_FACTOR = 10;
    private static int STARTING_GAME_TREE_DEPTH = 2;
    //http://www.cs.cornell.edu/~hn57/pdf/AbaloneFinalReport.pdf
    //This scientific paper legit tells you what to do
    //https://project.dke.maastrichtuniversity.nl/games/files/msc/pcreport.pdf
    //https://github.com/Te4ko/Abalone/blob/master/MiniMax.java
    //Another genuine solution
    private Game game;
    private GameBoard gameBoard;

    public AI(Game g){
        game = g;
        gameBoard = game.getGameBoard();
    }

    public AIMove chooseNextMove(){
        AIMove bestMove = new AIMove(gameBoard, new GridSelections(), new MovementLogic(0, false, Move.NO_MOVEMENT, 0), 0);

        ArrayList<Move> possibleMoves = gameBoard.getPossibleMoves(2);
        gameBoard.resetMemento();
        for(Move maxPlayerMove : possibleMoves){
            gameBoard.makeMove(maxPlayerMove.makeMove());
            //int moveScore = checkMove(gameBoard.getGameBoard());
            int moveScore = assessMoveTree(new GameBoard(gameBoard.getGameBoard()), STARTING_GAME_TREE_DEPTH);
            if(moveScore > bestMove.getScore()){
                bestMove = new AIMove(gameBoard, maxPlayerMove.getGridSelections(), maxPlayerMove.getMovementLogic(), moveScore);
            }
            gameBoard.revertGameBoard();
        }
        gameBoard.printGameBoard();
        System.out.println("Best move is to move in direction " + bestMove.getMovementLogic().getMovementDirection() + " with score " + bestMove.getScore());
        for(int[] selection : bestMove.getGridSelections().getSelectionsMade()){
            System.out.println("Moving " + selection[GridSelections.Y_COORDINATE] + " " + selection[GridSelections.X_COORDINATE]);
        }
        return bestMove;
    }


    private int checkMove(int[][] board){
        ArrayList<int[]> aiCounters = getCountersOfValue(2, board);
        ArrayList<int[]> playerCounters = getCountersOfValue(1, board);
        return calculateClosenessToCentre(aiCounters) + calculateDistanceBetweenEachCounter(aiCounters) * getRiskOfLosingCounter(aiCounters, board);
    }

    private int calculateClosenessToCentre(ArrayList<int[]> counters){
        int closenessToCentre = 0;

        for(int[] counter : counters){
            closenessToCentre += Math.sqrt((counter[GridSelections.X_COORDINATE] - 5) * (counter[GridSelections.X_COORDINATE] - 5) + (counter[GridSelections.Y_COORDINATE] - 5) * (counter[GridSelections.Y_COORDINATE] - 5));
        }

        return MAX_SCORE - closenessToCentre;
    }

    private int calculateDistanceBetweenEachCounter(ArrayList<int[]> aiCounters){
        int distanceApart = 0;

        for(int i = 0; i < aiCounters.size(); i++){
            for(int j = 0; j < aiCounters.size(); j++){
                if(i != j) {
                    distanceApart += Math.sqrt((aiCounters.get(i)[GridSelections.Y_COORDINATE] - aiCounters.get(j)[GridSelections.Y_COORDINATE]) * (aiCounters.get(i)[GridSelections.Y_COORDINATE] - aiCounters.get(j)[GridSelections.Y_COORDINATE]) + (aiCounters.get(i)[GridSelections.X_COORDINATE] - aiCounters.get(j)[GridSelections.X_COORDINATE]) * (aiCounters.get(i)[GridSelections.X_COORDINATE] - aiCounters.get(j)[GridSelections.X_COORDINATE]));
                }
            }
        }

        return MAX_SCORE - distanceApart;
    }

    private ArrayList<int[]> getCountersOfValue(int player, int[][] board){
        ArrayList<int[]> counters = new ArrayList<>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] == player){
                    counters.add(new int[]{i, j});
                }
            }
        }

        return counters;
    }

    private int getRiskOfLosingCounter(ArrayList<int[]> aiCounters, int[][] board){
        int risk = 1;

        int player = board[aiCounters.get(0)[GridSelections.Y_COORDINATE]][aiCounters.get(0)[GridSelections.X_COORDINATE]];
        int opponent;
        if(player == 1){
            opponent = 2;
        }
        else{
            opponent = 1;
        }
        for(int[] aiCounter: aiCounters){
            //Must all be separate if statements so that the counters in the middle row can be checked properly
            if(aiCounter[GridSelections.Y_COORDINATE] == 1){
                if((board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.Y_COORDINATE]] == opponent) || (board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE] + 1] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent)){
                    risk = risk* RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE]] == player && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 3][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 4][aiCounter[GridSelections.X_COORDINATE]] == opponent){
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE] + 1] == player && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 3][aiCounter[GridSelections.X_COORDINATE] + 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 4][aiCounter[GridSelections.X_COORDINATE] + 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
            }
            if(aiCounter[GridSelections.Y_COORDINATE] == 9){
                if((board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE]] == opponent) || (board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE] - 1] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent)) {
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE]] == player && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 3][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 4][aiCounter[GridSelections.X_COORDINATE]] == opponent){
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE] - 1] == player && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 3][aiCounter[GridSelections.X_COORDINATE] - 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 4][aiCounter[GridSelections.X_COORDINATE] - 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
            }
            if(aiCounter[GridSelections.X_COORDINATE] == 1){
                if((board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 1] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent) ||(board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE] + 1] == 1 && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent)) {
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 1] == player && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE] + 1] == player && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 3][aiCounter[GridSelections.X_COORDINATE] + 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 4][aiCounter[GridSelections.X_COORDINATE] + 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
            }
            if(aiCounter[GridSelections.X_COORDINATE] == aiCounter[GridSelections.Y_COORDINATE] - 4){
                if((board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE]] == opponent) || (board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 1] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent)) {
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE]] == player && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 3][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 4][aiCounter[GridSelections.X_COORDINATE]] == opponent){
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 1] == player && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] + 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
            }
            if(aiCounter[GridSelections.X_COORDINATE] == 9){
                if((board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE] - 1] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent) || (board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 1] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent)){
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] - 1][aiCounter[GridSelections.X_COORDINATE] - 1] == player && board[aiCounter[GridSelections.Y_COORDINATE] - 2][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 3][aiCounter[GridSelections.X_COORDINATE] - 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] - 4][aiCounter[GridSelections.X_COORDINATE] - 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 1] == player && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
            }
            if(aiCounter[GridSelections.X_COORDINATE] == aiCounter[GridSelections.Y_COORDINATE] + 4){
                if((board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.X_COORDINATE]] == opponent) || (board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 1] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent)) {
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE] + 1][aiCounter[GridSelections.X_COORDINATE]] == player && board[aiCounter[GridSelections.Y_COORDINATE] + 2][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 3][aiCounter[GridSelections.X_COORDINATE]] == opponent && board[aiCounter[GridSelections.Y_COORDINATE] + 4][aiCounter[GridSelections.X_COORDINATE]] == opponent){
                    risk = risk * RISK_FACTOR;
                }
                else if(board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 1] == player && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 2] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 3] == opponent && board[aiCounter[GridSelections.Y_COORDINATE]][aiCounter[GridSelections.X_COORDINATE] - 4] == opponent){
                    risk = risk * RISK_FACTOR;
                }
            }
        }
        return risk;
    }

    private int assessMoveTree(GameBoard board, int depth){
        if(depth == MAX_DEPTH){
            int bestScore;
            if(depth % 2 == 1){
                bestScore = 0;
            }
            else{
                bestScore = WORST_POSSIBLE_MIN_PLAYER_SCORE;
            }
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
                System.out.println("Score = " + score);
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
}