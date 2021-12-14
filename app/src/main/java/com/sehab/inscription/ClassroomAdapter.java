package com.sehab.inscription;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;


public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ClassroomViewHolder> {

    Context context;
    ArrayList<Classroom> classroomArrayList;

    public ClassroomAdapter(Context context, ArrayList<Classroom> classroomArrayList) {
        this.context = context;
        this.classroomArrayList = classroomArrayList;
    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_classroom,parent,false);
        ClassroomViewHolder viewHolder = new ClassroomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Classroom classroom = classroomArrayList.get(position);
        holder.className.setText(classroom.getClassName());
        holder.subjectName.setText(classroom.getSubjectName());
        holder.teacherName.setText(classroom.getTeacherName());
        holder.classroomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TopicMain.class);
                intent.putExtra("classKey", classroom.getClassKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classroomArrayList.size();
    }

    public static class ClassroomViewHolder extends RecyclerView.ViewHolder {

        TextView className;
        TextView subjectName;
        TextView teacherName;
        RelativeLayout classroomView;
        public ClassroomViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            subjectName = itemView.findViewById(R.id.subjectName);
            teacherName = itemView.findViewById(R.id.teacherName);
            classroomView = itemView.findViewById(R.id.classroomCard);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    view.getContext().startActivity(new Intent(view.getContext(), TopicMain.class));
//
//                    //Toast.makeText(context.getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
