package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class MainActivityStudent extends AppCompatActivity {
    FloatingActionButton add_class;
    RecyclerView contentRecycler;
    DatabaseReference mBase;
    ClassroomAdapter classroomAdapter;
    ArrayList<Classroom> classroomList;
    TextView emptyClassroom;

    public MainActivityStudent() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);

        add_class = findViewById(R.id.add_class);
        mBase = FirebaseDatabase.getInstance().getReference("Classrooms");
        mBase.keepSynced(true);
        contentRecycler = (RecyclerView) findViewById(R.id.contentRecycler);
        emptyClassroom = findViewById(R.id.emptyClassroom);

        contentRecycler.setHasFixedSize(true);
        contentRecycler.setLayoutManager(new LinearLayoutManager(this));
        contentRecycler.setItemAnimator(new DefaultItemAnimator());


        mBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classroomList = new ArrayList<>();
                if (snapshot.getChildrenCount() <= 0) {
                    emptyClassroom.setVisibility(View.VISIBLE);
                } else {
                    emptyClassroom.setVisibility(View.GONE);
                }


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //Classroom classroom = dataSnapshot.getValue(Classroom.class);
                    String classKey = dataSnapshot.getKey();
                    String className = dataSnapshot.child("className").getValue().toString();
                    String subjectName = dataSnapshot.child("subjectName").getValue().toString();
                    String teacherName = dataSnapshot.child("teacherName").getValue().toString();
                    classroomList.add(new Classroom(className, subjectName, teacherName, classKey));
                }
                //Collections.sort(classroomList,Collections.reverseOrder());
                classroomAdapter = new ClassroomAdapter(MainActivityStudent.this, classroomList);
                contentRecycler.setAdapter(classroomAdapter);
                classroomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert =  new AlertDialog.Builder(MainActivityStudent.this);
                alert.setTitle("Join a classroom");
                EditText input = new EditText(MainActivityStudent.this);
                alert.setView(input);
                alert.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String code = String.valueOf(input.getText());
                        //Firebase code to join the classroom
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
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
                startActivity(new Intent(MainActivityStudent.this, about_us.class));
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivityStudent.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivityStudent.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
