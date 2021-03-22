package com.example.yuekao0316.mvp;

import com.example.yuekao0316.Entity.FoodEntity;

import io.reactivex.Observer;

public interface MyContract {
    interface iModel{
        void Getdata(Observer<FoodEntity> observer,int page);
    }
    interface iPresenter{
        void Getdata(int page);
    }
    interface iView{
        void OnOk(FoodEntity foodEntity);
        void OnError(int code,String msg);
    }
}
