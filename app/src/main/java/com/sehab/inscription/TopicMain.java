package com.sehab.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Calendar;
import java.util.Date;

public class TopicMain extends AppCompatActivity {
    FloatingActionButton add_topic;
    RecyclerView contentRecycler;
    DatabaseReference mBase,userRef, actionBar;
    TopicAdapter topicAdapter;
    ArrayList<Topic> classList;
    TextView emptyTopics;
    TextView classroomKey;
    Button buttonCopy, attendStatus;
     public int p=0,c=0;
    String classKey,uid,type,uname;
    FirebaseAuth auth;
    public TopicMain() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_main);
        getSupportActionBar().setTitle("Topic List");

        add_topic = findViewById(R.id.add_topic); // floating button
        classroomKey = findViewById(R.id.classroomKey);
        buttonCopy = findViewById(R.id.buttonCopy);
        attendStatus = findViewById(R.id.attendStatus);

        classKey = getIntent().getExtras().getString("classKey");

        classroomKey.setText(classKey);

        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


        mBase = FirebaseDatabase.getInstance().getReference().child("Classrooms").child(classKey).child("Topics");
        mBase.keepSynced(true);
        contentRecycler = (RecyclerView)findViewById(R.id.contentRecycler);
        emptyTopics = findViewById(R.id.emptyTopics); // emptyClassroom id
        contentRecycler.setHasFixedSize(true);
        contentRecycler.setLayoutManager(new LinearLayoutManager(this));
        contentRecycler.setItemAnimator(new DefaultItemAnimator());



        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                type = snapshot.child("Type").getValue().toString();
                uname = snapshot.child("Name").getValue().toString();
                if (type.equalsIgnoreCase("student")) {
                    add_topic.setVisibility(View.GONE);
                    attendStatus.setVisibility(View.VISIBLE);
                } else {
                    add_topic.setVisibility(View.VISIBLE);
                    attendStatus.setVisibility(View.GONE);
                }


                mBase.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        p=0;c=0;
                        classList = new ArrayList<>();
                        if(snapshot.getChildrenCount() <= 0) {
                            emptyTopics.setVisibility(View.VISIBLE);
                        } else {
                            emptyTopics.setVisibility(View.GONE);
                        }


                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String key = dataSnapshot.getKey();
                            String status = "";
                            String topic = dataSnapshot.child("topicName").getValue().toString();
                            String desc = dataSnapshot.child("code").getValue().toString();
                            String date = dataSnapshot.child("date").getValue().toString();
                            DataSnapshot userSnap = dataSnapshot.child("Present").child(uid);
                            if (type.equalsIgnoreCase("Student")) {
                                c++;// class count
                                if (userSnap.getChildrenCount() > 0) {
                                    status = "Present";
                                    p++;//present count
                                } else {
                                    status = "Absent";
                                }
                            }

                            classList.add(new Topic(key,topic,desc,status,date));
                        }
                        topicAdapter = new TopicAdapter(TopicMain.this,classList,type,classKey,uname);
                        contentRecycler.setAdapter(topicAdapter);
                        topicAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Classroom Key", classroomKey.getText().toString());
                clipboard.setPrimaryClip(clip);
                clip.getDescription();
                Toast.makeText(TopicMain.this, "Copied", Toast.LENGTH_SHORT).show();
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

        attendStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TopicMain.this, AttendanceStatus.class);
                intent2.putExtra("classes attended",String.valueOf(p));
                intent2.putExtra("classes taken",String.valueOf(c));
                startActivity(intent2);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //3 dot Menu on top right corner
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //on click action on menu options(About and Logout)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(TopicMain.this, about_us.class));
                break;

            case R.id.student_list:
                Intent intent = new Intent(TopicMain.this, ClassStudentList.class);
                intent.putExtra("classCode",classKey);
                startActivity(intent);
                break;

//            case R.id.AttendanceStatus:
//                Intent intent2 = new Intent(TopicMain.this, AttendanceStatus.class);
//                intent2.putExtra("classes attended",String.valueOf(p));
//                intent2.putExtra("classes taken",String.valueOf(c));
//                startActivity(intent2);
//                break;

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