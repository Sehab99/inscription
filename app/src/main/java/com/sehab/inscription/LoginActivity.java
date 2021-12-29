package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String[] ITEMS = new String[] {"Teacher", "Student"};
    private Spinner userDropdown;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputLoginPassword;
    private Button buttonForgotPassword;
    private Button buttonLogin;
    private Button buttonToSignUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;

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
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference().child("Users");

        userDropdown = (Spinner) findViewById(R.id.userDropdown);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ITEMS);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userDropdown.setAdapter(userAdapter);

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
                    String type = userDropdown.getSelectedItem().toString();
                    userRef.child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String user_type =  snapshot.child("Type").getValue().toString();
                            if (type.equals("Student")) {
                                if (user_type.equals(type)) {
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivityStudent.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "This user is not a student", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (user_type.equals(type)) {
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "This user is not a teacher", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "Wrong credential", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}