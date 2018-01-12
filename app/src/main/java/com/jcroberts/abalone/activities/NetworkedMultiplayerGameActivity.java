package com.jcroberts.abalone.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.jcroberts.abalone.multiplayer.*;

import java.util.ArrayList;

/**
 * Extension of the game activity class to allow networked gaming on two different devices
 * Author: Joshua Roberts
 */

public class NetworkedMultiplayerGameActivity extends GameActivity{
    public static final int GOOGLE_SELECT_PLAYERS = 0;
    public static final int MAX_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;

    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;
    private GoogleSignInAccount googleUserAccount;
    private boolean allowAutoMatch = false;
    private String usersName;

    private Multiplayer multiplayer;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        googleUserAccount = GoogleSignIn.getLastSignedInAccount(this);
        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, googleUserAccount);
        multiplayer = new Multiplayer(this, googleUserAccount);
        usersName = cutName(googleUserAccount.getDisplayName());
        player1ScoreText.setText(usersName + COLON_SPACE + game.getNumberOfPlayer2CountersTaken());
        player2ScoreText.setText("Player 2" + COLON_SPACE + game.getNumberOfPlayer1CountersTaken());
        System.out.println("Inviting players");
        invite();
    }

    @Override
    protected void changePlayer(){
        waitingForOtherPlayerToTakeTurn = true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SELECT_PLAYERS) {
            if (resultCode != Activity.RESULT_OK) {
                // Canceled or other unrecoverable error.
                return;
            }
            ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // Get automatch criteria
            Bundle autoMatchCriteria = null;

            TurnBasedMatchConfig.Builder builder = TurnBasedMatchConfig.builder().addInvitedPlayers(invitees);
            builder.setAutoMatchCriteria(RoomConfig.createAutoMatchCriteria(MIN_PLAYERS, MAX_PLAYERS, 0));

            turnBasedMultiplayerClient.createMatch(builder.build()).addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
                @Override
                public void onComplete(@NonNull Task<TurnBasedMatch> task) {
                    if (task.isSuccessful()) {
                        TurnBasedMatch match = task.getResult();
                        if (match.getData() == null) {
                            // First turn, initialize the game data.
                            // (You need to implement this).
                            //initializeGameData(match);


                        }

                        // Show the turn UI.
                        // (Game specific logic)
                        //showTurnUI(match);
                    } else {
                        System.out.println("Fucked it");
                        // There was an error. Show the error.
                        int status = CommonStatusCodes.DEVELOPER_ERROR;
                        Exception exception = task.getException();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            status = apiException.getStatusCode();
                        }
                        //handleError(status, exception);
                    }
                }
            });
        }
    }


    public void invite(){
        turnBasedMultiplayerClient.getSelectOpponentsIntent(MIN_PLAYERS, MAX_PLAYERS, allowAutoMatch).addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        System.out.println("Found intent to select opponents");
                        startActivityForResult(intent, GOOGLE_SELECT_PLAYERS);
                    }
                });
    }
}
