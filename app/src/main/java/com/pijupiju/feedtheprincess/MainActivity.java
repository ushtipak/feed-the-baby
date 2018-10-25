package com.pijupiju.feedtheprincess;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MealDialog.MealDialogListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    enum MealType {
        LEFT_BOOB,
        RIGHT_BOOB,
        BABY_FOOD
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        initViews();
    }

    private void initViews() {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rvMeals);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(this, MealStorage.getInstance().getMealList(this));
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealDialog mealDialog = new MealDialog();
                Bundle args = new Bundle();
                args.putString("nextMeal", MealStorage.getInstance().getNextMeal());
                args.putString("etDetail", "");
                args.putString("etType", "");
                args.putString("id", "");
                mealDialog.setArguments(args);
                mealDialog.show(getSupportFragmentManager(), "Meal Dialog");
            }
        });
    }

    @Override
    public void setMeal(String mealTime, MealType mealType, Integer mealMl, String id, Boolean updated) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        Log.d(TAG, "setMeal - mealTime: " + mealTime + ", mealType: " + mealType + ", mealMl: " + mealMl + ", id:" + id + ", updated: " + updated);

        String mealDetail;
        if (mealType.equals(MealType.BABY_FOOD)) {
            mealDetail = mealTime + " (" + mealMl + " ml)";
        } else {
            mealDetail = mealTime;
        }

        if (updated) {
            for (Meal meal : MealStorage.getInstance().mealList) {
                if (meal.getId().equals(id)) {
                    meal.setMealType(mealType);
                    meal.setMealDetail(mealDetail);
                }
            }
        } else {
            MealStorage.getInstance().mealList.add(new Meal(mealType, mealDetail, id));
        }
        while (MealStorage.getInstance().mealList.size() > 6) {
            MealStorage.getInstance().saveHistoricalData(this, MealStorage.getInstance().mealList.get(0));
            MealStorage.getInstance().mealList.remove(0);
        }
        MealStorage.getInstance().saveMeals(this);
        adapter.notifyDataSetChanged();
    }

}
