package com.souillard.Activities;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.R;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.models.ModelsDAO;
import com.souillard.BasesDeDonnées.AppDataBase;
import java.lang.String;



public class ChooseListActivity extends Activity {

    private ListView mListView;
    public final static String nameList = "";
    public final static String nameModel ="";
    private TextView text;

    AppDataBase db = AppDataBase.getAppDatabase(ChooseListActivity.this);
    ListesDAO dbListes = db.ListesDAO();
    ModelsDAO dbModels = db.ModelsDAO();
    String[] namesList = dbListes.getNames();
    String[] namesListDisplay = dbListes.getProperNames();
    String choixUser;
    String modeChoisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_liste);

        //Défini notre listView
        mListView = (ListView) findViewById(R.id.listView);

        Bundle extras = getIntent().getExtras();
        choixUser = extras.getString("choixUtilisateur");
        modeChoisi = extras.getString("mode");

        if (choixUser.equals("mots")) {
            motsChoosed();
        }
        else if (choixUser.equals("model")) {
            modelsChoosed();
        }
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
                String nomliste = namesList[position];
                if (modeChoisi.equals("apprentissage")) {
                    Intent i = new Intent(ChooseListActivity.this, LearningMotsActivity.class);
                    i.putExtra(nameList, nomliste);
                    startActivity(i);
                }
                else if (modeChoisi.equals("evaluation")){
                    Intent i = new Intent (ChooseListActivity.this, EvaluationActivity.class);
                    i.putExtra(nameList, nomliste);
                    startActivity(i);
                }
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
}
