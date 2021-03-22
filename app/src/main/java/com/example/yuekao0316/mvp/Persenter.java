package com.example.yuekao0316.mvp;

import com.example.yuekao0316.Entity.FoodEntity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Persenter implements MyContract.iPresenter{
    private Model model;
    private MyContract.iView iView;

    public Persenter(MyContract.iView iView) {
        this.iView = iView;
        model = new Model();
    }

    @Override
    public void Getdata(int page) {
        model.Getdata(new Observer<FoodEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FoodEntity foodEntity) {
                iView.OnOk(foodEntity);
            }

            @Override
            public void onError(Throwable e) {
                iView.OnError(110,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        },page);
    }
}
