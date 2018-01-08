package com.jcroberts.abalone.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jcroberts.abalone.R;

import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainMenuActivity extends AppCompatActivity {
    private Intent intent;

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "Signing in";

    private Button singlePlayerButton;
    private Button localMultiPlayerButton;
    private Button networkedMultiplayerButton;

    /**
     * Used to determine whether or not the app has already attempted to sign in and, therefore,
     * whether or not to call the silent sign in method
     */
    private GoogleSignInAccount signedInAccount;
    private GoogleSignInClient googleSignInClient;

    private LinearLayout googleButton;

    private TextView googleText;
    private TextView profileName;

    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signIn();
        setContentView(R.layout.activity_main_menu);

        singlePlayerButton = (Button)findViewById(R.id.singlePlayerButton);
        localMultiPlayerButton = (Button)findViewById(R.id.localMultiplayerButton);
        networkedMultiplayerButton = (Button)findViewById(R.id.networkedMultiplayerButton);
        googleButton = (LinearLayout)findViewById(R.id.googleSignInButton);
        googleText = (TextView)findViewById(R.id.googleSignOutText);
        profileName = (TextView)findViewById(R.id.profileName);
        profilePicture = (ImageView)findViewById(R.id.profilePicture);

        GameClickListener gClickListener = new GameClickListener();

        singlePlayerButton.setOnClickListener(gClickListener);
        localMultiPlayerButton.setOnClickListener(gClickListener);
        networkedMultiplayerButton.setOnClickListener(gClickListener);

    }

    /**
     * Start the Google sign in intent
     */
    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

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
            System.out.println("Signed in successfully");

            profileName.setText(signedInAccount.getDisplayName());

            Uri uri = signedInAccount.getPhotoUrl();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Drawable pPicture = Drawable.createFromStream(inputStream, uri.toString());

                profilePicture.setImageDrawable(pPicture);
            }
            catch(FileNotFoundException fnfe){

            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    /**
     * Whenever the activity is visited, the app forgets that the number of players was selected
     */
    @Override
    protected void onResume(){
        super.onResume();
        //signIn();
    }

    public void signOut(View v) {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                signIn();
            }
        });
    }

    /**
     * Activated when the help button is pressed. Starts the help activity.
     * @param v Which button is pressed.
     */
    public void goToHelp(View v){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private class GameClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.singlePlayerButton:
                    intent = new Intent(v.getContext(), SinglePlayerGameActivity.class);
                    intent.putExtra("GoogleAccount", signedInAccount);
                    startActivity(intent);

                    break;

                case R.id.localMultiplayerButton:
                    intent = new Intent(v.getContext(), LocalMultiplayerGameActivity.class);
                    intent.putExtra("GoogleAccount", signedInAccount);
                    startActivity(intent);

                    break;

                case R.id.networkedMultiplayerButton:
                    intent = new Intent(v.getContext(), NetworkedMultiplayerGameActivity.class);
                    intent.putExtra("GoogleAccount", signedInAccount);
                    startActivity(intent);

                    break;
            }
        }
    }
}
