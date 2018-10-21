package com.pijupiju.feedthebaby;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(date);

        etTime.setText(currentTime);

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
                        MainActivity.MealType mealType = MainActivity.MealType.RIGHT_BOOB;

                        Integer mealMl;
                        String mealMlInput = etMl.getText().toString();
                        if (mealMlInput.equals("")) {
                            mealMl = 0;
                        } else {
                            mealMl = Integer.parseInt(mealMlInput);
                        }

                        listener.addMeal(mealTime, mealType, mealMl);
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
        void addMeal(String mealTime, MainActivity.MealType mealType, Integer mealMl);
    }

}
