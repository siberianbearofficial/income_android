package com.example.filetest;

import android.os.Bundle;

import static com.example.filetest.Consts.EMAIL;
import static com.example.filetest.Consts.PASS;

public class AuthHandler {

    private static final String[] EMAIL_CORRECT = {"f@f.f", "lyu-orlova@yandex.ru"}, PASS_CORRECT = {"12345", "11111"};

    public static boolean isAuthenticated(Bundle user) {
        String email = user.getString(EMAIL), password = user.getString(PASS);
        for (int i = 0; i < EMAIL_CORRECT.length; i++) {
            if (email.equals(EMAIL_CORRECT[i]) && password.equals(PASS_CORRECT[i])) {
                return true;
            }
        }
        return false;
    }
}
