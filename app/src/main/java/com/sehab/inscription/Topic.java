package com.sehab.inscription;

public class Topic {
    private String key;
    private String topicName;
    private String code;
    private String date;

    public Topic(String key, String topicName, String code, String date) {
        this.key = key;
        this.topicName = topicName;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}