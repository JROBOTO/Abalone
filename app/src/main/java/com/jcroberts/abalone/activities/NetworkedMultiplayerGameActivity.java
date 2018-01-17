package com.jcroberts.abalone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.Multiplayer;
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
    public static final int GOOGLE_SELECT_PLAYERS = 40;
    public static final int MAX_PLAYERS = 1;
    public static final int MIN_PLAYERS = 1;

    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;
    private GoogleSignInAccount googleUserAccount;
    private boolean allowAutoMatch = false;
    private String usersName;

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
        usersName = cutName(googleUserAccount.getDisplayName());
        player1ScoreText.setText(usersName + COLON_SPACE + game.getNumberOfPlayer2CountersTaken());
        player2ScoreText.setText("Player 2" + COLON_SPACE + game.getNumberOfPlayer1CountersTaken());
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
                System.out.println("Shit");
                System.out.println(resultCode);
                returnToMainMenu();
                return;
            }
            System.out.println("We're good");
            ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // Get automatch criteria
            Bundle autoMatchCriteria = null;
            int minAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

            TurnBasedMatchConfig.Builder builder = TurnBasedMatchConfig.builder().addInvitedPlayers(invitees);
            builder.setAutoMatchCriteria(RoomConfig.createAutoMatchCriteria(minAutoPlayers, maxAutoPlayers, 0));

            Games.getTurnBasedMultiplayerClient(this, googleUserAccount).createMatch(builder.build()).addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
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
                        // There was an error. Show the error.
                        int status = CommonStatusCodes.DEVELOPER_ERROR;
                        Exception exception = task.getException();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            status = apiException.getStatusCode();
                        }
                        System.out.println(task.getException().getMessage());

                        returnToMainMenu();
                    }
                }
            });
        }
    }


    public void invite(){
        turnBasedMultiplayerClient.getSelectOpponentsIntent(MIN_PLAYERS, MAX_PLAYERS, allowAutoMatch).addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, GOOGLE_SELECT_PLAYERS);
                    }
                });
    }
}
