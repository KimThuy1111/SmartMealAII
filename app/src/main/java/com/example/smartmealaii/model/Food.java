package com.example.smartmealaii.model;

import java.io.Serializable;

public class Food implements Serializable {

    private String id;
    private String name;
    private String englishName;
    private String image;

    private double calories;
    private double protein;
    private double fat;


    private double calcium;
    private double iron;
    private double zinc;
    private double sodium;
    private double magnesium;

    private double vitaminA;
    private double potassium;
    private double mufaPufa;
    private double carbs;

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }


    // 🔹 BẮT BUỘC constructor rỗng cho Firestore
    public Food() {}

    // getter & setter (chỉ ví dụ vài cái)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public String getImage() { return image; }

    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getFat() { return fat; }
    public double getCarbohydrate() { return carbs; }

    public double getCalcium() { return calcium; }
    public double getIron() { return iron; }
    public double getZinc() { return zinc; }
    public double getSodium() { return sodium; }
    public double getMagnesium() { return magnesium; }

    public double getVitaminA() { return vitaminA; }
    public double getPotassium() { return potassium; }
    public double getMufaPufa() { return mufaPufa; }


}
