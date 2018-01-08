package com.jcroberts.abalone.activities;

import android.os.Bundle;

import com.jcroberts.abalone.multiplayer.*;

/**
 * Extension of the game activity class to allow networked gaming on two different devices
 * Author: Joshua Roberts
 */

public class NetworkedMultiplayerGameActivity extends GameActivity{

    private Multiplayer multiplayer;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        multiplayer = new Multiplayer(this, googleUserAccount);
        multiplayer.invite();
    }

    @Override
    protected void changePlayer(){
        waitingForOtherPlayerToTakeTurn = true;

    }
}
