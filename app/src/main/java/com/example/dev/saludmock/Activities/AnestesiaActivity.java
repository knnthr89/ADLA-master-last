package com.example.dev.saludmock.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kennethrizo on 24/10/17.
 */

public class AnestesiaActivity extends AppCompatActivity{

    AutoCompleteTextView dosis1, dosis2, dosis3;
    Spinner sp;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anestesia_main);
        sp = (Spinner)findViewById(R.id.spinner);
        addItemsOnSpinner();

        dosis1 = (AutoCompleteTextView)findViewById(R.id.dosis);
        dosis2 = (AutoCompleteTextView)findViewById(R.id.dosis2);
        dosis3 = (AutoCompleteTextView)findViewById(R.id.dosis3);

        guardar = (Button)findViewById(R.id.btn);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    database = manager.getDatabase("registro");
                }catch (CouchbaseLiteException e){
                    e.printStackTrace();
                }

                Document document = database.getDocument("");
                try {
                    document.update(new Document.DocumentUpdater() {
                        @Override
                        public boolean update(UnsavedRevision unsavedRevision) {
                            Map<String, Object> properties = new HashMap<String, Object>();
                            properties.put("dosis", Dosis);
                            properties.put("dosis2", Dosis2);
                            properties.put("dosis3", Dosis3);
                            return true;
                        }
                    });
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(), "Se ha registrado correctamente la dosis de la mascota", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AnestesiaActivity.this, PanelActivity.class);
                startActivity(intent);

            }
        });
    }

    public void addItemsOnSpinner(){

        List<String> list = new ArrayList<String>();
        list.add("Seleccione el número de ficha de la mascota");
        //Cambiar por variable
        list.add("000000001");
        list.add("000000002");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
    }
}
