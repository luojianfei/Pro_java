package com.leo.pro.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.leo.pro.R;
import com.leo.pro.app.Constant;
import com.leo.pro.app.CustomApplication;
import com.leo.pro.app.MyActivityManager;
import com.leo.pro.nBase.GenericHelper;
import com.leo.pro.nBase.NPresenter;
import com.leo.pro.utils.Res;
import com.leo.pro.utils.ScreenUtils;

import java.util.List;

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 15:10
 */

public abstract class BaseFragmentActivity<T extends NPresenter,G extends ViewDataBinding> extends FragmentActivity implements View.OnClickListener {
    /**
     * 设置activity布局
     */
    public abstract int onSetContentView();

    /**
     * 初始化组件
     */
    public abstract void onInitView();

    /**
     * 初始化数据
     */
    public abstract void onInitData();

    /**
     * 初始化监听
     */
    public abstract void onInitListener();
    public boolean mDoubleKeyExit = false;//是否双击推出应用
    public Context mContext;//上下文对象
    private long mFirstTime = 0;//第一次按下返回键
    private long mSecondTime = 0;//第二次按下返回键
    public final static int GONE = View.GONE;
    public final static int VISIBLE = View.VISIBLE;
    public final static int INVISIBLE = View.INVISIBLE;
    private Toast mToast;//toast
    public View mParentView;//main view
    public LayoutInflater mInflater;
    public CustomApplication mApplication ;
    public G mViewBinding;
    public T mPresenter;
    public boolean mIsAlph = false;//状态栏是否透明
    public boolean mIsTop = false;//布局是否要到最顶部
    public boolean mFullScreen = false;//是否全屏
    public int mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ;//默认竖屏
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(mOrientation);//竖屏
        mApplication = (CustomApplication) getApplication();
        this.mContext = this ;
        mParentView = View.inflate(mContext, onSetContentView(), null);
        try {
            mViewBinding = DataBindingUtil.bind(mParentView);
            mPresenter = GenericHelper.newPresenter(this) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mParentView);
        mInflater = LayoutInflater.from(mContext);
        if (!mFullScreen) {
            if (!mIsTop) {
                mParentView.setFitsSystemWindows(true);
            }
            ScreenUtils.titleAlph(mIsAlph?true:mIsTop, Res.getColorRes(R.color.app_main_color, mContext), this);
        }
        MyActivityManager.addActivity(this);
        onSetContentView();
        onInitView();
        onInitData();
        onInitListener();
        if(mPresenter != null){
            mPresenter.start();
        }
    }

    public Context context() {
        return mContext;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDoubleKeyExit) {
                if (mFirstTime == 0) {
                    mFirstTime = System.currentTimeMillis();
                    showShortToast("再次点击返回键退出应用");
                } else {
                    mSecondTime = System.currentTimeMillis();
                    long interval = mSecondTime - mFirstTime;
                    if (interval <= Constant.DOUBLE_CLICK_EXIT_DENY) {
                        MyActivityManager.exitApplicaion();
                    } else {
                        mFirstTime = 0;
                        mSecondTime = 0;
                    }
                }
            } else {
                onBack();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 返回键
     */
    public void onBack() {
        finish();
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBack();
                break;
        }
    }
    /**
     * 弹出short toast提示
     *
     * @param msg
     */
    public void showShortToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mToast.cancel();
                } catch (Exception e) {
                }
                mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
    @Override
    protected void onPause() {
        if (mToast != null) {//toast随页面消失而消失
            try {
                mToast.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.end();
        }
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public boolean isBackground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {

            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

}
