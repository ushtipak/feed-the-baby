package com.pijupiju.feedthebaby;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = MainActivity.class.getSimpleName();
    private List<String> mealList;

    MyRecyclerViewAdapter(List<String> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new mealItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        mealItem mealItem = (mealItem) holder;
        mealItem.mealTV.setText(mealList.get(position));
        mealItem.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "REMOVE", Toast.LENGTH_SHORT).show();
            }
        });
        mealItem.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "EDIT", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        return mealList.size();
    }

    public static class mealItem extends RecyclerView.ViewHolder {

        TextView mealTV;
        Button btnRemove;
        Button btnEdit;

        mealItem(View itemView) {
            super(itemView);

            mealTV = itemView.findViewById(R.id.tvMeal);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
