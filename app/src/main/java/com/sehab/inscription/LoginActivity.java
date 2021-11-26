package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputLoginPassword;
    private Button buttonForgotPassword;
    private Button buttonLogin;
    private Button buttonToSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        textInputUsername = findViewById(R.id.textInputUsername);
        textInputLoginPassword = findViewById(R.id.textInputLoginPassword);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonToSignUp = findViewById(R.id.buttonToSignUp);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = textInputUsername.getEditText().getText().toString();
                String password = textInputLoginPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(userName,password);
                }
            }
        });

        buttonToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });
    }

    private void loginUser(String userName, String password) {
        firebaseAuth.signInWithEmailAndPassword(userName,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong credential", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}