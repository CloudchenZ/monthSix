package com.example.yuekao0316.http;

import com.example.yuekao0316.Entity.FoodEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

//http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1
public interface XiaApi {
    @GET("ios/cf/dish_list.php")
    Observable<FoodEntity> getFoodBean(@Query("stage_id")int stage_id,@Query("limit")int limit,@Query("page") int page);
}
