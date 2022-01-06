package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.Random;

public class AddTopic extends AppCompatActivity {
    private TextInputLayout topicName;
    private Button buttonAdd;
    FirebaseAuth auth;
    DatabaseReference mBase;
    HashMap<String, Object> newTopicL;
    String classKey;
    Random random = new Random();
    String code = String.format("%04d", random.nextInt(10000));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        getSupportActionBar().setTitle("Create Topic");

        classKey = getIntent().getExtras().getString("classKey");
        topicName = findViewById(R.id.textTopicName);
        buttonAdd = findViewById(R.id.buttonAddTopic);
        newTopicL = new HashMap<>();
        auth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance().getReference();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uID = auth.getUid();
                String TopicName = topicName.getEditText().getText().toString();


                mBase.child("Users").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(TextUtils.isEmpty(TopicName) || TextUtils.isEmpty(code)) {
                            Toast.makeText(AddTopic.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                        } else {

                            Calendar calendar = Calendar.getInstance();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                            String time = sdf.format(calendar.getTime());
                            Toast.makeText(AddTopic.this, time, Toast.LENGTH_SHORT).show();
                            // dd-mm-yyyy 8:00 am
                            newTopicL.put("topicName", TopicName);
                            newTopicL.put("code", code);
                            newTopicL.put("date", time); //date
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