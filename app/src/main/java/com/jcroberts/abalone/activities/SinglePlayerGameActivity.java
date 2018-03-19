package com.jcroberts.abalone.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.jcroberts.abalone.ai.AI;
import com.jcroberts.abalone.ai.AIMove;

/**
 * Author: Joshua Roberts
 */

public class SinglePlayerGameActivity extends GameActivity {
    private AI aiPlayer;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        waitingDialog = new ProgressDialog(this);
        aiPlayer = new AI(game);
    }

    @Override
    protected void changeTurn(){
        super.changeTurn();
        if(game.getCurrentPlayer() == 2){
            stopUserTurn();
            waitingDialog.show();
            AIMove move = aiPlayer.chooseNextMove();
            game.setMovementLogic(move.getMovementLogic());
            game.setGridSelections(move.getGridSelections());
            game.makeMove();
            waitingDialog.dismiss();
            updateGameBoard();
            playUserTurn();
        }
    }
}

