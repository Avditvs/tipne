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
    private MenuButton btApprentissage = null;
    private MenuButton btStt = null;
    private MenuButton btEval = null;
    private Intent intent = null;
    private Intent intent4 = null;
    private Intent intent2 = null;
    public final static String mode = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////Récupération des views/////////////////
        btApprentissage = findViewById(R.id.apprentissage);
        btApprentissage.setOnClickListener(listenerBtApprentissage);

        btStt = findViewById(R.id.STT);
        btStt.setOnClickListener(listenerBtStt);

        btEval = findViewById(R.id.evaluations);
        btEval.setOnClickListener(listenerBtEval);
    }


    private View.OnClickListener listenerBtEval = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent4 = new Intent(getApplicationContext(), ChooseListActivity.class);
            Handler handler = new Handler();
            Bundle extras = new Bundle();
            extras.putString("mode", "evaluation");
            extras.putString("choixUtilisateur", "mots");
            intent4.putExtras(extras);
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent4);
                }
            }, 300);
        }
    };

    private View.OnClickListener listenerBtApprentissage = new View.OnClickListener() {
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


}
