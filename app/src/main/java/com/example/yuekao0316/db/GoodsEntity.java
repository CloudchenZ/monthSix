package com.example.yuekao0316.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GoodsEntity {
    @Id(autoincrement = true)
    Long id;
    @NotNull
    String pic;
    @NotNull
    int price;
    @NotNull
    String title;
    @Generated(hash = 498527640)
    public GoodsEntity(Long id, @NotNull String pic, int price,
            @NotNull String title) {
        this.id = id;
        this.pic = pic;
        this.price = price;
        this.title = title;
    }
    @Generated(hash = 223916156)
    public GoodsEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
   
}
