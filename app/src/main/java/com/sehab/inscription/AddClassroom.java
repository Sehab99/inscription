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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddClassroom extends AppCompatActivity {
    private TextInputLayout textInputClassName;
    private TextInputLayout textInputSubjectName;
    private Button buttonAdd;
    FirebaseAuth auth;
    DatabaseReference mBase;
    HashMap<String, Object> newClass;
    HashMap<String,Object> userTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classroom);

        textInputClassName = findViewById(R.id.textClassName);
        textInputSubjectName = findViewById(R.id.textSubjectName);
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
                mBase.child("Users").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String teacherName = snapshot.child("Name").getValue().toString();
                        if(TextUtils.isEmpty(className) || TextUtils.isEmpty(subjectName)) {
                            Toast.makeText(AddClassroom.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                        } else {
                            newClass.put("className", className);
                            newClass.put("subjectName", subjectName);
                            newClass.put("teacherName", teacherName);
                            userTree = new HashMap<>();

                            addClassroom(uID,className);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void addClassroom(String uid,String className) {

        DatabaseReference classID = mBase.push();
        String classroomID = classID.getKey();

        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("className",className);
        userTree.put(classroomID,tempMap);


        mBase.child("Classrooms").child(classroomID).updateChildren(newClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    mBase.child("Users").child(uid).child("Classroom").updateChildren(userTree).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if (task1.isSuccessful()) {
                                Toast.makeText(AddClassroom.this, classroomID, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddClassroom.this, MainActivity.class));
                                finish();
                            }  else {
                                Toast.makeText(AddClassroom.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(AddClassroom.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}