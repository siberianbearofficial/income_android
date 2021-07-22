package com.example.filetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.example.filetest.Consts.COUNT;
import static com.example.filetest.Consts.GREEN_BG_IMAGE;
import static com.example.filetest.Consts.NAME;
import static com.example.filetest.Consts.OLD_PRICE;
import static com.example.filetest.Consts.ORANGE_BG_IMAGE;

public class MainScreen extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageButton logoutButton, refreshButton, exportButton, addButton, deleteButton;
    private LockableScrollView scrollView;
    private Random random = new Random();
    public SummaryIncome summaryIncomeFragment;
    private ArrayList<Bundle> trades;

    public static Bundle currentPrices;
    public static float summaryIncome = 0;

    @Override
    public void onBackPressed() {Toast.makeText(this, R.string.try_sign_out, Toast.LENGTH_SHORT).show();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        currentPrices = new Bundle();
        scrollView = findViewById(R.id.scrollView);
        ConstraintLayout root = findViewById(R.id.rootMainScreen);
        root.setBackgroundColor(Colors.getYellow(60));

        fragmentManager = getSupportFragmentManager();
        trades = new ArrayList<>();

        if (savedInstanceState == null) {
            try {
                trades = FileHandler.getAllTrades(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int previous = random.nextInt(4), current;
            for (Bundle trade : trades) {
                //choose background
                do {
                    current = random.nextInt(4);
                } while (current == previous);

                trade.putInt("background_color", current);
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new TabFragment();
                fragment.setArguments(trade);
                fragmentTransaction.add(R.id.containerLayout, fragment);
                fragmentTransaction.commit();
                previous = current;

            }

            if (trades.size() <= 0) {
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new BlankFragment();
                fragmentTransaction.add(R.id.containerLayout, fragment);
                fragmentTransaction.commit();
                Random r = new Random();
                int f = r.nextInt(4) + 1, color;
                switch (f) {
                    case 1:
                        color = Colors.getOrange(30);
                        break;
                    case 2:
                        color = Colors.getRed(30);
                        break;
                    case 3:
                        color = Colors.getGreen(30);
                        break;
                    case 4:
                        color = Colors.getBlue(30);
                        break;
                    default:
                        color = Color.RED;
                }
                scrollView.setBackgroundColor(color);
            }

            fragmentTransaction = fragmentManager.beginTransaction();
            summaryIncomeFragment = new SummaryIncome();
            Bundle bundle = new Bundle();
            bundle.putString(SummaryIncome.KEY, String.valueOf(summaryIncome));
            summaryIncomeFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.containerLayout, summaryIncomeFragment);
            fragmentTransaction.commit();

            addButton = findViewById(R.id.addButton);
            deleteButton = findViewById(R.id.deleteButton);
            exportButton = findViewById(R.id.exportButton);
            refreshButton = findViewById(R.id.refreshButton);
            logoutButton = findViewById(R.id.logoutButton);

            addButton.setOnClickListener(v -> onAddButtonClick());
            deleteButton.setOnClickListener(v -> onDeleteButtonClick());
            exportButton.setOnClickListener(v -> onExportButtonClick());
            refreshButton.setOnClickListener(v -> Refresh());
            logoutButton.setOnClickListener(v -> onLogoutButtonClick());
        }
    }

    public void updateSummaryIncome(float income) {
        summaryIncome += income;
        summaryIncomeFragment.updateSummaryIncome(String.valueOf(summaryIncome));
    }

    private void onDeleteButtonClick() {
        ClearDialog clearDialog = new ClearDialog();
        clearDialog.show(fragmentManager, "");
    }

    public void Refresh() {
        startActivity(new Intent(this, MainScreen.class));
    }

    private void onLogoutButtonClick() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void onExportButtonClick() {
        //Toast.makeText(this, R.string.unable, Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");

        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        String shareBody = getString(R.string.subject) + " (" + dateText + "):\n\n"; // your text body
        String shareSub = ""; // your subject here
        String tabTitle = getString(R.string.tab_title); // your share tab title

        for (Bundle trade : trades) {
            String tradeName = trade.getString(NAME);
            String tradeOldPrice = trade.getString(OLD_PRICE);
            String tradeCount = trade.getString(COUNT);
            String tradeCurrPrice = currentPrices.getString(tradeName);
            //String tradeIncome = incomes.getString(tradeName);

            String old_price = getString(R.string.old_price), count = getString(R.string.count), curr_price = getString(R.string.curr_price), income = getString(R.string.app_name);

            String tradeString = tradeName + "\n" + old_price + ": " + tradeOldPrice + "; " + count + ": " + tradeCount + "; " + curr_price + ": " + tradeCurrPrice + ";"/* + income + ": " + tradeIncome + ";"*/;
            shareBody += (tradeString + "\n\n");
        }
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, tabTitle));
    }

    private void onAddButtonClick() {
        setDisabledButtons();
        scrollView.setScrollingEnabled(false);
        ConstraintLayout constraintLayout = findViewById(R.id.containerBig);
        constraintLayout.setBackgroundColor(Color.argb((int) ((float) 50 / 100 * 255), 0, 0, 0));
        LinearLayout linearLayout = findViewById(R.id.container);
        linearLayout.setBackgroundColor(Color.WHITE);
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new AddFragment();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void setDisabledButtons() {
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
        exportButton.setEnabled(false);
        refreshButton.setEnabled(false);
        logoutButton.setEnabled(false);
    }
}