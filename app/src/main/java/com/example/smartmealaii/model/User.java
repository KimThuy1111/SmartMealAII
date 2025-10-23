package com.example.smartmealaii.model;

public class User {
    private String uid;
    private String email;
    private String name;
    private int age;
    private double weight;
    private double height;
    private String gender;
    private String activity;
    private String goal;

    public User() {
        // Bắt buộc cần constructor rỗng cho Firestore
    }

    public User(String uid, String email, String name, int age, double weight, double height,
                String gender, String activity, String goal) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.activity = activity;
        this.goal = goal;
    }

    // --- Getters và Setters ---
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
}
