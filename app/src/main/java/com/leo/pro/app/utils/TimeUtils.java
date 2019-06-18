package com.leo.pro.app.utils;

//import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者: Administrator on 2016-11-02 14:59.
 */

public class TimeUtils {



    public static String getOnlyDate(long time) {

        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String retStrFormatNowDate = sdFormatter.format(time);
        return retStrFormatNowDate;
    }

    public static String getFormatTime(long time) {

        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = sdFormatter.format(time);
        return retStrFormatNowDate;
    }


    public static String getTime(long timeStample) {
       System.out.println(timeStample+"");

        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (timeStample + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天

        if (intervalTime == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return "今天:"+sdf.format(timeStample);
        } else if (intervalTime == -1) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
            return "昨天:" + sdf1.format(timeStample);
        } else if (intervalTime == -2) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
            return "前天:" + sdf1.format(timeStample);
        } else {
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf3.format(timeStample);
        }

    }

    public static String getAtTime(long timeStample) {

        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (timeStample + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天

        if (intervalTime == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return "今天"+sdf.format(timeStample);
        } else if (intervalTime == -1) {
            return "昨天";
        } else if (intervalTime == -2) {
            return "前天";
        } else {
            return Math.abs(intervalTime)+"天前";
        }

    }

    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }
    public static String getFormatTime(long millTime, String format) {
        String formatTime = "";
        if (millTime > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date();
            date.setTime(millTime);
            formatTime = sdf.format(date);
        }
        return formatTime;
    }

    /**
     * 通过秒获取用时
     *
     * @param useTime
     * @return
     */
    @NotNull
    public static String getUseTime(long useTime) {
        String useStrTime = "";
        if (useTime < 3600) {//不足一小时
            useStrTime = String.format("%02d:%02d", useTime / 60, useTime % 60);
        } else {//超过一小时
            useStrTime = String.format("%02d:%02d:%02d", useTime / 3600,
                    useTime % 3600 / 60, useTime % 3600 % 60);
        }
        return useStrTime;
    }

    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String getTime2(String returnTimeString){
//        /Date(1521983778343+0800)/
        String result = "";
        if(returnTimeString.contains("Date")&&returnTimeString.length()>19){
            SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy.MM.dd");
            result  = sdFormatter.format(Long.parseLong(returnTimeString.substring(6,19)));
        }
        return result;
    }

}
