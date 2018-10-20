package com.pijupiju.feedthebaby;

enum MealType {
    LEFT_BOOB,
    RIGHT_BOOB,
    BABY_FOOD
}

public class Meal {
    private MealType mealType;
    private String mealDetail;

    public Meal(MealType mealType, String mealDetail) {
        this.mealType = mealType;
        this.mealDetail = mealDetail;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getMealDetail() {
        return mealDetail;
    }

    public void setMealDetail(String mealDetail) {
        this.mealDetail = mealDetail;
    }
}
