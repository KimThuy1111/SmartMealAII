package com.example.smartmealaii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartmealaii.api.PexelsAPI;
import com.example.smartmealaii.model.Food;

import java.util.Locale;

public class FoodDetailActivity extends AppCompatActivity {

    ImageView img,btnBack;
    TextView name, cal, protein, fat, carb;
    TextView ca, iron, zinc, sodium, mag, chol, vitA, potassium, mufa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        img = findViewById(R.id.imgDetailFood);
        name = findViewById(R.id.tvDetailName);
        cal = findViewById(R.id.tvDetailCalories);
        protein = findViewById(R.id.tvDetailProtein);
        fat = findViewById(R.id.tvDetailFat);
        carb = findViewById(R.id.tvDetailCarb);

        ca = findViewById(R.id.tvDetailCalcium);
        iron = findViewById(R.id.tvDetailIron);
        zinc = findViewById(R.id.tvDetailZinc);
        sodium = findViewById(R.id.tvDetailSodium);
        mag = findViewById(R.id.tvDetailMagnesium);

        chol = findViewById(R.id.tvDetailCholesterol);
        vitA = findViewById(R.id.tvDetailVitaminA);
        potassium = findViewById(R.id.tvDetailPotassium);
        mufa = findViewById(R.id.tvDetailMufaPufa);

        Food f = (Food) getIntent().getSerializableExtra("food_data");

        if (f == null) return;

        name.setText(f.getName());
        cal.setText(String.format(Locale.getDefault(), "%.0f cal", f.getCalories()));
        protein.setText("" + f.getProtein());
        fat.setText("" + f.getFat());
        carb.setText("" + f.getCarbohydrate());

        ca.setText("" + f.getCalcium());
        iron.setText("" + f.getIron());
        zinc.setText("" + f.getZinc());
        sodium.setText("" + f.getSodium());
        mag.setText("" + f.getMagnesium());
        chol.setText("" + f.getCholesterol());
        vitA.setText("" + f.getVitaminA());
        potassium.setText("" + f.getPotassium());
        mufa.setText("" + f.getMufaPufa());

        btnBack = findViewById(R.id.btnBack);
        // 2. Xử lý sự kiện Click cho nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hàm finish() sẽ đóng Activity hiện tại
                // và quay về Activity trước đó (SearchFoodActivity)
                finish();
            }
        });

        // Load ảnh
        String keyword = f.getEnglishName();
        if (keyword == null || keyword.trim().isEmpty())
            keyword = f.getName();

        PexelsAPI.searchImage(this, keyword, new PexelsAPI.ImageCallback() {
            @Override
            public void onSuccess(String url) {
                Glide.with(FoodDetailActivity.this)
                        .load(url)
                        .placeholder(R.drawable.ic_food_placeholder)
                        .into(img);
            }

            @Override
            public void onError() { }
        });
    }
}
