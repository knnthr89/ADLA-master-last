package com.example.dev.saludmock.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.listener.Credentials;
import com.couchbase.lite.replicator.Replication;
import com.example.dev.saludmock.R;
import com.example.dev.saludmock.adapters.DataAdapter;
import com.example.dev.saludmock.adapters.NewDoctorAdapter;
import com.example.dev.saludmock.adapters.NewRegistersArrayAdapter;
import com.example.dev.saludmock.config.Application;
import com.example.dev.saludmock.config.Configuration;
import com.example.dev.saludmock.config.DataPet;
import com.example.dev.saludmock.config.DiscoveryListener;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import okhttp3.internal.DiskLruCache;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;
import static com.couchbase.lite.Database.TAG;

/**
 * Created by kennethrizo on 22/11/17.
 */

public class ContentPanelActivity extends ListActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnKeyListener {

    private Replication pullReplication;
    private Replication pushReplication;

    WifiP2pManager.Channel mChannel;
    WifiP2pManager mManager;

    String estado = "desactivado";
    String dString;

    protected NewDoctorAdapter newDoctorAdapter;
    ListView itemListView;

    String button1_value, tv1_value;

    private Object date;

    // SharedPreferences preferences = getSharedPreferences("prefs_file_name", MODE_PRIVATE);

    private static final String CIRCLEBUTTON_COLOR = "CIRCLEBUTTON_COLOR";

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;

    BottomNavigationView bottomNavigationView;

    ImageButton imageButton;

    DatePickerDialog datePickerDialog;

    EditText et;

    Object saved;
    QueryEnumerator result;

    String mascota = null;
    //  String tmascota = "perro";
    //  String t2mascota = "gato";

    //botones de perros y gatos
    @BindView(R.id.btn1)
    CircleButton circleButton1;
    @BindView(R.id.btn2)
    CircleButton circleButton2;
    @BindView(R.id.btn3)
    CircleButton circleButton3;
    @BindView(R.id.btn4)
    CircleButton circleButton4;
    @BindView(R.id.btn5)
    CircleButton circleButton5;
    @BindView(R.id.btn6)
    CircleButton circleButton6;
    @BindView(R.id.btn7)
    CircleButton circleButton7;
    @BindView(R.id.btn8)
    CircleButton circleButton8;
    @BindView(R.id.btn9)
    CircleButton circleButton9;
    @BindView(R.id.btn10)
    CircleButton circleButton10;
    @BindView(R.id.btn11)
    CircleButton circleButton11;
    @BindView(R.id.btn12)
    CircleButton circleButton12;
    @BindView(R.id.btn13)
    CircleButton circleButton13;
    @BindView(R.id.btn14)
    CircleButton circleButton14;
    @BindView(R.id.btn15)
    CircleButton circleButton15;
    @BindView(R.id.btn16)
    CircleButton circleButton16;
    @BindView(R.id.btn17)
    CircleButton circleButton17;
    @BindView(R.id.btn18)
    CircleButton circleButton18;
    @BindView(R.id.btn19)
    CircleButton circleButton19;
    @BindView(R.id.btn20)
    CircleButton circleButton20;
    @BindView(R.id.btn21)
    CircleButton circleButton21;
    @BindView(R.id.btn22)
    CircleButton circleButton22;
    @BindView(R.id.btn23)
    CircleButton circleButton23;
    @BindView(R.id.btn24)
    CircleButton circleButton24;
    @BindView(R.id.btn25)
    CircleButton circleButton25;
    @BindView(R.id.btn26)
    CircleButton circleButton26;
    @BindView(R.id.btn27)
    CircleButton circleButton27;
    @BindView(R.id.btn28)
    CircleButton circleButton28;
    @BindView(R.id.btn29)
    CircleButton circleButton29;
    @BindView(R.id.btn30)
    CircleButton circleButton30;
    @BindView(R.id.btn31)
    CircleButton circleButton31;
    @BindView(R.id.btn32)
    CircleButton circleButton32;
    @BindView(R.id.btn33)
    CircleButton circleButton33;
    @BindView(R.id.btn34)
    CircleButton circleButton34;
    @BindView(R.id.btn35)
    CircleButton circleButton35;
    @BindView(R.id.btn36)
    CircleButton circleButton36;
    @BindView(R.id.btn37)
    CircleButton circleButton37;
    @BindView(R.id.btn38)
    CircleButton circleButton38;
    @BindView(R.id.btn39)
    CircleButton circleButton39;
    @BindView(R.id.btn40)
    CircleButton circleButton40;
    @BindView(R.id.btn41)
    CircleButton circleButton41;
    @BindView(R.id.btn42)
    CircleButton circleButton42;
    @BindView(R.id.btn43)
    CircleButton circleButton43;
    @BindView(R.id.btn44)
    CircleButton circleButton44;
    @BindView(R.id.btn45)
    CircleButton circleButton45;
    @BindView(R.id.btn46)
    CircleButton circleButton46;
    @BindView(R.id.btn47)
    CircleButton circleButton47;
    @BindView(R.id.btn48)
    CircleButton circleButton48;
    @BindView(R.id.btn49)
    CircleButton circleButton49;
    @BindView(R.id.btn50)
    CircleButton circleButton50;
    @BindView(R.id.btn51)
    CircleButton circleButton51;
    @BindView(R.id.btn52)
    CircleButton circleButton52;
    @BindView(R.id.btn53)
    CircleButton circleButton53;
    @BindView(R.id.btn54)
    CircleButton circleButton54;
    @BindView(R.id.btn55)
    CircleButton circleButton55;
    @BindView(R.id.btn56)
    CircleButton circleButton56;
    @BindView(R.id.btn57)
    CircleButton circleButton57;
    @BindView(R.id.btn58)
    CircleButton circleButton58;
    @BindView(R.id.btn59)
    CircleButton circleButton59;
    @BindView(R.id.btn60)
    CircleButton circleButton60;
    @BindView(R.id.btn61)
    CircleButton circleButton61;
    @BindView(R.id.btn62)
    CircleButton circleButton62;
    @BindView(R.id.btn63)
    CircleButton circleButton63;
    @BindView(R.id.btn64)
    CircleButton circleButton64;
    @BindView(R.id.btn65)
    CircleButton circleButton65;
    @BindView(R.id.btn66)
    CircleButton circleButton66;
    @BindView(R.id.btn67)
    CircleButton circleButton67;
    @BindView(R.id.btn68)
    CircleButton circleButton68;
    @BindView(R.id.btn69)
    CircleButton circleButton69;
    @BindView(R.id.btn70)
    CircleButton circleButton70;
    @BindView(R.id.btn71)
    CircleButton circleButton71;
    @BindView(R.id.btn72)
    CircleButton circleButton72;

    //folio
    @BindView(R.id.message)
    TextView tv1;
    @BindView(R.id.message2)
    TextView tv2;
    @BindView(R.id.message3)
    TextView tv3;
    @BindView(R.id.message4)
    TextView tv4;
    @BindView(R.id.message5)
    TextView tv5;
    @BindView(R.id.message6)
    TextView tv6;
    @BindView(R.id.message7)
    TextView tv7;
    @BindView(R.id.message8)
    TextView tv8;
    @BindView(R.id.message9)
    TextView tv9;
    @BindView(R.id.message10)
    TextView tv10;
    @BindView(R.id.message11)
    TextView tv11;
    @BindView(R.id.message12)
    TextView tv12;
    @BindView(R.id.message13)
    TextView tv13;
    @BindView(R.id.message14)
    TextView tv14;
    @BindView(R.id.message15)
    TextView tv15;
    @BindView(R.id.message16)
    TextView tv16;
    @BindView(R.id.message17)
    TextView tv17;
    @BindView(R.id.message18)
    TextView tv18;
    @BindView(R.id.message19)
    TextView tv19;
    @BindView(R.id.message20)
    TextView tv20;
    @BindView(R.id.message21)
    TextView tv21;
    @BindView(R.id.message22)
    TextView tv22;
    @BindView(R.id.message23)
    TextView tv23;
    @BindView(R.id.message24)
    TextView tv24;
    @BindView(R.id.message25)
    TextView tv25;
    @BindView(R.id.message26)
    TextView tv26;
    @BindView(R.id.message27)
    TextView tv27;
    @BindView(R.id.message28)
    TextView tv28;
    @BindView(R.id.message29)
    TextView tv29;
    @BindView(R.id.message30)
    TextView tv30;
    @BindView(R.id.message31)
    TextView tv31;
    @BindView(R.id.message32)
    TextView tv32;
    @BindView(R.id.message33)
    TextView tv33;
    @BindView(R.id.message34)
    TextView tv34;
    @BindView(R.id.message35)
    TextView tv35;
    @BindView(R.id.message36)
    TextView tv36;
    @BindView(R.id.message37)
    TextView tv37;
    @BindView(R.id.message38)
    TextView tv38;
    @BindView(R.id.message39)
    TextView tv39;
    @BindView(R.id.message40)
    TextView tv40;
    @BindView(R.id.message41)
    TextView tv41;
    @BindView(R.id.message42)
    TextView tv42;
    @BindView(R.id.message43)
    TextView tv43;
    @BindView(R.id.message44)
    TextView tv44;
    @BindView(R.id.message45)
    TextView tv45;
    @BindView(R.id.message46)
    TextView tv46;
    @BindView(R.id.message47)
    TextView tv47;
    @BindView(R.id.message48)
    TextView tv48;
    @BindView(R.id.message49)
    TextView tv49;
    @BindView(R.id.message50)
    TextView tv50;
    @BindView(R.id.message51)
    TextView tv51;
    @BindView(R.id.message52)
    TextView tv52;
    @BindView(R.id.message53)
    TextView tv53;
    @BindView(R.id.message54)
    TextView tv54;
    @BindView(R.id.message55)
    TextView tv55;
    @BindView(R.id.message56)
    TextView tv56;
    @BindView(R.id.message57)
    TextView tv57;
    @BindView(R.id.message58)
    TextView tv58;
    @BindView(R.id.message59)
    TextView tv59;
    @BindView(R.id.message60)
    TextView tv60;
    @BindView(R.id.message61)
    TextView tv61;
    @BindView(R.id.message62)
    TextView tv62;
    @BindView(R.id.message63)
    TextView tv63;
    @BindView(R.id.message64)
    TextView tv64;
    @BindView(R.id.message65)
    TextView tv65;
    @BindView(R.id.message66)
    TextView tv66;
    @BindView(R.id.message67)
    TextView tv67;
    @BindView(R.id.message68)
    TextView tv68;
    @BindView(R.id.message69)
    TextView tv69;
    @BindView(R.id.message70)
    TextView tv70;
    @BindView(R.id.message71)
    TextView tv71;
    @BindView(R.id.message72)
    TextView tv72;

    Locale l = new Locale("es", "MX");

    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"), l);

