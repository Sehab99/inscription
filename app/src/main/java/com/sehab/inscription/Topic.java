package com.sehab.inscription;

public class Topic {
    private String key;
    private String topicName;
    private String topicDescription;
    private String date;

    public Topic(String key, String topicName, String topicDescription, String date) {
        this.key = key;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}