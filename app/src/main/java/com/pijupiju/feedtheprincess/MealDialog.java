package com.pijupiju.feedtheprincess;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MealDialog extends AppCompatDialogFragment {
    private final static String TAG = MealDialog.class.getSimpleName();
    private MealDialogListener listener;
    Button btnLeft;
    Button btnRight;
    Button btnBottle;
    EditText etTime;
    EditText etMl;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.MyDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_meal, null);

        btnLeft = view.findViewById(R.id.btnLeft);
        btnRight = view.findViewById(R.id.btnRight);
        btnBottle = view.findViewById(R.id.btnBottle);
        etTime = view.findViewById(R.id.etTime);
        etMl = view.findViewById(R.id.etMl);

        String mealType = "";
        String mealDetail = "";
        String id = "";
        String nextMeal = "";
        final Boolean updated;
        if (getArguments() != null) {
            nextMeal = getArguments().getString("nextMeal");
            mealType = getArguments().getString("etType");
            mealDetail = getArguments().getString("etDetail");
            id = getArguments().getString("id");
        }
        Log.d(TAG, "nextMeal: " + nextMeal + ", mealType: " + mealType + ", mealDetail: " + mealDetail + ", id: " + id);
        if (!Objects.equals(mealDetail, "")) {
            updated = true;
            assert mealType != null;
            switch (mealType) {
                case "LEFT_BOOB":
                    btnLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    break;
                case "RIGHT_BOOB":
                    btnRight.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    break;
                case "BABY_FOOD":
                    btnBottle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    break;
            }
            if (Objects.requireNonNull(mealDetail).length() > 5) {
                etTime.setText(mealDetail.substring(0,5));
                etMl.setText(mealDetail.substring(7,9));
            } else {
                etTime.setText(mealDetail);
            }
        } else {
            updated = false;
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormatForDisplay = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String currentTime = dateFormatForDisplay.format(date);

            etTime.setText(currentTime);

            DateFormat dateFormatForId = new SimpleDateFormat("yyyy-MM-dd-S", Locale.getDefault());
            id = dateFormatForId.format(date);

            if (Objects.requireNonNull(nextMeal).equals("LEFT_BOOB")) {
                btnLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                btnRight.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }

        final String finalId = id;
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mealTime = etTime.getText().toString();


                        MainActivity.MealType mealType = null;
                        Integer selectColor = getResources().getColor(R.color.colorPrimaryDark);

                        if (selectColor.equals(((ColorDrawable) btnLeft.getBackground()).getColor())) {
                            mealType = MainActivity.MealType.LEFT_BOOB;
                        } else if (selectColor.equals(((ColorDrawable) btnRight.getBackground()).getColor())) {
                            mealType = MainActivity.MealType.RIGHT_BOOB;
                        } else if (selectColor.equals(((ColorDrawable) btnBottle.getBackground()).getColor())) {
                            mealType = MainActivity.MealType.BABY_FOOD;
                        }

                        Integer mealMl;
                        String mealMlInput = etMl.getText().toString();
                        if (mealMlInput.equals("")) {
                            mealMl = 0;
                        } else {
                            mealMl = Integer.parseInt(mealMlInput);
                        }

                        listener.setMeal(mealTime, mealType, mealMl, finalId, updated);
                    }
                });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton(btnLeft);
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton(btnRight);
            }
        });
        btnBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton(btnBottle);
            }
        });

        return builder.create();
    }

    private void toggleButton(Button button) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        if (!btnLeft.equals(button)) {
            btnLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (!btnRight.equals(button)) {
            btnRight.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (!btnBottle.equals(button)) {
            btnBottle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        if (button.equals(btnBottle)) {
            etMl.setEnabled(true);
        } else {
            etMl.setEnabled(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Log.d(TAG, "-> " + methodName);

        super.onAttach(context);
        listener = (MealDialogListener) context;
    }

    public interface MealDialogListener {
        void setMeal(String mealTime, MainActivity.MealType mealType, Integer mealMl, String id, Boolean updated);
    }

}
