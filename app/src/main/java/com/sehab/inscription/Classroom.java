package com.sehab.inscription;

public class Classroom {
    private String className;
    private String subjectName;
    private String teacherName;

    public Classroom() {

    }

    public Classroom(String className, String subjectName, String teacherName) {
        this.className = className;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
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
}
