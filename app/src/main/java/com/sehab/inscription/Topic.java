package com.sehab.inscription;

public class Topic {
    private String TopicName;
    private String Description;
    private String date;

    public Topic(String TopicName, String Description, String date) {
        this.TopicName = TopicName;
        this.Description = Description;
        this.date = date;
    }

    public Topic() {

    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String TopicName) {
        this.TopicName = TopicName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}