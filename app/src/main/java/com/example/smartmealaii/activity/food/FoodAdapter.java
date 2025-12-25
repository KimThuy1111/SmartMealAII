package com.example.smartmealaii.activity.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmealaii.R;
import com.example.smartmealaii.model.Food;

import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context context;
    private List<Food> foodList;
    private OnFoodActionListener listener;

    // =====================
    // INTERFACE
    // =====================
    public interface OnFoodActionListener {
        void onAddFood(Food food);
    }

    // =====================
    // CONSTRUCTOR
    // =====================
    public FoodAdapter(Context context, List<Food> foodList, OnFoodActionListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.tvName.setText(food.getName());
        holder.tvCalories.setText(
                String.format(Locale.getDefault(), "%.0f cal", food.getCalories())
        );

        if (food.getImage() != null && !food.getImage().isEmpty()) {
            Glide.with(context)
                    .load(food.getImage())
                    .placeholder(R.drawable.ic_food_placeholder)
                    .error(R.drawable.ic_food_placeholder)
                    .into(holder.imgFood);
        } else {
            holder.imgFood.setImageResource(R.drawable.ic_food_placeholder);
        }

        // 👉 CLICK DẤU +
        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddFood(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood, btnAdd;
        TextView tvName, tvCalories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
