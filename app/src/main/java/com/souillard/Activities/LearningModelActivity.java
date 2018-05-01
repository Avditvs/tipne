package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.R;
import com.souillard.BasesDeDonnées.models.ModelsDAO;

import static com.souillard.Activities.ChooseListActivity.nameModel;


public class LearningModelActivity extends Activity {

    AppDataBase db = AppDataBase.getAppDatabase(LearningModelActivity.this);
    ModelsDAO dbModels = db.ModelsDAO();
    private boolean playing = false;
    private boolean alreadyPlayed = false;
    private Button listeningButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_model_activity);
        TextView text = findViewById(R.id.name);
        listeningButton = findViewById(R.id.TextToSpeech);

        Intent previousIntent = getIntent();
        String properName = previousIntent.getStringExtra(nameModel);
        String rawName = dbModels.getNameOfModel(properName);
        String fileName = dbModels.getAudioName(properName);

        text.setText(properName);

        final int speakerNoir = getResources().getIdentifier("speaker_noir", "drawable", getPackageName());
        final int speakerBleu = getResources().getIdentifier("speaker_bleu", "drawable", getPackageName());
        int audioID = getResources().getIdentifier(fileName,"raw", getPackageName());
        //int drawID = getResources().getIdentifier(fileName, "drawable", getPackageName());


        final MediaPlayer mediaPlayer = MediaPlayer.create(this, audioID);

        listeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playing) {
                    mediaPlayer.pause();
                    playing = false;
                    listeningButton.setBackgroundResource(speakerNoir);
                }
                else {
                    mediaPlayer.start();
                    playing = true;
                    listeningButton.setBackgroundResource(speakerBleu);
                }
            }
        });
    }



}

