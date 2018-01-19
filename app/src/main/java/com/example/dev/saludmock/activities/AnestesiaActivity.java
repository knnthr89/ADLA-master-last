package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by kennethrizo on 24/10/17.
 */

public class AnestesiaActivity extends AppCompatActivity{

    AutoCompleteTextView  busqueda_registro, dosis1, dosis2, dosis3;

    Button guardar;

    private static SimpleDateFormat mDateFormatter =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anestesia_main);

        busqueda_registro = (AutoCompleteTextView)findViewById(R.id.busqueda_registro);

        dosis1 = (AutoCompleteTextView)findViewById(R.id.dosis1);
        dosis2 = (AutoCompleteTextView)findViewById(R.id.dosis2);
        dosis3 = (AutoCompleteTextView)findViewById(R.id.dosis3);

        guardar = (Button)findViewById(R.id.btn);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDocument();

                Toast.makeText(getApplicationContext(), "Se ha registrado correctamente la dosis de la mascota", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AnestesiaActivity.this, ContentPanelActivity.class);
                startActivity(intent);

            }
        });
    }

    public void updateDocument(){
        final String busqueda_reg = busqueda_registro.getText().toString();

        final String currentTimeString = mDateFormatter.format(new Date());

              final String Dosis = dosis1.getText().toString();
              final   String Dosis2 = dosis2.getText().toString();
              final   String Dosis3 = dosis3.getText().toString();

        //Create manager
        Manager manager = null;
        try{
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        }catch (IOException e){
            e.printStackTrace();
        }

        //Create or open the database named app
        Database database = null;
        try{
            database = manager.getDatabase("adla");
        }catch (CouchbaseLiteException e){
            e.printStackTrace();
        }

        final Document document = database.getDocument(busqueda_reg);


        try {
            document.update(new Document.DocumentUpdater() {
                @Override
                public boolean update(UnsavedRevision newRevision) {
                    Map<String, Object> properties = newRevision.getProperties();
                    properties.put("primeradosis", Dosis);
                    properties.put("segundadosis", Dosis2);
                    properties.put("terceradosis", Dosis3);
                    properties.put("hora_anestesia", currentTimeString );
                    newRevision.setUserProperties(properties);
                    return true;
                }
            });
        }catch (CouchbaseLiteException e){
            e.printStackTrace();
        }
    }
}
