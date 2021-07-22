package com.example.filetest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static com.example.filetest.Consts.COUNT;
import static com.example.filetest.Consts.NAME;
import static com.example.filetest.Consts.OLD_PRICE;
import static com.example.filetest.Consts.TRADES_FILE_NAME;
import static com.example.filetest.Consts.URL;

public class FileHandler {

    public static void addTrade (Context context, Bundle trade) throws IOException {
        ArrayList<Bundle> trades = getAllTrades(context);
        if (contains(trade, trades)) throw new IOException("THIS TRADE ALREADY EXISTS");

        // отрываем поток для записи
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                context.openFileOutput(TRADES_FILE_NAME, MODE_APPEND)));
        // пишем данные
        bw.write(trade.getString(NAME) + " " + trade.getString(URL) + " " + trade.getString(OLD_PRICE) + " " + trade.getString(COUNT) + "\n");
        bw.flush();
        // закрываем поток
        bw.close();
        System.out.println("Файл записан");
    }

    public static void createFile (Context context) throws IOException {
        // отрываем поток для записи
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                context.openFileOutput(TRADES_FILE_NAME, MODE_APPEND)));
        // пишем данные
        //bw.write("");
        bw.flush();
        // закрываем поток
        bw.close();
        System.out.println("Файл создан (если еще не был создан до этого)");
    }

    public static void removeTrade (Context context, Bundle tradeForRemoving) throws IOException {
        ArrayList<Bundle> trades = getAllTrades(context);
        clearTradesFile(context);
        for (Bundle trade : trades) {
            boolean namesEq = trade.getString(NAME).equals(tradeForRemoving.getString(NAME));
            boolean urlsEq = trade.getString(URL).equals(tradeForRemoving.getString(URL));
            boolean oldPricesEq = trade.getString(OLD_PRICE).equals(tradeForRemoving.getString(OLD_PRICE));
            boolean countsEq = trade.getString(COUNT).equals(tradeForRemoving.getString(COUNT));
            if (!(namesEq || urlsEq || oldPricesEq || countsEq)) {
                addTrade(context, trade);
            }
        }
    }

    private static boolean contains(Bundle what, ArrayList<Bundle> where) {
        for (Bundle test : where) {
            boolean namesEq = test.getString(NAME).equals(what.getString(NAME));
            boolean urlsEq = test.getString(URL).equals(what.getString(URL));
            boolean oldPricesEq = test.getString(OLD_PRICE).equals(what.getString(OLD_PRICE));
            boolean countsEq = test.getString(COUNT).equals(what.getString(COUNT));
            if (namesEq & urlsEq & oldPricesEq & countsEq) {
                return true;
            }
        } return false;
    }

    public static void clearTradesFile (Context context) throws IOException {
        // отрываем поток для записи
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                context.openFileOutput(TRADES_FILE_NAME, MODE_PRIVATE)));
        // пишем данные
        bw.write("");
        bw.flush();
        // закрываем поток
        bw.close();
        System.out.println("Файл очищен");
    }

    public static ArrayList<Bundle> getAllTrades(Context context) throws IOException {
        ArrayList<Bundle> trades = new ArrayList<Bundle>();
        // открываем поток для чтения
        BufferedReader br = new BufferedReader(new InputStreamReader(
                context.openFileInput(TRADES_FILE_NAME)));
        String gotString;
        // читаем содержимое
        while ((gotString = br.readLine()) != null) {
            Log.e("GOTSTRING", gotString);
            String[] words = gotString.split(" ");
            String name = words[0], url = words[1], old_price = words[2], count = words[3];
            Bundle trade = new Bundle();
            trade.putString(NAME, name);
            trade.putString(URL, url);
            trade.putString(OLD_PRICE, old_price);
            trade.putString(COUNT, count);

            trades.add(trade);
        }
        br.close();
        return trades;
    }
}
