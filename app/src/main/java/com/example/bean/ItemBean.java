package com.example.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ItemBean implements MultiItemEntity {

    public String name;
    public  int state;




    public int type=0;

    public ItemBean(String name, int state) {
        this.name = name;
        this.state = state;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
