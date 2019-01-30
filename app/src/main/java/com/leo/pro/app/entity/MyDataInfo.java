package com.leo.pro.app.entity;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:51
 */

public class MyDataInfo {

    private String id ;
    private String name ;
    private String title ;
    private String imgUrl ;

    public MyDataInfo(String id, String name,String title, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
