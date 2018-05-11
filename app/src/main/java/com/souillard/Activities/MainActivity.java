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
    private MenuButton btEntrainement = null;
    private MenuButton btStt = null;
    private MenuButton btVerbes = null;
    private MenuButton btEval = null;
    private Intent intent = null;
    private Intent intent2 = null;
    public final static String mode = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////Récupération des views/////////////////
        btEntrainement = findViewById(R.id.entrainement);
        btStt = findViewById(R.id.STT);
        btEntrainement.setOnClickListener(listenerBtEntrainement);
        btStt.setOnClickListener(listenerBtStt);
        btVerbes = findViewById(R.id.bt_verbes);
        btVerbes.setOnClickListener(listenerVerbes);
        btEval = findViewById(R.id.bt_eval);
        btEval.setOnClickListener(listenerbtEval);



    }

    private View.OnClickListener listenerbtEval = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Intent intent4 = new Intent(getApplicationContext(), EvaluationActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent4);
                }
            }, 300);
        }
    };

    private View.OnClickListener listenerBtEntrainement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            intent = new Intent(getApplicationContext(), ChooseActivity.class);
            Handler handler = new Handler();
            intent.putExtra(mode, "apprentissage");
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent);
                }
            }, 300);

        }
    };
    private View.OnClickListener listenerBtStt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            intent2 = new Intent(getApplicationContext(), StatsActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent2);
                }
            }, 300);

        }
    };

    private View.OnClickListener listenerVerbes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Intent intent3 = new Intent(getApplicationContext(), LearningVerbsActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent3);
                }
            }, 300);
        }
    };

    @Override
    public void onBackPressed(){
        //Ne rien faire bite
    }

}
