package com.leo.pro.app.utils;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/28.
 */

public class ViewUtils {

    /**
     * 设置View的灰度
     * @param view  设置灰度的View
     * @param b     为真是设置灰度，假则正常显示
     */
    public static void setImageViewColorMatrix(ImageView view,boolean b) {

        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0); // 设置饱和度
        ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
        if (b){
            view.setColorFilter(grayColorFilter); // 如果想恢复彩色显示，设置为null即可
        }else  {
            view.setColorFilter(null);
        }

    }

//    /**
//     * 当listView没有数据时，加载此View
//     * @param context
//     * @return
//     */
//    public static ImageView getListViewNoDataView(Context context){
//
//        ImageView emptyView = new ImageView(context);
//        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
//        emptyView.setBackgroundResource(R.drawable.nodata);
//
////        emptyView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//        emptyView.setVisibility(View.GONE);
//        return emptyView;
//    }


    /**
     * 测量View的高度
     * @param view
     * @return
     */
    public static int getViewHeight(View view){
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int  height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    /**
     * 测量View的宽度
     * @param view
     * @return
     */
    public static int getViewWidth(View view){
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int  height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredWidth();
    }

}
