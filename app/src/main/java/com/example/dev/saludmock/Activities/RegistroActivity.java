package com.example.dev.saludmock.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.util.Log;
import com.example.dev.saludmock.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by dev on 8/31/17.
 */

public class RegistroActivity extends AppCompatActivity {

     AutoCompleteTextView nombre, direccion, telefono, correo, nombre_mascota, raza, edad, comentarios, cirugia, rescatado, vacuna, desparacitacion, celo, lactar;

    private String[] arraySpinner;
    private String[] arraySpinner2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registra_main);

        final Date date = new Date();
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       final Random rnd = new Random();



        nombre = (AutoCompleteTextView)findViewById(R.id.nombre_dueño);
        direccion = (AutoCompleteTextView)findViewById(R.id.direccion);
        telefono = (AutoCompleteTextView) findViewById(R.id.phone);
        correo = (AutoCompleteTextView) findViewById(R.id.email);
        nombre_mascota = (AutoCompleteTextView) findViewById(R.id.nombre_mascota);
        raza = (AutoCompleteTextView)findViewById(R.id.raza);
        edad = (AutoCompleteTextView) findViewById(R.id.edad);
        comentarios = (AutoCompleteTextView)findViewById(R.id.comentarios);
        cirugia = (AutoCompleteTextView) findViewById(R.id.cirugia);
        rescatado = (AutoCompleteTextView) findViewById(R.id.recatado);
        vacuna = (AutoCompleteTextView) findViewById(R.id.vacuna);
        desparacitacion = (AutoCompleteTextView) findViewById(R.id.desparacitacion);
        celo = (AutoCompleteTextView)findViewById(R.id.celo);
        lactar = (AutoCompleteTextView)findViewById(R.id.lactar);
        button = (Button)findViewById(R.id.registrar);



        this.arraySpinner = new String[]{
         "Seleccione una opción","Perra","Perro","Gata","Gato"
        };

        Spinner s = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);


        this.arraySpinner2 = new String[]{
                "Seleccione una opción", "Facebook","Volante","Vi una lona en el lugar de campaña","Ya he ido a otras campañas de Abpogados de los Animales","Recomendación de otra persona", "Otro"
        };
        Spinner s2 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner2);
        s2.setAdapter(adapter2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Nombre = nombre.getText().toString();
                String Direccion = direccion.getText().toString();
                String Telefono = telefono.getText().toString();
                String Correo = correo.getText().toString();
                String Nombre_Mascota = nombre_mascota.getText().toString();
                String Raza = raza.getText().toString();
                String Edad = edad.getText().toString();
                String Comentarios = comentarios.getText().toString();
                String Cirugia = cirugia.getText().toString();
                String Rescatado = rescatado.getText().toString();
                String Vacuna = vacuna.getText().toString();
                String Desparacitacion = desparacitacion.getText().toString();
                String Celo = celo.getText().toString();
                String Lactar = lactar.getText().toString();



                //Create a manager
                Manager manager = null;
                try{
                    manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
                }catch (IOException e){
                    e.printStackTrace();
                }

                //Create or open the database named app
                Database database = null;
                try {
                    database = manager.getDatabase("registro");
                }catch (CouchbaseLiteException e){
                    e.printStackTrace();
                }

                //The properties that will be saved on the document
                Map<String, Object> properties = new HashMap<String, Object>();

             //   properties.put("ficha", rnd.nextInt());
                properties.put("nombre", Nombre);
                properties.put("direccion", Direccion);
                properties.put("telefono", Telefono);
                properties.put("correo", Correo);
                properties.put("nombre_mascota", Nombre_Mascota);
                properties.put("raza", Raza);
                properties.put("edad", Edad);
                properties.put("comentarios", Comentarios);
                properties.put("cirugia", Cirugia);
                properties.put("rescatado", Rescatado);
                properties.put("vacuna", Vacuna);
                properties.put("desparacitacion", Desparacitacion);
                properties.put("celo", Celo);
                properties.put("lactar", Lactar);


                //Create a new document
                Document document = database.createDocument();
                //Save the document to the database
                try{
                    document.putProperties(properties);
                }catch(CouchbaseLiteException e){
                    e.printStackTrace();
                }

                //Log the document ID (generated by the database)
                //and properties
                Log.d("registro", String.format("Document ID :: %s", document.getId()));
                Log.d("registro", String.format("Los datos que han sido guardados son: ",
                    //    (String) document.getProperty("ficha"),
                        (String) document.getProperty("nombre"),
                        (String)document.getProperty("direccion"),
                        (String) document.getProperty("telefono"),
                        (String) document.getProperty("correo"),
                        (String) document.getProperty("nombre_mascota"),
                        (String) document.getProperty("raza"),
                        (String) document.getProperty("edad"),
                        (String) document.getProperty("comentarios"),
                        (String) document.getProperty("cirugia"),
                        (String) document.getProperty("rescatado"),
                        (String) document.getProperty("vacuna"),
                        (String) document.getProperty("desparacitacion"),
                        (String) document.getProperty("celo"),
                        (String) document.getProperty("lactar")));

                //Create replicators to push & pull changes to & from Sync Gateway.
     /*           URL url = null;
                try{
                    url = new URL("http://10.0.0.2:4984/hello");
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }

                Replication push = database.createPushReplication(url);
                Replication pull = database.createPullReplication(url);
                push.setContinuous(true);
                pull.setContinuous(true);

                push.start();
                pull.start();
                */

                Toast.makeText(getApplicationContext(), "Su mascota ha sido registrada correctamente!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistroActivity.this, PanelActivity.class);
                startActivity(intent);
            }
        });


    }
}
