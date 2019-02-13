package com.leo.pro.app.customView;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.leo.pro.R;
import com.leo.pro.app.utils.DateUtils;
import com.leo.pro.app.utils.Res;
import com.leo.pro.app.utils.ScreenUtils;


/**
 * 创建人 LEO
 * 创建时间 2018/11/28 15:53
 */
public class CustomDatePickerDialog extends View {
    private static final String TAG = "CustomDatePickerDialog";
    private static final int[] MONTHS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};//一年12个月
    private final Context context;
    private int[] mYearMonths ;
    private Paint paint;
    private Paint paintTitleText;
    private String mLeftText = "取消";
    private String mRightText = "确定";
    private String mTitle = "选择日期";
    private int mTitleHeight;//title高
    private int mTitleWidth;//title宽
    private int mTitleOutPadding;//title外边距
    private Rect mLeftButtonRect;//左边按钮区域
    private Rect mRightButtonRect;//右边按钮区域
    private Rect mTitleRect;
    private Paint mLeftButtonRectPaint;
    private Paint mRightButtonRectPaint;
    private int mTotalHeight;
    private int mMostEarlyYear = 1971;//最早年
    private int mMostEarlyMonth = 1;//最早月
    private int mCurrentYear; //当前年
    private int mCurrentMonth; //当前月
    private int mCurrentShowYear;//显示年
    private int mCurrentShowMonth;//显示月
    private int mCenterTempMonthY = Integer.MAX_VALUE;
    private int mCenterMonthY;
    private int mCenterYearY;
    private int mCenterTempYearY = Integer.MAX_VALUE;
    private Rect mYearRect;
    private Rect mMonthRect;
    private Rect mCenterRect;
    private int mPickerYear;
    private int mPickerMonth;
    private int mMoveCenterYear;
    private int mMoveCenterMonth;
    private DatePickerResultListener pickerResultListener;
    private AlertDialog dialog;

    public interface DatePickerResultListener {
        void onPicker(int year, int month);
    }

    private CustomDatePickerDialog(Context context) {
        this(context, null);
    }

    private CustomDatePickerDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private CustomDatePickerDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPickerYear = mCurrentShowYear = mCurrentYear = DateUtils.getCurrentYear();
        mPickerMonth = mCurrentShowMonth = mCurrentMonth = DateUtils.getCurrentMonth();
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTotalHeight = (int) Res.getDimen(R.dimen.x400, context);
        setMeasuredDimension(ScreenUtils.getScreenWidth(context), mTotalHeight);
    }
    /**
     * 初始化画笔
     */
    private void initPaint() {
        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(Res.getDimen(R.dimen.x32, context));
        }
        paint.setColor(Res.getColorRes(R.color.app_main_color, context));
        if (paintTitleText == null) {
            paintTitleText = new Paint();
            paintTitleText.setAntiAlias(true);
            paintTitleText.setStyle(Paint.Style.FILL);
        }
        paintTitleText.setColor(Color.WHITE);
        paintTitleText.setTextSize(Res.getDimen(R.dimen.x28, context));
        if (mLeftButtonRectPaint == null)
            mLeftButtonRectPaint = new Paint();
        if (mRightButtonRectPaint == null)
            mRightButtonRectPaint = new Paint();
        mLeftButtonRectPaint.setColor(Res.getColorRes(R.color.app_main_color, context));
        mRightButtonRectPaint.setColor(Res.getColorRes(R.color.app_main_color, context));

    }

    private float mDownX;
    private float mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoveCenterMonth = mCenterTempMonthY;
                mMoveCenterYear = mCenterTempYearY;
                mDownX = event.getX();
                mDownY = event.getY();
                if (pointIsInsideRect(event.getX(), event.getY(), mLeftButtonRect)) {
                    mLeftButtonRectPaint.setColor(Color.parseColor("#1F95E8"));
                    invalidate(mLeftButtonRect);//重画title区域
                }
                if (pointIsInsideRect(event.getX(), event.getY(), mRightButtonRect)) {
                    mRightButtonRectPaint.setColor(Color.parseColor("#1F95E8"));
                    invalidate(mRightButtonRect);//重画title区域
                }
                break;
            case MotionEvent.ACTION_UP:
                if (pointIsInsideRect(event.getX(), event.getY(), mLeftButtonRect) && pointIsInsideRect(mDownX, mDownY, mLeftButtonRect)) {
                    dialog.dismiss();
                }
                if (pointIsInsideRect(event.getX(), event.getY(), mRightButtonRect) && pointIsInsideRect(mDownX, mDownY, mRightButtonRect)) {
                    if (pickerResultListener != null) {
                        pickerResultListener.onPicker(mPickerYear, mPickerMonth);
                    }
                    dialog.dismiss();
                }
                invalidate(mTitleRect);//重画title区域
                if (pointIsInsideRect(mDownX, mDownY, mYearRect)) {
                    if (mCenterTempYearY >= mCenterYearY) {
                        int count = Math.round(Float.valueOf(mCenterTempYearY - mCenterYearY) / mCenterRect.height());
                        if (count > mCurrentShowYear - mMostEarlyYear) {
                            count = mCurrentShowYear - mMostEarlyYear;
                        }
                        mCenterTempYearY = mCenterYearY + count * mCenterRect.height();
                    } else {
                        int count = Math.round(Float.valueOf(mCenterYearY - mCenterTempYearY) / mCenterRect.height());
                        if (count > mCurrentYear - mCurrentShowYear) {
                            count = mCurrentYear - mCurrentShowYear;
                        }
                        mCenterTempYearY = mCenterYearY - count * mCenterRect.height();
                    }
                    invalidate(mYearRect);
                } else if (pointIsInsideRect(mDownX, mDownY, mMonthRect)) {
                    if (mCenterTempMonthY >= mCenterMonthY) {
                        int count = Math.round(Float.valueOf(mCenterTempMonthY - mCenterMonthY) / mCenterRect.height());
                        if (count > mCurrentShowMonth - 1) {
                            count = mCurrentShowMonth - 1;
                        }
                        mCenterTempMonthY = mCenterMonthY + count * mCenterRect.height();
                    } else {
                        int count = Math.round(Float.valueOf(mCenterMonthY - mCenterTempMonthY) / mCenterRect.height());
                        if (count > mYearMonths.length - mCurrentShowMonth) {
                            count = mYearMonths.length - mCurrentShowMonth;
                        }
                        mCenterTempMonthY = mCenterMonthY - count * mCenterRect.height();
                    }
                    invalidate(mMonthRect);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointIsInsideRect(mDownX, mDownY, mYearRect)) {//滑动年份
                    mCenterTempYearY = mMoveCenterYear + Math.round(event.getY() - mDownY);
                    invalidate(mYearRect);
                } else if (pointIsInsideRect(mDownX, mDownY, mMonthRect)) {//滑动月份
                    mCenterTempMonthY = mMoveCenterMonth + Math.round(event.getY() - mDownY);
                    invalidate(mMonthRect);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(drawTitle(), 0, 0, paint);
        canvas.drawBitmap(drawContent(), 0, mTitleHeight, paint);
    }

    /**
     * 绘制日期主体
     *
     * @return
     */
    private Bitmap drawContent() {
        Bitmap bitmap = Bitmap.createBitmap(mTitleWidth, mTotalHeight - mTitleHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mCenterRect = new Rect(0, canvas.getHeight() / 2 - mTitleHeight / 2, mTitleWidth, canvas.getHeight() / 2 + mTitleHeight / 2);
        paint.setColor(Color.parseColor("#EAF5FE"));
        canvas.drawRect(mCenterRect, paint);
        initPaint();
        canvas.drawLine(0, canvas.getHeight() / 2 - mTitleHeight / 2, mTitleWidth, canvas.getHeight() / 2 - mTitleHeight / 2, paint);
        canvas.drawLine(0, canvas.getHeight() / 2 + mTitleHeight / 2, mTitleWidth, canvas.getHeight() / 2 + mTitleHeight / 2, paint);
        paint.setColor(Color.parseColor("#F3F3F3"));
        canvas.drawLine(mTitleWidth / 2, mTitleHeight + mTitleHeight / 2, mTitleWidth / 2, mTitleHeight - mTitleHeight / 2, paint);//画分割线
        initPaint();
        Rect showYearRect = new Rect();
        paint.getTextBounds(getYearStr(mCurrentShowYear), 0, getYearStr(mCurrentShowYear).length(), showYearRect);//测量显示年尺寸
        mCenterYearY = mCenterRect.top + mCenterRect.height() / 2 + showYearRect.height() / 4;
        if (mCenterTempYearY == Integer.MAX_VALUE)
            mCenterTempYearY = mCenterYearY;
//        mYearRect = new Rect(mTitleWidth / 4 - showYearRect.width() / 2,mTitleHeight,mTitleWidth / 4 + showYearRect.width() / 2,mTotalHeight);
        mYearRect = new Rect(0, mTitleHeight, mTitleWidth / 2, mTotalHeight);
        initPaint();
        Rect showMonthRect = new Rect();
        paint.getTextBounds(getYearStr(mCurrentShowMonth), 0, getYearStr(mCurrentShowMonth).length(), showMonthRect);//测量显示年尺寸
        mCenterMonthY = mCenterRect.top + mCenterRect.height() / 2 + showMonthRect.height() / 4;
        if (mCenterTempMonthY == Integer.MAX_VALUE)
            mCenterTempMonthY = mCenterMonthY;
        mMonthRect = new Rect(mTitleWidth / 2, mTitleHeight, mTitleWidth, mTotalHeight);
        paint.setColor(Color.parseColor("#D4DBEE"));
        canvas.drawLine(mTitleWidth / 2, mTitleHeight / 2, mTitleWidth / 2, canvas.getHeight() - mTitleHeight / 2, paint);//画分割线
        initPaint();
        //绘制年份
        for (int i = 0; i < mCurrentYear - mMostEarlyYear + 1; i++) {
            int tempY;
            if (mCurrentShowYear <= mCurrentYear - i) {
                tempY = mCenterTempYearY + (mCurrentYear - i - mCurrentShowYear) * mCenterRect.height();
            } else {
                tempY = mCenterTempYearY - (mCurrentShowYear - mCurrentYear + i) * mCenterRect.height();
            }
            if (pointIsInsideRect(mTitleWidth / 4 - showYearRect.width() / 2, tempY, mCenterRect)) {
                paint.setColor(Res.getColorRes(R.color.app_main_color, context));
                mPickerYear = mCurrentYear - i;
                updateYearMonths(mPickerYear);
            } else {
                paint.setColor(Color.BLACK);
            }
            canvas.drawText(getYearStr(mCurrentYear - i), mTitleWidth / 4 - showYearRect.width() / 2, tempY, paint);
        }
        initPaint();
        if (mPickerMonth > mYearMonths.length){//当前选择年 的当前月没在12月份
            mCenterTempMonthY = mCenterTempMonthY + (mPickerMonth-mYearMonths.length) * mCenterRect.height() ;
        }
        for (int i = 0; i < mYearMonths.length; i++) {
            int tempY;
            if (mCurrentShowMonth <= mYearMonths[i]) {
                tempY = mCenterTempMonthY + (mYearMonths[i] - mCurrentShowMonth) * mCenterRect.height();
            } else {
                tempY = mCenterTempMonthY - (mCurrentShowMonth - mYearMonths[i]) * mCenterRect.height();
            }
            if (pointIsInsideRect(mTitleWidth * 3 / 4 - showMonthRect.width() / 2, tempY, mCenterRect)) {
                paint.setColor(Res.getColorRes(R.color.app_main_color, context));
                mPickerMonth = mYearMonths[i];
            } else {
                paint.setColor(Color.BLACK);
            }
            canvas.drawText(getMonthStr(mYearMonths[i]), mTitleWidth * 3 / 4 - showMonthRect.width() / 2, tempY, paint);
        }
        initPaint();
        return bitmap;
    }

    /**
     * 绘制title
     *
     * @return
     */
    private Bitmap drawTitle() {
        mTitleHeight = (int) Res.getDimen(R.dimen.x80, context);
        mTitleWidth = ScreenUtils.getScreenWidth(context);
        mTitleOutPadding = (int) Res.getDimen(R.dimen.x26, context);
        mTitleRect = new Rect(0, 0, mTitleWidth, mTitleHeight);//title区域
        Bitmap bitmap = Bitmap.createBitmap(mTitleWidth, mTitleHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Res.getColorRes(R.color.app_main_color, context));
        paintTitleText.measureText(mLeftText);

        Rect leftTextRect = new Rect();
        paint.getTextBounds(mLeftText, 0, mLeftText.length(), leftTextRect);//测量左边汉子的尺寸
        mLeftButtonRect = new Rect(0, 0, mTitleOutPadding * 2 + leftTextRect.width(), mTitleHeight);
        canvas.drawRect(mLeftButtonRect, mLeftButtonRectPaint);
        canvas.drawText(mLeftText, mTitleOutPadding, mTitleHeight / 2 + leftTextRect.height() / 4, paintTitleText);//画取消按钮

        Rect titleTextRect = new Rect();
        paint.getTextBounds(mTitle, 0, mTitle.length(), titleTextRect);//测量中间汉子的尺寸
        canvas.drawText(mTitle, mTitleWidth / 2 - titleTextRect.width() / 2, mTitleHeight / 2 + titleTextRect.height() / 4, paintTitleText);//画title

        Rect rightTextRect = new Rect();
        paint.getTextBounds(mRightText, 0, mLeftText.length(), rightTextRect);//测量右边汉子的尺寸
        mRightButtonRect = new Rect(mTitleWidth - mTitleOutPadding * 2 - rightTextRect.width(), 0, mTitleWidth, mTitleHeight);
        canvas.drawRect(mRightButtonRect, mRightButtonRectPaint);
        canvas.drawText(mRightText, mTitleWidth - mTitleOutPadding - rightTextRect.width(), mTitleHeight / 2 + rightTextRect.height() / 4,
                paintTitleText);//画确定按钮
        initPaint();//重置画笔
        return bitmap;
    }

    public AlertDialog showDialog() {
        if(dialog == null){
            dialog = new AlertDialog.Builder(context).create();
            dialog.show();
            dialog.setContentView(this);
            dialog.setCancelable(true);//点击外部不可dismiss
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(null);
            window.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.horizontalMargin = 0;
            window.setAttributes(params);
        }
        return dialog;
    }

    /**
     * 判断某坐标是否在指定区域
     *
     * @param x
     * @param y
     * @param rect
     */
    private boolean pointIsInsideRect(float x, float y, Rect rect) {
        if (x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom) {
            return true;
        }
        return false;
    }

    /**
     * 更新当前年的月份
     * @param year
     */
    private void updateYearMonths(int year){
        if(year == mCurrentYear){
            mYearMonths = new int[mCurrentMonth] ;
            for(int i = 0; i < mCurrentMonth ; i++){
                mYearMonths[i] = i+1 ;
            }
        }else{
            mYearMonths = MONTHS ;
        }
    }

    /**
     * 月份
     *
     * @param month
     * @return
     */
    private String getMonthStr(int month) {
        return String.format(month < 10 ? "0%d月" : "%d月", month);
    }

    /**
     * 年份
     *
     * @param year
     * @return
     */
    private String getYearStr(int year) {
        return String.format("%d年", year);
    }

    public static class Builder{

        private final CustomDatePickerDialog pickerDialog;

        public Builder(Context context) {
            pickerDialog = new CustomDatePickerDialog(context);
        }
        public void show(){
            pickerDialog.showDialog() ;
        }
        /**
         * 设置当前显示年
         * @param year
         * @return
         */
        public Builder setShowYear(int year) {
            pickerDialog.mPickerYear = pickerDialog.mCurrentShowYear = year;
            return this;
        }

        /**
         * 设置选择监听
         * @param pickerResultListener
         * @return
         */
        public Builder setPickerResultListener(DatePickerResultListener pickerResultListener) {
            pickerDialog.pickerResultListener = pickerResultListener;
            return this;
        }

        /**
         * 设置当前显示月
         * @param Month
         * @return
         */
        public Builder setShowMonth(int Month) {
            pickerDialog.mPickerMonth = pickerDialog.mCurrentShowMonth = Month;
            return this;
        }
    }
}
