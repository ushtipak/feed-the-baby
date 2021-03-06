package com.pijupiju.feedtheprincess;


import java.io.Serializable;

public class Meal implements Serializable {
    private MainActivity.MealType mealType;
    private String mealDetail;
    private String id;

    Meal(MainActivity.MealType mealType, String mealDetail, String id) {
        this.mealType = mealType;
        this.mealDetail = mealDetail;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    MainActivity.MealType getMealType() {
        return mealType;
    }

    void setMealType(MainActivity.MealType mealType) {
        this.mealType = mealType;
    }

    String getMealDetail() {
        return mealDetail;
    }

    void setMealDetail(String mealDetail) {
        this.mealDetail = mealDetail;
    }
}
