package com.example.gwen.vfacture;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gwen on 11/02/2016.
 */
public class Reglement {
    private String DateReg;
    private String libelle;
    private String libellebis;

    public Reglement (String uneDateReg, String unLibelle, String unLibellebis){
        this.DateReg = Date_Anglais_Francais(uneDateReg);
        this.libelle = unLibelle;
        this.libellebis = unLibellebis;
    }

    public String getDateReg() {
        return DateReg;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getLibellebis() {
        return libellebis;
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
