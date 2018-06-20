package com.newproject.snapandsell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {
    private Button signup_button;
    private AutoCompleteTextView mUsername , mEmail;
    private EditText mPassword,mConfirm_password;
    private FirebaseAuth mAuth;
    private String DISPLAY_NAME_PREF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mUsername = findViewById(R.id.register_username);
        mEmail = findViewById(R.id.register_email);
        mPassword = findViewById(R.id.register_password);
        mConfirm_password = findViewById(R.id.register_confirm_password);
        signup_button = findViewById(R.id.register_button);


    }

    public boolean ValidEmail(String email){
        return email.contains("@");
    }

    public boolean ConfirmedPassword(String password){
        String confirmed = mConfirm_password.getText().toString();
        return confirmed.equals(password) && password.length()>5;
    }

    public void CreateNewUser(View view){
        if(mUsername.getText().toString().length() == 0) {
            mUsername.setError("Please enter username.");
        }
            if (mEmail.getText().toString().length() == 0 || !ValidEmail(mEmail.getText().toString())) {
                mEmail.setError("Please valid enter email.");
            }

            if (mPassword.getText().toString().length() == 0) {
                mPassword.setError("Please enter password.");

            }
            if (mConfirm_password.getText().toString().length() == 0 || !ConfirmedPassword(mPassword.getText().toString())){
                mConfirm_password.setError("Please enter valid password and confirm your password.");
        }

else {
            Createuser();
        }
    }
public void Createuser(){
    String email = mEmail.getText().toString();
    String password = mPassword.getText().toString();

    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (!task.isSuccessful()) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity_register.this);
                dialog.setTitle("Error");
                dialog.setMessage("Creating new user unsuccessful.");
                dialog.setPositiveButton("OK", null);
                dialog.show();
                Log.d("activity_register", "onComplete: " + task.getResult());
            } else {
                Toast.makeText(activity_register.this,"User created successful.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_register.this,activity_login.class);
                finish();
                startActivity(intent);
            }



        }
    });
}


}
