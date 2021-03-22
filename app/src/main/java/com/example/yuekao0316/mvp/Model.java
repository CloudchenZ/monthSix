package com.example.yuekao0316.mvp;

import com.example.yuekao0316.Entity.FoodEntity;
import com.example.yuekao0316.http.XiaApi;
import com.example.yuekao0316.mvp.MyContract.iModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1
public class Model implements iModel{
    @Override
    public void Getdata(Observer<FoodEntity> observer, int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.qubaobei.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(XiaApi.class).getFoodBean(1,20,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
