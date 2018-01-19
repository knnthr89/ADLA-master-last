package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.dev.saludmock.R;

public class AddUrlSheet extends AppCompatActivity {

    AutoCompleteTextView addSheet;
    Button btn;
    String idSheet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url_sheet);

        addSheet = findViewById(R.id.idsheet);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idSheet = addSheet.getText().toString();


                if(idSheet == null || idSheet.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Requiere ingresar un Id para avanzar", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(getApplicationContext(), idSheet, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddUrlSheet.this, DownloadActivity.class);
                    intent.putExtra("idSheet", idSheet);
                    startActivity(intent);

                }
            }
        });
    }
}
