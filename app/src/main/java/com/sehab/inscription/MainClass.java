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

public class MainClass extends AppCompatActivity {
    FloatingActionButton add_topic;
    RecyclerView contentRecycler;
    DatabaseReference mBase;
    TopicAdapter topicAdapter;
    ArrayList<Topic> classList;
    TextView emptyTopics;

    public MainClass() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_class);

        add_topic = findViewById(R.id.add_topic); // floating button
        mBase = FirebaseDatabase.getInstance().getReference("Topics"); // firebase variable
        mBase.keepSynced(true);
        contentRecycler = (RecyclerView)findViewById(R.id.contentRecycler);
        emptyTopics = findViewById(R.id.emptyTopics); // emptyClassroom id

        contentRecycler.setHasFixedSize(true);
        contentRecycler.setLayoutManager(new LinearLayoutManager(this));
        contentRecycler.setItemAnimator(new DefaultItemAnimator());


        mBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classList = new ArrayList<>();
                if(snapshot.getChildrenCount() <= 0) {
                    emptyTopics.setVisibility(View.VISIBLE);
                } else {
                    emptyTopics.setVisibility(View.GONE);
                }


                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Topic topic = dataSnapshot.getValue(Topic.class);
                    classList.add(topic);
                }
                topicAdapter = new TopicAdapter(MainClass.this,classList);
                contentRecycler.setAdapter(topicAdapter);
                topicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainClass.this, AddTopic.class));
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

            case R.id.student_list:
                startActivity(new Intent(MainClass.this, ClassStudentList.class));
                break;
            case R.id.about:
                startActivity(new Intent(MainClass.this, about_us.class));
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainClass.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainClass.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}