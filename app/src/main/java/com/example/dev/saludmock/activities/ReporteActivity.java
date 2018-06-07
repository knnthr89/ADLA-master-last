package com.example.dev.saludmock.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.example.dev.saludmock.adapters.NewRegistersArrayAdapter;


import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReporteActivity  extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnKeyListener {

    TextView tv, tv2, tv3;
    Button btn;

    String fecha = "";
    String nombre = "";
    String mascota = "";

    Object date;

    ListView itemListView;

    protected NewRegistersArrayAdapter newRegistersArrayAdapter;

    QueryEnumerator result;

    Manager manager;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        itemListView = findViewById(R.id.itemListView);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(fecha, nombre, mascota);

            }
        });


        String dia = getIntent().getExtras().getString("dia");
        String mes = getIntent().getExtras().getString("mes");
        String anio = getIntent().getExtras().getString("anio");

        //  Toast.makeText(getApplicationContext(), anio + "/" + mes + "/" + dia , Toast.LENGTH_LONG).show();

        date = anio + "/" + mes + "/" + dia;

        try {
            initializeQuery();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
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
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReporteActivity.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeQuery() throws Exception {

        //Create a manager
        manager = null;
        try {
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        database = null;
        try {
            database = manager.getDatabase("adla");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        com.couchbase.lite.View membersView = database.getView("creat_at");
        // if (membersView == null) {
        membersView.setMap(
                new Mapper() {
                    @Override
                    public void map(Map<String, Object> document, Emitter emitter) {
                        Log.e("inside map", "map");
                        Object date = document.get("creat_at");
                        if (date != null)
                            emitter.emit((String) document.get("creat_at"), document);
                    }
                }, "1" /* The version number of the mapper... */
        );
        // }
        Query query = database.getView("creat_at").createQuery();
        query.setStartKey(date);
        result = query.run();
     /*   for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
           // Log.w("ADLA", "REPORT", row.getKey(), ((Double)row.getValue()).toString());

        }*/

        displayRows(result);

        getRowsFromQueryEnumerator(result);

    }

    private List<QueryRow> getRowsFromQueryEnumerator(QueryEnumerator queryEnumerator) {
        List<QueryRow> rows = new ArrayList<QueryRow>();
        for (Iterator<QueryRow> it = queryEnumerator; it.hasNext(); ) {
            QueryRow row = it.next();
            rows.add(row);
        }
        return rows;
    }

    private void displayRows(QueryEnumerator queryEnumerator) {

        final List<QueryRow> rows = getRowsFromQueryEnumerator(queryEnumerator);

        //  tv.setText(rows);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                newRegistersArrayAdapter = new NewRegistersArrayAdapter(
                        getApplicationContext(),
                        R.layout.register_list_item,
                        R.id.txt_numero,
                        rows
                );

                itemListView.setAdapter(newRegistersArrayAdapter);
                itemListView.setOnItemClickListener(ReporteActivity.this);
                itemListView.setOnItemLongClickListener(ReporteActivity.this);

            }
        });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ReporteActivity.this);
        builder.setTitle("Informacion Completa");
        LayoutInflater inflater = LayoutInflater.from(this);
         view = inflater.inflate(R.layout.datoscompletosdialog, null);

           TextView folio = view.findViewById(R.id.folio);
           TextView tdueno = view.findViewById(R.id.dueno_guardado);
           TextView tdireccion = view.findViewById(R.id.direccion_guardado);
           TextView ttelefono = view.findViewById(R.id.telefono_guardado);
           TextView tcorreo = view.findViewById(R.id.correo_guardado);
           TextView tnmascota = view.findViewById(R.id.nmascota);
           TextView tmsocial = view.findViewById(R.id.msocial_respuesta);
           TextView traza = view.findViewById(R.id.raza_respuesta);
           TextView tedad = view.findViewById(R.id.edad_respuesta);
           TextView ttmascota = view.findViewById(R.id.tamanomascota_respuesta);

           TextView comentarioregistrotv = view.findViewById(R.id.comentarioregistro_respuesta);
           TextView tratamientotv = view.findViewById(R.id.tratamiento_respuesta);
           TextView rescatadotv = view.findViewById(R.id.rescatado_respuesta);
           TextView vacunatv = view.findViewById(R.id.vacuna_respuesta);
           TextView desparacitaciontv = view.findViewById(R.id.desparacitacion_respuesta);
           TextView celotv = view.findViewById(R.id.celo_respuesta);
           TextView lactartv = view.findViewById(R.id.lactante_respuesta);
           TextView tipomascotatv = view.findViewById(R.id.tipomascota_respuesta);
           TextView pesotv = view.findViewById(R.id.peso_respuesta);
           TextView alergiatv = view.findViewById(R.id.alergia_respuesta);
           TextView fecharegistrotv = view.findViewById(R.id.fecharegistro_respuesta);

           TextView precioanestesia = view.findViewById(R.id.precioanestesia_respuesta);
           TextView cantidadtarjeta = view.findViewById(R.id.cantidadtarjeta_respuesta);
           TextView cantidadefectivo = view.findViewById(R.id.cantidadefectivo_respuesta);


        muestraDatos((int) id,folio, tdueno, tdireccion, ttelefono, tcorreo, tnmascota, tmsocial, traza, tedad, ttmascota, comentarioregistrotv, tratamientotv, rescatadotv,
        vacunatv, desparacitaciontv, celotv, lactartv, tipomascotatv, pesotv, alergiatv, fecharegistrotv, precioanestesia, cantidadtarjeta, cantidadefectivo);

        builder.setView(view);

        builder.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        return false;
    }

    public void muestraDatos(int id,TextView folio, TextView tdueno, TextView tdireccion, TextView ttelefono, TextView tcorreo, TextView tnmascota, TextView tmsocial, TextView traza, TextView tedad, TextView ttmascota,
    TextView comentarioregistrotv, TextView tratamientotv, TextView rescatadotv, TextView vacunatv, TextView desparacitaciontv, TextView celotv, TextView lactartv, TextView tipomascotatv, TextView pesotv, TextView alergiatv,
                             TextView fecharegistrotv, TextView precioanestesia, TextView cantidadtarjeta, TextView cantidadefectivo){

        // Toast.makeText(ReporteActivity.this, idString, Toast.LENGTH_SHORT).show();

        //Create manager
        Manager manager = null;

        try {
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create or open the database named app
        Database database = null;

        try {
            database = manager.getDatabase("adla");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        Document doc = database.getDocument(String.valueOf(id));

        doc.getProperty("folio");

        Map<String, Object> properties = doc.getProperties();

        //registro
        String folioString = (String) properties.get("folio");
        String duenoString = (String) properties.get("nombreDueno");
        String direccionString = (String) properties.get("direccion");
        String telefono = (String) properties.get("telefono");
        String correo = (String) properties.get("correo");
        String nmascota = (String) properties.get("nombreMascota");
        String msocial = (String) properties.get("mediosocial");
        String raza = (String) properties.get("raza");
        String edad = (String) properties.get("edad");
        String tmascota = (String) properties.get("tamanoMascota");
        String comentarioregistro = (String) properties.get("comentarioRegistro");
        String tratamiento = (String) properties.get("tratamiento");
        String rescatado = (String) properties.get("rescatado");
        String vacuna = (String) properties.get("vacuna");
        String desparacitacion = (String) properties.get("desparacitacion");
        String celo = (String) properties.get("celo");
        String lactar = (String) properties.get("lactar");
        String tipomascota = (String) properties.get("tipoMascota");
        String peso = (String) properties.get("peso");
        String alergia = (String) properties.get("alergia");
        String fecharegistro = (String) properties.get("creat_at");

        //pago
        String precio_anestesiaString = (String) properties.get("precio_anestesia");
        String cantidad_tarjetaString = (String) properties.get("cantidadTarjeta");
        String cantidad_efectivoString = (String) properties.get("cantidadEfectivo");

        //anestesia
        String dosis1 = (String) properties.get("dosis1");
        String dosis2 = (String) properties.get("dosis2");
        String dosis3 = (String) properties.get("dosis3");
        String horaanestesia = (String) properties.get("fecha_anestesia");

        //cirugia
        String cirujano = (String) properties.get("cirujano");
        String comentario_cirujano = (String) properties.get("comentario");
        String prenada = (String) properties.get("preñada");
        String celo_cirugia = (String) properties.get("celo_cirugia");
        String lactante_cirugia = (String) properties.get("lactante_cirugia");
        String hemorragia = (String) properties.get("hemorragia");
        String parorespiratorio = (String) properties.get("parorespiratorio");
        String parocardiaco = (String) properties.get("parocardiaco");
        String testiculoinguinal = (String) properties.get("testiculoinguinal");
        String finado = (String) properties.get("finado");
        String alergico_cirugia = (String) properties.get("alergico_cirugia");
        String fecha_cirugia = (String) properties.get("fecha_cirugia");

        //recuperacion
        String antibioticoString = (String) properties.get("antibiotico");
        String tatuajeString = (String) properties.get("tatuaje");
        String analgesicoString = (String) properties.get("analgesico");
        String sueroString = (String) properties.get("suero");
        String cicatrizanteString = (String)properties.get("cicatrizante");
        String comentarioString = (String) properties.get("comentario_recuperacion");
        String tallaIsabelinoString = (String) properties.get("tallaIsabelino");
        String llevaIsabelinoString = (String) properties.get("llevaIsabelino");
        String llevaMedicamentoString = (String) properties.get("llevaMedicamento");
        String recuperacion_fecha = (String) properties.get("recuperacion_fecha");

        //medicamento
        String pregunta_medicamento = (String) properties.get("pregunta_medicamento");
        String isabelino = (String) properties.get("isabelino");
        String isodine = (String) properties.get("isodine");
        String mascota_entregada = (String) properties.get("mascota_entregada");
        String medicamento_fecha = (String) properties.get("medicamento_fecha");



       // final TextView tfolio = findViewById(R.id.antibiotico_pregunta);
        //  final  TextView tdueno = view.findViewById(R.id.dueno);
        //  final  TextView tdireccion = view.findViewById(R.id.direccion);
        //  final TextView ttelefono = view.findViewById(R.id.telefono);
        //  final  TextView tcorreo = view.findViewById(R.id.correo);
        //  final TextView tnmascota = view.findViewById(R.id.nmascota);
        //  final TextView tmsocial = view.findViewById(R.id.msocial);
        //  final TextView traza = view.findViewById(R.id.raza);
        //  final TextView tedad = view.findViewById(R.id.edad);
        //  final  TextView ttmascota = view.findViewById(R.id.tmascota);

        if(folioString != null){
          //  Toast.makeText(getApplicationContext(), folio,Toast.LENGTH_SHORT).show();
            folio.setText(folioString);
        }

          if(duenoString != null){
          //  Toast.makeText(getApplicationContext(), dueno,Toast.LENGTH_SHORT).show();
         tdueno.setText(duenoString);
        }

        if(direccionString != null){
         //   Toast.makeText(getApplicationContext(), direccion,Toast.LENGTH_SHORT).show();
         tdireccion.setText(direccionString);
        }

       if(telefono != null){
          //  Toast.makeText(getApplicationContext(), telefono,Toast.LENGTH_SHORT).show();
            ttelefono.setText(telefono);
        }

        if(correo != null){
           // Toast.makeText(getApplicationContext(), correo,Toast.LENGTH_SHORT).show();
           tcorreo.setText(correo);
        }

        if(nmascota != null){
           // Toast.makeText(getApplicationContext(), nmascota,Toast.LENGTH_SHORT).show();
         tnmascota.setText(nmascota);
        }

        if(msocial != null){
          //  Toast.makeText(getApplicationContext(), msocial,Toast.LENGTH_SHORT).show();
          tmsocial.setText(msocial);
        }

        if(raza != null){
          //  Toast.makeText(getApplicationContext(), raza,Toast.LENGTH_SHORT).show();
         traza.setText(raza);
        }

        if(edad != null){
           // Toast.makeText(getApplicationContext(), edad,Toast.LENGTH_SHORT).show();
         tedad.setText(edad);
        }

        if(tmascota != null){
          //  Toast.makeText(getApplicationContext(), tmascota,Toast.LENGTH_SHORT).show();
           ttmascota.setText(tmascota);
        }

        if(comentarioregistro != null){
            comentarioregistrotv.setText(comentarioregistro);
        }

        if(tratamiento != null){
            tratamientotv.setText(tratamiento);
        }

        if(rescatado != null){
            rescatadotv.setText(rescatado);
        }

        if(vacuna != null){
           vacunatv.setText(vacuna);
        }

        if(desparacitacion != null){
           desparacitaciontv.setText(desparacitacion);
        }

        if(celo != null){
           celotv.setText(celo);
        }

        if(lactar != null){
            lactartv.setText(lactar);
        }

        if(tipomascota != null){
            tipomascotatv.setText(tipomascota);
        }

        if(peso != null){
            pesotv.setText(peso);
        }

        if(alergia != null){
            alergiatv.setText(alergia);
        }

        if(fecharegistro != null){
            fecharegistrotv.setText(fecharegistro);
        }

        if(precio_anestesiaString != null){
            precioanestesia.setText(precio_anestesiaString);
        }

        if(cantidad_tarjetaString != null){
            cantidadtarjeta.setText(cantidad_tarjetaString);
        }

        if(cantidad_efectivoString != null){
            cantidadefectivo.setText(cantidad_efectivoString);
        }
    }

    }
