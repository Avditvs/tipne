package com.souillard.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.Normalizer2;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.DataBaseBuilder;
import com.souillard.BasesDeDonnées.evaluations.Evaluations;
import com.souillard.Notifications.NotificationsReceiver;
import com.souillard.R;

import java.util.Calendar;
import com.google.android.gms.ads.MobileAds;


public class DataBaseActivity  extends Activity {

    private boolean dbIsLoaded = false;
    private boolean classeEstChoisie = true;
    private Intent intent = null;
    private TextView text;
    private SharedPreferences sharedPreferences;
    private LinearLayout layout;
    private PopupWindow popup;
    private ProgressBar progressBar;
    private ImageView checkView;
    private SharedPreferences.Editor spEditor;
    private AlarmManager am;
    private InterstitialAd mInterstitialAd;
    private int nbOfLaunch;


    public void onCreate (Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);
        layout = findViewById(R.id.databaseact);
        text = findViewById(R.id.affdb);
        progressBar = findViewById(R.id.progressbar_db);
        checkView = findViewById(R.id.check_db);
        intent = new Intent(this, MainActivity.class);

        MobileAds.initialize(this, "ca-app-pub-5703356156338006~8507828990");
        sharedPreferences = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
        boolean firstLaunch = sharedPreferences.getBoolean("first_launch", true);
        if (firstLaunch){
            classeEstChoisie = false;
          layout.post(new Runnable() {
              @Override
              public void run() {
                  onFirstLaunch();
              }
          });

        }
        else{
            threadDb.start();
        }

        ////////////////////Publicités///////////////////////
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        sharedPreferences = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
        nbOfLaunch = sharedPreferences.getInt("pub", 0);





        }

    private Thread threadDb = new Thread(new Runnable() {
        @Override
        public void run() {
            int annee = sharedPreferences.getInt("user_year", 1);
            DataBaseBuilder dataBaseBuilder = new DataBaseBuilder(getApplicationContext());
            dataBaseBuilder.buildDataBase(annee);


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            dbIsLoaded = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("Chargement terminé, appuyez pour continuer");
                    progressBar.animate().alpha(0.0f).setDuration(300);
                    AnimatedVectorDrawable check = (AnimatedVectorDrawable) checkView.getDrawable();
                    check.start();
                }
            });

        }
    });


    public void onScreenClick(View view) {
        if(dbIsLoaded&&classeEstChoisie){
            if (nbOfLaunch==5){
                nbOfLaunch=4;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
            else{
                nbOfLaunch++;
            }
            spEditor.putInt("pub", nbOfLaunch);
            spEditor.commit();
            startActivity(intent);
        }
        else{
            Log.i("Base", "Pas encore chargée");
        }
    }


    public void onFirstLaunch(){
        Toast.makeText(this, "premier lancement", Toast.LENGTH_SHORT ).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first_launch", false);
        editor.putInt("pub", 3);
        editor.commit();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.year_choose, null);
        popup = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final Switch aSwitch = view.findViewById(R.id.ychooseswitch);

        Button quitButton = view.findViewById(R.id.btchoose);

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year;

                if(aSwitch.isChecked()){
                    year = 2;
                }
                else{
                    year = 1;
                }


                spEditor.putInt("user_year", year);
                spEditor.commit();



                popup.dismiss();
                classeEstChoisie = true;
                threadDb.start();
                clearDim((ViewGroup)getWindow().getDecorView().getRootView());
            }
        });

        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
        applyDim((ViewGroup)getWindow().getDecorView().getRootView(), 210);

        ////////////////////Notifications///////////////////////////

        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ajouterAlarme();

    }

    public void applyDim(@NonNull ViewGroup parent, int dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha(dimAmount);

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    @Override
    public void onBackPressed(){
    }

    private void ajouterAlarme(){
        Intent alarmIntent = new Intent(this, NotificationsReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.DAY_OF_WEEK, 7);
        cal.set(Calendar.HOUR_OF_DAY, 19);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);


        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY * 7, pendingIntent);
    }
}
