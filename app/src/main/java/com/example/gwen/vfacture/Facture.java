package com.example.gwen.vfacture;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gwen on 27/01/2016.
 */
public class Facture {
    private int num_fact;
    private String date_fact;
    private int code_client;
    private String type;
    private String nom_client;
    private String prenom_client;
    private Double acompte;
    private Double total_fact;
    private Double reglement;
    private Double solde;

    private Double montant;

    private String email;


    public Facture(int un_num_fact,String une_date_fact, int un_code_client, String un_type, String un_nom_client,
                   String un_prenom_client, Double un_acompte, Double un_total_fact, Double un_reglement, Double un_solde, String un_email){
        num_fact = un_num_fact;
        date_fact = Date_Anglais_Francais(une_date_fact);
        code_client = un_code_client;
        type = un_type;
        nom_client = un_nom_client;
        prenom_client = un_prenom_client;
        acompte = un_acompte;
        total_fact = un_total_fact;
        reglement = un_reglement;
        solde = un_solde;
        email = un_email;

        montant = un_acompte + un_total_fact + un_reglement;
    }

    public int getNum_fact(){
        return num_fact;
    }
    public String getDate_fact(){
        return date_fact;
    }
    public int getCode_client(){
        return  code_client;
    }
    public String getType(){
        return type;
    }
    public String getNom_client(){
        return nom_client;
    }
    public String getPrenom_client(){
        return prenom_client;
    }
    public Double getAcompte(){
        return acompte;
    }
    public Double getTotal_fact(){
        return total_fact;
    }
    public Double getReglement(){
        return reglement;
    }
    public Double getSolde(){
        return solde;
    }
    public String getEmail(){
        return email;
    }

    public Double getMontant(){
        return montant;
    }

    public void setType(String type) {
        this.type = type;
    }

    public final String Date_Anglais_Francais(String date_en_anglais) {
        String dateStr = date_en_anglais;
        try {
            DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = srcDf.parse(dateStr);

            DateFormat destDf = new SimpleDateFormat("dd-MM-yyyy");
            dateStr = destDf.format(date);
            //System.out.println("Converted date is : " + dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }
}
