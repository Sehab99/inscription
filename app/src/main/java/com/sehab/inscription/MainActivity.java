package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView contentRecycler;
    DatabaseReference mBase;
    ClassroomAdapter classroomAdapter;
    ArrayList<Classroom> classroomList;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBase = FirebaseDatabase.getInstance().getReference("Classrooms");
        mBase.keepSynced(true);
        contentRecycler = (RecyclerView)findViewById(R.id.contentRecycler);

        contentRecycler.setHasFixedSize(true);
        contentRecycler.setLayoutManager(new LinearLayoutManager(this));
        contentRecycler.setItemAnimator(new DefaultItemAnimator());


        mBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classroomList = new ArrayList<>();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Classroom classroom = dataSnapshot.getValue(Classroom.class);
                    classroomList.add(classroom);
                }

                classroomAdapter = new ClassroomAdapter(MainActivity.this,classroomList);
                contentRecycler.setAdapter(classroomAdapter);
                classroomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
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