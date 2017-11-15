package com.example.dev.saludmock.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class OperacionActivity extends AppCompatActivity{

    private Spinner sp;
    private TextView tv;
    private Button btn;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operacion_main);
        et = (EditText)findViewById(R.id.et);
        sp = (Spinner)findViewById(R.id.spinner);
        tv = (TextView)findViewById(R.id.tv);
        addItemsOnSpinner();
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Comentario = et.getText().toString();
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
                            properties.put("comentario", Comentario);
                            return true;
                        }
                    });
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(OperacionActivity.this, PanelActivity.class );
                startActivity(intent);
            }
        });
    }

    public void addItemsOnSpinner(){

        List<String> list = new ArrayList<String>();
        list.add("Selecciona folio de la mascota");
        //Cambiar por variable
        list.add("000000000001");
        list.add("000000000002");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
    }
}
