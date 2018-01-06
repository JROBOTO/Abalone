package com.jcroberts.abalone.multiplayer;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * Created by jcrob on 02/01/2018.
 */

public class GoogleLogIn {

    private GoogleSignInClient mGoogleSignInClient;

    public GoogleLogIn(Context context) {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, signInOptions);
    }
}
