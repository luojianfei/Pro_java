package com.leo.pro.fun1.contract;

import com.leo.pro.app.entity.MyDataInfo;
import com.leo.pro.app.nBase.NContract;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:02
 */

public interface Fun1Contract {
    interface View extends NContract.View {
        void setData(int page, ArrayList<MyDataInfo> dataInfos) ;
    }

    interface Presenter extends NContract.Presenter {
        void loadData(int page) ;
    }

    interface Model extends NContract.Model {
    }
}
