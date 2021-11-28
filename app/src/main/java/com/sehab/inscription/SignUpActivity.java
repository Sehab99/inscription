package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputRegID;
    private TextInputLayout textInputSignUpPassword;
    private TextInputLayout textInputConformPassword;
    private Button buttonSignUp;
    private Button buttonToLogin;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    HashMap<String, Object> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputRegID = findViewById(R.id.textInputRegID);
        textInputSignUpPassword = findViewById(R.id.textInputSignUpPassword);
        textInputConformPassword = findViewById(R.id.textInputConformPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonToLogin = findViewById(R.id.buttonToLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        userData = new HashMap<>();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textInputEmail.getEditText().getText().toString();
                String fullName = textInputName.getEditText().getText().toString();
                String regID = textInputRegID.getEditText().getText().toString();
                String password = textInputSignUpPassword.getEditText().getText().toString();
                String conformPassword = textInputConformPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(fullName) ||
                        TextUtils.isEmpty(regID) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(conformPassword)) {
                    Toast.makeText(SignUpActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else if(password.length() < 8) {
                    Toast.makeText(SignUpActivity.this, "Password should be minimum 8 character", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(conformPassword)) {
                    Toast.makeText(SignUpActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                } else {
                    userData.put("Email", email);
                    userData.put("Name", fullName);
                    userData.put("Reg ID", regID);
                    signUpUser(email, password);
                }
            }
        });

        buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void signUpUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String userID = task.getResult().getUser().getUid();
                    databaseReference.child(userID).updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> t) {
                            if(t.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "SignUp completed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}