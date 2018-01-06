package com.jcroberts.abalone.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jcroberts.abalone.R;

import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainMenuActivity extends AppCompatActivity {
    private Intent intent;

    private Button singlePlayerButton;
    private Button localMultiPlayerButton;
    private Button networkedMultiplayerButton;

    /**
     * Used to determine whether or not the app has already attempted to sign in and, therefore,
     * whether or not to call the silent sign in method
     */
    private GoogleSignInAccount signedInAccount;
    private LinearLayout googleButton;

    private TextView googleText;
    private TextView profileName;

    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signedInAccount = getIntent().getParcelableExtra("Account");
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
     * Whenever the activity is visited, the app forgets that the number of players was selected
     */
    @Override
    protected void onResume(){
        super.onResume();
        signInSilently();
    }

    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            signedInAccount = task.getResult();
                            profileName.setText(signedInAccount.getDisplayName());

                            Uri uri = signedInAccount.getPhotoUrl();
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                Drawable pPicture = Drawable.createFromStream(inputStream, uri.toString());

                                profilePicture.setImageDrawable(pPicture);
                            }
                            catch(FileNotFoundException fnfe){

                            }
                        } else {
                            goToSignInScreen();
                        }
                    }
                });
    }

    public void signOut(View v) {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                goToSignInScreen();
            }
        });
    }

    private void goToSignInScreen(){
        intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
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
                    startActivity(intent);

                    break;

                case R.id.localMultiplayerButton:
                    intent = new Intent(v.getContext(), LocalMultiplayerGameActivity.class);
                    startActivity(intent);

                    break;

                case R.id.networkedMultiplayerButton:
                    intent = new Intent(v.getContext(), NetworkedMultiplayerGameActivity.class);
                    startActivity(intent);

                    break;
            }
        }
    }
}
