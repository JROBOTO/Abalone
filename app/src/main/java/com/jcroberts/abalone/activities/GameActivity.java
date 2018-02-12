package com.jcroberts.abalone.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.jcroberts.abalone.R;
import com.jcroberts.abalone.game.Game;

import java.util.ArrayList;

/**
 * The main game activity which builds the game board and allows turns to be taken to play the
 * game. It runs a terminal test after each turn whether it is a human player or not.
 *
 * Author: Joshua Roberts
 */

public class GameActivity extends AppCompatActivity {
    public static final String COLON_SPACE = ": ";
    public static final int MAX_NAME_LENGTH = 20;
    public static final int GOOGLE_SIGN_IN = 1;

    protected String player1ScoreString = "Player 1" + COLON_SPACE;
    protected String player2ScoreString = "Player 2" + COLON_SPACE;

    protected ProgressDialog waitingDialog;

    protected ImageView[][] gameBoardView;
    protected TextView player1ScoreText;
    protected TextView player2ScoreText;

    protected Drawable player1CounterDrawable;
    protected Drawable player1CounterSelectedDrawable;
    protected Drawable player2CounterDrawable;
    protected Drawable player2CounterSelectedDrawable;
    protected Drawable neutralSpaceDrawable;

    protected GoogleSignInAccount googleUserAccount;

    protected Game game;

    /**
     * Main creation method for the game to create the game board and run the main game loop
     * @param savedInstanceState The saved instance from the last activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        googleUserAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleUserAccount == null) {
            signIn();
        }

        player1CounterDrawable = getResources().getDrawable(R.drawable.grid_space_red);
        player1CounterSelectedDrawable = getResources().getDrawable(R.drawable.grid_space_red_selected);

        player2CounterDrawable = getResources().getDrawable(R.drawable.grid_space_blue);
        player2CounterSelectedDrawable = getResources().getDrawable(R.drawable.grid_space_blue_selected);

        neutralSpaceDrawable = getResources().getDrawable(R.drawable.grid_space_grey);

        player1ScoreText = (TextView)findViewById(R.id.player1Score);
        player2ScoreText = (TextView)findViewById(R.id.player2Score);

        game = new Game();

        setupGameBoard();

        changeScoreBubbles();
        Toast.makeText(this, "Player " + Integer.toString(game.getPlayerToTakeFirstTurn()) + " to go first", Toast.LENGTH_LONG).show();
        playUserTurn();
    }

    /**
     * Set up the game board in terms of initializing each space in turn. The arrays start at 1
     * because the arrays in the game class are accounting for the borders
     */
    private void setupGameBoard(){

        ImageView[] y1;
        ImageView[] y2;
        ImageView[] y3;
        ImageView[] y4;
        ImageView[] y5;
        ImageView[] y6;
        ImageView[] y7;
        ImageView[] y8;
        ImageView[] y9;

        y1 = new ImageView[11];

        y1[1] = (ImageView)findViewById(R.id.column1Row1);
        y1[2] = (ImageView)findViewById(R.id.column2Row1);
        y1[3] = (ImageView)findViewById(R.id.column3Row1);
        y1[4] = (ImageView)findViewById(R.id.column4Row1);
        y1[5] = (ImageView)findViewById(R.id.column5Row1);

        y2 = new ImageView[11];

        y2[1] = (ImageView)findViewById(R.id.column1Row2);
        y2[2] = (ImageView)findViewById(R.id.column2Row2);
        y2[3] = (ImageView)findViewById(R.id.column3Row2);
        y2[4] = (ImageView)findViewById(R.id.column4Row2);
        y2[5] = (ImageView)findViewById(R.id.column5Row2);
        y2[6] = (ImageView)findViewById(R.id.column6Row2);

        y3 = new ImageView[11];

        y3[1] = (ImageView)findViewById(R.id.column1Row3);
        y3[2] = (ImageView)findViewById(R.id.column2Row3);
        y3[3] = (ImageView)findViewById(R.id.column3Row3);
        y3[4] = (ImageView)findViewById(R.id.column4Row3);
        y3[5] = (ImageView)findViewById(R.id.column5Row3);
        y3[6] = (ImageView)findViewById(R.id.column6Row3);
        y3[7] = (ImageView)findViewById(R.id.column7Row3);

        y4 = new ImageView[11];

        y4[1] = (ImageView)findViewById(R.id.column1Row4);
        y4[2] = (ImageView)findViewById(R.id.column2Row4);
        y4[3] = (ImageView)findViewById(R.id.column3Row4);
        y4[4] = (ImageView)findViewById(R.id.column4Row4);
        y4[5] = (ImageView)findViewById(R.id.column5Row4);
        y4[6] = (ImageView)findViewById(R.id.column6Row4);
        y4[7] = (ImageView)findViewById(R.id.column7Row4);
        y4[8] = (ImageView)findViewById(R.id.column8Row4);

        y5 = new ImageView[11];

        y5[1] = (ImageView)findViewById(R.id.column1Row5);
        y5[2] = (ImageView)findViewById(R.id.column2Row5);
        y5[3] = (ImageView)findViewById(R.id.column3Row5);
        y5[4] = (ImageView)findViewById(R.id.column4Row5);
        y5[5] = (ImageView)findViewById(R.id.column5Row5);
        y5[6] = (ImageView)findViewById(R.id.column6Row5);
        y5[7] = (ImageView)findViewById(R.id.column7Row5);
        y5[8] = (ImageView)findViewById(R.id.column8Row5);
        y5[9] = (ImageView)findViewById(R.id.column9Row5);

        y6 = new ImageView[11];

        y6[2] = (ImageView)findViewById(R.id.column2Row6);
        y6[3] = (ImageView)findViewById(R.id.column3Row6);
        y6[4] = (ImageView)findViewById(R.id.column4Row6);
        y6[5] = (ImageView)findViewById(R.id.column5Row6);
        y6[6] = (ImageView)findViewById(R.id.column6Row6);
        y6[7] = (ImageView)findViewById(R.id.column7Row6);
        y6[8] = (ImageView)findViewById(R.id.column8Row6);
        y6[9] = (ImageView)findViewById(R.id.column9Row6);

        y7 = new ImageView[11];

        y7[3] = (ImageView)findViewById(R.id.column3Row7);
        y7[4] = (ImageView)findViewById(R.id.column4Row7);
        y7[5] = (ImageView)findViewById(R.id.column5Row7);
        y7[6] = (ImageView)findViewById(R.id.column6Row7);
        y7[7] = (ImageView)findViewById(R.id.column7Row7);
        y7[8] = (ImageView)findViewById(R.id.column8Row7);
        y7[9] = (ImageView)findViewById(R.id.column9Row7);

        y8 = new ImageView[11];

        y8[4] = (ImageView)findViewById(R.id.column4Row8);
        y8[5] = (ImageView)findViewById(R.id.column5Row8);
        y8[6] = (ImageView)findViewById(R.id.column6Row8);
        y8[7] = (ImageView)findViewById(R.id.column7Row8);
        y8[8] = (ImageView)findViewById(R.id.column8Row8);
        y8[9] = (ImageView)findViewById(R.id.column9Row8);

        y9 = new ImageView[11];

        y9[5] = (ImageView)findViewById(R.id.column5Row9);
        y9[6] = (ImageView)findViewById(R.id.column6Row9);
        y9[7] = (ImageView)findViewById(R.id.column7Row9);
        y9[8] = (ImageView)findViewById(R.id.column8Row9);
        y9[9] = (ImageView)findViewById(R.id.column9Row9);

        gameBoardView = new ImageView[11][11];

        gameBoardView[1] = y1;
        gameBoardView[2] = y2;
        gameBoardView[3] = y3;
        gameBoardView[4] = y4;
        gameBoardView[5] = y5;
        gameBoardView[6] = y6;
        gameBoardView[7] = y7;
        gameBoardView[8] = y8;
        gameBoardView[9] = y9;

        updateGameBoard();
    }

