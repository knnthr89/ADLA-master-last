package com.example.dev.saludmock.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.example.dev.saludmock.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kennethrizo on 24/10/17.
 */

public class MedicamentoActivity extends AppCompatActivity {

    private Button btn;
    private RadioButton radioButtonSi, radioButtonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicamento_main);

        radioButtonSi = (RadioButton)findViewById(R.id.rbno);
        radioButtonNo = (RadioButton)findViewById(R.id.rbsi);



        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager manager = null;
                try{
                    manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
                }catch (IOException e){
                    e.printStackTrace();
                }

                //Create or open the database named app
                Database database = null;

                try{
                    database = manager.getDatabase("registro");
                }catch (CouchbaseLiteException e){
                    e.printStackTrace();
                }

                Document document = database.getDocument("");
                try {
                    document.update(new Document.DocumentUpdater() {
                        @Override
                        public boolean update(UnsavedRevision unsavedRevision) {
                            Map<String, Object> properties = new HashMap<String, Object>();
                            properties.put("medicamento", "");
                            return true;
                        }
                    });
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Hay un problema con el registro de la dosis con esta mascota!", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(MedicamentoActivity.this, PanelActivity.class );
                startActivity(intent);
            }
        });

    }
}
