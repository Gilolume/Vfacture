package com.example.gwen.vfacture;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.mysql.jdbc.Statement;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/*import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.fragments.OneFragment;
import info.androidhive.materialtabs.fragments.ThreeFragment;
import info.androidhive.materialtabs.fragments.TwoFragment;*/

public class VisuFactTabActivity extends AppCompatActivity {

    protected static Connection conn;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    protected static List<Prestation> listePrestations = new ArrayList<Prestation>() ;
    protected static List<Reglement> listeReglements = new ArrayList<Reglement>();

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private boolean option_createPdf_activ = true;

    //Stockage des variable de test du fragment prestation
    /*public static int quantite_save = 0;
    public static Double prix_unit_save = 0.00;
    public static boolean ajout = false;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_fact_tab);

        conn = LoginActivity.conn;

        recupererFacture(ListeFactActivity.num_select_fact);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //Permet d'afficher le bouton de navigation up sur l'application
        //actionBar.setDisplayHomeAsUpEnabled(true);
        NumberFormat formatter_seven = new DecimalFormat("0000000");
        String num_fact_action_bar = formatter_seven.format(ListeFactActivity.num_select_fact);
        String type_fact_action_bar = "";
        if (ListeFactActivity.type_select_fact.equals("f")){
            type_fact_action_bar = "Facture";
        }
        else{
            type_fact_action_bar = "Avoir";
        }
        actionBar.setTitle(type_fact_action_bar + " n°" + num_fact_action_bar);
        actionBar.setSubtitle(ListeFactActivity.nom_prenom_select_fact);


        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final View v = findViewById(R.id.action_email);

                if (v != null) {
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (option_createPdf_activ == true) {
                                telechargerPdf(ListeFactActivity.num_select_fact);
                            } else {
                                envoiEmail(ListeFactActivity.num_select_fact, ListeFactActivity.email_clientselect_fact);
                            }

                        }
                    });

                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (option_createPdf_activ == true) {
                                option_createPdf_activ = false;
                                Toast.makeText(VisuFactTabActivity.this, "Création des PDF désactivé", Toast.LENGTH_SHORT).show();
                            } else {
                                option_createPdf_activ = true;
                                Toast.makeText(VisuFactTabActivity.this, "Création des PDF activé", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });

                }
            }
        });


        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_email:
                //envoiEmail(ListeFactActivity.num_select_fact);
                telechargerPdf(ListeFactActivity.num_select_fact);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        VisuEnteteFrag.passagers.clear();
        listePrestations.clear();
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VisuEnteteFrag(), "En-tête");
        adapter.addFragment(new VisuPrestationFrag(), "Prestation");
        adapter.addFragment(new VisuReglementFrag(), "Reglement");

        viewPager.setPageMargin(1);
        viewPager.setPageMarginDrawable(R.color.separatorTabColor);

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void recupererFacture(final int unNum_fact) {
        class recupererFactureClass extends AsyncTask<String,Void,String> {

            ProgressDialog Chargement;

            @Override
            protected String doInBackground(String... params) {
                String resultat = "true";
                if (verifierConnextion() == true){
                    requeteVente(unNum_fact);
                }
                else{
                    resultat = "conn_false";
                }
                return resultat;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Chargement = ProgressDialog.show(VisuFactTabActivity.this,
                        "En attente",
                        "Chargement des données");
                Chargement.setCancelable(true);
                Chargement.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        // Do something...
                        recupererFactureClass.this.cancel(true);
                        Toast.makeText(VisuFactTabActivity.this, "Annulé", Toast.LENGTH_SHORT).show();
                        onCancelled();
                    }
                });
            }

            @Override
            protected void onPostExecute(String resultat) {
                super.onPostExecute(resultat);
                Chargement.dismiss();
                if (resultat.equals("conn_false")){
                    Intent intent = new Intent(VisuFactTabActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else if(resultat.equals("true")){
                    viewPager = (ViewPager) findViewById(R.id.viewpager);
                    setupViewPager(viewPager);
                    tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(viewPager);
                }
                else if (resultat.equals("false")){
                    Toast.makeText(VisuFactTabActivity.this,"Erreur, aucun résultat",Toast.LENGTH_SHORT).show();
                    VisuFactTabActivity.this.finish();
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Chargement.dismiss();
                VisuFactTabActivity.this.finish();
            }

        }
        recupererFactureClass recuperer_facture = new recupererFactureClass();
        recuperer_facture.execute(Integer.toString(unNum_fact));
    }
    public void requeteVente(int unNum_fact){
        try{

            String type_paiement;
            int code_client;

            String sql = "select * from vente where num_fact = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, Integer.toString(unNum_fact));

            rs = pst.executeQuery();
            if(rs.next()){
                //Affichage des champs
                VisuEnteteFrag.visu_code_client = rs.getInt("code_client");
                VisuEnteteFrag.visu_no_grp = rs.getString("no_grp");
                VisuEnteteFrag.visu_cd_ag = rs.getInt("cd_ag");
                VisuEnteteFrag.visu_code_1 = rs.getInt("code_1");
                VisuEnteteFrag.visu_code_2 = rs.getInt("code_2");
                VisuEnteteFrag.visu_code_3 = rs.getInt("code_3");
                VisuEnteteFrag.visu_code_fact = rs.getString("code_fact");
                VisuEnteteFrag.visu_code_dest = rs.getString("code_dest");
                VisuEnteteFrag.visu_date_depart = rs.getString("date_depart");
                VisuEnteteFrag.visu_date_retour = rs.getString("date_retour");


                Double commission = rs.getDouble("commission");
                Double tva = rs.getDouble("tva");
                Double acompte = rs.getDouble("acompte");
                Double reglement = rs.getDouble("reglement");
                Double total_fact = rs.getDouble("total_fact");

                VisuReglementFrag.visu_total_brut = total_fact + reglement + acompte + commission + tva;
                VisuReglementFrag.visu_com_tva = commission + tva;
                VisuReglementFrag.visu_total_net = acompte + reglement + total_fact;
                VisuReglementFrag.visu_acompte = acompte;
                VisuReglementFrag.visu_reglement = reglement;
                VisuReglementFrag.visu_total_fact = total_fact;
                VisuReglementFrag.visu_commentaire1 = rs.getString("commentaire1");
                VisuReglementFrag.visu_commentaire2 = rs.getString("commentaire2");
                VisuReglementFrag.visu_commentaire3 = rs.getString("commentaire3");
                VisuReglementFrag.visu_date_fact = rs.getString("date_fact");
                VisuReglementFrag.visu_dat_echeance = rs.getString("dat_echeance");
                VisuReglementFrag.visu_solde = rs.getDouble("solde");



                //Recuperation pour requete suivante

                type_paiement = rs.getString("paiement");
                code_client = rs.getInt("code_client");

                requeteClientligne();
                requeteVentligne(unNum_fact);
                requetePaiement(type_paiement);
                requeteClients(type_paiement, code_client);
                requetePassager(unNum_fact);
                VisuEnteteFrag.visu_adresse_de_facturation = "Adresse de facturation";
                if (VisuEnteteFrag.visu_nom_et1 == null){
                    requeteAdrcli(code_client);
                    VisuEnteteFrag.visu_adresse_de_facturation = "";
                }
                requeteReglement(unNum_fact);
                requeteAcom_utl(unNum_fact);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requeteClientligne(){
        try{
            String sql = "select * from clientligne where type_fiche = 'V' and code_fiche = 1 and ligne < 990";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            if(rs.next()){
                //Affichage des champs

                //Pas de recup pour requete suivante
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requeteVentligne(int un_num_fact){
        try{

            int code_four;
            String code_pv;
            String code_pays;

            String sql = "select * from ventligne where num_fact = ? order by ligne";
            pst = conn.prepareStatement(sql);
            pst.setString(1, Integer.toString(un_num_fact));

            rs = pst.executeQuery();
            while (rs.next()){
                //Affichage des champs

                String le_code_pays = rs.getString("code_pays");
                int le_code_four = rs.getInt("code_four");
                String le_code_pv = rs.getString("code_pv");

                listePrestations.add(new Prestation(rs.getString("code_prod"),le_code_four, le_code_pays,
                        le_code_pv,rs.getInt("voucher"),rs.getString("libelle1"),rs.getString("libelle2"),
                        rs.getString("libelle3"),rs.getString("libelle4"),rs.getDouble("prix"),rs.getInt("quantite"),
                        requetePays(le_code_pays),requeteFournisseur(le_code_four),
                        requetePrestvente(le_code_pv),"visible",0.00));
            }

            triAffichagePrestation();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public String requeteFournisseur(int un_no_four){
        String resultat = "";
        try{
            String sql_four = "select nm_four from fournisseur where no_four = ?";
            PreparedStatement pst_four = conn.prepareStatement(sql_four);
            pst_four.setString(1, Integer.toString(un_no_four));

            ResultSet rs_four = pst_four.executeQuery();
            if(rs_four.next()){
                //Affichage des champs
                resultat = rs_four.getString("nm_four");
                //Recuperation pour requete suivante
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultat;
    }
    public String requetePrestvente(String un_code_pv){
        String resultat = "";
        try{
            String sql_pv = "select libelle from prestvente where code_pv = ?";
            PreparedStatement pst_pv = conn.prepareStatement(sql_pv);
            pst_pv.setString(1, un_code_pv);

            ResultSet rs_pv = pst_pv.executeQuery();
            if(rs_pv.next()){
                //Affichage des champs
                resultat = rs_pv.getString("libelle");
                //Recuperation pour requete suivante
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultat;
    }
    public String requetePays(String un_cd_pays){
        String resultat = "";
        try{
            String sql_pays = "select nm_pays from pays where cd_pays = ?";
            PreparedStatement pst_pays = conn.prepareStatement(sql_pays);
            pst_pays.setString(1, un_cd_pays);

            ResultSet rs_pays = pst_pays.executeQuery();
            if(rs_pays.next()){
                //Affichage des champs
                resultat = rs_pays.getString("nm_pays");
                //Recuperation pour requete suivante
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultat;
    }
    public void requetePaiement(String un_type){
        try{
            String sql = "select * from paiement where type = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, un_type);

            rs = pst.executeQuery();
            if(rs.next()){
                //Affichage des champs
                VisuReglementFrag.visu_libelle = rs.getString("libelle");

                //Recuperation pour requete suivante
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requeteClients(String un_type_fiche, int un_code_client){
        try{
            String sql = "select nom , prenom , qualite , adresse , adrsuite , localite , pays , telephone , email , code_postal , code_categ, code , MID(IFNULL(clientligne.comment,''),9,80) as comment " +
                    "from clients left join clientligne on (clientligne.code_fiche = clients.code AND type_fiche = ?) where code = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, un_type_fiche);
            pst.setString(2, Integer.toString(un_code_client));

            rs = pst.executeQuery();
            if(rs.next()){
                //Affichage des champs
                //VisuEnteteFrag.visu_nom_prenom_client = rs.getString("nom") + " " + rs.getString("prenom");


                //Recuperation pour requete suivante

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requetePassager(int un_num_fact){
        try{
            String sql = "select * from passager where num_fact = ? and type = 'f'";
            pst = conn.prepareStatement(sql);
            pst.setString(1, Integer.toString(un_num_fact));

            rs = pst.executeQuery();

            VisuEnteteFrag.visu_nom_et1 = null;
            VisuEnteteFrag.visu_nom_et2 = null;
            VisuEnteteFrag.visu_nom_et3 = null;
            VisuEnteteFrag.visu_nom_et4 = null;
            VisuEnteteFrag.visu_nom_et5 = null;
            VisuEnteteFrag.visu_nom_et6 = null;
            VisuEnteteFrag.visu_nom_et7 = null;

            String nom;

            while(rs.next()){
                //Affichage des champs
                nom = rs.getString("nom");
                if (nom.startsWith("*")){
                    if (nom.startsWith("*1")){
                        //System.out.println("Nom : " +   nom.substring(2));
                        VisuEnteteFrag.visu_nom_et1 = nom.substring(2);
                    }
                    else if (nom.startsWith("*2")){
                        //System.out.println("Adresse : " + nom.substring(2));
                        VisuEnteteFrag.visu_nom_et2 = nom.substring(2);
                    }
                    else if (nom.startsWith("*3")){
                        //System.out.println("Adresse suite : " + nom.substring(2));
                        VisuEnteteFrag.visu_nom_et3 = nom.substring(2);
                    }
                    else if (nom.startsWith("*4")){
                        //System.out.println("Ville : " + nom.substring(2));
                        VisuEnteteFrag.visu_nom_et4 = nom.substring(2);
                    }
                    else if (nom.startsWith("*5")){
                        //System.out.println("Pays : " + nom.substring(2));
                        VisuEnteteFrag.visu_nom_et5 = nom.substring(2);
                    }
                    else if (nom.startsWith("*6")){
                        //System.out.println("N tel : " + nom.substring(2));
                        VisuEnteteFrag.visu_nom_et6 = nom.substring(2);
                    }
                    else if (nom.startsWith("*7")){
                        //System.out.println("Mail : " + nom.substring(2));
                        VisuEnteteFrag.visu_nom_et7 = nom.substring(2);
                    }
                }
                else{
                    //System.out.println("Passager : " + nom);
                    VisuEnteteFrag.passagers.add(nom);
                }
                //Recuperation pour requete suivante

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requeteAdrcli(int un_code_client){
        try{
            String sql = "select adresse,adrsuite,code_postal,localite,pays,telephone from adrcli where code_client = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, Integer.toString(un_code_client));

            rs = pst.executeQuery();
            VisuEnteteFrag.visu_nom_et1 = null;
            VisuEnteteFrag.visu_nom_et2 = null;
            VisuEnteteFrag.visu_nom_et3 = null;
            VisuEnteteFrag.visu_nom_et4 = null;
            VisuEnteteFrag.visu_nom_et5 = null;
            VisuEnteteFrag.visu_nom_et6 = null;
            VisuEnteteFrag.visu_nom_et7 = null;
            if(rs.next()){
                //Affichage des champs
                VisuEnteteFrag.visu_nom_et1 = ListeFactActivity.nom_prenom_select_fact;
                VisuEnteteFrag.visu_nom_et2 = rs.getString("adresse");
                VisuEnteteFrag.visu_nom_et3 = rs.getString("adrsuite");
                VisuEnteteFrag.visu_nom_et4 = rs.getString("code_postal")+rs.getString("localite");
                VisuEnteteFrag.visu_nom_et5 = rs.getString("pays");
                VisuEnteteFrag.visu_nom_et6 = rs.getString("telephone");
                //Recuperation pour requete suivante

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requeteReglement(int un_num_fact){
        try{
            String sql = "select t_dtl.date_regl as DateReg , IF( ((t_dtl.cd_payt = 'BTA') or (t_dtl.cd_payt = 'BSP') or (t_dtl.cd_payt = 'DCL') or (t_dtl.cd_payt = 'APL') or (select FIND_IN_SET((select cd_payt from mod_pay\n" +
                    "where ban_mdpt = t_dtl.code_banque limit 1) ,'BTA,BSP,DCL,APL'))  )\n" +
                    ",concat(RPAD(SUBSTRING(t_dtl.lib_regl,1,7),7,' '),'*******'),RPAD(t_dtl.lib_regl,14,' '))as libelle, \n" +
                    "concat(' ',RPAD(t_dtl.cd_payt,5,' '),' ',replace(LPAD(SUBSTRING_INDEX(t_dtl.mt_regl,'.',(select 1 + long_decimal * (-1) from parametre)),10,' '),'.',','),\n" +
                    "IF( (select count(*) from regl_utl  as t_utl2 where t_utl2.no_regl  = t_dtl.no_regl and t_utl2.num_fact <> t_utl.num_fact    ) = 0,'  ','* ') , t_dtl.devise ) as libellebis \n" +
                    "from regl_utl as t_utl, regl_dtl as t_dtl\n" +
                    "where t_utl.num_fact = ?\n" +
                    "and t_dtl.no_regl = t_utl.no_regl";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, un_num_fact);

            rs = pst.executeQuery();

            while(rs.next()){
                //Ajout de tous les reglements dans la liste
                listeReglements.add(new Reglement(rs.getString("DateReg"), rs.getString("libelle"), rs.getString("libellebis")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requeteAcom_utl(int un_num_fact){
        try{
            String sql = "select count(num_acom) as rowcount from acom_utl where num_fact = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, un_num_fact);
            rs = pst.executeQuery();

            int nombre_ligne = 0;
            if (rs.next()){
                nombre_ligne = rs.getInt("rowcount");
            }

            if (nombre_ligne == 1){
                try{
                    sql = "select num_acom from acom_utl where num_fact = ?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, un_num_fact);
                    rs = pst.executeQuery();

                    if (rs.next()){
                        VisuReglementFrag.visu_acompte_num = "(n°" + rs.getInt("num_acom") + ")";
                    }
                    else{
                        VisuReglementFrag.visu_acompte_num = "";
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                VisuReglementFrag.visu_acompte_num = "";
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void triAffichagePrestation(){

        int taille = listePrestations.size();
        int index = 0;

        for (Prestation laPrestation : listePrestations){

            //Verification si on est pas à la derniere presation
            boolean possibilite_cumul;
            if (listePrestations.indexOf(laPrestation) >= taille){
                possibilite_cumul = false;
            }
            else{
                possibilite_cumul = true;
            }

            //Si on est pas à la derniere prestation on peux faire le cumul avec la prestation suivante
            if (possibilite_cumul == true){

                //On test le premier caractere du libelle1, si il est different de "" alors on récupere sont premier caractere pour le switch
                String test_libelle1_caract1;
                if (laPrestation.getLibelle1().equals("")) {
                    test_libelle1_caract1 = "";
                }
                else{
                    test_libelle1_caract1 = laPrestation.getLibelle1().substring(0,1);
                }

                int quantite = laPrestation.getQuantite();
                Double prix = laPrestation.getPrix();

                switch(test_libelle1_caract1) {
                    case "*":
                        if (listePrestations.get(index + 1).getQuantite() == quantite){
                            laPrestation.setVisibility("cache");
                            listePrestations.get(index + 1).cumulPrix(prix);
                        }
                        else{
                            laPrestation.setVisibility("visible");
                            laPrestation.setTotal(prix * quantite);
                        }
                        break;
                    case "#":
                        if (listePrestations.get(index + 1).getQuantite() == quantite){
                            laPrestation.setVisibility("partielle");
                            listePrestations.get(index + 1).cumulPrix(prix);
                        }
                        else{
                            laPrestation.setVisibility("visible");
                            laPrestation.setTotal(prix * quantite);
                        }
                        break;
                    default:
                        laPrestation.setTotal(prix * quantite);
                }
                index = index + 1;
            }
        }
    }

    public void envoiEmail(int unNumeroFacture, String unEmail){
        try{
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "Facture" + unNumeroFacture + ".pdf");
            Intent intent = new Intent(Intent.ACTION_SEND , Uri.parse("mailto:")); // it's not ACTION_SEND
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {unEmail});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            this.startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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

    private void telechargerPdf(final int unNum_fact) {
        class telechargerPdfClass extends AsyncTask<String,Void,String> {

            ProgressDialog Chargement;

            @Override
            protected String doInBackground(String... params) {
                String resultat = "false";

                if (verifierConnextion() == true){
                    try {
                        String sql = "INSERT INTO ftp_facture (num_fact, action) VALUES (?, 0)";
                        pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        pst.setInt(1, unNum_fact);

                        pst.executeUpdate();
                        ResultSet rs = pst.getGeneratedKeys();
                        rs.next();
                        int auto_id = rs.getInt(1);

                        try {
                            int progression = 0;
                            String url_facture = "";
                            sql = "SELECT * FROM ftp_facture WHERE id = ? AND action = 1";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, auto_id);
                            while (progression == 0){
                                rs = pst.executeQuery();
                                if(rs.next()){
                                    progression = 1;
                                    url_facture = rs.getString("url_fact");
                                }
                                else{
                                    progression = 0;
                                }
                            }
                            if (url_facture.equals("")){
                                resultat = "false";
                            }
                            else{
                                String fileName = "Facture"+unNum_fact+".pdf";  // -> maven.pdf
                                String fileUrl = url_facture;   // -> http://maven.apache.org/maven-1.x/maven.pdf
                                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                File pdfFile = new File(path, fileName);

                                try{
                                    pdfFile.createNewFile();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                FileDownloader.downloadFile(fileUrl, pdfFile);
                                resultat = "true";

                                try{
                                    sql = "UPDATE ftp_facture SET action = 2 WHERE id = ?";
                                    pst = conn.prepareStatement(sql);
                                    pst.setInt(1, auto_id);
                                    pst.executeUpdate();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
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
                final CountDownTimer LeTimer = new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    public void onFinish() {
                        // stop async task if not in progress
                        if (telechargerPdfClass.this.getStatus() == Status.RUNNING) {
                            telechargerPdfClass.this.cancel(true);
                            Toast.makeText(VisuFactTabActivity.this,"Délai d'attente dépassé",Toast.LENGTH_SHORT).show();
                            onCancelled();
                        }
                    }
                }.start();

                Chargement = ProgressDialog.show(VisuFactTabActivity.this,
                        "En attente",
                        "Création du fichier PDF");
                Chargement.setCancelable(true);
                Chargement.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        // Do something...
                        telechargerPdfClass.this.cancel(true);
                        LeTimer.cancel();
                        Toast.makeText(VisuFactTabActivity.this,"Annulé",Toast.LENGTH_SHORT).show();
                        onCancelled();
                    }
                });
            }

            @Override
            protected void onPostExecute(String resultat) {
                super.onPostExecute(resultat);
                Chargement.dismiss();
                if (resultat.equals("conn_false")){
                    Intent intent = new Intent(VisuFactTabActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else if(resultat.equals("true")){
                    envoiEmail(unNum_fact, ListeFactActivity.email_clientselect_fact);
                }
                else if (resultat.equals("false")){
                    Toast.makeText(VisuFactTabActivity.this,"Impossible de télécharger le PDF",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Chargement.dismiss();
            }
        }
        telechargerPdfClass telechargerPdf = new telechargerPdfClass();
        telechargerPdf.execute(Integer.toString(unNum_fact));

    }

}