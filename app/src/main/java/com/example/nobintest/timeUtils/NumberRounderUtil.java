package com.example.nobintest.timeUtils;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberRounderUtil {

    public static double correctRounding(String Num) {
        double number = Math.abs(Double.parseDouble(Num));
        if (number >= 1) {
            return round(number, 2);
        } else {
            int i = 0;
            do {
                i = i + 1;
            } while (!(number * Math.pow(10, i) >= 1));
            return round(number, i + 1);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) {
            Log.v("TAG", "Error Happened in NumberRounderUtil");
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
