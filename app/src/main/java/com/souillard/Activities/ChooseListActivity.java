package com.souillard.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.verbes.VerbesDAO;
import com.souillard.R;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.models.ModelsDAO;
import com.souillard.BasesDeDonnées.AppDataBase;
import java.lang.String;
import java.util.Locale;
import android.widget.Toast;

public class ChooseListActivity extends Activity {

    private ListView mListView;
    private Toolbar toolbar;
    private TextView header;
    private CardView textToSpeech = null;
    private TextToSpeech voice;
    public final static String nameList = "";
    public final static String nameModel ="";
    private TextView text;
    private int position;
    private SharedPreferences sharedPreferences;
    private PopupWindow popup;
    private int nbMots;
    private String nomliste;

    AppDataBase db = AppDataBase.getAppDatabase(ChooseListActivity.this);
    ListesDAO dbListes = db.ListesDAO();
    ModelsDAO dbModels = db.ModelsDAO();
    VerbesDAO dbVerbes = db.VerbesDAO();
    String[] namesList = dbListes.getNames();
    String[] namesListDisplay = dbListes.getProperNames();
    String[] verbFr = dbVerbes.getFr();
    String[] verbBv = dbVerbes.getBv();
    String[] verbPret = dbVerbes.getPret();
    String[] verbPart = dbVerbes.getPart();
    String choixUser;
    String modeChoisi;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_liste);
        layout = findViewById(R.id.choix_liste);

        //Défini notre listView
        mListView = (ListView) findViewById(R.id.listView);
        toolbar = findViewById(R.id.toolbar);
        header = findViewById(R.id.instructions);
        Bundle extras = getIntent().getExtras();
        choixUser = extras.getString("choixUtilisateur");
        modeChoisi = extras.getString("mode");

        if (choixUser.equals("mots")) {
            motsChoosed();
        }
        else if (choixUser.equals("model")) {
            modelsChoosed();
        }
        else if (choixUser.equals("verbes")){
            verbsChoosed();
            toolbar.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
        }
        else if (choixUser.equals("abbrev")){
            abbrevsChoosed();
        }
    }

    private void verbsChoosed() {
        CustomAdapter adapter = new CustomAdapter(ChooseListActivity.this, verbFr, verbBv, verbPret, verbPart);
        mListView.setAdapter(adapter);
        voice = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    voice.setLanguage(Locale.ENGLISH);
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                voice.speak(verbBv[position] + ' ' + verbPret[position] + ' ' + verbPart[position], TextToSpeech.QUEUE_FLUSH, null);
            }

        });
    }

    private void motsChoosed () {
        //Défini les données à afficher et comment on les affiche
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseListActivity.this,
                R.layout.card_view,R.id.file_name_text, namesListDisplay);

        //On associe ces données à la ListView
        mListView.setAdapter(adapter);

        //que faire lorsqu'on clique sur un item ?
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nomliste = namesList[position];
                sharedPreferences = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
                int annee = sharedPreferences.getInt("user_year", 1);
                if (annee == 1) {
                    Toast.makeText(ChooseListActivity.this, "Liste entière ?", Toast.LENGTH_SHORT).show();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View aview = inflater.inflate(R.layout.half_list, null);
                    popup = new PopupWindow(aview, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    final Switch aSwitch = aview.findViewById(R.id.hlchoose);


                    Button quitButton = aview.findViewById(R.id.btchoose);

                    quitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (aSwitch.isChecked()) {
                                nbMots = dbListes.getNbWordsHalf(nomliste);
                            } else {
                                nbMots = dbListes.getNbWords(nomliste);
                            }
                            popup.dismiss();
                            launchingActivity(nomliste);
                        }
                    });

                }

                else {
                    launchingActivity(nomliste);
                }
                popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });
    }

    private void modelsChoosed(){
        final String[] properNames = dbModels.getProperNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseListActivity.this,
                R.layout.card_view,R.id.file_name_text, properNames);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nomModel = properNames[position];
                if (modeChoisi.equals("apprentissage")){
                    Intent i = new Intent(ChooseListActivity.this, LearningModelActivity.class);
                    i.putExtra(nameModel, nomModel);
                    startActivity(i);
                }
            }
        });
    }

    private void abbrevsChoosed(){
        final String[] abbrevList = getResources().getStringArray(R.array.ListesAbreviations);

        String[] properAbbrevList = setProperList(abbrevList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseListActivity.this,
                R.layout.card_view,R.id.file_name_text, properAbbrevList);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameList = abbrevList[position];
                Intent i = new Intent(ChooseListActivity.this, LearningMotsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("nameList", nameList);
                extras.putString("choixUtilisateur", "abbrev");
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }

    private String[] setProperList (String[] nonProperList){
        int i = 0;
        String[] properList = new String[6];
        for (String linkedList : nonProperList){
            String[] parsedList = linkedList.split("_");
            String aProperList = "";
            for (String pieceList : parsedList){
                aProperList = aProperList + ' ' + pieceList;
            }
            properList[i] = aProperList;
            i++;
        }
        return properList;
    }

    private void launchingActivity(String nomliste){
        if (modeChoisi.equals("apprentissage")) {
            Intent i = new Intent(ChooseListActivity.this, LearningMotsActivity.class);
            Bundle extras = new Bundle();
            extras.putString("nameList", nomliste);
            extras.putString("choixUtilisateur", "mots");
            extras.putInt("nbMots", nbMots);
            i.putExtras(extras);
            startActivity(i);
        } else if (modeChoisi.equals("evaluation")) {
            Intent i = new Intent(ChooseListActivity.this, EvaluationActivity.class);
            Bundle extras = new Bundle();
            extras.putString("nameList", nomliste);
            extras.putInt("nbMots", nbMots);
            i.putExtras(extras);
            startActivity(i);
        }
        else if (modeChoisi.equals("entrainement")){
            Intent i = new Intent(ChooseListActivity.this, GameOneActivity.class);
            Bundle extras = new Bundle();
            extras.putString("nameList", nomliste);
            i.putExtras(extras);
            startActivity(i);
        }
    }

}


