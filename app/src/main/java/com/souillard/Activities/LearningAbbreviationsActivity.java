package com.souillard.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.abreviations.AbreviationsDAO;
import com.souillard.BasesDeDonnées.verbes.VerbesDAO;
import com.souillard.R;

import java.util.Locale;

    public class LearningAbbreviationsActivity extends Activity {

        AppDataBase db = AppDataBase.getAppDatabase(LearningAbbreviationsActivity.this);
        AbreviationsDAO dbAbreviations = db.AbreviationsDAO();
}
