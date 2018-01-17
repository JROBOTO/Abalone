package com.jcroberts.abalone.multiplayer;

import android.content.Context;
import android.content.Intent;

//import com.google.android.gms.auth.*;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.*;
import com.google.android.gms.games.multiplayer.turnbased.*;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * Class to control the google play API
 * Author: Joshua Roberts
 */

public class MultiplayerGame {

    private Context activityContext;
    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;
    private GoogleSignInAccount googleUserAccount;
    private boolean allowAutoMatch = true;

    public MultiplayerGame(Context context, GoogleSignInAccount userSignIn){
        activityContext = context;
        googleUserAccount = GoogleSignIn.getLastSignedInAccount(activityContext);
        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(activityContext, userSignIn);
    }


}
