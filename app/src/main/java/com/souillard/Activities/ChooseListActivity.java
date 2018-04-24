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
import com.souillard.BasesDeDonnées.AppDataBase;



public class ChooseListActivity extends Activity {

    private ListView mListView;
    public final static String nameList = "";

    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };

    private TextView text;

    AppDataBase db = AppDataBase.getAppDatabase(ChooseListActivity.this);
    ListesDAO dbListes = db.ListesDAO();
    String[] namesList = dbListes.getNames();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_liste);

        //Défini notre listView
        mListView = (ListView) findViewById(R.id.listView);
        text = findViewById(R.id.test);

        //Défini les données à afficher et comment on les affiche
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseListActivity.this,
                R.layout.button_choix_liste,R.id.liste, namesList);

        //On associe ces données à la ListView
        mListView.setAdapter(adapter);

        //que faire lorsqu'on clique sur un item ?
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String nomliste = (String) mListView.getItemAtPosition(position);
                    Intent i = new Intent(ChooseListActivity.this, LearningMotsActivity.class);
                    i.putExtra(nameList, nomliste);
                    startActivity(i);
            }
        });
    }
}
