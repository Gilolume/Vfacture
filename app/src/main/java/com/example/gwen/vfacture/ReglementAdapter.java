package com.example.gwen.vfacture;

/**
 * Created by gwen on 11/02/2016.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReglementAdapter extends ArrayAdapter<Reglement> {

    public ReglementAdapter(Context context, List<Reglement> reglements) {
        super(context, 0, reglements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ligne_reglement,parent, false);
        }

        ReglementViewHolder viewHolder = (ReglementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ReglementViewHolder();
            viewHolder.textView_regl_DateReg = (TextView) convertView.findViewById(R.id.textView_regl_DateReg);
            viewHolder.textView_regl_libelle = (TextView) convertView.findViewById(R.id.textView_regl_libelle);
            viewHolder.textView_regl_libellebis = (TextView) convertView.findViewById(R.id.textView_regl_libellebis);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Reglement> reglements
        Reglement reglements = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.textView_regl_DateReg.setTypeface(Typeface.MONOSPACE);
        viewHolder.textView_regl_libelle.setTypeface(Typeface.MONOSPACE);
        viewHolder.textView_regl_libellebis.setTypeface(Typeface.MONOSPACE);

        viewHolder.textView_regl_DateReg.setText(reglements.getDateReg().toString());
        viewHolder.textView_regl_libelle.setText(reglements.getLibelle().toString());
        viewHolder.textView_regl_libellebis.setText(reglements.getLibellebis().toString());

        return convertView;
    }

    private class ReglementViewHolder{
        public TextView textView_regl_DateReg;
        public TextView textView_regl_libelle;
        public TextView textView_regl_libellebis;
    }
}
