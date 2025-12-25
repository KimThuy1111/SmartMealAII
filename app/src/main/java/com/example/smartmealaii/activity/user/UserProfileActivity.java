package com.example.smartmealaii.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmealaii.R;
import com.example.smartmealaii.activity.FooterNavigator;
import com.example.smartmealaii.activity.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {

    // UI
    private ImageView imgAvatar;
    private TextView tvProfileName, tvProfileEmail;

    private LinearLayout btnMyGoals, btnSettings, btnLogout;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile); // ← đúng tên layout XML

        // INIT
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // BIND VIEW
        imgAvatar = findViewById(R.id.imgAvatar);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);

        btnMyGoals = findViewById(R.id.btnMyGoals);
        btnSettings = findViewById(R.id.btnSettings);
        btnLogout = findViewById(R.id.btnLogout);

        // Check login
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Load user data
        loadUserProfile();

        // Event
        setupActions();

        FooterNavigator.setup(this, FooterNavigator.TAB_USER);
    }

    // Load user info
    private void loadUserProfile() {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String name = doc.getString("name");
                        String email = doc.getString("email");

                        tvProfileName.setText(name != null ? name : "Người dùng");
                        tvProfileEmail.setText(email != null ? email : "");
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi tải hồ sơ", Toast.LENGTH_SHORT).show()
                );
    }

    // BUTTON ACTIONS
    private void setupActions() {

        // Mục tiêu sức khỏe
        btnMyGoals.setOnClickListener(v -> {
            Toast.makeText(this, "Mục tiêu sức khỏe (chưa làm)", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, MyGoalsActivity.class));
        });

        // Cài đặt ứng dụng
        btnSettings.setOnClickListener(v -> {
            Toast.makeText(this, "Cài đặt ứng dụng (chưa làm)", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, SettingsActivity.class));
        });

        // Đăng xuất
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
