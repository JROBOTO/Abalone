package com.jcroberts.abalone.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jcroberts.abalone.R;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "Signing in";
    /**
     * Used to determine whether or not the app has already attempted to sign in and, therefore,
     * whether or not to call the silent sign in method
     */
    private boolean signedIn = false;
    private boolean triedToSignIn = false;

    private GoogleSignInAccount signedInAccount;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        if(isSignedIn()){
            signInSilently();

            //Wait for attempt to sign in
            while(!triedToSignIn){}

            if(signedIn) {
                continueToGame();
            }
        }
        else{
            signIn();
        }
    }

    private void continueToGame(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("Account", signedInAccount);
        startActivity(intent);
    }

    /**
     * Start the Google sign in intent
     */
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Handles the response of the sign in intent
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            //continueToGame();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            signedInAccount = completedTask.getResult(ApiException.class);

            // Signed in successfully
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void skipSignIn(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You must be signed in to Google Play to use network features.").setTitle("Are you sure you want to skip sign in?");
        builder.setPositiveButton(R.string.confirmSkip, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.denySkip, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private boolean isSignedIn(){
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    /**
     * Adapted from the Google Play API to determine if it can log in to the Google Play account
     * @return
     */
    private void signInSilently() {
        googleSignInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        googleSignInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            signedInAccount = task.getResult();
                            triedToSignIn = true;
                            signedIn = true;
                        } else {
                            triedToSignIn = true;
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInSilently();
    }

    public void startSignInIntent(View v) {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
}
