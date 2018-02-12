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
import com.google.android.gms.games.Player;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchUpdateCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jcroberts.abalone.game.Game;
import com.jcroberts.abalone.multiplayer.*;

/**
 * Extension of the game activity class to allow networked gaming on two different devices
 * Author: Joshua Roberts
 */

public class NetworkedMultiplayerGameActivity extends GameActivity{

    private static String TAG = "Networked Multiplayer Activity";

    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;
    private GoogleSignInAccount signedInAccount;
    private String currentPlayerID;
    private String usersName;
    private String player2Name;
    private String opponent;
    private TurnBasedMatch turnBasedMatch;
    private MultiplayerGame multiplayerGame;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        signedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        multiplayerGame = new MultiplayerGame();
        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, googleUserAccount);

        Games.getPlayersClient(this, signedInAccount).getCurrentPlayer().addOnSuccessListener(this, new OnSuccessListener<Player>() {
            @Override
            public void onSuccess(Player player) {
                currentPlayerID = player.getPlayerId();
            }
        });

        turnBasedMultiplayerClient.loadMatch(getIntent().getStringExtra("Match ID")).addOnSuccessListener(new OnSuccessListener<AnnotatedData<TurnBasedMatch>>() {
            @Override
            public void onSuccess(AnnotatedData<TurnBasedMatch> turnBasedMatchAnnotatedData) {
                turnBasedMatch = turnBasedMatchAnnotatedData.get();

                if(turnBasedMatch.getData() == null){
                    takeFirstTurn();
                }
                else{
                    continueGame();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Failed to load match");
                e.printStackTrace();
            }
        });
        showLoadingDialog();
    }

    private void takeFirstTurn(){
        multiplayerGame.setPlayer1ID(turnBasedMatch.getParticipantIds().get(0));
        multiplayerGame.setPlayer2ID(turnBasedMatch.getParticipantIds().get(1));

        opponent = cutName(turnBasedMatch.getParticipant(turnBasedMatch.getParticipantIds().get(1)).getDisplayName());
        if(googleUserAccount == null){
            returnToMainMenu();
        }
        usersName = cutName(googleUserAccount.getGivenName());
        player1ScoreString = usersName + COLON_SPACE;
        player2ScoreString = opponent + COLON_SPACE;
        String player1ScoreTextString = player1ScoreString + game.getNumberOfPlayer2CountersTaken();
        player1ScoreText.setText(player1ScoreTextString);
        String player2ScoreTextString = player2ScoreString + game.getNumberOfPlayer1CountersTaken();
        player2ScoreText.setText(player2ScoreTextString);
        dismissWaitingDialog();
    }

    private void continueGame(){
        multiplayerGame = (MultiplayerGame)MultiplayerGame.deserializeData(turnBasedMatch.getData());
        game = multiplayerGame.getCurrentGameState();
        updateGameBoard();
        dismissWaitingDialog();
        if(game.hasGameEnded()){
            endGame();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(waitingDialog != null){
            dismissWaitingDialog();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void changeTurn(){
        super.changeTurn();
        if(game.getCurrentPlayer() == 1){
            multiplayerGame.setGameData(game);
            byte[] gameData = multiplayerGame.serializeData();
            if(gameData != null) {
                stopUserTurn();
                turnBasedMultiplayerClient.takeTurn(turnBasedMatch.getMatchId(), gameData, turnBasedMatch.getParticipantIds().get(0));
                returnToMainMenu();
            }
            else{
                System.out.println("Game Data is NULL");
            }
        }
        if(game.getCurrentPlayer() == 2){
            multiplayerGame.setGameData(game);
            byte[] gameData = multiplayerGame.serializeData();
            if(gameData != null) {
                stopUserTurn();
                turnBasedMultiplayerClient.takeTurn(turnBasedMatch.getMatchId(), gameData, turnBasedMatch.getParticipantIds().get(1));
                returnToMainMenu();
            }
            else{
                System.out.println("Game Data is NULL");
            }
        }
    }

    @Override
    protected void endGame(){
        turnBasedMultiplayerClient.finishMatch(turnBasedMatch.getMatchId()).addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
            @Override
            public void onSuccess(TurnBasedMatch turnBasedMatch) {
            //TODO Figure out what this is for
            }
        });
    }
}