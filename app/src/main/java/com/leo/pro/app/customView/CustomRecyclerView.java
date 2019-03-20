package com.leo.pro.app.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.pro.R;
import com.leo.pro.app.base.BaseRecyclerViewAdapter;
import com.leo.pro.app.utils.Res;
import com.leo.pro.app.utils.ScreenUtils;
import com.leo.pro.app.utils.ViewUtils;

import static android.support.v4.view.ViewCompat.TYPE_TOUCH;

/**
 * Created by HX·罗 on 2017/7/14.
 */

public class CustomRecyclerView extends RecyclerView {

    private boolean isLoading = true;
    private int currentPage = 1;
    private int mWidth = 0;
    private int mDividerHeight;
    private int mDividerColor;
    private int mLayoutLeft = 0;
    private int mLayoutTop = 0;
    private int mLayoutRight = 0;
    private int mLayoutBottom = 0;
    private boolean needLoadMore;
    private boolean needRefresh;
    private Context context;
    private ValueAnimator animator;
    private int mTouchSlop = 4;//最小滑动距离
    private int headHeight;
    private RecyclerViewHeader viewHeader = null;

    public void resetPage() {
        currentPage = 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public interface ListActionListener {
        void onLoadMore(int currentPage);

        void onRefresh();
    }

    private ListActionListener actionListener;

    public void setActionListener(ListActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public CustomRecyclerView(Context context) {
        this(context, null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        mWidth = ScreenUtils.getScreenWidth(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView);
        mDividerHeight = (int) ta.getDimension(R.styleable.CustomRecyclerView_dividerHeight, Res.getDimen(R.dimen.x2, context));
        mDividerColor = ta.getColor(R.styleable.CustomRecyclerView_dividerColor, Res.getColorRes(R.color.divider_color, context));
        needLoadMore = ta.getBoolean(R.styleable.CustomRecyclerView_needLoadMore, true);
        needRefresh = ta.getBoolean(R.styleable.CustomRecyclerView_needRefresh, true);
        mDividerColor = ta.getColor(R.styleable.CustomRecyclerView_dividerColor, Res.getColorRes(R.color.divider_color, context));
        init(context);
    }

    /**
     * 关闭刷新状态
     */
    public void setEndLoading(){
        if(viewHeader != null){
            viewHeader.endLoading();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (!canScrollVertically(-1)) {
                if (viewHeader == null) {
                    viewHeader = (RecyclerViewHeader) getChildAt(0);
                }
                if (animator != null && animator.isRunning()) {
                    animator.cancel();
                }
                viewHeader.endLoading();
                viewHeader.setEffective(true);
                startY = e.getRawY();
            }
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (actionListener != null && needRefresh) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if ((getTop() > mLayoutTop || (!canScrollVertically(-1) && e.getRawY() > startY)) && hasHeadView) {
                        float offsetY = (e.getRawY() - startY) / 4;
                        viewHeader.setState(1,actionListener);
                        viewHeader.setVisibleHeight((int) offsetY);
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    viewHeader.setState(2,actionListener);
                    break;
            }
        }
        return super.onTouchEvent(e);
    }

    private void init(Context context) {

        addOnItemTouchListener(new OnItemTouchListener() {
            float mStartY;
            boolean mStartScroll = false;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartScroll = false;
                        mStartY = e.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dY = e.getRawY() - mStartY;
                        if (!canScrollVertically(-1) && dY > mTouchSlop) {
                            mStartScroll = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        stopNestedScroll(TYPE_TOUCH);
                        break;
                }
                return mStartScroll;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, mDividerHeight, mDividerColor));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            protected int getExtraLayoutSpace(State state) {
                return 20;
            }
        };
        setLayoutManager(linearLayoutManager);

        try {
            addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    if (actionListener != null && needLoadMore) {
                        actionListener.onLoadMore(currentPage);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (getChildCount() > 0 && headHeight == 0) {
//            headHeight = ViewUtils.getViewHeight(getChildAt(0)) ;
//            MarginLayoutParams  params = (MarginLayoutParams) getLayoutParams();
//            params.topMargin = -headHeight ;
//            setLayoutParams(params);
//            setPadding(0,-headHeight,0,0);
//            System.out.print("");
        }
    }

//    /**
//     * 设置数据适配器
//     *
//     * @param adapter
//     */
//    public void setAdapter(BaseRecyclerViewAdapter adapter) {
//        super.setAdapter(adapter);
//    }

    /**
     * 是否有headView
     */
    private boolean hasHeadView = false;

    @Override
    public void setAdapter(Adapter adapter) {
        hasHeadView = BaseRecyclerViewAdapter.class.isAssignableFrom(adapter.getClass());
        if (BaseRecyclerViewAdapter.class.isAssignableFrom(adapter.getClass())) {
        }
        super.setAdapter(adapter);
    }

    public abstract class EndlessRecyclerOnScrollListener extends OnScrollListener {
        int totalItemCount, lastVisibleItem, currentItemCount;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(
                LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0 && dy > dx) {
                totalItemCount = mLinearLayoutManager.getItemCount();
                lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (!isLoading
                        && lastVisibleItem != -1 && lastVisibleItem == totalItemCount - 1) {
                    isLoading = true;
                    if (totalItemCount > currentItemCount || currentPage == 1) {//加载更多成功且有更多数据 那么当前页+1
                        currentPage++;
                    }
                    currentItemCount = totalItemCount;
                    onLoadMore(currentPage);
                }
            }
        }

        public abstract void onLoadMore(int currentPage);
    }

    private float startY = 0;

    //    private boolean initHead = false ;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if ((mLayoutLeft == 0 && mLayoutTop == 0 && mLayoutRight == 0 && mLayoutBottom == 0)) {
//            if(headHeight > 0){
//                initHead = true ;
//            }
            mLayoutLeft = getLeft();
            mLayoutTop = getTop();
            mLayoutRight = getRight();
            mLayoutBottom = getBottom();
        }
    }


}
