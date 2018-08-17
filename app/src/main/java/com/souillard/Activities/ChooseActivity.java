package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;
import com.souillard.R;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;


public class ChooseActivity extends Activity {


    private String choixMode;
    private CardView mots = null;
    private CardView model = null;
    private CardView verbes = null;
    private CardView abbrev = null;
    private CardView grammar = null;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_activity);

        Intent i = getIntent();
        choixMode = i.getStringExtra(MainActivity.mode);

        mots = findViewById(R.id.vocabulaire);
        model = findViewById(R.id.model);
        verbes = findViewById(R.id.verbes);
        abbrev = findViewById(R.id.abbréviations);
        grammar = findViewById(R.id.grammar);


        ////////////////Publicité/////////////////////////
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ////////////////Listeners////////////////////////
        mots.setOnClickListener(clickListenerMots);
        model.setOnClickListener(clickListenerModel);
        verbes.setOnClickListener(clickListenerVerbes);
        abbrev.setOnClickListener(clickListenerAbbrev);
        grammar.setOnClickListener(clickListenerGrammar);

    }

    private View.OnClickListener clickListenerMots = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChooseActivity.this, ChooseListActivity.class);
            Bundle extras = new Bundle();
            extras.putString("choixUtilisateur", "mots");
            extras.putString("mode", choixMode);
            intent.putExtras(extras);
            startActivity(intent);
        }
    };

    private View.OnClickListener clickListenerModel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent (ChooseActivity.this, ChooseListActivity.class);
            Bundle extras = new Bundle();
            extras.putString("choixUtilisateur", "model");
            extras.putString("mode", choixMode);
            intent.putExtras(extras);
            startActivity(intent);
        }
    };

    private View.OnClickListener clickListenerVerbes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (choixMode.equals("apprentissage")) {
                Intent intent = new Intent(ChooseActivity.this, ChooseListActivity.class);
                Bundle extras = new Bundle();
                extras.putString("choixUtilisateur", "verbes");
                extras.putString("mode", choixMode);
                intent.putExtras(extras);
                startActivity(intent);
            }
            //else {
            // Intent intent = new Intent(ChooseActivity.this, EvaluationVerbsActivity.class);
            // startActivity(intent);
            //}
        }
    };

    private View.OnClickListener clickListenerAbbrev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChooseActivity.this, ChooseListActivity.class);
            Bundle extras = new Bundle();
            extras.putString("choixUtilisateur", "abbrev");
            extras.putString("mode", choixMode);
            intent.putExtras(extras);
            startActivity(intent);
        }
    };

    private View.OnClickListener clickListenerGrammar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChooseActivity.this, ChooseListActivity.class);
            Bundle extras = new Bundle();
            extras.putString("choixUtilisateur", "grammar");
            extras.putString("mode", choixMode);
            intent.putExtras(extras);
            startActivity(intent);
        }
    };
}
