package com.sehab.inscription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ClassroomViewHolder> {

    Context context;
    ArrayList<Topic> topicArrayList;

    public TopicAdapter(Context context, ArrayList<Topic> topicArrayList) {
        this.context = context;
        this.topicArrayList = topicArrayList;
    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_topic,parent,false);
        ClassroomViewHolder viewHolder = new ClassroomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Topic topic = topicArrayList.get(position);
        holder.TopicName.setText(topic.getTopicName());
        holder.Description.setText(topic.getTopicDescription());
        holder.date.setText(topic.getDate());
    }

    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public class ClassroomViewHolder extends RecyclerView.ViewHolder {

        TextView TopicName;
        TextView Description;
        TextView date;


        public ClassroomViewHolder(@NonNull View itemView) {
            super(itemView);
            TopicName = itemView.findViewById(R.id.topicName);
            Description = itemView.findViewById(R.id.descName);
            date = itemView.findViewById(R.id.date);


        }


    }
}