    /**
     * Update the game board on the screen based on the current state of the game in the Game class
     */
    protected void updateGameBoard(){
        int[][] gameBoard = game.getGameBoard().getGameBoard();
        for(int y = 0; y < 10; y++){
            for(int x = 0; x < 10; x++){
                try {
                    if (gameBoard[x][y] == 0) {
                        gameBoardView[x][y].setImageDrawable(neutralSpaceDrawable);
                    } else if (gameBoard[x][y] == 1) {
                        gameBoardView[x][y].setImageDrawable(player1CounterDrawable);
                    } else if (gameBoard[x][y] == 2) {
                        gameBoardView[x][y].setImageDrawable(player2CounterDrawable);
                    }
                }
                catch(NullPointerException npe){
                    System.out.println("NullPointerException in updateGameBoard() at " + x + " " + y);
                }
                catch(ArrayIndexOutOfBoundsException aioobe){
                    System.out.println("ArrayIndexOutOfBoundsException in updateGameBoard() at " + x + " " + y);
                }
            }

            String player1ScoreTextString = player1ScoreString + game.getNumberOfPlayer2CountersTaken();
            player1ScoreText.setText(player1ScoreTextString);
            String player2ScoreTextString = player2ScoreString + game.getNumberOfPlayer2CountersTaken();
            player2ScoreText.setText(player2ScoreTextString);
        }



    }

    protected void showLoadingDialog(){
        waitingDialog = ProgressDialog.show(this, "", "Loading, please wait...");
    }

    protected void dismissWaitingDialog(){
        if(waitingDialog != null){
            waitingDialog.dismiss();
        }
    }

