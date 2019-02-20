package com.leo.pro.fun2.model;

import android.content.Context;

import com.leo.pro.app.net.HttpClient;
import com.leo.pro.app.net.NetCallBack;
import com.leo.pro.app.net.RequestModelConfig;
import com.leo.pro.fun2.contract.Fun2Contract;
import com.leo.pro.app.nBase.NModel;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:04
 */

public class Fun2Model extends NModel implements Fun2Contract.Model {
    @Override
    public void requestShutdown(Context context, String requestStr, NetCallBack callBack) {
        HttpClient.Param params1 = new HttpClient.Param("data", requestStr) ;
        HttpClient.Param[] params = new HttpClient.Param[]{params1} ;
        HttpClient.postAsyn(getUrl(RequestModelConfig.SERVER_SHUTDOWN_ADDRESS),
                callBack,params);
    }
}
