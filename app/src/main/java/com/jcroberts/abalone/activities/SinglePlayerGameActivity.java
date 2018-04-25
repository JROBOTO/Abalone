package com.jcroberts.abalone.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.jcroberts.abalone.ai.AI;
import com.jcroberts.abalone.ai.AIMove;
import com.jcroberts.abalone.game.Game;

/**
 * Author: Joshua Roberts
 */

public class SinglePlayerGameActivity extends GameActivity {
    private AI aiPlayer;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        game.setGameType(Game.SINGLE_PLAYER_GAME);
        game.setPlayer1(googleUserAccount.getDisplayName(), googleUserAccount.getPhotoUrl().toString());
        game.setPlayer2("Your phone", "");
        waitingDialog = new ProgressDialog(this);
        aiPlayer = new AI(game);
    }

    @Override
    protected void changeTurn(){
        super.changeTurn();
        if(game.getCurrentPlayer() == 2){
            stopUserTurn();
            waitingDialog.setMessage("Choosing next move...");
            waitingDialog.show();
            AIMove move = aiPlayer.chooseNextMove();
            game.setMovementLogic(move.getMovementLogic());
            game.setGridSelections(move.getGridSelections());
            game.makeMove();
            waitingDialog.hide();
            updateGameBoard();
            playUserTurn();
        }
    }
}

