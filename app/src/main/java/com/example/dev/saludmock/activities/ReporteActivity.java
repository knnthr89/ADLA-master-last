package com.example.dev.saludmock.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.dev.saludmock.adapters.LlenarTablaAdapter;
import com.example.dev.saludmock.adapters.NewRegistersArrayAdapter;
import com.example.dev.saludmock.adapters.RegistroCompletoAdapter;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ReporteActivity  extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnKeyListener {

    TextView tv, tv2, tv3;
    Button btn;

    String fecha = "";
    String nombre = "";
    String mascota = "";

    private String dia, mes, anio;

    Object date;

    ListView itemListView;

    protected NewRegistersArrayAdapter newRegistersArrayAdapter;

    QueryEnumerator result;

    Manager manager;
    Database database;

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        itemListView = findViewById(R.id.itemListView);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

                askPermissionAndWriteFile();
               /* try {
                    readXLSFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

             //  writeFile();
                //   SearchRows();
                 sendEmail(fecha);

            }
        });


         dia = getIntent().getExtras().getString("dia");
         mes = getIntent().getExtras().getString("mes");
         anio = getIntent().getExtras().getString("anio");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.come_back:
                regresar();
                return true;
            case R.id.exit:
                salir();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void regresar(){

        // Toast.makeText(getApplicationContext(), "Regresar", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void salir(){

        //  Toast.makeText(getApplicationContext(), "Salir", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ReporteActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    protected void sendEmail(String fecha) {

        String fileName = "prueba.xls";

        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + '/', fileName));

        if(uri != null) {

            String[] TO = {"kenm.riv@gmail.com"}; //aquí pon tu correo
            String[] CC = {"kenm.riv@gmail.com"};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            // Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reporte de registros de campaña de " + dia + "/" + mes + "/" + anio);
            //emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            try {
                startActivity(Intent.createChooser(emailIntent, "Enviando Reporte..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ReporteActivity.this,
                        "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Error al mandar el xls", Toast.LENGTH_SHORT).show();
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
       Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();

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

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (canWrite && canRead) {
            this.writeFile();

        }
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        writeFile();
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFile() {

        Object nombre = null,
                 nombreMascota = null,
                 peso = null,
                 raza = null,
                 rescatado = null,
                 tamanoMascota= null,
                 telefono = null,
                 tipoMascota,
                 tratamiento = null,
                 vacuna = null,
                 medioSocial = null,
                 lactar = null,
                 folio = null,
                 edad = null,
                 direccion = null,
                 desparacitacion = null,
                 fecha = null,
                 correo = null,
                 comentarioRegistro = null,
                 celo = null,
                 alergia = null,

                 precio_anestesia = null,
                 cantidadTarjeta = null,
                 cantidadEfectivo = null,

                 dosis1 = null,
                 dosis2 = null,
                 dosis3 = null,
                 fecha_anestesia = null,  //checar si se encuentra el campo en el xls

                 cirujano = null,
                 comentario  = null,
                 prenada = null,
                 celo_cirugia = null,
                 lactante_cirugia = null,
                 hemorragia = null,
                 parorespiratorio = null,
                 parocardiaco = null,
                 testiculoinguinal = null,
                 finado = null,
                 alergico_cirugia = null,
                 operacion_anterior = null,
                 fecha_cirugia = null,

                 antibiotico = null,
                 tatuaje = null,
                 analgesico = null,
                 suero = null,
                 cicatrizante = null,
                 recuperacion_fecha = null,
                 comentario_recuperacion = null,
                 tallaIsabelino = null,
                 llevaIsabelino = null,
                 llevaMedicamento = null,


                 pregunta_medicamento = null,
                 isabelino = null,
                 isodine = null,
                 mascota_entregada = null,
                 candidato = null,
                 medicamento_fecha = null;

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

        if (membersView != date) {
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

            try {

                result = query.run();

            } catch (CouchbaseLiteException e) {

                e.printStackTrace();

            }

            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
                QueryRow row = it.next();

                folio = row.getDocument().getProperty("folio"); //0
                nombre = row.getDocument().getProperty("nombreDueno");  //1
                nombreMascota = row.getDocument().getProperty("nombreMascota");  //2 Trae nulo
                peso = row.getDocument().getProperty("peso");  //3
                raza = row.getDocument().getProperty("raza");  //4
                rescatado = row.getDocument().getProperty("rescatado");  //5
                tamanoMascota = row.getDocument().getProperty("tamanoMascota");  //6
                telefono = row.getDocument().getProperty("telefono");  //7
                tipoMascota = row.getDocument().getProperty("tipoMascota");  //8
                tratamiento = row.getDocument().getProperty("tratamiento");  //9
                vacuna = row.getDocument().getProperty("vacuna");   //10
                medioSocial = row.getDocument().getProperty("mediosocial");  //11
                lactar = row.getDocument().getProperty("lactar");  //12
               // folio = row.getDocument().getProperty("folio"); //13
                edad = row.getDocument().getProperty("edad");  //14
                direccion = row.getDocument().getProperty("direccion");  //15
                desparacitacion = row.getDocument().getProperty("desparacitacion");  //16
                fecha = row.getDocument().getProperty("creat_at"); //17
                correo = row.getDocument().getProperty("correo");  //18
                comentarioRegistro = row.getDocument().getProperty("comentarioRegistro"); //19
                celo = row.getDocument().getProperty("celo"); //20
                alergia = row.getDocument().getProperty("alergia"); //21

                precio_anestesia = row.getDocument().getProperty("precio_anestesia");
                cantidadEfectivo = row.getDocument().getProperty("cantidadEfectivo");
                cantidadTarjeta = row.getDocument().getProperty("cantidadTarjeta");

                dosis1 = row.getDocument().getProperty("dosis1");
                dosis2 = row.getDocument().getProperty("dosis2");
                dosis3 = row.getDocument().getProperty("dosis3");
                fecha_anestesia = row.getDocument().getProperty("fecha_anestesia");

                cirujano = row.getDocument().getProperty("cirujano");
                comentario = row.getDocument().getProperty("comentario");
                prenada = row.getDocument().getProperty("preñada");
                celo_cirugia = row.getDocument().getProperty("celo_cirugia");
                lactante_cirugia = row.getDocument().getProperty("lactante_cirugia");
                hemorragia = row.getDocument().getProperty("hemorragia");
                parorespiratorio = row.getDocument().getProperty("parorespiratorio");
                parocardiaco = row.getDocument().getProperty("parocardiaco");
                testiculoinguinal = row.getDocument().getProperty("testiculoinguinal");
                finado = row.getDocument().getProperty("finado");
                alergico_cirugia = row.getDocument().getProperty("alergico_cirugia");
                operacion_anterior = row.getDocument().getProperty("operacion_anterior");
                fecha_cirugia = row.getDocument().getProperty("fecha_cirugia");

                antibiotico = row.getDocument().getProperty("antibiotico");
                tatuaje = row.getDocument().getProperty("tatuaje");
                analgesico = row.getDocument().getProperty("analgesico");
                suero = row.getDocument().getProperty("suero");
                cicatrizante = row.getDocument().getProperty("cicatrizante");
                recuperacion_fecha = row.getDocument().getProperty("recuperacion_fecha");
                comentario_recuperacion = row.getDocument().getProperty("comentario_recuperacion");
                tallaIsabelino = row.getDocument().getProperty("tallaIsabelino");
                llevaIsabelino = row.getDocument().getProperty("tallaIsabelino");
                llevaMedicamento = row.getDocument().getProperty("llevaMedicamento");

                pregunta_medicamento = row.getDocument().getProperty("pregunta_medicamento");
                isabelino = row.getDocument().getProperty("isabelino");
                isodine = row.getDocument().getProperty("isodine");
                mascota_entregada = row.getDocument().getProperty("mascota_entregada");
                candidato = row.getDocument().getProperty("candidato");
                medicamento_fecha = row.getDocument().getProperty("medicamento_fecha");


                RegistroCompletoAdapter registroCompletoAdapter = new RegistroCompletoAdapter(
                        folio,
                        nombre,
                        nombreMascota,
                        peso,
                        raza,
                        rescatado,
                        tamanoMascota,
                        telefono,
                        tipoMascota,
                        tratamiento,
                        vacuna,
                        medioSocial,
                        lactar,
                        edad,
                        direccion,
                        desparacitacion,
                        fecha,
                        correo,
                        comentarioRegistro,
                        celo,
                        alergia,

                        precio_anestesia,
                        cantidadEfectivo,
                        cantidadTarjeta,

                        dosis1,
                        dosis2,
                        dosis3,
                        fecha_anestesia,

                        cirujano,
                        comentario,
                        prenada,
                        celo_cirugia,
                        lactante_cirugia,
                        hemorragia,
                        parorespiratorio,
                        parocardiaco,
                        testiculoinguinal,
                        finado,
                        alergico_cirugia,
                        operacion_anterior,
                        fecha_cirugia,

                        antibiotico,
                        tatuaje,
                        analgesico,
                        suero,
                        cicatrizante,
                        recuperacion_fecha,
                        comentario_recuperacion,
                        tallaIsabelino,
                        llevaIsabelino,
                        llevaMedicamento,

                        pregunta_medicamento,
                        isabelino,
                        isodine,
                        mascota_entregada,
                        candidato,
                        medicamento_fecha
                );

                registroCompletoAdapter.setFolio(folio);
                registroCompletoAdapter.setNombreDueno(nombre);
                registroCompletoAdapter.setNombreMascota(nombreMascota);
                registroCompletoAdapter.setPeso(peso);
                registroCompletoAdapter.setRaza(raza);
                registroCompletoAdapter.setRescatado(rescatado);
                registroCompletoAdapter.setTamanoMascota(tamanoMascota);
                registroCompletoAdapter.setTelefono(telefono);
                registroCompletoAdapter.setTipoMascota(tipoMascota);
                registroCompletoAdapter.setTratamiento(tratamiento);
                registroCompletoAdapter.setVacuna(vacuna);
                registroCompletoAdapter.setMediosocial(medioSocial);
                registroCompletoAdapter.setLactar(lactar);
                registroCompletoAdapter.setEdad(edad);
                registroCompletoAdapter.setDireccion(direccion);
                registroCompletoAdapter.setDesparacitacion(desparacitacion);
                registroCompletoAdapter.setFecha(fecha);
                registroCompletoAdapter.setCorreo(correo);
                registroCompletoAdapter.setComentarioRegistro(comentarioRegistro);
                registroCompletoAdapter.setCelo(celo);
                registroCompletoAdapter.setAlergia(alergia);

                registroCompletoAdapter.setPrecio_anestesia(precio_anestesia);
                registroCompletoAdapter.setCantidadEfectivo(cantidadEfectivo);
                registroCompletoAdapter.setCantidadTarjeta(cantidadTarjeta);

                registroCompletoAdapter.setDosis1(dosis1);
                registroCompletoAdapter.setDosis2(dosis2);
                registroCompletoAdapter.setDosis3(dosis3);
                registroCompletoAdapter.setFecha_anestesia(fecha_anestesia);

                registroCompletoAdapter.setCirujano(cirujano);
                registroCompletoAdapter.setComentario(comentario);
                registroCompletoAdapter.setPrenada(prenada);
                registroCompletoAdapter.setCelo_cirugia(celo_cirugia);
                registroCompletoAdapter.setLactante_cirugia(lactante_cirugia);
                registroCompletoAdapter.setHemorragia(hemorragia);
                registroCompletoAdapter.setParorespiratorio(parorespiratorio);
                registroCompletoAdapter.setParocardiaco(parocardiaco);
                registroCompletoAdapter.setTesticuloinguinal(testiculoinguinal);
                registroCompletoAdapter.setFinado(finado);
                registroCompletoAdapter.setAlergico_cirugia(alergico_cirugia);
                registroCompletoAdapter.setOperacion_anterior(operacion_anterior);
                registroCompletoAdapter.setFecha_cirugia(fecha_cirugia);

                registroCompletoAdapter.setAntibiotico(antibiotico);
                registroCompletoAdapter.setTatuaje(tatuaje);
                registroCompletoAdapter.setAnalgesico(analgesico);
                registroCompletoAdapter.setSuero(suero);
                registroCompletoAdapter.setCicatrizante(cicatrizante);
                registroCompletoAdapter.setRecuperacion_fecha(recuperacion_fecha);
                registroCompletoAdapter.setComentario_recuperacion(comentario_recuperacion);
                registroCompletoAdapter.setTallaIsabelino(tallaIsabelino);
                registroCompletoAdapter.setLlevaIsabelino(llevaIsabelino);
                registroCompletoAdapter.setLlevaMedicamento(llevaMedicamento);

                registroCompletoAdapter.setPregunta_medicamento(pregunta_medicamento);
                registroCompletoAdapter.setIsabelino(isabelino);
                registroCompletoAdapter.setIsodine(isodine);
                registroCompletoAdapter.setMascota_entregada(mascota_entregada);
                registroCompletoAdapter.setCandidato(candidato);
                registroCompletoAdapter.setMedicamento_fecha(medicamento_fecha);

                String estado = Environment.getExternalStorageState();

                if (estado.equals(Environment.MEDIA_MOUNTED)) {
                    sdDisponible = true;
                    sdAccesoEscritura = true;

                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet();
                    workbook.setSheetName(0, "Hoja Excel");

                    String[] headers = new String[]{
                            "Folio",
                            "Nombre Dueño",
                            "Dirección",
                            "Teléfono",
                            "Correo",
                            "Nombre Mascota",   //no lo muestra
                            "Medio Social",
                            "Raza",
                            "Edad",
                            "Tamaño de la Mascota",
                            "Comentario de Registro",
                            "Tratamiento",
                            "Rescatado",
                            "Vacuna",
                            "Desparacitación",
                            "Celo",
                            "Lactar",
                            "Tipo Mascota",
                            "Peso",
                            "Alergía",
                            "Fecha",
                            "Precio",     //muestra 0 pesos
                            "Cantidad Tarjeta",    //muestra 0 pesos
                            "Cantidad Efectivo",     //muestra 0 pesos
                            "Cantidad Dosis 1",       //nada
                            "Cantidad Dosis 2",       //nada
                            "Cantidad Dosis 3",       //nada
                            "Cirujano",               //nada
                            "Comentario Cirujano",
                            "Preñada",
                            "Celo Cirugía",
                            "Lactante Cirugía",
                            "Hemorragía",
                            "Paro Respiratorio",
                            "Paro Cardiaco",
                            "Testiculo Inguinal",
                            "Finado",
                            "Alergico Cirugía",
                            "Operación Anterior",
                            "Antibiótico",
                            "Tatuaje",               //nada
                            "Analgésico",
                            "Suero",
                            "Cicatrizante",
                            "Comentario Recuperación",
                            "Talla Isabelino",
                            "Lleva Isabelino",       //nada
                            "Lleva Medicamento",
                            "Pregunta Medicamento",
                            "Isabelino",
                            "Isodine",
                            "Mascota Entregada",
                            "Candidato",
                            "Medicamento_Fecha"
                                                    //extra
                                                    //extra

                    };



                      Object folioRecibido =  registroCompletoAdapter.getFolio();
                      Object nombreRecibido = registroCompletoAdapter.getNombreDueno();
                      Object nombreMascotaRecibido = registroCompletoAdapter.getNombreMascota();
                      Object pesoRecibido = registroCompletoAdapter.getPeso();
                      Object razaRecibido = registroCompletoAdapter.getRaza();
                      Object rescatadoRecibido = registroCompletoAdapter.getRescatado();
                      Object tamanoMascotaRecibido = registroCompletoAdapter.getTamanoMascota();
                      Object telefonoRecibido = registroCompletoAdapter.getTelefono();
                      Object tipoMascotaRecibido = registroCompletoAdapter.getTipoMascota();
                      Object tratamientoRecibido = registroCompletoAdapter.getTratamiento();
                      Object vacunaRecibido = registroCompletoAdapter.getVacuna();
                      Object medioSocialRecibido = registroCompletoAdapter.getMediosocial();
                      Object lactarRecibido = registroCompletoAdapter.getLactar();
                      Object edadRecibido = registroCompletoAdapter.getEdad();
                      Object direccionRecibido = registroCompletoAdapter.getDireccion();
                      Object desparacitacionRecibido = registroCompletoAdapter.getDesparacitacion();
                      Object fechaRecibido = registroCompletoAdapter.getFecha();
                      Object correoRecibido = registroCompletoAdapter.getCorreo();
                      Object comentarioRegistroRecibido = registroCompletoAdapter.getComentarioRegistro();
                      Object celoRecibido = registroCompletoAdapter.getCelo();
                      Object alergiaRecibido = registroCompletoAdapter.getAlergia();

                      Object precioAnestesiaRecibido = registroCompletoAdapter.getPrecio_anestesia();
                      Object cantidadEfectivoRecibido = registroCompletoAdapter.getCantidadEfectivo();
                      Object cantidadTarjetaRecibido = registroCompletoAdapter.getCantidadTarjeta();

                      Object dosis1Recibido = registroCompletoAdapter.getDosis1();
                      Object dosis2Recibido = registroCompletoAdapter.getDosis2();
                      Object dosis3Recibido = registroCompletoAdapter.getDosis3();
                      Object fechaAnestesiaRecibido = registroCompletoAdapter.getFecha_anestesia();

                      Object cirujanoRecibido = registroCompletoAdapter.getCirujano();
                      Object comentarioRecibido = registroCompletoAdapter.getComentario();
                      Object prenadaRecibido = registroCompletoAdapter.getPrenada();
                      Object celoCirugiaRecibido = registroCompletoAdapter.getCelo_cirugia();
                      Object lactanteCirugiaRecibido = registroCompletoAdapter.getLactante_cirugia();
                      Object hemorragiaRecibido = registroCompletoAdapter.getHemorragia();
                      Object paroRespiratorioRecibido = registroCompletoAdapter.getParorespiratorio();
                      Object paroCardiacoRecibido = registroCompletoAdapter.getParocardiaco();
                      Object testiculoInguinalRecibido = registroCompletoAdapter.getTesticuloinguinal();
                      Object finadoRecibido = registroCompletoAdapter.getFinado();
                      Object alergicoCirugiaRecibido = registroCompletoAdapter.getAlergico_cirugia();
                      Object operaionAnteriorRecibido = registroCompletoAdapter.getOperacion_anterior();
                      Object fechaOperacionRecibido = registroCompletoAdapter.getFecha_cirugia();

                      Object antibioticoRecibido = registroCompletoAdapter.getAntibiotico();
                      Object tatuajeRecibido = registroCompletoAdapter.getTatuaje();
                      Object analgesicoRecibido = registroCompletoAdapter.getAnalgesico();
                      Object sueroRecibido = registroCompletoAdapter.getSuero();
                      Object cicatrizanteRecibido = registroCompletoAdapter.getCicatrizante();
                      Object recuperacionFechaRecibido = registroCompletoAdapter.getRecuperacion_fecha();
                      Object comentarioRecuperacionRecibido = registroCompletoAdapter.getComentarioRegistro();
                      Object tallaIsabelinoRecibido = registroCompletoAdapter.getTallaIsabelino();
                      Object llevaIsabelinoRecibido = registroCompletoAdapter.getLlevaIsabelino();
                      Object llevaMedicamentoRecibido = registroCompletoAdapter.getLlevaMedicamento();
                      Object preguntaMedicamentoRecibido = registroCompletoAdapter.getPregunta_medicamento();
                      Object isabelinoRecibido = registroCompletoAdapter.getIsabelino();
                      Object isodineRecibido = registroCompletoAdapter.getIsodine();
                      Object mascotaEntregadaRecibido = registroCompletoAdapter.getMascota_entregada();
                      Object candidatoRecibido = registroCompletoAdapter.getCandidato();
                      Object medicamentoRecibido = registroCompletoAdapter.getMedicamento_fecha();

                   //   Toast.makeText(getApplicationContext(), folioRecibido, Toast.LENGTH_SHORT).show();

                    Object[][] data = new Object[][]{

                            new Object[]{folioRecibido,
                                    nombreRecibido,
                                    direccionRecibido,
                                    telefonoRecibido,
                                    correoRecibido,
                                    nombreMascotaRecibido,
                                    medioSocialRecibido,
                                    razaRecibido,
                                    edadRecibido,
                                    tamanoMascotaRecibido,
                                    comentarioRegistroRecibido,
                                    tratamientoRecibido,
                                    rescatadoRecibido,
                                    vacunaRecibido,
                                    desparacitacionRecibido,
                                    celoRecibido,
                                    lactarRecibido,
                                    tipoMascotaRecibido,
                                    pesoRecibido,
                                    alergiaRecibido,
                                    fechaRecibido,

                                    precioAnestesiaRecibido,
                                    cantidadEfectivoRecibido,
                                    cantidadTarjetaRecibido,

                                    dosis1Recibido,
                                    dosis2Recibido,
                                    dosis3Recibido,
                                    //fecha_anestesia,

                                    cirujanoRecibido,
                                    comentarioRecibido,
                                    prenadaRecibido,
                                    celoCirugiaRecibido,
                                    lactanteCirugiaRecibido,
                                    hemorragiaRecibido,
                                    paroRespiratorioRecibido,
                                    paroCardiacoRecibido,
                                    testiculoInguinalRecibido,
                                    finadoRecibido,
                                    alergicoCirugiaRecibido,
                                    operaionAnteriorRecibido,
                                    //fecha_cirugia,

                                    antibioticoRecibido,
                                    tatuajeRecibido,
                                    analgesicoRecibido,
                                    sueroRecibido,
                                    cicatrizanteRecibido,
                                 //   recuperacion_fecha,
                                    comentarioRecibido,
                                    tallaIsabelinoRecibido,
                                    llevaIsabelinoRecibido,
                                    llevaMedicamentoRecibido,

                                    preguntaMedicamentoRecibido,
                                    isabelinoRecibido,
                                    isodineRecibido,
                                    mascotaEntregadaRecibido,
                                    candidatoRecibido,
                                    medicamentoRecibido
                            }

                    };



                    CellStyle headerStyle = workbook.createCellStyle();
                    HSSFFont font = workbook.createFont();
                    font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
                    headerStyle.setFont(font);

                    CellStyle style = workbook.createCellStyle();
                    style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);

                    HSSFRow headerRow = sheet.createRow(0);
                    //creación de cada titulo por columna
                    for (int i = 0; i < headers.length; ++i) {
                        String header = headers[i];
                        HSSFCell cell = headerRow.createCell(i);
                        cell.setCellStyle(headerStyle);
                        cell.setCellValue(header);
                    }

                    //llenando cada campo con la base de datos
                    for (int i = 0; i < data.length; ++i) {
                        HSSFRow dataRow = sheet.createRow(i + 1);

                        Object[] d = data[i];
                        String folioD = (String) d[0];
                        String nombreD = (String) d[1];
                        String direccionM = (String) d[2];
                        String telefonoM = (String) d[3];
                        String correoM = (String) d[4];
                        String nombreM = (String) d[5];  //nada
                        String mSocialM = (String) d[6];
                        String razaM = (String) d[7];
                        String edadM = (String) d[8];
                        String tamanoM = (String) d[9];
                        String comentarioRegistroM = (String) d[10];
                        String tratamientoM = (String) d[11];
                        String rescatadoM = (String) d[12];
                        String vacunaM = (String) d[13];
                        String desparacitacionM = (String) d[14];
                        String celoM = (String) d[15];
                        String lactarM = (String) d[16];
                        String tipoMascotaM = (String) d[17];
                        String pesoM = (String) d[18];
                        String alergiaM = (String) d[19];
                        String fechaM = (String) d[20];

                        String precioM = (String) d[21];          //guarda 0
                        String cantidadTarjetaM = (String) d[22];    //guarda 0
                        String cantidadEfectivoM = (String) d[23];    //guarda 0


                        String cantidadAnestesia1M = (String) d[24];    //nada
                        String cantidadAnestesia2M = (String) d[25];    //nada
                        String cantidadAnestesia3M = (String) d[26];    //nada
            //            String fecha_anestesiaM = (String) d[27];

                        String cirujanoM = (String) d[27];          //nada
                        String comentarioM = (String) d[28];
                        String prenadaM = (String) d [29];
                        String celo_cirugiaM = (String) d[30];
                        String lactante_cirugiaM = (String) d[31];
                        String hemorragiaM = (String) d[32];
                        String parorespiratorioM = (String) d[33];
                        String parocardiacoM = (String) d[34];
                        String testiculoinguinalM = (String) d[35];
                        String finadoM = (String) d[36];
                        String alergicocirugiaM = (String) d[37];
                        String operacion_anteriorM = (String) d[38];
                //        String fecha_cirugiaM = (String) d[40];

                        String antibioticoM = (String) d[39];
                        String tatuajeM = (String) d[40];        //nada
                        String analgesicoM = (String) d[41];
                        String sueroM = (String) d[42];
                        String cicatrizanteM = (String) d[43];
                //        String recuperacion_fechaM = (String) d[46];
                        String comentario_recuperacionM = (String) d[44];
                        String tallaisabelinoM = (String) d[45];
                        String llevaisabelinoM = (String) d[46];
                        String llevaMedicamentoM = (String) d[47];

                        String pregunta_medicamentoM = (String) d[48];
                        String isabelinoM = (String) d[49];
                        String isodineM = (String) d[50];
                        String mascota_entregadaM = (String) d[51];
                        String candidatoM = (String) d[52];
                        String medicamentoFechaM = (String) d[53];


                        // String cirujanoM = (String) d[27];

                        dataRow.createCell(0).setCellValue(folioD);
                        dataRow.createCell(1).setCellValue(nombreD);
                        dataRow.createCell(2).setCellValue(direccionM);
                        dataRow.createCell(3).setCellValue(telefonoM);
                        dataRow.createCell(4).setCellValue(correoM);

                        dataRow.createCell(5).setCellValue(nombreM);
                        dataRow.createCell(6).setCellValue(mSocialM);
                        dataRow.createCell(7).setCellValue(razaM);
                        dataRow.createCell(8).setCellValue(edadM);
                        dataRow.createCell(9).setCellValue(tamanoM);
                        dataRow.createCell(10).setCellValue(comentarioRegistroM);
                        dataRow.createCell(11).setCellValue(tratamientoM);
                        dataRow.createCell(12).setCellValue(rescatadoM);
                        dataRow.createCell(13).setCellValue(vacunaM);
                        dataRow.createCell(14).setCellValue(desparacitacionM);
                        dataRow.createCell(15).setCellValue(celoM);

                        dataRow.createCell(16).setCellValue(lactarM);
                        dataRow.createCell(17).setCellValue(tipoMascotaM);
                        dataRow.createCell(18).setCellValue(pesoM);
                        dataRow.createCell(19).setCellValue(alergiaM);
                        dataRow.createCell(20).setCellValue(fechaM);

                        dataRow.createCell(21).setCellValue(precioM);
                        dataRow.createCell(22).setCellValue(cantidadTarjetaM);
                        dataRow.createCell(23).setCellValue(cantidadEfectivoM);

                        dataRow.createCell(24).setCellValue(cantidadAnestesia1M);
                        dataRow.createCell(25).setCellValue(cantidadAnestesia2M);
                        dataRow.createCell(26).setCellValue(cantidadAnestesia3M);
                     //   dataRow.createCell(27).setCellValue(fecha_anestesiaM);

                        dataRow.createCell(27).setCellValue(cirujanoM);
                        dataRow.createCell(28).setCellValue(comentarioM);
                        dataRow.createCell(29).setCellValue(prenadaM);
                        dataRow.createCell(30).setCellValue(celo_cirugiaM);
                        dataRow.createCell(31).setCellValue(lactante_cirugiaM);
                        dataRow.createCell(32).setCellValue(hemorragiaM);
                        dataRow.createCell(33).setCellValue(parorespiratorioM);
                        dataRow.createCell(34).setCellValue(parocardiacoM);
                        dataRow.createCell(35).setCellValue(testiculoinguinalM);
                        dataRow.createCell(36).setCellValue(finadoM);
                        dataRow.createCell(37).setCellValue(alergicocirugiaM);
                        dataRow.createCell(38).setCellValue(operacion_anteriorM);
               //         dataRow.createCell(40).setCellValue(fecha_cirugiaM);

                        dataRow.createCell(39).setCellValue(antibioticoM);
                        dataRow.createCell(40).setCellValue(tatuajeM);
                        dataRow.createCell(41).setCellValue(analgesicoM);
                        dataRow.createCell(42).setCellValue(sueroM);
                        dataRow.createCell(43).setCellValue(cicatrizanteM);
               //         dataRow.createCell(46).setCellValue(recuperacion_fechaM);
                        dataRow.createCell(44).setCellValue(comentario_recuperacionM);
                        dataRow.createCell(45).setCellValue(tallaisabelinoM);
                        dataRow.createCell(46).setCellValue(llevaisabelinoM);
                        dataRow.createCell(47).setCellValue(llevaMedicamentoM);

                        dataRow.createCell(48).setCellValue(pregunta_medicamentoM);
                        dataRow.createCell(49).setCellValue(isabelinoM);
                        dataRow.createCell(50).setCellValue(isodineM);
                        dataRow.createCell(51).setCellValue(mascota_entregadaM);
                        dataRow.createCell(52).setCellValue(candidatoM);
                        dataRow.createCell(53).setCellValue(medicamentoFechaM);

                        HSSFRow dataRoww = sheet.createRow(21 + data.length);
                        HSSFCell total = dataRoww.createCell(21);
                        total.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA);
                        total.setCellStyle(style);
                        total.setCellFormula(String.format("SUM(V2:V%d)", 1 + data.length));

                        String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/excel";

               /* File dir = new File(Environment.getExternalStorageDirectory() + file);
                if (dir.isDirectory())
                    dir.mkdirs();*/
                        // Log.d("PDFCreator", "PDF Path:" + file);

                        //if(Environment.getExternalStorageState() == true)

                        try {

                            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + '/';
                            String fileName = "prueba.xls";

                            File f = new File(baseDir, fileName);
                            FileOutputStream fileOutputStream = new FileOutputStream(f);
                            workbook.write(fileOutputStream);
                            fileOutputStream.close();

                            //  File nfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "prueba" + ".xls");
                            //  FileOutputStream filen = new FileOutputStream(nfile);
                            //  workbook.write(filen);
                            //  filen.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }

                // Toast.makeText(getApplicationContext(), String.valueOf(nombre), Toast.LENGTH_SHORT).show();

            }


        }

        //Comprobamos el estado de la memoria externa (tarjeta SD)



    }

    private void SearchRows () {

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

        if (membersView != date) {
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

            try {

                result = query.run();

            } catch (CouchbaseLiteException e) {

                e.printStackTrace();

            }

            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
                QueryRow row = it.next();


              Object nombre = row.getDocument().getProperty("nombreDueno");  //1
              Object nombreMascota = row.getDocument().getProperty("nombreMascota");  //2
              Object peso = row.getDocument().getProperty("peso");  //3
              Object raza = row.getDocument().getProperty("raza");  //4
              Object rescatado = row.getDocument().getProperty("recatado");  //5
              Object tamanoMascota = row.getDocument().getProperty("tamanoMascota");  //6
              Object telefono = row.getDocument().getProperty("telefono");  //7
              Object tipoMascota = row.getDocument().getProperty("tipoMascota");  //8
              Object tratamiento = row.getDocument().getProperty("tratamiento");  //9
              Object vacuna = row.getDocument().getProperty("vacuna");   //10
              Object medioSocial = row.getDocument().getProperty("mediosicial");  //11
              Object lactar = row.getDocument().getProperty("lactar");  //12
              Object folio = row.getDocument().getProperty("folio"); //13
              Object edad = row.getDocument().getProperty("edad");  //14
              Object direccion = row.getDocument().getProperty("direccion");  //15
              Object desparacitacion = row.getDocument().getProperty("desparacitacion");  //16
              Object fecha = row.getDocument().getProperty("creat_at"); //17
              Object correo = row.getDocument().getProperty("correo");  //18
              Object comentarioRegistro = row.getDocument().getProperty("comentarioRegistro"); //19
              Object celo = row.getDocument().getProperty("celo"); //20
              Object alergia = row.getDocument().getProperty("alergia"); //21


               // Toast.makeText(getApplicationContext(), String.valueOf(nombre), Toast.LENGTH_SHORT).show();

            }


        }

    }

    public void readXLSFile() throws IOException {

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;

           // String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";

           /* File dir = new File(file);
            if (!dir.exists())
                dir.mkdirs();*/

            String file = Environment.getExternalStorageDirectory().getAbsolutePath() + '/';

            String fileName = "prueba.xls";

            InputStream ExcelFileToRead = new FileInputStream(file + fileName);

            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            if (ExcelFileToRead != null) {



           /*     HSSFSheet sheet = wb.getSheetAt(0);
                HSSFRow row;
                HSSFCell cell;

                Iterator rows = sheet.rowIterator();

                while (rows.hasNext()) {
                    row = (HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();

                    while (cells.hasNext()) {
                        cell = (HSSFCell) cells.next();

                        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            System.out.print(cell.getStringCellValue() + " ");
                        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            System.out.print(cell.getNumericCellValue() + " ");
                        } else {
                            //U Can Handel Boolean, Formula, Errors
                        }
                    }
                    System.out.println();
                }*/

            }

        }
    }

}