    String fecha = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE)/* + "T" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":"  + cal.get(Calendar.SECOND)*/;

    int anio = cal.get(Calendar.YEAR);

    int dia = cal.get(Calendar.DAY_OF_MONTH);

    int mes = cal.get(Calendar.MONTH) + 1;

    String anioString = Integer.toString(anio);

    int idDocument;

    private String PREFS_KEY = "mispreferencias";

    private AutoCompleteTextView nombre;
    private AutoCompleteTextView direccion;
    private AutoCompleteTextView telefono;
    private AutoCompleteTextView nmascota;
    private AutoCompleteTextView raza;
    private AutoCompleteTextView edad;
    private AutoCompleteTextView tratamiento;
    private AutoCompleteTextView rescatado;
    private AutoCompleteTextView vacuna;
    private AutoCompleteTextView desparacitacion;
    private AutoCompleteTextView celo;
    private AutoCompleteTextView lactar;
    private AutoCompleteTextView peso;
    private AutoCompleteTextView doctor;

    AutoCompleteTextView email;

    String nombreString;
    String direccionString;
    String telefonoString;
    String mascotaString;
    String razaString;
    String edadString;
    String tratamientoString;
    String rescatadoString;
    String vacunaString;
    String desparacitacionString;
    String celoString;
    String lactarString;
    String pesoString;


    CheckBox facebook;
    CheckBox volante;
    CheckBox lona;
    CheckBox campana;
    CheckBox recomendacion;
    CheckBox otro;

    CheckBox perro, perra, gato, gata;

    CheckBox chico, mediano, grande, gigante;

    AutoCompleteTextView comentarios, alergia;

    CheckBox button4, button5, button1, button2, button3;

    AutoCompleteTextView acprecio;

    Switch sTarjeta, sEfectivo;

    EditText editTarjeta, editEfectivo;

    Switch devolucion;

    EditText dosis1;

    AutoCompleteTextView comentario;

    private CheckBox cirujano1,
            cirujano2,
            cirujano3,
            cirujano4,
            cirujano5,
            cirujano6,
            cirujano7,
            cirujano8,
            cirujano9,
            cirujano10,
            cirujano11,
            cirujano12;

    Context context = this;

    CheckBox uno, dos, tres, cuatro, cinco, seis, siete;

    private TextView mTextView = null;

    private Database database;
    private Application application;
    // String mes = null;

    DiscoveryListener discoveryListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_panel);
        com.couchbase.lite.util.Log.d(Application.TAG, "Starting MainActivity");
        //itemListView = findViewById(R.id.itemListView);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
