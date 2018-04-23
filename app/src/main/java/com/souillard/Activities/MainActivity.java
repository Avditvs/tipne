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
    private Intent intent = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////Récupération des views/////////////////
        button = findViewById(R.id.button);

        button.setOnClickListener(listener);



    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            intent = new Intent(getApplicationContext(), ChooseListActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run() {
                    startActivity(intent);
                }
            }, 300);

        }
    };

    @Override
    public void onBackPressed(){
        //Ne rien faire bite
    }

}
