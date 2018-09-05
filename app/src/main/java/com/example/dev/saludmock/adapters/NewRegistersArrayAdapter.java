package com.example.dev.saludmock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;
import com.example.dev.saludmock.activities.ContentPanelActivity;
import com.example.dev.saludmock.activities.DownloadSheetToDBStep2;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Created by kennethrizo on 20/02/18.
 */

public class NewRegistersArrayAdapter extends ArrayAdapter<QueryRow> {

    private List<QueryRow> list;
    private final Context context;

    public  NewRegistersArrayAdapter(Context context, int resource, int textViewResourceId, List<QueryRow> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    private static class ViewHolder {
       // ImageView icon;
       // TextView label;

       public TextView folio;    ///
       public TextView fecha;
       public TextView nombre;    //
    //   public TextView direccion;
       public TextView nmascota;   //
       // TextView email;
       public TextView tmascota;  //
      //  TextView tmascota;
      //  TextView raza;
      //  TextView edad;
      //  TextView msocial;
      //  TextView comentarios;
      //  TextView tratamiento;
      //  TextView rescatado;
      //  TextView vacuna;
      //  TextView desparacitacion;
      //  TextView celo;
      //  TextView lactar;
        TextView telefono;   //
      //  TextView peso;
      //  TextView alergia;
    }

    @Override
    public android.view.View getView(int position, View itemView, ViewGroup parent) {

        if (itemView == null) {
            LayoutInflater vi = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //se modifico itemView por si no funciona
            itemView = vi.inflate(R.layout.register_list_item, null);
            ViewHolder vh = new ViewHolder();
            //nombre label del id en el layout
            vh.folio = (TextView) itemView.findViewById(R.id.txt_numero);
            vh.nombre = (TextView) itemView.findViewById(R.id.txt_nombre);
           // vh.direccion = (TextView) itemView.findViewById(R.id.txt_direccion);

            //cambiar a nombre mascota
            vh.nmascota = (TextView) itemView.findViewById(R.id.txt_nmascota);


         //   vh.email = (TextView) itemView.findViewById(R.id.label);

            //cambiar a tipo de mascota
            vh.tmascota = (TextView) itemView.findViewById(R.id.txt_tmascota);
           // vh.tmascota = (TextView) itemView.findViewById(R.id.label);
          //  vh.raza = (TextView) itemView.findViewById(R.id.label);
          //  vh.edad = (TextView) itemView.findViewById(R.id.label);
          //  vh.msocial = (TextView) itemView.findViewById(R.id.label);
          //  vh.comentarios = (TextView) itemView.findViewById(R.id.label);
          //  vh.tratamiento = (TextView) itemView.findViewById(R.id.label);
          //  vh.rescatado = (TextView) itemView.findViewById(R.id.label);
          //  vh.vacuna = (TextView) itemView.findViewById(R.id.label);
          //  vh.desparacitacion = (TextView) itemView.findViewById(R.id.label);
          //  vh.celo = (TextView) itemView.findViewById(R.id.label);
          //  vh.lactar = (TextView) itemView.findViewById(R.id.label);

            //cambiar a telefono
            vh.telefono = (TextView) itemView.findViewById(R.id.txt_telefono);
          //  vh.peso = (TextView) itemView.findViewById(R.id.label);
          //  vh.alergia = (TextView) itemView.findViewById(R.id.label);

            itemView.setTag(vh);
        }

        try {
            TextView label = ((ViewHolder)itemView.getTag()).nombre;
            TextView label1 = (((ViewHolder) itemView.getTag()).folio);
            TextView label2 = (((ViewHolder) itemView.getTag()).nmascota);
            TextView label3 = (((ViewHolder) itemView.getTag()).telefono);
            TextView label4 = (((ViewHolder) itemView.getTag()).tmascota);
            QueryRow row = getItem(position);
            SavedRevision currentRevision = row.getDocument().getCurrentRevision();
            Object check = (Object) currentRevision.getProperty("");
            boolean isGroceryItemChecked = false;
            if (check != null && check instanceof Boolean) {
                isGroceryItemChecked = ((Boolean)check).booleanValue();
            }
            String folio = (String) currentRevision.getProperty("folio");
            String nombre = (String) currentRevision.getProperty("nombreDueno");
            String tipoMascota = (String) currentRevision.getProperty("nombreMascota");
            String telefono = (String) currentRevision.getProperty("telefono");
            String mascota = (String) currentRevision.getProperty("tipoMascota");

            label1.setText(folio);
            label.setText(nombre);
            label2.setText(tipoMascota);
            label3.setText(telefono);
            label4.setText(mascota);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemView;
    }

}
