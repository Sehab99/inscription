package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add_class;
    RecyclerView contentRecycler;
    DatabaseReference mBase;
    ClassroomAdapter classroomAdapter;
    ArrayList<Classroom> classroomList;
    FirebaseAuth auth;
    String uid;
    TextView emptyClassroom;
    int count = 0;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_class = findViewById(R.id.add_class);
        auth = FirebaseAuth.getInstance();
        uid = auth.getUid().toString();
        mBase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Classroom");
        mBase.keepSynced(true);
        contentRecycler = (RecyclerView)findViewById(R.id.contentRecycler);
        emptyClassroom = findViewById(R.id.emptyClassroom);

        contentRecycler.setHasFixedSize(true);
        contentRecycler.setLayoutManager(new LinearLayoutManager(this));
        contentRecycler.setItemAnimator(new DefaultItemAnimator());


        mBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classroomList = new ArrayList<>();
                if(snapshot.getChildrenCount() <= 0) {
                    emptyClassroom.setVisibility(View.VISIBLE);
                } else {
                    emptyClassroom.setVisibility(View.GONE);
                }

                DatabaseReference classRef = FirebaseDatabase.getInstance().getReference("Classrooms");

                for(DataSnapshot classSnap : snapshot.getChildren()) {
                    //Classroom classroom = dataSnapshot.getValue(Classroom.class);
                    String classKey = classSnap.getKey();
                    classRef.child(classKey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String className = dataSnapshot.child("className").getValue().toString();
                            String subjectName = dataSnapshot.child("subjectName").getValue().toString();
                            String teacherName = dataSnapshot.child("teacherName").getValue().toString();
                            classroomList.add(new Classroom(className, subjectName, teacherName, classKey));

                            count++;
                            Log.i("Teacher_Home_Count", String.valueOf(count));
                            Log.i("Teacher_Home_Total", String.valueOf(snapshot.getChildrenCount()));
                            if (count>=snapshot.getChildrenCount()) {
                                Log.i("Teacher_Home_", String.valueOf(classroomList));
                                //Collections.sort(classroomList,Collections.reverseOrder());
                                classroomAdapter = new ClassroomAdapter(MainActivity.this, classroomList);
                                contentRecycler.setAdapter(classroomAdapter);
                                classroomAdapter.notifyDataSetChanged();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddClassroom.class));
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

//3 dot Menu on top right corner
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

//on click action on menu options(About and Logout)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(MainActivity.this, about_us.class));
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}