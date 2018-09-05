package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.dev.saludmock.R;

public class DownloadSheetToDB extends AppCompatActivity {

    private Button consultar;
    private AutoCompleteTextView url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_sheet_to_db);

        url = (AutoCompleteTextView)findViewById(R.id.tvSheets);

        String urlstring = url.getText().toString();

        SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
        final String idSheetS = preferences.getString("idSheetS", urlstring);

        url.setText(idSheetS);

        consultar = (Button)findViewById(R.id.btncsheet);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idSheet = url.getText().toString();


                if (idSheet == null || idSheet.isEmpty()) {
                    url.setError("Debe de ingresar in ID para continuar");
                } else {
                    //Toast.makeText(getApplicationContext(), idSheet, Toast.LENGTH_SHORT).show();

                    if (idSheetS != idSheet) {

                        SharedPreferences prefs = getSharedPreferences("values", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("idSheetS", idSheet);
                        editor.commit();
                    }

                    // Toast.makeText(getApplicationContext(), surl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DownloadSheetToDB.this, DownloadSheetToDBStep2.class);
                    intent.putExtra("surl", idSheet);
                    startActivity(intent);
                }
            }
        });

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
        Intent i = new Intent(DownloadSheetToDB.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}
