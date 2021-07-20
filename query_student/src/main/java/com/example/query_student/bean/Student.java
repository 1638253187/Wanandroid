package com.example.query_student.bean;

public class Student {
    private int age;
    private String name;
    private String home;

    public Student() {
    }

    public Student(int age, String name, String home) {
        this.age = age;
        this.name = name;
        this.home = home;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", home='" + home + '\'' +
                '}';
    }
}
