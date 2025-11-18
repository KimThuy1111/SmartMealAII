package com.example.smartmealaii;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText emailRegister, passwordRegister, confirmPassword, nameInput;
    Button btnNext;
    FirebaseAuth mAuth;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ
        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        confirmPassword = findViewById(R.id.confirmPassword);
        nameInput = findViewById(R.id.nameInput);
        btnNext = findViewById(R.id.btnRegister);
        tvLogin =  findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        mAuth = FirebaseAuth.getInstance();

        btnNext.setOnClickListener(v -> {
            String email = emailRegister.getText().toString().trim();
            String password = passwordRegister.getText().toString().trim();
            String confirm = confirmPassword.getText().toString().trim();
            String name = nameInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(result -> {
                        String uid = result.getUser().getUid();

                        // Chuyển sang Step 2
                        Intent intent = new Intent(this, RegisterStep2Activity.class);
                        intent.putExtra("user_uid", uid);
                        intent.putExtra("user_email", email);
                        intent.putExtra("user_name", name);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Lỗi đăng ký: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        });
    }
}
