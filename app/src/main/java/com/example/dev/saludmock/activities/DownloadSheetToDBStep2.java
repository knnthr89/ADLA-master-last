package com.example.dev.saludmock.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;

import com.example.dev.saludmock.adapters.LlenarTablaAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.sheets.v4.SheetsScopes;

import com.google.api.services.sheets.v4.model.*;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class DownloadSheetToDBStep2 extends Activity
        implements EasyPermissions.PermissionCallbacks {


    String estado = "desactivado";

    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private Button mCallApiButton;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String BUTTON_TEXT = "Call Google Sheets API";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { SheetsScopes.SPREADSHEETS_READONLY };

    private ListView mListView;

    private ArrayList<LlenarTablaAdapter.PocketMov> list;

    private ArrayList<LlenarTablaAdapter.PocketMov> listado;
    String url = null;

    Manager manager = null;
    Database database = null;

    int idDocument;

    AutoCompleteTextView nombre;  //
    //  EditText numero = view.findViewById(R.id.edit_numero);  //
    AutoCompleteTextView mascota;  //
    AutoCompleteTextView telefono;  //
    AutoCompleteTextView direccion; //
    AutoCompleteTextView edadet;  //
    AutoCompleteTextView razaet;  //
    AutoCompleteTextView tmascotaet;
    AutoCompleteTextView tratamientoet;
    AutoCompleteTextView socialet; //
    AutoCompleteTextView comentarioet;
    AutoCompleteTextView trescatadoet;
    AutoCompleteTextView fvacunaet;
    AutoCompleteTextView fdesparacitacionet;
    AutoCompleteTextView fceloet;
    AutoCompleteTextView tlactaret;
    AutoCompleteTextView correoet;  //
    AutoCompleteTextView dmascotaet;
    AutoCompleteTextView alergiaet;

    Locale l = new Locale("es", "MX");
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),l);
    String fecha = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE);

    int anio = cal.get(Calendar.YEAR);
    String anioString = Integer.toString(anio);
    String mes = null;

    /**
     * Create the main activity.
     * @param savedInstanceState previously saved instance data.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_sheet_to_dbstep2);

        url = getIntent().getStringExtra("surl");


      //  Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();

        LinearLayout activityLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 16);

        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        mCallApiButton = new Button(this);
        mCallApiButton.setText(BUTTON_TEXT);
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);
                mOutputText.setText("");
                getResultsFromApi();
                mCallApiButton.setEnabled(true);
            }
        });
        activityLayout.addView(mCallApiButton);

        mOutputText = new TextView(this);
        mOutputText.setLayoutParams(tlp);
        mOutputText.setPadding(16, 16, 16, 16);
        mOutputText.setVerticalScrollBarEnabled(true);
        mOutputText.setMovementMethod(new ScrollingMovementMethod());
        mOutputText.setText(
                "Click the \'" + BUTTON_TEXT +"\' button to test the API.");
        activityLayout.addView(mOutputText);

        mListView = new ListView(this);
        activityLayout.addView(mListView);

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nom = listado.get(position).getNombre();  //
                String num = listado.get(position).getNumero();  //
                String mas = listado.get(position).getMascota();  //
                String tel = listado.get(position).getTelefono(); //
                String dir = listado.get(position).getDireccion();   //
                String edad = listado.get(position).getEdad();  //
                String raza = listado.get(position).getRaza();  //
                String tmascota = listado.get(position).getTmascota();  //
                String tratamiento = listado.get(position).getTratamiento();
                String social = listado.get(position).getSocial();
                String comentario = listado.get(position).getComentario();
                String trescatado = listado.get(position).getTrescatado();
                String fvacuna = listado.get(position).getFvacuna();
                String fdesparacitacion = listado.get(position).getFdesparacitacion();
                String fcelo = listado.get(position).getFcelo();
                String tlactar = listado.get(position).getTlactar();
                String correo = listado.get(position).getCorreo();   //
               String dmascota = listado.get(position).getDmascota();



                muestraDatos(nom, num, mas, tel, dir, edad, raza, tmascota, tratamiento, social, comentario, trescatado, fvacuna, fdesparacitacion, fcelo, tlactar, correo, dmascota);

                Toast.makeText(getApplicationContext(), raza, Toast.LENGTH_SHORT).show();

         //        mostrarDatosDeSheet(nom, mas, tel, dir, edad, raza, tmascota, tratamiento, social, comentario, trescatado, fvacuna, fdesparacitacion, fcelo, tlactar, correo, dmascota);



     /*           try{
                    manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
                }catch (IOException e){
                  e.printStackTrace();
                }

                try{
                 database = manager.getDatabase("adla");
                }catch (CouchbaseLiteException d){
                    d.printStackTrace();
                }

                SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
                idDocument = preferences.getInt("idDocument", 0);

                idDocument++;

                SharedPreferences prefs = getSharedPreferences("values", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("idDocument", idDocument);
                editor.commit();

                String idDocumentS = Integer.toString(idDocument);

                //The properties that will be saved on the document
                Map<String, Object> properties = new HashMap<String, Object>();
                properties.put("folio", idDocumentS);
                properties.put("nombreDueno", nom);
                properties.put("direccion", dir);
                properties.put("telefono", tel);
                properties.put("correo", correo);
                properties.put("nombreMascota", mas);
                properties.put("mediosocial", social);
                properties.put("raza", raza);
                properties.put("edad", edad);
                properties.put("tamanoMascota", tmascota);
                properties.put("comentarioRegistro", comentario);
                properties.put("tratamiento", tratamiento);
                properties.put("rescatado", trescatado);
                properties.put("vacuna", fvacuna);
                properties.put("desparacitacion", fdesparacitacion);
                properties.put("celo", fcelo);
                properties.put("lactar", tlactar);
               // properties.put("tipoMascota", tipoMascotaString);
               // properties.put("peso", pesoString);
               // properties.put("alergia", alergiaString);
               // properties.put("creat_at", fecha);

                Document document = database.getDocument(idDocumentS);

                try{
                  document.putProperties(properties);
                }catch (CouchbaseLiteException e){
                    e.printStackTrace();
                }


                //Toast.makeText(getApplicationContext(), "Click en la posición "  + listado.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                //Object listItem = list.getItemAtPosition(position);
            /**    Intent i = new Intent(DownloadSheetToDBStep2.this, DownloadDataSheetToCB.class);
                i.putExtra("nombre",nom);
                i.putExtra("numero", num);
                i.putExtra("mascota", mas);
                i.putExtra("telefono", tel);
                i.putExtra("direccion", dir);
                i.putExtra("edad", edad);
                i.putExtra("raza", raza);
                i.putExtra("tmascota", tmascota);
                i.putExtra("tratamiento", tratamiento);
                i.putExtra("social", social);
                i.putExtra("comentario", comentario);
                i.putExtra("trescatado", trescatado);
                i.putExtra("fvacuna", fvacuna);
                i.putExtra("fdesparacitacion", fdesparacitacion);
                i.putExtra("fcelo", fcelo);
                i.putExtra("tlactar", tlactar);
                i.putExtra("correo", correo);
                i.putExtra("dmascota", dmascota);

                startActivity(i);*/
            }
       });




        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Calling Google Sheets API ...");

        setContentView(activityLayout);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        if(cal.get(Calendar.MONTH) + 1 == 1){
            mes = "Enero";
        }else if(cal.get(Calendar.MONTH) + 1 == 2){
            mes = "Febrero";
        }else if(cal.get(Calendar.MONTH) + 1 == 3){
            mes = "Marzo";
        }else if(cal.get(Calendar.MONTH) + 1 == 4){
            mes = "Abril";
        }else if(cal.get(Calendar.MONTH) + 1 == 5){
            mes = "Mayo";
        }else if(cal.get(Calendar.MONTH) + 1 == 6){
            mes = "Junio";
        } else if(cal.get(Calendar.MONTH) + 1 == 7){
            mes = "Julio";
        } else if(cal.get(Calendar.MONTH) + 1 == 8){
            mes = "Agosto";
        } else if(cal.get(Calendar.MONTH) + 1 == 9){
            mes = "Septiembre";
        } else if(cal.get(Calendar.MONTH) + 1 == 10){
            mes = "Octubre";
        }else if(cal.get(Calendar.MONTH) + 1 == 11){
            mes = "Noviembre";
        }else if(cal.get(Calendar.MONTH) + 1 == 12){
            mes = "Diciembre";
        }else{
            mes = "Error al recibir el mes";
        }
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            mOutputText.setText("No network connection available.");
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                DownloadSheetToDBStep2.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, ArrayList<LlenarTablaAdapter.PocketMov>> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Sheets API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Sheets API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected ArrayList<LlenarTablaAdapter.PocketMov> doInBackground(Void... params) {
            try {
                return list = getDataFromApi();

            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of names and majors of students in a sample spreadsheet:
         * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
         * @return List of names and majors
         * @throws IOException
         */
        private ArrayList<LlenarTablaAdapter.PocketMov> getDataFromApi() throws IOException {
            String spreadsheetId = url;
            String range = "Respuestas de Formulario 1!A2:Q";
            List<String> results = new ArrayList<String>();
            listado = new ArrayList<LlenarTablaAdapter.PocketMov>();
            ValueRange response = this.mService.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values != null) {
                //results.add("Name, Major");
                for (int i = 0; i < values.size();i++) {
                    List row = values.get(i);
                        LlenarTablaAdapter.PocketMov poc = new LlenarTablaAdapter.PocketMov();
                        //AId
                        if (row.size() >= 0 || row.isEmpty()) {
                            poc.setNumero(row.get(0).toString());
                        } else {
                            poc.setNumero("");
                        }
                        //B
                        //Nombre del dueño
                        if (row.size() >= 1 || row.isEmpty()) {
                            poc.setNombre(row.get(1).toString());
                        } else {
                            poc.setNombre("");
                        }
                        //C
                        //Dirección del dueño
                        if (row.size() >= 2 || row.isEmpty()) {
                            poc.setDireccion(row.get(2).toString());
                        } else {
                            poc.setDireccion("");
                        }
                        //D
                        //Teléfono del dueño
                        if (row.size() >= 3 || row.isEmpty()) {
                            poc.setTelefono(row.get(3).toString());
                        } else {
                            poc.setTelefono("");
                        }
                        //E
                        //Correo
                        if (row.size() >= 4 || row.isEmpty()) {
                            poc.setCorreo(row.get(4).toString());
                        } else {
                            poc.setCorreo("");
                        }
                        //F
                        //Nombre de la mascota
                        if (row.size() >= 5 || row.isEmpty()) {
                            poc.setMascota(row.get(5).toString());
                        } else {
                            poc.setMascota("");
                        }
                        //G
                        //Detalles de la mascota
                        if (row.size() >= 6 || row.isEmpty()) {
                            poc.setDmascota(row.get(6).toString());
                        } else {
                            poc.setDmascota("");
                        }
                        //MARCA ERROR SI VIENE VACIO
                        //H Raza
                        if (row.size() >= 7 || row.isEmpty()) {
                            poc.setRaza(row.get(7).toString());
                        } else {
                            poc.setRaza("");
                        }
                        //I
                        //Edad de la mascota
                        if (row.size() >= 8 || row.isEmpty()) {
                            poc.setEdad(row.get(8).toString());
                        } else {
                            poc.setEdad("");
                        }
                        //J
                        //medio social
                        if (row.size() >= 9 || row.isEmpty()) {
                            poc.setSocial(row.get(9).toString());
                        } else {
                            poc.setSocial("");
                        }
  ///////////////////////******NO GUARDA DATOS DESDE AQUI********///////////////////////////////
                        //K
                        //Comentario
                        if (row.size() >= 0 && row.isEmpty()) {
                            poc.setComentario(row.get(10).toString());
                        } else if (row.isEmpty() && row.size() >= 0) {
                            poc.setComentario(row.get(10).toString());
                        }else if (row.size() == 0){
                            poc.setComentario("");
                        }else{
                            poc.setComentario("");
                        }
                        //L
                        //Tratamiento de la mascota
                        if (row.size() >= 0 && row.isEmpty()) {
                            poc.setTratamiento(row.get(11).toString());
                        } else if (row.isEmpty() && row.size() >= 0){
                            poc.setTratamiento(row.get(11).toString());
                        }else if (row.size() == 0){
                            poc.setTratamiento("");
                        }else{
                            poc.setTratamiento("");
                        }

                        //M
                        //Rescatado
                      if (row.size() >= 0 && row.isEmpty()) {
                            poc.setTrescatado(row.get(12).toString());
                        } else  if (row.isEmpty() && row.size() >= 0) {
                          poc.setTrescatado(row.get(12).toString());
                        }else if (row.size() == 0){
                          poc.setTrescatado("");
                      }else{
                          poc.setTrescatado("");
                      }
                        //N
                        //Vacunación
                       if (row.size() >= 0 && row.isEmpty()) {
                            poc.setFvacuna(row.get(13).toString());
                        } else if(row.isEmpty() && row.size() >= 0) {
                           poc.setFvacuna(row.get(13).toString());
                        }else if (row.size() == 0){
                           poc.setTrescatado("");
                       }else{
                           poc.setTrescatado("");
                       }
                        //O
                        //Desparacitacion
                        if (row.size() >= 0 && row.isEmpty()) {
                            poc.setFdesparacitacion(row.get(14).toString());
                        } else if (row.isEmpty() && row.size() >= 0) {
                            poc.setFdesparacitacion(row.get(14).toString());
                        }else if (row.size() == 0){
                            poc.setTrescatado("");
                        }else{
                            poc.setTrescatado("");
                        }
                        //P
                        //Celo
                        if (row.size() >= 0 && row.isEmpty()) {
                            poc.setFcelo(row.get(15).toString());
                        } else if (row.isEmpty() && row.size() >= 0) {
                            poc.setFcelo(row.get(15).toString());
                        }else if (row.size() == 0){
                            poc.setTrescatado("");
                        }else{
                            poc.setTrescatado("");
                        }
                        //Q
                        //Lactar
                        if (row.size() >= 0 && row.isEmpty()) {
                            poc.setTlactar(row.get(16).toString());
                        } else if (row.isEmpty() && row.size() >= 0){
                            poc.setTlactar(row.get(16).toString());
                        }else if (row.size() == 0){
                            poc.setTrescatado("");
                        }else{
                            poc.setTrescatado("");
                        }

/////////////////////////************************************************////////////////////////////////////
                        for (int j = 0; j < row.size(); j++) {
                            results.add(row.get(j) + ",");
                        }
                        listado.add(poc);
                    }
                }

            return listado;
        }



        @Override
        protected void onPreExecute() {
            mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(ArrayList<LlenarTablaAdapter.PocketMov> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                mOutputText.setText("No results returned.");
            } else {
              //  output.add(0, "Data retrieved using the Google Sheets API:");
              //  mOutputText.setText(TextUtils.join("\n", output));

                final LlenarTablaAdapter adapter = new LlenarTablaAdapter(getApplicationContext(), R.layout.text_view, list);
                mListView.setAdapter(adapter);
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            DownloadSheetToDBStep2.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
                }
            } else {
                mOutputText.setText("Request cancelled.");
            }
        }
    }


    public void muestraDatos(String nom, String num,String mas,String tel,String dir,String edad,String raza,String tmascota,String tratamiento,String social,String comentario,String trescatado,String fvacuna,String fdesparacitacion,String fcelo,String tlactar,String correo,String dmascota){

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.editadatosalert_main, null);

        Toast.makeText(getApplicationContext(), nom, Toast.LENGTH_SHORT).show();

        nombre = view.findViewById(R.id.edit_nombre);  //
      //  EditText numero = view.findViewById(R.id.edit_numero);  //
        mascota = view.findViewById(R.id.edit_mascota);  //
        telefono = view.findViewById(R.id.edit_telefono);  //
        direccion = view.findViewById(R.id.edit_direccion); //
        edadet = view.findViewById(R.id.edit_edad);  //
        razaet = view.findViewById(R.id.edit_raza);  //
        tmascotaet = view.findViewById(R.id.edit_tmascota);
        tratamientoet = view.findViewById(R.id.edit_tratamiento);
        socialet = view.findViewById(R.id.edit_social); //
        comentarioet = view.findViewById(R.id.edit_comentario);
        trescatadoet = view.findViewById(R.id.edit_trescatado);
        fvacunaet = view.findViewById(R.id.edit_fvacuna);
        fdesparacitacionet = view.findViewById(R.id.edit_fdesparacitacion);
        fceloet = view.findViewById(R.id.edit_fcelo);
        tlactaret = view.findViewById(R.id.edit_tlactar);
        correoet = view.findViewById(R.id.edit_correo);  //
        dmascotaet = view.findViewById(R.id.edit_dmascota);
        alergiaet = view.findViewById(R.id.edit_alergia);
        //

        if(num != null || !num.isEmpty()) {

         //   Toast.makeText(getApplicationContext(), nom, Toast.LENGTH_LONG).show();
         //   nombre.setText("algun nombre");

         //   Toast.makeText(getApplicationContext(), num, Toast.LENGTH_SHORT).show();


        }else{
       //     Toast.makeText(getApplicationContext(), "vacio", Toast.LENGTH_LONG).show();
           // numero.setText("Vacio");


        }

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(DownloadSheetToDBStep2.this);
        dialogo1.setTitle("Edite su registro si cuenta con algún error");

        dialogo1.setView(view);

        nombre.setText(nom); //
     //   numero.setText(num); //
        mascota.setText(mas);  //
        telefono.setText(tel);  //
        direccion.setText(dir);  //
        edadet.setText(edad); //
        razaet.setText(raza);  //
        //tamaño de mascota
        tmascotaet.setText(tmascota);
        tratamientoet.setText(tratamiento);
        socialet.setText(social);
        comentarioet.setText(comentario);
        trescatadoet.setText(trescatado);
        fvacunaet.setText(fvacuna);
        fdesparacitacionet.setText(fdesparacitacion);
        fceloet.setText(fcelo);
        tlactaret.setText(tlactar);
        correoet.setText(correo);   //
        //detalle de la mascota
        dmascotaet.setText(dmascota);



        //dialogo1.setMessage("¿ Acepta la ejecución de este programa en modo prueba ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                 aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                  cancelar();
            }
        });
        dialogo1.show();

    }

    private void aceptar(){

     String nombreGuardar = nombre.getText().toString();
     String mascotaGuardar = mascota.getText().toString();
     String telefonoGuardar = telefono.getText().toString();
     String direccionGuardar = direccion.getText().toString();
     String edadGuardar = edadet.getText().toString();
     String razaGuardar = razaet.getText().toString();
     String tamañoMascotaGuardar = tmascotaet.getText().toString();
     String tratamientoGuardar = tratamientoet.getText().toString();
     String socialGuardar = socialet.getText().toString();
     String comentarioGuardar = comentarioet.getText().toString();
     String trescatadoGuardar = trescatadoet.getText().toString();
     String fvacunaGuardar = fvacunaet.getText().toString();
     String fdesparacitacionGuardar = fdesparacitacionet.getText().toString();
     String fceloGuardar = fceloet.getText().toString();
     String tlactarGuardar = tlactaret.getText().toString();
     String correoGuardar = correoet.getText().toString();
     String detalleMascotaGuardar = dmascotaet.getText().toString();
     String alergiaGuardar = alergiaet.getText().toString();

     try{
         manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
     }catch (IOException e){
        e.printStackTrace();
     }

     try{
         database = manager.getDatabase("adla");
     }catch (CouchbaseLiteException d){
         d.printStackTrace();
     }

        SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
        idDocument = preferences.getInt("idDocument", 0);

        idDocument++;

        SharedPreferences prefs = getSharedPreferences("values", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("idDocument", idDocument);
        editor.commit();

        String idDocumentS = Integer.toString(idDocument);


        //The properties that will be saved on the document
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("folio", idDocumentS);
        properties.put("nombreDueno", nombreGuardar);
        properties.put("direccion", direccionGuardar);
        properties.put("telefono", telefonoGuardar);
        properties.put("correo", correoGuardar);
        properties.put("nombreMascota", mascotaGuardar);
        properties.put("mediosocial", socialGuardar);
        properties.put("raza", razaGuardar);
        properties.put("edad", edadGuardar);
        properties.put("tamanoMascota", tamañoMascotaGuardar);
        properties.put("comentarioRegistro", comentarioGuardar);
        properties.put("tratamiento", tratamientoGuardar);
        properties.put("rescatado", trescatadoGuardar);
        properties.put("vacuna", fvacunaGuardar);
        properties.put("desparacitacion", fdesparacitacionGuardar);
        properties.put("celo", fceloGuardar);
        properties.put("lactar", tlactarGuardar);
        properties.put("tipoMascota", detalleMascotaGuardar);
       // properties.put("peso", pesoString);
        properties.put("alergia", alergiaGuardar);
        properties.put("creat_at", fecha);
        properties.put("campana", anioString + mes);
        properties.put("active", estado);

        //Create a new document
        Document document = database.getDocument(idDocumentS);


        //Save the document to the database
        try {
            document.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        creaPdf();

        if (detalleMascotaGuardar != "Detalle de la Mascota") {

         //   ContentPanelActivity contentPanelActivity = new ContentPanelActivity();
         //   contentPanelActivity.buscaDesocupado(idDocumentS, detalleMascotaGuardar);

            Toast.makeText(getApplicationContext(), "El id de registro es " + idDocumentS + " con fecha " + fecha, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DownloadSheetToDBStep2.this, CreatePdfActivity.class);
            intent.putExtra("idDocumentStringS", idDocumentS);
            intent.putExtra("nombre", nombreGuardar);
            intent.putExtra("mascota", mascotaGuardar);
            intent.putExtra("tmascota", tamañoMascotaGuardar);
            intent.putExtra("telefono", telefonoGuardar);
            intent.putExtra("direccion", direccionGuardar);
            intent.putExtra("edad", edadGuardar);
            intent.putExtra("raza", razaGuardar);
            intent.putExtra("dmascota", detalleMascotaGuardar);
            intent.putExtra("active", estado);
           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            //askPermissionAndWriteFile();
        } else {
            Toast.makeText(getApplicationContext(), "Debe seleccionar alguna opción en Detalle de la mascota", Toast.LENGTH_SHORT).show();
        }

    }

    private void cancelar(){
    finish();

    }

    private void creaPdf(){

    }


}
