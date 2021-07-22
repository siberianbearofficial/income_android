package com.example.filetest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import static com.example.filetest.Consts.COUNT;
import static com.example.filetest.Consts.NAME;
import static com.example.filetest.Consts.OLD_PRICE;
import static com.example.filetest.Consts.URL;
import static com.example.filetest.Consts.URL_SAMPLE;

public class AddFragment extends Fragment {

    private ImageButton addButton;
    private EditText nameET, oldPriceET, countET, webLinkET;
    private Activity activity;
    private ImageView mainIco;

    public AddFragment() {}

    public static AddFragment newInstance(String param1, String param2) {
        return new AddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add, container, false);
        addButton = layout.findViewById(R.id.addButtonAddFragment);
        mainIco = layout.findViewById(R.id.mainIcoAddFragment);
        addButton.setOnClickListener(v -> onAddButtonClick());
        mainIco.setOnClickListener(v -> onCloseImageClick());
        nameET = layout.findViewById(R.id.nameET); oldPriceET = layout.findViewById(R.id.oldPriceET); countET = layout.findViewById(R.id.countET); webLinkET = layout.findViewById(R.id.linkET);
        webLinkET.setBackgroundColor(Colors.getYellow(15));
        webLinkET.setHintTextColor(Colors.getOrange(45));
        webLinkET.setText(URL_SAMPLE);
        LinearLayout linearLayout = layout.findViewById(R.id.linearLayoutAddFragment);
        linearLayout.setBackgroundColor(Colors.getOrange(30));
        ConstraintLayout root = layout.findViewById(R.id.rootAddFragment);
        root.setBackgroundColor(Colors.getYellow(60));
        return layout;
    }

    private void onAddButtonClick() {
        activity = getActivity();
        String name = nameET.getText().toString(), oldPrice = oldPriceET.getText().toString(), count = countET.getText().toString(), url = webLinkET.getText().toString();
        if (url.contains(URL_SAMPLE) && !url.equals(URL_SAMPLE)) {
            if (name.equals("") || name.contains(" ")) {
                nameET.setText("");
                Toast.makeText(activity, R.string.incorrect_name, Toast.LENGTH_SHORT).show();
            } else {
                boolean testValues = true;
                try {
                    Integer.parseInt(count);
                } catch (Exception e) {
                    testValues = false;
                    countET.setText("");
                    Toast.makeText(activity, R.string.incorrect_count, Toast.LENGTH_SHORT).show();
                }
                try {
                    Float.parseFloat(oldPrice);
                } catch (Exception e) {
                    testValues = false;
                    oldPriceET.setText("");
                    Toast.makeText(activity, R.string.incorrect_oldprice, Toast.LENGTH_SHORT).show();
                }
                if (testValues) {
                    Float.parseFloat(oldPrice);
                    Bundle trade = new Bundle();
                    trade.putString(NAME, name);
                    trade.putString(URL, url);
                    trade.putString(OLD_PRICE, oldPrice);
                    trade.putString(COUNT, count);
                    try {
                        FileHandler.addTrade(getContext(), trade);
                    } catch (IOException e) {
                        Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    closeFragment();
                }
            }
        } else {
            if (activity != null) Toast.makeText(activity.getApplicationContext(), R.string.incorrect_link, Toast.LENGTH_SHORT).show();
            else Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), R.string.no_activity, Toast.LENGTH_SHORT).show();
        }
    }

    private void onCloseImageClick () {
        activity = getActivity();
        closeFragment();
    }

    private void closeFragment () {
        if (activity != null) {
            setEnabledButtons();
            LinearLayout container = activity.findViewById(R.id.container);
            ConstraintLayout containerBig = activity.findViewById(R.id.containerBig);
            containerBig.setBackgroundColor(Color.argb(0, 255, 255, 255));
            LockableScrollView scrollView = activity.findViewById(R.id.scrollView);
            scrollView.setScrollingEnabled(true);
            container.removeAllViews();
            startActivity(new Intent(getContext(), MainScreen.class));
        } else Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), R.string.no_activity, Toast.LENGTH_SHORT).show();
    }

    private void setEnabledButtons() {
        ((ImageButton) activity.findViewById(R.id.addButton)).setEnabled(true);
        ((ImageButton) activity.findViewById(R.id.deleteButton)).setEnabled(true);
        ((ImageButton) activity.findViewById(R.id.exportButton)).setEnabled(true);
        ((ImageButton) activity.findViewById(R.id.refreshButton)).setEnabled(true);
        ((ImageButton) activity.findViewById(R.id.logoutButton)).setEnabled(true);
    }
}