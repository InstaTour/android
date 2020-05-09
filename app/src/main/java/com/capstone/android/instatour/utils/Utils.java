package com.capstone.android.instatour.utils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String getNowByTimeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();
    }

    public static String getNowTime() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR)+"."+String.valueOf(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.DATE);
    }
}
