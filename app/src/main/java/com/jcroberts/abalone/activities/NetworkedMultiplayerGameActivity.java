package com.jcroberts.abalone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
        usersName = cutName(googleUserAccount.getGivenName());
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
                System.out.println(resultCode);
                returnToMainMenu();
                return;
            }
            final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // Get automatch criteria
            Bundle autoMatchCriteria = null;

            TurnBasedMatchConfig turnBasedMatchConfig = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(RoomConfig.createAutoMatchCriteria(MIN_PLAYERS, MAX_PLAYERS, 1))
                    .build();

            Games.getTurnBasedMultiplayerClient(this, googleUserAccount).createMatch(turnBasedMatchConfig).addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                @Override
                public void onSuccess(TurnBasedMatch match) {

                    player2ScoreText.setText(match.getParticipant(invitees.get(0)).getDisplayName() + COLON_SPACE + game.getNumberOfPlayer2CountersTaken());
                    if (match.getData() == null) {


                    }

                    takeFirstTurn();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Yup");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    returnToMainMenu();
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

    private void takeFirstTurn(){

    }
}
