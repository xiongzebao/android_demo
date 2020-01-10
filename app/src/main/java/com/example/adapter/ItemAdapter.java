package com.example.adapter;



import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android_demo.R;
import com.example.bean.ItemBean;


import java.util.List;

public class ItemAdapter extends BaseMultiItemQuickAdapter<ItemBean, BaseViewHolder> {

    private Context context;


    public ItemAdapter(Context context, List data) {
        super(data);
        this.context = context;
        addItemType(0,R.layout.layout_sign_item);

    }


    @Override
    protected void convert(BaseViewHolder helper,  ItemBean item) {
        if(item.getItemType()==0){
            if(item.state==0){
                helper.setBackgroundColor(R.id.root,context.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                helper.setBackgroundColor(R.id.root,context.getResources().getColor(R.color.colorPrimary));
            }
            helper.setText(R.id.txt,item.name);
        }
    }


}
