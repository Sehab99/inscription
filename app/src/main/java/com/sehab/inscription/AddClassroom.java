package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddClassroom extends AppCompatActivity {
    private TextInputLayout textInputClassName;
    private TextInputLayout textInputSubjectName;
    private Button buttonAdd;
    FirebaseAuth auth;
    DatabaseReference mBase;
    HashMap<String, Object> newClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classroom);

        textInputClassName = findViewById(R.id.textSubjectName);
        textInputSubjectName = findViewById(R.id.textClassName);
        buttonAdd = findViewById(R.id.buttonAddClassroom);
        newClass = new HashMap<>();
        auth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance().getReference();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uID = auth.getUid();
                String className = textInputClassName.getEditText().getText().toString();
                String subjectName = textInputSubjectName.getEditText().getText().toString();
                String teacherName = mBase.child("Users").child(uID).child("Name").getValue().toString();

                if(TextUtils.isEmpty(className) || TextUtils.isEmpty(subjectName)) {
                    Toast.makeText(AddClassroom.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    newClass.put("className", className);
                    newClass.put("subjectName", subjectName);
                    newClass.put("teacherName", teacherName);
                    addClassroom();
                }
            }
        });

    }

    private void addClassroom() {
        DatabaseReference classID = mBase.push();
        String classroomID = classID.toString();
        mBase.child("Classrooms").child(classroomID).updateChildren(newClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AddClassroom.this, "Classroom created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddClassroom.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(AddClassroom.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}