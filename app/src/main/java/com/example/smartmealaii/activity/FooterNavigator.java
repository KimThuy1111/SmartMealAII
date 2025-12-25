package com.example.smartmealaii.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartmealaii.MainActivity;
import com.example.smartmealaii.R;
import com.example.smartmealaii.activity.diary.FoodDiaryActivity;
import com.example.smartmealaii.activity.user.UserProfileActivity;

public class FooterNavigator {

    public static final int TAB_HOME = 0;
    public static final int TAB_DIARY = 1;
    public static final int TAB_SUGGEST = 2;
    public static final int TAB_USER = 3;

    public static void setup(
            Activity activity,
            int selectedTab
    ) {

        //  Bind views 
        LinearLayout tabHome = activity.findViewById(R.id.tabHome);
        LinearLayout tabDiary = activity.findViewById(R.id.tabDiary);
        LinearLayout tabSuggest = activity.findViewById(R.id.tabSuggest);
        LinearLayout tabUser = activity.findViewById(R.id.tabUser);

        ImageView iconHome = activity.findViewById(R.id.iconHome);
        ImageView iconDiary = activity.findViewById(R.id.iconDiary);
        ImageView iconSuggest = activity.findViewById(R.id.iconSuggest);
        ImageView iconUser = activity.findViewById(R.id.iconUser);

        TextView txtHome = activity.findViewById(R.id.txtHome);
        TextView txtDiary = activity.findViewById(R.id.txtDiary);
        TextView txtSuggest = activity.findViewById(R.id.txtSuggest);
        TextView txtUser = activity.findViewById(R.id.txtUser);

        //  Reset màu 
        int gray = Color.parseColor("#777777");
        iconHome.setColorFilter(gray);
        iconDiary.setColorFilter(gray);
        iconSuggest.setColorFilter(gray);
        iconUser.setColorFilter(gray);

        txtHome.setTextColor(gray);
        txtDiary.setTextColor(gray);
        txtSuggest.setTextColor(gray);
        txtUser.setTextColor(gray);

        //  Highlight tab hiện tại 
        int green = Color.parseColor("#00C569");
        switch (selectedTab) {
            case TAB_HOME:
                iconHome.setColorFilter(green);
                txtHome.setTextColor(green);
                break;
            case TAB_DIARY:
                iconDiary.setColorFilter(green);
                txtDiary.setTextColor(green);
                break;
            case TAB_SUGGEST:
                iconSuggest.setColorFilter(green);
                txtSuggest.setTextColor(green);
                break;
            case TAB_USER:
                iconUser.setColorFilter(green);
                txtUser.setTextColor(green);
                break;
        }

        //  Click events 
        tabHome.setOnClickListener(v -> {
            if (!(activity instanceof MainActivity)) {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }
        });

        tabDiary.setOnClickListener(v -> {
            if (!(activity instanceof FoodDiaryActivity)) {
                activity.startActivity(new Intent(activity, FoodDiaryActivity.class));
                activity.finish();
            }
        });

        tabUser.setOnClickListener(v -> {
            if (!(activity instanceof UserProfileActivity)) {
                activity.startActivity(new Intent(activity, UserProfileActivity.class));
                activity.finish();
            }
        });

        // tabSuggest (chưa dùng thì để trống)
    }
}
