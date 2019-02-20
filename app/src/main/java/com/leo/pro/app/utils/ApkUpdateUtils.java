package com.leo.pro.app.utils;

import android.content.Context;

import com.leo.pro.app.entity.VersionInfo;
import com.leo.pro.app.net.HttpClient;
import com.leo.pro.app.net.NetCallBack;
import com.leo.pro.app.net.NetObj;

import java.io.File;

import okhttp3.Request;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.leo.pro.app.net.RequestModelConfig.CHECK_VERSION_NAME;
import static com.leo.pro.app.net.RequestModelConfig.SERVER_BASE_ADDRESS;

/**
 * 创建人 LEO
 * 创建时间 2019/2/13 11:51
 */

public class ApkUpdateUtils {
    private static final String TAG = "ApkUpdateUtils" ;
    public interface VersionCallBack {
        void onVersion(VersionInfo info);

        void onError(String msg);
    }

    /**
     * 检查版本
     *
     * @return
     */
    public static void checkVersion(final Context context, final VersionCallBack callBack) {
        HttpClient.downloadAsyn(SERVER_BASE_ADDRESS+CHECK_VERSION_NAME, FileUtils.getSaveVersionDirPath(context),"", new NetCallBack() {
            @Override
            public void onRequestComplete() {
                super.onRequestComplete();
            }

            @Override
            public void onNetError(String des) {
                super.onNetError(des);
                callBack.onError("网络错误");
            }

            @Override
            public void onError(Request request, Exception e) {
                super.onError(request, e);
                callBack.onError("获取信息失败");
            }

            @Override
            public void onResponse(NetObj object) {
                super.onResponse(object);
                if ("1".equals(object.getCode())) {
                    compareVersion(context, callBack);
                } else {
                    callBack.onError(object.getMessge());
                }
            }
        });
    }

    /**
     * 比对版本号
     *
     * @param context
     * @param callBack
     */
    private static void compareVersion(final Context context, final VersionCallBack callBack) {
        Observable.just(FileUtils.getSaveVersionFilePath(context))
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, VersionInfo>() {
                    @Override
                    public VersionInfo call(String s) {
                        VersionInfo info = null;
                        String result = FileUtils.readFile(new File(s)) ;
                        info = (VersionInfo) JSONUtils.jsonToObject(result, VersionInfo.class);
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VersionInfo>() {
                    @Override
                    public void call(VersionInfo versionInfo) {
                        String oldVersion = AppUtils.getVersionName(context).replace(".", "").trim();
                        String newVersion = versionInfo.getVersion().replace(".", "").trim();
                        int differ = oldVersion.length() - newVersion.length();
                        for (int i = 0; i < Math.abs(differ); i++) {
                            if (differ > 0) {
                                newVersion = newVersion + "0";
                            } else {
                                oldVersion = oldVersion + "0";
                            }
                        }
                        int i = newVersion.compareTo(oldVersion);
                        if (i > 0) {//有最新版本
                            callBack.onVersion(versionInfo);
                        } else {//已是最新版本
                            callBack.onVersion(null);
                        }
                    }
                });
    }
}
