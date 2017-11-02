package com.jcroberts.abalone.game;

/**
 * All the game logic for the abalone game app. This class is to be extended for the purpose of
 * each game type
 * Author: Joshua Roberts
 */

public class Game {
    private GameBoard gameBoard;
    private int numberOfPlayer1CountersTaken;
    private int numberOfPlayer2CountersTaken;

    private boolean gameEnded;

    /**
     * Initialize the game
     */
    public Game(){
        //Initialize game logic
        gameEnded = false;
        numberOfPlayer1CountersTaken = 0;
        numberOfPlayer2CountersTaken = 0;

        gameBoard = new GameBoard();
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
}
