package com.example.smartmealaii;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmealaii.activity.FooterNavigator;
import com.example.smartmealaii.activity.auth.LoginActivity;
import com.example.smartmealaii.activity.food.SearchFoodActivity;
import com.example.smartmealaii.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private TextView tvGreeting, tvCalories, tvGoal, tvBreakfast, tvLunch, tvDinner;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        FooterNavigator.setup(this, FooterNavigator.TAB_HOME);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Nếu chưa đăng nhập quay lại LoginActivity
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        loadUserData();
    }
    private double getActivityFactor(String activity) {

        if (activity == null) return 1.2;

        switch (activity.toLowerCase()) {
            case "low":
            case "ít vận động":
                return 1.2;

            case "medium":
            case "trung bình":
                return 1.55;

            case "high":
            case "nhiều":
                return 1.725;

            default:
                return 1.2;
        }
    }

    private int calculateTDEE(User user) {

        double bmr;

        if (user.getGender().equalsIgnoreCase("male")) {
            bmr = 10 * user.getWeight()
                    + 6.25 * user.getHeight()
                    - 5 * user.getAge()
                    + 5;
        } else {
            bmr = 10 * user.getWeight()
                    + 6.25 * user.getHeight()
                    - 5 * user.getAge()
                    - 161;
        }

        double activityFactor = getActivityFactor(user.getActivity());

        return (int) Math.round(bmr * activityFactor);
    }


    private void loadUserData() {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {

                        User user = doc.toObject(User.class);
                        if (user == null) return;

                        String name = doc.getString("name");
                        String goal = doc.getString("goal");

                        tvGreeting.setText("Chào buổi sáng, " + name + "!");
                        tvGoal.setText("Mục tiêu: " + goal);
                        int tdee = calculateTDEE(user);
                        tvCalories.setText(String.valueOf(tdee));

                        tvBreakfast.setText(doc.contains("breakfast") ? doc.getString("breakfast") : "");
                        tvLunch.setText(doc.contains("lunch") ? doc.getString("lunch") : "");
                        tvDinner.setText(doc.contains("dinner") ? doc.getString("dinner") : "");
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

}
