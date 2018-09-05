package com.example.dev.saludmock.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.example.dev.saludmock.R;

public class DownloadDataSheetToCB extends AppCompatActivity {

    Manager manager = null;
    Database database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data_sheet_to_cb);

       String nombre = getIntent().getStringExtra("nombre");
       String numero = getIntent().getStringExtra("numero");
       String mascota = getIntent().getStringExtra("mascota");
       String telefono = getIntent().getStringExtra("telefono");
       String direccion = getIntent().getStringExtra("direccion");
       String edad = getIntent().getStringExtra("edad");
       String raza = getIntent().getStringExtra("raza");
       String tmascota = getIntent().getStringExtra("tmascota");
       String tratamiento = getIntent().getStringExtra("tratamiento");
       String social = getIntent().getStringExtra("social");
       String comentario = getIntent().getStringExtra("comentario");
       String trescatado = getIntent().getStringExtra("trescatado");
       String fvacuna = getIntent().getStringExtra("fvacuna");
       String fdesparacitacion = getIntent().getStringExtra("fdesparacitacion");
       String fcelo = getIntent().getStringExtra("fcelo");
       String tlactar = getIntent().getStringExtra("tlactar");
       String correo = getIntent().getStringExtra("correo");
       String dmascota = getIntent().getStringExtra("dmascota");


        Toast.makeText(getApplicationContext(), nombre + numero + mascota + telefono + direccion+ edad + raza + tmascota + tratamiento + social, Toast.LENGTH_LONG).show();
    }
}
