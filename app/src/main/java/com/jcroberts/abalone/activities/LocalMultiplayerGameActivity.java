package com.jcroberts.abalone.activities;

import android.os.Bundle;

import com.jcroberts.abalone.R;
import com.jcroberts.abalone.activities.GameActivity;
import com.jcroberts.abalone.game.Game;

/**
 * Author: Joshua Roberts
 */

public class LocalMultiplayerGameActivity extends GameActivity {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        game.setGameType(Game.LOCAL_MULTIPLAYER_GAME);

        game.setPlayer1(googleUserAccount.getDisplayName(), googleUserAccount.getPhotoUrl().toString());
        game.setPlayer2("Player 2", "");
    }

}
