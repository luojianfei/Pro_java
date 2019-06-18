package com.leo.pro.app.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leo.pro.R;
import com.leo.pro.app.customView.RecyclerViewHeader;
import com.leo.pro.app.utils.ViewUtils;
import com.leo.pro.databinding.ItemRecyclerHeadBinding;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/2/22 17:03
 */

public abstract class BaseRecyclerViewAdapter<T extends Object> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.CustomHolder> {
    public static final int TYPE_ITEM_HEAD = 0;//headView
    public static final int TYPE_ITEM_OTHER = 1;//other View
    public Context context;
    public ArrayList<T> dataInfos;
    private View headView;

    public BaseRecyclerViewAdapter(Context context, ArrayList<T> dataInfos) {
        this.context = context;
        this.dataInfos = dataInfos;
    }

    @Override
    public final int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_HEAD;
        } else {
            return getItemViewTypeWhithoutHead(position);
        }
    }

    public int getItemViewTypeWhithoutHead(int position) {//除了 HeadView之外的itemView
        return TYPE_ITEM_OTHER;
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_HEAD) {
            headView = new RecyclerViewHeader(context);
            return new CustomHolder(headView);
        } else {
            return getItemView(parent, viewType);
        }
    }

    public abstract CustomHolder getItemView(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(CustomHolder holder, int position) {
        if (position > 0) {
            setData(holder, position - 1);
        }
    }

    public abstract void setData(CustomHolder holder, int position);

    @Override
    public final int getItemCount() {
        return dataInfos.size() + 1;
    }

    public static class CustomHolder<G extends ViewDataBinding> extends RecyclerView.ViewHolder {

        public G viewBinding;

        public CustomHolder(View itemView) {
            super(itemView);
            try {
                viewBinding = DataBindingUtil.bind(itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
