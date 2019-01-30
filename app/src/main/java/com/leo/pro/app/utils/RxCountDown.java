package com.leo.pro.app.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 10:19
 */

public class RxCountDown {
    private static final String TAG = "RxCountDown" ;
    private Subscription mSub;
    private Observable<Integer> mObservable;
    public interface CountDownListener{
        void onStart() ;
        void onNext(int number) ;
        void onCompleted() ;
        void onError(Throwable e) ;
    }

    public RxCountDown(int mTime) {
        countdown(mTime);
    }

    /**
     * 倒计时
     * @param time
     * @return
     */
    private void countdown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        mObservable = Observable.interval(0, 1, TimeUnit.SECONDS)
//                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);

    }

    /**
     * 开始倒计时
     * @param listener
     */
    public void startCountDown(final CountDownListener listener){
        mSub = mObservable.doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.d(TAG,"开始计时");
                        if(listener != null){
                            listener.onStart();
                        }
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG,"计时完成");
                        if(listener != null){
                            listener.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(listener != null){
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"当前计时：" + integer);
                        if(listener != null){
                            listener.onNext(integer);
                        }
                    }
                });
    }

    /**
     * 取消
     */
    public void cancel(){
        mSub.unsubscribe();
    }

}
