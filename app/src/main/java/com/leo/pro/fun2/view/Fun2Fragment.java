package com.leo.pro.fun2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.ErrorLogActivity;
import com.leo.pro.app.base.BaseFragment;
import com.leo.pro.app.entity.VersionInfo;
import com.leo.pro.app.net.HttpClient;
import com.leo.pro.app.net.NetCallBack;
import com.leo.pro.app.net.NetObj;
import com.leo.pro.app.utils.ActivityUtils;
import com.leo.pro.app.utils.ApkUpdateUtils;
import com.leo.pro.app.utils.FileUtils;
import com.leo.pro.app.utils.JSONUtils;
import com.leo.pro.databinding.FragmentFun2Binding;
import com.leo.pro.fun2.contract.Fun2Contract;
import com.leo.pro.fun2.presenter.Fun2Presenter;

import okhttp3.Request;

import static com.leo.pro.app.Constant.DOWNLOAD_APK_SAVE_NAME;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:04
 */

public class Fun2Fragment extends BaseFragment<Fun2Presenter, FragmentFun2Binding> implements Fun2Contract.View {
    public static final String TAG = "Fun2Fragment" ;

    @Override
    protected int onSetContentView() {
        return R.layout.fragment_fun2;
    }

    @Override
    public void onInitView() {
        mViewBinding.layoutTitle.ivBack.setVisibility(View.GONE);
    }

    @Override
    public void onInitData(Bundle arguments) {
        mViewBinding.layoutTitle.setTitle("Fun2");
    }

    @Override
    public void onInitListener() {
        mViewBinding.setClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_shutdown:
//                NetCallBack callBack = new NetCallBack<VersionInfo>() {
//                } ;
//                VersionInfo info = new VersionInfo("1","1","2") ;
//                String s = JSONUtils.putValue(new String[]{"code","message","data"},new String[]{"1","sss",JSONUtils.objToJson(info).toString()}).toString() ;
//                callBack.netObj.setJsonData(s) ;
//                Log.d(TAG, String.valueOf(callBack.netObj.getClazz())) ;
                mPresenter.shutdown();
                break;
            case R.id.bt_log:
                ActivityUtils.startActivityIntent(getActivity(), ErrorLogActivity.class);
                break;
            case R.id.bt_get_version:
                ApkUpdateUtils.checkVersion(mActivity, new ApkUpdateUtils.VersionCallBack() {
                    @Override
                    public void onVersion(VersionInfo info) {
                        if (info == null) {
                            showToast("已是最新版本");
                        } else {
                            showToast("有最新版本：" + info.getVersion());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        showToast(msg);
                    }
                });
                break;
            case R.id.bt_down_apk:
                HttpClient.downloadAsyn("http://192.168.2.254:8080/test.apk", FileUtils.getSaveApkDirPath(mActivity), DOWNLOAD_APK_SAVE_NAME, new NetCallBack() {
                    @Override
                    public void onStart(long length) {
                        super.onStart(length);
                    }

                    @Override
                    public void onComplete(String filePath) {
                        super.onComplete(filePath);
                        mViewBinding.setSpeed("下载速度：0B/s");
                        mViewBinding.setPath(filePath);
                    }

                    @Override
                    public void onProgress(float progress, String realSpped) {
                        super.onProgress(progress, realSpped);
                        mViewBinding.setProgress(String.format("下载进度：%.2f%s", progress, "%"));
                        mViewBinding.setSpeed(String.format("下载速度：%s", realSpped));
                    }

                    @Override
                    public void onNetError(String des) {
                        super.onNetError(des);
                        showToast(des);
                    }

                });
                break;
        }
    }

    @Override
    public void onShutdownCallback(boolean result) {
        showToast(result ? "指令下达成功" : "关机失败");
    }
}
