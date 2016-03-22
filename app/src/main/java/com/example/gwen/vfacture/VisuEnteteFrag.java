package com.example.gwen.vfacture;

/**
 * Created by gwen on 28/01/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VisuEnteteFrag extends Fragment{

    public static int visu_num_fact;
    public static int visu_code_client;
    public static String visu_nom_prenom_client;
    public static String visu_no_grp;
    public static int visu_cd_ag;
    public static int visu_code_1;
    public static int visu_code_2;
    public static int visu_code_3;
    public static String visu_code_fact;
    public static String visu_code_dest;
    public static String visu_date_depart;
    public static String visu_date_retour;
    public static String visu_nom_et1;
    public static String visu_nom_et2;
    public static String visu_nom_et3;
    public static String visu_nom_et4;
    public static String visu_nom_et5;
    public static String visu_nom_et6;
    public static String visu_nom_et7;

    public static String visu_adresse_de_facturation;

    private ScrollView scrollView;

    public static ArrayList<String> passagers = new ArrayList<String>();


    public VisuEnteteFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.entete_visu_frag, container, false);

        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        scrollView.setVisibility(View.VISIBLE);

        /*TextView textView_visu_num_fact = (TextView) view.findViewById(R.id.textView_visu_num_fact);
        textView_visu_num_fact.setText("Facture : " + Integer.toString(visu_num_fact));*/

        TextView textView_visu_code_client = (TextView) view.findViewById(R.id.textView_visu_code_client);
        textView_visu_code_client.setText("Client : " + Integer.toString(visu_code_client));

        /*TextView textView_visu_nom_prenom_client = (TextView) view.findViewById(R.id.textView_visu_nom_prenom_client);
        textView_visu_nom_prenom_client.setText(visu_nom_prenom_client);*/

        TextView textView_visu_no_grp = (TextView) view.findViewById(R.id.textView_visu_no_grp);
        textView_visu_no_grp.setText("Dossier : " + visu_no_grp);

        TextView textView_visu_cd_ag = (TextView) view.findViewById(R.id.textView_visu_cd_ag);
        textView_visu_cd_ag.setText("Agence : " + Integer.toString(visu_cd_ag));

        ListView listView_nom_passager = (ListView) view.findViewById(R.id.listView_nom_passager);
        //ArrayAdapter<String> adapter_passager = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, passagers);
        ArrayAdapter<String> adapter_passager = new ArrayAdapter<String>(this.getContext(), R.layout.ligne_passager, passagers);
        listView_nom_passager.setAdapter(adapter_passager);

        TextView textView_visu_code_1 = (TextView) view.findViewById(R.id.textView_visu_code_1);
        textView_visu_code_1.setText("Adulte(s) : " + Integer.toString(visu_code_1));

        TextView textView_visu_code_2 = (TextView) view.findViewById(R.id.textView_visu_code_2);
        textView_visu_code_2.setText("Enfant(s) : " + Integer.toString(visu_code_2));

        TextView textView_visu_code_3 = (TextView) view.findViewById(R.id.textView_visu_code_3);
        textView_visu_code_3.setText("Bebe(s) : " + Integer.toString(visu_code_3));

        TextView textView_visu_code_fact = (TextView) view.findViewById(R.id.textView_visu_code_fact);
        textView_visu_code_fact.setText("Ref vendeur : " + visu_code_fact);

        TextView textView_visu_code_dest = (TextView) view.findViewById(R.id.textView_visu_code_dest);
        textView_visu_code_dest.setText("Ref client : " + visu_code_dest);

        TextView textView_visu_date_depart = (TextView) view.findViewById(R.id.textView_visu_date_depart);
        textView_visu_date_depart.setText("Date depart : " + Date_Anglais_Francais(visu_date_depart));


        TextView textView_visu_date_retour = (TextView) view.findViewById(R.id.textView_visu_date_retour);
        if (visu_date_retour.equals("1899-12-30")){
            textView_visu_date_retour.setText("");
        }
        else{
            textView_visu_date_retour.setText("Date retour : " + Date_Anglais_Francais(visu_date_retour));
        }


        //Adresse de facturation
        TextView textView_adresse_de_facturation = (TextView) view.findViewById(R.id.textView_adresse_de_facturation);
        textView_adresse_de_facturation.setText(visu_adresse_de_facturation);

        TextView textView_visu_nom_et1 = (TextView) view.findViewById(R.id.textView_visu_nom_et1);
        if (visu_nom_et1 != null){
            textView_visu_nom_et1.setText("Nom : " + visu_nom_et1);
        }
        else{
            textView_visu_nom_et1.setText("");
        }

        TextView textView_visu_nom_et2 = (TextView) view.findViewById(R.id.textView_visu_nom_et2);
        if (visu_nom_et2 != null){
            textView_visu_nom_et2.setText("Adresse : " + visu_nom_et2);
        }
        else{
            textView_visu_nom_et2.setText("");
        }

        TextView textView_visu_nom_et3 = (TextView) view.findViewById(R.id.textView_visu_nom_et3);
        if (visu_nom_et3 != null){
            textView_visu_nom_et3.setText(visu_nom_et3);
        }
        else{
            textView_visu_nom_et3.setText("");
        }

        TextView textView_visu_nom_et4 = (TextView) view.findViewById(R.id.textView_visu_nom_et4);
        if (visu_nom_et4 != null){
            textView_visu_nom_et4.setText("Ville : " + visu_nom_et4);
        }
        else{
            textView_visu_nom_et4.setText("");
        }

        TextView textView_visu_nom_et5 = (TextView) view.findViewById(R.id.textView_visu_nom_et5);
        if (visu_nom_et5 != null){
            textView_visu_nom_et5.setText("Pays : " + visu_nom_et5);
        }
        else{
            textView_visu_nom_et5.setText("");
        }

        TextView textView_avant_tel = (TextView) view.findViewById(R.id.textView_avant_tel);
        TextView textView_visu_nom_et6 = (TextView) view.findViewById(R.id.textView_visu_nom_et6);
        if (visu_nom_et6 != null){
            textView_avant_tel.setText("Numero téléphone : ");
            textView_visu_nom_et6.setText(visu_nom_et6);
        }
        else{
            textView_avant_tel.setText("");
            textView_visu_nom_et6.setText("");
        }

        TextView textView_avant_mail = (TextView) view.findViewById(R.id.textView_avant_mail);
        TextView textView_visu_nom_et7 = (TextView) view.findViewById(R.id.textView_visu_nom_et7);
        if (visu_nom_et7 != null){
            textView_avant_mail.setText("Mail : ");
            textView_visu_nom_et7.setText(visu_nom_et7);
        }
        else{
            textView_avant_mail.setText("");
            textView_visu_nom_et7.setText("");
        }

        if (visu_nom_et1 == null && visu_nom_et2 == null && visu_nom_et3 == null && visu_nom_et4 == null
                && visu_nom_et5 == null && visu_nom_et6 == null && visu_nom_et7 == null){
            scrollView.setVisibility(View.INVISIBLE);
        }

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