package com.leo.pro.app.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建人 LEO
 * 创建时间 2018/4/27 11:15
 */

public class NumberUtil {
    private static String[] geP = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private static String[] oP = {"", "十", "百", "千", "万"};


    /**
     *
     * @param count 迭代次数 调用传0
     * @param number 转换数字 1~99999
     * @return
     */
    public static String num2Lower(int count, int number) {
        if(number > 99999 || number < 1){
            throw new RuntimeException("number is too large or too small and the rang is 1 to 99999") ;
        }else{
            String temp = "";
            if (number / 10 > 0) {
                temp = num2Lower(count + 1, number / 10);
            }
            String s = String.format("%s%s", temp, geP[number % 10]);
            if (count == 0) {//当count为零的时候 迭代完成
                return lower2Real(s);
            }
            return s;
        }
    }

    private static String lower2Real(String lower) {
        if (lower.length() == 1) {//只有一位数直接返回
            return lower;
        } else if (lower.length() == 2 && lower.startsWith("一")) {
            lower = lower.replaceFirst("一", "十");
        } else {
            String temp = "";
            for (int i = 0; i < lower.length(); i++) {
                temp = temp + lower.charAt(i);
                if (!temp.endsWith("零")) {
                    temp = temp + oP[lower.length() - 1 - i];
                }
            }
            lower = temp;
        }
        //去连续重复‘零’
        Matcher matcher = Pattern.compile("(\\w)\\1*").matcher(lower) ;
        lower = "" ;
        while (matcher.find()){
            lower+=matcher.group().length()>1?matcher.group().substring(0,1):matcher.group() ;
            Log.d("leo", matcher.group()) ;
        }
        if (lower.lastIndexOf("零") == lower.length() - 1) {//如果最后一位是‘零’就去掉
            lower = lower.substring(0, lower.length() - 1);
        }
        return lower;
    }
}
