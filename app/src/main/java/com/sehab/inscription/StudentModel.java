package com.sehab.inscription;

public class StudentModel {
    String code,name,status;

    public StudentModel(String code, String name, String status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public StudentModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

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
