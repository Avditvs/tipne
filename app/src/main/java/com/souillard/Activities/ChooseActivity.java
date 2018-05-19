package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.souillard.R;


public class ChooseActivity extends Activity {


    private String choixMode;
    private CardView mots = null;
    private CardView model = null;
    private CardView verbes = null;
    private CardView abbrev = null;

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

        mots.setOnClickListener(clickListenerMots);
        model.setOnClickListener(clickListenerModel);
        verbes.setOnClickListener(clickListenerVerbes);
        abbrev.setOnClickListener(clickListenerAbbrev);

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
                Intent intent = new Intent(ChooseActivity.this, LearningVerbsActivity.class);
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
}
