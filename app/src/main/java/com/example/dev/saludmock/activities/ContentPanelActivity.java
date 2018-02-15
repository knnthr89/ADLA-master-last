package com.example.dev.saludmock.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.DiskLruCache;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by kennethrizo on 22/11/17.
 */

public class ContentPanelActivity extends AppCompatActivity {

    private static final String CIRCLEBUTTON_COLOR = "CIRCLEBUTTON_COLOR";

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;

    BottomNavigationView bottomNavigationView;

    ImageButton imageButton;

    DatePickerDialog datePickerDialog;

    EditText et;

    String mascota = null;
    //  String tmascota = "perro";
    //  String t2mascota = "gato";

    //botones de gatos
    CircleButton circleButton1,
            circleButton2,
            circleButton3,
            circleButton4,
            circleButton5,
            circleButton6,
            circleButton7,
            circleButton8,
            circleButton9,
            circleButton10,
            circleButton11,
            circleButton12,
            circleButton13,
            circleButton14,
            circleButton15,
            circleButton16,
            circleButton17,
            circleButton18,
            circleButton19,
            circleButton20,
            circleButton21,
            circleButton22,
            circleButton23,
            circleButton24,
            circleButton25,
            circleButton26,
            circleButton27,
            circleButton28,
            circleButton29,
            circleButton30,
            circleButton31,
            circleButton32,
            circleButton33,
            circleButton34,
            circleButton35,
            circleButton36;

    //botones deperros
    CircleButton circleButton37,
    circleButton38,
    circleButton39,
    circleButton40,
    circleButton41,
    circleButton42,
    circleButton43,
    circleButton44,
    circleButton45,
    circleButton46,
    circleButton47,
    circleButton48,
    circleButton49,
    circleButton50,
    circleButton51,
    circleButton52,
    circleButton53,
    circleButton54,
    circleButton55,
    circleButton56,
    circleButton57,
    circleButton58,
    circleButton59,
    circleButton60,
    circleButton61,
    circleButton62,
    circleButton63,
    circleButton64,
    circleButton65,
    circleButton66,
    circleButton67,
    circleButton68,
    circleButton69,
    circleButton70,
    circleButton71,
    circleButton72;

    //textview de gatos
    TextView tv1,
             tv2,
             tv3,
             tv4,
             tv5,
             tv6,
             tv7,
             tv8,
             tv9,
            tv10,
            tv11,
            tv12,
            tv13,
            tv14,
            tv15,
            tv16,
            tv17,
            tv18,
            tv19,
            tv20,
            tv21,
            tv22,
            tv23,
            tv24,
            tv25,
            tv26,
            tv27,
            tv28,
            tv29,
            tv30,
            tv31,
            tv32,
            tv33,
            tv34,
            tv35,
            tv36;

    //textview de perros

    TextView tv37,
    tv38,
    tv39,
    tv40,
    tv41,
    tv42,
    tv43,
    tv44,
    tv45,
    tv46,
    tv47,
    tv48,
    tv49,
    tv50,
    tv51,
    tv52,
    tv53,
    tv54,
    tv55,
    tv56,
    tv57,
    tv58,
    tv59,
    tv60,
    tv61,
    tv62,
    tv63,
    tv64,
    tv65,
    tv66,
    tv67,
    tv68,
    tv69,
    tv70,
    tv71,
    tv72;

    Spinner smedida, smedio, stmascota;


    Locale l = new Locale("es", "MX");
   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),l);
    String fecha = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE)/* + "T" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":"  + cal.get(Calendar.SECOND)*/;

    int idDocument;


  //  String [] modulos = {"Anestesia", "Cirugía", "Recuperación"};
    String [] medida = {"Tamaño de la mascota","Chico", "Mediano", "Grande", "Gigante"};
    String [] medio = {"¿Cómo te enteraste de la campaña?","Facebook","Volante","Vi una lona en la campaña", "Ya he ido a otra campaña de Abogados de los Animales", "Recomendación de otra persona", "Otro"};
   // String [] tamano_mascota = {"Detalles de las Mascotas", "Perro", "Perra", "Gato", "Gata"};
   String [] pet_size = {"Detalle de la Mascota", "Perro", "Perra", "Gato", "Gata"};
    String [] pet = {"Desea cambiar el sexo de la mascota?", "Perro", "Perra", "Gato", "Gata"};

   private String PREFS_KEY = "mispreferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_panel);

        bottomNavigationView = findViewById(R.id.navigation);

        ButterKnife.bind(this);



      //  imageButton = (ImageButton)findViewById(R.id.imageButton);

      //  mascota = getIntent().getStringExtra("mascota");

       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        et = findViewById(R.id.et);

        //botones y textview de gatos
        circleButton1 = findViewById(R.id.btn1);
        tv1 = findViewById(R.id.message);
        circleButton2 = findViewById(R.id.btn2);
        tv2 = findViewById(R.id.message2);
        circleButton3 = findViewById(R.id.btn3);
        tv3 = findViewById(R.id.message3);
        circleButton4 = findViewById(R.id.btn4);
        tv4 = findViewById(R.id.message4);
        circleButton5 = findViewById(R.id.btn5);
        tv5 = findViewById(R.id.message5);
        circleButton6 = findViewById(R.id.btn6);
        tv6 = findViewById(R.id.message6);
        circleButton7 = findViewById(R.id.btn7);
        tv7 = findViewById(R.id.message7);
        circleButton8 = findViewById(R.id.btn8);
        tv8 = findViewById(R.id.message8);
        circleButton9 = findViewById(R.id.btn9);
        tv9 = findViewById(R.id.message9);
        circleButton10 = findViewById(R.id.btn10);
        tv10 = findViewById(R.id.message10);
        circleButton11 = findViewById(R.id.btn11);
        tv11 = findViewById(R.id.message11);
        circleButton12 = findViewById(R.id.btn12);
        tv12 = findViewById(R.id.message12);
        circleButton13 = findViewById(R.id.btn13);
        tv13 = findViewById(R.id.message13);
        circleButton14 = findViewById(R.id.btn14);
        tv14 = findViewById(R.id.message14);
        circleButton15 = findViewById(R.id.btn15);
        tv15 = findViewById(R.id.message15);
        circleButton16 = findViewById(R.id.btn16);
        tv16 = findViewById(R.id.message16);
        circleButton17 = findViewById(R.id.btn17);
        tv17 = findViewById(R.id.message17);
        circleButton18 = findViewById(R.id.btn18);
        tv18 = findViewById(R.id.message18);
        circleButton19 = findViewById(R.id.btn19);
        tv19 = findViewById(R.id.message19);
        circleButton20 = findViewById(R.id.btn20);
        tv20 = findViewById(R.id.message20);
        circleButton21 = findViewById(R.id.btn21);
        tv21 = findViewById(R.id.message21);
        circleButton22 = findViewById(R.id.btn22);
        tv22 = findViewById(R.id.message22);
        circleButton23 = findViewById(R.id.btn23);
        tv23 = findViewById(R.id.message23);
        circleButton24 = findViewById(R.id.btn24);
        tv24 = findViewById(R.id.message24);
        circleButton25 = findViewById(R.id.btn25);
        tv25 = findViewById(R.id.message25);
        circleButton26 = findViewById(R.id.btn26);
        tv26 = findViewById(R.id.message26);
        circleButton27 = findViewById(R.id.btn27);
        tv27 = findViewById(R.id.message27);
        circleButton28 = findViewById(R.id.btn28);
        tv28 = findViewById(R.id.message28);
        circleButton29 = findViewById(R.id.btn29);
        tv29 = findViewById(R.id.message29);
        circleButton30 = findViewById(R.id.btn30);
        tv30 = findViewById(R.id.message30);
        circleButton31 = findViewById(R.id.btn31);
        tv31 = findViewById(R.id.message31);
        circleButton32 = findViewById(R.id.btn32);
        tv32 = findViewById(R.id.message32);
        circleButton33 = findViewById(R.id.btn33);
        tv33 = findViewById(R.id.message33);
        circleButton34 = findViewById(R.id.btn34);
        tv34 = findViewById(R.id.message34);
        circleButton35 = findViewById(R.id.btn35);
        tv35 = findViewById(R.id.message35);
        circleButton36 = findViewById(R.id.btn36);
        tv36 = findViewById(R.id.message36);

        //botones y textview de perros
        circleButton37 = findViewById(R.id.btn37);
        tv37 = findViewById(R.id.message37);
        circleButton38 = findViewById(R.id.btn38);
        tv38 = findViewById(R.id.message38);
        circleButton39 = findViewById(R.id.btn39);
        tv39 = findViewById(R.id.message39);
        circleButton40 = findViewById(R.id.btn40);
        tv40 = findViewById(R.id.message40);
        circleButton41 = findViewById(R.id.btn41);
        tv41 = findViewById(R.id.message41);
        circleButton42 = findViewById(R.id.btn42);
        tv42 = findViewById(R.id.message42);
        circleButton43 = findViewById(R.id.btn43);
        tv43 = findViewById(R.id.message43);
        circleButton44 = findViewById(R.id.btn44);
        tv44 = findViewById(R.id.message44);
        circleButton45 = findViewById(R.id.btn45);
        tv45 = findViewById(R.id.message45);
        circleButton46 = findViewById(R.id.btn46);
        tv46 = findViewById(R.id.message46);
        circleButton47 = findViewById(R.id.btn47);
        tv47 = findViewById(R.id.message47);
        circleButton48 = findViewById(R.id.btn48);
        tv48 = findViewById(R.id.message48);
        circleButton49 = findViewById(R.id.btn49);
        tv49 = findViewById(R.id.message49);
        circleButton50 = findViewById(R.id.btn50);
        tv50 = findViewById(R.id.message50);
        circleButton51 = findViewById(R.id.btn51);
        tv51 = findViewById(R.id.message51);
        circleButton52 = findViewById(R.id.btn52);
        tv52 = findViewById(R.id.message52);
        circleButton53 = findViewById(R.id.btn53);
        tv53 = findViewById(R.id.message53);
        circleButton54 = findViewById(R.id.btn54);
        tv54 = findViewById(R.id.message54);
        circleButton55 = findViewById(R.id.btn55);
        tv55 = findViewById(R.id.message55);
        circleButton56 = findViewById(R.id.btn56);
        tv56 = findViewById(R.id.message56);
        circleButton57 = findViewById(R.id.btn57);
        tv57 = findViewById(R.id.message57);
        circleButton58 = findViewById(R.id.btn58);
        tv58 = findViewById(R.id.message58);
        circleButton59 = findViewById(R.id.btn59);
        tv59 = findViewById(R.id.message59);
        circleButton60 = findViewById(R.id.btn60);
        tv60 = findViewById(R.id.message60);
        circleButton61 = findViewById(R.id.btn61);
        tv61 = findViewById(R.id.message61);
        circleButton62 = findViewById(R.id.btn62);
        tv62 = findViewById(R.id.message62);
        circleButton63 = findViewById(R.id.btn63);
        tv63 = findViewById(R.id.message63);
        circleButton64 = findViewById(R.id.btn64);
        tv64 = findViewById(R.id.message64);
        circleButton65 = findViewById(R.id.btn65);
        tv65 = findViewById(R.id.message65);
        circleButton66 = findViewById(R.id.btn66);
        tv66 = findViewById(R.id.message66);
        circleButton67 = findViewById(R.id.btn67);
        tv67 = findViewById(R.id.message67);
        circleButton68 = findViewById(R.id.btn68);
        tv68 = findViewById(R.id.message68);
        circleButton69 = findViewById(R.id.btn69);
        tv69 = findViewById(R.id.message69);
        circleButton70 = findViewById(R.id.btn70);
        tv70 = findViewById(R.id.message70);
        circleButton71 = findViewById(R.id.btn71);
        tv71 = findViewById(R.id.message71);
        circleButton72 = findViewById(R.id.btn72);
        tv72 = findViewById(R.id.message72);

        smedida = findViewById(R.id.medida);
        smedio = findViewById(R.id.medio);
        stmascota = findViewById(R.id.tmascota);



