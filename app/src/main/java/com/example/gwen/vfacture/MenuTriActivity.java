package com.example.gwen.vfacture;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuTriActivity extends AppCompatActivity implements View.OnClickListener {

    protected static Connection conn;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    protected static List<Facture> listeFactures = new ArrayList<Facture>() ;

    //Button du choix consulter par
    private Button button_consult_numero;
    private Button button_consult_date;
    private Button button_consult_client;

    //Les RelativeLayout avec leurs composants
    //Par date
    private RelativeLayout RelativeLayout_consult_date;
    private EditText editText_consult_date_du;
    private EditText editText_consult_date_au;
    private Button button_consult_date_chercher;
    //Par client
    private RelativeLayout RelativeLayout_consult_client;
    private EditText editText_consult_code_client;
    private Button button_consult_client_chercher;
    private EditText editText_consult_client_date_du;
    private EditText editText_consult_client_date_au;
    private ImageButton imageButton_consult_client_reset_date_du;
    private ImageButton imageButton_consult_client_reset_date_au;
    //Par numero de facture
    private RelativeLayout RelativeLayout_consult_facture;
    private EditText editText_consult_num_facture;
    private Button button_consult_facture_chercher;

    //Les calendrier
    private DatePickerDialog DatePickerDialogDu;
    private DatePickerDialog DatePickerDialogAu;
    private DatePickerDialog DatePickerDialogClientDu;
    private DatePickerDialog DatePickerDialogClientAu;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tri);
        //imageButton_consult_client_reset_date_du.setColorFilter(getColor(R.color.colorButton), PorterDuff.Mode.SRC_ATOP);

        conn = LoginActivity.conn;
        //Button du choix consulter par
        button_consult_numero = (Button) findViewById(R.id.button_consult_numero);
        button_consult_date = (Button) findViewById(R.id.button_consult_date);
        button_consult_client = (Button) findViewById(R.id.button_consult_client);

        //Les RelativeLayout avec leurs composants
        //Par date
        RelativeLayout_consult_date = (RelativeLayout) findViewById(R.id.RelativeLayout_consult_date);
        editText_consult_date_du = (EditText) findViewById(R.id.editText_consult_date_du);
        editText_consult_date_au = (EditText) findViewById(R.id.editText_consult_date_au);
        button_consult_date_chercher = (Button) findViewById(R.id.button_consult_date_chercher);
        //Par client
        RelativeLayout_consult_client = (RelativeLayout) findViewById(R.id.RelativeLayout_consult_client);
        editText_consult_code_client = (EditText) findViewById(R.id.editText_consult_code_client);
        button_consult_client_chercher = (Button) findViewById(R.id.button_consult_client_chercher);
        editText_consult_client_date_du = (EditText) findViewById(R.id.editText_consult_client_date_du);
        editText_consult_client_date_au = (EditText) findViewById(R.id.editText_consult_client_date_au);
        imageButton_consult_client_reset_date_du = (ImageButton) findViewById(R.id.imageButton_consult_client_reset_date_du);
        imageButton_consult_client_reset_date_au = (ImageButton) findViewById(R.id.imageButton_consult_client_reset_date_au);
        imageButton_consult_client_reset_date_du.setColorFilter(Color.rgb(169,169,169));
        imageButton_consult_client_reset_date_au.setColorFilter(Color.rgb(169,169,169));

        //Par numero de facture
        RelativeLayout_consult_facture = (RelativeLayout) findViewById(R.id.RelativeLayout_consult_facture);
        editText_consult_num_facture = (EditText) findViewById(R.id.editText_consult_num_facture);
        button_consult_facture_chercher = (Button) findViewById(R.id.button_consult_facture_chercher);


        //Les Listener sur activity pour chaques button
        button_consult_numero.setOnClickListener(this);
        button_consult_date.setOnClickListener(this);
        button_consult_client.setOnClickListener(this);

        //Par date
        button_consult_date_chercher.setOnClickListener(this);
        editText_consult_date_du.setOnClickListener(this);
        editText_consult_date_au.setOnClickListener(this);
        //Par client
        button_consult_client_chercher.setOnClickListener(this);
        editText_consult_client_date_du.setOnClickListener(this);
        editText_consult_client_date_au.setOnClickListener(this);
        imageButton_consult_client_reset_date_du.setOnClickListener(this);
        imageButton_consult_client_reset_date_au.setOnClickListener(this);


        //Par numero de facture
        button_consult_facture_chercher.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

        setDateTimeField();

        //editText_consult_date_du.setText("01-02-2016");
        //editText_consult_date_au.setText("15-02-2016");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onClick(View v) {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(MenuTriActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (v == button_consult_numero) {
            RelativeLayout_consult_date.setVisibility(View.GONE);
            RelativeLayout_consult_client.setVisibility(View.GONE);
            RelativeLayout_consult_facture.setVisibility(View.VISIBLE);

        }
        if (v == button_consult_date) {
            //On rend visible le layout de tri par date et les autre invisible.
            RelativeLayout_consult_date.setVisibility(View.VISIBLE);
            RelativeLayout_consult_client.setVisibility(View.GONE);
            RelativeLayout_consult_facture.setVisibility(View.GONE);

        }
        if (v == button_consult_client) {
            //On rend visible le layout de tri par client et les autre invisible.
            RelativeLayout_consult_date.setVisibility(View.GONE);
            RelativeLayout_consult_client.setVisibility(View.VISIBLE);
            RelativeLayout_consult_facture.setVisibility(View.GONE);

        }

        if (v == editText_consult_date_du) {
            DatePickerDialogDu.show();
        }
        if (v == editText_consult_date_au) {
            DatePickerDialogAu.show();
        }

        if (v == editText_consult_client_date_du) {
            DatePickerDialogClientDu.show();
            imageButton_consult_client_reset_date_du.setVisibility(View.VISIBLE);
        }
        if (v == editText_consult_client_date_au) {
            DatePickerDialogClientAu.show();
            imageButton_consult_client_reset_date_au.setVisibility(View.VISIBLE);
        }

        if (v == imageButton_consult_client_reset_date_du) {
            editText_consult_client_date_du.setText("");
            imageButton_consult_client_reset_date_du.setVisibility(View.GONE);
        }
        if (v == imageButton_consult_client_reset_date_au) {
            editText_consult_client_date_au.setText("");
            imageButton_consult_client_reset_date_au.setVisibility(View.GONE);
        }

        if (v == button_consult_date_chercher) {
            if (editText_consult_date_du.getText().toString().equals("") || editText_consult_date_au.getText().toString().equals("")){
                Toast.makeText(MenuTriActivity.this, "Veuillez remplir tous les champs requis", Toast.LENGTH_SHORT).show();
            }
            else{
                consultParDate(Date_Francais_Anglais(editText_consult_date_du.getText().toString()),Date_Francais_Anglais(editText_consult_date_au.getText().toString()));
            }
        }
        if (v == button_consult_client_chercher) {
            if (editText_consult_code_client.getText().toString().equals("")){
                Toast.makeText(MenuTriActivity.this, "Veuillez remplir le champ requis", Toast.LENGTH_SHORT).show();
            }
            else if (editText_consult_client_date_du.getText().toString().equals("") && !editText_consult_client_date_au.getText().toString().equals("")){
                //Toast.makeText(MenuTriActivity.this, "code, date au", Toast.LENGTH_SHORT).show();
                consultParClient(Integer.parseInt(String.valueOf(editText_consult_code_client.getText())),
                        3, "", Date_Francais_Anglais(editText_consult_client_date_au.getText().toString()));
            }
            else if (!editText_consult_client_date_du.getText().toString().equals("") && editText_consult_client_date_au.getText().toString().equals("")){
                //Toast.makeText(MenuTriActivity.this, "code, date du", Toast.LENGTH_SHORT).show();
                consultParClient(Integer.parseInt(String.valueOf(editText_consult_code_client.getText())),
                        2, Date_Francais_Anglais(editText_consult_client_date_du.getText().toString()), "");
            }
            else if (!editText_consult_client_date_du.getText().toString().equals("") && !editText_consult_client_date_au.getText().toString().equals("")){
                //Toast.makeText(MenuTriActivity.this, "code, date du, date au", Toast.LENGTH_SHORT).show();
                consultParClient(Integer.parseInt(String.valueOf(editText_consult_code_client.getText())),
                        4, Date_Francais_Anglais(editText_consult_client_date_du.getText().toString()),
                        Date_Francais_Anglais(editText_consult_client_date_au.getText().toString()));
            }
            else if (editText_consult_client_date_du.getText().toString().equals("") && editText_consult_client_date_au.getText().toString().equals("")){
                //Toast.makeText(MenuTriActivity.this, "code", Toast.LENGTH_SHORT).show();
                consultParClient(Integer.parseInt(String.valueOf(editText_consult_code_client.getText())),
                        1, "", "");
            }
        }

        if (v == button_consult_facture_chercher) {
            if (editText_consult_num_facture.getText().toString().equals("")){
                Toast.makeText(MenuTriActivity.this, "Veuillez remplir le champ requis", Toast.LENGTH_SHORT).show();
            }
            else{
                consultParFacture(Integer.parseInt(String.valueOf(editText_consult_num_facture.getText())));
            }
        }


    }

    //Construction des 4 calendrier
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialogDu = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText_consult_date_du.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialogAu = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText_consult_date_au.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialogClientDu = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText_consult_client_date_du.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialogClientAu = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText_consult_client_date_au.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void consultParDate(final String date_du, final String date_au) {
        class consultParDateClass extends AsyncTask<String,Void,String> {

            ProgressDialog Chargement;

            @Override
            protected String doInBackground(String... params) {
                String resultat = "false";
                if (verifierConnextion() == true){
                    try {
                        String sql = "SELECT date_fact,num_fact,code_client,acompte,total_fact,reglement,type,solde,clients.nom,clients.prenom,clients.email FROM vente LEFT JOIN clients ON (clients.code = vente.code_client) WHERE date_fact >=? AND date_fact <=? ORDER BY date_fact, num_fact";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, date_du);
                        pst.setString(2, date_au);

                        rs = pst.executeQuery();
                        while (rs.next()){
                            resultat = "true";
                            listeFactures.add(new Facture(rs.getInt("num_fact"),rs.getString("date_fact"),rs.getInt("code_client"),
                                    rs.getString("type"),rs.getString("nom"),rs.getString("prenom"),rs.getDouble("acompte"),rs.getDouble("total_fact"),
                                    rs.getDouble("reglement"),rs.getDouble("solde"), rs.getString("email")));
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    resultat = "conn_false";
                }
                return resultat;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Chargement = ProgressDialog.show(MenuTriActivity.this,
                        "En attente",
                        "Chargement des données");
                Chargement.setCancelable(true);
                Chargement.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        // Do something...
                        consultParDateClass.this.cancel(true);
                        Toast.makeText(MenuTriActivity.this, "Annulé", Toast.LENGTH_SHORT).show();
                        onCancelled();
                    }
                });
            }

            @Override
            protected void onPostExecute(String resultat) {
                super.onPostExecute(resultat);
                Chargement.dismiss();
                if (resultat.equals("conn_false")){
                    Intent intent = new Intent(MenuTriActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else if(resultat.equals("true")){
                    Intent intent = new Intent(MenuTriActivity.this,ListeFactActivity.class);
                    startActivity(intent);
                }
                else if (resultat.equals("false")){
                    Toast.makeText(MenuTriActivity.this,"Aucun resultat",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Chargement.dismiss();
            }
        }
        consultParDateClass consult_par_date = new consultParDateClass();
        consult_par_date.execute(date_du,date_au);
    }

    private void consultParClient(final int num_client, final int mode_consult, final String client_date_du, final String client_date_au) {
        class consultParClientClass extends AsyncTask<String,Void,String> {

            ProgressDialog Chargement;

            @Override
            protected String doInBackground(String... params) {
                String resultat = "false";
                if (verifierConnextion() == true){
                    try {
                        if (mode_consult == 1){
                            String sql = "SELECT date_fact,num_fact,code_client,acompte,total_fact,reglement,type,solde,clients.nom,clients.prenom,clients.email FROM vente LEFT JOIN clients ON (clients.code = vente.code_client) WHERE vente.code_client=? ORDER BY date_fact, num_fact";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, num_client);
                        }
                        else if (mode_consult == 2){
                            String sql = "SELECT date_fact,num_fact,code_client,acompte,total_fact,reglement,type,solde,clients.nom,clients.prenom,clients.email FROM vente LEFT JOIN clients ON (clients.code = vente.code_client) WHERE vente.code_client=? AND date_fact>=? ORDER BY date_fact, num_fact";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, num_client);
                            pst.setString(2, client_date_du);
                        }
                        else if (mode_consult == 3){
                            String sql = "SELECT date_fact,num_fact,code_client,acompte,total_fact,reglement,type,solde,clients.nom,clients.prenom,clients.email FROM vente LEFT JOIN clients ON (clients.code = vente.code_client) WHERE vente.code_client=? AND date_fact<=? ORDER BY date_fact, num_fact";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, num_client);
                            pst.setString(2, client_date_au);
                        }
                        else{
                            String sql = "SELECT date_fact,num_fact,code_client,acompte,total_fact,reglement,type,solde,clients.nom,clients.prenom,clients.email FROM vente LEFT JOIN clients ON (clients.code = vente.code_client) WHERE vente.code_client=? AND date_fact>=? AND date_fact<=? ORDER BY date_fact, num_fact";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, num_client);
                            pst.setString(2, client_date_du);
                            pst.setString(3, client_date_au);
                        }

                        rs = pst.executeQuery();
                        while (rs.next()){
                            resultat = "true";
                            listeFactures.add(new Facture(rs.getInt("num_fact"),rs.getString("date_fact"),rs.getInt("code_client"),
                                    rs.getString("type"),rs.getString("nom"),rs.getString("prenom"),rs.getDouble("acompte"),rs.getDouble("total_fact"),
                                    rs.getDouble("reglement"),rs.getDouble("solde"),rs.getString("email")));
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    resultat = "conn_false";
                }

                return resultat;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Chargement = ProgressDialog.show(MenuTriActivity.this,
                        "En attente",
                        "Chargement des données");
                Chargement.setCancelable(true);
                Chargement.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        // Do something...
                        consultParClientClass.this.cancel(true);
                        Toast.makeText(MenuTriActivity.this, "Annulé", Toast.LENGTH_SHORT).show();
                        onCancelled();
                    }
                });
            }

            @Override
            protected void onPostExecute(String resultat) {
                super.onPostExecute(resultat);
                Chargement.dismiss();
                if (resultat.equals("conn_false")){
                    Intent intent = new Intent(MenuTriActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else if(resultat.equals("true")){
                    Intent intent = new Intent(MenuTriActivity.this,ListeFactActivity.class);
                    startActivity(intent);
                }
                else if(resultat.equals("false")){
                    Toast.makeText(MenuTriActivity.this,"Aucun resultat",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Chargement.dismiss();
            }
        }
        consultParClientClass consult_par_client = new consultParClientClass();
        consult_par_client.execute(String.valueOf(num_client), String.valueOf(mode_consult), client_date_du, client_date_au);
    }

    private void consultParFacture(final int num_facture) {
        class consultParFactureClass extends AsyncTask<String,Void,String> {

            ProgressDialog Chargement;

            @Override
            protected String doInBackground(String... params) {
                String resultat = "false";
                if (verifierConnextion() == true){
                    try {
                        String sql = "SELECT date_fact,num_fact,code_client,acompte,total_fact,reglement,type,solde,clients.nom,clients.prenom,clients.email FROM vente LEFT JOIN clients ON (clients.code = vente.code_client) WHERE vente.num_fact=? ORDER BY date_fact, num_fact";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, num_facture);

                        rs = pst.executeQuery();
                        if (rs.next()){
                            resultat = "true";
                            ListeFactActivity.num_select_fact = rs.getInt("vente.num_fact");
                            ListeFactActivity.nom_prenom_select_fact = rs.getString("clients.nom") + rs.getString("clients.prenom");
                            ListeFactActivity.email_clientselect_fact = rs.getString("clients.email");
                            ListeFactActivity.type_select_fact = rs.getString("type");
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    resultat = "conn_false";
                }
                return resultat;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Chargement = ProgressDialog.show(MenuTriActivity.this,
                        "En attente",
                        "Chargement des données");
                Chargement.setCancelable(true);
                Chargement.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        // Do something...
                        consultParFactureClass.this.cancel(true);
                        Toast.makeText(MenuTriActivity.this, "Annulé", Toast.LENGTH_SHORT).show();
                        onCancelled();
                    }
                });
            }

            @Override
            protected void onPostExecute(String resultat) {
                super.onPostExecute(resultat);
                Chargement.dismiss();
                if (resultat.equals("conn_false")){
                    Intent intent = new Intent(MenuTriActivity.this,LoginActivity.class);
                    startActivity(intent);

                }

                else if(resultat.equals("true")){
                    Intent intent = new Intent(MenuTriActivity.this,VisuFactTabActivity.class);
                    startActivity(intent);
                }
                else if (resultat.equals("false")){
                    Toast.makeText(MenuTriActivity.this,"Aucun resultat",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Chargement.dismiss();
            }
        }
        consultParFactureClass consult_par_facture = new consultParFactureClass();
        consult_par_facture.execute(String.valueOf(num_facture));
    }

    public final String Date_Francais_Anglais(String date_en_francais){
        String dateStr = date_en_francais;
        try{
            DateFormat srcDf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = srcDf.parse(dateStr);

            DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = destDf.format(date);
            //System.out.println("Converted date is : " + dateStr);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public boolean verifierConnextion(){
        boolean resultat = true;
        try {
            String sql_test_conn = "select 1";
            pst = conn.prepareStatement(sql_test_conn);
            rs = pst.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
            resultat = false;
            conn = null;
        }
        return resultat;
    }
}
