package com.example.gwen.vfacture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListeFactActivity extends AppCompatActivity {

    private ListView mListView;
    public static int num_select_fact;
    protected static String nom_prenom_select_fact;
    protected static String email_clientselect_fact;
    protected static String type_select_fact;
    private FactureAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_fact);

        mListView = (ListView) findViewById(R.id.listView);

        adapter = new FactureAdapter(ListeFactActivity.this, MenuTriActivity.listeFactures);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {

                Facture laFactureSelect = (Facture) mListView.getItemAtPosition(pos);

                num_select_fact = laFactureSelect.getNum_fact();
                nom_prenom_select_fact = laFactureSelect.getNom_client() + " " + laFactureSelect.getPrenom_client();
                email_clientselect_fact = laFactureSelect.getEmail();
                type_select_fact = laFactureSelect.getType();

                Intent intent = new Intent(ListeFactActivity.this, VisuFactTabActivity.class);
                startActivity(intent);

            }
        });


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clear();
        adapter.notifyDataSetChanged();
        finish();
    }

}
