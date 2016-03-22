package com.example.gwen.vfacture;

/**
 * Created by gwen on 28/01/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VisuReglementFrag extends Fragment{

    public static Double visu_total_brut;
    public static Double visu_com_tva;
    public static Double visu_total_net;
    public static Double visu_acompte;
    public static String visu_acompte_num;
    public static Double visu_reglement;
    public static Double visu_total_fact;

    public static String visu_commentaire1;
    public static String visu_commentaire2;
    public static String visu_commentaire3;

    public static String visu_libelle;
    public static String visu_date_fact;
    public static String visu_dat_echeance;
    public static Double visu_solde;

    private DecimalFormat decimal_formatter = new DecimalFormat("0.00");

    private ListView listView_reglement;
    private ReglementAdapter adapter_reglement;

    public VisuReglementFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.reglement_visu_frag, container, false);

        //Total brut, com, tva
        TextView textView_visu_total_brut = (TextView) view.findViewById(R.id.textView_visu_total_brut);
        textView_visu_total_brut.setText(decimal_formatter.format(visu_total_brut));



        TextView textView_visu_com_tva = (TextView) view.findViewById(R.id.textView_visu_com_tva);
        textView_visu_com_tva.setText(decimal_formatter.format(visu_com_tva));

        //Total net
        TextView textView_visu_total_net = (TextView) view.findViewById(R.id.textView_visu_total_net);
        textView_visu_total_net.setText(decimal_formatter.format(visu_total_net));

        //Acompte, reglement
        TextView textView_visu_acompte = (TextView) view.findViewById(R.id.textView_visu_acompte);
        textView_visu_acompte.setText(decimal_formatter.format(visu_acompte));

        TextView textView_visu_acompte_num = (TextView) view.findViewById(R.id.textView_visu_acompte_num);
        textView_visu_acompte_num.setText(visu_acompte_num);

        TextView textView_visu_reglement = (TextView) view.findViewById(R.id.textView_visu_reglement);
        textView_visu_reglement.setText(decimal_formatter.format(visu_reglement));

        //Total fact
        TextView textView_visu_total_fact = (TextView) view.findViewById(R.id.textView_visu_total_fact);
        textView_visu_total_fact.setText(decimal_formatter.format(visu_total_fact));

        //Commentaire
        TextView textView_visu_commentaire1 = (TextView) view.findViewById(R.id.textView_visu_commentaire1);
        textView_visu_commentaire1.setText(visu_commentaire1);

        TextView textView_visu_commentaire2 = (TextView) view.findViewById(R.id.textView_visu_commentaire2);
        textView_visu_commentaire2.setText(visu_commentaire2);

        TextView textView_visu_commentaire3 = (TextView) view.findViewById(R.id.textView_visu_commentaire3);
        textView_visu_commentaire3.setText(visu_commentaire3);

        //Reglement, date fact, date echeance, solde
        TextView textView_visu_libelle = (TextView) view.findViewById(R.id.textView_visu_libelle);
        textView_visu_libelle.setText(visu_libelle);

        TextView textView_visu_date_fact = (TextView) view.findViewById(R.id.textView_visu_date_fact);
        textView_visu_date_fact.setText(Date_Anglais_Francais(visu_date_fact));

        TextView textView_visu_dat_echeance = (TextView) view.findViewById(R.id.textView_visu_dat_echeance);
        textView_visu_dat_echeance.setText(Date_Anglais_Francais(visu_dat_echeance));

        TextView textView_visu_solde = (TextView) view.findViewById(R.id.textView_visu_solde);
        textView_visu_solde.setText(decimal_formatter.format(visu_solde));


        listView_reglement = (ListView) view.findViewById(R.id.listView_reglement);
        adapter_reglement = new ReglementAdapter(getContext(), VisuFactTabActivity.listeReglements);
        listView_reglement.setAdapter(adapter_reglement);


        return view;
    }

    public final String Date_Anglais_Francais(String date_en_anglais){
        String dateStr = date_en_anglais;
        try{
            DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = srcDf.parse(dateStr);

            DateFormat destDf = new SimpleDateFormat("dd-MM-yyyy");
            dateStr = destDf.format(date);
            //System.out.println("Converted date is : " + dateStr);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

}