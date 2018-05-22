package com.souillard.Activities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.souillard.R;

class VerbAdapter extends ArrayAdapter<String>
    {
        String[] verbFr;
        String[] verbBv;
    String[] verbPret;
    String[] verbPart;

    LayoutInflater mInflater;
    public VerbAdapter(Context context, String[] verbFr, String verbBv[], String[] verbPret, String verbPart[])
    {
        super(context,R.layout.verbes_card,verbFr);
        this.verbFr = verbFr;
        this.verbBv = verbBv;
        this.verbPret = verbPret;
        this.verbPart = verbPart;
        mInflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.verbes_card,parent,false);
            holder = new ViewHolder();
            holder.tv1 = (TextView)convertView.findViewById(R.id.trad);
            holder.tv2 = (TextView)convertView.findViewById(R.id.base_verbale);
            holder.tv3 = (TextView)convertView.findViewById(R.id.preterit);
            holder.tv4 = (TextView)convertView.findViewById(R.id.participe_passe);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tv1.setText(verbFr[position]);
        holder.tv2.setText(verbBv[position]);
        holder.tv3.setText(verbPret[position]);
        holder.tv4.setText(verbPart[position]);
        return convertView;
    }
    static class ViewHolder
    {
        TextView tv1,tv2, tv3, tv4;
    }
}