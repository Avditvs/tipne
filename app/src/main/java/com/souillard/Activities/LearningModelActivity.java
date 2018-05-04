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
    private boolean prompt = false;
    private Button listeningButton;
    private MediaPlayer mediaPlayer = null;
    private TextView nomModel;
    private TextView model;
    private int audioID;
    private int drawID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_model_activity);
        nomModel = findViewById(R.id.name);
        listeningButton = findViewById(R.id.TextToSpeech);
        model = findViewById(R.id.model);

        Intent previousIntent = getIntent();
        String properName = previousIntent.getStringExtra(nameModel);
        String rawName = dbModels.getNameOfModel(properName);
        String fileName = dbModels.getAudioName(properName);

        nomModel.setText(properName);

        final int speakerNoir = getResources().getIdentifier("speaker_noir", "drawable", getPackageName());
        final int speakerBleu = getResources().getIdentifier("speaker_bleu", "drawable", getPackageName());
        audioID = getResources().getIdentifier(fileName,"raw", getPackageName());
        drawID = getResources().getIdentifier(fileName, "drawable", getPackageName());


        mediaPlayer = MediaPlayer.create(this, audioID);

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

    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void onClick(View v){
        if (prompt){
            model.setText("Ceci est le dialogue complet");
            prompt = false;
        }
        else {
            model.setText("");
            prompt = true;
            model.setBackgroundResource(drawID);
        }
    }
}

