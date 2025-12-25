package com.example.smartmealaii.activity.food;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smartmealaii.R;
import com.example.smartmealaii.model.Food;

import java.util.Locale;

public class FoodDetailActivity extends AppCompatActivity {

    ImageView img,btnBack;
    TextView name, cal, protein, fat, carb;
    TextView ca, iron, zinc, sodium, mag,  vitA, potassium, mufa;

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

//        chol = findViewById(R.id.tvDetailCholesterol);
        vitA = findViewById(R.id.tvDetailVitaminA);
        potassium = findViewById(R.id.tvDetailPotassium);
        mufa = findViewById(R.id.tvDetailMufaPufa);

        Food f = (Food) getIntent().getSerializableExtra("food_data");

        if (f == null) return;

        name.setText(f.getName());
        cal.setText(String.format(Locale.getDefault(), "%.0f cal", f.getCalories()));
        protein.setText("Protein\n" + f.getProtein());
        fat.setText("Fat\n" + f.getFat());
        carb.setText("Carbs\n" + f.getCarbs());

        ca.setText("" + f.getCalcium());
        iron.setText("" + f.getIron());
        zinc.setText("" + f.getZinc());
        sodium.setText("" + f.getSodium());
        mag.setText("" + f.getMagnesium());
//        chol.setText("" + f.getCholesterol());
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
        String imageUrl = f.getImage();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_food_placeholder)
                    .error(R.drawable.ic_food_placeholder)
                    .into(img);
        }




    }
}
