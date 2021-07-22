package com.example.filetest;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.example.filetest.Consts.NEEDED_CLASS;
import static java.lang.Character.isDigit;

public class HTTPHandler implements Runnable {

    private Handler handler;
    private String url;

    HTTPHandler (Handler h, String url) {
        this.handler = h;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            String gotPrice = getPrice(this.url);
            Message m = Message.obtain();
            m.obj = gotPrice;
            handler.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPrice (String url) throws Exception {
        Document doc = null; Elements price = null;
        doc = Jsoup.connect(url)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        if (doc != null) {
            price = doc.select(NEEDED_CLASS);
        }
        //String[] result = price.text().split(" ");
        String result = price.text(), resultReady = "";
        for (int i = 0; i < result.length(); i++) {
            if (isDigit(result.charAt(i)) || result.charAt(i) == ',' || result.charAt(i) == '.') {
                resultReady += result.charAt(i);
            }
        }
        String resultString = resultReady.replace(',', '.');
        System.out.println(resultString);
        System.out.println(resultString);
        System.out.println(resultString);
        System.out.println(resultString);
        System.out.println(resultString);
        return resultString;
    }
}