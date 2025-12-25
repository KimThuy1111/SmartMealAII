package com.example.smartmealaii.repository;

import android.content.Context;
import android.util.Log;

import com.example.smartmealaii.model.Food;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodRepository {

    private static FoodRepository instance;
    private FirebaseFirestore db;

    private FoodRepository(Context c) {
        db = FirebaseFirestore.getInstance();
    }

    public static FoodRepository getInstance(Context c) {
        if (instance == null) {
            instance = new FoodRepository(c);
        }
        return instance;
    }

    // Load food
    public void getTopFoods(int limit, FoodCallback callback) {
        db.collection("food")
                .get()
                .addOnSuccessListener(qs -> {
                    List<Food> list = new ArrayList<>();
                    for (QueryDocumentSnapshot d : qs) {
                        Food f = d.toObject(Food.class);
                        f.setId(d.getId());
                        list.add(f);
                    }

                    // random 10 món
                    Collections.shuffle(list);
                    if (list.size() > limit) {
                        list = list.subList(0, limit);
                    }

                    callback.onResult(list);
                })
                .addOnFailureListener(e -> {
                    Log.e("FOOD_DEBUG", "Firestore error", e);
                });
    }

    // Search food theo tên
    public void searchFood(String keyword, FoodCallback callback) {
        db.collection("food")
                .get()
                .addOnSuccessListener(qs -> {
                    List<Food> list = new ArrayList<>();
                    for (QueryDocumentSnapshot d : qs) {
                        Food f = d.toObject(Food.class);
                        if (f.getName() != null &&
                                f.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            f.setId(d.getId());
                            list.add(f);
                        }
                    }
                    callback.onResult(list);
                });
    }

    // CALLBACK
    public interface FoodCallback {
        void onResult(List<Food> list);
    }
}
