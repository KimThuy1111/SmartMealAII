package com.example.smartmealaii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartmealaii.model.Food;
import com.example.smartmealaii.repository.FoodRepository;

import java.util.ArrayList;

public class SearchFoodActivity extends AppCompatActivity {

    ImageView btnBack;
    EditText edt;
    RecyclerView rcv;
    ArrayList<Food> data = new ArrayList<>();

    FoodAdapter adp;
    FoodRepository repo;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.activity_search_food);

        edt = findViewById(R.id.edtSearch);
        rcv = findViewById(R.id.rcvFood);
        btnBack = findViewById(R.id.btnBack);
        // 2. Xử lý sự kiện Click cho nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hàm finish() sẽ đóng Activity hiện tại
                // và quay về Activity trước đó (MainActivity)
                finish();
            }
        });

        repo = FoodRepository.getInstance(this);

        adp = new FoodAdapter(this, data, f -> {

            Intent i = new Intent(this, FoodDetailActivity.class);
            i.putExtra("food_data", f);
            startActivity(i);
        });


        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adp);

        loadTop();

        edt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() == 0) loadTop();
                else search(s.toString());
            }
        });
    }

    void loadTop() {
        data.clear();
        data.addAll(repo.getTopFoods(10));
        adp.notifyDataSetChanged();
    }

    void search(String k) {
        data.clear();
        data.addAll(repo.searchFood(k));
        adp.notifyDataSetChanged();
    }
}
