package com.example.smartmealaii.activity.diary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmealaii.R;
import com.example.smartmealaii.activity.food.FoodDetailActivity;
import com.example.smartmealaii.model.Food;
import com.example.smartmealaii.model.FoodDiary;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Locale;

public class FoodDiaryAdapter extends RecyclerView.Adapter<FoodDiaryAdapter.ViewHolder> {

    private Context context;
    private List<FoodDiary> foodList;

    public FoodDiaryAdapter(Context context, List<FoodDiary> foodList, Object ignored) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_food_diary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDiary item = foodList.get(position);

        holder.tvName.setText(item.getName());
        holder.tvCalories.setText(
                String.format(Locale.getDefault(), "%.0f cal", item.getCalories())
        );

        if (item.getImage() != null && !item.getImage().isEmpty()) {
            Glide.with(context)
                    .load(item.getImage())
                    .placeholder(R.drawable.ic_food_placeholder)
                    .error(R.drawable.ic_food_placeholder)
                    .into(holder.imgFood);
        } else {
            holder.imgFood.setImageResource(R.drawable.ic_food_placeholder);
        }

        
        // CLICK → OPEN FOOD DETAIL
        
        holder.itemView.setOnClickListener(v -> {
            openFoodDetail(item.getFoodId());
        });
    }

    private void openFoodDetail(String foodId) {
        FirebaseFirestore.getInstance()
                .collection("food")
                .document(foodId)
                .get()
                .addOnSuccessListener(doc -> {
                    Food food = doc.toObject(Food.class);
                    if (food == null) return;

                    Intent intent = new Intent(context, FoodDetailActivity.class);
                    intent.putExtra("food_data", food);
                    context.startActivity(intent);
                });
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView tvName, tvCalories;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvCalories = itemView.findViewById(R.id.tvCalories);
        }
    }
}
