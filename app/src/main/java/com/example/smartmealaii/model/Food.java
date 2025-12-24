package com.example.smartmealaii.model;

import java.io.Serializable;

public class Food implements Serializable {

    // Cho phép FoodRepository gán trực tiếp không cần setter
    public String name;
    public String englishName;

    private String image;

    public String getImage() {
        return image;
    }
    public double calories;
    public double protein;
    public double fat;
    public double carbohydrate;

    public double vitaminA;
    public double potassium;
    public double mufaPufa;
    public double calcium;
    public double iron;
    public double zinc;
    public double sodium;
    public double cholesterol;
    public double magnesium;

    // OPTIONAL thêm vitamin C / beta-caroten / transfat nếu có
    public double vitaminC;
    public double betaCaroten;
    public double transfat;

    public Food() {
    }

    // ==== Getter dùng cho Adapter và DetailActivity ====

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName == null || englishName.isEmpty()
                ? name            // fallback tiếng Việt nếu không có English
                : englishName;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public double getVitaminA() {
        return vitaminA;
    }

    public double getPotassium() {
        return potassium;
    }

    public double getMufaPufa() {
        return mufaPufa;
    }

    public double getCalcium() {
        return calcium;
    }

    public double getIron() {
        return iron;
    }

    public double getZinc() {
        return zinc;
    }

    public double getSodium() {
        return sodium;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public double getVitaminC() {
        return vitaminC;
    }

    public double getBetaCaroten() {
        return betaCaroten;
    }

    public double getTransfat() {
        return transfat;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
