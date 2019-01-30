package com.leo.pro.nBase;

import android.content.Context;

import com.leo.pro.net.NetCallBack;
import com.leo.pro.net.RequestModelConfig;
import com.leo.pro.utils.NetUtils;

/**
 * Created by HX·罗 on 2017/11/14.
 */

public class NModel {

    /**
     * 拼接url地址
     *
     * @param method
     * @return
     */
    protected static String getUrl(String method) {
        return String.format("%s%s", RequestModelConfig.SERVER_BASE_ADDRESS, method);
    }

    /**
     * 检查网络
     *
     * @param context
     * @param callBack
     * @return
     */
    protected boolean examNet(Context context, NetCallBack callBack) {
        if (!NetUtils.isConnected(context)) {//判断是否有网络连接
            callBack.onNetError("网络连接错误，请检查网络");
            return false;
        } else {
            return true;
        }
    }
}
