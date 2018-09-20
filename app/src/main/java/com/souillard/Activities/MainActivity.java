package com.souillard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.souillard.R;
import com.souillard.customViews.MenuButton;


public class MainActivity extends Activity {
    private ViewGroup btApprentissage = null;
    private ViewGroup btEval = null;
    private ViewGroup btStats = null;
    private ViewGroup  btParams;
    private ViewGroup  btEntrain;
    private Intent intent = null;
    private Intent intent4 = null;
    private Intent intent2 = null;
    public final static String mode = "";
    private InterstitialAd mInterstitialAd;
    private int nbOfLaunch;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    private boolean pubLancée;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////////Récupération des views/////////////////
        btApprentissage = findViewById(R.id.apprentissage);
        btApprentissage.setOnClickListener(listenerBtApprentissage);

        btStats = findViewById(R.id.statistiques);
        btStats.setOnClickListener(listenerBtStats);

        btEval = findViewById(R.id.evaluations);
        btEval.setOnClickListener(listenerBtEval);

        btParams = findViewById(R.id.options);
        btParams.setOnClickListener(listenerBtParams);

        btEntrain = findViewById(R.id.entrainement);
        btEntrain.setOnClickListener(listenerBtEntrain);

        ////////////////////Publicités///////////////////////
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5703356156338006/1127568744");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        sharedPreferences = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
        nbOfLaunch = sharedPreferences.getInt("pub", 0);
        pubLancée = false;
    }


    private View.OnClickListener listenerBtEval = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //pubLauncher();
            //if (pubLancée) {
            //    mInterstitialAd.setAdListener(new AdListener() {
            //        @Override
            //        public void onAdClosed() {
            //            intent = new Intent(getApplicationContext(), ChooseExerciseActivity.class);
            //            Bundle extras = new Bundle();
            //            extras.putString("mode", "evaluation");
            //            intent.putExtras(extras);
            //            startActivity(intent);
            //        }
            //    });
            //} else {
                intent = new Intent(getApplicationContext(), ChooseExerciseActivity.class);
                Bundle extras = new Bundle();
                extras.putString("mode", "evaluation");
                intent.putExtras(extras);
                startActivity(intent);
           // }

        }
    };

    private View.OnClickListener listenerBtApprentissage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //pubLauncher();
            //if (pubLancée) {
            //    mInterstitialAd.setAdListener(new AdListener() {
            //        @Override
            //        public void onAdClosed() {
            //            intent = new Intent(getApplicationContext(), ChooseActivity.class);
            //            intent.putExtra(mode, "apprentissage");
            //            startActivity(intent);
            //        }
            //    });
            //} else {
                intent = new Intent(getApplicationContext(), ChooseActivity.class);
                intent.putExtra(mode, "apprentissage");
                startActivity(intent);
            //}
        }
    };

    private View.OnClickListener listenerBtStats = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //pubLauncher();
            //if (pubLancée) {
            //    mInterstitialAd.setAdListener(new AdListener() {
            //        @Override
            //        public void onAdClosed() {
            //            intent = new Intent(getApplicationContext(), StatsActivity.class);
            //            startActivity(intent);
            //        }
            //    });
            //} else {
                intent = new Intent(getApplicationContext(), StatsActivity.class);
                startActivity(intent);
            //}

        }
    };

    private View.OnClickListener listenerBtEntrain = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            //pubLauncher();
            //if (pubLancée) {
            //    mInterstitialAd.setAdListener(new AdListener() {
            //        @Override
            //       public void onAdClosed() {
            //            intent = new Intent(getApplicationContext(), ChooseListActivity.class);
            //            Bundle extras = new Bundle();
            //            extras.putString("mode", "entrainement");
            //            extras.putString("choixUtilisateur", "mots");
            //            ;
            //            intent.putExtras(extras);
            //            startActivity(intent);
            //        }
            //    });
            //} else {
                intent = new Intent(getApplicationContext(), ChooseListActivity.class);
                Bundle extras = new Bundle();
                extras.putString("mode", "entrainement");
                extras.putString("choixUtilisateur", "mots");;
                intent.putExtras(extras);
                startActivity(intent);
            //}
        }
    };

    private View.OnClickListener listenerBtParams = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            intent = new Intent(getApplicationContext(), ParamsActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed(){

    }

    private void pubLauncher(){
        if (nbOfLaunch==10){
            nbOfLaunch=0;
            if (mInterstitialAd.isLoaded()) {
                pubLancée = true;
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }
        else{
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            nbOfLaunch++;
            pubLancée = false;
        }
        spEditor.putInt("pub", nbOfLaunch);
        spEditor.commit();
    }

}
