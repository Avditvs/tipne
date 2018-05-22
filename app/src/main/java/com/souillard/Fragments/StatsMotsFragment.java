package com.souillard.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.evaluations.Evaluations;
import com.souillard.BasesDeDonnées.evaluations.EvaluationsDAO;
import com.souillard.BasesDeDonnées.listes.ListesDAO;

import com.souillard.R;
import com.souillard.customViews.ResizingListView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatsMotsFragment extends Fragment {

    private ListesDAO listesDAO;
    private EvaluationsDAO evaluationsDAO;
    ArrayList<StatsListeMot> listsStatsmots;
    ListView listView;
    TextView text;
    LinearLayout scrollingLayout;
    private static MotsAdapter adapter;
    private LineChart lineChart;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    Set<Integer> idUniqueEvals;
    ArrayList<String> uniqueNamesEvals;

    public  StatsMotsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listesDAO = AppDataBase.getAppDatabase(getContext()).ListesDAO();
        evaluationsDAO = AppDataBase.getAppDatabase(getContext()).EvaluationsDAO();
        idUniqueEvals = new HashSet<Integer>(evaluationsDAO.getEvalsIdListe());
        listsStatsmots = new ArrayList<>();
        uniqueNamesEvals = new ArrayList<>();
        for(int id : idUniqueEvals){
            listsStatsmots.add(new StatsListeMot(id));
            uniqueNamesEvals.add(listesDAO.getProperName(id));
        }




        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, uniqueNamesEvals);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_stats_mots, container, false);/*
        listView = view.findViewById(R.id.listView);
        adapter = new MotsAdapter(listsStatsmots, getContext());*/
        scrollingLayout = view.findViewById(R.id.scrollingLayout);
        lineChart = view.findViewById(R.id.lineChart);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setValueFormatter(new DateAxisValueFormatter(null));
        lineChart.getXAxis().setLabelRotationAngle(-45f);
        spinner = view.findViewById(R.id.spinnerselector);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(selectionListener);
        return view;

    }


    AdapterView.OnItemSelectedListener selectionListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            List<Entry> entries= new ArrayList<>();
            parent.getItemAtPosition(position);
            String test = uniqueNamesEvals.get(position);
            int idListe = listesDAO.getIdFromProperName(uniqueNamesEvals.get(position));
            setTextView(idListe);
            List<Long> epochs = evaluationsDAO.getEpochs(idListe);
            List<Float> notes = evaluationsDAO.getListNotes(idListe);

            for(int i=0;i<notes.size();i++){
                entries.add(new Entry((epochs.get(i)), notes.get(i)));
            }
            LineDataSet dataSet = new LineDataSet(entries, "Notes");
            LineData lineData = new LineData(dataSet);
            lineChart.setData(lineData);
            lineChart.invalidate(); // refresh

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    public void setTextView(Integer idListe){
        float mean;
        View view;

        view = getView();

        text=view.findViewById(R.id.nbevaltest);
        text.setText(String.valueOf(evaluationsDAO.countEvals(idListe)));
        text=view.findViewById(R.id.mean);
        mean = mean(evaluationsDAO.getListNotes(idListe));
        text.setText(String.valueOf(mean));
        text=view.findViewById(R.id.last);
        text.setText(String.valueOf(evaluationsDAO.getLastNote(idListe)));
    }

    public float mean(List<Float> liste){
        float somme = 0;
        for(float note : liste){
            somme +=note;
        }
        return somme/liste.size();
    }

    private class StatsListeMot{
        private int idListe;
        private String nom;
        private int nbEval;
        private float mean;
        private float last;

        public StatsListeMot(int id){
            idListe = id;
            nom = listesDAO.getProperName(id);
            nbEval = evaluationsDAO.countEvals(id);
            mean = mean(evaluationsDAO.getListNotes(id));
            last = evaluationsDAO.getLastNote(id);
        }


        public String getNom() {
            return nom;
        }

        public int getNbEval() {
            return nbEval;
        }

        public float getLast() {
            return last;
        }

        public float getMean() {
            return mean;
        }

        private float mean(List<Float> liste){
            float somme = 0;
            for(float note : liste){
                somme +=note;
            }
            return somme/liste.size();
        }

    }


    public class MotsAdapter extends ArrayAdapter<StatsListeMot>{

        private ArrayList<StatsListeMot> dataset;
        private Context mContext;

        public class ViewHolder{
            TextView nomView;
            TextView nbEvalView;
            TextView lastView;
            TextView meanView;
        }

        public MotsAdapter(ArrayList<StatsListeMot> data, Context context){
            super(context, R.layout.stats_mots_line, data);
            this.dataset = data;
            this.mContext = context;
        }


        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            StatsListeMot statsListeMot = getItem(position);
            ViewHolder viewHolder;


            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.stats_mots_line, parent, false);
                viewHolder.nomView = convertView.findViewById(R.id.nom);
                viewHolder.nbEvalView = convertView.findViewById(R.id.nbevaltest);
                viewHolder.lastView = convertView.findViewById(R.id.mean);
                viewHolder.meanView = convertView.findViewById(R.id.last);


                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            int evals = statsListeMot.getNbEval();
            viewHolder.nomView.setText(statsListeMot.getNom());
            viewHolder.nbEvalView.setText(String.valueOf(evals));
            viewHolder.meanView.setText(String.valueOf(statsListeMot.getMean()));
            viewHolder.lastView.setText(String.valueOf(statsListeMot.getLast()));
            // Return the completed view to render on screen
            return convertView;



        }


    }



    class DateAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");

        public DateAxisValueFormatter(String[] values) {
            this.mValues = values; }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return sdf.format(new Date((long) value));
        }
    }


}


