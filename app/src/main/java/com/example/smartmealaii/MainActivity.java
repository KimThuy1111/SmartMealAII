package com.example.smartmealaii;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView tvWelcome, tvEmail, tvGoal;
    Button btnLogout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        tvWelcome = findViewById(R.id.tvWelcome);
        tvEmail = findViewById(R.id.tvEmail);
        tvGoal = findViewById(R.id.tvGoal);
        btnLogout = findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();

        // Lấy dữ liệu từ intent
        String name = getIntent().getStringExtra("user_name");
        String email = getIntent().getStringExtra("user_email");
        String goal = getIntent().getStringExtra("user_goal");

        tvWelcome.setText("Xin chào, " + name + " 👋");
        tvEmail.setText("Email: " + email);
        tvGoal.setText("Mục tiêu: " + goal);

        // Xử lý logout
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
