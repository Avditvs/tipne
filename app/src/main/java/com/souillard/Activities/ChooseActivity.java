package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;
import com.souillard.R;


public class ChooseActivity extends Activity {


    private String choixMode;
    private CardView mots = null;
    private CardView model = null;
    private CardView verbes = null;
    private CardView abbrev = null;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    //@Override

/*    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }

    public class MyAdapter extends FragmentPagerAdapter {
        private String[] titles = { "Vocabulaire","Model","Verbes","Abbrevs" };

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:{
                    return ChooseListActivity.newInstance(position);
                }
                case 1:{
                    return FileViewerFragment.newInstance(position);
                }
                case 3:{
                    return FileViewerFragment.newInstance(position);
                }
                case 4:{
                    return FileViewerFragment.newInstance(position);
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }*/

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
                Intent intent = new Intent(ChooseActivity.this, ChooseListActivity.class);
                Bundle extras = new Bundle();
                extras.putString("choixUtilisateur", "verbes");
                extras.putString("mode", choixMode);
                intent.putExtras(extras);
                startActivity(intent);
            }
            //else {
            // Intent intent = new Intent(ChooseActivity.this, EvaluationVerbsActivity.class);
            // startActivity(intent);
            //}
        }
    };

    //private View.OnClickListener clickListenerAbbrev = new View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    //        Intent intent = new Intent(ChooseActivity.this, ChooseListActivity.class);
    //        Bundle extras = new Bundle();
    //            extras.putString(choixUtilisateur, "mots");
    //            extras.putString(mode, choixMode);
    //            intent.putExtras(extras);
    //        startActivity(intent);
    //    };
}
