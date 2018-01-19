package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;

import java.io.IOException;
import java.util.Map;

public class ReporteActivity extends AppCompatActivity {

  TextView tv, tv2, tv3;
  Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });



        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

      String dia =  getIntent().getExtras().getString("dia");
      String mes =  getIntent().getExtras().getString("mes");
      String anio =getIntent().getExtras().getString("anio");

        Toast.makeText(getApplicationContext(), anio + "/" + mes + "/" + dia , Toast.LENGTH_LONG).show();

        Object date = anio + "/" + mes + "/" + dia;

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

        Document doc = database.getDocument("tapes");
        Map<String, Object> properties = doc.getProperties();
        String fecha = (String) properties.get("fecha");

        if(fecha == date){
            tv.setText(fecha);
        }else {
            Toast.makeText(getApplicationContext(), "No se encuentra registros esa fecha", Toast.LENGTH_LONG).show();
        }



       // String anestesia2 = (String) properties.get("dosis2");
       // String anestesia3 = (String) properties.get("dosis3");


  /*      if(doc!=null){
            tv.setText(fecha);
          //  tv2.setText(anestesia2);
          //  tv3.setText(anestesia3);
        }else {
            Toast.makeText(getApplicationContext(), "No se encuentra el documento", Toast.LENGTH_LONG).show();
        }*/



    }

    protected void sendEmail() {
        String[] TO = {"qro.abogadosdelosanimales@gmail.com"}; //aquí pon tu correo
        String[] CC = {"kenm.riv@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reporte de registros de campaña");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Este es un correo de prueba...");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReporteActivity.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}
