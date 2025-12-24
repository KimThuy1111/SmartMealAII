package com.example.smartmealaii.repository;

import android.content.Context;
import android.util.Log;

import com.example.smartmealaii.model.Food;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class FoodRepository {

    private static FoodRepository instance;
    private final List<Food> list = new ArrayList<>();

    private String loadJSON(Context c) {
        try {
            InputStream is = c.getAssets().open("foods.json");
            byte[] b = new byte[is.available()];
            is.read(b);
            is.close();
            return new String(b, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private void parse(String json) {
        try {
            JSONArray arr = new JSONArray(json);
            list.clear();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                Food f = new Food();

                // Gán thủ công bằng reflection? Không. Gán từng field
                f.name = o.optString("Name");
                f.englishName = o.optString("English Name");

                f.calories = o.optDouble("Calories", 0);
                f.protein = o.optDouble("Chất đạm", 0);
                f.fat = o.optDouble("Chất béo", 0);
                f.carbohydrate = o.optDouble("Carbohydrate", 0);

                f.vitaminA = o.optDouble("Vitamin A", 0);
                f.potassium = o.optDouble("Potassium", 0);
                f.mufaPufa = o.optDouble("MUFA+PUFA", 0);
                f.calcium = o.optDouble("Canxi", 0);
                f.iron = o.optDouble("Sắt", 0);
                f.zinc = o.optDouble("Kẽm", 0);
                f.sodium = o.optDouble("Natri", 0);
                f.cholesterol = o.optDouble("Cholesterol", 0);
                f.magnesium = o.optDouble("Magie", 0);
                f.setImage(o.getString("image"));

                list.add(f);
            }

        } catch (Exception e) {
            Log.e("ERR", "Parse JSON lỗi", e);
        }
    }

    public static FoodRepository getInstance(Context c) {
        if (instance == null) {
            instance = new FoodRepository();

            String json = instance.loadJSON(c);
            instance.parse(json);
        }
        return instance;
    }

    private String normalize(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(temp)
                .replaceAll("")
                .replace("đ", "d");
    }

    public List<Food> searchFood(String key) {
        key = normalize(key.toLowerCase());

        List<Food> out = new ArrayList<>();
        for (Food f : list) {
            String v1 = normalize(f.getName().toLowerCase());
            String v2 = normalize(f.getEnglishName().toLowerCase());

            if (v1.contains(key) || v2.contains(key)) out.add(f);
        }
        return out;
    }

    public List<Food> getTopFoods(int k) {
        List<Food> temp = new ArrayList<>(list);
        Collections.shuffle(temp);
        return temp.subList(0, Math.min(k, temp.size()));
    }
}
