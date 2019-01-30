package com.leo.pro.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leo.pro.nBase.GenericHelper;
import com.leo.pro.nBase.NContract;
import com.leo.pro.nBase.NPresenter;

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 16:20
 */

public abstract class BaseFragment<T extends NPresenter,G extends ViewDataBinding> extends Fragment implements NContract.View,View.OnClickListener {

    /**
     * 贴附的activity
     */
    protected Activity mActivity;

    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;
    public T mPresenter;
    public G mViewBinding;
    private Toast mToast;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(onSetContentView(), container, false);
        try {
            mViewBinding = DataBindingUtil.bind(mRootView);
            mPresenter = GenericHelper.newPresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitView();
        onInitData(getArguments());
        mIsPrepare = true;
        onLazyLoad();
        onInitListener();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    /**
     * 弹出Toast
     * @param msg
     */
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mToast.cancel();
                } catch (Exception e) {
                }
                mToast = Toast.makeText(context(), msg, Toast.LENGTH_SHORT);
                mToast.show();

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     */
    protected void onLazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.end();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }

        return (T) mRootView.findViewById(id);
    }

    /**
     * 设置根布局资源id
     *
     * @return
     */
    protected abstract int onSetContentView();


    /**
     * 初始化组件
     */
    public abstract void onInitView();

    /**
     * 初始化数据
     */
    public abstract void onInitData(Bundle arguments);

    /**
     * 初始化监听
     */
    public abstract void onInitListener();

    @Override
    public Context context() {
        return getActivity();
    }
}
