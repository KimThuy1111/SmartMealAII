package com.example.smartmealaii.activity.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmealaii.R;
import com.example.smartmealaii.activity.FooterNavigator;
import com.example.smartmealaii.activity.food.SearchFoodActivity;
import com.example.smartmealaii.model.Food;
import com.example.smartmealaii.model.FoodDiary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FoodDiaryActivity extends Activity {

    RecyclerView rcvBreakfast, rcvLunch, rcvDinner;

    FoodDiaryAdapter adpBreakfast, adpLunch, adpDinner;

    List<FoodDiary> breakfastList = new ArrayList<>();
    List<FoodDiary> lunchList = new ArrayList<>();
    List<FoodDiary> dinnerList = new ArrayList<>();

    FirebaseFirestore db;
    String uid;
    String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        
        // INIT
        
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        today = LocalDate.now().toString();

        
        // BIND RECYCLER VIEW
        
        rcvBreakfast = findViewById(R.id.rcvBreakfast);
        rcvLunch = findViewById(R.id.rcvLunch);
        rcvDinner = findViewById(R.id.rcvDinner);

        setupRecycler(rcvBreakfast);
        setupRecycler(rcvLunch);
        setupRecycler(rcvDinner);

        adpBreakfast = new FoodDiaryAdapter(this, breakfastList, null);
        adpLunch = new FoodDiaryAdapter(this, lunchList, null);
        adpDinner = new FoodDiaryAdapter(this, dinnerList, null);

        rcvBreakfast.setAdapter(adpBreakfast);
        rcvLunch.setAdapter(adpLunch);
        rcvDinner.setAdapter(adpDinner);

        
        // LOAD DATA
        
        loadDiaryByDate();

        
        // ADD BUTTON
        
        ImageView btnAddMeal = findViewById(R.id.btnAddMealRound);
        btnAddMeal.setOnClickListener(v ->
                startActivity(new Intent(this, SearchFoodActivity.class))
        );

        FooterNavigator.setup(this, FooterNavigator.TAB_DIARY);
    }

    private void setupRecycler(RecyclerView rcv) {
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setNestedScrollingEnabled(false);
    }

    
    // LOAD FOOD DIARY & CHIA BUỔI
    
    private void loadDiaryByDate() {

        breakfastList.clear();
        lunchList.clear();
        dinnerList.clear();

        db.collection("food_diary")
                .whereEqualTo("userId", uid)
                .whereEqualTo("date", today)
                .get()
                .addOnSuccessListener(snapshot -> {

                    snapshot.forEach(doc -> {
                        String foodId = doc.getString("foodId");
                        String meal = doc.getString("meal");

                        db.collection("food")
                                .document(foodId)
                                .get()
                                .addOnSuccessListener(foodDoc -> {

                                    Food food = foodDoc.toObject(Food.class);
                                    if (food == null) return;

                                    FoodDiary item = new FoodDiary();
                                    item.setFoodId(foodId);
                                    item.setMeal(meal);
                                    item.setDate(today);
                                    item.setName(food.getName());
                                    item.setImage(food.getImage());
                                    item.setCalories(food.getCalories());

                                    switch (meal) {
                                        case "breakfast":
                                            breakfastList.add(item);
                                            adpBreakfast.notifyDataSetChanged();
                                            break;
                                        case "lunch":
                                            lunchList.add(item);
                                            adpLunch.notifyDataSetChanged();
                                            break;
                                        case "dinner":
                                            dinnerList.add(item);
                                            adpDinner.notifyDataSetChanged();
                                            break;
                                    }
                                });
                    });
                });
    }
}
