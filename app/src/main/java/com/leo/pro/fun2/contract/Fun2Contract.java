package com.leo.pro.fun2.contract;

import android.content.Context;

import com.leo.pro.app.nBase.NContract;
import com.leo.pro.app.net.NetCallBack;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:04
 */

public interface Fun2Contract {
    interface View extends NContract.View {
        void onShutdownCallback(boolean result) ;
    }

    interface Presenter extends NContract.Presenter {
        void shutdown() ;
    }

    interface Model extends NContract.Model {
        void requestShutdown(Context context, String requestStr, NetCallBack callBack) ;
    }
}
