package com.example.yuekao0316.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yuekao0316.Entity.FoodEntity;
import com.example.yuekao0316.R;
import com.example.yuekao0316.adapter.RecylAdapter;
import com.example.yuekao0316.db.DaoMaster;
import com.example.yuekao0316.db.DaoSession;
import com.example.yuekao0316.db.GoodsEntity;
import com.example.yuekao0316.mvp.MyContract;
import com.example.yuekao0316.mvp.Persenter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyContract.iView {
    private Persenter persenter;
    private RecylAdapter recylAdapter;
    private RecyclerView rvshou;
    private PullToRefreshLayout ref;
    private List<FoodEntity.DataBean> dataBeans = new ArrayList<>();
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        persenter = new Persenter(this);
        persenter.Getdata(page);
        ref.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                page++;
                persenter.Getdata(page);
                Toast.makeText(MainActivity.this, "加载了20条数据", Toast.LENGTH_SHORT).show();
                recylAdapter.notifyDataSetChanged();
                ref.finishRefresh();
            }

            @Override
            public void loadMore() {
                recylAdapter.notifyDataSetChanged();
                ref.finishLoadMore();
            }
        });
        recylAdapter = new RecylAdapter(R.layout.list, dataBeans);
        rvshou.setAdapter(recylAdapter);
        rvshou.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void OnOk(FoodEntity foodEntity) {
        final List<FoodEntity.DataBean> data = foodEntity.getData();
        dataBeans.addAll(data);
        recylAdapter.notifyDataSetChanged();
        recylAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                intent.putExtra("pic",data.get(position).getPic());
                intent.putExtra("title",data.get(position).getTitle());
                DaoSession daoSession = DaoMaster.newDevSession(MainActivity.this, "goods.db");
                GoodsEntity goodsEntity = new GoodsEntity();
                goodsEntity.setPic(data.get(position).getPic());
                goodsEntity.setTitle(data.get(position).getTitle());
                goodsEntity.setPrice(1000);
                daoSession.insert(goodsEntity);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnError(int code, String msg) {

    }

    private void initView() {
        rvshou = findViewById(R.id.rvshou);
        ref = findViewById(R.id.ref);
    }
}
