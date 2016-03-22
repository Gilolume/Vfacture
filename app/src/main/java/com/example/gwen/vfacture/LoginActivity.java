package com.example.gwen.vfacture;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_identifiant;
    EditText editText_mdp;
    Button button_connexion;
    Button button_reconnect_bdd;
    ImageView image_facture;

    protected static Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    private int click_count = 0;
    private boolean base_internet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_identifiant = (EditText)findViewById(R.id.editText_identifiant);
        editText_mdp = (EditText)findViewById(R.id.editText_mdp);
        button_connexion = (Button)findViewById(R.id.button_connexion);
        button_reconnect_bdd = (Button)findViewById(R.id.button_reconnect_bdd);
        image_facture = (ImageView)findViewById(R.id.image_facture);

        button_connexion.setOnClickListener(this);
        button_reconnect_bdd.setOnClickListener(this);
        image_facture.setOnClickListener(this);

        connexionBDD();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (v == button_connexion) {
            connexionCompte(editText_identifiant.getText().toString().trim(), editText_mdp.getText().toString().trim());
        }
        if (v == button_reconnect_bdd){
            connexionBDD();
            button_connexion.setEnabled(true);
            button_reconnect_bdd.setVisibility(View.INVISIBLE);
        }
        if (v == image_facture) {
            click_count ++;
            if (click_count >= 10){
                click_count = 0;
                if (base_internet == false){
                    base_internet = true;
                    Toast.makeText(LoginActivity.this,"La base de données a été changée : internet",Toast.LENGTH_SHORT).show();
                }
                else{
                    base_internet = false;
                    Toast.makeText(LoginActivity.this,"La base de données a été changée : ordinateur",Toast.LENGTH_SHORT).show();
                }
                connexionBDD();
            }
        }
    }

    private String md5(String in) {

        MessageDigest digest;

        try
        {

            digest = MessageDigest.getInstance("MD5");

            digest.reset();

            digest.update(in.getBytes());

            byte[] a = digest.digest();

            int len = a.length;

            StringBuilder sb = new StringBuilder(len << 1);

            for (int i = 0; i < len; i++)
            {

                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));

                sb.append(Character.forDigit(a[i] & 0x0f, 16));

            }

            return sb.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void connexionBDD() {
        class connexionBDDClass extends AsyncTask<String,Void,Boolean> {

            ProgressDialog Chargement;

            @Override
            protected Boolean doInBackground(String... params) {
                Boolean resultat = false;
                conn = new connectBDD().Connexion_bdd(base_internet);
                if (conn != null){
                    resultat = true;
                }
                else{
                    resultat = false;
                }
                return resultat;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Chargement = ProgressDialog.show(LoginActivity.this, "En attente", "Connexion à la BDD", true, false);
                new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    public void onFinish() {
                        // stop async task if not in progress
                        if (connexionBDDClass.this.getStatus() == Status.RUNNING) {
                            connexionBDDClass.this.cancel(true);
                        }
                    }
                }.start();
            }

            @Override
            protected void onPostExecute(Boolean resultat) {
                super.onPostExecute(resultat);
                Chargement.dismiss();
                if (resultat != true){
                    button_connexion.setEnabled(false);
                    button_reconnect_bdd.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this,"Erreur, vérifier votre connexion internet",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onCancelled() {
                button_connexion.setEnabled(false);
                button_reconnect_bdd.setVisibility(View.VISIBLE);
                conn = null;
                Chargement.dismiss();
                Toast.makeText(LoginActivity.this,"Délai d'attente dépassé",Toast.LENGTH_SHORT).show();
            }
        }
        connexionBDDClass connexion_BDD = new connexionBDDClass();
        connexion_BDD.execute();
    }

    private void connexionCompte(final String identifiant, final String mot_de_passe) {
        class connexionCompteClass extends AsyncTask<String,Void,String>{

            ProgressDialog Chargement;

            @Override
            protected String doInBackground(String... params) {
                String resultat = "false";

                if (verifierConnextion() == true){
                    try {
                        String sql = "SELECT cd_user,mt_pass FROM utilisateur WHERE cd_user=? AND mt_pass=?";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, identifiant);
                        pst.setString(2, md5(mot_de_passe));

                        rs = pst.executeQuery();
                        if (rs.next()) {
                            resultat = "true";
                        } else {
                            resultat = "false";
                        }
                    } catch (Exception e) {
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
                Chargement = ProgressDialog.show(LoginActivity.this,
                        "En attente",
                        "Identification");
                Chargement.setCancelable(true);
                Chargement.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        // Do something...
                        connexionCompteClass.this.cancel(true);
                        Toast.makeText(LoginActivity.this, "Annulé", Toast.LENGTH_SHORT).show();
                        onCancelled();
                    }
                });
            }

            @Override
            protected void onPostExecute(String resultat) {
                super.onPostExecute(resultat);
                Chargement.dismiss();
                if(resultat.equals("true")){
                    Intent intent = new Intent(LoginActivity.this,MenuTriActivity.class);
                    startActivity(intent);
                }
                else if (resultat.equals("false")){
                    Toast.makeText(LoginActivity.this,"Identifiant ou mot de passe incorrect",Toast.LENGTH_SHORT).show();
                }
                else if (resultat.equals("conn_false")){
                    Toast.makeText(LoginActivity.this,"Erreur, vérifier votre connexion internet",Toast.LENGTH_SHORT).show();
                    button_connexion.setEnabled(false);
                    button_reconnect_bdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Chargement.dismiss();
            }
        }
        connexionCompteClass connexion_compte = new connexionCompteClass();
        connexion_compte.execute(identifiant,mot_de_passe);
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