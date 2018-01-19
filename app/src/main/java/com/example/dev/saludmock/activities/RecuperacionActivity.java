package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class RecuperacionActivity extends AppCompatActivity{

    private RadioButton analgesico, tatuaje, antibiotico, suero, cicatrizante, otro;
    private EditText et;
    private Button btn;
    AutoCompleteTextView busqueda_registro;

    private static SimpleDateFormat mDateFormatter =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    String antibioticoString;
    String tatuajeString;
    String analgesicoString;
    String sueroString;
    String cicatrizanteString;
    String otroString;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperacion_main);

        busqueda_registro = (AutoCompleteTextView)findViewById(R.id.busqueda_registro);

        antibiotico = (RadioButton)findViewById(R.id.radio3);
        tatuaje = (RadioButton)findViewById(R.id.radio2);
        analgesico = (RadioButton)findViewById(R.id.radio1);
         suero = (RadioButton)findViewById(R.id.radio4);
        cicatrizante = (RadioButton)findViewById(R.id.radio5);
        otro = (RadioButton)findViewById(R.id.radio6);

        et = (EditText)findViewById(R.id.et);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(antibiotico.isChecked()){
                    antibioticoString = "antibiotico";
                }
                 if(tatuaje.isChecked()){
                    tatuajeString = "tatuaje";
                }
                if(analgesico.isChecked()){
                    analgesicoString = "analgesico";
                }
                if(suero.isChecked()){
                    sueroString = "suero";
                }
                if(cicatrizante.isChecked()){
                    cicatrizanteString = "cicatrizante";
                }

                if(otro.isChecked()){
                    otroString = "otro";
                }


               // Toast.makeText(getApplicationContext(), tatuajeString, Toast.LENGTH_LONG).show();

                agregarComentarioOperacion();
                Toast.makeText(getApplicationContext(), "Los datos de recuperación han sido guardados exisosamente!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecuperacionActivity.this, ContentPanelActivity.class);
                startActivity(intent);
            }
        });

    }

    public void agregarComentarioOperacion(){
        final String currentTimeString = mDateFormatter.format(new Date());
        final String Comentario = et.getText().toString();

        final String busquedaString = busqueda_registro.getText().toString();

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
                    properties.put("antibiotico", antibioticoString);
                    properties.put("tatuaje", tatuajeString);
                    properties.put("analgesico", analgesicoString);
                    properties.put("suero", sueroString);
                    properties.put("cicatrizante", cicatrizanteString);
                    properties.put("otro", otroString);
                    properties.put("comentario_recuperacion", Comentario);
                    properties.put("hora_recuperacion", currentTimeString);
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
