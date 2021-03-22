package com.example.yuekao0316.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yuekao0316.Entity.FoodEntity;
import com.example.yuekao0316.R;

import java.util.List;



public class RecylAdapter extends BaseQuickAdapter<FoodEntity.DataBean, BaseViewHolder> {
    public RecylAdapter(int layoutResId, @Nullable List<FoodEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodEntity.DataBean item) {
        helper.setText(R.id.tvtitle,item.getTitle());
        helper.setText(R.id.tvprice,"1000");
//        TextView textView = helper.getView(R.id.tvprice);
//        textView.setText("1000");
        Glide.with(mContext).load(item.getPic()).transform(new CenterCrop()).into((ImageView) helper.getView(R.id.ivphoto));
    }
}
