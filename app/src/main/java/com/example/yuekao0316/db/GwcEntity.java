package com.example.yuekao0316.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GwcEntity {
    @Id
    Long id;
    @NotNull
    String pic;
    @NotNull
    String titile;
    @NotNull
    int num;
    Boolean isChecked;
    @Generated(hash = 39268045)
    public GwcEntity(Long id, @NotNull String pic, @NotNull String titile, int num,
            Boolean isChecked) {
        this.id = id;
        this.pic = pic;
        this.titile = titile;
        this.num = num;
        this.isChecked = isChecked;
    }
    @Generated(hash = 641256618)
    public GwcEntity() {
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
    public String getTitile() {
        return this.titile;
    }
    public void setTitile(String titile) {
        this.titile = titile;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public Boolean getIsChecked() {
        return this.isChecked;
    }
    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
}
