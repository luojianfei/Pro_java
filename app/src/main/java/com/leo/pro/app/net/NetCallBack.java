package com.leo.pro.app.net;

import android.support.annotation.CallSuper;

import okhttp3.Request;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public abstract  class NetCallBack<T>/* implements Callback */{

    NetObj<T> netObj ;

    public NetCallBack(){
        this(null) ;
    }
    public NetCallBack(Class<T> clazz) {
        netObj = new NetObj<T>(clazz) ;
    }

    public void onRequestComplete(){
        //请求数据完成
    }

    /**
     * 开始下载
     * @param length 文件总大小
     */
    public void onStart(long length) {}

    /**
     * 完成
     */
    public void onComplete() {}

    /**
     * 下载进度
     * @param progress 进度
     * @param realSpped 实时下载速度
     */
    public void onProgress(float progress,String realSpped) {}
    @CallSuper
    public void onNetError(String des) {//网络连接错误
        onRequestComplete();
    }
    @CallSuper
    public void onError(Request request, Exception e){
        onRequestComplete();
    }
    @CallSuper
    public void onResponse(NetObj object){
        onRequestComplete();
    }

}
