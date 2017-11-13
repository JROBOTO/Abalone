package com.jcroberts.abalone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jcroberts.abalone.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void goToMainMenu(View v){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
