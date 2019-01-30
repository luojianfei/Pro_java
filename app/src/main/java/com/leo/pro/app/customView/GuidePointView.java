package com.leo.pro.app.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.utils.Res;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 11:42
 */

public class GuidePointView extends View {
    private static final int DEFAULT_POINT_RADIU = 5;
    private static final int DEFAULT_POINT_SPACING = 10;
    private static final int DEFAULT_POINT_COLOR = Color.parseColor("#cccccc");
    private int mPointRadiu = 0;//圆点半径
    private int mPointColor = 0;//圆点颜色
    private int mPointCupyColor = 0;//圆点占用颜色
    private int mPointSpacing = 0;//圆点间距
    private int mPointCount = 0;//圆点个数
    private Context mContext;
    private ViewPager mViewPager;
    private Paint mPaint;
    private float mPositionOffset;
    private int mPosition = 0 ;

    public GuidePointView(Context context) {
        this(context, null);
    }

    public void bindViewPager(ViewPager pager, int count) {
        mViewPager = pager;
        mPointCount = count;
        init();
    }

    public GuidePointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GuidePointView, defStyleAttr, 0);
        mPointRadiu = a.getDimensionPixelSize(R.styleable.GuidePointView_pointRadiu, DEFAULT_POINT_RADIU);
        mPointSpacing = a.getDimensionPixelSize(R.styleable.GuidePointView_pointSpacing, DEFAULT_POINT_SPACING);
        mPointColor = a.getColor(R.styleable.GuidePointView_pointColor, DEFAULT_POINT_COLOR);
        mPointCupyColor = a.getColor(R.styleable.GuidePointView_pointCupyColor, Res.getColorRes(R.color.app_main_color, context));
        mContext = context;
    }

    /**
     * 初始化
     */
    private void init() {
        invalidate();
        mPaint = new Paint();
        mPaint.setColor(mPointColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPositionOffset = positionOffset;
                mPosition = position ;
                invalidate();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mViewPager != null) {
            mPaint.setColor(mPointColor);
            for (int i = 0; i < mPointCount; i++) {
                canvas.drawCircle(mPointRadiu + i * (mPointSpacing + mPointRadiu * 2), mPointRadiu, mPointRadiu, mPaint);
            }
            mPaint.setColor(mPointCupyColor);
            int position = mViewPager.getCurrentItem();
            float offset ;
            if(position > mPosition){//向右滑
                offset = (1-mPositionOffset) * (mPointSpacing + mPointRadiu * 2) * -1 ;
            }else{//向左滑
                offset = mPositionOffset * (mPointSpacing + mPointRadiu * 2) ;

            }
            canvas.drawCircle(mPointRadiu + position * (mPointSpacing + mPointRadiu * 2) + offset, mPointRadiu, mPointRadiu, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mPointRadiu * 2 * mPointCount + (mPointCount - 1) * mPointSpacing, mPointRadiu * 2);
    }
}
