package com.pijupiju.feedtheprincess;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MealStorage {
    private final static String TAG = MealStorage.class.getSimpleName();

    static List<Meal> getMeals(Context context) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context);

        return RetrieveMealList(context, "meals-active");
    }

    static void addMeal(Context context, Meal targetMeal) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context + "; Meal: " + targetMeal);

        List<Meal> meals = RetrieveMealList(context, "meals-active");
        meals.add(targetMeal);

        while (meals.size() > 6) {
            saveHistoricalData(context, meals.get(0));
            meals.remove(0);
        }

        saveMeals(context, meals);
    }

    static void removeMeal(Context context, Meal targetMeal) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        List<Meal> meals = RetrieveMealList(context, "meals-active");
        for (Meal meal : meals) {
            if (meal.getId().equals(targetMeal.getId())) {
                meals.remove(meal);
                break;
            }
        }
        saveMeals(context, meals);
    }

    static void editMeal(Context context, MainActivity.MealType mealType, String mealDetail, String id) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context + "; MainActivity.MealType: " + mealType + "; (String) mealDetail: " + mealDetail + "; (String) id: " + id);

        List<Meal> meals = RetrieveMealList(context, "meals-active");
        for (Meal meal : meals) {
            if (meal.getId().equals(id)) {
                meal.setMealType(mealType);
                meal.setMealDetail(mealDetail);
            }
        }
        saveMeals(context, meals);
    }

    private static void saveMeals(Context context, List<Meal> meals) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context + "; List<Meal>: " + meals);

        storeMeals(context, "meals-active", meals);
    }

    static private void saveHistoricalData(Context context, Meal targetMeal) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context + "; Meal: " + targetMeal);

        List<Meal> historicalMeals = RetrieveMealList(context, "historical-data");
        historicalMeals.add(targetMeal);

        storeMeals(context, "historical-data", historicalMeals);
    }

    static String getNextMeal(Context context) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context);

        List<Meal> meals = RetrieveMealList(context, "meals-active");
        try {
            MainActivity.MealType lastMeal = meals.get(meals.size() - 1).getMealType();
            MainActivity.MealType oneBeforeMeal = meals.get(meals.size() - 2).getMealType();

            if (lastMeal.equals(MainActivity.MealType.DESNA_SIKA) && oneBeforeMeal.equals(MainActivity.MealType.DESNA_SIKA) ||
                    (lastMeal.equals(MainActivity.MealType.LEVA_SIKA) && oneBeforeMeal.equals(MainActivity.MealType.DESNA_SIKA))) {
                return "LEVA_SIKA";
            }
            if (lastMeal.equals(MainActivity.MealType.LEVA_SIKA) && oneBeforeMeal.equals(MainActivity.MealType.LEVA_SIKA) ||
                    lastMeal.equals(MainActivity.MealType.DESNA_SIKA) && oneBeforeMeal.equals(MainActivity.MealType.LEVA_SIKA)) {
                Log.d("INSPECT", "LEVA_SIKA + LEVA_SIKA = DESNA_SIKA");
                return "DESNA_SIKA";
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return "";
    }

    static private List<Meal> RetrieveMealList(Context context, String fileName) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        List<Meal> meals = new ArrayList<>();
        FileInputStream fileInputStream;
        try {
            fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            meals = (List<Meal>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        Log.d(TAG, "mealList: " + String.valueOf(meals));
        return meals;
    }

    static private void storeMeals(Context context, String fileName, List<Meal> meals) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context + "; (String) fileName: " + fileName + "; List<Meal>: " + meals);

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(meals);
            objectOutputStream.close();
            fileOutputStream.close();
            Log.d(TAG, "List<Meal> [" + fileName + "]: " + String.valueOf(meals));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    static void getStats(Context context) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> Context: " + context);

        List<Meal> allMeals = RetrieveMealList(context, "historical-data");
        allMeals.addAll(RetrieveMealList(context, "meals-active"));

        Calendar cal = Calendar.getInstance();
        Date date;
        String targetDate;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        date = cal.getTime();
        targetDate = df.format(date);
        String statsForToday = getStatsForDate(allMeals, targetDate);

        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        targetDate = df.format(date);
        String statsForYesterday = getStatsForDate(allMeals, targetDate);

        String dailyStats = statsForYesterday + "\n\n" + statsForToday;
        Toast.makeText(context, dailyStats, Toast.LENGTH_LONG).show();

//        String allMealStats = getAllMealStats(allMeals);
//        Toast.makeText(context, allMealStats, Toast.LENGTH_LONG).show();
    }

    static private String getStatsForDate(List<Meal> meals, String targetDate) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> List<Meal>: " + meals + "; (String) targetDate: " + targetDate);

        Integer mealsTotal = 0;
        Integer mealsBottlesCount = 0;
        Integer mealsBottlesInMl = 0;
        for (Meal meal: meals) {
            if (meal.getId().startsWith(targetDate)) {
                mealsTotal++;
                if (meal.getMealDetail().contains("(")) {
                    mealsBottlesCount++;
                    Pattern pattern = Pattern.compile("\\((.*?)\\)");
                    Matcher matcher = pattern.matcher(meal.getMealDetail());
                    if (matcher.find())
                    {
                        mealsBottlesInMl += Integer.parseInt(matcher.group(1).replace(" ml", ""));
                    }
                }
            }
        }

        return "[" +
                targetDate +
                "]\n" +
                "  Total Meals: " +
                mealsTotal +
                "\n" +
                "  Bottled Meals: " +
                mealsBottlesCount +
                "\n" +
                "  Bottled Milk: " +
                mealsBottlesInMl +
                " ml";
    }

    static private String getAllMealStats(List<Meal> meals) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);
        Log.d(TAG, methodName + "-> List<Meal>: " + meals);

        StringBuilder allMealStats = new StringBuilder();
        for (Meal meal : meals) {
            allMealStats.append(meal.getId().substring(0, 9));
            allMealStats.append(", ");
            allMealStats.append(meal.getMealDetail());
            if (!meal.getMealDetail().contains("ml")) {
                allMealStats.append(" ");
                allMealStats.append(meal.getMealType().toString().substring(0, 1));
            }
            allMealStats.append("\n");
        }
        return allMealStats.toString();
    }


}
