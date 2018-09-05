package com.example.dev.saludmock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.couchbase.lite.QueryRow;
import com.couchbase.lite.SavedRevision;
import com.example.dev.saludmock.R;

import java.util.ArrayList;
import java.util.List;

public class NewDoctorAdapter extends ArrayAdapter<QueryRow> {

    private final Context context;
    ArrayList<QueryRow> lista;

    String nombre;
    String id;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NewDoctorAdapter(Context applicationContext, int register_list_doctor, int txt_numero, int txt_folio, List<QueryRow> rows) {
        super(applicationContext, register_list_doctor, txt_numero,rows);
        this.context = applicationContext;
    }




// private final Context context;

    /*public  NewDoctorAdapter(Context context, int resource, int textViewResourceId, List<QueryRow> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }*/


    private static class ViewHolder {
        // ImageButton icon, icon2;

        // TextView label;

        public TextView newDoctor, folio;    //

    }

    @Override
    public android.view.View getView(int position, View itemView, ViewGroup parent) {


        if (itemView == null) {
            LayoutInflater vi = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = vi.inflate(R.layout.register_list_doctor, null);
            ViewHolder vh = new ViewHolder();
            vh.newDoctor = (TextView) itemView.findViewById(R.id.txt_numero);
            vh.folio = itemView.findViewById(R.id.txt_folio);
            // vh.icon = (ImageButton) itemView.findViewById(R.id.img);
            // vh.icon2 = itemView.findViewById(R.id.img2);
            itemView.setTag(vh);
        }

        try {
            TextView label = ((ViewHolder) itemView.getTag()).newDoctor;
            TextView label2 = ((ViewHolder) itemView.getTag()).folio;
            QueryRow row = getItem(position);
            SavedRevision currentRevision = row.getDocument().getCurrentRevision();
            Object check = (Object) currentRevision.getProperty("");
            boolean isGroceryItemChecked = false;
            if (check != null && check instanceof Boolean) {
                isGroceryItemChecked = ((Boolean) check).booleanValue();
            }
            String doctor = (String) currentRevision.getProperty("doctor");
            String folio = (String) currentRevision.getProperty("folio");

            label.setText(doctor);
            label2.setText(folio);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemView;
    }



}
