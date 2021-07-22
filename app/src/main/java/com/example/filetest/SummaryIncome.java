package com.example.filetest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.filetest.Consts.DOLLAR;

public class SummaryIncome extends Fragment {

    private String income;
    public static String KEY = "some_key";
    private TextView incomeTV;

    public SummaryIncome() {}

    public static SummaryIncome newInstance(Bundle args) {
        SummaryIncome fragment = new SummaryIncome();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.income = getArguments().getString(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_summary_income, container, false);
        incomeTV = layout.findViewById(R.id.income_all_tv);
        incomeTV.setText(income);
        return layout;
    }

    public void updateSummaryIncome(String income) {
        incomeTV.setText(income + DOLLAR);
        incomeTV.setTextColor(Float.parseFloat(income) > 0 ? Colors.green : Colors.rose);
    }
}