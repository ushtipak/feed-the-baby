package com.pijupiju.feedthebaby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;

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
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getMeals());
        recyclerView.setAdapter(adapter);
    }

    public List<Meal> getMeals() {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        List<Meal> mealList = new ArrayList<>();
        mealList.add(new Meal(MealType.LEFT_BOOB, "17:14"));
        mealList.add(new Meal(MealType.RIGHT_BOOB, "17:41"));
        mealList.add(new Meal(MealType.LEFT_BOOB, "19:06"));
        mealList.add(new Meal(MealType.RIGHT_BOOB, "19:22"));
        mealList.add(new Meal(MealType.BABY_FOOD, "19:51 (45 ml)"));

        return mealList;
    }
}
