package com.example.smartmealaii.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmealaii.MainActivity;
import com.example.smartmealaii.R;
import com.example.smartmealaii.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText emailLogin, passwordLogin;
    Button btnLogin;
    TextView tvSignUp;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Kiểm tra nếu đã đăng nhập rồi thì vào luôn MainActivity
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(uid).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user_name", user.getName());
                            intent.putExtra("user_email", user.getEmail());
                            intent.putExtra("user_goal", user.getGoal());
                            startActivity(intent);
                            finish();
                        }
                    });
        }


        // Ánh xạ view
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        progressBar = findViewById(R.id.progressBar);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(v -> loginUser());
        tvSignUp.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void loginUser() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);

        // Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = mAuth.getCurrentUser().getUid();

                    // Lấy thông tin người dùng từ Firestore
                    db.collection("users").document(uid).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    User user = documentSnapshot.toObject(User.class);

                                    Toast.makeText(this, "Chào mừng " + user.getName() + "!", Toast.LENGTH_SHORT).show();

                                    // Gửi user qua MainActivity
                                    Intent intent = new Intent(this, MainActivity.class);
                                    intent.putExtra("user_name", user.getName());
                                    intent.putExtra("user_email", user.getEmail());
                                    intent.putExtra("user_goal", user.getGoal());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_LONG).show();
                                }
                                showLoading(false);
                            })
                            .addOnFailureListener(e -> {
                                showLoading(false);
                                Toast.makeText(this, "Lỗi Firestore: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                })
                .addOnFailureListener(e -> {
                    showLoading(false);
                    Toast.makeText(this, "Đăng nhập thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? ProgressBar.VISIBLE : ProgressBar.GONE);
        btnLogin.setEnabled(!isLoading);
    }
}
