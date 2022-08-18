package com.example.nobintest.timeUtils;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.Months;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

public class UtilTimeDifference {
    public static int[] stringExtractor(String time) {

        String[] firstSplitPart = time.split("-");
        String[] secondSplitPart = firstSplitPart[2].split("T");
        String[] thirdSplitPart = secondSplitPart[1].split(":");
        String[] fourthSplitPart = thirdSplitPart[2].split("\\.");

        int year = Integer.parseInt(firstSplitPart[0]);
        int month = Integer.parseInt(firstSplitPart[1]);
        int day = Integer.parseInt(secondSplitPart[0]);
        int hour = Integer.parseInt(thirdSplitPart[0]);
        int minute = Integer.parseInt(thirdSplitPart[1]);
        int second = Integer.parseInt(fourthSplitPart[0]);

        return new int[]{year, month, day, hour, minute, second};

    }

    public static String calculateTimeDiff(String currentTime, String publishTime) {

        int[] currentExtracted = stringExtractor(currentTime);
        int[] publishExtracted = stringExtractor(publishTime);

        int currentYear = currentExtracted[0];
        int currentMonth = currentExtracted[1];
        int currentDay = currentExtracted[2];
        int currentHour = currentExtracted[3];
        int currentMinute = currentExtracted[4];
        int currentSecond = currentExtracted[5];
        int publishYear = publishExtracted[0];
        int publishMonth = publishExtracted[1];
        int publishDay = publishExtracted[2];
        int publishHour = publishExtracted[3];
        int publishMinute = publishExtracted[4];
        int publishSecond = publishExtracted[5];

        String dateStart = "" + publishMonth + "/" + publishDay + "/" + publishYear + " " + publishHour + ":" + publishMinute + ":" + publishSecond;
        String dateStop = "" + currentMonth + "/" + currentDay + "/" + currentYear + " " + currentHour + ":" + currentMinute + ":" + currentSecond;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1;
        Date d2;

        try {

            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            int yearDiff = Years.yearsBetween(dt1, dt2).getYears();
            int monthDiff = Months.monthsBetween(dt1, dt2).getMonths();
            int dayDiff = Days.daysBetween(dt1, dt2).getDays();
            int hourDiff = Hours.hoursBetween(dt1, dt2).getHours();
            int minuteDiff = Minutes.minutesBetween(dt1, dt2).getMinutes();

            String result;

            if (yearDiff > 5) { // th

                result = "Long time ago";

            } else if (yearDiff > 0) {

                if (yearDiff == 1) {
                    result = "1 year ago";
                } else {
                    result = String.valueOf(yearDiff).concat(" years ago");
                }

            } else if (monthDiff > 0) {

                if (monthDiff == 1) {
                    result = "1 month ago";
                } else {
                    result = String.valueOf(monthDiff).concat(" months ago");
                }

            } else if (dayDiff > 0) {

                if (dayDiff == 1) {
                    result = "1 day ago";
                } else {
                    result = String.valueOf(dayDiff).concat(" days ago");
                }

            } else if (hourDiff > 0) {

                if (hourDiff == 1) {
                    result = "1 hour ago";
                } else {
                    result = String.valueOf(hourDiff).concat(" hours ago");
                }

            } else if (minuteDiff > 5) {  // th

                result = String.valueOf(minuteDiff).concat(" minutes ago");

            } else {

                result = "Moments ago";

            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}