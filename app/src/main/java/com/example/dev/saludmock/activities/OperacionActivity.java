package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by kennethrizo on 24/10/17.
 */

public class OperacionActivity extends AppCompatActivity{

    AutoCompleteTextView busqueda_registro;
    private TextView tv;
    private Button btn;
    private EditText et, et2;

    private static SimpleDateFormat mDateFormatter =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operacion_main);
        et = (EditText)findViewById(R.id.et);
        et2 = (EditText)findViewById(R.id.opero_et);
        busqueda_registro = (AutoCompleteTextView)findViewById(R.id.busqueda_registro);
        tv = (TextView)findViewById(R.id.tv);
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agregarComentarioOperacion();
                Toast.makeText(getApplicationContext(), "Se ha guardado correctamente los comentarios de la operación", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OperacionActivity.this,  ContentPanelActivity.class );
                startActivity(intent);
            }
        });
    }

    public void agregarComentarioOperacion(){
        final String busquedaString = busqueda_registro.getText().toString();

        final String currentTimeString = mDateFormatter.format(new Date());

        final String Comentario = et.getText().toString();
        final String Opero = et2.getText().toString();
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

        Document document = database.getDocument(busquedaString);
        try {
            document.update(new Document.DocumentUpdater() {
                @Override
                public boolean update(UnsavedRevision newRevision) {
                    Map<String, Object> properties = newRevision.getProperties();
                    properties.put("comentario", Comentario);
                    properties.put("opero", Opero);
                    properties.put("hora_operacion", currentTimeString);
                    newRevision.setUserProperties(properties);
                    return true;
                }
            });
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
        }
    }

}
