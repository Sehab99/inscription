package com.sehab.inscription;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ClassroomViewHolder> {

    Context context;
    ArrayList<Topic> topicArrayList;
    String type,uname,classkey;
    View view;



    public TopicAdapter(Context context, ArrayList<Topic> topicArrayList, String type,String classkey,String uname) {
        this.context = context;
        this.topicArrayList = topicArrayList;
        this.type = type;
        this.uname = uname;
        this.classkey = classkey;
    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.card_topic, parent, false);
        ClassroomViewHolder viewHolder = new ClassroomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Topic topic = topicArrayList.get(position);

        holder.TopicName.setText(topic.getTopicName());
        holder.date.setText(topic.getDate());

        if (type.equalsIgnoreCase("student")) {
            holder.Description.setText(topic.getStatus());
            holder.topicCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    otp_pop(topic.getCode(),topic);
                }
            });
        }
        else {
            holder.Description.setText(topic.getCode());
            holder.topicCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeacherAttendanceList.class);
                    intent.putExtra("classCode",classkey);
                    intent.putExtra("topicCode",topic.getKey());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void otp_pop(String code,Topic topic) {
        Button back, present, attend_code;
        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        EditText otp_enter;
        dialogBuilder = new AlertDialog.Builder(context);

        final View popup_view = LayoutInflater.from(context).inflate(R.layout.popup, null);
        otp_enter = (EditText) popup_view.findViewById(R.id.otp_num);
        back = (Button) popup_view.findViewById(R.id.pop_back);
        present = (Button) popup_view.findViewById(R.id.pop_present);
        dialogBuilder.setView(popup_view);

        dialog = dialogBuilder.create();
//        Toast.makeText(context, code, Toast.LENGTH_SHORT).show();
        dialog.show();
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp_enter.getText().toString().equals(code)) {
//                    Toast.makeText(context,"Entered Code : "+ otp_enter.getText().toString(), Toast.LENGTH_SHORT).show();
                    updateTree(topic);
                }

                else
                    Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void updateTree(Topic topic) {
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Classrooms").child(classkey).child("Topics").child(topic.getKey()).child("Present").child(uid);
        HashMap<String,Object> hashMap = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String time = sdf.format(calendar.getTime());

        hashMap.put("time", time);
        hashMap.put("name",uname);
        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(context, "Marked as present", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Cannot mark as present", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public class ClassroomViewHolder extends RecyclerView.ViewHolder {

        TextView TopicName;
        RelativeLayout topicCard;
        TextView Description;
        TextView date;

        public ClassroomViewHolder(@NonNull View itemView) {
            super(itemView);
            TopicName = itemView.findViewById(R.id.topicName);
            Description = itemView.findViewById(R.id.code);
            topicCard = itemView.findViewById(R.id.topicCard);
            date = itemView.findViewById(R.id.date);
        }
    }
}
