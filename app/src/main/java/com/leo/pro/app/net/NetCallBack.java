package com.leo.pro.app.net;

import android.support.annotation.CallSuper;

import com.leo.pro.app.utils.GenericSuperclassUtil;

import okhttp3.Request;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public abstract  class NetCallBack<T extends Object>/* implements Callback */{

    public NetObj<T> netObj ;

    public NetCallBack() {
        netObj = new NetObj(GenericSuperclassUtil.getActualTypeArgument(getClass())) ;
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
     * @param filePath  文件保存地址
     */
    public void onComplete(String filePath) {}
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
