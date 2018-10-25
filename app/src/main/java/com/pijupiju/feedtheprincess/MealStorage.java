package com.pijupiju.feedtheprincess;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MealStorage {
    private final static String TAG = MealStorage.class.getSimpleName();
    private static MealStorage instance;

    public static MealStorage getInstance() {
        if (instance == null)
            instance = new MealStorage();
        return instance;
    }

    public List<Meal> mealList = new ArrayList<>();

    public List<Meal> getMealList(Context context) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        String fileName = "testing-meals-3";
        FileInputStream fileInputStream;
        try {
            fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            mealList = (List<Meal>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        Log.d(TAG, "mealList: " + String.valueOf(mealList));
        return mealList;
    }

    public void saveMeals(Context context) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        String fileName = "testing-meals-3";
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(mealList);
            objectOutputStream.close();
            fileOutputStream.close();
            Log.d(TAG, "mealList: " + String.valueOf(mealList));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public String getNextMeal() {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        Log.d(TAG, String.valueOf(mealList));

        try {
            MainActivity.MealType lastMeal = mealList.get(mealList.size() - 1).getMealType();
            MainActivity.MealType oneBeforeMeal = mealList.get(mealList.size() - 2).getMealType();

            if (lastMeal.equals(MainActivity.MealType.RIGHT_BOOB) && oneBeforeMeal.equals(MainActivity.MealType.RIGHT_BOOB) ||
                    (lastMeal.equals(MainActivity.MealType.LEFT_BOOB) && oneBeforeMeal.equals(MainActivity.MealType.RIGHT_BOOB))) {
                return "LEFT_BOOB";
            }
            if (lastMeal.equals(MainActivity.MealType.LEFT_BOOB) && oneBeforeMeal.equals(MainActivity.MealType.LEFT_BOOB) ||
                    lastMeal.equals(MainActivity.MealType.RIGHT_BOOB) && oneBeforeMeal.equals(MainActivity.MealType.LEFT_BOOB)) {
                Log.d("INSPECT", "LEFT_BOOB + LEFT_BOOB = RIGHT_BOOB");
                return "RIGHT_BOOB";
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return "";
    }


}
