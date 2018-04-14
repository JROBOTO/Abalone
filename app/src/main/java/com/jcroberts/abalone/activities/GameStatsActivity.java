package com.jcroberts.abalone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jcroberts.abalone.R;
import com.jcroberts.abalone.game.Game;
import com.jcroberts.abalone.game.Statistics;
import com.jcroberts.abalone.multiplayer.MultiplayerGame;

public class GameStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);
        Game game = (Game) MultiplayerGame.deserializeData(getIntent().getByteArrayExtra("Game"));
        Statistics statistics = game.getStatistics();
        GoogleSignInAccount googleUserAccount = GoogleSignIn.getLastSignedInAccount(this);
        String player1Name;
        String player2Name;
        GoogleSignInAccount player1Account;
        GoogleSignInAccount player2Account;
        if(game.getStartingAccount().equals(googleUserAccount)){
            player1Name = googleUserAccount.getDisplayName();
            player2Name = "Player 2";

            player1Account = googleUserAccount;
            player2Account = game.getOtherAccount();
        }
        else{
            player1Name = game.getStartingAccount().getDisplayName();
            try {
                player2Name = googleUserAccount.getDisplayName();
            }
            catch(NullPointerException npe){
                try {
                    player2Name = googleUserAccount.getGivenName();
                }
                catch(NullPointerException npe2){
                    player2Name = "Player 2";
                }
            }
            player1Account = game.getStartingAccount();
            player2Account = googleUserAccount;
        }

        TextView winnersText = findViewById(R.id.winnersTextView);
        String winningProfilePictureUrl = null;
        if(statistics.getWinningPlayer() == 1){
            String winningString = player1Name + " Wins";
            winnersText.setText(winningString);
            winnersText.setBackground(getResources().getDrawable(R.drawable.main_menu_button_red));
            try {
                winningProfilePictureUrl = player1Account.getPhotoUrl().toString();
            }
            catch(NullPointerException npe){
                npe.getMessage();
            }
        }
        else{
            String winningString = player2Name + " Wins";
            winnersText.setText(winningString);
            winnersText.setBackground(getResources().getDrawable(R.drawable.main_menu_button_blue));
            try{
                winningProfilePictureUrl = player2Account.getPhotoUrl().toString();
            }
            catch(NullPointerException npe){
                npe.getMessage();
            }
        }

        ImageView winningProfilePictureImageView = findViewById(R.id.winningProfilePicture);
        if(winningProfilePictureUrl != null) {
            Glide.with(getApplicationContext()).load(winningProfilePictureUrl).into(winningProfilePictureImageView);
        }

        TextView moveTotalTextView = findViewById(R.id.moveTotalTextView);
        String moveTotalString = "Moves: " + statistics.getNumberOfMovesMade();
        moveTotalTextView.setText(moveTotalString);

        TextView player1NameTextView = findViewById(R.id.player1NameTextView);
        player1NameTextView.setText(player1Name);

        TextView player1ScoreTextView = findViewById(R.id.player1ScoreTextView);
        String player1ScoreString = "Score: " + statistics.getNumberOfCountersTaken()[0];
        player1ScoreTextView.setText(player1ScoreString);

        TextView player1PushingMovesTextView = findViewById(R.id.player1PushingMovesTextView);
        String player1PushingMovesString = "Pushing moves: " + statistics.getNumberOfMovesPushingForPlayer()[0];
        player1PushingMovesTextView.setText(player1PushingMovesString);

        TextView player1CountersPushedTextView = findViewById(R.id.player1CountersPushedTextView);
        String player1CountersPushedString = "Counters pushed: " + statistics.getNumberOfCountersPushed()[0];
        player1CountersPushedTextView.setText(player1CountersPushedString);

        TextView player2NameTextView = findViewById(R.id.player2NameTextView);
        player2NameTextView.setText(player2Name);

        TextView player2ScoreTextView = findViewById(R.id.player2ScoreTextView);
        String player2ScoreString = "Score: " + statistics.getNumberOfCountersTaken()[1];
        player2ScoreTextView.setText(player2ScoreString);

        TextView player2PushingMovesTextView = findViewById(R.id.player2PushingMovesTextView);
        String player2PushingMovesString = "Pushing moves: " + statistics.getNumberOfMovesPushingForPlayer()[1];
        player2PushingMovesTextView.setText(player2PushingMovesString);

        TextView player2CountersPushedTextView = findViewById(R.id.player2CountersPushedTextView);
        String player2CountersPushedString = "Counters pushed: " + statistics.getNumberOfCountersPushed()[1];
        player2CountersPushedTextView.setText(player2CountersPushedString);
    }

    public void returnToMainMenu(View v){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
