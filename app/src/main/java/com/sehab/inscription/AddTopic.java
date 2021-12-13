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

public class AddTopic extends AppCompatActivity {
    private TextInputLayout textInputClassName;
    private TextInputLayout textInputSubjectName;
    private Button buttonAdd;
    FirebaseAuth auth;
    DatabaseReference mBase;
    HashMap<String, Object> newTopicL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classroom);

        textInputClassName = findViewById(R.id.textClassName);
        textInputSubjectName = findViewById(R.id.textSubjectName);
        buttonAdd = findViewById(R.id.buttonAddClassroom);
        newTopicL = new HashMap<>();
        auth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance().getReference();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uID = auth.getUid();
                String TopicName = textInputClassName.getEditText().getText().toString();
                String Description = textInputSubjectName.getEditText().getText().toString();
                mBase.child("Users").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String teacherName = snapshot.child("Name").getValue().toString();
                        if(TextUtils.isEmpty(TopicName) || TextUtils.isEmpty(Description)) {
                            Toast.makeText(AddTopic.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                        } else {
                            newTopicL.put("TopicName", TopicName);
                            newTopicL.put("subjectName", Description);
                            newTopicL.put("teacherName", teacherName); //date
                            addtopic();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void addtopic() {

        DatabaseReference TopicListID = mBase.push();
        String TopicID = TopicListID.getKey();
        mBase.child("Topics").child(TopicID).updateChildren(newTopicL).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AddTopic.this, "topic created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTopic.this, MainClass.class));
                    finish();
                } else {
                    Toast.makeText(AddTopic.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}