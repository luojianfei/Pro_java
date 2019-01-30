package com.leo.pro.net;


import com.google.gson.Gson;
import com.leo.pro.utils.JSONUtils;
import com.leo.pro.utils.TextUtil;

import java.util.ArrayList;

/**
 * 请求返回对象
 *
 * @auther Leo
 * created at 2016/8/2 0002 12:32
 */
public class NetObj<T extends Object> {
    private String code;
    private String messge;
    private String data;
    private String jsonStr;
    private T t;
    private ArrayList<T> ts;
    private Class<T> clazz;

    public NetObj(Class<T> clazz) {
        this.clazz = clazz;
    }

    public NetObj(String jsonStr) {
        setJsonData(jsonStr);
    }

    public NetObj setJsonData(String jsonStr1) {
        this.jsonStr = jsonStr1 ;
//        try {
//            this.jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
//            this.ts = JSONUtils.jsonToArrayObj(data, clazz);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try{
            this.code = JSONUtils.getValueForKey(jsonStr,"code");
            this.messge = JSONUtils.getValueForKey(jsonStr,"message");
            this.data = JSONUtils.getValueForKey(jsonStr,"data") ;
            if(!TextUtil.isEmpty(data) && data.startsWith("["))
            {
              this.ts = JSONUtils.jsonToArrayObj(data,clazz) ;
            }else if (!TextUtil.isEmpty(data) && data.startsWith("{"))
            {
              this.t = (T) new Gson().fromJson(JSONUtils.getValueForKey(jsonStr,"data"),clazz);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public String getMessge() {
        return TextUtil.isEmpty(messge) ? "请求错误，请稍后再试" : messge;
    }

    public NetObj setMessge(String messge) {
        this.messge = messge;
        return this;
    }

    public T getObject() {
        return t;
    }

    public ArrayList<T> getObjects() {
        return ts;
    }

    public String getCode() {
        return code;
    }

    public NetObj setCode(String code) {
        this.code = code;
        return this;
    }

    public String getData() {
        return data;
    }

    public NetObj setData(String data) {
        this.data = data;
        return this;
    }

    public String getJsonStr() {
        return jsonStr;
    }
}