    protected void returnToMainMenu(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    protected void playUserTurn(){
        for(int y = 0; y < 11; y++){
            for(int x = 0; x < 11; x++){
                GridLocationClickListener glcl = new GridLocationClickListener(new int[]{x, y}, this.getApplicationContext());
                try {
                    gameBoardView[x][y].setOnClickListener(glcl);
                } catch(NullPointerException npe){
                    npe.getMessage();
                }
            }
        }
    }

    protected void stopUserTurn(){
        for(int y = 0; y < 11; y++){
            for(int x = 0; x < 11; x++){
                try {
                    gameBoardView[x][y].setOnClickListener(null);
                } catch(NullPointerException npe){
                    npe.getMessage();
                }
            }
        }
    }

    protected String cutName(String name){
        if(name == null){
            return "Player";
        }
        if(name.length() > MAX_NAME_LENGTH) {
            return name.substring(0, MAX_NAME_LENGTH);
        }
        else{
            return name;
        }
    }

    protected void signIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    /**
     * Handles the response of the sign in intent
     * @param requestCode The int corresponding to tha intention leaving the activity
     * @param resultCode The int corresponding to the result of the activity on returning to the activity
     * @param data The data returned from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            //continueToGame();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            googleUserAccount = completedTask.getResult(ApiException.class);
            player1ScoreText.setText(googleUserAccount.getDisplayName() + COLON_SPACE + game.getNumberOfPlayer2CountersTaken());


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println("signInResult:failed code=" + e.getStatusCode());
        }
    }
    protected void changeScoreBubbles(){
        String scoreString1 = player1ScoreString + 0;
        player1ScoreText.setText(scoreString1);
        String scoreString2 = player2ScoreString + 0;
        player2ScoreText.setText(scoreString2);

    }

    protected void endGame(){
        AlertDialog.Builder endGameDialogBuilder = new AlertDialog.Builder(this)
                .setMessage("Game Ended")
                .setPositiveButton("Return to main menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        returnToMainMenu();
                    }
                });
    }

    protected void changeTurn(){
        //Method made to be overridden
        if(game.getCurrentPlayer() == 1){
            playUserTurn();
        }
    }

    /**
     * Click listener for when some counters have been selected to move and now a location needs to be selected to move to.
     */
    protected class GridLocationClickListener implements View.OnClickListener{
        private int[] gridLocation;

        private GridLocationClickListener(int[] gl, Context c){
            gridLocation = gl;
        }

        @Override
        public void onClick(View v) {
            ImageView location = (ImageView)v;

            if(game.getCurrentPlayer() == 1){
                if(location.getDrawable().getConstantState().equals(player1CounterDrawable.getConstantState())){
                    if(game.counterSelectionIsLegal(gridLocation)){
                        gameBoardView[gridLocation[0]][gridLocation[1]].setImageDrawable(player1CounterSelectedDrawable);
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }
                else if(location.getDrawable().getConstantState().equals(neutralSpaceDrawable.getConstantState()) && game.getNumberOfCountersSelected() > 0){
                    if(game.isMovementLegal(gridLocation, false)){
                        game.makeMove();
                        changeTurn();
                        updateGameBoard();
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }
                else if(location.getDrawable().getConstantState().equals(player2CounterDrawable.getConstantState()) && game.getNumberOfCountersSelected() > 0){
                    if(game.isMovementLegal(gridLocation, true)){
                        game.makeMove();
                        if(game.hasGameEnded()){
                            endGame();
                        }
                        else {
                            changeTurn();
                            updateGameBoard();
                        }
                    }
                    else{
                        resetPlayerSelections(1);
                    }
                }

            }
            else{
                if(location.getDrawable().getConstantState().equals(player2CounterDrawable.getConstantState())){
                    if(game.counterSelectionIsLegal(gridLocation)){
                        gameBoardView[gridLocation[0]][gridLocation[1]].setImageDrawable(player2CounterSelectedDrawable);
                    }
                    else{
                        resetPlayerSelections(2);
                    }
                }
                else if(location.getDrawable().getConstantState().equals(neutralSpaceDrawable.getConstantState()) && game.getNumberOfCountersSelected() > 0){
                    if(game.isMovementLegal(gridLocation, false)) {
                        game.makeMove();
                        updateGameBoard();
                        changeTurn();
                    }
                    else{
                        resetPlayerSelections(2);
                    }

                }
                else if(location.getDrawable().getConstantState().equals(player1CounterDrawable.getConstantState()) && game.getNumberOfCountersSelected() > 0){
                    if(game.isMovementLegal(gridLocation, true)){
                        game.makeMove();
                        if(game.hasGameEnded()){
                            endGame();
                        }
                        else {
                            updateGameBoard();
                            changeTurn();
                        }
                    }
                    else{
                        resetPlayerSelections(2);
                    }
                }
            }
        }



        /**
         * Cancel the players current selections
         * @param player Which player is currently taking a turn
         */
        private void resetPlayerSelections(int player){
            ArrayList<int[]> selectionsMade = game.getGridSelections().getSelectionsMade();
            if(player == 1){
                for(int i = 0; i < selectionsMade.size(); i++){
                    gameBoardView[selectionsMade.get(i)[0]][selectionsMade.get(i)[1]].setImageDrawable(player1CounterDrawable);
                }
            }
            else if(player == 2){
                for(int i = 0; i < selectionsMade.size(); i++){
                    gameBoardView[selectionsMade.get(i)[0]][selectionsMade.get(i)[1]].setImageDrawable(player2CounterDrawable);
                }
            }
            game.resetPlayerSelections();
        }

    }


}
