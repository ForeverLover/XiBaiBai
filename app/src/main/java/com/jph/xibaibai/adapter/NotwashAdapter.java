package com.jph.xibaibai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.BeautyItemProduct;

import java.util.List;

/**
 * Created by Eric on 2015/11/9.
 * 非必洗适配器
 */
public class NotwashAdapter extends BaseAdapter{

    private List<BeautyItemProduct> notWashList;

    private int carType = 0;

    private boolean[] checkState = null;

    public NotwashAdapter(List<BeautyItemProduct> notWashList,int carType){
        this.notWashList = notWashList;
        this.carType = carType;
        checkState = new boolean[notWashList.size()];
        initCheckState();
    }
    private void initCheckState(){
        for(int i = 0;i<checkState.length;i++){
            checkState[i] = false;
        }
    }

    public boolean[] getCheckState() {
        return checkState;
    }

    public void setCheckState(boolean[] checkState) {
        this.checkState = checkState;
    }

    @Override
    public int getCount() {
        return notWashList.size();
    }

    @Override
    public Object getItem(int position) {
        return notWashList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beauty_wax,null);
            holder.item_wax_tv = (TextView) convertView.findViewById(R.id.item_wax_tv);
            holder.item_single_price = (TextView) convertView.findViewById(R.id.item_single_price);
            holder.item_selected_img = (ImageView) convertView.findViewById(R.id.item_selected_img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        BeautyItemProduct itemProduct = notWashList.get(position);
        holder.item_wax_tv.setText(itemProduct.getP_name());
        if(carType == 1){
            holder.item_single_price.setText("￥"+itemProduct.getP_price());
        }else if(carType == 2){
            holder.item_single_price.setText("￥"+itemProduct.getP_price2());
        }
        if(checkState[position]){
            holder.item_selected_img.setVisibility(View.VISIBLE);
        }else {
            holder.item_selected_img.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        private TextView item_wax_tv;
        private TextView item_single_price;
        private ImageView item_selected_img;
    }
}
