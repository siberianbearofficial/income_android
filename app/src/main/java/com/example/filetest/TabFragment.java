package com.example.filetest;

import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static com.example.filetest.Consts.COUNT;
import static com.example.filetest.Consts.DOLLAR;
import static com.example.filetest.Consts.GREEN_BG_IMAGE;
import static com.example.filetest.Consts.NAME;
import static com.example.filetest.Consts.OLD_PRICE;
import static com.example.filetest.Consts.ORANGE_BG_IMAGE;
import static com.example.filetest.Consts.URL;
import static com.example.filetest.MainScreen.currentPrices;
import static com.example.filetest.MainScreen.summaryIncome;

public class TabFragment extends Fragment {

    private Bundle trade;
    private Handler handler;
    private Random random = new Random();
    private TextView oldPriceTV, currPriceTV, countTV, bargainTV, nameTV;
    private ImageView tick;
    private ImageButton deleteButton, checkBox;
    private float oldPrice = 0f, currPrice = 0f, bargain = 0f;
    private int count = 0;

    public TabFragment() {}

    public static TabFragment newInstance(Bundle trade) {
        TabFragment fragment = new TabFragment();
        fragment.setArguments(trade);
        return fragment;
    }

    private void addCurrentPriceToStaticArrayList(String thisCurrPrice, String name) {
        currentPrices.putString(name, thisCurrPrice);
        //incomes.putString(name, String.valueOf(income));
    }

    private void updateTickVisibility(boolean checked) {
        tick.setVisibility(checked ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String currPriceStr = msg.obj.toString();
                currPrice = Float.parseFloat(currPriceStr);
                currPriceTV.setText(currPriceStr + DOLLAR);
                bargain = getBargain(oldPrice, currPrice, count);
                bargainTV.setText(String.valueOf(bargain) + DOLLAR);

                //change color of text
                if (currPrice < oldPrice) currPriceTV.setTextColor(Colors.rose);
                else if (currPrice > oldPrice) currPriceTV.setTextColor(Colors.green);
                if (bargain > 0) bargainTV.setTextColor(Colors.green);
                else if (bargain < 0) bargainTV.setTextColor(Colors.rose);

                //save for exporting
                addCurrentPriceToStaticArrayList(currPriceStr, trade.getString(NAME));
                updateSummaryIncome(bargain);
            }
        };
        if (getArguments() != null) {
            this.trade = getArguments();
        }
    }

    private void updateSummaryIncome(float income) {
        ((MainScreen) Objects.requireNonNull(getActivity())).updateSummaryIncome(income);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = colorBackground(inflater.inflate(R.layout.fragment_tab, container, false));
        oldPriceTV = layout.findViewById(R.id.oldPriceTV);
        currPriceTV = layout.findViewById(R.id.currPriceTV);
        countTV = layout.findViewById(R.id.countTV);
        bargainTV = layout.findViewById(R.id.bargainTV);
        nameTV = layout.findViewById(R.id.name);
        deleteButton = layout.findViewById(R.id.deleteCurrentTabButton);
        tick = layout.findViewById(R.id.tick);
        checkBox = layout.findViewById(R.id.addCurrentToSummaryCheckBox);
        checkBox.setOnClickListener(v -> {
            boolean visible = tick.getVisibility() == View.VISIBLE;
            updateTickVisibility(!visible);
            updateSummaryIncome(visible ? (-bargain) : bargain);
        });
        deleteButton.setOnClickListener(v -> {
            try {
                deleteTab();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            oldPrice = Float.parseFloat(trade.getString(OLD_PRICE));
            count = Integer.parseInt(trade.getString(COUNT));
            nameTV.setText(trade.getString(NAME).replace('_', ' '));
            Thread thread = new Thread(new HTTPHandler(handler, trade.getString(URL))); thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            nameTV.setText(NAME);
        }
        oldPriceTV.setText(String.valueOf(oldPrice) + DOLLAR);
        countTV.setText(String.valueOf(count));
        return layout;
    }

    private View colorBackground(View layout) {
        ConstraintLayout constraintLayout = layout.findViewById(R.id.rootOrangeTabFragment);
        constraintLayout.setBackgroundColor(Colors.getYellow(60));
        LinearLayout linearLayout = layout.findViewById(R.id.linearLayoutOrangeTabFragment);
        ImageView bgImage = layout.findViewById(R.id.bgImage);

        int colorNum = trade.getInt("background_color");

        int bgColor, bgImageResource;
        switch (colorNum) {
            case 0:
                bgImageResource = ORANGE_BG_IMAGE;
                bgColor = Colors.getOrange(30);
                break;
            case 1:
                bgImageResource = ORANGE_BG_IMAGE;
                bgColor = Colors.getRed(30);
                break;
            case 2:
                bgImageResource = GREEN_BG_IMAGE;
                bgColor = Colors.getGreen(30);
                break;
            default:
                bgImageResource = GREEN_BG_IMAGE;
                bgColor = Colors.getBlue(30);
                break;
        }

        linearLayout.setBackgroundColor(bgColor);
        bgImage.setImageResource(bgImageResource);
        return layout;
    }

    private void deleteTab () throws IOException {
        FileHandler.removeTrade(getContext(), trade);
        startActivity(new Intent(getContext(), MainScreen.class));
    }

    private float getBargain (float oldPrice, float currPrice, float count) {
        return (currPrice - oldPrice) * count;
    }
}