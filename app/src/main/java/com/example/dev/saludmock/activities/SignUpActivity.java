package com.example.dev.saludmock.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev.saludmock.db.DBHelper;
import com.example.dev.saludmock.models.Users;
import com.example.dev.saludmock.R;

/**
 * Created by dev on 8/18/17.
 */

public class SignUpActivity extends LoginActivity {

    DBHelper helper = new DBHelper(this);

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView unameView;
    private AutoCompleteTextView passView;
    private AutoCompleteTextView passView2;

    private Button button;

   // private String namestr = "";

    String namestr = "";
    String emailstr = "";
    String unamestr = "";
    String pass1str = "";
    String pass2str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        unameView = findViewById(R.id.username);
        passView = findViewById(R.id.password);
        passView2 = findViewById(R.id.password2);

        button = (Button)findViewById(R.id.save_user_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText name = (EditText)findViewById(R.id.name);
                EditText email = (EditText)findViewById(R.id.email);
                EditText uname = (EditText)findViewById(R.id.username);
                EditText pass1 = (EditText)findViewById(R.id.password);
                EditText pass2 = (EditText)findViewById(R.id.password2);

                namestr = name.getText().toString();
                emailstr = email.getText().toString();
                unamestr = uname.getText().toString();
                pass1str = pass1.getText().toString();
                pass2str = pass2.getText().toString();

                validaUsuario();

                if(!unamestr.isEmpty() && !pass1str.isEmpty() && !pass2str.isEmpty() && isPasswordValid(pass1str) && isPasswordValid(pass2str)){

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    public void validaUsuario(){

        unameView.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if(TextUtils.isEmpty(unamestr)){
            unameView.setError(getString(R.string.error_field_required));
            focusView = unameView;
            cancel = true;
        }else if(!isUserIdValid(unamestr)){
            unameView.setError(getString(R.string.error_invalid_username));
            focusView = passView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(pass1str)){
            passView.setError(getString(R.string.error_field_required));
            focusView = passView;
            cancel = true;
        }else if(!isPasswordValid(pass1str)){
            passView.setError(getString(R.string.error_invalid_password));
            focusView = passView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(pass2str)){
            passView2.setError(getString(R.string.error_field_required));
            focusView = passView2;
            cancel = true;
        }else if(!isPasswordValid(pass2str)){
            passView2.setError(getString(R.string.error_invalid_password));
            focusView = passView2;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            registraUsuario();
        }
    }


    public void registraUsuario(){

       /* LoginActivity loginActivity = new LoginActivity();
        loginActivity.showProgress(true);*/

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
    }

    private boolean isPasswordValid(String pass1str) {
        //TODO: Replace this with your own logic
        return pass1str.length() >= 6;
    }

    private boolean isUserIdValid(String unamestr) {
        //TODO: Replace this with your own logic
        return unamestr.length() >= 6;
    }



}
