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

public class TimeDifference {

    public static int[] stringExtractor(String time) {

        String[] splitted1 = time.split("-");
        String[] splitted2 = splitted1[2].split("T");
        String[] splitted3 = splitted2[1].split(":");
        String[] splitted4 = splitted3[2].split("\\.");

        int year = Integer.parseInt(splitted1[0]);
        int month = Integer.parseInt(splitted1[1]);
        int day = Integer.parseInt(splitted2[0]);
        int hour = Integer.parseInt(splitted3[0]);
        int minute = Integer.parseInt(splitted3[1]);
        int second = Integer.parseInt(splitted4[0]);

        return new int[]{year, month, day, hour, minute, second};

    }

    public TimeDiffLabel calculateTimeDiff(String currentTime, String publishTime) {

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

            if (yearDiff > 5) { // th
                return new TimeDiffLabel(TimeScale.LongTimeAgo, 0);
            } else if (yearDiff > 0) {
                return new TimeDiffLabel(TimeScale.YearsAgo, yearDiff);
            } else if (monthDiff > 0) {
                return new TimeDiffLabel(TimeScale.MonthsAgo, monthDiff);
            } else if (dayDiff > 0) {
                return new TimeDiffLabel(TimeScale.DaysAgo, dayDiff);
            } else if (hourDiff > 0) {
                return new TimeDiffLabel(TimeScale.HoursAgo, hourDiff);
            } else if (minuteDiff > 5) {  // th
                return new TimeDiffLabel(TimeScale.MinutesAgo, minuteDiff);
            } else {
                return new TimeDiffLabel(TimeScale.MomentsAgo, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}

enum TimeScale {
    MomentsAgo,
    MinutesAgo,
    HoursAgo,
    DaysAgo,
    MonthsAgo,
    YearsAgo,
    LongTimeAgo
}