//        smedida.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, medida));


      //  String idDocumentS = Integer.toString(idDocument);

      //  Toast.makeText(getApplicationContext(), idDocumentS, Toast.LENGTH_SHORT).show();


        Bundle datos = this.getIntent().getExtras();
        String idDocumentStringS = datos.getString("idDocumentStringS");
        String tmascota = datos.getString("tmascota");

       //falta editar si escogen perro o perra, para cambiar de método
        if(idDocumentStringS != null){
            buscaDesocupadoGato(idDocumentStringS);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    Intent intent = new Intent(ContentPanelActivity.this, AddUrlSheet.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_home:
                   /* Intent i = new Intent(ContentPanelActivity.this, RegistroActivity.class);
                    startActivity(i);*/
                    registroActivity();
                    return true;
                case R.id.navigation_dashboard:
               //recuperacionDialog();
                    return true;
                case R.id.navigation_notifications:
                    reporteRegistros();
                   // Intent tintent = new Intent(ContentPanelActivity.this, ReporteActivity.class);
                   // startActivity(tintent);
                    return true;
            }
            return false;
        }
    };


    public void registroActivity(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.registroalert_main, null);

        final AutoCompleteTextView nombre = view.findViewById(R.id.nombre_dueño);
        final AutoCompleteTextView direccion = view.findViewById(R.id.direccion);
        final AutoCompleteTextView telefono = view.findViewById(R.id.telefono);
        final AutoCompleteTextView email = view.findViewById(R.id.email);
        final AutoCompleteTextView nmascota = view.findViewById(R.id.mascota);
        final Spinner msocial =view.findViewById(R.id.tmascota);
        final AutoCompleteTextView raza = view.findViewById(R.id.raza);
        final AutoCompleteTextView edad = view.findViewById(R.id.edad);
        final Spinner tmascota = view.findViewById(R.id.msocial);
        final AutoCompleteTextView comentarios = view.findViewById(R.id.comentarios);
        final AutoCompleteTextView tratamiento = view.findViewById(R.id.tratamiento);
        final AutoCompleteTextView rescatado = view.findViewById(R.id.rescatado);
        final AutoCompleteTextView vacuna = view.findViewById(R.id.vacuna);
        final AutoCompleteTextView desparacitacion = view.findViewById(R.id.desparacitacion);
        final AutoCompleteTextView celo = view.findViewById(R.id.celo);
        final AutoCompleteTextView lactar = view.findViewById(R.id.lactar);
        final Spinner tipo_mascota = view.findViewById(R.id.tamano_mascota);
        final AutoCompleteTextView peso = view.findViewById(R.id.peso);


        //medio social
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(ContentPanelActivity.this, android.R.layout.simple_spinner_item, medio);
        msocial.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        msocial.setAdapter(adp);

        //tamaño de mascota
        final ArrayAdapter<String> adp1 = new ArrayAdapter<String>(ContentPanelActivity.this, android.R.layout.simple_spinner_item, medida);
        tmascota.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tmascota.setAdapter(adp1);

        //tipo mascota
        final ArrayAdapter<String> adp2 = new ArrayAdapter<String>(ContentPanelActivity.this, android.R.layout.simple_spinner_item, pet_size);
        tipo_mascota.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tipo_mascota.setAdapter(adp2);

        final AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Registra nueva mascota");

        builder.setView(view);

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                            String nombreString = nombre.getText().toString(); //
                            String direccionString = direccion.getText().toString();//
                            String telefonoString = telefono.getText().toString(); //
                            String emailString = email.getText().toString();//
                            String mascota = nmascota.getText().toString();//
                            String msocialString = msocial.getSelectedItem().toString(); //
                            String razaString = raza.getText().toString();//
                            String edadString = edad.getText().toString();//
                            String tamanoMascotaString = msocial.getSelectedItem().toString(); //
                            String comentarioString = comentarios.getText().toString(); //
                            String tratamientoString = tratamiento.getText().toString(); //
                            String rescatadoString = rescatado.getText().toString(); //
                            String vacunaString = vacuna.getText().toString();  //
                            String desparacitacionString = desparacitacion.getText().toString(); //
                            String celoString = celo.getText().toString();//
                            String lactarString = lactar.getText().toString();//
                            String tipoMascotaString = tipo_mascota.getSelectedItem().toString();
                            String pesoString = peso.getText().toString();

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
                                database = manager.getDatabase("adla");
                            } catch (CouchbaseLiteException d) {
                                d.printStackTrace();
                            }


                        //The properties that will be saved on the document
                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put("nombreDueno", nombreString);
                        properties.put("direccion", direccionString);
                        properties.put("telefono", telefonoString);
                        properties.put("correo", emailString);
                        properties.put("nombreMascota", mascota);
                        properties.put("mediosocial", msocialString);
                        properties.put("raza", razaString);
                        properties.put("edad", edadString);
                        properties.put("tamanoMascota", tamanoMascotaString);
                        properties.put("comentarioRegistro", comentarioString);
                        properties.put("tratamiento", tratamientoString);
                        properties.put("rescatado", rescatadoString);
                        properties.put("vacuna", vacunaString);
                        properties.put("desparacitacion", desparacitacionString);
                        properties.put("celo", celoString);
                        properties.put("lactar", lactarString);
                        properties.put("tipoMascota", tipoMascotaString);
                        properties.put("peso", pesoString);
                        properties.put("creat_at", fecha);

                        SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
                        idDocument = preferences.getInt("idDocument", 0);

                        idDocument++;

                        SharedPreferences prefs = getSharedPreferences("values", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("idDocument", idDocument);
                        editor.commit();

                        String idDocumentS = Integer.toString(idDocument);

                        //Create a new document
                        Document document = database.getDocument(idDocumentS);


                        //Save the document to the database
                        try{
                            document.putProperties(properties);
                        }catch (CouchbaseLiteException e){
                            e.printStackTrace();
                        }

                        if(tipoMascotaString != "Detalle de la Mascota") {
                            if (tipoMascotaString == "Gato" || tipoMascotaString == "Gata") {
                                buscaDesocupadoGato(idDocumentS);
                                Toast.makeText(getApplicationContext(), "El id de registro es " + idDocumentS + " con fecha " + fecha, Toast.LENGTH_SHORT).show();
                                askPermissionAndWriteFile();

                            } else if (tipoMascotaString == "Perro" || tipoMascotaString == "Perra") {
                                buscaDesocupadoPerro(idDocumentS);
                                Toast.makeText(getApplicationContext(), "El id de registro es " + idDocumentS + " con fecha " + fecha, Toast.LENGTH_SHORT).show();
                                askPermissionAndWriteFile();

                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Debe de seleccionar alguna opcion de Detelles de la mascota", Toast.LENGTH_SHORT).show();
                        }

                      //  buscaDesocupadoAlternativa(idDocumentS);
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //    builder.setView(sp1);
        builder.show().getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
    }

    public void anestesiaDialog(final String idDocumentStringS){

        final AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Dosis de anestesia");

       // Context context = builder.getContext();
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText dosis1 = new EditText(this);
        dosis1.setHint("Primer dosis");
        layout.addView(dosis1);
        dosis1.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText dosis2 = new EditText(this);
        dosis2.setHint("Segunda dosis");
        layout.addView(dosis2);
        dosis2.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText dosis3 = new EditText(this);
        dosis3.setHint("Tercer dosis");
        layout.addView(dosis3);
        dosis3.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(layout);

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String dosisuno = dosis1.getText().toString();
                        String dosisdos = dosis2.getText().toString();
                        String dosistres = dosis3.getText().toString();

                        // Create a manager
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

                        //The properties that will be saved on the document
                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put("dosis1", dosisuno);
                        properties.put("dosis2", dosisdos);
                        properties.put("dosis3", dosistres);
                        properties.put("fecha_anestesia", fecha);

                        //Create a new document
                        Document document = database.getDocument(idDocumentStringS);

                        //Save the document to the database
                        try{
                            document.putProperties(properties);
                        }catch (CouchbaseLiteException e){
                            e.printStackTrace();
                        }


                        Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();

                       int color = Color.GREEN;
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           dialogInterface.cancel();
            }
        });
        builder.show().getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
    }

    public void cirugiaDialog(final String idDocumentStringS){

        final String tipoMascotaString;

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.cirugiaalert_main, null);

        final AutoCompleteTextView cirujano = view.findViewById(R.id.nombre_cirujano);
        final AutoCompleteTextView comentario = view.findViewById(R.id.comentario);

      //  final TextView tvtexto = view.findViewById(R.id.texto);
        final TextView tv = view.findViewById(R.id.tipomascotaseleccionada);

        final Spinner tipo_mascota =view.findViewById(R.id.tmascota);

        //tipo_mascota
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(ContentPanelActivity.this, android.R.layout.simple_spinner_item, pet);
        tipo_mascota.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tipo_mascota.setAdapter(adp);

        //Create a manager
        Manager manager = null;
        try{
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        }catch (IOException e){
            e.printStackTrace();
        }

        Database database = null;
        try {
            database = manager.getDatabase("adla");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        Document doc = database.getDocument(idDocumentStringS);

        doc.getProperty("tipoMascota");

        Map<String, Object> properties = doc.getProperties();
        tipoMascotaString = (String) properties.get("tipoMascota");


        tv.setText(tipoMascotaString);

        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Cirugía");

        builder.setView(view);
        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    final String ncirujano = cirujano.getText().toString();
                    final String ncomentario = comentario.getText().toString();
                    final String tipomascotaString = tipo_mascota.getSelectedItem().toString();

                    if (tipomascotaString == "Desea cambiar el sexo de la mascota?" || tipomascotaString.equals(tipoMascotaString)){

                        //Create a manager
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

                        //The properties that will be saved on the document
                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put("cirujano", ncirujano);
                        properties.put("comentario", ncomentario);
                        properties.put("fecha_cirugia", fecha);

                        //create a new document
                        Document document = database.getDocument(idDocumentStringS);

                        //Save the document tothe database
                        try{
                            document.putProperties(properties);
                        }catch (CouchbaseLiteException e){
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_SHORT).show();

                    }else {

                        //Create a manager
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

                        //create a new document
                        Document document = database.getDocument(idDocumentStringS);

                        try{
                            document.update(new Document.DocumentUpdater() {
                                @Override
                                public boolean update(UnsavedRevision newRevision) {
                                    Map<String, Object> properties = newRevision.getProperties();
                                    properties.put("cirujano",ncirujano);
                                    properties.put("comentario", ncomentario);
                                    properties.put("tipoMascota", tipomascotaString);
                                    properties.put("fecha_cirugia", fecha);

                                    return true;
                                }
                            });
                            Toast.makeText(getApplicationContext(), "Se cambió de sexo la mascota", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();
                        }catch (CouchbaseLiteException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
                        }
                    }
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
    }

    public void recuperacionDialog(final String idDocumentStringS){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Recuperación");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.recuperacionalert_main, null);

        final Switch antibiotico = view.findViewById(R.id.antibiotico_switch);
        final Switch tatuaje = view.findViewById(R.id.tatuaje_switch);
        final Switch analgesico = view.findViewById(R.id.analgesico_switch);
        final Switch suero = view.findViewById(R.id.suero_switch);
        final Switch cicatrizante = view.findViewById(R.id.cicatrizante_switch);
        final Switch otro = view.findViewById(R.id.otro_switch);
        final AutoCompleteTextView comentario = view.findViewById(R.id.comentario);

        builder.setView(view);

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String antibioticoString;
                        final String tatuajeString;
                        final String analgesicoString;
                        final String sueroString;
                        final String cicatrizanteString;
                        final String otroString;
                        final String comentarioString = comentario.getText().toString();

                        if(antibiotico.isChecked()){
                       antibioticoString = "si";
                      Toast.makeText(getApplicationContext(), "Antibiótico" + antibioticoString, Toast.LENGTH_SHORT).show();

                      }else{
                       antibioticoString = "no";
                       Toast.makeText(getApplicationContext(), "Antibiótico" + antibioticoString, Toast.LENGTH_SHORT).show();
                      }

                   if(tatuaje.isChecked()){
                       tatuajeString = "si";
                       Toast.makeText(getApplicationContext(), "Tatuaje" + tatuajeString, Toast.LENGTH_SHORT).show();
                   }else{
                       tatuajeString = "no";
                       Toast.makeText(getApplicationContext(), "Tatuaje" + tatuajeString, Toast.LENGTH_SHORT).show();
                       }

                   if(analgesico.isChecked()){
                       analgesicoString = "si";
                            Toast.makeText(getApplicationContext(), "Analgésico" + analgesicoString, Toast.LENGTH_SHORT).show();
                        }else{
                       analgesicoString = "no";
                            Toast.makeText(getApplicationContext(), "Analgésico" + analgesicoString, Toast.LENGTH_SHORT).show();
                        }

                   if(suero.isChecked()){
                       sueroString = "si";
                            Toast.makeText(getApplicationContext(), "Suero" + sueroString, Toast.LENGTH_SHORT).show();
                        }else{
                       sueroString = "no";
                            Toast.makeText(getApplicationContext(), "Suero" + sueroString, Toast.LENGTH_SHORT).show();
                        }

                        if(cicatrizante.isChecked()){
                       cicatrizanteString = "si";
                            Toast.makeText(getApplicationContext(), "Cicatrizante" + cicatrizanteString, Toast.LENGTH_SHORT).show();
                        }else{
                            cicatrizanteString = "no";
                            Toast.makeText(getApplicationContext(), "Cicatrizante" + cicatrizanteString, Toast.LENGTH_SHORT).show();
                        }

                        if(otro.isChecked()){
                            otroString = "si";
                            Toast.makeText(getApplicationContext(), "Otro" + otroString, Toast.LENGTH_SHORT).show();

                        }else{
                            otroString = "no";
                            Toast.makeText(getApplicationContext(), "Otro" + otroString, Toast.LENGTH_SHORT).show();
                        }

                        comentario.getText().toString();

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

                        Document document = database.getDocument(idDocumentStringS);

                        try{
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
                                    properties.put("recuperacion_fecha", fecha);
                                    properties.put("comentario_recuperacion", comentarioString);
                                    return true;
                                }
                            });
                            Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();
                        }catch (CouchbaseLiteException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show().getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
    }

    public void compraMedicamentoDialog(final String idDocumentStringS){


        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Medicamento");
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.compramedicamentoalert_main, null);

        final Switch pregunta = view.findViewById(R.id.antibiotico_switch);

        builder.setView(view);

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String idDocumentStringS1 = idDocumentStringS;
                        final String preguntaString;

                        if (pregunta.isChecked()) {
                            preguntaString = "Si";
                        } else {
                            preguntaString = "No";
                        }

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

                        Document document = database.getDocument(idDocumentStringS1);

                        try {
                            document.update(new Document.DocumentUpdater() {
                                @Override
                                public boolean update(UnsavedRevision newRevision) {
                                    Map<String, Object> properties = newRevision.getProperties();
                                    properties.put("pregunta_medicamento", preguntaString);
                                    properties.put("recuperacion_fecha", fecha);
                                    return true;
                                }
                            });
                            Toast.makeText(getApplicationContext(), preguntaString, Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();
                        } catch (CouchbaseLiteException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Hay un problema al guardar los datos de mediacmento!", Toast.LENGTH_LONG).show();

                        }
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


    public void reporteRegistros(){

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        datePickerDialog = new DatePickerDialog(ContentPanelActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
               // Toast.makeText(getApplicationContext(), dayOfMonth + monthOfYear + year, Toast.LENGTH_LONG).show();

                int m = monthOfYear + 1;

                String dia = String.valueOf(dayOfMonth);
                String mes = String.valueOf(m);
                String anio = String.valueOf(year);

               //  Toast.makeText(getApplicationContext(), anio + "/" + mes + "/" + dia, Toast.LENGTH_LONG).show();

                 Intent i = new Intent(ContentPanelActivity.this, ReporteActivity.class);
                 i.putExtra("dia",dia);
                 i.putExtra("mes",mes);
                 i.putExtra("anio",anio);
                 startActivity(i);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
            }


    public void buscaDesocupadoGato(final String idDocumentStringS){


      /*  final SharedPreferences prefs = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        circleButton1.setVisibility(prefs.getBoolean("boton1", false) ? View.VISIBLE : View.GONE);
        tv1.setVisibility(prefs.getBoolean("tv1", false)  ? View.VISIBLE : View.GONE);

        circleButton2.setVisibility(prefs.getBoolean("boton2", false) ? View.VISIBLE : View.GONE);
        tv2.setVisibility(prefs.getBoolean("tv2", false)  ? View.VISIBLE : View.GONE);

        circleButton3.setVisibility(prefs.getBoolean("boton3", false) ? View.VISIBLE : View.GONE);
        tv3.setVisibility(prefs.getBoolean("tv3", false)  ? View.VISIBLE : View.GONE);

        circleButton4.setVisibility(prefs.getBoolean("boton4", false) ? View.VISIBLE : View.GONE);
        tv4.setVisibility(prefs.getBoolean("tv4", false) ? View.VISIBLE : View.GONE);

        circleButton5.setVisibility(prefs.getBoolean("boton5", false) ? View.VISIBLE : View.GONE);
        tv5.setVisibility(prefs.getBoolean("tv5", false) ? View.VISIBLE : View.GONE);

        circleButton6.setVisibility(prefs.getBoolean("boton6", false) ? View.VISIBLE : View.GONE);
        tv6.setVisibility(prefs.getBoolean("tv6", false) ? View.VISIBLE : View.GONE);

        circleButton7.setVisibility(prefs.getBoolean("boton7", false) ? View.VISIBLE : View.GONE);
        tv7.setVisibility(prefs.getBoolean("tv7", false) ? View.VISIBLE : View.GONE);

        circleButton8.setVisibility(prefs.getBoolean("boton8", false) ? View.VISIBLE : View.GONE);
        tv8.setVisibility(prefs.getBoolean("tv8", false) ? View.VISIBLE : View.GONE);

        circleButton9.setVisibility(prefs.getBoolean("boton9", false) ? View.VISIBLE : View.GONE);
        tv9.setVisibility(prefs.getBoolean("tv9", false) ? View.VISIBLE : View.GONE);

        circleButton10.setVisibility(prefs.getBoolean("boton10", false) ? View.VISIBLE : View.GONE);
        tv10.setVisibility(prefs.getBoolean("tv10", false) ? View.VISIBLE : View.GONE);

        circleButton11.setVisibility(prefs.getBoolean("boton11", false) ? View.VISIBLE : View.GONE);
        tv11.setVisibility(prefs.getBoolean("tv11", false) ? View.VISIBLE : View.GONE);

        circleButton12.setVisibility(prefs.getBoolean("boton12", false) ? View.VISIBLE : View.GONE);
        tv12.setVisibility(prefs.getBoolean("tv12", false) ? View.VISIBLE : View.GONE);

        circleButton13.setVisibility(prefs.getBoolean("boton13", false) ? View.VISIBLE : View.GONE);
        tv13.setVisibility(prefs.getBoolean("tv13", false) ? View.VISIBLE : View.GONE);

        circleButton14.setVisibility(prefs.getBoolean("boton14", false) ? View.VISIBLE : View.GONE);
        tv14.setVisibility(prefs.getBoolean("tv14", false) ? View.VISIBLE : View.GONE);

        circleButton15.setVisibility(prefs.getBoolean("boton15", false) ? View.VISIBLE : View.GONE);
        tv15.setVisibility(prefs.getBoolean("tv15", false) ? View.VISIBLE : View.GONE);

        circleButton16.setVisibility(prefs.getBoolean("boton16", false) ? View.VISIBLE : View.GONE);
        tv16.setVisibility(prefs.getBoolean("tv16", false) ? View.VISIBLE : View.GONE);

        circleButton17.setVisibility(prefs.getBoolean("boton17", false) ? View.VISIBLE : View.GONE);
        tv17.setVisibility(prefs.getBoolean("tv17", false) ? View.VISIBLE : View.GONE);

        circleButton18.setVisibility(prefs.getBoolean("boton18", false) ? View.VISIBLE : View.GONE);
        tv18.setVisibility(prefs.getBoolean("tv18", false) ? View.VISIBLE : View.GONE);

        circleButton19.setVisibility(prefs.getBoolean("boton19", false) ? View.VISIBLE : View.GONE);
        tv19.setVisibility(prefs.getBoolean("tv19", false) ? View.VISIBLE : View.GONE);

        circleButton20.setVisibility(prefs.getBoolean("boton20", false) ? View.VISIBLE : View.GONE);
        tv20.setVisibility(prefs.getBoolean("tv20", false) ? View.VISIBLE : View.GONE);


        circleButton21.setVisibility(prefs.getBoolean("boton21", false) ? View.VISIBLE : View.GONE);
        tv21.setVisibility(prefs.getBoolean("tv21", false) ? View.VISIBLE : View.GONE);

        circleButton22.setVisibility(prefs.getBoolean("boton22", false) ? View.VISIBLE : View.GONE);
        tv22.setVisibility(prefs.getBoolean("tv22", false) ? View.VISIBLE : View.GONE);

        circleButton23.setVisibility(prefs.getBoolean("boton23", false) ? View.VISIBLE : View.GONE);
        tv23.setVisibility(prefs.getBoolean("tv23", false) ? View.VISIBLE : View.GONE);

        circleButton24.setVisibility(prefs.getBoolean("boton24", false) ? View.VISIBLE : View.GONE);
        tv24.setVisibility(prefs.getBoolean("tv24", false) ? View.VISIBLE : View.GONE);

        circleButton25.setVisibility(prefs.getBoolean("boton25", false) ? View.VISIBLE : View.GONE);
        tv25.setVisibility(prefs.getBoolean("tv25", false) ? View.VISIBLE : View.GONE);

        circleButton26.setVisibility(prefs.getBoolean("boton26", false) ? View.VISIBLE : View.GONE);
        tv26.setVisibility(prefs.getBoolean("tv26", false) ? View.VISIBLE : View.GONE);

        circleButton27.setVisibility(prefs.getBoolean("boton27", false) ? View.VISIBLE : View.GONE);
        tv27.setVisibility(prefs.getBoolean("tv27", false) ? View.VISIBLE : View.GONE);

        circleButton28.setVisibility(prefs.getBoolean("boton28", false) ? View.VISIBLE : View.GONE);
        tv28.setVisibility(prefs.getBoolean("tv28", false) ? View.VISIBLE : View.GONE);

        circleButton29.setVisibility(prefs.getBoolean("boton29", false) ? View.VISIBLE : View.GONE);
        tv29.setVisibility(prefs.getBoolean("tv29", false) ? View.VISIBLE : View.GONE);

        circleButton30.setVisibility(prefs.getBoolean("boton30", false) ? View.VISIBLE : View.GONE);
        tv30.setVisibility(prefs.getBoolean("tv30", false) ? View.VISIBLE : View.GONE);

        circleButton31.setVisibility(prefs.getBoolean("boton31", false) ? View.VISIBLE : View.GONE);
        tv31.setVisibility(prefs.getBoolean("tv31", false) ? View.VISIBLE : View.GONE);

        circleButton32.setVisibility(prefs.getBoolean("boton32", false) ? View.VISIBLE : View.GONE);
        tv32.setVisibility(prefs.getBoolean("tv32", false) ? View.VISIBLE : View.GONE);

        circleButton33.setVisibility(prefs.getBoolean("boton33", false) ? View.VISIBLE : View.GONE);
        tv33.setVisibility(prefs.getBoolean("tv33", false) ? View.VISIBLE : View.GONE);

        circleButton34.setVisibility(prefs.getBoolean("boton34", false) ? View.VISIBLE : View.GONE);
        tv34.setVisibility(prefs.getBoolean("tv34", false) ? View.VISIBLE : View.GONE);

        circleButton35.setVisibility(prefs.getBoolean("boton35", false) ? View.VISIBLE : View.GONE);
        tv35.setVisibility(prefs.getBoolean("tv35", false) ? View.VISIBLE : View.GONE);

        circleButton36.setVisibility(prefs.getBoolean("boton36", false) ? View.VISIBLE : View.GONE);
        tv36.setVisibility(prefs.getBoolean("tv36", false) ? View.VISIBLE : View.GONE);*/



        if(circleButton1.getVisibility() == View.GONE && tv1.getVisibility() == View.GONE){

            circleButton1.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText(idDocumentStringS);
            circleButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton1.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton1.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton1.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton1.setVisibility(View.GONE);
                                tv1.setVisibility(View.GONE);

                            }
                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });




        } else if(circleButton2.getVisibility() == View.GONE && tv2.getVisibility() == View.GONE){


            circleButton2.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv2.setText(idDocumentStringS);
            circleButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton2.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton2.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton2.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton2.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });



        }else if (circleButton3.getVisibility() == View.GONE && tv3.getVisibility() == View.GONE){

            circleButton3.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv3.setText(idDocumentStringS);
            circleButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton3.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton3.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton3.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton3.setVisibility(View.GONE);
                                tv3.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        }else if(circleButton4.getVisibility() == View.GONE && tv4.getVisibility() == View.GONE){

            circleButton4.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText(idDocumentStringS);
            circleButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton4.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton4.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton4.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton4.setVisibility(View.GONE);
                                tv4.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        }else if(circleButton5.getVisibility() == View.GONE && tv5.getVisibility() == View.GONE){

            circleButton5.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            tv5.setText(idDocumentStringS);
            circleButton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton5.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton5.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton5.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton5.setVisibility(View.GONE);
                                tv5.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        }else if(circleButton6.getVisibility() == View.GONE && tv6.getVisibility() == View.GONE){

            circleButton6.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
            tv6.setText(idDocumentStringS);
            circleButton6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton6.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton6.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton6.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton6.setVisibility(View.GONE);
                                tv6.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });

        }else if(circleButton7.getVisibility() == View.GONE && tv7.getVisibility() == View.GONE){

            circleButton7.setVisibility(View.VISIBLE);
            tv7.setVisibility(View.VISIBLE);
            tv7.setText(idDocumentStringS);
            circleButton7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton7.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton7.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton7.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton7.setVisibility(View.GONE);
                                tv7.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        }else if(circleButton8.getVisibility() == View.GONE && tv8.getVisibility() == View.GONE){


            circleButton8.setVisibility(View.VISIBLE);
            tv8.setVisibility(View.VISIBLE);
            tv8.setText(idDocumentStringS);
            circleButton8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton8.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton8.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton8.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton8.setVisibility(View.GONE);
                                tv8.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });

        }else if(circleButton9.getVisibility() == View.GONE && tv9.getVisibility() == View.GONE){

            circleButton9.setVisibility(View.VISIBLE);
            tv9.setVisibility(View.VISIBLE);
            tv9.setText(idDocumentStringS);
            circleButton9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton9.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton9.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton9.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton9.setVisibility(View.GONE);
                                tv9.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        }else if(circleButton10.getVisibility() == View.GONE && tv10.getVisibility() == View.GONE){

            circleButton10.setVisibility(View.VISIBLE);
            tv10.setVisibility(View.VISIBLE);
            tv10.setText(idDocumentStringS);
            circleButton10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton10.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton10.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton10.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton10.setVisibility(View.GONE);
                                tv10.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });



        }else if(circleButton11.getVisibility() == View.GONE && tv11.getVisibility() == View.GONE){

    //        prefs.edit().putBoolean("boton11", true).apply();
    //        prefs.edit().putBoolean("tv11", true).apply();

            circleButton11.setVisibility(View.VISIBLE);
            tv11.setVisibility(View.VISIBLE);
            tv11.setText(idDocumentStringS);
            circleButton11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton11.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton11.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton11.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton11.setVisibility(View.GONE);
                                tv11.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton12.getVisibility() == View.GONE && tv12.getVisibility() == View.GONE){

    //        prefs.edit().putBoolean("boton12", true).apply();
    //        prefs.edit().putBoolean("tv12", true).apply();

            circleButton12.setVisibility(View.VISIBLE);
            tv12.setVisibility(View.VISIBLE);
            tv12.setText(idDocumentStringS);
            circleButton12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton12.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton12.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton12.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton12.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton13.getVisibility() == View.GONE && tv13.getVisibility() == View.GONE){

        //   prefs.edit().putBoolean("boton13", true).apply();
        //    prefs.edit().putBoolean("tv13", true).apply();

            circleButton13.setVisibility(View.VISIBLE);
            tv13.setVisibility(View.VISIBLE);
            tv13.setText(idDocumentStringS);
            circleButton13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton13.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton13.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton13.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton13.setVisibility(View.GONE);
                                tv13.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton14.getVisibility() == View.GONE && tv14.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton14", true).apply();
        //    prefs.edit().putBoolean("tv14", true).apply();

            circleButton14.setVisibility(View.VISIBLE);
            tv14.setVisibility(View.VISIBLE);
            tv14.setText(idDocumentStringS);
            circleButton14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton14.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton14.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton14.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton14.setVisibility(View.GONE);
                                tv14.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton15.getVisibility() == View.GONE && tv15.getVisibility() == View.GONE){

       //    prefs.edit().putBoolean("boton15", true).apply();
       //     prefs.edit().putBoolean("tv15", true).apply();

            circleButton15.setVisibility(View.VISIBLE);
            tv15.setVisibility(View.VISIBLE);
            tv15.setText(idDocumentStringS);
            circleButton15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton15.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton15.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton15.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton15.setVisibility(View.GONE);
                                tv15.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton16.getVisibility() == View.GONE && tv16.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton16", true).apply();
        //    prefs.edit().putBoolean("tv16", true).apply();

            circleButton16.setVisibility(View.VISIBLE);
            tv16.setVisibility(View.VISIBLE);
            tv16.setText(idDocumentStringS);
            circleButton16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton16.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton16.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton16.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton16.setVisibility(View.GONE);
                                tv16.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton17.getVisibility() == View.GONE && tv17.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton17", true).apply();
        //    prefs.edit().putBoolean("tv17", true).apply();

            circleButton17.setVisibility(View.VISIBLE);
            tv17.setVisibility(View.VISIBLE);
            tv17.setText(idDocumentStringS);
            circleButton17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton17.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton17.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton17.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton17.setVisibility(View.GONE);
                                tv17.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton18.getVisibility() == View.GONE && tv18.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton18", true).apply();
        //    prefs.edit().putBoolean("tv18", true).apply();

            circleButton18.setVisibility(View.VISIBLE);
            tv18.setVisibility(View.VISIBLE);
            tv18.setText(idDocumentStringS);
            circleButton18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton18.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton18.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton18.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton18.setVisibility(View.GONE);
                                tv18.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton19.getVisibility() == View.GONE && tv19.getVisibility() == View.GONE){

         //   prefs.edit().putBoolean("boton19", true).apply();
         //   prefs.edit().putBoolean("tv19", true).apply();

            circleButton19.setVisibility(View.VISIBLE);
            tv19.setVisibility(View.VISIBLE);
            tv19.setText(idDocumentStringS);
            circleButton19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton19.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton19.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton19.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton19.setVisibility(View.GONE);
                                tv19.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton20.getVisibility() == View.GONE && tv20.getVisibility() == View.GONE){

         //   prefs.edit().putBoolean("boton20", true).apply();
         //   prefs.edit().putBoolean("tv20", true).apply();

            circleButton20.setVisibility(View.VISIBLE);
            tv20.setVisibility(View.VISIBLE);
            tv20.setText(idDocumentStringS);
            circleButton20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton20.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton20.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton20.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton20.setVisibility(View.GONE);
                                tv20.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton21.getVisibility() == View.GONE && tv21.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton21", true).apply();
        //    prefs.edit().putBoolean("tv21", true).apply();

            circleButton21.setVisibility(View.VISIBLE);
            tv21.setVisibility(View.VISIBLE);
            tv21.setText(idDocumentStringS);
            circleButton21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton21.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton21.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton21.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton21.setVisibility(View.GONE);
                                tv21.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton22.getVisibility() == View.GONE && tv22.getVisibility() == View.GONE){

         //   prefs.edit().putBoolean("boton22", true).apply();
         //   prefs.edit().putBoolean("tv22", true).apply();

            circleButton22.setVisibility(View.VISIBLE);
            tv22.setVisibility(View.VISIBLE);
            tv22.setText(idDocumentStringS);
            circleButton22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton22.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton22.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton22.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton22.setVisibility(View.GONE);
                                tv22.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton23.getVisibility() == View.GONE && tv23.getVisibility() == View.GONE){

         //   prefs.edit().putBoolean("boton23", true).apply();
        //    prefs.edit().putBoolean("tv23", true).apply();

            circleButton23.setVisibility(View.VISIBLE);
            tv23.setVisibility(View.VISIBLE);
            tv23.setText(idDocumentStringS);
            circleButton23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton23.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton23.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton23.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton23.setVisibility(View.GONE);
                                tv23.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton24.getVisibility() == View.GONE && tv24.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton24", true).apply();
        //    prefs.edit().putBoolean("tv24", true).apply();

            circleButton24.setVisibility(View.VISIBLE);
            tv24.setVisibility(View.VISIBLE);
            tv24.setText(idDocumentStringS);
            circleButton24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton24.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton24.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton24.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton24.setVisibility(View.GONE);
                                tv24.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton25.getVisibility() == View.GONE && tv25.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton25", true).apply();
        //    prefs.edit().putBoolean("tv25", true).apply();

            circleButton25.setVisibility(View.VISIBLE);
            tv25.setVisibility(View.VISIBLE);
            tv25.setText(idDocumentStringS);
            circleButton25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton25.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton25.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton25.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton25.setVisibility(View.GONE);
                                tv25.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton26.getVisibility() == View.GONE && tv26.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton26", true).apply();
        //    prefs.edit().putBoolean("tv26", true).apply();

            circleButton26.setVisibility(View.VISIBLE);
            tv26.setVisibility(View.VISIBLE);
            tv26.setText(idDocumentStringS);
            circleButton26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton26.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton26.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton26.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton26.setVisibility(View.GONE);
                                tv26.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton27.getVisibility() == View.GONE && tv27.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton27", true).apply();
        //    prefs.edit().putBoolean("tv27", true).apply();

            circleButton27.setVisibility(View.VISIBLE);
            tv27.setVisibility(View.VISIBLE);
            tv27.setText(idDocumentStringS);
            circleButton27.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton27.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton27.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton27.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton27.setVisibility(View.GONE);
                                tv27.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton28.getVisibility() == View.GONE && tv28.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton28", true).apply();
        //    prefs.edit().putBoolean("tv28", true).apply();

            circleButton28.setVisibility(View.VISIBLE);
            tv28.setVisibility(View.VISIBLE);
            tv28.setText(idDocumentStringS);
            circleButton28.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton28.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton28.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton28.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton28.setVisibility(View.GONE);
                                tv28.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton29.getVisibility() == View.GONE && tv29.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton29", true).apply();
        //    prefs.edit().putBoolean("tv29", true).apply();

            circleButton29.setVisibility(View.VISIBLE);
            tv29.setVisibility(View.VISIBLE);
            tv29.setText(idDocumentStringS);
            circleButton29.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton29.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton29.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton29.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton29.setVisibility(View.GONE);
                                tv29.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton30.getVisibility() == View.GONE && tv30.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton30", true).apply();
        //    prefs.edit().putBoolean("tv30", true).apply();

            circleButton30.setVisibility(View.VISIBLE);
            tv30.setVisibility(View.VISIBLE);
            tv30.setText(idDocumentStringS);
            circleButton30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton30.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton30.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton30.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton30.setVisibility(View.GONE);
                                tv30.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton31.getVisibility() == View.GONE && tv31.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton31", true).apply();
        //    prefs.edit().putBoolean("tv31", true).apply();

            circleButton31.setVisibility(View.VISIBLE);
            tv31.setVisibility(View.VISIBLE);
            tv31.setText(idDocumentStringS);
            circleButton31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton31.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton31.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton31.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton31.setVisibility(View.GONE);
                                tv31.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton32.getVisibility() == View.GONE && tv32.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton32", true).apply();
        //    prefs.edit().putBoolean("tv32", true).apply();

            circleButton32.setVisibility(View.VISIBLE);
            tv32.setVisibility(View.VISIBLE);
            tv32.setText(idDocumentStringS);
            circleButton32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton32.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton32.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton32.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton32.setVisibility(View.GONE);
                                tv32.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton33.getVisibility() == View.GONE && tv33.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton33", true).apply();
        //    prefs.edit().putBoolean("tv33", true).apply();

            circleButton33.setVisibility(View.VISIBLE);
            tv33.setVisibility(View.VISIBLE);
            tv33.setText(idDocumentStringS);
            circleButton33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton33.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton33.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton33.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton33.setVisibility(View.GONE);
                                tv33.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton34.getVisibility() == View.GONE && tv34.getVisibility() == View.GONE){

         //   prefs.edit().putBoolean("boton34", true).apply();
         //   prefs.edit().putBoolean("tv34", true).apply();

            circleButton34.setVisibility(View.VISIBLE);
            tv34.setVisibility(View.VISIBLE);
            tv34.setText(idDocumentStringS);
            circleButton34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton34.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton34.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton34.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton34.setVisibility(View.GONE);
                                tv34.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton35.getVisibility() == View.GONE && tv35.getVisibility() == View.GONE){

        //    prefs.edit().putBoolean("boton35", true).apply();
        //    prefs.edit().putBoolean("tv35", true).apply();

            circleButton35.setVisibility(View.VISIBLE);
            tv35.setVisibility(View.VISIBLE);
            tv35.setText(idDocumentStringS);
            circleButton35.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton35.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton35.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton35.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton35.setVisibility(View.GONE);
                                tv35.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton36.getVisibility() == View.GONE && tv36.getVisibility() == View.GONE){

         //   prefs.edit().putBoolean("boton36", true).apply();
         //   prefs.edit().putBoolean("tv36", true).apply();

            circleButton36.setVisibility(View.VISIBLE);
            tv36.setVisibility(View.VISIBLE);
            tv36.setText(idDocumentStringS);
            circleButton36.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton36.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton36.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton36.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton36.setVisibility(View.GONE);
                                tv36.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }


    }

    private void buscaDesocupadoPerro(final String idDocumentStringS){

         if(circleButton37.getVisibility() == View.GONE && tv37.getVisibility() == View.GONE){
            circleButton37.setVisibility(View.VISIBLE);
            tv37.setVisibility(View.VISIBLE);
            tv37.setText(idDocumentStringS);
            circleButton37.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton37.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton37.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton37.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton37.setVisibility(View.GONE);
                                tv37.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);

                }
            });
        }else if(circleButton38.getVisibility() == View.GONE && tv38.getVisibility() == View.GONE){
            circleButton38.setVisibility(View.VISIBLE);
            tv38.setVisibility(View.VISIBLE);
            tv38.setText(idDocumentStringS);
            circleButton38.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton38.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton38.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton38.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton38.setVisibility(View.GONE);
                                tv38.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton39.getVisibility() == View.GONE && tv39.getVisibility() == View.GONE){
            circleButton39.setVisibility(View.VISIBLE);
            tv39.setVisibility(View.VISIBLE);
            tv39.setText(idDocumentStringS);
            circleButton39.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton39.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton39.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton39.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton39.setVisibility(View.GONE);
                                tv39.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton40.getVisibility() == View.GONE && tv40.getVisibility() == View.GONE){
            circleButton40.setVisibility(View.VISIBLE);
            tv40.setVisibility(View.VISIBLE);
            tv40.setText(idDocumentStringS);
            circleButton40.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton40.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton40.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton40.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton40.setVisibility(View.GONE);
                                tv40.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton41.getVisibility() == View.GONE && tv41.getVisibility() == View.GONE){
            circleButton41.setVisibility(View.VISIBLE);
            tv41.setVisibility(View.VISIBLE);
            tv41.setText(idDocumentStringS);
            circleButton41.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton41.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton41.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton41.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton41.setVisibility(View.GONE);
                                tv41.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton42.getVisibility() == View.GONE && tv42.getVisibility() == View.GONE){
            circleButton42.setVisibility(View.VISIBLE);
            tv42.setVisibility(View.VISIBLE);
            tv42.setText(idDocumentStringS);
            circleButton42.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton42.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton42.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton42.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton42.setVisibility(View.GONE);
                                tv42.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton43.getVisibility() == View.GONE && tv43.getVisibility() == View.GONE){
            circleButton43.setVisibility(View.VISIBLE);
            tv43.setVisibility(View.VISIBLE);
            tv43.setText(idDocumentStringS);
            circleButton43.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton43.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton43.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton43.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton43.setVisibility(View.GONE);
                                tv43.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton44.getVisibility() == View.GONE && tv44.getVisibility() == View.GONE){
            circleButton44.setVisibility(View.VISIBLE);
            tv44.setVisibility(View.VISIBLE);
            tv44.setText(idDocumentStringS);
            circleButton44.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton44.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton44.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton44.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton44.setVisibility(View.GONE);
                                tv44.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton45.getVisibility() == View.GONE && tv45.getVisibility() == View.GONE){
            circleButton45.setVisibility(View.VISIBLE);
            tv45.setVisibility(View.VISIBLE);
            tv45.setText(idDocumentStringS);
            circleButton45.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton45.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton45.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton45.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton45.setVisibility(View.GONE);
                                tv45.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton46.getVisibility() == View.GONE && tv46.getVisibility() == View.GONE){
            circleButton46.setVisibility(View.VISIBLE);
            tv46.setVisibility(View.VISIBLE);
            tv46.setText(idDocumentStringS);
            circleButton46.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton46.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton46.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton46.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton46.setVisibility(View.GONE);
                                tv46.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton47.getVisibility() == View.GONE && tv47.getVisibility() == View.GONE){
            circleButton47.setVisibility(View.VISIBLE);
            tv47.setVisibility(View.VISIBLE);
            tv47.setText(idDocumentStringS);
            circleButton47.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton47.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton47.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton47.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton47.setVisibility(View.GONE);
                                tv47.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton48.getVisibility() == View.GONE && tv48.getVisibility() == View.GONE){
            circleButton48.setVisibility(View.VISIBLE);
            tv48.setVisibility(View.VISIBLE);
            tv48.setText(idDocumentStringS);
            circleButton48.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton48.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton48.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton48.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton48.setVisibility(View.GONE);
                                tv48.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton49.getVisibility() == View.GONE && tv49.getVisibility() == View.GONE){
            circleButton49.setVisibility(View.VISIBLE);
            tv49.setVisibility(View.VISIBLE);
            tv49.setText(idDocumentStringS);
            circleButton49.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton49.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton49.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton49.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton49.setVisibility(View.GONE);
                                tv49.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton50.getVisibility() == View.GONE && tv50.getVisibility() == View.GONE){
            circleButton50.setVisibility(View.VISIBLE);
            tv50.setVisibility(View.VISIBLE);
            tv50.setText(idDocumentStringS);
            circleButton50.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton50.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton50.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton50.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton50.setVisibility(View.GONE);
                                tv50.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton51.getVisibility() == View.GONE && tv51.getVisibility() == View.GONE){
            circleButton51.setVisibility(View.VISIBLE);
            tv51.setVisibility(View.VISIBLE);
            tv51.setText(idDocumentStringS);
            circleButton51.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton51.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton51.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton51.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton51.setVisibility(View.GONE);
                                tv51.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton52.getVisibility() == View.GONE && tv52.getVisibility() == View.GONE){
            circleButton52.setVisibility(View.VISIBLE);
            tv52.setVisibility(View.VISIBLE);
            tv52.setText(idDocumentStringS);
            circleButton52.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton52.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton52.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton52.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton52.setVisibility(View.GONE);
                                tv52.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton53.getVisibility() == View.GONE && tv53.getVisibility() == View.GONE){
            circleButton53.setVisibility(View.VISIBLE);
            tv53.setVisibility(View.VISIBLE);
            tv53.setText(idDocumentStringS);
            circleButton53.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton53.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton53.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton53.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton53.setVisibility(View.GONE);
                                tv53.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton54.getVisibility() == View.GONE && tv54.getVisibility() == View.GONE){
            circleButton54.setVisibility(View.VISIBLE);
            tv54.setVisibility(View.VISIBLE);
            tv54.setText(idDocumentStringS);
            circleButton54.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton54.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton54.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton54.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton54.setVisibility(View.GONE);
                                tv54.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton55.getVisibility() == View.GONE && tv55.getVisibility() == View.GONE){
            circleButton55.setVisibility(View.VISIBLE);
            tv55.setVisibility(View.VISIBLE);
            tv55.setText(idDocumentStringS);
            circleButton55.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton55.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton55.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton55.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton55.setVisibility(View.GONE);
                                tv55.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton56.getVisibility() == View.GONE && tv56.getVisibility() == View.GONE){
            circleButton56.setVisibility(View.VISIBLE);
            tv56.setVisibility(View.VISIBLE);
            tv56.setText(idDocumentStringS);
            circleButton56.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton56.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton56.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton56.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton56.setVisibility(View.GONE);
                                tv56.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton57.getVisibility() == View.GONE && tv57.getVisibility() == View.GONE){
            circleButton57.setVisibility(View.VISIBLE);
            tv57.setVisibility(View.VISIBLE);
            tv57.setText(idDocumentStringS);
            circleButton57.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton57.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton57.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton57.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton57.setVisibility(View.GONE);
                                tv57.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton58.getVisibility() == View.GONE && tv58.getVisibility() == View.GONE){
            circleButton58.setVisibility(View.VISIBLE);
            tv58.setVisibility(View.VISIBLE);
            tv58.setText(idDocumentStringS);
            circleButton58.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton58.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton58.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton58.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton58.setVisibility(View.GONE);
                                tv58.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton59.getVisibility() == View.GONE && tv59.getVisibility() == View.GONE){
            circleButton59.setVisibility(View.VISIBLE);
            tv59.setVisibility(View.VISIBLE);
            tv59.setText(idDocumentStringS);
            circleButton59.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton59.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton59.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton59.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton59.setVisibility(View.GONE);
                                tv59.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton60.getVisibility() == View.GONE && tv60.getVisibility() == View.GONE){
            circleButton60.setVisibility(View.VISIBLE);
            tv60.setVisibility(View.VISIBLE);
            tv60.setText(idDocumentStringS);
            circleButton60.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton60.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton60.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton60.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton60.setVisibility(View.GONE);
                                tv60.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton61.getVisibility() == View.GONE && tv61.getVisibility() == View.GONE){
            circleButton61.setVisibility(View.VISIBLE);
            tv61.setVisibility(View.VISIBLE);
            tv61.setText(idDocumentStringS);
            circleButton61.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton61.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton61.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton61.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton61.setVisibility(View.GONE);
                                tv61.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton62.getVisibility() == View.GONE && tv62.getVisibility() == View.GONE){
            circleButton62.setVisibility(View.VISIBLE);
            tv62.setVisibility(View.VISIBLE);
            tv62.setText(idDocumentStringS);
            circleButton62.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton62.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton62.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton62.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton62.setVisibility(View.GONE);
                                tv62.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton63.getVisibility() == View.GONE && tv63.getVisibility() == View.GONE){
            circleButton63.setVisibility(View.VISIBLE);
            tv63.setVisibility(View.VISIBLE);
            tv63.setText(idDocumentStringS);
            circleButton63.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton63.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton63.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton63.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton63.setVisibility(View.GONE);
                                tv63.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton64.getVisibility() == View.GONE && tv64.getVisibility() == View.GONE){
            circleButton64.setVisibility(View.VISIBLE);
            tv64.setVisibility(View.VISIBLE);
            tv64.setText(idDocumentStringS);
            circleButton64.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton64.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton64.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton64.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton64.setVisibility(View.GONE);
                                tv64.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton65.getVisibility() == View.GONE && tv65.getVisibility() == View.GONE){
            circleButton65.setVisibility(View.VISIBLE);
            tv65.setVisibility(View.VISIBLE);
            tv65.setText(idDocumentStringS);
            circleButton65.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton65.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton65.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton65.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton65.setVisibility(View.GONE);
                                tv65.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton66.getVisibility() == View.GONE && tv66.getVisibility() == View.GONE){
            circleButton66.setVisibility(View.VISIBLE);
            tv66.setVisibility(View.VISIBLE);
            tv66.setText(idDocumentStringS);
            circleButton66.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton66.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton66.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton66.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton66.setVisibility(View.GONE);
                                tv66.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton67.getVisibility() == View.GONE && tv67.getVisibility() == View.GONE){
            circleButton67.setVisibility(View.VISIBLE);
            tv67.setVisibility(View.VISIBLE);
            tv67.setText(idDocumentStringS);
            circleButton67.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton67.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton67.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton67.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton67.setVisibility(View.GONE);
                                tv67.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton68.getVisibility() == View.GONE && tv68.getVisibility() == View.GONE){
            circleButton68.setVisibility(View.VISIBLE);
            tv68.setVisibility(View.VISIBLE);
            tv68.setText(idDocumentStringS);
            circleButton68.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton68.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton68.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton68.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton68.setVisibility(View.GONE);
                                tv68.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton69.getVisibility() == View.GONE && tv69.getVisibility() == View.GONE){
            circleButton69.setVisibility(View.VISIBLE);
            tv69.setVisibility(View.VISIBLE);
            tv69.setText(idDocumentStringS);
            circleButton69.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton69.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton69.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton69.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton69.setVisibility(View.GONE);
                                tv69.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton70.getVisibility() == View.GONE && tv70.getVisibility() == View.GONE){
            circleButton70.setVisibility(View.VISIBLE);
            tv70.setVisibility(View.VISIBLE);
            tv70.setText(idDocumentStringS);
            circleButton70.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton70.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton70.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton70.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton70.setVisibility(View.GONE);
                                tv70.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton71.getVisibility() == View.GONE && tv71.getVisibility() == View.GONE){
            circleButton71.setVisibility(View.VISIBLE);
            tv71.setVisibility(View.VISIBLE);
            tv71.setText(idDocumentStringS);
            circleButton71.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton71.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton71.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton71.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton71.setVisibility(View.GONE);
                                tv71.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton72.getVisibility() == View.GONE && tv72.getVisibility() == View.GONE){
            circleButton72.setVisibility(View.VISIBLE);
            tv72.setVisibility(View.VISIBLE);
            tv72.setText(idDocumentStringS);
            circleButton72.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton72.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton72.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton72.setColor(Color.BLUE);
                            } else if (which == 3) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton72.setVisibility(View.GONE);
                                tv72.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
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

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        final Font BLACK_BOLD1 = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
        final Font BLACK_BOLD2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        final Font BLACK_BOLD3 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

        final Chunk A = new Chunk("ABOGADOS DE LOS ANIMALES, A.C.", BLACK_BOLD1);
        final Chunk B = new Chunk("CAMPAÑA DE ESTERILIZACIÓN", BLACK_BOLD2);
        final Chunk C = new Chunk("SOLICITUD Y RESPONSIVA" + "Ciudad: Querétaro " +  "Fecha: ", BLACK_BOLD2);
        final Chunk D = new Chunk("ABOGADOS DE LOS ANIMALES, A.C.", BLACK_BOLD3);
        final Chunk E = new Chunk("El curso post operatorio es responsabilidad del propietario de la mascota esterilizada y deberá ser supervisada por un médico veterinario competente, siguiendo las recomendaciones del médico veterinario que efectúe la cirugía. \n \n", BLACK_BOLD3);
        final Chunk F = new Chunk("REQUISITOS: \n ", BLACK_BOLD3);

     /*   String idDocumentStringS = getIntent().getExtras().getString("numero_folio");
        String nombre = getIntent().getExtras().getString("nombre");
        String numero = getIntent().getExtras().getString("numero");
        String mascota = getIntent().getExtras().getString("mascota");
        String tmascota = getIntent().getExtras().getString("tmascota");
        String telefono = getIntent().getExtras().getString("telefono");
        String direccion = getIntent().getExtras().getString("direccion");
        String edad = getIntent().getExtras().getString("edad");
        String raza = getIntent().getExtras().getString("raza");*/


        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;

            //create document object
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            //output file path

            try {
                String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ Cartas Responsivas";

                File dir = new File(file);
                if(!dir.exists())
                    dir.mkdirs();
                Log.d("PDFCreator", "PDF Path:" + file);

                File nfile = new File(dir, /*idDocumentS + */".pdf");
                FileOutputStream fOut = new FileOutputStream(nfile);
                PdfWriter.getInstance(doc, fOut);
                //create pdf writer instance
                // PdfWriter.getInstance(doc, new FileOutputStream(file));
                //open the document for writing
                doc.open();

                Drawable d = getResources().getDrawable(R.drawable.logo);

                BitmapDrawable bitDw = ((BitmapDrawable) d);

                Bitmap bmp = bitDw.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                Image image = Image.getInstance(stream.toByteArray());

                image.scaleAbsolute(50f, 50f);

                doc.add(image);

                doc.add(new Paragraph(A));
                doc.add(new Paragraph(B));

                LineSeparator ls = new LineSeparator();
                doc.add(new Chunk(ls));

                //add paragraph to the document
                doc.add(new Paragraph(C + dateFormat.format(date)));

                doc.add(new Paragraph(D + "no se hace responsable por muerte, pérdida o algún otro contratiempo que ocurra durante la cirugía o post operatorio. \n"));

                doc.add(new Paragraph(E));

                doc.add(new Paragraph(F));

                doc.add(new Paragraph("1. La persona que presente la mascota para su esterilización debe de ser mayor de edad. \n " +
                        "2. La mascota deberá tener por lo menos 2 meses de edad o 1 kg de peso. \n" +
                        "3. La mascota deberá estar sin comer ni beber agua por lo menos 12 horas antes de la cirugía. De lo contrario \n" +
                        "4. La mascota NO deberá estar lactando, gestando o en celo. \n" +
                        "5. La mascota deberá estar sana y o más limpa posible.\n" +
                        "6. Acepta que se marque la oreja de su mascota con un pequeño tatuaje. \n" +
                        "7. RESCATADOS: " + "Más de 25 días de haberlos rescatados. \n " +
                        "8. No se le debe de haber aplicado ninguna vacuna 2 semanas antes de la cirugía ni deben de aplicarse vacunas \n" +
                        "mínimo en los siguientes 20 días después de la cirugía."));

                doc.add(new Paragraph("DATOS DEL PROPIETARIO: \n"));

                doc.add(new Paragraph("Nombre: " /* + nombreString*/));
                doc.add(new Paragraph("Dirección: " /* + direccionString*/));
                doc.add(new Paragraph("Cel / Tel: " /*+ telefonoString*/));

                doc.add(new Paragraph("DATOS DE LA MASCOTA: \n"));

                doc.add(new Paragraph("Nombre: " + mascota));
                doc.add(new Paragraph("Edad: " /*+ edadString*/));
                doc.add(new Paragraph("Raza: " /*+ razaString*/));
                doc.add(new Paragraph("Color:___________________ " + "¿Tiene vacuna antirrabica?    Si       No   "));
                doc.add(new Paragraph("Tamaño:     Ch     Med     Gde    Gigante     Peso:__________________(nosotros l@s pesamos)"));
                doc.add(new Paragraph("OBSERVACIONES(condición médica, alergías, algunos problemas con el uso de anestesia)_________" +
                        "___________________________________________________________________________________________________________ \n\n\n"));


                doc.add(new Paragraph("Acepto las condiciones y confirmo que recibí el volante de cuidados post operatorio: \n"));


                doc.add(new Paragraph("Nombre y Firma:"));

                //close the document
                doc.close();

                Toast.makeText(getApplicationContext(), "PDF creado correctamente", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void buscaDesocupadoAlternativa(final String idDocumentStringS){

        final SharedPreferences prefs = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        circleButton1.setVisibility(prefs.getBoolean("boton1", false) ? View.VISIBLE : View.GONE);
        tv1.setVisibility(prefs.getBoolean("tv1", false)  ? View.VISIBLE : View.GONE);

        circleButton2.setVisibility(prefs.getBoolean("boton2", false) ? View.VISIBLE : View.GONE);
        tv2.setVisibility(prefs.getBoolean("tv2", false)  ? View.VISIBLE : View.GONE);

        circleButton3.setVisibility(prefs.getBoolean("boton3", false) ? View.VISIBLE : View.GONE);
        tv3.setVisibility(prefs.getBoolean("tv3", false)  ? View.VISIBLE : View.GONE);

        circleButton4.setVisibility(prefs.getBoolean("boton4", false) ? View.VISIBLE : View.GONE);
        tv4.setVisibility(prefs.getBoolean("tv4", false) ? View.VISIBLE : View.GONE);

        circleButton5.setVisibility(prefs.getBoolean("boton5", false) ? View.VISIBLE : View.GONE);
        tv5.setVisibility(prefs.getBoolean("tv5", false) ? View.VISIBLE : View.GONE);

        circleButton6.setVisibility(prefs.getBoolean("boton6", false) ? View.VISIBLE : View.GONE);
        tv6.setVisibility(prefs.getBoolean("tv6", false) ? View.VISIBLE : View.GONE);

        circleButton7.setVisibility(prefs.getBoolean("boton7", false) ? View.VISIBLE : View.GONE);
        tv7.setVisibility(prefs.getBoolean("tv7", false) ? View.VISIBLE : View.GONE);

        circleButton8.setVisibility(prefs.getBoolean("boton8", false) ? View.VISIBLE : View.GONE);
        tv8.setVisibility(prefs.getBoolean("tv8", false) ? View.VISIBLE : View.GONE);

        circleButton9.setVisibility(prefs.getBoolean("boton9", false) ? View.VISIBLE : View.GONE);
        tv9.setVisibility(prefs.getBoolean("tv9", false) ? View.VISIBLE : View.GONE);

        circleButton10.setVisibility(prefs.getBoolean("boton10", false) ? View.VISIBLE : View.GONE);
        tv10.setVisibility(prefs.getBoolean("tv10", false) ? View.VISIBLE : View.GONE);

        int botonvacio = 0;

        switch (botonvacio){
            case 0:
               if(circleButton1.getVisibility() == View.GONE && tv1.getVisibility() == View.GONE){
                circleButton1.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv1.setText(idDocumentStringS);

                   prefs.edit().putBoolean("boton1", true).apply();
                   prefs.edit().putBoolean("tv1", true).apply();
               }
                botonvacio++;
               break;
            case 1:
                if(circleButton2.getVisibility() == View.GONE && tv2.getVisibility() == View.GONE){
                circleButton2.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv2.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton2", true).apply();
                    prefs.edit().putBoolean("tv2", true).apply();
                }
                botonvacio++;
                break;
            case 2:
                if(circleButton3.getVisibility() == View.GONE && tv3.getVisibility() == View.GONE){
                    circleButton3.setVisibility(View.VISIBLE);
                    circleButton3.setVisibility(View.VISIBLE);
                    tv3.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton3", true).apply();
                    prefs.edit().putBoolean("tv3", true).apply();
                }
                botonvacio++;
                break;
            case 3:
                if(circleButton4.getVisibility() == View.GONE && tv4.getVisibility() == View.GONE){
                  circleButton4.setVisibility(View.VISIBLE);
                  tv4.setVisibility(View.VISIBLE);
                  tv4.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton4", true).apply();
                    prefs.edit().putBoolean("tv4", true).apply();
                }
                botonvacio++;
                break;
            case 4:
                if(circleButton5.getVisibility() == View.GONE && tv5.getVisibility() == View.GONE){
                    circleButton5.setVisibility(View.VISIBLE);
                    tv5.setVisibility(View.VISIBLE);
                    tv5.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton5", true).apply();
                    prefs.edit().putBoolean("tv5", true).apply();
                }
                botonvacio++;
                break;
            case 5:
               if(circleButton6.getVisibility() == View.GONE && tv6.getVisibility() == View.GONE){
                   circleButton6.setVisibility(View.VISIBLE);
                   tv6.setVisibility(View.VISIBLE);
                   tv6.setText(idDocumentStringS);

                   prefs.edit().putBoolean("boton6", true).apply();
                   prefs.edit().putBoolean("tv6", true).apply();
               }
               botonvacio++;
               break;
            case 6:
                if(circleButton7.getVisibility() == View.GONE && tv7.getVisibility() == View.GONE){
                    circleButton7.setVisibility(View.VISIBLE);
                    tv7.setVisibility(View.VISIBLE);
                    tv7.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton7", true).apply();
                    prefs.edit().putBoolean("tv7", true).apply();
                }
               botonvacio++;
                break;
            case 7:
                if(circleButton8.getVisibility() == View.GONE && tv8.getVisibility() == View.GONE){
                 circleButton8.setVisibility(View.VISIBLE);
                 tv8.setVisibility(View.VISIBLE);
                 tv8.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton8", true).apply();
                    prefs.edit().putBoolean("tv8", true).apply();
                }
                botonvacio++;
                break;

            case 8:
                if(circleButton9.getVisibility() == View.GONE && tv9.getVisibility() == View.GONE){
                    circleButton9.setVisibility(View.VISIBLE);
                    circleButton9.setVisibility(View.VISIBLE);
                    tv9.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton9", true).apply();
                    prefs.edit().putBoolean("tv9", true).apply();
                }
                botonvacio++;
                break;

            case 9:
                if(circleButton10.getVisibility() == View.GONE && tv10.getVisibility() == View.GONE){
                   circleButton10.setVisibility(View.VISIBLE);
                   circleButton10.setVisibility(View.VISIBLE);
                   tv10.setText(idDocumentStringS);

                    prefs.edit().putBoolean("boton10", true).apply();
                    prefs.edit().putBoolean("tv10", true).apply();
                }
                botonvacio++;
                break;
        }

    }


    public void buttonOnClick(View view, final String idDocumentStringS) {

        final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Selecciona Módulo");
        builder.setItems(modulos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    anestesiaDialog(idDocumentStringS);
                    circleButton72.setColor(Color.GREEN);
                } else if (which == 1) {
                    cirugiaDialog(idDocumentStringS);
                    circleButton72.setColor(Color.YELLOW);
                } else if (which == 2) {
                    recuperacionDialog(idDocumentStringS);
                    circleButton72.setColor(Color.BLUE);
                } else if (which == 3) {
                    //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        }
    }



