package com.leo.pro.fun1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.leo.pro.R;
import com.leo.pro.app.base.BaseRecyclerViewAdapter;
import com.leo.pro.app.entity.MyDataInfo;
import com.leo.pro.app.utils.GlideUtils;
import com.leo.pro.databinding.ItemListDataBinding;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/2/22 17:40
 */

public class MyListAdapter extends BaseRecyclerViewAdapter<MyDataInfo> {

    public MyListAdapter(Context context, ArrayList<MyDataInfo> dataInfos) {
        super(context, dataInfos);
    }

    @Override
    public BaseRecyclerViewAdapter.CustomHolder getItemView(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_list_data,null) ;
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new CustomHolder<ItemListDataBinding>(view);
    }
    @Override
    public void setData(BaseRecyclerViewAdapter.CustomHolder holder, int position) {
        ItemListDataBinding dataBinding = (ItemListDataBinding) holder.viewBinding;
        dataBinding.setDataInfo(dataInfos.get(position));
        GlideUtils.loadStringBitmap(dataInfos.get(position).getImgUrl(),dataBinding.ivImg);
    }

}
