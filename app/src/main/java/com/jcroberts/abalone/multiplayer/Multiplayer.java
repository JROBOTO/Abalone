package com.jcroberts.abalone.multiplayer;

import android.content.Context;

//import com.google.android.gms.auth.*;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
//import com.google.android.gms.games.multiplayer.*;
//import com.google.android.gms.games.multiplayer.turnbased.*;

/**
 * Class to control the google play API
 * Author: Joshua Roberts
 */

public class Multiplayer {
    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;

    public Multiplayer(Context context, GoogleSignInAccount userSignIn){
        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(context, userSignIn);
    }

}
