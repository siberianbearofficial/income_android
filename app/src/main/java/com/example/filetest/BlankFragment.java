package com.example.filetest;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class BlankFragment extends Fragment {

    public BlankFragment() {}

    public static BlankFragment newInstance(String param1, String param2) {return new BlankFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_blank, container, false);
        ConstraintLayout root = layout.findViewById(R.id.rootBlankFragment);
        root.setBackgroundColor(Color.argb(0, 255, 255, 255));
//        TextView textView = layout.findViewById(R.id.textBlankFragment);
//        textView.setBackgroundColor(color);
        return layout;
    }
}