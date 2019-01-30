package com.leo.pro.fun1.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.leo.pro.R;
import com.leo.pro.app.entity.MyDataInfo;
import com.leo.pro.app.utils.GlideUtils;
import com.leo.pro.databinding.ItemListDataBinding;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:50
 */

public class MyListDataAdapter extends RecyclerView.Adapter<MyListDataAdapter.MyHolder> {
    private ArrayList<MyDataInfo> mDataInfos ;
    private Context mContext ;

    public MyListDataAdapter(ArrayList<MyDataInfo> mDataInfos, Context mContext) {
        this.mDataInfos = mDataInfos;
        this.mContext = mContext;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_list_data,null) ;
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mDataInfos.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private final ItemListDataBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
        public void setData(int position){
            binding.setDataInfo(mDataInfos.get(position));
            GlideUtils.loadStringBitmap(mDataInfos.get(position).getImgUrl(),binding.ivImg);
        }
    }
}
