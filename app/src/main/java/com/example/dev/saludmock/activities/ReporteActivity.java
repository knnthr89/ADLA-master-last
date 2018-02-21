package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.LiveQuery;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.internal.database.util.DatabaseUtils;
import com.couchbase.lite.util.Log;
import com.example.dev.saludmock.R;


import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReporteActivity extends AppCompatActivity {

  TextView tv, tv2, tv3;
  Button btn;

  String fecha = "";
  String nombre = "";
  String mascota = "";

  Object date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(fecha, nombre, mascota);




            }
        });



        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

      String dia =  getIntent().getExtras().getString("dia");
      String mes =  getIntent().getExtras().getString("mes");
      String anio =getIntent().getExtras().getString("anio");

        Toast.makeText(getApplicationContext(), anio + "/" + mes + "/" + dia , Toast.LENGTH_LONG).show();

        date = anio + "/" + mes + "/" + dia;

      /*  //Create a manager
        Manager manager = null;
        try{
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        }catch (IOException e){
            e.printStackTrace();
        }

        Database database = null;
        try {
            database = manager.getDatabase("adla");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        //   Toast.makeText(getApplicationContext(), "si hay ejemplos", Toast.LENGTH_SHORT).show();

        Document doc = database.getDocument("1");

        doc.getProperty("creat_at");

         Map<String, Object> properties = doc.getProperties();
          fecha = (String) properties.get("creat_at");
          nombre = (String) properties.get("nombreDueno");
          mascota = (String) properties.get("nombreMascota");
        

            tv.setText(fecha);
            tv2.setText(nombre);
            tv3.setText(mascota);

*/
        try {
            initializeQuery();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

  /*      if(doc!=null){
            tv.setText(fecha);
          //  tv2.setText(anestesia2);
          //  tv3.setText(anestesia3);
        }else {
            Toast.makeText(getApplicationContext(), "No se encuentra el documento", Toast.LENGTH_LONG).show();
        }*/



    }

    protected void sendEmail(String fecha, String nombre, String mascota) {
        String[] TO = {"kenm.riv@gmail.com"}; //aquí pon tu correo
        String[] CC = {"kenm.riv@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reporte de registros de campaña");
        emailIntent.putExtra(Intent.EXTRA_TEXT, fecha + nombre + mascota);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReporteActivity.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeQuery() throws CouchbaseLiteException {

        //Create a manager
        Manager manager = null;
        try{
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        }catch (IOException e){
            e.printStackTrace();
        }

        Database database = null;
        try {
            database = manager.getDatabase("adla");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        com.couchbase.lite.View membersView = database.getView("creat_at");
        //if (membersView == null) {
            membersView.setMap(
                    new Mapper(){
                       @Override
                        public void map(Map<String, Object> document, Emitter emitter) {
                            Log.e("inside map", "map");
                            Object date = document.get("creat_at");
                            if (date != null)
                                emitter.emit((String) document.get("creat_at"), document);
                        }
                    }, "1" /* The version number of the mapper... */
            );

        Query query = database.getView("creat_at").createQuery();
        query.setStartKey(date);
        QueryEnumerator result = query.run();
     /*   for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
           // Log.w("ADLA", "REPORT", row.getKey(), ((Double)row.getValue()).toString());

        }*/
     getRowsFromQueryEnumerator(result);

   // displayRows(result);

    }

    private List<QueryRow> getRowsFromQueryEnumerator(QueryEnumerator queryEnumerator) {
        List<QueryRow> rows = new ArrayList<QueryRow>();
        for (Iterator<QueryRow> it = queryEnumerator; it.hasNext();) {
            QueryRow row = it.next();
            rows.add(row);
        }
        return rows;
    }

    private void displayRows(Object rows) {

        //final List<QueryRow> rows = getRowsFromQueryEnumerator(queryEnumerator);



      //  tv.setText(rows);

     /*   runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Adapter itemListViewAdapter = new RegistersSyncListAdapter(
                        getApplicationContext(),
                        R.layout.text_view,
                        R.id.label,
                        rows
                );
                itemListView.setAdapter(itemListViewAdapter);
                itemListView.setOnItemClickListener(ReporteActivity.this);
                itemListView.setOnItemLongClickListener(ReporteActivity.this);

            }
        });*/
    }

}
