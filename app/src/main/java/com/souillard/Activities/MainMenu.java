package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.souillard.SpeechToText.SpeechtoTextActivity;

import com.souillard.R;


public class MainMenu extends Activity {

    private Intent intent = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void onScreenClick(View view) {

        intent = new Intent(this, SpeechtoTextActivity.class);
        startActivity(intent);
    }

}
