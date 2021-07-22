package com.example.filetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static com.example.filetest.AuthHandler.isAuthenticated;
import static com.example.filetest.Consts.EMAIL;
import static com.example.filetest.Consts.PASS;

public class MainActivity extends AppCompatActivity {

    private EditText emailET, passwordET;

    @Override
    public void onBackPressed() {Toast.makeText(this, R.string.unable, Toast.LENGTH_SHORT).show();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        try {
            FileHandler.createFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinearLayoutCompat root = findViewById(R.id.activity_start_root);
        emailET = findViewById(R.id.emailET); passwordET = findViewById(R.id.passwordET);
        emailET.setHintTextColor(Colors.getOrange(45));
        passwordET.setHintTextColor(Colors.getOrange(45));
        emailET.setBackgroundColor(Colors.getYellow(15));
        passwordET.setBackgroundColor(Colors.getYellow(15));
        root.setBackgroundColor(Colors.getYellow(60));

        setRightParams(); //TODO: REMOVE AFTER TESTING
    }

    private void setRightParams() {
        emailET.setText("f@f.f");
        passwordET.setText("12345");
    }

    private void deleteButtonMethod () {
//        EditText nameET = findViewById(R.id.nameET);
//        EditText countET = findViewById(R.id.countET);
//        EditText oldPriceET = findViewById(R.id.oldPriceET);
//        EditText urlET = findViewById(R.id.urlET);
//
//        String name = nameET.getText().toString(), count = countET.getText().toString(), oldPrice = oldPriceET.getText().toString(), url = urlET.getText().toString();
//
//        Bundle trade = new Bundle();
//        trade.putString(NAME, name);
//        trade.putString(URL, URL_SAMPLE + url);
//        trade.putString(OLD_PRICE, oldPrice);
//        trade.putString(COUNT, count);
//
//        try {
//            FileHandler2.removeTrade(this, trade);
//            //FileHandler2.clearTradesFile(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            FileHandler.clearTradesFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void login (View v) {
        String email = emailET.getText().toString(), password = passwordET.getText().toString();
        Bundle user = new Bundle();
        user.putString(EMAIL, email);
        user.putString(PASS, password);
        if (isAuthenticated(user))
            startActivity(new Intent(this, MainScreen.class));
        else
            Toast.makeText(this, R.string.incorrect_credentials, Toast.LENGTH_SHORT).show();
    }

    public void signUp (View v) {
        Toast.makeText(this, R.string.sign_up_toast, Toast.LENGTH_SHORT).show();
    }

    public void forgotPassword (View v) {
        Toast.makeText(this, R.string.forgot_password_toast, Toast.LENGTH_SHORT).show();
    }
}