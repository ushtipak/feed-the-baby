package com.pijupiju.feedthebaby;


public class Meal {
    private MainActivity.MealType mealType;
    private String mealDetail;

    public Meal(MainActivity.MealType mealType, String mealDetail) {
        this.mealType = mealType;
        this.mealDetail = mealDetail;
    }

    public MainActivity.MealType getMealType() {
        return mealType;
    }

    public void setMealType(MainActivity.MealType mealType) {
        this.mealType = mealType;
    }

    public String getMealDetail() {
        return mealDetail;
    }

    public void setMealDetail(String mealDetail) {
        this.mealDetail = mealDetail;
    }
}
