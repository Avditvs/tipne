package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.souillard.R;

public class ChooseExerciseActivity extends Activity {

    private String choixMode;
    private CardView grammar = null;
    private CardView mots = null;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_exercise_activity);

        Bundle extras = getIntent().getExtras();
        choixMode = extras.getString("mode");

        mots = findViewById(R.id.vocabulaire);
        grammar = findViewById(R.id.grammar);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mots.setOnClickListener(clickListenerMots);
        grammar.setOnClickListener(clickListenerGrammar);
    }

    private View.OnClickListener clickListenerGrammar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChooseExerciseActivity.this, ChooseListActivity.class);
            Bundle extras = new Bundle();
            extras.putString("choixUtilisateur", "grammar");
            extras.putString("mode", choixMode);
            intent.putExtras(extras);
            startActivity(intent);
        }
    };

    private View.OnClickListener clickListenerMots = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChooseExerciseActivity.this, ChooseListActivity.class);
            Bundle extras = new Bundle();
            extras.putString("choixUtilisateur", "mots");
            extras.putString("mode", choixMode);
            intent.putExtras(extras);
            startActivity(intent);
        }
    };

}
