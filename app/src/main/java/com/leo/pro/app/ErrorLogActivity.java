package com.leo.pro.app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo.pro.R;
import com.leo.pro.app.base.Base2Activity;
import com.leo.pro.app.utils.CrashHandler;
import com.leo.pro.app.utils.DiaLogUtils;
import com.leo.pro.app.utils.FileUtils;
import com.leo.pro.app.utils.Res;
import com.leo.pro.app.utils.TextUtil;
import com.leo.pro.databinding.ActivityErrorLogBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.leo.pro.app.utils.Res.getDimen;

/**
 * 创建人 LEO
 * 创建时间 2019/2/12 14:55
 */

public class ErrorLogActivity extends Base2Activity<ActivityErrorLogBinding> {
    private ArrayList<String> mLogInfos = new ArrayList<>();
    private AlertDialog mDialog;
    private TextView mLog;
    private AlertDialog mPrefressDialog;

    @Override
    public int onSetContentView() {
        return R.layout.activity_error_log;
    }

    @Override
    public void onInitView() {
        mLog = new TextView(mContext);
        mLog.setBackgroundColor(Color.WHITE);
        mLog.setTextColor(Color.BLACK);
        mLog.setMovementMethod(ScrollingMovementMethod.getInstance());
        mLog.setPadding((int) getDimen(R.dimen.outside_padding, mContext), (int) getDimen(R.dimen.outside_padding, mContext), (int) getDimen(R.dimen.outside_padding, mContext), (int) getDimen(R.dimen.outside_padding, mContext));
        mLog.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onInitData() {
        mViewBinding.layoutTitle.setTitle("错误日志");
        initCrashData();
    }

    /**
     * 初始化日志文件
     */
    private void initCrashData() {
        File crashDir = new File(CrashHandler.LOG_FILE_ADDRESS);
        if (crashDir.exists()) {
            File[] files = crashDir.listFiles();
            for (int i = files.length - 1; i >= 0; i--) {
                File file = files[i];
                mLogInfos.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
            }
        }
        LogAdapter adapter = new LogAdapter();
        mViewBinding.crvLog.setAdapter(adapter);
    }

    @Override
    public void onInitListener() {
        mViewBinding.layoutTitle.setViewClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 显示错误日志dialog
     */
    private void showDialog() {
        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(mContext).create();
            mDialog.setView(mLog, 0, 0, 0, 0);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
        }
        mDialog.show();
    }

    class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(mContext);
            tv.setPadding((int) getDimen(R.dimen.outside_padding, mContext), (int) getDimen(R.dimen.outside_padding, mContext), 0, (int) getDimen(R.dimen.outside_padding, mContext));
            tv.setBackgroundDrawable(Res.getDrawableRes(R.drawable.selector_white_bg, mContext));
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            return new MyHolder(tv);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.setData(position);
        }

        @Override
        public int getItemCount() {
            return mLogInfos.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            TextView mTv;
            private int position;

            public MyHolder(View itemView) {
                super(itemView);
                mTv = (TextView) itemView;
                mTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoding(position);
                    }
                });
            }

            /**
             * 设置数据
             *
             * @param position
             */
            public void setData(int position) {
                this.position = position;
                mTv.setText(mLogInfos.get(position));
            }
        }
    }

    /**
     * 显示加载loading
     */
    private void showLoding(int position) {
        if (mPrefressDialog == null) {
            mPrefressDialog = DiaLogUtils.showDialog(mContext, "加载数据中", null);
        } else {
            mPrefressDialog.show();
        }
        Observable.just(position)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        String result = null;
                        String fileName = mLogInfos.get(integer);
                        File logFile = new File(CrashHandler.LOG_FILE_ADDRESS + File.separator + fileName + ".log");
                        if (logFile.exists()) {
                            result = new String(FileUtils.readFile(logFile));
                        }
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mPrefressDialog.dismiss();
                        if (!TextUtil.isEmpty(s)) {
                            mLog.setText(s);
                            showDialog();
                        } else {
                            showShortToast("数据加载失败");
                        }
                    }
                });
    }

}
