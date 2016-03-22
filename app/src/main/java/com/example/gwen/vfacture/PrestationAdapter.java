package com.example.gwen.vfacture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class PrestationAdapter extends ArrayAdapter<Prestation> {

    private DecimalFormat decimal_formatter = new DecimalFormat("0.00");

    public PrestationViewHolder viewHolder;

    /*private int quantite_save = VisuFactTabActivity.quantite_save;
    private Double prix_unit_save = VisuFactTabActivity.prix_unit_save;
    private boolean ajout = VisuFactTabActivity.ajout;*/


    public PrestationAdapter(Context context, List<Prestation> prestations) {
        super(context, 0, prestations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        //getItem(position) va récupérer l'item [position] de la List<Prestation> prestations
        Prestation prestations = getItem(position);

        //il ne reste plus qu'à remplir notre vue

        String libelle1 = prestations.getLibelle1();
        String libelle2 = prestations.getLibelle2();
        String libelle3 = prestations.getLibelle3();
        String libelle4 = prestations.getLibelle4();
        Double prix = prestations.getPrix();
        int quantite = prestations.getQuantite();
        Double total = prestations.getTotal();
        String visibility = prestations.getVisibility();

        switch(visibility) {
            case "visible":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.ligne_prestation,parent, false);
                viewHolder = (PrestationViewHolder) convertView.getTag();
                viewHolder = new PrestationViewHolder();
                viewHolder.RelativeLayout_ligne_prest = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout_ligne_prest);
                viewHolder.textView_prest_lib_etoile = (TextView) convertView.findViewById(R.id.textView_prest_lib_etoile);
                viewHolder.textView_prest_libelle1 = (TextView) convertView.findViewById(R.id.textView_prest_libelle1);
                viewHolder.textView_prest_libelle2 = (TextView) convertView.findViewById(R.id.textView_prest_libelle2);
                viewHolder.textView_prest_libelle3 = (TextView) convertView.findViewById(R.id.textView_prest_libelle3);
                viewHolder.textView_prest_libelle4 = (TextView) convertView.findViewById(R.id.textView_prest_libelle4);
                viewHolder.textView_prest_prix = (TextView) convertView.findViewById(R.id.textView_prest_prix);
                viewHolder.textView_prest_quantite = (TextView) convertView.findViewById(R.id.textView_prest_quantite);
                viewHolder.textView_prest_total = (TextView) convertView.findViewById(R.id.textView_prest_total);
                convertView.setTag(viewHolder);

                viewHolder.textView_prest_lib_etoile.setText(" ");
                viewHolder.textView_prest_libelle1.setText(libelle1);
                if (libelle1.equals("")){
                    viewHolder.textView_prest_libelle1.setText("");
                }
                else{
                    if (libelle1.substring(0,1).equals("*")){
                        viewHolder.textView_prest_libelle1.setText(libelle1.substring(1));
                    }
                    else if (libelle1.substring(0,1).equals("#")){
                        viewHolder.textView_prest_libelle1.setText(libelle1.substring(1));
                    }
                    else {
                        viewHolder.textView_prest_libelle1.setText(libelle1);
                    }
                }
                if (libelle2.equals("")){
                    viewHolder.textView_prest_libelle2.setText("");
                }
                else{
                    if (libelle2.substring(0,1).equals("*")){
                        viewHolder.textView_prest_libelle2.setVisibility(View.GONE);
                    } else{
                        viewHolder.textView_prest_libelle2.setText(libelle2);
                    }
                }

                if (libelle3.equals("")){
                    viewHolder.textView_prest_libelle3.setText("");
                }
                else{
                    if (libelle3.substring(0,1).equals("*")){
                        viewHolder.textView_prest_libelle3.setVisibility(View.GONE);
                    } else{
                        viewHolder.textView_prest_libelle3.setText(libelle3);
                    }
                }

                if (libelle4.equals("")){
                    viewHolder.textView_prest_libelle4.setText("");
                }
                else{
                    if (libelle4.substring(0,1).equals("*")){
                        viewHolder.textView_prest_libelle4.setVisibility(View.GONE);
                    }
                    else{
                        viewHolder.textView_prest_libelle4.setText(libelle4);
                    }
                }
                viewHolder.textView_prest_prix.setText(decimal_formatter.format(prix));
                viewHolder.textView_prest_quantite.setText(Integer.toString(quantite));
                viewHolder.textView_prest_total.setText(decimal_formatter.format(prix * quantite));
                break;
            case "partielle":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.ligne_prestation,parent, false);
                viewHolder = (PrestationViewHolder) convertView.getTag();
                viewHolder = new PrestationViewHolder();
                viewHolder.RelativeLayout_ligne_prest = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout_ligne_prest);
                viewHolder.textView_prest_lib_etoile = (TextView) convertView.findViewById(R.id.textView_prest_lib_etoile);
                viewHolder.textView_prest_libelle1 = (TextView) convertView.findViewById(R.id.textView_prest_libelle1);
                viewHolder.textView_prest_libelle2 = (TextView) convertView.findViewById(R.id.textView_prest_libelle2);
                viewHolder.textView_prest_libelle3 = (TextView) convertView.findViewById(R.id.textView_prest_libelle3);
                viewHolder.textView_prest_libelle4 = (TextView) convertView.findViewById(R.id.textView_prest_libelle4);
                viewHolder.textView_prest_prix = (TextView) convertView.findViewById(R.id.textView_prest_prix);
                viewHolder.textView_prest_quantite = (TextView) convertView.findViewById(R.id.textView_prest_quantite);
                viewHolder.textView_prest_total = (TextView) convertView.findViewById(R.id.textView_prest_total);
                convertView.setTag(viewHolder);

                viewHolder.textView_prest_lib_etoile.setText(" ");
                if (libelle1.equals("")){
                    viewHolder.textView_prest_libelle1.setText("");
                }
                else {
                    viewHolder.textView_prest_libelle1.setText(libelle1.substring(1));
                }
                if (libelle2.equals("")){
                    viewHolder.textView_prest_libelle2.setText("");
                }
                else{
                    if (libelle2.substring(0,1).equals("*")){
                        viewHolder.textView_prest_libelle2.setVisibility(View.GONE);
                    } else{
                        viewHolder.textView_prest_libelle2.setText(libelle2);
                    }
                }

                if (libelle3.equals("")){
                    viewHolder.textView_prest_libelle3.setText("");
                }
                else{
                    if (libelle3.substring(0,1).equals("*")){
                        viewHolder.textView_prest_libelle3.setVisibility(View.GONE);
                    } else{
                        viewHolder.textView_prest_libelle3.setText(libelle3);
                    }
                }

                if (libelle4.equals("")){
                    viewHolder.textView_prest_libelle4.setText("");
                }
                else{
                    if (libelle4.substring(0,1).equals("*")){
                        viewHolder.textView_prest_libelle4.setVisibility(View.GONE);
                    }
                    else{
                        viewHolder.textView_prest_libelle4.setText(libelle4);
                    }
                }
                viewHolder.textView_prest_prix.setText("");
                viewHolder.textView_prest_quantite.setText("");
                viewHolder.textView_prest_total.setText("");
                break;
            case "cache":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.blank_layout,parent, false);
                viewHolder = (PrestationViewHolder) convertView.getTag();
                viewHolder = new PrestationViewHolder();
                convertView.setTag(viewHolder);
                break;
        }

        return convertView;
    }

    private class PrestationViewHolder{
        public RelativeLayout RelativeLayout_ligne_prest;

        public TextView textView_prest_lib_etoile;
        public TextView textView_prest_libelle1;
        public TextView textView_prest_libelle2;
        public TextView textView_prest_libelle3;
        public TextView textView_prest_libelle4;

        public TextView textView_prest_prix;
        public TextView textView_prest_quantite;
        public TextView textView_prest_total;
    }

}
