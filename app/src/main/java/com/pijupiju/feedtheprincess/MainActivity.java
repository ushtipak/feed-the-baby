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
        LEVA_SIKA,
        DESNA_SIKA,
        DOHRANICA
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
        adapter = new MyRecyclerViewAdapter(this, MealStorage.getMeals(this));
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealDialog mealDialog = new MealDialog();
                Bundle args = new Bundle();
                args.putString("nextMeal", MealStorage.getNextMeal(getApplicationContext()));
                args.putString("etDetail", "");
                args.putString("etType", "");
                args.putString("id", "");
                mealDialog.setArguments(args);
                mealDialog.show(getSupportFragmentManager(), "Meal Dialog");
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MealStorage.getStats(getApplicationContext());
                return true;
            }
        });
    }

    @Override
    public void setMeal(String mealTime, MealType mealType, Integer mealMl, String id, Boolean updated) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        Log.d(TAG, "setMeal - (String) mealTime: " + mealTime + ", MealType: " + mealType + ", (Integer) mealMl: " + mealMl + ", (String) id:" + id + ", (Boolean) updated: " + updated);

        String mealDetail;
        if (mealType.equals(MealType.DOHRANICA)) {
            mealDetail = mealTime + " (" + mealMl + " ml)";
        } else {
            mealDetail = mealTime;
        }

        if (!updated) {
            MealStorage.addMeal(getApplicationContext(), new Meal(mealType, mealDetail, id));
        } else {
            MealStorage.editMeal(getApplicationContext(), mealType, mealDetail, id);
        }
        initViews();
    }

}
