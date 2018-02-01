package com.jcroberts.abalone.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.jcroberts.abalone.multiplayer.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Extension of the game activity class to allow networked gaming on two different devices
 * Author: Joshua Roberts
 */

public class NetworkedMultiplayerGameActivity extends GameActivity{
    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;
    private GoogleSignInAccount googleUserAccount;
    private boolean allowAutoMatch = false;
    private String usersName;
    private String player2Name;

    private String opponentID;

    private MultiplayerGame multiplayerGame;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //TODO if statement for new game versus continuing game
        googleUserAccount = GoogleSignIn.getLastSignedInAccount(this);
        opponentID = getIntent().getStringExtra("Opponent ID");
        if(googleUserAccount == null){
            returnToMainMenu();
        }
        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, googleUserAccount);
        multiplayerGame = new MultiplayerGame(this, googleUserAccount);
        usersName = cutName(googleUserAccount.getGivenName());
        String player1ScoreString = usersName + COLON_SPACE + game.getNumberOfPlayer2CountersTaken();
        player1ScoreText.setText(player1ScoreString);
        String player2ScoreString = "Player 2" + COLON_SPACE + game.getNumberOfPlayer1CountersTaken();
        player2ScoreText.setText(player2ScoreString);


    }

    @Override
    protected void changePlayer(){
        waitingForOtherPlayerToTakeTurn = true;
    }


    @Override
    protected void changeTurn(){
        super.changeTurn();
        if(game.getCurrentPlayer() == 2){
            byte[] gameData = multiplayerGame.serializeData(game);
            if(gameData != null) {
                stopUserTurn();
                turnBasedMultiplayerClient.takeTurn("1", gameData, opponentID);
            }

            ProgressDialog.show(this, "", "Waiting for " + player2Name + " to take their turn...");
        }
    }


}
