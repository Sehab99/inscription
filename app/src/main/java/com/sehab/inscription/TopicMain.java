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
import java.util.Calendar;
import java.util.Date;

public class TopicMain extends AppCompatActivity {
    FloatingActionButton add_topic;
    RecyclerView contentRecycler;
    DatabaseReference mBase;
    TopicAdapter topicAdapter;
    ArrayList<Topic> classList;
    TextView emptyTopics;
    TextView classroomKey;

    String classKey;
    //FirebaseAuth auth;
    public TopicMain() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_main);
        add_topic = findViewById(R.id.add_topic); // floating button
        classroomKey = findViewById(R.id.classroomKey);

        classKey = getIntent().getExtras().getString("classKey");

        classroomKey.setText(classKey);

        mBase = FirebaseDatabase.getInstance().getReference().child("Classrooms").child(classKey).child("Topics");
        mBase.keepSynced(true);
        contentRecycler = (RecyclerView)findViewById(R.id.contentRecycler);
        emptyTopics = findViewById(R.id.emptyTopics); // emptyClassroom id
        contentRecycler.setHasFixedSize(true);
        contentRecycler.setLayoutManager(new LinearLayoutManager(this));
        contentRecycler.setItemAnimator(new DefaultItemAnimator());


        mBase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classList = new ArrayList<>();
                if(snapshot.getChildrenCount() <= 0) {
                    emptyTopics.setVisibility(View.VISIBLE);
                } else {
                    emptyTopics.setVisibility(View.GONE);
                }


                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    String topic = dataSnapshot.child("topicName").getValue().toString();
                    String desc = dataSnapshot.child("topicDescription").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    classList.add(new Topic(key,topic,desc,date));
                }
                topicAdapter = new TopicAdapter(TopicMain.this,classList);
                contentRecycler.setAdapter(topicAdapter);
                topicAdapter.notifyDataSetChanged();


               // Date currentTime = Calendar.getInstance().getTime();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( TopicMain.this,AddTopic.class);
                intent.putExtra("classKey", classKey);
                startActivity(intent);
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
                startActivity(new Intent(TopicMain.this, ClassStudentList.class));
                break;
            case R.id.about:
                startActivity(new Intent(TopicMain.this, about_us.class));
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(TopicMain.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TopicMain.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}