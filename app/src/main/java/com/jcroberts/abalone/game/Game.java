package com.jcroberts.abalone.game;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * All the game logic for the abalone game app. This class is to be extended for the purpose of
 * each game type
 * Author: Joshua Roberts
 */

public class Game implements Serializable{
    public static final int MAX_NUMBER_OF_OPPONENTS = 1;
    public static final int MIN_NUMBER_OF_OPPONENTS = 1;

    public static final int SINGLE_PLAYER_GAME = 0;
    public static final int LOCAL_MULTIPLAYER_GAME = 1;
    public static final int NETWORKED_MULTIPLAYER_GAME = 2;

    private GameBoard gameBoard;
    private int numberOfPlayer1CountersTaken;
    private int numberOfPlayer2CountersTaken;
    private int playerToTakeFirstTurn;
    private int currentPlayer;

    private boolean gameEnded;

    private Random random;

    private GridSelections gridSelections;
    private SelectionChecker selectionChecker;
    private MovementLogic movementLogic;
    private Statistics statistics;

    private int gameType;

    private String player1Name;
    private String player1ProfilePictureUrl;

    private String player2Name;
    private String player2ProfilePictureUrl;

//    private GoogleSignInAccount startingAccount;
//    private GoogleSignInAccount otherAccount;

    /**
     * Initialize the game
     */
    public Game(){
//        startingAccount = a;
        //Initialize game logic
        gameEnded = false;
        numberOfPlayer1CountersTaken = 0;
        numberOfPlayer2CountersTaken = 0;

        gameBoard = new GameBoard();
        statistics = new Statistics();
        gridSelections = new GridSelections();

        selectionChecker = new SelectionChecker();
        random = new Random();
        setPlayerToTakeFirstTurn();
    }

    /**
     * Test if the game has ended
     */
    private void runTerminalTest(){
        if(numberOfPlayer1CountersTaken == 6 || numberOfPlayer2CountersTaken == 6){
            gameEnded = true;
        }
    }

    /**
     * Check if the counter selection is legal
     * @param gridLocation An int[] with the x and y location on the grid
     * @return Whether or not the selection was legal
     */
    public boolean counterSelectionIsLegal(int[] gridLocation){
        boolean isLegal = selectionChecker.counterSelectionIsLegal(gridLocation, gridSelections);
        if(isLegal){
            gridSelections.add(gridLocation);
        }
        return isLegal;
    }

    /**
     * Check to see if the movement follows the standard Abalone rules
     * @param gridLocation The current x and y location on the game board
     * @return The boolean value of whether or not the movement follows the traditional Abalone rules
     */
    public boolean isMovementLegal(int[] gridLocation){
        movementLogic = selectionChecker.checkMoveSelectionIsLegal(gridLocation, gridSelections, gameBoard);

        return movementLogic.getIsMovementLegal();
    }

    public void makeMove(){
        Move move = new Move(gameBoard, gridSelections, movementLogic);
        gameBoard.revertGameBoard();
        gameBoard.makeMove(move.makeMove());
        gameBoard.resetMemento();

        updateScores(move);

        runTerminalTest();
        statistics.update(currentPlayer, movementLogic.getNumberOfCountersBeingPushed(), move.getHasTakenACounter());
        resetPlayerSelections();
        changePlayer();
    }

    private void updateScores(Move move){
        if(move.getHasTakenACounter()){
            if(getCurrentPlayer() == 1){
                numberOfPlayer2CountersTaken++;
            }
            else{
                numberOfPlayer1CountersTaken++;
            }
        }
    }

    public int getNumberOfCountersSelected(){
        return gridSelections.getNumberOfCountersSelected();
    }

    /**
     * Cancel the players current selections
     */
    public void resetPlayerSelections(){
        gridSelections = new GridSelections();
    }

    /**
     * @return The game board instance currently in use
     */
    public GameBoard getGameBoard(){
        return gameBoard;
    }

    /**
     * @return The number of player 1 counters which have been taken
     */
    public int getNumberOfPlayer1CountersTaken(){
        return numberOfPlayer1CountersTaken;
    }

    /**
     * @return The number of player 2 counters which have been taken
     */
    public int getNumberOfPlayer2CountersTaken(){
        return numberOfPlayer2CountersTaken;
    }

    public boolean hasGameEnded(){
        return gameEnded;
    }

    /**
     *  Randomize the player to take the first go
     */
    private void setPlayerToTakeFirstTurn(){
        playerToTakeFirstTurn = 1;
        currentPlayer = playerToTakeFirstTurn;
    }

    /**
     * @return The current player taking their turn
     */
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    private void changePlayer(){
        if(currentPlayer == 1){
            currentPlayer = 2;
        }
        else{
            currentPlayer = 1;
        }
    }

    public GridSelections getGridSelections(){
        return gridSelections;
    }

    public void setMovementLogic(MovementLogic ml){
        movementLogic = ml;
    }

    public void setGridSelections(GridSelections gs){
        gridSelections = gs;
    }

    public Statistics getStatistics(){
        return statistics;
    }

    public void setGameType(int gt){
        gameType = gt;
    }

    public int getGameType(){
        return gameType;
    }

    public void setPlayer1(String name, String profilePicUrl){
        player1Name = name;
        player1ProfilePictureUrl = profilePicUrl;
    }

    public void setPlayer2(String name, String profilePicUrl){
        player2Name = name;
        player2ProfilePictureUrl = profilePicUrl;
    }

    public String getPlayer1Name(){
        return player1Name;
    }

    public String getPlayer1ProfilePictureUrl(){
        return player1ProfilePictureUrl;
    }

    public String getPlayer2Name(){
        return player2Name;
    }

    public String getPlayer2ProfilePictureUrl(){
        return player2ProfilePictureUrl;
    }

//    public GoogleSignInAccount getStartingAccount(){
//        return startingAccount;
//    }
//
//    public void setOtherAccount(GoogleSignInAccount oa){
//        if(otherAccount == null) {
//            otherAccount = oa;
//        }
//    }
//
//    public GoogleSignInAccount getOtherAccount(){
//        return otherAccount;
//    }
}
