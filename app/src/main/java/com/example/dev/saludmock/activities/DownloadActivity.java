package com.example.dev.saludmock.activities;

/**
 * Created by dev on 15/08/17.
 */
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.adapters.LlenarTablaAdapter;
import com.example.dev.saludmock.R;
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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class DownloadActivity extends Activity
        implements EasyPermissions.PermissionCallbacks{

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private Button mCallApiButton;
    private Button createPdf;
    ProgressDialog mProgress;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<LlenarTablaAdapter.PocketMov> list;


    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String BUTTON_TEXT = "Consulta la última campaña";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {SheetsScopes.SPREADSHEETS};
    private ListView mListView;
    private EditText editText;
    int textlength = 0;
    private ArrayList<LlenarTablaAdapter.PocketMov> listado;

    String sheet = "";
    int idDocument;

    /**
     * Create the main activity.
     *
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sheet = getIntent().getExtras().getString("idSheet");
        if (sheet != null) {
            Toast.makeText(getApplicationContext(), sheet, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

        //setContentView(R.layout.activity_main);
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
                "Click the \'" + BUTTON_TEXT + "\' button to test the API.");
        activityLayout.addView(mOutputText);

        mListView = new ListView(this);
        activityLayout.addView(mListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nom = listado.get(position).getNombre();
                String num = listado.get(position).getNumero();
                String mas = listado.get(position).getMascota();
                String tel = listado.get(position).getTelefono();
                String dir = listado.get(position).getDireccion();
                String edad = listado.get(position).getEdad();
                String raza = listado.get(position).getRaza();
                String tmascota = listado.get(position).getTmascota();
                String tratamiento = listado.get(position).getTratamiento();
                String social = listado.get(position).getSocial();
                String comentario = listado.get(position).getSocial();
                String trescatado = listado.get(position).getTrescatado();
                String fvacuna = listado.get(position).getFvacuna();
                String fdesparacitacion = listado.get(position).getFdesparacitacion();
                String fcelo = listado.get(position).getFcelo();
                String tlactar = listado.get(position).getTlactar();
                String correo = listado.get(position).getCorreo();
                String dmascota = listado.get(position).getDmascota();

                Toast.makeText(getApplicationContext(), dmascota, Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), "Click en la posición "  + listado.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                //Object listItem = list.getItemAtPosition(position);
                Intent i = new Intent(DownloadActivity.this, DownloadSheetToCouchbase.class);
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

                startActivity(i);

            /*    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(DownloadActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Acepta la ejecución de este programa en modo prueba ?");
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
                dialogo1.show();*/

            }
        });

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Consultando datos ...");

        setContentView(activityLayout);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
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
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
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
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     *
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
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
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
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
               DownloadActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Object, Object, ArrayList<LlenarTablaAdapter.PocketMov>> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Consulta la ultima campaña")
                    .build();
        }

        /**
         * Background task to call Google Sheets API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected ArrayList<LlenarTablaAdapter.PocketMov> doInBackground(Object... params) {
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
         *
         * @return List of names and majors
         * @throws IOException
         */
        private ArrayList<LlenarTablaAdapter.PocketMov> getDataFromApi() throws IOException {

            String spreadsheetId = sheet;
            String range = "Respuestas de Formulario 1!A2:Q";
            List<String> results = new ArrayList<String>();
            listado = new ArrayList<LlenarTablaAdapter.PocketMov>();
            ValueRange response = this.mService.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values != null) {
                //results.add("Dueño, Mascota");
                for (List row : values) {
                    LlenarTablaAdapter.PocketMov poc = new LlenarTablaAdapter.PocketMov();
                    //AId
                    if(row.size() >= 0  || row.isEmpty()){
                        poc.setNumero(row.get(0).toString());
                        String value1 = poc.getNumero();
                        Toast.makeText(getApplicationContext(), value1, Toast.LENGTH_LONG).show();

                    }else {
                        poc.setNumero("");
                    }
                    //B
                    //Nombre del dueño
                    if(row.size() >= 1  || row.isEmpty()){
                        poc.setNombre(row.get(1).toString());
                    }else {
                        poc.setNombre("");
                    }
                    //C
                    //Dirección del dueño
                    if(row.size() >= 2  || row.isEmpty()){
                        poc.setDireccion(row.get(2).toString());
                    }else {
                        poc.setDireccion("");
                    }
                    //D
                    //Teléfono del dueño
                    if(row.size() >= 3  || row.isEmpty()){
                        poc.setTelefono(row.get(3).toString());
                    }else {
                        poc.setTelefono("");
                    }
                    //E
                    //Correo
                    if(row.size() >= 4  || row.isEmpty()){
                        poc.setCorreo(row.get(4).toString());
                    }else {
                        poc.setCorreo("");
                    }
                    //F
                    //Nombre de la mascota
                   if(row.size() >= 5 || row.isEmpty() ){
                        poc.setMascota(row.get(5).toString());
                    }else {
                        poc.setMascota("");
                    }
                     //G
                    //Detalles de la mascota
                    if(row.size() >= 6 || row.isEmpty()){
                        poc.setDmascota(row.get(6).toString());
                    }else {
                        poc.setDmascota("");
                    }
                    //MARCA ERROR SI VIENE VACIO
                    //H Raza
                if(row.size() >= 7  && row.isEmpty()){
                        poc.setRaza(row.get(7).toString());
                    }else{
                        poc.setRaza("");
                    }
                    //I
                    //Edad de la mascota
                        if(row.size() >= 8  && row.isEmpty()){
                        poc.setEdad(row.get(8).toString());
                    }else {
                        poc.setEdad("");
                    }
                 //J
                    //medio social
                   if(row.size() >= 9 && row.isEmpty()){
                        poc.setSocial(row.get(9).toString());
                    }else {
                        poc.setSocial("");
                    }
                    //K
                    //Comentario
                    if(row.size() >= 10 && row.isEmpty()){
                        poc.setComentario(row.get(10).toString());
                    }else {
                        poc.setComentario("");
                    }
                 //L
                    //Tratamiento de la mascota
                    if(row.size() >= 11 && row.isEmpty()){
                        poc.setTratamiento(row.get(11).toString());
                    }else {
                        poc.setTratamiento("");
                    }
                    //M
                    //Rescatado
                   if(row.size() >= 12 && row.isEmpty()){
                        poc.setTrescatado(row.get(12).toString());
                    }else {
                        poc.setTrescatado("");
                    }
                    //N
                    //Vacunación
                     if(row.size() >= 13 && row.isEmpty()){
                        poc.setFvacuna(row.get(13).toString());
                    }else {
                        poc.setFvacuna("");
                    }
                  //O
                    //Desparacitacion
                    if(row.size() >= 14 && row.isEmpty()){
                        poc.setFdesparacitacion(row.get(14).toString());
                    }else {
                        poc.setFdesparacitacion("");
                    }
                    //P
                    //Celo
                    if(row.size() >= 15 && row.isEmpty()){
                        poc.setFcelo(row.get(15).toString());
                    }else {
                        poc.setFcelo("");
                    }
                    //Q
                    //Lactar
                   if(row.size() >= 16 && row.isEmpty()){
                        poc.setTlactar(row.get(16).toString());
                    }else {
                        poc.setTlactar("");
                   }

                    results.add(row.get(0) + "," + row.get(1));
                    listado.add(poc);
                }

            }

            //LlenarTablaAdapter adapter = new LlenarTablaAdapter(getApplicationContext(), R.layout.text_view, listado);
            //mListView.setAdapter(adapter);

            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,results);
            //ListView lv= (ListView) findViewById(R.id.id_lista);
            //lv.setAdapter(adapter);
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
                //output.add(0, "Data retrieved using the Google Sheets API:");
                //mOutputText.setText(TextUtils.join("\n", output));

                //Llena el listview
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
                            DownloadActivity.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
                }
            } else {
                mOutputText.setText("Request cancelled.");
            }
        }

    }

    public void aceptar() {
        Toast t=Toast.makeText(this,"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
        t.show();
    }

    public void cancelar() {
        finish();
    }
}
