package com.example.yuekao0316.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yuekao0316.R;
import com.example.yuekao0316.db.GoodsEntity;

import java.util.List;

public class PopwindowAdapter extends BaseQuickAdapter<GoodsEntity, BaseViewHolder> {
    public PopwindowAdapter(int layoutResId, @Nullable List<GoodsEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsEntity item) {
        helper.setText(R.id.tv_list_history,item.getTitle());
        Glide.with(mContext).load(item.getPic()).transform(new CenterCrop()).into((ImageView) helper.getView(R.id.iv_list_history));
    }
}
