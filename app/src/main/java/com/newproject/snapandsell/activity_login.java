package com.newproject.snapandsell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class activity_login extends AppCompatActivity {
private Button mRegister,mLogin;
private AutoCompleteTextView mUsername;
private EditText mPassword;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        mRegister= findViewById(R.id.register_button);
        mLogin = findViewById(R.id.signin_button);
        mPassword =findViewById(R.id.login_password);
        mUsername = findViewById(R.id.login_email);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_login.this,activity_register.class);
                startActivity(i);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(activity_login.this);
                progressDialog.setMessage("Verifying...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (mUsername.getText().toString().length() == 0) {
                    mUsername.setError("Enter your email");
                }
                if (mPassword.getText().toString().length() == 0) {
                    mPassword.setError("Enter your password");
                } else{
                    String email = mUsername.getText().toString();
                    String password = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("activity_login", "onComplete: " +task.getException().getMessage());
                            Toast.makeText(activity_login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity_login.this, "Log in successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity_login.this, MainActivity.class);
                            finish();
                            startActivity(intent);

                        }
                        progressDialog.dismiss();
                    }

                });
            }
            }
        });


    }
}
