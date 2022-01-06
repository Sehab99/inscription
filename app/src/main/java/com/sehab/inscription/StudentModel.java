package com.sehab.inscription;

public class StudentModel {
    String code;
    String name;
    String status;
    String dnt;

    public StudentModel(String code, String name, String status, String dnt) {
        this.code = code;
        this.name = name;
        this.status = status;
        this.dnt = dnt;
    }

    public StudentModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getDnt() { return dnt; }

    public void setDnt(String dnt) { this.dnt = dnt; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
