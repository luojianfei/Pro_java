package com.leo.pro.app.customView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leo.pro.R;
import com.leo.pro.app.utils.Res;
import com.leo.pro.databinding.ItemRecyclerHeadBinding;

/**
 * 创建人 LEO
 * 创建时间 2019/3/20 11:41
 */

public class RecyclerViewHeader extends LinearLayout {
    private int mContainerHeight = 0;
    private Context mContext;
    private View mContainer;
    private ItemRecyclerHeadBinding binding;
    private ValueAnimator animator;
    private boolean effective = false;

    public void setEffective(boolean effective) {
        this.effective = effective;
    }

    /**
     * 设置状态
     *
     * @param state
     */
    public void setState(final int state, final CustomRecyclerView.ListActionListener actionListener) {
        binding.setState(1);
        if (state == 2) {
            final int tagValue = getHeight() < mContainerHeight ? 0 : mContainerHeight;
            animator = ValueAnimator.ofInt(getHeight(), tagValue).setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    setHeight(value);
                    if (value == tagValue && value != 0) {
                        if (actionListener != null) {
                            actionListener.onRefresh();
                        }
                        binding.setState(state);
                    }
                }
            });
            animator.start();
        }
    }

    /**
     * 结束刷新状态
     */
    public void endLoading() {
        if (getHeight() != 0) {
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            } else {
                animator = ValueAnimator.ofInt(getHeight(), 0).setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setHeight((Integer) animation.getAnimatedValue());
                    }
                });
                animator.start();
            }
        }
    }

    public RecyclerViewHeader(Context context) {
        this(context, null);
    }

    public RecyclerViewHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mContainerHeight = (int) Res.getDimen(R.dimen.x90, mContext);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        setGravity(Gravity.BOTTOM);
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContainerHeight, Gravity.BOTTOM);
        mContainer = View.inflate(mContext, R.layout.item_recycler_head, null);
        binding = DataBindingUtil.bind(mContainer);
        addView(mContainer, params);
    }

    /**
     * 设置隐藏高度
     *
     * @param height
     */
    public void setVisibleHeight(int height) {
        if (animator != null && animator.isRunning()) {
            effective = false;
        }
        if (effective) {
            setHeight(height);
        }
    }

    /**
     * 设置高度
     * @param height
     */
    private void setHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) getLayoutParams();
        lp.height = height;
        setLayoutParams(lp);
    }
}
