package com.leo.pro.fun1.view;

import android.os.Bundle;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.base.BaseFragment;
import com.leo.pro.app.entity.MyDataInfo;
import com.leo.pro.databinding.FragmentFun1Binding;
import com.leo.pro.fun1.adapter.MyListDataAdapter;
import com.leo.pro.fun1.contract.Fun1Contract;
import com.leo.pro.fun1.presenter.Fun1Presenter;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:02
 */

public class Fun1Fragment extends BaseFragment<Fun1Presenter,FragmentFun1Binding> implements Fun1Contract.View {
    @Override
    protected int onSetContentView() {
        return R.layout.fragment_fun1;
    }

    @Override
    public void onInitView() {
        mViewBinding.layoutTitle.ivBack.setVisibility(View.GONE);
    }

    @Override
    public void onInitData(Bundle arguments) {
        mViewBinding.layoutTitle.setTitle("Fun1");
        ArrayList<MyDataInfo> mDataInfos = new ArrayList<>() ;
        for(int i = 0; i < 10 ; i++){
            mDataInfos.add(new MyDataInfo(""+i,"name"+i,"title"+i,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548845457017&di=e9842bb94def0ffce4add64ec9326624&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Df29d02216d09c93d13ff06b4f75492a9%2F71cf3bc79f3df8dcea8ac7bec711728b4710286d.jpg")) ;
        }
        MyListDataAdapter adapter = new MyListDataAdapter(mDataInfos,getActivity()) ;
        mViewBinding.crvList.setAdapter(adapter); ;
    }

    @Override
    public void onInitListener() {

    }
}
