package com.example.dev.saludmock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dev.saludmock.activities.LoginActivity;

public class AppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(true){
            startActivity(new Intent((this), LoginActivity.class));
            finish();
            return;

        }

    }
}
