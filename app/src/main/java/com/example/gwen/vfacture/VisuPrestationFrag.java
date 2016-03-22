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


public class VisuPrestationFrag extends Fragment{

    private ListView listView_prestation;
    private PrestationAdapter adapter_prestation;

    public VisuPrestationFrag() {
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
        View view = inflater.inflate(R.layout.prestation_visu_frag, container, false);

        listView_prestation = (ListView) view.findViewById(R.id.listView_prestation);

        //adapter_prestation = new PrestationAdapter(VisuPrestationFrag.this, VisuFactTabActivity.listePrestations);
        adapter_prestation = new PrestationAdapter(getContext(), VisuFactTabActivity.listePrestations);
        listView_prestation.setAdapter(adapter_prestation);

        /*TextView textView_visu_type = (TextView) view.findViewById(R.id.textView_visu_type);
        textView_visu_type.setText(visu_type);*/


        return view;
    }

}