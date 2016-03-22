package com.example.gwen.vfacture;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class FactureAdapter extends ArrayAdapter<Facture> {

    private DecimalFormat decimal_formatter = new DecimalFormat("0.00");

    public FactureAdapter(Context context, List<Facture> factures) {
        super(context, 0, factures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ligne_liste_fact,parent, false);
        }

        FactureViewHolder viewHolder = (FactureViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new FactureViewHolder();
            viewHolder.textView_ligne_type = (TextView) convertView.findViewById(R.id.textView_ligne_type);
            viewHolder.textView_ligne_num_fact = (TextView) convertView.findViewById(R.id.textView_ligne_num_fact);
            viewHolder.textView_ligne_date_fact = (TextView) convertView.findViewById(R.id.textView_ligne_date_fact);
            viewHolder.textView_ligne_client = (TextView) convertView.findViewById(R.id.textView_ligne_client);
            viewHolder.textView_ligne_code_client = (TextView) convertView.findViewById(R.id.textView_ligne_code_client);
            viewHolder.textView_ligne_montant = (TextView) convertView.findViewById(R.id.textView_ligne_montant);
            viewHolder.textView_ligne_solde = (TextView) convertView.findViewById(R.id.textView_ligne_solde);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Facture> factures
        Facture factures = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        String couleur_type;
        if (factures.getSolde() == 0){
            couleur_type = "#4ab746";
            viewHolder.textView_ligne_solde.setText("");
        }
        else{
            couleur_type = "#e45f58";
            viewHolder.textView_ligne_solde.setTextColor(Color.parseColor(couleur_type));
            viewHolder.textView_ligne_solde.setText(" / " + decimal_formatter.format(factures.getSolde()) + " €");
        }

        viewHolder.textView_ligne_type.setBackgroundColor(Color.parseColor(couleur_type));
        if (factures.getType().equals("p")){
            viewHolder.textView_ligne_type.setText("a".toString().toUpperCase());
        }
        else{
            viewHolder.textView_ligne_type.setText(factures.getType().toString().toUpperCase());
        }

        viewHolder.textView_ligne_num_fact.setTextColor(Color.parseColor(couleur_type));

        NumberFormat formatter_seven = new DecimalFormat("0000000");
        String num_fact = formatter_seven.format(factures.getNum_fact());
        viewHolder.textView_ligne_num_fact.setText(num_fact);

        //String num_fact = "";
        //num_fact = num_fact.format("%07d", factures.getNum_fact());
        //viewHolder.textView_ligne_num_fact.setText(num_fact);
        //viewHolder.textView_ligne_num_fact.setText(Integer.toString(factures.getNum_fact()));

        viewHolder.textView_ligne_date_fact.setText("(" + factures.getDate_fact().toString() + ")");
        viewHolder.textView_ligne_client.setText(factures.getNom_client().toString() + " " + factures.getPrenom_client());


        NumberFormat formatter_six = new DecimalFormat("000000");
        String code_client = formatter_six.format(factures.getCode_client());
        viewHolder.textView_ligne_code_client.setText("(" + code_client + ")");

        viewHolder.textView_ligne_montant.setText(decimal_formatter.format(factures.getMontant()) + " €");

        return convertView;
    }

    private class FactureViewHolder{
        public TextView textView_ligne_type;
        public TextView textView_ligne_num_fact;
        public TextView textView_ligne_date_fact;
        public TextView textView_ligne_client;
        public TextView textView_ligne_code_client;
        public TextView textView_ligne_montant;
        public TextView textView_ligne_solde;
    }
}
