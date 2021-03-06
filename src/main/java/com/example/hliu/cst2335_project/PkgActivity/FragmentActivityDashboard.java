package com.example.hliu.cst2335_project.PkgActivity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hliu.cst2335_project.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActivityDashboard extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout setDailyGoal;
    private TextView textView_dailyGoal1, textView_dailyGoal2, textView_todayTime, textView_thisMonth, textView_lastMonth;
    private EditText editText_setDailyGoal;
    private Dialog dialog_setDailyGoal;
    private ImageView setDailyGoalCheck;
    private ProgressBar progressBar;

    private String year, month, day;

    private ActivityDatabaseHelper databaseHelper;

    public FragmentActivityDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activity_dashboard, container, false);
        setDailyGoal = view.findViewById(R.id.activity_dashboard_setdailygoal);
        setDailyGoal.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView_dailyGoal1 = view.findViewById(R.id.activity_dashboard_dailygoal);
        textView_dailyGoal2 = view.findViewById(R.id.activity_dashboard_textview_dailygoal);
        textView_todayTime = view.findViewById(R.id.activity_dashboard_textview_todayexercise);
        textView_thisMonth = view.findViewById(R.id.activity_dashboard_thismonth);
        textView_lastMonth = view.findViewById(R.id.activity_dashboard_lastmonth);
        progressBar = view.findViewById(R.id.activity_dashboard_progressbar);

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("User info", Context.MODE_PRIVATE);

        String dailyGoal = sharedPref.getString("DailyGoal", "0");
        textView_dailyGoal1.setText(dailyGoal);
        textView_dailyGoal2.setText(dailyGoal);

        databaseHelper = new ActivityDatabaseHelper(view.getContext());
        databaseHelper.openDatabase();

        textView_todayTime.setText(getTodayExerciseTime() + "");
        textView_thisMonth.setText(getThisMonthExerciseTime() + "");
        textView_lastMonth.setText(getLastMonthExerciseTime() + "");

        setProgressBar();
    }

    private void setTodayDate() {
        final Calendar cal = Calendar.getInstance();
        int x_year = cal.get(Calendar.YEAR);
        int x_month = cal.get(Calendar.MONTH);
        int x_day = cal.get(Calendar.DAY_OF_MONTH);
        year = Integer.toString(x_year);
        month = Integer.toString((x_month + 1));
        day = Integer.toString(x_day);
        if (x_month < 9) {

            month = "0" + (x_month + 1);
        }
        if (x_day < 10) {

            day = "0" + x_day;
        }
    }

    private int getTodayExerciseTime() {
        setTodayDate();
        String date = (year + "-" + month + "-" + day);

        Cursor cur = databaseHelper.database.rawQuery("SELECT SUM(Minute) FROM Activity WHERE Date = '" + date + "'", null);
        if (cur.moveToFirst()) {
            return cur.getInt(0);
        }
        return 0;
    }

    private int getThisMonthExerciseTime() {
        setTodayDate();
        String date = (year + "-" + month);

        Cursor cur = databaseHelper.getWritableDatabase().rawQuery("SELECT SUM(Minute) FROM Activity WHERE Date LIKE '%" + date + "%'", null);
        if (cur.moveToFirst()) {
            return cur.getInt(0);
        }
        return 0;
    }

    private int getLastMonthExerciseTime() {
        setTodayDate();
        String date = (year + "-" + (Integer.parseInt(month) - 1));

        Cursor cur = databaseHelper.getWritableDatabase().rawQuery("SELECT SUM(Minute) FROM Activity WHERE Date LIKE '%" + date + "%'", null);
        if (cur.moveToFirst()) {
            return cur.getInt(0);
        }
        return 0;
    }

    private void setProgressBar() {
        int progress = (int) ((Double.parseDouble(textView_todayTime.getText().toString()) / Double.parseDouble(textView_dailyGoal2.getText().toString())) * 100);
        progressBar.setProgress(progress);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_nav_bot_dashboard);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        databaseHelper.closeDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_dashboard_setdailygoal:
                AlertDialog.Builder dailyGoalBuilder = new AlertDialog.Builder(view.getContext());
                View dailyGoalView = getLayoutInflater().inflate(R.layout.activity_dashboard_dailygoal, null);
                setDailyGoalCheck = dailyGoalView.findViewById(R.id.activity_dashboard_dailygoal_check);
                setDailyGoalCheck.setOnClickListener(this);
                editText_setDailyGoal = dailyGoalView.findViewById(R.id.activity_dashboard_edittext_dailygoal);
                editText_setDailyGoal.setText(textView_dailyGoal1.getText());

                dailyGoalBuilder.setView(dailyGoalView);
                dialog_setDailyGoal = dailyGoalBuilder.create();
                dialog_setDailyGoal.setCanceledOnTouchOutside(false);
                dialog_setDailyGoal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog_setDailyGoal.show();
                editText_setDailyGoal.requestFocus();
                break;
            case R.id.activity_dashboard_dailygoal_check:
                textView_dailyGoal1.setText(editText_setDailyGoal.getText());
                textView_dailyGoal2.setText(editText_setDailyGoal.getText());

                SharedPreferences sharedPref = view.getContext().getSharedPreferences("User info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("DailyGoal", editText_setDailyGoal.getText().toString());
                editor.apply();

                setProgressBar();
                dialog_setDailyGoal.dismiss();
                break;
        }
    }

}

