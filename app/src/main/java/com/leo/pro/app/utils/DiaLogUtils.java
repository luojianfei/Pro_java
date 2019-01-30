package com.leo.pro.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.leo.pro.R;
import com.leo.pro.databinding.DialogLoadingBinding;

/**
 * Description:显示指定组件的对话框,并跳转至指定的Activity
 */
public class DiaLogUtils {

    public interface DialogDismissListener{
        void onDissmiss(AlertDialog dialog) ;
    }

    /**
     * 数据加载loading dialog
     * @param context
     * @param tip
     * @param dismissListener
     * @return
     */
    public static AlertDialog showDialog(Context context, String tip, final DialogDismissListener dismissListener) {
        final AlertDialog dialog = getAlphaDialog(context);
        View view = View.inflate(context, R.layout.dialog_loading, null);
        DialogLoadingBinding viewBinding = DataBindingUtil.bind(view);
        viewBinding.setTip(tip);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog1) {
                if (dismissListener != null) {
                    dismissListener.onDissmiss(dialog);
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtils.getScreenWidth(context) / 2;
        dialog.getWindow().setAttributes(params);
        return dialog;
    }

    /**
     * 设置dialog背景透明
     */
    private static AlertDialog getAlphaDialog(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        return dialog;
    }

    public static Toast showCenterMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }
}