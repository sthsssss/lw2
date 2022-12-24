package org.techtown.lottoworld;

import static java.util.Calendar.getInstance;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LatestRound {
    public static int round = 1028;
    public LatestRound() throws ParseException {
        round = calculateWeeks();
    }

    public static int getRound() {
        return round;
    }
    public int calculateWeeks() throws ParseException {
        Calendar cmpDate = getInstance();
        String s_date = "2022-08-13";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s_date);

        cmpDate.setTime(date); //특정 일자
        Calendar getToday = getInstance();

        getToday.setTime(new Date()); //금일 날짜

        long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24*60*60); //일자수 차이

        int hour = getInstance().get(Calendar.HOUR_OF_DAY);

        long weeks =  diffDays / 7;

        if(diffDays % 7 == 0 && hour <= 23){
            weeks -= 1;
        }
        return (int) weeks + 1028;
    }
}
