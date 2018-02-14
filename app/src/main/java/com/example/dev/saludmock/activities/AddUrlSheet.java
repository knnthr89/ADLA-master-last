package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
        final String idSheetS = preferences.getString("idSheetS", idSheet);

        addSheet.setText(idSheetS);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idSheet = addSheet.getText().toString();


                if(idSheet == null || idSheet.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Requiere ingresar un Id para avanzar", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(getApplicationContext(), idSheet, Toast.LENGTH_SHORT).show();

                    if(idSheetS != idSheet){

                        SharedPreferences prefs = getSharedPreferences("values", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("idSheetS", idSheet);
                        editor.commit();
                    }


                    Intent intent = new Intent(AddUrlSheet.this, DownloadActivity.class);
                    intent.putExtra("idSheet", idSheet);
                    startActivity(intent);

                }
            }
        });
    }
}
