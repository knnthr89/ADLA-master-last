package com.example.dev.saludmock.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by kennethrizo on 22/11/17.
 */

public class ContentPanelActivity extends AppCompatActivity {

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

    Spinner smodulos, smedida, smedio, stmascota;


    Locale l = new Locale("es", "MX");
   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),l);
    String fecha = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "T" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":"  + cal.get(Calendar.SECOND);


    private int color = 0;

   int idDocument;


  //  String [] modulos = {"Anestesia", "Cirugía", "Recuperación"};
    String [] medida = {"Chico", "Mediano", "Grande", "Gigante"};
    String [] medio = {"¿Cómo te enteraste de la campaña?","Facebook","Volante","Vi una lona en la campaña", "Ya he ido a otra campaña de Abogados de los Animales", "Recomendación de otra persona", "Otro"};
    String [] tmascota = {"Detalles de las Mascotas", "Perro", "Perra", "Gato", "Gata"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_panel);

        bottomNavigationView = findViewById(R.id.navigation);

        ButterKnife.bind(this);

        String user_name = getIntent().getStringExtra("username");


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

        smedida.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, medida));

       SharedPreferences preferences = getSharedPreferences("values", Context.MODE_PRIVATE);
        idDocument = preferences.getInt("idDocument", 0);

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
                /*    Intent i = new Intent(ContentPanelActivity.this, RegistroActivity.class);
                    startActivity(i);*/
                    registroActivity();
                    return true;
                case R.id.navigation_dashboard:
               recuperacionDialog();
                    return true;
                case R.id.navigation_notifications:
                    reporteRegistros();
                  //  Intent tintent = new Intent(ContentPanelActivity.this, ReporteActivity.class);
                  //  startActivity(tintent);
                    return true;
            }
            return false;
        }
    };




    public void registroActivity(){

       /* final ArrayAdapter<String> adp = new ArrayAdapter<String>(ContentPanelActivity.this, android.R.layout.simple_spinner_item, medio);
        final Spinner sp = new Spinner(ContentPanelActivity.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);

        final ArrayAdapter<String> adp1 = new ArrayAdapter<String>(ContentPanelActivity.this, android.R.layout.simple_spinner_item, medida);
        final Spinner sp1 = new Spinner(ContentPanelActivity.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp1.setAdapter(adp1);*/

        final AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Registra nueva mascota");

        Context context = builder.getContext();
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nombre = new EditText(this);
        nombre.setHint("Nombre completo del dueño");
        layout.addView(nombre);
        nombre.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText direccion = new EditText(this);
        direccion.setHint("Dirección");
        layout.addView(direccion);
        direccion.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText telefono = new EditText(this);
        telefono.setHint("Teléfono o celular de contacto");
        layout.addView(telefono);
        telefono.setInputType(InputType.TYPE_CLASS_PHONE);

        final EditText email = new EditText(this);
        email.setHint("Correo electrónico");
        layout.addView(email);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        final EditText mascota = new EditText(this);
        mascota.setHint("Nombre de la mascota");
        layout.addView(mascota);
        mascota.setInputType(InputType.TYPE_CLASS_TEXT);

        //falta spinner de tipo de mascota

        final EditText raza = new EditText(this);
        raza.setHint("Raza (o raza aproximada)");
        layout.addView(raza);
        raza.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText edad = new EditText(this);
        edad.setHint("Edad");
        layout.addView(edad);
        edad.setInputType(InputType.TYPE_CLASS_NUMBER);

        //falta spinner de medio social

        final EditText comentarios = new EditText(this);
        comentarios.setHint("Comentarios");
        layout.addView(comentarios);
        comentarios.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText tratamiento = new EditText(this);
        tratamiento.setHint("Si ha recibido tratamientos médicos o se ha sometido a alguna cirugía por favor indique la fecha y el tipo de tratamiento o cirugía");
        layout.addView(tratamiento);
        tratamiento.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText rescatado = new EditText(this);
        rescatado.setHint("Si es rescatado, ¿Cuánto tiempo tiene de rescatado?");
        layout.addView(rescatado);
        rescatado.setInputType(InputType.TYPE_CLASS_TEXT);

      final EditText vacuna = new EditText(this);
        vacuna.setHint("Fecha de su última vacuna");
        layout.addView(vacuna);
        vacuna.setInputType(InputType.TYPE_CLASS_TEXT);

      final EditText desparacitacion = new EditText(this);
        desparacitacion.setHint("Fecha de su última desparasitación");
        layout.addView(desparacitacion);
        desparacitacion.setInputType(InputType.TYPE_CLASS_TEXT);

          final EditText celo = new EditText(this);
        celo.setHint("Fecha de su última desparasitación");
        layout.addView(celo);
        celo.setInputType(InputType.TYPE_CLASS_TEXT);

      /*  final EditText lactar = new EditText(this);
        lactar.setHint("Si ha tenido cachorros recientemente cuanto tiempo tiene sin lactar");
        layout.addView(lactar);
        lactar.setInputType(InputType.TYPE_CLASS_TEXT);*/

        //falta spinner de tamaño de mascota

        builder.setView(layout);
        // builder.setView(sp);
        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                            String nombreString = nombre.getText().toString();
                            String direccionString = direccion.getText().toString();
                            String telefonoString = telefono.getText().toString();
                            String emailString = email.getText().toString();
                            String mascotaString = mascota.getText().toString();
                            String razaString = raza.getText().toString();
                            String edadString = edad.getText().toString();
                            String comentarioString = comentarios.getText().toString();
                            String tratamientoString = tratamiento.getText().toString();
                            String rescatadoString = rescatado.getText().toString();
                            String vacunaString = vacuna.getText().toString();
                            String desparacitacionString = desparacitacion.getText().toString();
                            String celoString = celo.getText().toString();
                          //  String lactarString = lactar.getText().toString();

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
                        properties.put("nombreMascota", mascotaString);
                        properties.put("raza", razaString);
                        properties.put("edad", edadString);
                        properties.put("comentarioRegistro", comentarioString);
                        properties.put("tratamiento", tratamientoString);
                        properties.put("rescatado", rescatadoString);
                        properties.put("vacuna", vacunaString);
                        properties.put("desparacitacion", desparacitacionString);
                        properties.put("celo", celoString);
                     //   properties.put("lactar", lactarString);
                        properties.put("creat_at", fecha);

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

                        Toast.makeText(getApplicationContext(), "El id de registro es " + idDocumentS + " con fecha " + fecha, Toast.LENGTH_SHORT).show();
                        buscaDesocupado(idDocumentS);
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

    public void anestesiaDialog(){

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
                        properties.put("fecha", fecha);

                        //Create a new document
                        Document document = database.getDocument("tapes");

                        //Save the document to the database
                        try{
                            document.putProperties(properties);
                        }catch (CouchbaseLiteException e){
                            e.printStackTrace();
                        }


                        Toast.makeText(getApplicationContext(), fecha, Toast.LENGTH_LONG).show();
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

    public void cirugiaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Cirugía");
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText cirujano = new EditText(this);
        cirujano.setHint("¿Quién operó?");
        layout.addView(cirujano);
        cirujano.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText comentario = new EditText(this);
        comentario.setHint("Comentario");
        layout.addView(comentario);
        comentario.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(layout);
        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    String ncirujano = cirujano.getText().toString();
                    String ncomentario = comentario.getText().toString();

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
                        properties.put("fecha", fecha);

                        //create a new document
                        Document document = database.createDocument();

                        //Save the document tothe database
                        try{
                            document.putProperties(properties);
                        }catch (CouchbaseLiteException e){
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Se ha registrado correctamente los datos de la cirugía", Toast.LENGTH_SHORT).show();

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

    public void recuperacionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Recuperación");

        String comentario;

        LinearLayout params = new LinearLayout(ContentPanelActivity.this);
        params.setOrientation(LinearLayout.VERTICAL);


        /*final TextView textAntibiotico = new TextView(ContentPanelActivity.this);
        textAntibiotico.setText("Antibiótico");
        layoutText.addView(textAntibiotico);*/

        final Switch sw1 = new Switch(ContentPanelActivity.this);
        params.addView(sw1);

       /* final TextView textTatuaje = new TextView(ContentPanelActivity.this);
        textTatuaje.setText("Tatuaje");
        layoutText.addView(textTatuaje);*/

        final Switch sw2 = new Switch(ContentPanelActivity.this);
        params.addView(sw2);

        final Switch sw3 = new Switch(ContentPanelActivity.this);
        params.addView(sw3);

        final Switch sw4 = new Switch(ContentPanelActivity.this);
        params.addView(sw4);

        final Switch sw5 = new Switch(ContentPanelActivity.this);
        params.addView(sw5);

        final Switch sw6 = new Switch(ContentPanelActivity.this);
        params.addView(sw6);

        final Switch sw7 = new Switch(ContentPanelActivity.this);
         params.addView(sw7);

        final EditText et2 = new EditText(ContentPanelActivity.this);
        et2.setVisibility(View.GONE);
        params.addView(et2);

        /*  if(sw7.isChecked()){
            et2.setVisibility(View.VISIBLE);
        }*/


        builder.setView(params);
       // builder.setView(layoutText);

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

                        if(sw1.isChecked()){
                       antibioticoString = "si";
                      Toast.makeText(getApplicationContext(), "Antibiótico" + antibioticoString, Toast.LENGTH_SHORT).show();

                      }else{
                       antibioticoString = "no";
                       Toast.makeText(getApplicationContext(), "Antibiótico" + antibioticoString, Toast.LENGTH_SHORT).show();
                      }

                   if(sw2.isChecked()){
                       tatuajeString = "si";
                       Toast.makeText(getApplicationContext(), "Tatuaje" + tatuajeString, Toast.LENGTH_SHORT).show();
                   }else{
                       tatuajeString = "no";
                       Toast.makeText(getApplicationContext(), "Tatuaje" + tatuajeString, Toast.LENGTH_SHORT).show();
                       }

                   if(sw3.isChecked()){
                       analgesicoString = "si";
                            Toast.makeText(getApplicationContext(), "Analgésico" + analgesicoString, Toast.LENGTH_SHORT).show();
                        }else{
                       analgesicoString = "no";
                            Toast.makeText(getApplicationContext(), "Analgésico" + analgesicoString, Toast.LENGTH_SHORT).show();
                        }

                   if(sw4.isChecked()){
                       sueroString = "si";
                            Toast.makeText(getApplicationContext(), "Suero" + sueroString, Toast.LENGTH_SHORT).show();
                        }else{
                       sueroString = "no";
                            Toast.makeText(getApplicationContext(), "Suero" + sueroString, Toast.LENGTH_SHORT).show();
                        }

                        if(sw5.isChecked()){
                       cicatrizanteString = "si";
                            Toast.makeText(getApplicationContext(), "Cicatrizante" + cicatrizanteString, Toast.LENGTH_SHORT).show();
                        }else{
                            cicatrizanteString = "no";
                            Toast.makeText(getApplicationContext(), "Cicatrizante" + cicatrizanteString, Toast.LENGTH_SHORT).show();
                        }

                        if(sw6.isChecked()){
                            otroString = "si";
                            Toast.makeText(getApplicationContext(), "Otro" + otroString, Toast.LENGTH_SHORT).show();
                        }else{
                            otroString = "no";
                            Toast.makeText(getApplicationContext(), "Otro" + otroString, Toast.LENGTH_SHORT).show();
                        }

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

                        Document document = database.createDocument();

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
                                //    properties.put("comentario_recuperacion", Comentario);
                                    return true;
                                }
                            });
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

    public void buscaDesocupado(String idDocumentStringS){

        if(circleButton1.getVisibility() == View.GONE && tv1.getVisibility() == View.GONE){
            circleButton1.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText(idDocumentStringS);
            circleButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton1.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton1.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton1.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton2.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton2.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton2.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton3.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton3.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton3.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton4.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton4.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton4.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton5.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton5.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton5.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton6.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton6.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton6.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton7.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton7.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton7.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton8.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton8.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton8.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton9.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton9.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton9.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton10.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton10.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton10.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton11.getVisibility() == View.GONE && tv11.getVisibility() == View.GONE){
            circleButton11.setVisibility(View.VISIBLE);
            tv11.setVisibility(View.VISIBLE);
            tv11.setText(idDocumentStringS);
            circleButton11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton11.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton11.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton11.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton12.getVisibility() == View.GONE && tv12.getVisibility() == View.GONE){
            circleButton12.setVisibility(View.VISIBLE);
            tv12.setVisibility(View.VISIBLE);
            tv12.setText(idDocumentStringS);
            circleButton12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton12.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton12.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton12.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton13.getVisibility() == View.GONE && tv13.getVisibility() == View.GONE){
            circleButton13.setVisibility(View.VISIBLE);
            tv13.setVisibility(View.VISIBLE);
            tv13.setText(idDocumentStringS);
            circleButton13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton13.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton13.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton13.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton14.getVisibility() == View.GONE && tv14.getVisibility() == View.GONE){
            circleButton14.setVisibility(View.VISIBLE);
            tv14.setVisibility(View.VISIBLE);
            tv14.setText(idDocumentStringS);
            circleButton14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton14.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton14.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton14.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton15.getVisibility() == View.GONE && tv15.getVisibility() == View.GONE){
            circleButton15.setVisibility(View.VISIBLE);
            tv15.setVisibility(View.VISIBLE);
            tv15.setText(idDocumentStringS);
            circleButton15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton15.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton15.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton15.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton16.getVisibility() == View.GONE && tv16.getVisibility() == View.GONE){
            circleButton16.setVisibility(View.VISIBLE);
            tv16.setVisibility(View.VISIBLE);
            tv16.setText(idDocumentStringS);
            circleButton16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton16.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton16.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton16.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton17.getVisibility() == View.GONE && tv17.getVisibility() == View.GONE){
            circleButton17.setVisibility(View.VISIBLE);
            tv17.setVisibility(View.VISIBLE);
            tv17.setText(idDocumentStringS);
            circleButton17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton17.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton17.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton17.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton18.getVisibility() == View.GONE && tv18.getVisibility() == View.GONE){
            circleButton18.setVisibility(View.VISIBLE);
            tv18.setVisibility(View.VISIBLE);
            tv18.setText(idDocumentStringS);
            circleButton18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton18.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton18.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton18.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton19.getVisibility() == View.GONE && tv19.getVisibility() == View.GONE){
            circleButton19.setVisibility(View.VISIBLE);
            tv19.setVisibility(View.VISIBLE);
            tv19.setText(idDocumentStringS);
            circleButton19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton19.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton19.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton19.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton20.getVisibility() == View.GONE && tv20.getVisibility() == View.GONE){
            circleButton20.setVisibility(View.VISIBLE);
            tv20.setVisibility(View.VISIBLE);
            tv20.setText(idDocumentStringS);
            circleButton20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton20.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton20.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton20.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton21.getVisibility() == View.GONE && tv21.getVisibility() == View.GONE){
            circleButton21.setVisibility(View.VISIBLE);
            tv21.setVisibility(View.VISIBLE);
            tv21.setText(idDocumentStringS);
            circleButton21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton21.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton21.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton21.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton22.getVisibility() == View.GONE && tv22.getVisibility() == View.GONE){
            circleButton22.setVisibility(View.VISIBLE);
            tv22.setVisibility(View.VISIBLE);
            tv22.setText(idDocumentStringS);
            circleButton22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton22.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton22.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton22.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton23.getVisibility() == View.GONE && tv23.getVisibility() == View.GONE){
            circleButton23.setVisibility(View.VISIBLE);
            tv23.setVisibility(View.VISIBLE);
            tv23.setText(idDocumentStringS);
            circleButton23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton23.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton23.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton23.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton24.getVisibility() == View.GONE && tv24.getVisibility() == View.GONE){
            circleButton24.setVisibility(View.VISIBLE);
            tv24.setVisibility(View.VISIBLE);
            tv24.setText(idDocumentStringS);
            circleButton24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton24.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton24.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton24.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton25.getVisibility() == View.GONE && tv25.getVisibility() == View.GONE){
            circleButton25.setVisibility(View.VISIBLE);
            tv25.setVisibility(View.VISIBLE);
            tv25.setText(idDocumentStringS);
            circleButton25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton25.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton25.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton25.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton26.getVisibility() == View.GONE && tv26.getVisibility() == View.GONE){
            circleButton26.setVisibility(View.VISIBLE);
            tv26.setVisibility(View.VISIBLE);
            tv26.setText(idDocumentStringS);
            circleButton26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton26.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton26.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton26.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton27.getVisibility() == View.GONE && tv27.getVisibility() == View.GONE){
            circleButton27.setVisibility(View.VISIBLE);
            tv27.setVisibility(View.VISIBLE);
            tv27.setText(idDocumentStringS);
            circleButton27.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton27.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton27.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton27.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton28.getVisibility() == View.GONE && tv28.getVisibility() == View.GONE){
            circleButton28.setVisibility(View.VISIBLE);
            tv28.setVisibility(View.VISIBLE);
            tv28.setText(idDocumentStringS);
            circleButton28.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton28.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton28.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton28.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton29.getVisibility() == View.GONE && tv29.getVisibility() == View.GONE){
            circleButton29.setVisibility(View.VISIBLE);
            tv29.setVisibility(View.VISIBLE);
            tv29.setText(idDocumentStringS);
            circleButton29.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton29.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton29.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton29.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton30.getVisibility() == View.GONE && tv30.getVisibility() == View.GONE){
            circleButton30.setVisibility(View.VISIBLE);
            tv30.setVisibility(View.VISIBLE);
            tv30.setText(idDocumentStringS);
            circleButton30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton30.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton30.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton30.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton31.getVisibility() == View.GONE && tv31.getVisibility() == View.GONE){
            circleButton31.setVisibility(View.VISIBLE);
            tv31.setVisibility(View.VISIBLE);
            tv31.setText(idDocumentStringS);
            circleButton31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton31.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton31.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton31.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton32.getVisibility() == View.GONE && tv32.getVisibility() == View.GONE){
            circleButton32.setVisibility(View.VISIBLE);
            tv32.setVisibility(View.VISIBLE);
            tv32.setText(idDocumentStringS);
            circleButton32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton32.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton32.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton32.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton33.getVisibility() == View.GONE && tv33.getVisibility() == View.GONE){
            circleButton33.setVisibility(View.VISIBLE);
            tv33.setVisibility(View.VISIBLE);
            tv33.setText(idDocumentStringS);
            circleButton33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton33.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton33.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton33.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton34.getVisibility() == View.GONE && tv34.getVisibility() == View.GONE){
            circleButton34.setVisibility(View.VISIBLE);
            tv34.setVisibility(View.VISIBLE);
            tv34.setText(idDocumentStringS);
            circleButton34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton34.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton34.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton34.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton35.getVisibility() == View.GONE && tv35.getVisibility() == View.GONE){
            circleButton35.setVisibility(View.VISIBLE);
            tv35.setVisibility(View.VISIBLE);
            tv35.setText(idDocumentStringS);
            circleButton35.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton35.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton35.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton35.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton36.getVisibility() == View.GONE && tv36.getVisibility() == View.GONE){
            circleButton36.setVisibility(View.VISIBLE);
            tv36.setVisibility(View.VISIBLE);
            tv36.setText(idDocumentStringS);
            circleButton36.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton36.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton36.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton36.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }else if(circleButton37.getVisibility() == View.GONE && tv37.getVisibility() == View.GONE){
            circleButton37.setVisibility(View.VISIBLE);
            tv37.setVisibility(View.VISIBLE);
            tv37.setText(idDocumentStringS);
            circleButton37.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton37.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton37.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton37.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton38.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton38.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton38.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton39.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton39.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton39.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton40.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton40.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton40.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton41.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton41.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton41.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton42.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton42.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton42.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton43.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton43.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton43.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton44.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton44.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton44.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton45.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton45.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton45.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton46.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton46.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton46.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton47.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton47.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton47.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton48.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton48.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton48.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton49.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton49.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton49.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton50.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton50.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton50.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton51.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton51.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton51.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton52.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton52.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton52.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton53.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton53.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton53.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton54.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton54.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton54.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton55.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton55.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton55.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton56.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton56.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton56.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton57.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton57.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton57.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton58.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton58.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton58.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton59.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton59.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton59.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton60.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton60.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton60.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton61.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton61.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton61.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton62.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton62.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton62.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton63.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton63.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton63.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton64.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton64.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton64.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton65.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton65.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton65.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton66.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton66.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton66.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton67.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton67.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton67.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton68.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton68.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton68.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton69.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton69.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton69.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton70.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton70.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton70.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton71.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton71.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton71.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
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
                    final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                anestesiaDialog();
                                circleButton72.setColor(Color.GREEN);
                            } else if (which == 1) {
                                cirugiaDialog();
                                circleButton72.setColor(Color.YELLOW);
                            } else if (which == 2) {
                                recuperacionDialog();
                                circleButton72.setColor(Color.BLUE);
                            } else if (which == 3) {
                                //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        }
    }

}


