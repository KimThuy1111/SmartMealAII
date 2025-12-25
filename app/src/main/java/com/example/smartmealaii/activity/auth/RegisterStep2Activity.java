package com.example.smartmealaii.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmealaii.R;
import com.example.smartmealaii.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterStep2Activity extends AppCompatActivity {

    EditText ageInput, weightInput, heightInput;
    Spinner activitySpinner, goalSpinner;
    RadioGroup genderGroup;
    Button btnContinue;
    FirebaseFirestore db;

    String uid, email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        // Ánh xạ View
        genderGroup = findViewById(R.id.genderGroup);
        ageInput = findViewById(R.id.ageInput);
        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        activitySpinner = findViewById(R.id.activitySpinner);
        goalSpinner = findViewById(R.id.goalSpinner);
        btnContinue = findViewById(R.id.btnContinue);

        db = FirebaseFirestore.getInstance();

        // Nhận dữ liệu từ Step 1
        uid = getIntent().getStringExtra("user_uid");
        email = getIntent().getStringExtra("user_email");
        name = getIntent().getStringExtra("user_name");

        // Set dữ liệu cho spinner
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(
                this, R.array.activity_array, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(activityAdapter);

        ArrayAdapter<CharSequence> goalAdapter = ArrayAdapter.createFromResource(
                this, R.array.goal_array, android.R.layout.simple_spinner_item);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSpinner.setAdapter(goalAdapter);

        // Gắn sự kiện
        btnContinue.setOnClickListener(v -> saveUserData());
    }

    private void saveUserData() {
        try {
            int age = Integer.parseInt(ageInput.getText().toString());
            double weight = Double.parseDouble(weightInput.getText().toString());
            double height = Double.parseDouble(heightInput.getText().toString());

            int selectedGenderId = genderGroup.getCheckedRadioButtonId();
            if (selectedGenderId == -1) {
                Toast.makeText(this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedGender = findViewById(selectedGenderId);
            String gender = selectedGender.getText().toString();

            String activity = activitySpinner.getSelectedItem().toString();
            String goal = goalSpinner.getSelectedItem().toString();

            User user = new User(uid, email, name, age, weight, height, gender, activity, goal);

            db.collection("users").document(uid)
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "🎉 Đăng ký hoàn tất!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Lỗi Firestore: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng số!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
