package com.leo.pro.app.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.utils.Res;
import com.leo.pro.app.utils.ScreenUtils;

/**
 * Created by HX·罗 on 2017/7/14.
 */

public class CustomRecyclerView extends RecyclerView {

    private boolean isLoading = true;
    private int currentPage = 1;
    private int mWidth = 0 ;
    private int mDividerHeight;
    private int mDividerColor;


    public void resetPage() {
        currentPage = 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public interface LoadMoreListener {
        void onLoadMore(int currentPage);
    }

    private LoadMoreListener loadMoreListener;

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public CustomRecyclerView(Context context) {
        this(context, null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        mWidth = ScreenUtils.getScreenWidth(context) ;
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView);
        mDividerHeight = (int) ta.getDimension(R.styleable.CustomRecyclerView_dividerHeight, Res.getDimen(R.dimen.x2,context));
        mDividerColor = ta.getColor(R.styleable.CustomRecyclerView_dividerColor, Res.getColorRes(R.color.divider_color,context));
        init(context);
    }

    private void init(Context context) {
//        setBackgroundColor(decorationColor);
        addItemDecoration(new RecycleViewDivider(context,LinearLayoutManager.VERTICAL,mDividerHeight,mDividerColor));
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
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore(currentPage);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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


}
