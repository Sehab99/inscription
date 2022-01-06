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

public class TeacherAttendanceList extends AppCompatActivity {

    RecyclerView studentRecycler;
    DatabaseReference mBase;
    String classCode,topicCode;

    StudentAdapter adapter;
    ArrayList<StudentModel> studentList;
    ArrayList<String> presentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_student_list);
        getSupportActionBar().setTitle("Attendance List");

        classCode = getIntent().getStringExtra("classCode");
        topicCode = getIntent().getStringExtra("topicCode");

        studentRecycler = findViewById(R.id.student_recycler);
        studentRecycler.setHasFixedSize(true);
        studentRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentRecycler.setItemAnimator(new DefaultItemAnimator());


        mBase = FirebaseDatabase.getInstance().getReference("Classrooms").child(classCode);
        studentList=  new ArrayList<>();

        mBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                presentList = new ArrayList<>();
                for (DataSnapshot userSnap : snapshot.child("Topics").child(topicCode).child("Present").getChildren()) {
                    String code = userSnap.getKey();

                    presentList.add(code);
//                  String name = userSnap.child("name").getValue().toString();

                }
                for (DataSnapshot userSnap : snapshot.child("Students").getChildren()) {
                    String key = userSnap.getKey();
                    String name = userSnap.child("studentName").getValue().toString();
//                    String dnt = "date and time";
                    String status = "",time ="";
                    if (presentList.contains(key)) {
                        time = snapshot.child("Topics").child(topicCode).child("Present").child(key).child("time").getValue().toString();
                        status = "Present";
                    } else {
                        status = "Absent";
                    }

                    studentList.add(new StudentModel(key,name,status,time));
                }
                adapter = new StudentAdapter(studentList, TeacherAttendanceList.this,true);
                studentRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}