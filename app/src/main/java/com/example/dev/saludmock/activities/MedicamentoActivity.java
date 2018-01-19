package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class MedicamentoActivity extends AppCompatActivity {


    AutoCompleteTextView busqueda_registro;
    private Button btn;
    private RadioButton radioButtonSi, radioButtonNo;
    String sinoString;


    private static SimpleDateFormat mDateFormatter =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicamento_main);

        radioButtonSi = (RadioButton)findViewById(R.id.rbsi);
        radioButtonNo = (RadioButton)findViewById(R.id.rbno);

        busqueda_registro = (AutoCompleteTextView)findViewById(R.id.busqueda_registro);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String currentTimeString = mDateFormatter.format(new Date());
                final String busquedaString = busqueda_registro.getText().toString();

               if(radioButtonSi.isChecked()){
                  sinoString = "si";
               }else if(radioButtonNo.isChecked()){
                   sinoString = "no";
               }else {
                   Toast.makeText(MedicamentoActivity.this, "No selecciono ninguna opción", Toast.LENGTH_SHORT).show();
               }

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
                            Map<String, Object> properties = newRevision.getProperties();;
                            properties.put("medicamento", sinoString);
                            properties.put("hora_medicamento", currentTimeString);
                            newRevision.setUserProperties(properties);
                            return true;
                        }
                    });
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), "Se ha guardado que " + sinoString + " ha recibido medicamento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MedicamentoActivity.this, ContentPanelActivity.class );
                startActivity(intent);
            }
        });

    }
}
