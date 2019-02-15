package com.leo.pro.app.utils.db;

import android.content.ContentValues;

import com.leo.pro.app.utils.JSONUtils;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by HX·罗 on 2017/2/24.
 */

public class ObjectUtils {

    private ObjectUtils() {
    }

    /**
     * 将对象转化成ContentValues对象
     *
     * @param obj
     * @return
     */
    public static ContentValues objToContentValues(Object obj) {
        ContentValues contentValues = new ContentValues();
        if (obj != null) {
            JSONObject object = JSONUtils.objToJson(obj);
            if (object != null) {
                Iterator iterator = object.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next().toString();
                    contentValues.put(key, JSONUtils.getValueForKey(object, key));
                }
            }
        }
        return contentValues;
    }

}
