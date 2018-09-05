package com.example.dev.saludmock.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;
import com.example.dev.saludmock.adapters.NewDoctorAdapter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.OnItemClick;


public class AddNewDoctor extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnKeyListener {

    View view;
    private AutoCompleteTextView doctor;
    QueryEnumerator result;
    NewDoctorAdapter newDoctorAdapter;

    ListView itemListView;

    int idDocument;

    Object folio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_doctor);

        ScrollView scrollView = findViewById(R.id.ScrollView01);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // ListView item = (ListView) findViewById(R.id.recyclerView);
       // View child = getLayoutInflater().inflate(R.layout.content_add_new_doctor, null);
       // item.addView(child);

        itemListView = findViewById(R.id.recyclerView);
        doctor = findViewById(R.id.newdoctor);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addDoctor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, "Agregado correctamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        try {
            showDoctor();
        } catch (Exception e) {
            e.printStackTrace();
        }

     /*    itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long itemid= itemListView.getItemIdAtPosition(position);
                int idItem= (int)itemid;
                Object a =itemListView.getItemAtPosition(position).toString();



                //Create a manager
              Manager manager = null;
                try {
                    manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Database database = null;
                try {
                    database = manager.getDatabase("adlad");
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }

                Document document = null;

                try {
                    document.delete();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }

                try {
                    showDoctor();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
*/



    }

    private List<QueryRow> getRowsFromQueryEnumerator(QueryEnumerator queryEnumerator) {
        List<QueryRow> rows = new ArrayList<QueryRow>();
        for (Iterator<QueryRow> it = queryEnumerator; it.hasNext(); ) {
            QueryRow row = it.next();
            rows.add(row);
        }
        return rows;
    }

    private void addDoctor() throws Exception {

        SharedPreferences preferences = getSharedPreferences("valuesd", MODE_PRIVATE);
        idDocument = preferences.getInt("idDocument", 0);

        idDocument++;

        SharedPreferences prefs = getSharedPreferences("valuesd", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("idDocument", idDocument);
        editor.commit();

        String idDocumentS = Integer.toString(idDocument);

        String add_doctor = "";


        String nomdoctor = doctor.getText().toString();


                        //create manager
                        Manager manager = null;
                        try {
                            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Create or open the database named app
                        Database database = null;
                        try {
                            database = manager.getDatabase("adlad");
                        } catch (CouchbaseLiteException d) {
                            d.printStackTrace();
                        }



                        //The properties that will be saved on the document
                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put("doctor", nomdoctor);
                        properties.put("folio", idDocumentS);

                        //Create a new document
                        Document document = database.getDocument(String.valueOf(idDocument));

                        //Save the document to the database
                        try {
                            document.putProperties(properties);
                        } catch (CouchbaseLiteException e) {
                            e.printStackTrace();
                        }

                        showDoctor();
    }

    public void showDoctor() throws Exception {

        Object nombre = null,
                folio = null;

        //Create a manager
        Manager manager = null;
        try {
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

       Database database = null;
        try {
            database = manager.getDatabase("adlad");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        com.couchbase.lite.View doctorView = database.getView("doctor");

        if (doctorView != null) {
            doctorView.setMap(
                    new Mapper() {
                        @Override
                        public void map(Map<String, Object> document, Emitter emitter) {
                            com.couchbase.lite.util.Log.e("inside map", "map");
                            Object doctorn = document.get("doctor");
                            if (doctorn != null)
                                emitter.emit((String) document.get("doctor"), document);


                        }
                    }, "1" /* The version number of the mapper... */
            );
            // }

            Query query = database.getView("doctor").createQuery();
            query.setDescending(true);

            try {

                result = query.run();

            } catch (CouchbaseLiteException e) {

                e.printStackTrace();

            }

            displayRows(result);

            getRowsFromQueryEnumerator(result);

        }else{

            doctorView.setMap(
                    new Mapper() {
                        @Override
                        public void map(Map<String, Object> document, Emitter emitter) {
                            com.couchbase.lite.util.Log.e("inside map", "map");
                            Object date = document.get("doctor");
                            if (date != null)
                                emitter.emit((String) document.get("doctor"), document);


                        }
                    }, "1" /* The version number of the mapper... */
            );
            // }

            Query query = database.getView("doctor").createQuery();
            query.setDescending(true);

            try {

                result = query.run();

            } catch (CouchbaseLiteException e) {

                e.printStackTrace();

            }

            displayRows(result);

            getRowsFromQueryEnumerator(result);
        }
    }

    private void displayRows(QueryEnumerator queryEnumerator) {

        //View view = View.inflate(R.layout.agregarmedico_main, );

        //   @SuppressLint("WrongViewCast") ConstraintLayout item = findViewById(R.id.newdoctor);
        //  View child = getLayoutInflater().inflate(R.layout.register_list_doctor, null);
        //   item.addView(child);

        final List<QueryRow> rows = getRowsFromQueryEnumerator(queryEnumerator);
        //itemListView = findViewById(R.id.itemListView);

        for(Iterator<QueryRow> it = result; it.hasNext();){
            QueryRow row = it.next();
            }
        if(rows.size()>0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    newDoctorAdapter = new NewDoctorAdapter(
                            getApplicationContext(),
                            R.layout.agregarmedico_main,
                            R.id.txt_numero,
                            R.id.txt_folio,
                            rows
                    );

                    itemListView.setAdapter(newDoctorAdapter);

                    itemListView.setOnItemClickListener(AddNewDoctor.this);
                    itemListView.setOnItemLongClickListener(AddNewDoctor.this);

                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Todavia no tiene registros", Toast.LENGTH_SHORT).show();
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_SHORT).show();
        }
    };

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
     Intent i = new Intent(AddNewDoctor.this, LoginActivity.class);
     startActivity(i);
     finish();
    }

    public void eliminaRegistro(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void editarRegistro(){

    }


  @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        long itemid= itemListView.getItemIdAtPosition(position);
        int idItem= (int)itemid;
        Object a =itemListView.getItemAtPosition(idItem).toString();

        String folio = newDoctorAdapter.getId();





       // Toast.makeText(getApplicationContext(), "Editar", Toast.LENGTH_SHORT).show();
        Snackbar.make(view,"", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


        }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

       // Toast.makeText(getApplicationContext(), "Elimina", Toast.LENGTH_SHORT).show();
        Snackbar.make(view, "Eliminar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        return false;
    }
}
