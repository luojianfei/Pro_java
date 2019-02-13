package com.leo.pro.app.entity;

import com.leo.pro.app.utils.TextUtil;

/**
 * Created by HX·罗 on 2017/10/20.
 */

public class ContactInfo {

    private String phoneNo ;
    private String name ;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        if(!TextUtil.isEmpty(phoneNo)){
            phoneNo = phoneNo.replaceAll("[^0-9]","") ;//将非数字的字符过滤
        }
        this.phoneNo = phoneNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
