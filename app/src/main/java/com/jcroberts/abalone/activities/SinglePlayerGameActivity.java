package com.jcroberts.abalone.activities;

import android.os.Bundle;

import com.jcroberts.abalone.ai.AI;
import com.jcroberts.abalone.ai.AIMove;

/**
 * Author: Joshua Roberts
 */

public class SinglePlayerGameActivity extends GameActivity {
    private AI aiPlayer;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        aiPlayer = new AI(game);
    }

    @Override
    protected void changeTurn(){
        super.changeTurn();
        if(game.getCurrentPlayer() == 2){
            stopUserTurn();
            AIMove move = aiPlayer.chooseNextMove(game.getGameBoard());
            game.getGameBoard().makeMove(move.makeMove());
            game.updateScores(move);
            updateGameBoard();
        }
    }
}

