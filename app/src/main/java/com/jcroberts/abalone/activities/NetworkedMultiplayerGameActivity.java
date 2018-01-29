package com.jcroberts.abalone.activities;

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

    private String oppositionID;

    private MultiplayerGame multiplayerGame;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        googleUserAccount = GoogleSignIn.getLastSignedInAccount(this);

        if(googleUserAccount == null){
            returnToMainMenu();
        }
        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, googleUserAccount);
        multiplayerGame = new MultiplayerGame(this, googleUserAccount);
        usersName = cutName(googleUserAccount.getGivenName());
        player1ScoreText.setText(usersName + COLON_SPACE + game.getNumberOfPlayer2CountersTaken());
        player2ScoreText.setText("Player 2" + COLON_SPACE + game.getNumberOfPlayer1CountersTaken());


    }

    @Override
    protected void changePlayer(){
        waitingForOtherPlayerToTakeTurn = true;
    }



    @Override
    protected void changeTurn(){
        super.changeTurn();
        if(game.getCurrentPlayer() == 2){
            byte[] gameData = serializeGameData();
            while(gameData == null){
                serializeGameData();
            }
            stopUserTurn();
            turnBasedMultiplayerClient.takeTurn("1", serializeGameData(), oppositionID);
        }
    }

    private byte[] serializeGameData(){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] gameBytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(game);
            out.flush();
            gameBytes = bos.toByteArray();
        }
        catch(IOException e){

        }
        finally{
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }

            return gameBytes;
        }
    }



    private void takeFirstTurn(){

    }
}
