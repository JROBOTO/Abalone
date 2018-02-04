package com.jcroberts.abalone.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jcroberts.abalone.multiplayer.*;

/**
 * Extension of the game activity class to allow networked gaming on two different devices
 * Author: Joshua Roberts
 */

public class NetworkedMultiplayerGameActivity extends GameActivity{

    private static String TAG = "Networked Multiplayer Activity";

    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;
    private GoogleSignInAccount signedInAccount;
    private String usersName;
    private String player2Name;
    private String opponentID;
    private TurnBasedMatch turnBasedMatch;
    private MultiplayerGame multiplayerGame;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        signedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        multiplayerGame = new MultiplayerGame();
        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, googleUserAccount);

        multiplayerGame.setPlayer1ID(signedInAccount.getId());

        Parcelable[] savedGameData = getIntent().getParcelableArrayExtra("Saved Game Data");

        if(savedGameData != null){
            Log.i(TAG, "Resuming game");
            //TODO Finish
        }
        else{
            Log.i(TAG, "Creating new game");
            String turnBasedMatchID = getIntent().getStringExtra("Match ID");
            final String player2ID = getIntent().getStringExtra("Opponent ID");
            turnBasedMultiplayerClient.loadMatch(turnBasedMatchID).addOnSuccessListener(new OnSuccessListener<AnnotatedData<TurnBasedMatch>>() {
                @Override
                public void onSuccess(AnnotatedData<TurnBasedMatch> turnBasedMatchAnnotatedData) {
                    turnBasedMatch = turnBasedMatchAnnotatedData.get();
                    multiplayerGame.setPlayer2ID(player2ID);

                    opponentID = turnBasedMatch.getParticipantId(getIntent().getStringExtra("Opponent ID"));
                    if(googleUserAccount == null){
                        returnToMainMenu();
                    }
                    usersName = cutName(googleUserAccount.getGivenName());
                    String player1ScoreString = usersName + COLON_SPACE + game.getNumberOfPlayer2CountersTaken();
                    player1ScoreText.setText(player1ScoreString);
                    String player2ScoreString = "Player 2" + COLON_SPACE + game.getNumberOfPlayer1CountersTaken();
                    player2ScoreText.setText(player2ScoreString);
                    dismissWaitingDialog();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            });

            showLoadingDialog();
        }
    }

    @Override
    protected void changePlayer(){
        waitingForOtherPlayerToTakeTurn = true;
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(waitingDialog != null){
            dismissWaitingDialog();
        }
    }

    @Override
    protected void changeTurn(){
        super.changeTurn();
        if(game.getCurrentPlayer() == 2){
            multiplayerGame.setGameData(game);
            byte[] gameData = multiplayerGame.serializeData();
            if(gameData != null) {
                stopUserTurn();
                turnBasedMultiplayerClient.takeTurn("1", gameData, opponentID);
            }

            //ProgressDialog.show(this, "", "Waiting for " + player2Name + " to take their turn...");
        }
    }
}