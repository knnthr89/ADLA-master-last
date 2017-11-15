package com.example.dev.saludmock.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev.saludmock.DB.DBHelper;
import com.example.dev.saludmock.Models.Users;
import com.example.dev.saludmock.R;

/**
 * Created by dev on 8/18/17.
 */

public class SignUpActivity extends LoginActivity {

    DBHelper helper = new DBHelper(this);
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        button = (Button)findViewById(R.id.save_user_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   attemptLogin();

                EditText name = (EditText)findViewById(R.id.name);
                EditText email = (EditText)findViewById(R.id.email);
                EditText uname = (EditText)findViewById(R.id.username);
                EditText pass1 = (EditText)findViewById(R.id.password);
                EditText pass2 = (EditText)findViewById(R.id.password2);

                String namestr = name.getText().toString();
                String emailstr = email.getText().toString();
                String unamestr = uname.getText().toString();
                String pass1str = pass1.getText().toString();
                String pass2str = pass2.getText().toString();

                if(!pass1str.equals(pass2str)){

                    //popup msg
                    Toast pass = Toast.makeText(SignUpActivity.this, "Las contraseñas no son iguales.", Toast.LENGTH_SHORT);
                    pass.show();

                }else{
                    //insert the details in database
                        Users user = new Users();
                    user.setName(namestr);
                    user.setEmail(emailstr);
                    user.setUsername(unamestr);
                    user.setPassword(pass1str);

                    helper.insertContact(user);
                }

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }


}
