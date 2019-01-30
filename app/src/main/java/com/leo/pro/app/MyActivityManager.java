package com.leo.pro.app;

import android.app.Activity;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class MyActivityManager {

    private static ArrayList<Activity> activities;

    static {
        if (activities == null) {
            activities = new ArrayList<Activity>();
        }
    }

    /**
     * 添加activity到activity集合
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        int length = activities.size();
        if (length == 0) {
            activities.add(activity);
        } else {
            for (int i = 0; i < length; i++) {
                Activity activity1 = activities.get(i);
                if (activity.getClass().equals(activities.get(i))) {
                    activities.remove(i);
                    activities.add(activity1);
                    break;
                } else if (i == length - 1) {
                    activities.add(activity);
                }
            }
        }
    }

    /**
     * 关闭某个activity
     *
     * @param activityName
     */
    public static void finish2ActivityName(String activityName) {
        int length = activities.size();
        for (int i = 0; i < length; i++) {
            String name = activities.get(i).getClass().getName();
            if (activities.get(i).getClass().getName().equals(activityName)) {
                activities.get(i).finish();
            }
        }
    }

    /**
     * 关闭所有activity 退出应用
     */
    public static void exitApplicaion() {
        int length = activities.size();
        for (int i = 0; i < length; i++) {
            activities.get(i).finish();
        }
    }

    /**
     * 获取最上层的activity
     *
     * @return
     */
    public static Activity getTopActivityInfo() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread")
                    .invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            ArrayMap activities = (ArrayMap) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null ;
    }
}
