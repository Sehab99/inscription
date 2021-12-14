package com.sehab.inscription;

public class Classroom {
    private String className;
    private String subjectName;
    private String teacherName;
    private String classKey;


    public Classroom(String className, String subjectName, String teacherName, String classKey) {
        this.className = className;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.classKey = classKey;
    }

    public Classroom() {

    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setClassKey(String classKey) {
        this.classKey = classKey;
    }

    public String getClassKey() {
        return classKey;
    }
}
