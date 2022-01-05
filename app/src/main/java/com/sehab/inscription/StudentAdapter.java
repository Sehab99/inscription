package com.sehab.inscription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    ArrayList<StudentModel> studentList;
    Context context;
    boolean topic = false;
    StudentAdapter(ArrayList<StudentModel> studentList,Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    StudentAdapter(ArrayList<StudentModel> studentList,Context context,boolean topic) {
        this.studentList = studentList;
        this.context = context;
        this.topic = topic;
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student,parent,false);
        StudentViewHolder viewHolder = new StudentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel student = studentList.get(position);
        holder.studentText.setText(student.getName());
        if (topic) {
            holder.statusText.setVisibility(View.VISIBLE);
            holder.statusText.setText(student.getStatus());
        } else {
            holder.statusText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentText,statusText;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentText = itemView.findViewById(R.id.student_name);
            statusText = itemView.findViewById(R.id.status);
        }
    }
}
