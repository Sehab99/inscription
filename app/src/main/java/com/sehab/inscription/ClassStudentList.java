package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassStudentList extends AppCompatActivity {

    RecyclerView studentRecycler;
    DatabaseReference mBase;
    String classCode;
    
    StudentAdapter adapter;
    ArrayList<StudentModel> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_student_list);
        getSupportActionBar().setTitle("Student List");

        classCode = getIntent().getStringExtra("classCode");
        
        studentRecycler = findViewById(R.id.student_recycler);
        studentRecycler.setHasFixedSize(true);
        studentRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentRecycler.setItemAnimator(new DefaultItemAnimator());


        mBase = FirebaseDatabase.getInstance().getReference("Classrooms").child(classCode).child("Students");
        studentList=  new ArrayList<>();
        
        mBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    String key = userSnap.getKey();
                    String name = userSnap.child("studentName").getValue().toString();
                    studentList.add(new StudentModel(key,name));
                }
                adapter = new StudentAdapter(studentList,ClassStudentList.this);
                studentRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}