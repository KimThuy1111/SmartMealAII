package com.example.smartmealaii.activity.food;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmealaii.R;
import com.example.smartmealaii.activity.FooterNavigator;
import com.example.smartmealaii.model.Food;
import com.example.smartmealaii.repository.FoodRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SearchFoodActivity extends AppCompatActivity {

    // UI
    private ImageView btnBack;
    private EditText edtSearch;
    private RecyclerView rcvFood;

    // Data
    private final ArrayList<Food> data = new ArrayList<>();
    private FoodAdapter adapter;
    private FoodRepository repo;
    private java.util.Locale Locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        // BIND VIEW
        edtSearch = findViewById(R.id.edtSearch);
        rcvFood = findViewById(R.id.rcvFood);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        // INIT
        repo = FoodRepository.getInstance(this);
        FooterNavigator.setup(this, FooterNavigator.TAB_HOME);

        
        // ADAPTER
        
        adapter = new FoodAdapter(this, data, food -> {
            showMealPickerDialog(food);
        });

        rcvFood.setLayoutManager(new LinearLayoutManager(this));
        rcvFood.setAdapter(adapter);

        // LOAD DATA
        loadTopFoods();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() == 0) {
                    loadTopFoods();
                } else {
                    searchFood(s.toString());
                }
            }
        });
    }

    
    // LOAD TOP FOOD
    
    private void loadTopFoods() {
        repo.getTopFoods(10, list -> {
            data.clear();
            data.addAll(list);
            adapter.notifyDataSetChanged();
        });
    }

    
    // SEARCH FOOD
    
    private void searchFood(String keyword) {
        repo.searchFood(keyword, list -> {
            data.clear();
            data.addAll(list);
            adapter.notifyDataSetChanged();
        });
    }

    // SHOW DIALOG CHỌN BUỔI ĂN
    private void showMealPickerDialog(Food food) {

        String[] meals = {"Bữa sáng", "Bữa trưa", "Bữa tối"};
        String[] mealValues = {"breakfast", "lunch", "dinner"};

        new AlertDialog.Builder(this)
                .setTitle("Chọn buổi ăn")
                .setItems(meals, (dialog, which) -> {
                    addFoodToDiary(food, mealValues[which]);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // ADD FOOD TO DIARY (FIRESTORE)
    private void addFoodToDiary(Food food, String meal) {

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> diary = new HashMap<>();
        diary.put("userId", uid);
        diary.put("foodId", food.getId());           // chỉ lưu ID
        diary.put("meal", meal);                     // breakfast / lunch / dinner
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
        diary.put("date", today);
        diary.put("createdAt", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance()
                .collection("food_diary")
                .add(diary)
                .addOnSuccessListener(doc ->
                        Toast.makeText(this, "Đã thêm vào nhật ký", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi khi thêm món", Toast.LENGTH_SHORT).show()
                );
    }
}
