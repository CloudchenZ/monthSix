package com.example.yuekao0316.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yuekao0316.R;
import com.example.yuekao0316.db.GwcEntity;

import java.util.List;

public class GouWcAdapter extends BaseQuickAdapter<GwcEntity, BaseViewHolder> {
    public GouWcAdapter(int layoutResId, @Nullable List<GwcEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GwcEntity item) {
        helper.setText(R.id.list_num,item.getNum()+"");
        helper.setText(R.id.title_list_gwc,item.getTitile());
        Glide.with(mContext).load(item.getPic()).transform(new CenterCrop()).into((ImageView) helper.getView(R.id.iv_list_gwc));
        helper.addOnClickListener(R.id.cbx);
    }
}
