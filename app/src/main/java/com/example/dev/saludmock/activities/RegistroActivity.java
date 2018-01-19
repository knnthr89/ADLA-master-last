package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.graphics.Color;
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
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;
import com.example.dev.saludmock.R;
import com.example.dev.saludmock.db.ManagerCouch;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by dev on 8/31/17.
 */

public class RegistroActivity extends AppCompatActivity {

    AutoCompleteTextView nombre, direccion, telefono, correo, nombre_mascota, raza, edad, comentarios, cirugia, rescatado, vacuna, desparacitacion, celo, lactar, peso;

   // private String[] arraySpinner;
   // private String[] arraySpinner2;

    public ArrayList<String> spinnerlist;
    String spinnertext;

    public ArrayList<String> spinnersecondlist;
    String spinnertexttwo;

    Button button;

    Locale l = new Locale("es", "MX");
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),l);
    String fecha = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "T" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":"  + cal.get(Calendar.SECOND);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registra_main);

        nombre = findViewById(R.id.nombre_dueño);
        direccion = findViewById(R.id.direccion);
        telefono = findViewById(R.id.phone);
        correo = findViewById(R.id.email);
        nombre_mascota =  findViewById(R.id.nombre_mascota);
        raza = findViewById(R.id.raza);
        edad = findViewById(R.id.edad);
        comentarios = findViewById(R.id.comentarios);
        cirugia = findViewById(R.id.cirugia);
        rescatado = findViewById(R.id.recatado);
        vacuna = findViewById(R.id.vacuna);
        desparacitacion = findViewById(R.id.desparacitacion);
        celo = findViewById(R.id.celo);
        lactar = findViewById(R.id.lactar);
        peso = findViewById(R.id.peso);
        button = (Button)findViewById(R.id.registrar);

    /*    this.arraySpinner = new String[]{
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
**/

        final Spinner spinner = (Spinner)findViewById(R.id.spinner);

        spinnerlist= new ArrayList<String>();
        spinnerlist.add("Detalles de la mascota");
        spinnerlist.add("Perra");
        spinnerlist.add("Perro");
        spinnerlist.add("Gata");
        spinnerlist.add("Gato");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerlist);
        adapter.setNotifyOnChange(true);
        spinner.setAdapter(adapter);
        spinner.setLongClickable(true);
        spinner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                spinner.getSelectedItem().toString();
                return false;
            }
        });


        final Spinner spinnerdos = (Spinner)findViewById(R.id.spinner2);

        spinnersecondlist= new ArrayList<String>();
        spinnersecondlist.add("¿Cómo te entaraste de la campaña?");
        spinnersecondlist.add("Facebook");
        spinnersecondlist.add("Volante");
        spinnersecondlist.add("Vi una lona en el lugar de campaña");
        spinnersecondlist.add("Ya he ido a otras campañas de Abpogados de los Animales");
        spinnersecondlist.add("Recomendación de otra persona");
        spinnersecondlist.add("Otro");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnersecondlist);
        adapter2.setNotifyOnChange(true);
        spinnerdos.setAdapter(adapter2);
        spinnerdos.setLongClickable(true);
        spinnerdos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                spinnerdos.getSelectedItem().toString();
                return false;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String nombreString = nombre.getText().toString();
                String direccionString = direccion.getText().toString();
                String telefonoString = telefono.getText().toString();
                String correoString = correo.getText().toString();
                String nombreMascotaString = nombre_mascota.getText().toString();
                String tipoMascotaString = spinner.getSelectedItem().toString();
                String razaString = raza.getText().toString();
                String edadString = edad.getText().toString();
                String medioString = spinnerdos.getSelectedItem().toString();
                String comentariosString = comentarios.getText().toString();
                String cirugiaString = cirugia.getText().toString();
                String rescatadoString = rescatado.getText().toString();
                String vacunaString = vacuna.getText().toString();
                String desparacitacionString = desparacitacion.getText().toString();
                String celoString = celo.getText().toString();
                String lactarString = lactar.getText().toString();
                String pesoString = peso.getText().toString();

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
                    database = manager.getDatabase("adla");
                }catch (CouchbaseLiteException e){
                    e.printStackTrace();
                }

           //     ManagerCouch managerCouch = new ManagerCouch();
           //     managerCouch.manager();
           //     managerCouch.database();

                //The properties that will be saved on the document
                    Map<String, Object> properties = new HashMap<String, Object>();

                    //properties.put("ficha", rnd.nextInt());
                    //  properties.put("_rev", String.valueOf(i));

                    properties.put("nombre", nombreString);
                    properties.put("direccion", direccionString);
                    properties.put("telefono", telefonoString);
                    properties.put("correo", correoString);
                    properties.put("nombre_mascota", nombreMascotaString);
                    properties.put("tipo_mascota", tipoMascotaString);
                    properties.put("raza", razaString);
                    properties.put("edad", edadString);
                    properties.put("medio", medioString);
                    properties.put("comentarios_registro", comentariosString);
                    properties.put("cirugia", cirugiaString);
                    properties.put("rescatado", rescatadoString);
                    properties.put("vacuna", vacunaString);
                    properties.put("desparacitacion", desparacitacionString);
                    properties.put("celo", celoString);
                    properties.put("lactar", lactarString);
                    properties.put("peso", pesoString);
                    properties.put("created_at", fecha);


                   int numero = (int) (Math.random() * 10000000) + 1;
                  String numeroString = Integer.toString(numero);

                //Create a new document
                    Document document = database.getDocument(numeroString);


                    //     UnsavedRevision revision = document.createRevision();
                     //    revision.setUserProperties(properties);
                    //Save the document to the database
                    try {
                        document.putProperties(properties);
                    } catch (CouchbaseLiteException e) {
                        e.printStackTrace();
                    }

                    //Log the document ID (generated by the database)
                    //and properties
               /*     Log.d("registro", String.format("Document ID :: %s", document.getId()));
                    Log.d("registro", String.format("Los datos que han sido guardados son: ",
                            //    (String) document.getProperty("ficha"),
                            (String) document.getProperty("nombre"),
                            (String) document.getProperty("direccion"),
                            (String) document.getProperty("telefono"),
                            (String) document.getProperty("correo"),
                            (String) document.getProperty("nombre_mascota"),
                            (String) document.getProperty("raza"),
                            (String) document.getProperty("edad"),
                            (String) document.getProperty("comentarios_registro"),
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

               String utilizaBoton = "BUSCA";

                Toast.makeText(getApplicationContext(), "Su mascota ha sido registrada correctamente con el número de folio " +numeroString + " y el peso de la mascota es " + pesoString, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistroActivity.this, ContentPanelActivity.class);
                intent.putExtra("utilizaBoton", utilizaBoton);
                startActivity(intent);
            }
        });
    }
}