//
        application = (Application) getApplication();
        this.database = application.getDatabase();


        date = anio + "/" + mes + "/" + dia;

        //Toast.makeText(getApplicationContext(), String.valueOf(date), Toast.LENGTH_SHORT).show();

        bottomNavigationView = findViewById(R.id.navigation);

        ButterKnife.bind(this);

        /*for(int i = 0; i <= 7; anio++){
            Toast.makeText(getApplicationContext(), String.valueOf(anio), Toast.LENGTH_SHORT).show();
        }*/

        //  imageButton = (ImageButton)findViewById(R.id.imageButton);


        //   mascota = getIntent().getStringExtra("mascota");

        // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // et = findViewById(R.id.et);


        // String idDocumentS = Integer.toString(idDocument);

        //  Toast.makeText(getApplicationContext(), idDocumentS, Toast.LENGTH_SHORT).show();


        //TRAE NULO A TMASCOTA

        //Recibe los datos de los preregistros que vienen de google sheets
        Bundle datos = this.getIntent().getExtras();
        if (datos != null) {

            try {
                showNoFinishedButtons();
            } catch (Exception e) {
                e.printStackTrace();
            }


            String idDocumentStringS = datos.getString("idDocumentStringS");
            String dmascota = datos.getString("dmascota");
            String estadorecibido = datos.getString("active");

            if (idDocumentStringS != null && dmascota != null) {
                estadorecibido = "activado";

                //se necesita guardar a activado porqur solo lo cambiamos pero no se guarda en el registro

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

                try {
                    final String finalEstadorecibido = estadorecibido;

                    document.update(new Document.DocumentUpdater() {
                        @Override
                        public boolean update(UnsavedRevision newRevision) {
                            Map<String, Object> properties = newRevision.getProperties();
                            properties.put("active", finalEstadorecibido);
                            return true;
                        }
                    });
                    Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No se pudo actualizar nuevo preregistro", Toast.LENGTH_LONG).show();
                }

                //falta editar si escogen perro o perra, para cambiar de método

                if (idDocumentStringS != null) {

                    Toast.makeText(getApplication(), dmascota, Toast.LENGTH_SHORT).show();
                    buscaDesocupado(idDocumentStringS, dmascota, estadorecibido);

                } else if (dmascota == null) {
                    // Toast.makeText(getApplicationContext(), "Viene nulo", Toast.LENGTH_SHORT).show();
                } else if (!dmascota.equals("Perro") || !dmascota.equals("Perra") || !dmascota.equals("Gato") || !dmascota.equals("Gato")) {
                    Toast.makeText(getApplicationContext(), "Error, no viene ninguna de las opciones mostradas", Toast.LENGTH_SHORT).show();
                }
            }

        } else {


        }
      /*  if(datos== null) {
           try {
               showNoFinishedButtons();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }*/

        Query all = DataPet.findStatsByDate(database);
        all.setGroupLevel(0);
        all.setDescending(true);
        LiveQuery lq = all.toLiveQuery();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                    setupSync();

            }
        }).start();



    }

    // Here you will enable Multidex
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getBaseContext());
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
                    //   Toast.makeText(getApplicationContext(), "Boton Funcionando", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ContentPanelActivity.this, AddNewDoctor.class);
                    startActivity(intent);
              /*      try {
                        addDoctor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    return true;
                case R.id.navigation_home:
                   /* Intent i = new Intent(ContentPanelActivity.this, RegistroActivity.class);
                    startActivity(i);*/
                    registroActivity();
                    return true;
                case R.id.navigation_dashboard:
                    Intent i = new Intent(ContentPanelActivity.this, DownloadSheetToDB.class);
                    startActivity(i);
                    //recuperacionDialog();
                    return true;
                case R.id.navigation_notifications:
                    reporteRegistros();
                    // Intent tintent = new Intent(ContentPanelActivity.this, ReporteActivity.class);
                    // startActivity(tintent);
                    return true;
                case R.id.load_deleted:
                    stopReplication();
                    /*try {
                        loadDeletedRecords();
                    } catch (CouchbaseLiteException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    return true;
            }
            return false;
        }
    };

    public void registroActivity() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.registroalert_main, null);

        nombre = view.findViewById(R.id.nombre_dueño);
        direccion = view.findViewById(R.id.direccion);
        telefono = view.findViewById(R.id.telefono);
        email = view.findViewById(R.id.email);
        nmascota = view.findViewById(R.id.mascota);
        // final Spinner tipo_mascota = view.findViewById(R.id.tamano_mascota);
        perro = view.findViewById(R.id.perro);
        perra = view.findViewById(R.id.perra);
        gato = view.findViewById(R.id.gato);
        gata = view.findViewById(R.id.gata);
        //final Spinner msocial =view.findViewById(R.id.tmascota);

        facebook = view.findViewById(R.id.fb);
        volante = view.findViewById(R.id.volante);
        lona = view.findViewById(R.id.lona);
        campana = view.findViewById(R.id.campana);
        recomendacion = view.findViewById(R.id.recomendacion);
        otro = view.findViewById(R.id.otro);

        chico = view.findViewById(R.id.chico);
        mediano = view.findViewById(R.id.mediano);
        grande = view.findViewById(R.id.grande);
        gigante = view.findViewById(R.id.gigante);

        raza = view.findViewById(R.id.raza);
        edad = view.findViewById(R.id.edad);
        //  final Spinner tmascota = view.findViewById(R.id.msocial);
        comentarios = view.findViewById(R.id.comentarios);
        tratamiento = view.findViewById(R.id.tratamiento);
        rescatado = view.findViewById(R.id.rescatado);
        vacuna = view.findViewById(R.id.vacuna);
        desparacitacion = view.findViewById(R.id.desparacitacion);
        celo = view.findViewById(R.id.celo);
        lactar = view.findViewById(R.id.lactar);
        peso = view.findViewById(R.id.peso);
        alergia = view.findViewById(R.id.alergia);

        final AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);

        builder.setTitle("Registra nueva mascota");

        builder.setView(view);

        validaRegistro();

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String tipoMascotaString = "";
                        String msocialString = "";
                        String tamanoMascotaString;

                        if (perro.isChecked()) {
                            tipoMascotaString = "Perro";
                        } else if (perra.isChecked()) {
                            tipoMascotaString = "Perra";
                        } else if (gato.isChecked()) {
                            tipoMascotaString = "Gato";
                        } else if (gata.isChecked()) {
                            tipoMascotaString = "Gata";
                        } else {
                            Toast.makeText(getApplicationContext(), "Error no se selecciono detalle de la mascota", Toast.LENGTH_SHORT).show();
                        }

                        if (facebook.isChecked()) {
                            msocialString = "Facebook";
                        } else if (volante.isChecked()) {
                            msocialString = "Volante";
                        } else if (lona.isChecked()) {
                            msocialString = "Vi una lona en la Campaña";
                        } else if (campana.isChecked()) {
                            msocialString = "He ido a otra campaña de Abogados de los Animales";
                        } else if (recomendacion.isChecked()) {
                            msocialString = "Recomendación de otra persona";
                        } else if (otro.isChecked()) {
                            msocialString = "Otro";
                        } else {
                            msocialString = "No se selecciono ninguna opción";
                        }

                        if (chico.isChecked()) {
                            tamanoMascotaString = "Chico";
                        } else if (mediano.isChecked()) {
                            tamanoMascotaString = "Mediano";
                        } else if (grande.isChecked()) {
                            tamanoMascotaString = "Grande";
                        } else if (gigante.isChecked()) {
                            tamanoMascotaString = "Gigante";
                        } else {
                            tamanoMascotaString = "No se selecciono ninguna opción";
                        }

                        nombreString = nombre.getText().toString(); //
                        direccionString = direccion.getText().toString();//
                        telefonoString = telefono.getText().toString(); //
                        String emailString = email.getText().toString();//
                        mascotaString = nmascota.getText().toString();//
                        //    String msocialString = msocial.getSelectedItem().toString(); //
                        razaString = raza.getText().toString();//
                        edadString = edad.getText().toString();//
                        // String tamanoMascotaString = msocial.getSelectedItem().toString(); //
                        String comentarioString = comentarios.getText().toString(); //
                        tratamientoString = tratamiento.getText().toString(); //
                        rescatadoString = rescatado.getText().toString(); //
                        vacunaString = vacuna.getText().toString();  //
                        desparacitacionString = desparacitacion.getText().toString(); //
                        celoString = celo.getText().toString();//
                        lactarString = lactar.getText().toString();//
                        pesoString = peso.getText().toString();
                        String alergiaString = alergia.getText().toString();


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

                        SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
                        idDocument = preferences.getInt("idDocument", 0);

                        idDocument++;

                        SharedPreferences prefs = getSharedPreferences("values", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("idDocument", idDocument);
                        editor.clear().commit();

                        String idDocumentS = Integer.toString(idDocument);

                        estado = "activado";

                        //The properties that will be saved on the document
                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put("folio", idDocumentS);
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
                        properties.put("alergia", alergiaString);
                        properties.put("creat_at", fecha);
                        properties.put("active", estado);
                        //properties.put("campana", anioString + mes);


                        //Create a new document
                        Document document = database.getDocument(idDocumentS);


                        //Save the document to the database
                        try {
                            document.putProperties(properties);
                        } catch (CouchbaseLiteException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Esta guardando " + estado, Toast.LENGTH_SHORT).show();

                        if (tipoMascotaString != "Detalle de la Mascota") {

                            buscaDesocupado(idDocumentS, tipoMascotaString, estado);

                            // Toast.makeText(getApplicationContext(), "El id de registro es " + idDocumentS + " con fecha " + fecha, Toast.LENGTH_SHORT).show();
                           // askPermissionAndWriteFile();
                        } else {
                            // Toast.makeText(Context.CONTEXT_IGNORE_SECURITY, "Debe seleccionar alguna opción en Detalle de la mascota", Toast.LENGTH_SHORT).show();
                        }


                    }


                });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        //    builder.setView(sp1);
        builder.show().getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
    }

    public void pagoDialog(final String idDocumentStringS) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Pago");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.pago_main, null);

        button4 = view.findViewById(R.id.btn4);
        button5 = view.findViewById(R.id.btn5);
        button1 = view.findViewById(R.id.btn1);
        button2 = view.findViewById(R.id.btn2);
        button3 = view.findViewById(R.id.btn3);
        acprecio = view.findViewById(R.id.acprecio);

        sTarjeta = view.findViewById(R.id.starjeta);
        sEfectivo = view.findViewById(R.id.sefectivo);

        editTarjeta = view.findViewById(R.id.editTarjeta);
        editEfectivo = view.findViewById(R.id.editEfectivo);

        devolucion = view.findViewById(R.id.regresardinero_switch);

        builder.setView(view);

        validaPago();

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String precio = "";
                        String stringCantidadTarjeta = "";
                        String stringCantidadEfectivo = "";
                        String aprecio = acprecio.getText().toString();
                        String cantidadTarjeta = editTarjeta.getText().toString();
                        String cantidadEfectivo = editEfectivo.getText().toString();
                        //  String devolucionString = devolucion.getText().toString();

                        if (button1.isChecked()) {
                            //button1.setBackgroundColor(Color.GREEN);
                            precio = "200";
                        } else if (button2.isChecked()) {
                            // button2.setBackgroundColor(Color.GREEN);
                            precio = "250";
                        } else if (button3.isChecked()) {
                            //button3.setBackgroundColor(Color.GREEN);
                            precio = "300";

                        } else if (button4.isChecked()) {
                            precio = "0";

                        } else if (button5.isChecked()) {
                            precio = "150";
                        } else {
                            precio = aprecio;
                        }

                        if (sTarjeta.isChecked()) {
                            stringCantidadTarjeta = cantidadTarjeta;
                        }

                        if (sEfectivo.isChecked()) {
                            stringCantidadEfectivo = cantidadEfectivo;
                        } else {

                        }

                        if (devolucion.isChecked()) {
                            precio = "0";
                            stringCantidadEfectivo = "0";
                            stringCantidadTarjeta = "0";

                        }

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

                        String idDocumentS = Integer.toString(idDocument);

                        //Create a new document
                        Document document = database.getDocument(idDocumentS);

                        try {
                            final String finalPrecio = precio;
                            final String finalStringCantidadTarjeta = stringCantidadTarjeta;
                            final String finalStringCantidadEfectivo = stringCantidadEfectivo;
                            document.update(new Document.DocumentUpdater() {
                                @Override
                                public boolean update(UnsavedRevision newRevision) {
                                    Map<String, Object> properties = newRevision.getProperties();
                                    properties.put("precio_anestesia", finalPrecio);
                                    properties.put("cantidadTarjeta", finalStringCantidadTarjeta);
                                    properties.put("cantidadEfectivo", finalStringCantidadEfectivo);
                                    return true;
                                }
                            });
                            Toast.makeText(getApplicationContext(), "correcto", Toast.LENGTH_LONG).show();
                        } catch (CouchbaseLiteException e) {
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

    public void anestesiaDialog(final String idDocumentStringS) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Folio " + idDocumentStringS);

        // Context context = builder.getContext();
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        dosis1 = new EditText(this);
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

        validaAnestesia();

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String dosisuno = dosis1.getText().toString();
                        String dosisdos = dosis2.getText().toString();
                        String dosistres = dosis3.getText().toString();

                        // Create a manager
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

                        //The properties that will be saved on the document
                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put("dosis1", dosisuno);
                        properties.put("dosis2", dosisdos);
                        properties.put("dosis3", dosistres);
                        properties.put("fecha_anestesia", fecha);

                        //Create a new document
                        Document document = database.getDocument(idDocumentStringS);

                        //Save the document to the database
                        try {
                            document.putProperties(properties);
                        } catch (CouchbaseLiteException e) {
                            e.printStackTrace();
                        }


                        Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();

                        // int color = Color.GREEN;
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

    public void cirugiaDialog(final String idDocumentStringS) {

        final String tipoMascotaString = null;
        final String operacionAnteriorString;

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.cirugiaalert_main, null);

        //2if
        comentario = view.findViewById(R.id.comentario);

        //1 if
        cirujano1 = view.findViewById(R.id.cirujano1);
        cirujano2 = view.findViewById(R.id.cirujano2);
        cirujano3 = view.findViewById(R.id.cirujano3);
        cirujano4 = view.findViewById(R.id.cirujano4);
        cirujano5 = view.findViewById(R.id.cirujano5);
        cirujano6 = view.findViewById(R.id.cirujano6);
        cirujano7 = view.findViewById(R.id.cirujano7);
        cirujano8 = view.findViewById(R.id.cirujano8);
        cirujano9 = view.findViewById(R.id.cirujano9);
        cirujano10 = view.findViewById(R.id.cirujano10);
        cirujano11 = view.findViewById(R.id.cirujano11);
        cirujano12 = view.findViewById(R.id.cirujano12);


        final CheckBox prenada = view.findViewById(R.id.prenada);
        final CheckBox celo = view.findViewById(R.id.celo);
        final CheckBox lactante = view.findViewById(R.id.lactante);
        final CheckBox hemorragia = view.findViewById(R.id.hemorragia);
        final CheckBox paroRespiratorio = view.findViewById(R.id.parorespiratorio);
        final CheckBox paroCardiaco = view.findViewById(R.id.parocardiaco);
        final CheckBox testiculoInguinal = view.findViewById(R.id.tescuculoinguinal);
        final CheckBox finado = view.findViewById(R.id.finado);
        final CheckBox alergico = view.findViewById(R.id.alergico);

        final CheckBox perro = view.findViewById(R.id.perro);
        final CheckBox perra = view.findViewById(R.id.perra);
        final CheckBox gato = view.findViewById(R.id.gato);
        final CheckBox gata = view.findViewById(R.id.gata);

        final Switch operacionanterior = view.findViewById(R.id.anteriormenteoperado_switch);

        if (tipoMascotaString.equals("Perro")) {
            perro.setChecked(true);
        } else if (tipoMascotaString.equals("Perra")) {
            perra.setChecked(true);
        } else if (tipoMascotaString.equals("Gato")) {
            gato.setChecked(true);
        } else if (tipoMascotaString.equals("Gata")) {
            gata.setChecked(true);
        } else {
            Toast.makeText(getApplicationContext(), "No hay selecionado ningun tipo de mascota", Toast.LENGTH_SHORT).show();
        }

        validaCirugia();

        if (operacionanterior.isChecked()) {
            operacionAnteriorString = "Si";
        } else {
            operacionAnteriorString = " No";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Cirugía");

        builder.setView(view);
        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // final String ncirujano = cirujano.getText().toString();
                        final String ncomentario = comentario.getText().toString();
                        // final String tipomascotaString = tipo_mascota.getSelectedItem().toString();

                        String prenadaString = "";
                        String celoString = "";
                        String lactanteString = "";
                        String hemorragiaString = "";
                        String paroRespiratorioString = "";
                        String paroCardicoString = "";
                        String testiculoInguinalString = "";
                        String finadoString = "";
                        String alergicoString = "";
                        String ncirujano = "";

                        //cambiar los ncirujano a la variable del nombre que se tecleará
                        if (cirujano1.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano2.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano3.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else if (cirujano4.isChecked()) {
                            ncirujano = dString;
                        } else {
                            ncirujano = "No se selecciono cirujano";
                        }

                        if (prenada.isChecked()) {
                            prenadaString = "Si";
                        } else {
                            prenadaString = "No";
                        }

                        if (celo.isChecked()) {
                            celoString = "Si";
                        } else {
                            celoString = "No";
                        }

                        if (lactante.isChecked()) {
                            lactanteString = "Si";
                        } else {
                            lactanteString = "No";
                        }

                        if (hemorragia.isChecked()) {
                            hemorragiaString = "Si";
                        } else {
                            hemorragiaString = "No";
                        }

                        if (paroRespiratorio.isChecked()) {
                            paroRespiratorioString = "Si";
                        } else {
                            paroRespiratorioString = "No";
                        }

                        if (paroCardiaco.isChecked()) {
                            paroCardicoString = "Si";
                        } else {
                            paroCardicoString = "No";
                        }

                        if (testiculoInguinal.isChecked()) {
                            testiculoInguinalString = "Si";
                        } else {
                            testiculoInguinalString = "No";
                        }

                        if (finado.isChecked()) {
                            finadoString = "Si";
                        } else {
                            finadoString = "No";
                        }

                        if (alergico.isChecked()) {
                            alergicoString = "Si";
                        } else {
                            alergicoString = "No";
                        }


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

                        try {

                            final String finalPrenadaString = prenadaString;
                            final String finalCeloString = celoString;
                            final String finalLactanteString = lactanteString;
                            final String finalHemorragiaString = hemorragiaString;
                            final String finalParoRespiratorioString = paroRespiratorioString;
                            final String finalParoCardicoString = paroCardicoString;
                            final String finalTesticuloInguinalString = testiculoInguinalString;
                            final String finalFinadoString = finadoString;
                            final String finalAlergicoString = alergicoString;
                            final String finalncirujano = ncirujano;

                            //The properties that will be saved on the document
                            document.update(new Document.DocumentUpdater() {
                                @Override
                                public boolean update(UnsavedRevision newRevision) {
                                    Map<String, Object> properties = newRevision.getProperties();
                                    properties.put("cirujano", finalncirujano);
                                    properties.put("comentario", ncomentario);
                                    properties.put("preñada", finalPrenadaString);
                                    properties.put("celo_cirugia", finalCeloString);
                                    properties.put("lactante_cirugia", finalLactanteString);
                                    properties.put("hemorragia", finalHemorragiaString);
                                    properties.put("parorespiratorio", finalParoRespiratorioString);
                                    properties.put("parocardiaco", finalParoCardicoString);
                                    properties.put("testiculoinguinal", finalTesticuloInguinalString);
                                    properties.put("finado", finalFinadoString);
                                    properties.put("alergico_cirugia", finalAlergicoString);
                                    properties.put("operacion_anterior", operacionAnteriorString);
                                    properties.put("fecha_cirugia", fecha);
                                    return true;
                                }
                            });
                            Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_SHORT).show();
                        } catch (CouchbaseLiteException e) {
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

    public void recuperacionDialog(final String idDocumentStringS) {


        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Recuperación");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.recuperacionalert_main, null);

        final Switch antibiotico = view.findViewById(R.id.antibiotico_switch);
        final Switch tatuaje = view.findViewById(R.id.tatuaje_switch);
        final Switch analgesico = view.findViewById(R.id.analgesico_switch);
        final Switch suero = view.findViewById(R.id.suero_switch);
        final Switch cicatrizante = view.findViewById(R.id.cicatrizante_switch);


        //1 if
        uno = view.findViewById(R.id.uno);
        dos = view.findViewById(R.id.dos);
        tres = view.findViewById(R.id.tres);
        cuatro = view.findViewById(R.id.cuatro);
        cinco = view.findViewById(R.id.cinco);
        seis = view.findViewById(R.id.seis);
        siete = view.findViewById(R.id.siete);

        final Switch llevaIsabelino = view.findViewById(R.id.isabelino_switch);
        final Switch llevaMedicamento = view.findViewById(R.id.medicamento_switch);


        builder.setView(view);

        validaRecuperacion();

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String antibioticoString;
                        final String tatuajeString;
                        final String analgesicoString;
                        final String sueroString;
                        final String cicatrizanteString;
                        final String comentarioString = comentario.getText().toString();

                        final String tallaIsabelinoString;
                        final String llevaIsabelinoString;
                        final String llevaMedicamentoString;

                        if (antibiotico.isChecked()) {
                            antibioticoString = "si";
                        } else {
                            antibioticoString = "no";
                        }

                        if (tatuaje.isChecked()) {
                            tatuajeString = "si";
                        } else {
                            tatuajeString = "no";
                        }

                        if (analgesico.isChecked()) {
                            analgesicoString = "si";
                        } else {
                            analgesicoString = "no";
                        }

                        if (suero.isChecked()) {
                            sueroString = "si";
                        } else {
                            sueroString = "no";
                        }

                        if (cicatrizante.isChecked()) {
                            cicatrizanteString = "si";
                        } else {
                            cicatrizanteString = "no";
                        }

                        if (uno.isChecked()) {
                            tallaIsabelinoString = "1";
                        } else if (dos.isChecked()) {
                            tallaIsabelinoString = "2";
                        } else if (tres.isChecked()) {
                            tallaIsabelinoString = "3";
                        } else if (cuatro.isChecked()) {
                            tallaIsabelinoString = "4";
                        } else if (cinco.isChecked()) {
                            tallaIsabelinoString = "5";
                        } else if (seis.isChecked()) {
                            tallaIsabelinoString = "6";
                        } else if (tres.isChecked()) {
                            tallaIsabelinoString = "7";
                        } else {
                            tallaIsabelinoString = "SN";
                        }


                        if (llevaIsabelino.isChecked()) {
                            llevaIsabelinoString = "Si";
                        } else {
                            llevaIsabelinoString = "No";
                        }

                        if (llevaMedicamento.isChecked()) {
                            llevaMedicamentoString = "Si";
                        } else {
                            llevaMedicamentoString = "No";
                        }

                        comentario.getText().toString();

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

                        Document document = database.getDocument(idDocumentStringS);

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
                                    properties.put("recuperacion_fecha", fecha);
                                    properties.put("comentario_recuperacion", comentarioString);
                                    properties.put("tallaIsabelino", tallaIsabelinoString);
                                    properties.put("llevaIsabelino", llevaIsabelinoString);
                                    properties.put("llevaMedicamento", llevaMedicamentoString);
                                    return true;
                                }
                            });
                            Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();
                        } catch (CouchbaseLiteException e) {
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

    public void compraMedicamentoDialog(final String idDocumentStringS) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Medicamento");
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.compramedicamentoalert_main, null);

        final TextView antibiotico = view.findViewById(R.id.antibiotico_respuesta);
        final TextView tatuaje = view.findViewById(R.id.tatuaje_respuesta);
        final TextView analgesico = view.findViewById(R.id.analgesico_respuesta);
        final TextView suero = view.findViewById(R.id.suero_respuesta);
        final TextView cicatrizante = view.findViewById(R.id.cicatrizante_respuesta);

        final TextView tallaIsabelino = view.findViewById(R.id.talla_respuesta);
        final TextView llevaIsabelino = view.findViewById(R.id.isabelino_respuesta);
        final TextView llevaMedicamento = view.findViewById(R.id.medicamento_respuesta);
        final TextView comentario = view.findViewById(R.id.comentario_respuesta);

        final Switch candidato = view.findViewById(R.id.candidato_switch);
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

        Document doc = database.getDocument(idDocumentStringS);

        doc.getProperty("antibiotico");

        Map<String, Object> properties = doc.getProperties();
        String antibioticoString = (String) properties.get("antibiotico");
        String tatuajeString = (String) properties.get("tatuaje");
        String analgesicoString = (String) properties.get("analgesico");
        String sueroString = (String) properties.get("suero");
        String cicatrizanteString = (String) properties.get("cicatrizante");
        String comentarioString = (String) properties.get("comentario_recuperacion");
        String tallaIsabelinoString = (String) properties.get("tallaIsabelino");
        String llevaIsabelinoString = (String) properties.get("llevaIsabelino");
        String llevaMedicamentoString = (String) properties.get("llevaMedicamento");

        if (antibioticoString != null) {
            antibiotico.setText(antibioticoString);
        }

        if (tatuajeString != null) {
            tatuaje.setText(tatuajeString);
        }

        if (analgesicoString != null) {
            analgesico.setText(analgesicoString);
        }

        if (sueroString != null) {
            suero.setText(sueroString);
        }

        if (cicatrizanteString != null) {
            cicatrizante.setText(cicatrizanteString);
        }

        if (tallaIsabelinoString != null) {
            tallaIsabelino.setText(tallaIsabelinoString);
        }

        if (llevaIsabelinoString != null) {
            llevaIsabelino.setText(llevaIsabelinoString);
        }

        if (llevaMedicamentoString != null) {
            llevaMedicamento.setText(llevaMedicamentoString);
        }

        if (comentarioString != null) {
            comentario.setText(comentarioString);
        }

        final Switch pregunta = view.findViewById(R.id.antibiotico_switch);
        final Switch isabelino = view.findViewById(R.id.isabelino_switch);
        final Switch isodine = view.findViewById(R.id.isodine_switch);
        final Switch entregaMascota = view.findViewById(R.id.mascota_switch);

        builder.setView(view);

        builder.setPositiveButton("Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String idDocumentStringS1 = idDocumentStringS;
                        final String preguntaString;
                        final String isabelinoString;
                        final String isodineString;
                        final String entregaMascotaString;
                        final String candidatoString;


                        if (pregunta.isChecked()) {
                            preguntaString = "Si";
                        } else {
                            preguntaString = "No";
                        }

                        if (isabelino.isChecked()) {
                            isabelinoString = "Si";
                        } else {
                            isabelinoString = "No";
                        }

                        if (isodine.isChecked()) {
                            isodineString = "Si";
                        } else {
                            isodineString = "No";
                        }

                        if (entregaMascota.isChecked()) {
                            entregaMascotaString = "Si";
                        } else {
                            entregaMascotaString = "No";
                        }

                        if (candidato.isChecked()) {
                            candidatoString = "No";
                        } else {
                            candidatoString = "Si";
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
                        estado = "desactivado";

                        try {
                            document.update(new Document.DocumentUpdater() {
                                @Override
                                public boolean update(UnsavedRevision newRevision) {
                                    Map<String, Object> properties = newRevision.getProperties();
                                    properties.put("pregunta_medicamento", preguntaString);
                                    properties.put("isabelino", isabelinoString);
                                    properties.put("isodine", isodineString);
                                    properties.put("mascota_entregada", entregaMascotaString);
                                    properties.put("candidato", candidatoString);
                                    properties.put("medicamento_fecha", fecha);
                                    properties.put("active", estado);
                                    return true;
                                }
                            });
                            // Toast.makeText(getApplicationContext(), preguntaString, Toast.LENGTH_LONG).show();
                            //  Toast.makeText(getApplicationContext(), idDocumentStringS, Toast.LENGTH_LONG).show();


                            Toast.makeText(getApplicationContext(), estado, Toast.LENGTH_SHORT).show();
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


    public void reporteRegistros() {

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
                i.putExtra("dia", dia);
                i.putExtra("mes", mes);
                i.putExtra("anio", anio);
                startActivity(i);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void buscaDesocupado(final String idDocumentStringS, final String tipoMascotaString, final String active) {

        //  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContentPanelActivity.this);
        //  SharedPreferences.Editor editor = preferences.edit();

        if (circleButton1.getVisibility() == View.GONE && tv1.getVisibility() == View.GONE) {

            final int sdk = Build.VERSION.SDK_INT;

            circleButton1.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton1.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton1.setImageResource(R.drawable.ic_gato);
            }

            tv1.setVisibility(View.VISIBLE);
            tv1.setText(idDocumentStringS);
            circleButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton1.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton1.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton1.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton1.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton1.setVisibility(View.GONE);
                                tv1.setVisibility(View.GONE);


                            }
                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton2.getVisibility() == View.GONE && tv2.getVisibility() == View.GONE) {


            circleButton2.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton2.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton2.setImageResource(R.drawable.ic_gato);
            }

            tv2.setVisibility(View.VISIBLE);
            tv2.setText(idDocumentStringS);
            circleButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton2.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton2.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton2.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton2.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton2.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton3.getVisibility() == View.GONE && tv3.getVisibility() == View.GONE) {

            circleButton3.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton3.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton3.setImageResource(R.drawable.ic_gato);
            }

            tv3.setVisibility(View.VISIBLE);
            tv3.setText(idDocumentStringS);
            circleButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton3.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton3.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton3.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton3.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton3.setVisibility(View.GONE);
                                tv3.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton4.getVisibility() == View.GONE && tv4.getVisibility() == View.GONE) {

            circleButton4.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton4.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton4.setImageResource(R.drawable.ic_gato);
            }

            tv4.setVisibility(View.VISIBLE);
            tv4.setText(idDocumentStringS);
            circleButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton4.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton4.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton4.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton4.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton4.setVisibility(View.GONE);
                                tv4.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton5.getVisibility() == View.GONE && tv5.getVisibility() == View.GONE) {

            circleButton5.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton5.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton5.setImageResource(R.drawable.ic_gato);
            }

            tv5.setVisibility(View.VISIBLE);
            tv5.setText(idDocumentStringS);
            circleButton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton5.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton5.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton5.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton5.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton5.setVisibility(View.GONE);
                                tv5.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton6.getVisibility() == View.GONE && tv6.getVisibility() == View.GONE) {

            circleButton6.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton6.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton6.setImageResource(R.drawable.ic_gato);
            }

            tv6.setVisibility(View.VISIBLE);
            tv6.setText(idDocumentStringS);
            circleButton6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton6.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton6.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton6.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton6.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton6.setVisibility(View.GONE);
                                tv6.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });

        } else if (circleButton7.getVisibility() == View.GONE && tv7.getVisibility() == View.GONE) {

            circleButton7.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton7.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton7.setImageResource(R.drawable.ic_gato);
            }

            tv7.setVisibility(View.VISIBLE);
            tv7.setText(idDocumentStringS);
            circleButton7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton7.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton7.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton7.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton7.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton7.setVisibility(View.GONE);
                                tv7.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton8.getVisibility() == View.GONE && tv8.getVisibility() == View.GONE) {


            circleButton8.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton8.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton8.setImageResource(R.drawable.ic_gato);
            }

            tv8.setVisibility(View.VISIBLE);
            tv8.setText(idDocumentStringS);
            circleButton8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton8.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton8.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton8.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton8.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton8.setVisibility(View.GONE);
                                tv8.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });

        } else if (circleButton9.getVisibility() == View.GONE && tv9.getVisibility() == View.GONE) {

            circleButton9.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton9.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton9.setImageResource(R.drawable.ic_gato);
            }

            tv9.setVisibility(View.VISIBLE);
            tv9.setText(idDocumentStringS);
            circleButton9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton9.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton9.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton9.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton9.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton9.setVisibility(View.GONE);
                                tv9.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton10.getVisibility() == View.GONE && tv10.getVisibility() == View.GONE) {

            circleButton10.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton10.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton10.setImageResource(R.drawable.ic_gato);
            }

            tv10.setVisibility(View.VISIBLE);
            tv10.setText(idDocumentStringS);
            circleButton10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton10.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton10.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton10.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton10.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton10.setVisibility(View.GONE);
                                tv10.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });


        } else if (circleButton11.getVisibility() == View.GONE && tv11.getVisibility() == View.GONE) {

            //        prefs.edit().putBoolean("boton11", true).apply();
            //        prefs.edit().putBoolean("tv11", true).apply();

            circleButton11.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton11.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton11.setImageResource(R.drawable.ic_gato);
            }

            tv11.setVisibility(View.VISIBLE);
            tv11.setText(idDocumentStringS);
            circleButton11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton11.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton11.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton11.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton11.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton11.setVisibility(View.GONE);
                                tv11.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton12.getVisibility() == View.GONE && tv12.getVisibility() == View.GONE) {

            //        prefs.edit().putBoolean("boton12", true).apply();
            //        prefs.edit().putBoolean("tv12", true).apply();

            circleButton12.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton12.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton12.setImageResource(R.drawable.ic_gato);
            }

            tv12.setVisibility(View.VISIBLE);
            tv12.setText(idDocumentStringS);
            circleButton12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton12.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton12.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton12.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton12.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton12.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton13.getVisibility() == View.GONE && tv13.getVisibility() == View.GONE) {

            //   prefs.edit().putBoolean("boton13", true).apply();
            //    prefs.edit().putBoolean("tv13", true).apply();

            circleButton13.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton13.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton13.setImageResource(R.drawable.ic_gato);
            }

            tv13.setVisibility(View.VISIBLE);
            tv13.setText(idDocumentStringS);
            circleButton13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton13.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton13.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton13.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton13.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton13.setVisibility(View.GONE);
                                tv13.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton14.getVisibility() == View.GONE && tv14.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton14", true).apply();
            //    prefs.edit().putBoolean("tv14", true).apply();

            circleButton14.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton14.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton14.setImageResource(R.drawable.ic_gato);
            }

            tv14.setVisibility(View.VISIBLE);
            tv14.setText(idDocumentStringS);
            circleButton14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton14.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton14.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton14.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton14.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton14.setVisibility(View.GONE);
                                tv14.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton15.getVisibility() == View.GONE && tv15.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton15", true).apply();
            //     prefs.edit().putBoolean("tv15", true).apply();

            circleButton15.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton15.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton15.setImageResource(R.drawable.ic_gato);
            }

            tv15.setVisibility(View.VISIBLE);
            tv15.setText(idDocumentStringS);
            circleButton15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton15.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton15.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton15.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton15.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton15.setVisibility(View.GONE);
                                tv15.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton16.getVisibility() == View.GONE && tv16.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton16", true).apply();
            //    prefs.edit().putBoolean("tv16", true).apply();

            circleButton16.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton16.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton16.setImageResource(R.drawable.ic_gato);
            }

            tv16.setVisibility(View.VISIBLE);
            tv16.setText(idDocumentStringS);
            circleButton16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton16.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton16.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton16.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton16.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton16.setVisibility(View.GONE);
                                tv16.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton17.getVisibility() == View.GONE && tv17.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton17", true).apply();
            //    prefs.edit().putBoolean("tv17", true).apply();

            circleButton17.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton17.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton17.setImageResource(R.drawable.ic_gato);
            }

            tv17.setVisibility(View.VISIBLE);
            tv17.setText(idDocumentStringS);
            circleButton17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton17.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton17.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton17.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton17.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton17.setVisibility(View.GONE);
                                tv17.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton18.getVisibility() == View.GONE && tv18.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton18", true).apply();
            //    prefs.edit().putBoolean("tv18", true).apply();

            circleButton18.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton18.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton18.setImageResource(R.drawable.ic_gato);
            }

            tv18.setVisibility(View.VISIBLE);
            tv18.setText(idDocumentStringS);
            circleButton18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton18.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton18.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton18.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton18.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton18.setVisibility(View.GONE);
                                tv18.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton19.getVisibility() == View.GONE && tv19.getVisibility() == View.GONE) {

            //   prefs.edit().putBoolean("boton19", true).apply();
            //   prefs.edit().putBoolean("tv19", true).apply();

            circleButton19.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton19.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton19.setImageResource(R.drawable.ic_gato);
            }

            tv19.setVisibility(View.VISIBLE);
            tv19.setText(idDocumentStringS);
            circleButton19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton19.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton19.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton19.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton19.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton19.setVisibility(View.GONE);
                                tv19.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton20.getVisibility() == View.GONE && tv20.getVisibility() == View.GONE) {

            //   prefs.edit().putBoolean("boton20", true).apply();
            //   prefs.edit().putBoolean("tv20", true).apply();

            circleButton20.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton20.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton20.setImageResource(R.drawable.ic_gato);
            }

            tv20.setVisibility(View.VISIBLE);
            tv20.setText(idDocumentStringS);
            circleButton20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton20.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton20.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton20.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton20.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton20.setVisibility(View.GONE);
                                tv20.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton21.getVisibility() == View.GONE && tv21.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton21", true).apply();
            //    prefs.edit().putBoolean("tv21", true).apply();

            circleButton21.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton21.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton21.setImageResource(R.drawable.ic_gato);
            }

            tv21.setVisibility(View.VISIBLE);
            tv21.setText(idDocumentStringS);
            circleButton21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton21.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton21.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton21.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton21.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton21.setVisibility(View.GONE);
                                tv21.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton22.getVisibility() == View.GONE && tv22.getVisibility() == View.GONE) {

            //   prefs.edit().putBoolean("boton22", true).apply();
            //   prefs.edit().putBoolean("tv22", true).apply();

            circleButton22.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton22.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton22.setImageResource(R.drawable.ic_gato);
            }

            tv22.setVisibility(View.VISIBLE);
            tv22.setText(idDocumentStringS);
            circleButton22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton22.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton22.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton22.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton22.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton22.setVisibility(View.GONE);
                                tv22.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton23.getVisibility() == View.GONE && tv23.getVisibility() == View.GONE) {

            //   prefs.edit().putBoolean("boton23", true).apply();
            //    prefs.edit().putBoolean("tv23", true).apply();

            circleButton23.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton23.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton23.setImageResource(R.drawable.ic_gato);
            }

            tv23.setVisibility(View.VISIBLE);
            tv23.setText(idDocumentStringS);
            circleButton23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton23.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton23.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton23.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton23.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton23.setVisibility(View.GONE);
                                tv23.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton24.getVisibility() == View.GONE && tv24.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton24", true).apply();
            //    prefs.edit().putBoolean("tv24", true).apply();

            circleButton24.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton24.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton24.setImageResource(R.drawable.ic_gato);
            }

            tv24.setVisibility(View.VISIBLE);
            tv24.setText(idDocumentStringS);
            circleButton24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton24.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton24.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton24.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton24.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton24.setVisibility(View.GONE);
                                tv24.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton25.getVisibility() == View.GONE && tv25.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton25", true).apply();
            //    prefs.edit().putBoolean("tv25", true).apply();

            circleButton25.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton25.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton25.setImageResource(R.drawable.ic_gato);
            }

            tv25.setVisibility(View.VISIBLE);
            tv25.setText(idDocumentStringS);
            circleButton25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton25.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton25.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton25.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton25.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton25.setVisibility(View.GONE);
                                tv25.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton26.getVisibility() == View.GONE && tv26.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton26", true).apply();
            //    prefs.edit().putBoolean("tv26", true).apply();

            circleButton26.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton26.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton26.setImageResource(R.drawable.ic_gato);
            }

            tv26.setVisibility(View.VISIBLE);
            tv26.setText(idDocumentStringS);
            circleButton26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton26.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton26.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton26.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton26.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton26.setVisibility(View.GONE);
                                tv26.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton27.getVisibility() == View.GONE && tv27.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton27", true).apply();
            //    prefs.edit().putBoolean("tv27", true).apply();

            circleButton27.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton27.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton27.setImageResource(R.drawable.ic_gato);
            }

            tv27.setVisibility(View.VISIBLE);
            tv27.setText(idDocumentStringS);
            circleButton27.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton27.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton27.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton27.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton27.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton27.setVisibility(View.GONE);
                                tv27.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton28.getVisibility() == View.GONE && tv28.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton28", true).apply();
            //    prefs.edit().putBoolean("tv28", true).apply();

            circleButton28.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton28.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton28.setImageResource(R.drawable.ic_gato);
            }

            tv28.setVisibility(View.VISIBLE);
            tv28.setText(idDocumentStringS);
            circleButton28.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton28.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton28.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton28.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton28.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton28.setVisibility(View.GONE);
                                tv28.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton29.getVisibility() == View.GONE && tv29.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton29", true).apply();
            //    prefs.edit().putBoolean("tv29", true).apply();

            circleButton29.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton29.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton29.setImageResource(R.drawable.ic_gato);
            }

            tv29.setVisibility(View.VISIBLE);
            tv29.setText(idDocumentStringS);
            circleButton29.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton29.setColor(Color.GREEN);
                            }
                            if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton29.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton29.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton29.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton29.setVisibility(View.GONE);
                                tv29.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton30.getVisibility() == View.GONE && tv30.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton30", true).apply();
            //    prefs.edit().putBoolean("tv30", true).apply();

            circleButton30.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton30.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton30.setImageResource(R.drawable.ic_gato);
            }

            tv30.setVisibility(View.VISIBLE);
            tv30.setText(idDocumentStringS);
            circleButton30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton30.setColor(Color.GREEN);
                            } else if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton30.setColor(Color.BLUE);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton30.setColor(Color.RED);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton30.setColor(Color.YELLOW);
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
        } else if (circleButton31.getVisibility() == View.GONE && tv31.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton31", true).apply();
            //    prefs.edit().putBoolean("tv31", true).apply();

            circleButton31.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton31.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton31.setImageResource(R.drawable.ic_gato);
            }

            tv31.setVisibility(View.VISIBLE);
            tv31.setText(idDocumentStringS);
            circleButton31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton31.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton31.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton31.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton31.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton31.setVisibility(View.GONE);
                                tv31.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton32.getVisibility() == View.GONE && tv32.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton32", true).apply();
            //    prefs.edit().putBoolean("tv32", true).apply();

            circleButton32.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton32.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton32.setImageResource(R.drawable.ic_gato);
            }
            tv32.setVisibility(View.VISIBLE);
            tv32.setText(idDocumentStringS);
            circleButton32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton32.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton32.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton32.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton32.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton32.setVisibility(View.GONE);
                                tv32.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton33.getVisibility() == View.GONE && tv33.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton33", true).apply();
            //    prefs.edit().putBoolean("tv33", true).apply();

            circleButton33.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton33.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton33.setImageResource(R.drawable.ic_gato);
            }

            tv33.setVisibility(View.VISIBLE);
            tv33.setText(idDocumentStringS);
            circleButton33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton33.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton33.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton33.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton33.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton33.setVisibility(View.GONE);
                                tv33.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton34.getVisibility() == View.GONE && tv34.getVisibility() == View.GONE) {

            //   prefs.edit().putBoolean("boton34", true).apply();
            //   prefs.edit().putBoolean("tv34", true).apply();

            circleButton34.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton34.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton34.setImageResource(R.drawable.ic_gato);
            }

            tv34.setVisibility(View.VISIBLE);
            tv34.setText(idDocumentStringS);
            circleButton34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton34.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton34.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton34.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton34.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton34.setVisibility(View.GONE);
                                tv34.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton35.getVisibility() == View.GONE && tv35.getVisibility() == View.GONE) {

            //    prefs.edit().putBoolean("boton35", true).apply();
            //    prefs.edit().putBoolean("tv35", true).apply();

            circleButton35.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton35.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton35.setImageResource(R.drawable.ic_gato);
            }

            tv35.setVisibility(View.VISIBLE);
            tv35.setText(idDocumentStringS);
            circleButton35.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton35.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton35.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton35.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton35.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton35.setVisibility(View.GONE);
                                tv35.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton36.getVisibility() == View.GONE && tv36.getVisibility() == View.GONE) {

            //   prefs.edit().putBoolean("boton36", true).apply();
            //   prefs.edit().putBoolean("tv36", true).apply();

            circleButton36.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton36.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton36.setImageResource(R.drawable.ic_gato);
            }

            tv36.setVisibility(View.VISIBLE);
            tv36.setText(idDocumentStringS);
            circleButton36.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton36.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton36.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton36.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton36.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton36.setVisibility(View.GONE);
                                tv36.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton37.getVisibility() == View.GONE && tv37.getVisibility() == View.GONE) {
            circleButton37.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton37.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton37.setImageResource(R.drawable.ic_gato);
            }

            tv37.setVisibility(View.VISIBLE);
            tv37.setText(idDocumentStringS);
            circleButton37.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton37.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton37.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton37.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton37.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton37.setVisibility(View.GONE);
                                tv37.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);

                }
            });
        } else if (circleButton38.getVisibility() == View.GONE && tv38.getVisibility() == View.GONE) {
            circleButton38.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton38.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton38.setImageResource(R.drawable.ic_gato);
            }

            tv38.setVisibility(View.VISIBLE);
            tv38.setText(idDocumentStringS);
            circleButton38.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton38.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton38.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton38.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton38.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton38.setVisibility(View.GONE);
                                tv38.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton39.getVisibility() == View.GONE && tv39.getVisibility() == View.GONE) {
            circleButton39.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton39.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton39.setImageResource(R.drawable.ic_gato);
            }

            tv39.setVisibility(View.VISIBLE);
            tv39.setText(idDocumentStringS);
            circleButton39.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton39.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton39.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton39.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton39.setColor(Color.YELLOW);
                            } else if (which == 4
                                    ) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton39.setVisibility(View.GONE);
                                tv39.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton40.getVisibility() == View.GONE && tv40.getVisibility() == View.GONE) {
            circleButton40.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton40.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton40.setImageResource(R.drawable.ic_gato);
            }

            tv40.setVisibility(View.VISIBLE);
            tv40.setText(idDocumentStringS);
            circleButton40.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton40.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton40.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton40.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton40.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton40.setVisibility(View.GONE);
                                tv40.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton41.getVisibility() == View.GONE && tv41.getVisibility() == View.GONE) {
            circleButton41.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton41.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton41.setImageResource(R.drawable.ic_gato);
            }

            tv41.setVisibility(View.VISIBLE);
            tv41.setText(idDocumentStringS);
            circleButton41.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton41.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton41.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton41.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton41.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton41.setVisibility(View.GONE);
                                tv41.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton42.getVisibility() == View.GONE && tv42.getVisibility() == View.GONE) {
            circleButton42.setVisibility(View.VISIBLE);
            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton42.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton42.setImageResource(R.drawable.ic_gato);
            }
            tv42.setVisibility(View.VISIBLE);
            tv42.setText(idDocumentStringS);
            circleButton42.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton42.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton42.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton42.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton42.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton42.setVisibility(View.GONE);
                                tv42.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton43.getVisibility() == View.GONE && tv43.getVisibility() == View.GONE) {
            circleButton43.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton43.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton43.setImageResource(R.drawable.ic_gato);
            }
            tv43.setVisibility(View.VISIBLE);
            tv43.setText(idDocumentStringS);
            circleButton43.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton43.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton43.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton43.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton43.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton43.setVisibility(View.GONE);
                                tv43.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton44.getVisibility() == View.GONE && tv44.getVisibility() == View.GONE) {
            circleButton44.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton44.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton44.setImageResource(R.drawable.ic_gato);
            }

            tv44.setVisibility(View.VISIBLE);
            tv44.setText(idDocumentStringS);
            circleButton44.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton44.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton44.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton44.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton44.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton44.setVisibility(View.GONE);
                                tv44.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton45.getVisibility() == View.GONE && tv45.getVisibility() == View.GONE) {
            circleButton45.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton45.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton45.setImageResource(R.drawable.ic_gato);
            }

            tv45.setVisibility(View.VISIBLE);
            tv45.setText(idDocumentStringS);
            circleButton45.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton45.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton45.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton45.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton45.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton45.setVisibility(View.GONE);
                                tv45.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton46.getVisibility() == View.GONE && tv46.getVisibility() == View.GONE) {
            circleButton46.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton46.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton46.setImageResource(R.drawable.ic_gato);
            }

            tv46.setVisibility(View.VISIBLE);
            tv46.setText(idDocumentStringS);
            circleButton46.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton46.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton46.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton46.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton46.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton46.setVisibility(View.GONE);
                                tv46.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton47.getVisibility() == View.GONE && tv47.getVisibility() == View.GONE) {
            circleButton47.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton47.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton47.setImageResource(R.drawable.ic_gato);
            }

            tv47.setVisibility(View.VISIBLE);
            tv47.setText(idDocumentStringS);
            circleButton47.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton47.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton47.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton47.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton47.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton47.setVisibility(View.GONE);
                                tv47.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton48.getVisibility() == View.GONE && tv48.getVisibility() == View.GONE) {
            circleButton48.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton48.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton48.setImageResource(R.drawable.ic_gato);
            }

            tv48.setVisibility(View.VISIBLE);
            tv48.setText(idDocumentStringS);
            circleButton48.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton48.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton48.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton48.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton48.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton48.setVisibility(View.GONE);
                                tv48.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton49.getVisibility() == View.GONE && tv49.getVisibility() == View.GONE) {
            circleButton49.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton49.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton49.setImageResource(R.drawable.ic_gato);
            }

            tv49.setVisibility(View.VISIBLE);
            tv49.setText(idDocumentStringS);
            circleButton49.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton49.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton49.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton49.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton49.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton49.setVisibility(View.GONE);
                                tv49.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton50.getVisibility() == View.GONE && tv50.getVisibility() == View.GONE) {
            circleButton50.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton50.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton50.setImageResource(R.drawable.ic_gato);
            }

            tv50.setVisibility(View.VISIBLE);
            tv50.setText(idDocumentStringS);
            circleButton50.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton50.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton50.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton50.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton50.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton50.setVisibility(View.GONE);
                                tv50.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton51.getVisibility() == View.GONE && tv51.getVisibility() == View.GONE) {
            circleButton51.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton51.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton51.setImageResource(R.drawable.ic_gato);
            }

            tv51.setVisibility(View.VISIBLE);
            tv51.setText(idDocumentStringS);
            circleButton51.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton51.setColor(Color.GREEN);
                            } else if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton51.setColor(Color.BLUE);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton51.setColor(Color.RED);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton51.setColor(Color.YELLOW);
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
        } else if (circleButton52.getVisibility() == View.GONE && tv52.getVisibility() == View.GONE) {
            circleButton52.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton52.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton52.setImageResource(R.drawable.ic_gato);
            }

            tv52.setVisibility(View.VISIBLE);
            tv52.setText(idDocumentStringS);
            circleButton52.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton52.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton52.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton52.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton52.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton52.setVisibility(View.GONE);
                                tv52.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton53.getVisibility() == View.GONE && tv53.getVisibility() == View.GONE) {
            circleButton53.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton53.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton53.setImageResource(R.drawable.ic_gato);
            }

            tv53.setVisibility(View.VISIBLE);
            tv53.setText(idDocumentStringS);
            circleButton53.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton53.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton53.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton53.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton53.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton53.setVisibility(View.GONE);
                                tv53.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton54.getVisibility() == View.GONE && tv54.getVisibility() == View.GONE) {
            circleButton54.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton54.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton54.setImageResource(R.drawable.ic_gato);
            }

            tv54.setVisibility(View.VISIBLE);
            tv54.setText(idDocumentStringS);
            circleButton54.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton54.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton54.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton54.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton54.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton54.setVisibility(View.GONE);
                                tv54.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton55.getVisibility() == View.GONE && tv55.getVisibility() == View.GONE) {
            circleButton55.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton55.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton55.setImageResource(R.drawable.ic_gato);
            }

            tv55.setVisibility(View.VISIBLE);
            tv55.setText(idDocumentStringS);
            circleButton55.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton55.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton55.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton55.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton55.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton55.setVisibility(View.GONE);
                                tv55.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton56.getVisibility() == View.GONE && tv56.getVisibility() == View.GONE) {
            circleButton56.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton56.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton56.setImageResource(R.drawable.ic_gato);
            }

            tv56.setVisibility(View.VISIBLE);
            tv56.setText(idDocumentStringS);
            circleButton56.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton56.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton56.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton56.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton56.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton56.setVisibility(View.GONE);
                                tv56.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton57.getVisibility() == View.GONE && tv57.getVisibility() == View.GONE) {
            circleButton57.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton57.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton57.setImageResource(R.drawable.ic_gato);
            }

            tv57.setVisibility(View.VISIBLE);
            tv57.setText(idDocumentStringS);
            circleButton57.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton57.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton57.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton57.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton57.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton57.setVisibility(View.GONE);
                                tv57.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton58.getVisibility() == View.GONE && tv58.getVisibility() == View.GONE) {
            circleButton58.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton58.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton58.setImageResource(R.drawable.ic_gato);
            }

            tv58.setVisibility(View.VISIBLE);
            tv58.setText(idDocumentStringS);
            circleButton58.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton58.setColor(Color.GREEN);
                            } else if (which == 0) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton58.setColor(Color.BLUE);
                            } else if (which == 1) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton58.setColor(Color.RED);
                            } else if (which == 2) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton58.setColor(Color.YELLOW);
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
        } else if (circleButton59.getVisibility() == View.GONE && tv59.getVisibility() == View.GONE) {
            circleButton59.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton59.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton59.setImageResource(R.drawable.ic_gato);
            }

            tv59.setVisibility(View.VISIBLE);
            tv59.setText(idDocumentStringS);
            circleButton59.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton59.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton59.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton59.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton59.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton59.setVisibility(View.GONE);
                                tv59.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton60.getVisibility() == View.GONE && tv60.getVisibility() == View.GONE) {
            circleButton60.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton60.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton60.setImageResource(R.drawable.ic_gato);
            }

            tv60.setVisibility(View.VISIBLE);
            tv60.setText(idDocumentStringS);
            circleButton60.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton60.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton60.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton60.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton60.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton60.setVisibility(View.GONE);
                                tv60.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton61.getVisibility() == View.GONE && tv61.getVisibility() == View.GONE) {
            circleButton61.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton61.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton61.setImageResource(R.drawable.ic_gato);
            }

            tv61.setVisibility(View.VISIBLE);
            tv61.setText(idDocumentStringS);
            circleButton61.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton61.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton61.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton61.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton61.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton61.setVisibility(View.GONE);
                                tv61.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton62.getVisibility() == View.GONE && tv62.getVisibility() == View.GONE) {
            circleButton62.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton62.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton62.setImageResource(R.drawable.ic_gato);
            }

            tv62.setVisibility(View.VISIBLE);
            tv62.setText(idDocumentStringS);
            circleButton62.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                //  pagoDialog(idDocumentStringS);
                                circleButton62.setColor(Color.GREEN);
                            } else if (which == 1) {
                                //   anestesiaDialog(idDocumentStringS);
                                circleButton62.setColor(Color.BLUE);
                            } else if (which == 2) {
                                //   cirugiaDialog(idDocumentStringS);
                                circleButton62.setColor(Color.RED);
                            } else if (which == 3) {
                                //  recuperacionDialog(idDocumentStringS);
                                circleButton62.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton62.setVisibility(View.GONE);
                                tv62.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton63.getVisibility() == View.GONE && tv63.getVisibility() == View.GONE) {
            circleButton63.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton63.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton63.setImageResource(R.drawable.ic_gato);
            }

            tv63.setVisibility(View.VISIBLE);
            tv63.setText(idDocumentStringS);
            circleButton63.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton63.setColor(Color.GREEN);
                            }
                            if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton63.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton63.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton63.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton63.setVisibility(View.GONE);
                                tv63.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton64.getVisibility() == View.GONE && tv64.getVisibility() == View.GONE) {
            circleButton64.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton64.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton64.setImageResource(R.drawable.ic_gato);
            }

            tv64.setVisibility(View.VISIBLE);
            tv64.setText(idDocumentStringS);
            circleButton64.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton64.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton64.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton64.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton64.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton64.setVisibility(View.GONE);
                                tv64.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton65.getVisibility() == View.GONE && tv65.getVisibility() == View.GONE) {
            circleButton65.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton65.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton65.setImageResource(R.drawable.ic_gato);
            }

            tv65.setVisibility(View.VISIBLE);
            tv65.setText(idDocumentStringS);
            circleButton65.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton65.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton65.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton65.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton65.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton65.setVisibility(View.GONE);
                                tv65.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton66.getVisibility() == View.GONE && tv66.getVisibility() == View.GONE) {
            circleButton66.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton66.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton66.setImageResource(R.drawable.ic_gato);
            }

            tv66.setVisibility(View.VISIBLE);
            tv66.setText(idDocumentStringS);
            circleButton66.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton66.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton66.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton66.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton66.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton66.setVisibility(View.GONE);
                                tv66.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton67.getVisibility() == View.GONE && tv67.getVisibility() == View.GONE) {
            circleButton67.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton67.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton67.setImageResource(R.drawable.ic_gato);
            }

            tv67.setVisibility(View.VISIBLE);
            tv67.setText(idDocumentStringS);
            circleButton67.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton67.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton67.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton67.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton67.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton67.setVisibility(View.GONE);
                                tv67.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton68.getVisibility() == View.GONE && tv68.getVisibility() == View.GONE) {
            circleButton68.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton68.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton68.setImageResource(R.drawable.ic_gato);
            }

            tv68.setVisibility(View.VISIBLE);
            tv68.setText(idDocumentStringS);
            circleButton68.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton68.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton68.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton68.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton68.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton68.setVisibility(View.GONE);
                                tv68.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton69.getVisibility() == View.GONE && tv69.getVisibility() == View.GONE) {
            circleButton69.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton69.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton69.setImageResource(R.drawable.ic_gato);
            }

            tv69.setVisibility(View.VISIBLE);
            tv69.setText(idDocumentStringS);
            circleButton69.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton69.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton69.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton69.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton69.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton69.setVisibility(View.GONE);
                                tv69.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton70.getVisibility() == View.GONE && tv70.getVisibility() == View.GONE) {
            circleButton70.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton70.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton70.setImageResource(R.drawable.ic_gato);
            }

            tv70.setVisibility(View.VISIBLE);
            tv70.setText(idDocumentStringS);
            circleButton70.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton70.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton70.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton70.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton70.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton70.setVisibility(View.GONE);
                                tv70.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton71.getVisibility() == View.GONE && tv71.getVisibility() == View.GONE) {
            circleButton71.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton71.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton71.setImageResource(R.drawable.ic_gato);
            }

            tv71.setVisibility(View.VISIBLE);
            tv71.setText(idDocumentStringS);
            circleButton71.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton71.setColor(Color.GREEN);
                            }
                            if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton71.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton71.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton71.setColor(Color.YELLOW);
                            } else if (which == 4) {
                                compraMedicamentoDialog(idDocumentStringS);
                                circleButton71.setVisibility(View.GONE);
                                tv71.setVisibility(View.GONE);
                            }

                        }
                    });
                    builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
                }
            });
        } else if (circleButton72.getVisibility() == View.GONE && tv72.getVisibility() == View.GONE) {
            circleButton72.setVisibility(View.VISIBLE);

            if (tipoMascotaString.equals("Perro") || tipoMascotaString.equals("Perra")) {
                circleButton72.setImageResource(R.drawable.ic_perro);
            } else if (tipoMascotaString.equals("Gato") || tipoMascotaString.equals("Gata")) {
                circleButton72.setImageResource(R.drawable.ic_gato);
            }

            tv72.setVisibility(View.VISIBLE);
            tv72.setText(idDocumentStringS);
            circleButton72.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence modulos[] = new CharSequence[]{"Pago", "Anestesia", "Cirugía", "Recuperación", "Medicamento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
                    builder.setTitle("Selecciona Módulo");
                    builder.setItems(modulos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {
                                pagoDialog(idDocumentStringS);
                                circleButton72.setColor(Color.GREEN);
                            } else if (which == 1) {
                                anestesiaDialog(idDocumentStringS);
                                circleButton72.setColor(Color.BLUE);
                            } else if (which == 2) {
                                cirugiaDialog(idDocumentStringS);
                                circleButton72.setColor(Color.RED);
                            } else if (which == 3) {
                                recuperacionDialog(idDocumentStringS);
                                circleButton72.setColor(Color.YELLOW);
                            } else if (which == 4) {
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

        //editor.commit();

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
        final Chunk C = new Chunk("SOLICITUD Y RESPONSIVA" + "Ciudad: Querétaro " + "Fecha: ", BLACK_BOLD2);
        final Chunk D = new Chunk("ABOGADOS DE LOS ANIMALES, A.C.", BLACK_BOLD3);
        final Chunk E = new Chunk("El curso post operatorio es responsabilidad del propietario de la mascota esterilizada y deberá ser supervisada por un médico veterinario competente, siguiendo las recomendaciones del médico veterinario que efectúe la cirugía. \n \n", BLACK_BOLD3);
        final Chunk F = new Chunk("REQUISITOS: \n ", BLACK_BOLD3);

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;

            //create document object
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            //output file path

            try {
                String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ Cartas Responsivas";

                File dir = new File(file);
                if (!dir.exists())
                    dir.mkdirs();
                Log.d("PDFCreator", "PDF Path:" + file);

                File nfile = new File(dir, /*idDocumentS + */".pdf");
                FileOutputStream fOut = new FileOutputStream(nfile);
                PdfWriter.getInstance(doc, fOut);
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

    public void buttonOnClick(View view, final String idDocumentStringS) {

        final CharSequence modulos[] = new CharSequence[]{"Anestesia", "Cirugía", "Recuperación"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ContentPanelActivity.this);
        builder.setTitle("Selecciona Módulo");
        builder.setItems(modulos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    //anestesiaDialog(idDocumentStringS);
                    circleButton72.setColor(Color.GREEN);
                } else if (which == 1) {
                    //cirugiaDialog(idDocumentStringS);
                    circleButton72.setColor(Color.YELLOW);
                } else if (which == 2) {
                    // recuperacionDialog(idDocumentStringS);
                    circleButton72.setColor(Color.BLUE);
                } else if (which == 3) {
                    //Toast.makeText(getApplicationContext(), "Es necesario elegir una opción", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.show().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
    }

    public void validaRegistro() {

    /*    if(nombre.length() == 0){
            nombre.setError("No puede dejar el campo de Nombre vacío");
        }

        if(direccion.length() == 0){
            direccion.setError("No puede dejar el campo de Dirección vacío");
        }

        if(telefono.length() == 0){
            telefono.setError("No puede dejar el campo de Teléfono vacío");
        }

        if(nmascota.length() == 0){
            nmascota.setError("No puede dejar el campo de Nombre de la mascota vacío");
        }

        if(raza.length() == 0){
            raza.setError("No puede dejar el campo de raza vacío");
        }

        if(edad.length() == 0) {
            edad.setError("No puede dejar el campo de edad vacío");
        }

        if(tratamiento.length() == 0){
            tratamiento.setError("No puede dejar el campo de tratamiento vacío");
        }

        if(rescatado.length() == 0) {
            rescatado.setError("No puede dejar el campo rescatado vacío");
        }

        if(vacuna.length() == 0){
            vacuna.setError("No puede dejar el campo de vacuna vacío");
        }

        if(desparacitacion.length() == 0){
            desparacitacion.setError("No puede dejar el campo de desparacitacion vacío");
        }

        if(celo.length() == 0){
            celo.setError("No puede dejar el campo de Celo vacío");
        }

        if(lactar.length() == 0){
            lactar.setError("No puede dejar el campo de lactar vacío");
        }

        if(peso.length() == 0){
            peso.setError("No puede dejar el campo de Peso vacío");
        }

        if(email.length() == 0){
            email.setError("No puede dejar el campo de Email vacío");
        }

        if(comentarios.length() == 0){
            comentarios.setError("No puede dejar el campo de Comentarios vacío");
        }

        if(alergia.length() == 0){
            alergia.setError("No puede dejar el campo de Alergías vacío");
        }

        if(!facebook.isChecked()  || !volante.isChecked() || !lona.isChecked() || !campana.isChecked() || !recomendacion.isChecked() || !otro.isChecked()){
            otro.setError("Debe de seleccionar una opción de la fila");
        }else{

        }

        if(!chico.isChecked() || !mediano.isChecked() || !grande.isChecked() || !gigante.isChecked()){
            gigante.setError("Debe de seleccionar una opción de la fila");
        } else{
        }

        if(!perro.isChecked() || !perra.isChecked() || !gato.isChecked() || !gata.isChecked()){
            gigante.setError("Debe de seleccionar una opción de la fila");
        } else{

        }*/
    }

    private void validaPago() {

    /* if(!button1.isChecked() || !button2.isChecked() || !button3.isChecked() || !button4.isChecked() || !button5.isChecked()){
         button4.setError("Debe de seleccionar una opción de la fila");
     }*/

    }

    private void validaAnestesia() {
      /*  if(dosis1.length() == 0){
            dosis1.setError("Debe de aplicar al menos una dosis a la mascota");
        }*/
    }

    private void validaCirugia() {

     /*   if (comentario.length() == 0){
            comentario.setError("No puede dejar el campo de Comentario vacio");
        }

        if(!cirujano1.isChecked() || !cirujano2.isChecked() || !cirujano4.isChecked() || !cirujano3.isChecked()){
            cirujano4.setError("Debe de seleccionar un cirujano");
        }*/
    }

    private void validaRecuperacion() {
  /*  if(!uno.isChecked() || !dos.isChecked() || !tres.isChecked() || !cuatro.isChecked() || !cinco.isChecked() || !seis.isChecked() || !siete.isChecked()){
       // cirujano4.setError("Debe de seleccionar una talla");
    }*/

    }


    private void loadDeletedRecords() throws Exception {

        Object nombre = null,
                nombreMascota = null,
                peso = null,
                raza = null,
                rescatado = null,
                tamanoMascota = null,
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
                comentario = null,
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
                medicamento_fecha = null,
                estado_catch = null;


        //Create a manager
        Manager manager = null;
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
                            com.couchbase.lite.util.Log.e("inside map", "map");
                            Object date = document.get("creat_at");
                            if (date != null)
                                emitter.emit((String) document.get("creat_at"), document);


                        }
                    }, "1" /* The version number of the mapper... */
            );
            // }

            Query query = database.getView("creat_at").createQuery();
            query.setStartKey(date);

            //  Query query1 = database.getView("active").createQuery();
            //  query1.setStartKey("activado");

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
                estado_catch = row.getDocument().getProperty("active");

                //  Toast.makeText(getApplicationContext(), String.valueOf(fecha) + " " + folio, Toast.LENGTH_SHORT).show();

                if (estado_catch.equals("desactivado")) {
                    estado_catch = "activado";

                    //create manager
                    manager = null;
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

                    //Create a new document
                    Document document = database.getDocument(String.valueOf(folio));

                    try {
                        final Object finalEstado_catch = estado_catch;
                        document.update(new Document.DocumentUpdater() {
                            @Override
                            public boolean update(UnsavedRevision newRevision) {
                                Map<String, Object> properties = newRevision.getProperties();
                                properties.put("active", finalEstado_catch);
                                return true;
                            }
                        });
                        Toast.makeText(getApplicationContext(), "correcto", Toast.LENGTH_LONG).show();
                    } catch (CouchbaseLiteException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
                    }
                    // buscaDesocupado(String.valueOf(folio), String.valueOf(tipoMascota), String.valueOf(estado_catch));
                } else if (estado_catch.equals("activado")) {
                    //   buscaDesocupado(String.valueOf(folio), String.valueOf(tipoMascota), String.valueOf(estado_catch));
                } else {
                    // buscaDesocupado(String.valueOf(folio), "Nada", active_catch_boolean);
                }

            }
        }
    }

    private List<QueryRow> getRowsFromQueryEnumerator(QueryEnumerator queryEnumerator) {
        List<QueryRow> rows = new ArrayList<QueryRow>();
        for (Iterator<QueryRow> it = queryEnumerator; it.hasNext(); ) {
            QueryRow row = it.next();
            rows.add(row);
        }
        return rows;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public void showMeRows() {

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

            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
                QueryRow row = it.next();

                Object doctor = row.getDocument().getProperty("doctor");

                Toast.makeText(getApplicationContext(), String.valueOf(doctor), Toast.LENGTH_SHORT).show();

                if (doctor != null) {

                    dString = String.valueOf(doctor);

                    if (cirujano1.getVisibility() == View.GONE) {
                        cirujano1.setVisibility(View.VISIBLE);
                        cirujano1.setText(dString);
                    } else if (cirujano2.getVisibility() == View.GONE) {
                        cirujano2.setVisibility(View.VISIBLE);
                        cirujano2.setText(dString);
                    } else if (cirujano3.getVisibility() == View.GONE) {
                        cirujano3.setVisibility(View.VISIBLE);
                        cirujano3.setText(dString);
                    } else if (cirujano4.getVisibility() == View.GONE) {
                        cirujano4.setVisibility(View.VISIBLE);
                        cirujano4.setText(dString);
                    } else if (cirujano5.getVisibility() == View.GONE) {
                        cirujano5.setVisibility(View.VISIBLE);
                        cirujano5.setText(dString);
                    } else if (cirujano6.getVisibility() == View.GONE) {
                        cirujano6.setVisibility(View.VISIBLE);
                        cirujano6.setText(dString);
                    } else if (cirujano7.getVisibility() == View.GONE) {
                        cirujano7.setVisibility(View.VISIBLE);
                        cirujano7.setText(dString);
                    } else if (cirujano8.getVisibility() == View.GONE) {
                        cirujano8.setVisibility(View.VISIBLE);
                        cirujano8.setText(dString);
                    } else if (cirujano9.getVisibility() == View.GONE) {
                        cirujano9.setVisibility(View.VISIBLE);
                        cirujano9.setText(dString);
                    } else if (cirujano10.getVisibility() == View.GONE) {
                        cirujano10.setVisibility(View.VISIBLE);
                        cirujano10.setText(dString);
                    } else if (cirujano11.getVisibility() == View.GONE) {
                        cirujano11.setVisibility(View.VISIBLE);
                        cirujano11.setText(dString);
                    } else if (cirujano12.getVisibility() == View.GONE) {
                        cirujano12.setVisibility(View.VISIBLE);
                        cirujano12.setText(dString);
                    } else {

                        Toast.makeText(getApplicationContext(), "No hay suficientes Checkbox para los registros", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        } else {

            Toast.makeText(getApplicationContext(), "Sin registros", Toast.LENGTH_SHORT).show();

        }


        //  displayRows(result);

        //  getRowsFromQueryEnumerator(result);

        Toast.makeText(getApplicationContext(), "Show", Toast.LENGTH_SHORT).show();

    }

    public void showNoFinishedButtons() throws Exception {

        Toast.makeText(getApplicationContext(), "vamos a cargarlos", Toast.LENGTH_SHORT).show();

        Object nombre = null,
                nombreMascota = null,
                peso = null,
                raza = null,
                rescatado = null,
                tamanoMascota = null,
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
                comentario = null,
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
                medicamento_fecha = null,
                estado_catch = null;


        //Create a manager
        Manager manager = null;
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
                            com.couchbase.lite.util.Log.e("inside map", "map");
                            Object date = document.get("creat_at");
                            if (date != null)
                                emitter.emit((String) document.get("creat_at"), document);


                        }
                    }, "1" /* The version number of the mapper... */
            );
            // }

            Query query = database.getView("creat_at").createQuery();
            query.setStartKey(date);

            //  Query query1 = database.getView("active").createQuery();
            //  query1.setStartKey("activado");

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
                estado_catch = row.getDocument().getProperty("active");

                //  Toast.makeText(getApplicationContext(), String.valueOf(fecha) + " " + folio, Toast.LENGTH_SHORT).show();

                if (estado_catch.equals("activado")) {
                    //estado_catch = "activado";
                    // buscaDesocupado(String.valueOf(folio), String.valueOf(tipoMascota), String.valueOf(estado_catch));
                } else if (estado_catch.equals("desactivado")) {
                    // buscaDesocupado(String.valueOf(folio), String.valueOf(tipoMascota), String.valueOf(estado_catch));
                } else {
                    // buscaDesocupado(String.valueOf(folio), "Nada", active_catch_boolean);
                }

            }
        }

    }


    private void setupSync() {
        try {
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);

            String serviceName = "My Android Message Service";
            Configuration configuration = new Configuration(wifi, database, serviceName);
            int cbListenerPort = configuration.startCBLiteListener(5432);
            configuration.exposeService(cbListenerPort);
            configuration.listenForService();
        } catch (Exception e) {
            com.couchbase.lite.util.Log.e(Application.TAG, "Sync URL is invalid, setting up sync failed");
            Log.e("Exception", e.toString());
        }
    }

    private void stopReplication() {
        if (pullReplication != null) {
            pullReplication.removeChangeListener((Replication.ChangeListener) this);
            pullReplication.stop();
            pullReplication = null;
        }

        if (pushReplication != null) {
            pushReplication.removeChangeListener((Replication.ChangeListener) this);
            pushReplication.stop();
            pushReplication = null;
        }
    }
}




