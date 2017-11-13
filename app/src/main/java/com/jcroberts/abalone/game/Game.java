package com.jcroberts.abalone.game;


import com.jcroberts.abalone.R;

import java.util.Random;

/**
 * All the game logic for the abalone game app. This class is to be extended for the purpose of
 * each game type
 * Author: Joshua Roberts
 */

public class Game {
    private GameBoard gameBoard;
    private int numberOfPlayer1CountersTaken;
    private int numberOfPlayer2CountersTaken;
    private int playerToTakeFirstTurn;
    private int currentPlayer;

    private boolean gameEnded;

    private Random random;

    private GridSelectionsObject gridSelections;
    private SelectionChecker selectionChecker;
    private MovementLogic movementLogic;

    /**
     * Initialize the game
     */
    public Game(){
        //Initialize game logic
        gameEnded = false;
        numberOfPlayer1CountersTaken = 0;
        numberOfPlayer2CountersTaken = 0;

        gameBoard = new GameBoard();

        random = new Random();

        setPlayerToTakeFirstTurn();
    }

    /**
     * Test if the game has ended
     */
    private void runTerminalTest(){
        if(numberOfPlayer1CountersTaken >= 6 || numberOfPlayer2CountersTaken >= 6){
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
            gridSelections.add(gridLocation[0], gridLocation[1]);
        }
        return isLegal;
    }

    public boolean isMovementLegal(int[] gridLocation, boolean isPushing){
        movementLogic = selectionChecker.checkMoveSelectionIsLegal(gridLocation, gridSelections, isPushing);

        return !movementLogic.equals(null);
    }


    /**
     * Cancel the players current selections
     */
    public void resetPlayerSelections(){
        gridSelections = new GridSelectionsObject();
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

    /**
     * @return The player who is taking the first turn
     */
    public int getPlayerToTakeFirstTurn(){
        return playerToTakeFirstTurn;
    }

    public boolean hasGameEnded(){
        return gameEnded;
    }

    /**
     *  Randomize the player to take the first go
     */
    private void setPlayerToTakeFirstTurn(){
        while(playerToTakeFirstTurn != 1 || playerToTakeFirstTurn != 2){
            playerToTakeFirstTurn = random.nextInt(1) + 1;
        }
        currentPlayer = playerToTakeFirstTurn;
    }

    /**
     * @return The current player taking their turn
     */
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public int[][] getGridSelections(){
        return gridSelections.getSelectionsMade();
    }
}
