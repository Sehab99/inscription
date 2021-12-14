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

import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

public class AddTopic extends AppCompatActivity {
    private TextInputLayout textInputClassName;
    private TextInputLayout textInputSubjectName;
    private Button buttonAdd;
    FirebaseAuth auth;
    DatabaseReference mBase;
    HashMap<String, Object> newTopicL;
    String classKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classroom);

        classKey = getIntent().getExtras().getString("classKey");

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
//                for(final DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    if (snapshot.child("Product_Name").getValue(String.class).equals("Steak")){
//                        String theKey = snapshot.getKey(); //This will return -LoUXnfUCEj4k4A3dkte
//                    }
//                }
                mBase.child("Users").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String teacherName = snapshot.child("Name").getValue().toString();
                     //   Date currentTime = Calendar.getInstance().getTime();
                        if(TextUtils.isEmpty(TopicName) || TextUtils.isEmpty(Description)) {
                            Toast.makeText(AddTopic.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                        } else {

                            Calendar calendar = Calendar.getInstance();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                            String time = sdf.format(calendar.getTime());
                            Toast.makeText(AddTopic.this, time, Toast.LENGTH_SHORT).show();
                            // dd-mm-yyyy 8:00 am
                            newTopicL.put("topicName", TopicName);
                            newTopicL.put("topicDescription", Description);
                            newTopicL.put("date", time); //date
                            addtopic();

                            /*
                            Classroom
                                - basic data
                                - Topics
                                       - basic Data
                                       -
                             */
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
        mBase.child("Classrooms").child(classKey).child("Topics").child(TopicID).updateChildren(newTopicL).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AddTopic.this, "topic created", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(AddTopic.this, TopicMain.class));
                    finish();
                } else {
                    Toast.makeText(AddTopic.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}