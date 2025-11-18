package com.example.smartmealaii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private TextView tvGreeting, tvCalories, tvGoal, tvBreakfast, tvLunch, tvDinner;

    // Footer navigation
    private LinearLayout tabHome, tabDiary, tabSuggest, tabUser;
    private ImageView iconHome, iconDiary, iconSuggest, iconUser;
    private TextView txtHome, txtDiary, txtSuggest, txtUser;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Thêm ở đầu onCreate
        ImageView btnAddMeal = findViewById(R.id.btnAddMealRound);

// Khi bấm dấu +
        btnAddMeal.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchFoodActivity.class);
            startActivity(intent);
        });


        // Ánh xạ nội dung
        tvGreeting = findViewById(R.id.tvGreeting);
        tvCalories = findViewById(R.id.tvCalories);
        tvGoal = findViewById(R.id.tvGoal);
        tvBreakfast = findViewById(R.id.tvBreakfast);
        tvLunch = findViewById(R.id.tvLunch);
        tvDinner = findViewById(R.id.tvDinner);

        // Ánh xạ FOOTER
        tabHome = findViewById(R.id.tabHome);
        tabDiary = findViewById(R.id.tabDiary);
        tabSuggest = findViewById(R.id.tabSuggest);
        tabUser = findViewById(R.id.tabUser);

        iconHome = findViewById(R.id.iconHome);
        iconDiary = findViewById(R.id.iconDiary);
        iconSuggest = findViewById(R.id.iconSuggest);
        iconUser = findViewById(R.id.iconUser);

        txtHome = findViewById(R.id.txtHome);
        txtDiary = findViewById(R.id.txtDiary);
        txtSuggest = findViewById(R.id.txtSuggest);
        txtUser = findViewById(R.id.txtUser);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Nếu chưa đăng nhập → quay lại LoginActivity
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        highlightSelectedTab(0); // chọn Home mặc định
        setupFooterNavigation();
        loadUserData();
    }

    private void loadUserData() {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {

                        String name = doc.getString("name");
                        String goal = doc.getString("goal");

                        tvGreeting.setText("Chào buổi sáng, " + name + "!");
                        tvGoal.setText("Mục tiêu: " + goal);
                        tvCalories.setText("1450"); // sau này tính tự động

                        tvBreakfast.setText(doc.contains("breakfast") ? doc.getString("breakfast") : "");
                        tvLunch.setText(doc.contains("lunch") ? doc.getString("lunch") : "");
                        tvDinner.setText(doc.contains("dinner") ? doc.getString("dinner") : "");
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void setupFooterNavigation() {

        tabHome.setOnClickListener(v -> {
            highlightSelectedTab(0);
            // đang ở trang Home → không chuyển Activity
        });

//        tabDiary.setOnClickListener(v -> {
//            highlightSelectedTab(1);
//            startActivity(new Intent(this, DiaryActivity.class));
//        });
//
//        tabSuggest.setOnClickListener(v -> {
//            highlightSelectedTab(2);
//            startActivity(new Intent(this, SuggestActivity.class));
//        });
//
//        tabUser.setOnClickListener(v -> {
//            highlightSelectedTab(3);
//            startActivity(new Intent(this, UserActivity.class));
//        });
    }

    // Đổi màu icon + text khi chọn tab
    private void highlightSelectedTab(int index) {

        resetTabs(); // reset về màu xám

        switch (index) {
            case 0:
                iconHome.setColorFilter(Color.parseColor("#00C569"));
                txtHome.setTextColor(Color.parseColor("#00C569"));
                break;
            case 1:
                iconDiary.setColorFilter(Color.parseColor("#00C569"));
                txtDiary.setTextColor(Color.parseColor("#00C569"));
                break;
            case 2:
                iconSuggest.setColorFilter(Color.parseColor("#00C569"));
                txtSuggest.setTextColor(Color.parseColor("#00C569"));
                break;
            case 3:
                iconUser.setColorFilter(Color.parseColor("#00C569"));
                txtUser.setTextColor(Color.parseColor("#00C569"));
                break;
        }
    }

    private void resetTabs() {
        int defaultColor = Color.parseColor("#777777");

        iconHome.setColorFilter(defaultColor);
        iconDiary.setColorFilter(defaultColor);
        iconSuggest.setColorFilter(defaultColor);
        iconUser.setColorFilter(defaultColor);

        txtHome.setTextColor(defaultColor);
        txtDiary.setTextColor(defaultColor);
        txtSuggest.setTextColor(defaultColor);
        txtUser.setTextColor(defaultColor);
    }
}
