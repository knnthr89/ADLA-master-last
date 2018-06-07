package com.example.dev.saludmock.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dev.saludmock.db.DBHelper;
import com.example.dev.saludmock.models.Users;
import com.example.dev.saludmock.R;

import java.io.IOException;
import java.io.InputStream;

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

    private ImageView image;

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
        ImageView imageView = (ImageView) findViewById(R.id.image_logo);

        final String imgURL = "/sdcard/imh.jpeg";

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
       String restoreImage = sharedPreferences.getString("path", null);

        if(restoreImage != null){
            Toast.makeText(getApplicationContext(), restoreImage, Toast.LENGTH_LONG).show();
        }



      // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
     // String mImageUri = prefs.getString("imagepath", null);



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

    private Bitmap getBitmapFromAssets(String fileName){
        /*
            AssetManager
                Provides access to an application's raw asset files.
        */

        /*
            public final AssetManager getAssets ()
                Retrieve underlying AssetManager storage for these resources.
        */
        AssetManager am = getAssets();
        InputStream is = null;
        try{
            /*
                public final InputStream open (String fileName)
                    Open an asset using ACCESS_STREAMING mode. This provides access to files that
                    have been bundled with an application as assets -- that is,
                    files placed in to the "assets" directory.

                    Parameters
                        fileName : The name of the asset to open. This name can be hierarchical.
                    Throws
                        IOException
            */
            is = am.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }

        /*
            BitmapFactory
                Creates Bitmap objects from various sources, including files, streams, and byte-arrays.
        */

        /*
            public static Bitmap decodeStream (InputStream is)
                Decode an input stream into a bitmap. If the input stream is null, or cannot
                be used to decode a bitmap, the function returns null. The stream's
                position will be where ever it was after the encoded data was read.

                Parameters
                    is : The input stream that holds the raw data to be decoded into a bitmap.
                Returns
                    The decoded bitmap, or null if the image data could not be decoded.
        */
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

}
