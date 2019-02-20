package com.leo.pro.fun2.presenter;

import com.leo.pro.app.net.NetCallBack;
import com.leo.pro.app.net.NetObj;
import com.leo.pro.app.utils.JSONUtils;
import com.leo.pro.fun2.contract.Fun2Contract;
import com.leo.pro.fun2.model.Fun2Model;
import com.leo.pro.app.nBase.NPresenter;

import org.json.JSONObject;

import okhttp3.Request;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:04
 */

public class Fun2Presenter extends NPresenter<Fun2Contract.View,Fun2Model> implements Fun2Contract.Presenter {
    @Override
    public void shutdown() {
        showDialog(REQUEST_LOADING_MSG);
        JSONObject reqeustJson = JSONUtils.putValue(new String[]{"time"},new String[]{"2"}) ;
        model.requestShutdown(context, reqeustJson.toString(), new NetCallBack() {
            @Override
            public void onRequestComplete() {
                super.onRequestComplete();
                closeDialog();
            }

            @Override
            public void onNetError(String des) {
                super.onNetError(des);
            }

            @Override
            public void onError(Request request, Exception e) {
                super.onError(request, e);
            }

            @Override
            public void onResponse(NetObj object) {
                super.onResponse(object);
                view.onShutdownCallback("1".equals(object.getCode()));
            }
        });
    }
}
