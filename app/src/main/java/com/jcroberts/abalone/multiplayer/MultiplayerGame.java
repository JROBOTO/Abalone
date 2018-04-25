package com.jcroberts.abalone.multiplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

//import com.google.android.gms.auth.*;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.*;
import com.google.android.gms.games.multiplayer.turnbased.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jcroberts.abalone.game.Game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Class to control the google play API
 * Author: Joshua Roberts
 */

public class MultiplayerGame implements Serializable{

    private String player1ID;
    private String player2ID;

    private Game currentGameState;

    public MultiplayerGame(){

    }

    public byte[] serializeData(@Nullable Object objectToBeSerialized){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(objectToBeSerialized == null ? this : objectToBeSerialized);
            out.flush();
            bytes = bos.toByteArray();
        }
        catch(IOException e){
            System.out.println("Serialization error.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally{
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return bytes;
    }

    public static Object deserializeData(byte[] data){
        System.out.println(data == null ? "Data is null" : "Data is real");
        Object object = null;

        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;

        try{
            byteArrayInputStream = new ByteArrayInputStream(data);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                if(byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                if(objectInputStream != null) {
                    objectInputStream.close();
                }
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        System.out.println(object == null ? "Null game object" : "Game object deserialized");
        return object;
    }

    public void setPlayer1ID(String id){
        if(player1ID != null){
            player1ID = id;
        }
    }

    public void setPlayer2ID(String id){
        if(player2ID != null){
            player2ID = id;
        }
    }

    public String getPlayer1ID(){
        return player1ID;
    }

    public String getPlayer2ID(){
        return player2ID;
    }

    public void setGameData(Game game){
        currentGameState = game;
    }

    public Game getCurrentGameState(){
        return currentGameState;
    }
}
