package com.jcroberts.abalone.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchUpdateCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.jcroberts.abalone.R;
import com.jcroberts.abalone.game.Game;

import android.net.Uri;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.google.android.gms.games.multiplayer.Multiplayer.EXTRA_TURN_BASED_MATCH;

public class MainMenuActivity extends AppCompatActivity {
    private Intent intent;

    private static final int RC_SIGN_IN = 0;
    private static final int GOOGLE_SELECT_PLAYERS = 1;
    private static final int GOOGLE_INBOX_INTENT = 2;
    private static final String TAG = "Main Menu Activity";

    /**
     * Used to determine whether or not the app has already attempted to sign in and, therefore,
     * whether or not to call the silent sign in method
     */
    private GoogleSignInAccount signedInAccount;
    private TurnBasedMultiplayerClient turnBasedMultiplayerClient;
    private ProgressDialog loadingDialog;

    private LinearLayout mainMenuLinearLayout;
    private LinearLayout multiplayerOptionsLinearLayout;

    private TextView profileName;

    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button singlePlayerButton = findViewById(R.id.singlePlayerButton);
        Button localMultiPlayerButton = findViewById(R.id.localMultiplayerButton);
        Button networkedMultiplayerButton = findViewById(R.id.networkedMultiplayerButton);
        profileName = findViewById(R.id.profileName);
        profilePicture = findViewById(R.id.profilePicture);

        Button createGameButton = findViewById(R.id.createGameButton);
        Button existingGamesButton = findViewById(R.id.existingGamesButton);

        mainMenuLinearLayout = findViewById(R.id.mainMenuOptions);
        multiplayerOptionsLinearLayout = findViewById(R.id.multiplayerGameOptions);

        GameClickListener gClickListener = new GameClickListener();

        singlePlayerButton.setOnClickListener(gClickListener);
        localMultiPlayerButton.setOnClickListener(gClickListener);
        networkedMultiplayerButton.setOnClickListener(gClickListener);
        createGameButton.setOnClickListener(gClickListener);
        existingGamesButton.setOnClickListener(gClickListener);

        signedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signedInAccount == null) {
            signIn();
        }
        else{
            profileName.setText(signedInAccount.getDisplayName());
        }

        if(!signedInAccount.getGrantedScopes().contains(Games.SCOPE_GAMES_LITE)){
            GoogleSignIn.requestPermissions(this, 1, signedInAccount, Games.SCOPE_GAMES_LITE);
        }
        Uri uri = signedInAccount.getPhotoUrl();
        Glide.with(getApplicationContext()).load(uri).into(profilePicture);

        turnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, signedInAccount);

        turnBasedMultiplayerClient.registerTurnBasedMatchUpdateCallback(new TurnBasedMatchUpdateCallback() {
            @Override
            public void onTurnBasedMatchReceived(@NonNull TurnBasedMatch turnBasedMatch) {
                Toast.makeText(getBaseContext(), "Match Received", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTurnBasedMatchRemoved(@NonNull String s) {
                Toast.makeText(getBaseContext(), "Match Removed", Toast.LENGTH_LONG).show();
            }
        });

        InvitationsClient invitationsClient = Games.getInvitationsClient(this, signedInAccount);

        invitationsClient.registerInvitationCallback(new InvitationCallback() {
            @Override
            public void onInvitationReceived(@NonNull Invitation invitation) {
                Toast.makeText(getApplicationContext(), "Invitation Received", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInvitationRemoved(@NonNull String s) {

            }
        });

        System.out.println("Player ID: " + signedInAccount.getId());
    }

    /**
     * Start the Google sign in intent
     */
    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private boolean getHasInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showLoadingDialog(){
        loadingDialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
    }

    /**
     * Handles the response of the sign in intent
     * @param requestCode The code request corresponding to the activity selected
     * @param resultCode The result code passed back from the activity
     * @param data The data package returned from the activity
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
        else if (requestCode == GOOGLE_SELECT_PLAYERS) {
            if (resultCode != Activity.RESULT_OK) {
                // Canceled or other unrecoverable error.
                System.out.println(resultCode);
                return;
            }
            showLoadingDialog();
            final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            TurnBasedMatchConfig turnBasedMatchConfig = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(RoomConfig.createAutoMatchCriteria(Game.MIN_NUMBER_OF_OPPONENTS, Game.MAX_NUMBER_OF_OPPONENTS, 0))
                    .build();

            Games.getTurnBasedMultiplayerClient(this, signedInAccount).createMatch(turnBasedMatchConfig).addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                @Override
                public void onSuccess(TurnBasedMatch match) {
                    takeFirstTurn(match);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            });
        }
        else if(requestCode == GOOGLE_INBOX_INTENT){
            if(resultCode != Activity.RESULT_OK){
                System.out.println(resultCode);
                return;
            }

            TurnBasedMatch gameData = data.getParcelableExtra(EXTRA_TURN_BASED_MATCH);
            resumeGame(gameData);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            signedInAccount = completedTask.getResult(ApiException.class);
            System.out.println("Signed in successfully");

            profileName.setText(signedInAccount.getDisplayName());
            Uri uri = signedInAccount.getPhotoUrl();
            Glide.with(getApplicationContext()).load(uri).into(profilePicture);
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//                Drawable pPicture = Drawable.createFromStream(inputStream, uri.toString());
//
//                profilePicture.setImageDrawable(pPicture);
//
//            }
//            catch(FileNotFoundException fnfe){
//                System.out.println("Profile picture file not found");
//            }
//            catch(NullPointerException npe){
//                npe.printStackTrace();
//            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void takeFirstTurn(TurnBasedMatch turnBasedMatch){
        intent = new Intent(this, NetworkedMultiplayerGameActivity.class);
        intent.putExtra("Match ID", turnBasedMatch.getMatchId());
        intent.putExtra("Is New Game", true);
        startActivity(intent);
    }

    private void resumeGame(TurnBasedMatch turnBasedMatch){
        intent = new Intent(this, NetworkedMultiplayerGameActivity.class);
        intent.putExtra("Match ID", turnBasedMatch.getMatchId());
        intent.putExtra("Is New Game", false);
        startActivity(intent);
    }

    /**
     * Whenever the activity is visited, the app forgets that the number of players was selected
     */
    @Override
    protected void onResume(){
        super.onResume();
        //signIn();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(loadingDialog != null) {
            loadingDialog.dismiss();
        }
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

    public void invite(){
        turnBasedMultiplayerClient.getSelectOpponentsIntent(Game.MIN_NUMBER_OF_OPPONENTS, Game.MAX_NUMBER_OF_OPPONENTS, false).addOnSuccessListener(new OnSuccessListener<Intent>() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent, GOOGLE_SELECT_PLAYERS);
            }
        });
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
                    if(getHasInternetConnection()) {
                        showMultiplayerOptions();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No internet connection detected. Please connect to the internet try again.", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.createGameButton:
                    invite();
                    break;


                case R.id.existingGamesButton:
                    turnBasedMultiplayerClient.getInboxIntent().addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, GOOGLE_INBOX_INTENT);
                        }
                    });
                    break;

                case R.id.backButton:
                    showMainMenuOptions();

            }
        }

        private void showMainMenuOptions(){
            mainMenuLinearLayout.setVisibility(View.VISIBLE);
            multiplayerOptionsLinearLayout.setVisibility(View.GONE);
        }

        private void showMultiplayerOptions(){
            mainMenuLinearLayout.setVisibility(View.GONE);
            multiplayerOptionsLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
