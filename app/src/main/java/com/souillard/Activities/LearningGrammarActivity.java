package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.souillard.R;

public class LearningGrammarActivity extends Activity {

    int boucle = 0;
    TextView photo;
    TextView name;
    String[] photos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_grammar_activity);

        name = findViewById(R.id.name);

        photos = recupérationRessources();
        photo = findViewById(R.id.photo);
        photo.setBackgroundResource(getResources().getIdentifier(photos[boucle], "drawable", getPackageName()));


    };

    protected String[] recupérationRessources(){
        Intent i = getIntent();
        String nomExo = i.getStringExtra("exo");
        name.setText(nomExo);
        int idPhotos = getResources().getIdentifier(nomExo, "array", getPackageName());
        String[] nomsPhotos = getResources().getStringArray(idPhotos);
        return nomsPhotos;
    }

    protected void onClick (View v){
        if (boucle == photos.length-1){
            boucle = 0;
        }
        else {
            boucle ++;
        }
        photo.setBackgroundResource(getResources().getIdentifier(photos[boucle], "drawable", getPackageName()));

    }

}
