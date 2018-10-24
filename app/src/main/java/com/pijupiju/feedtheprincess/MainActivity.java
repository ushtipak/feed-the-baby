package com.pijupiju.feedtheprincess;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MealDialog.MealDialogListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<Meal> mealList = new ArrayList<>();
    MyRecyclerViewAdapter adapter;

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
        adapter = new MyRecyclerViewAdapter(this, getMeals());
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealDialog mealDialog = new MealDialog();
                mealDialog.show(getSupportFragmentManager(), "Meal Dialog");
            }
        });
    }

    public List<Meal> getMeals() {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        mealList.add(new Meal(MealType.LEFT_BOOB, "17:14", "2018-10-24-178"));
        mealList.add(new Meal(MealType.RIGHT_BOOB, "17:41", "2018-10-24-221"));
        mealList.add(new Meal(MealType.BABY_FOOD, "19:51 (45 ml)", "2018-10-24-377"));
        mealList.add(new Meal(MealType.RIGHT_BOOB, "20:02", "2018-10-24-420"));
        mealList.add(new Meal(MealType.LEFT_BOOB, "20:17", "2018-10-24-618"));
        mealList.add(new Meal(MealType.RIGHT_BOOB, "22:12", "2018-10-24-933"));

        return mealList;
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
            for (Meal meal: mealList) {
                if (meal.getId().equals(id)) {
                    meal.setMealType(mealType);
                    meal.setMealDetail(mealDetail);
                }
            }
        } else {
            mealList.add(new Meal(mealType, mealDetail, id));
        }
        while (mealList.size() > 6) {
            mealList.remove(0);
        }
        adapter.notifyDataSetChanged();
    }

}
