package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.souillard.R;
import com.souillard.customViews.MenuButton;


public class MainActivity extends Activity {
    MenuButton button = null;
    MenuButton button2 = null;
    private Intent intent = null;
    private Intent intent2 = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////Récupération des views/////////////////
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button.setOnClickListener(listener);
        button2.setOnClickListener(listener2);



    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            intent = new Intent(getApplicationContext(), LearningVerbsActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent);
                }
            }, 300);

        }
    };
    private View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            intent2 = new Intent(getApplicationContext(), testSTT.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent2);
                }
            }, 300);

        }
    };

    @Override
    public void onBackPressed(){
        //Ne rien faire bite
    }

}
