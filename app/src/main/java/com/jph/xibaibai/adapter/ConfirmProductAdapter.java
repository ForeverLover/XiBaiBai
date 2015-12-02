package com.jph.xibaibai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.BeautyItemProduct;

import java.util.List;

/**
 * Created by Eric on 2015/11/12.
 */
public class ConfirmProductAdapter extends BaseAdapter {

    private List<BeautyItemProduct> productList = null;

    private int carType;

    public ConfirmProductAdapter(List<BeautyItemProduct> productList,int carType){
        this.productList = productList;
        this.carType = carType;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_product,null);
            holder.item_product_name = (TextView) convertView.findViewById(R.id.item_product_name);
            holder.item_product_price = (TextView) convertView.findViewById(R.id.item_product_price);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        BeautyItemProduct product = productList.get(position);
        holder.item_product_name.setText(product.getP_name());
        if(carType == 1){
            holder.item_product_price.setText("￥"+product.getP_price());
        }else if(carType == 2){
            holder.item_product_price.setText("￥"+product.getP_price2());
        }
        return convertView;
    }

    class ViewHolder{
        private TextView item_product_name;
        private TextView item_product_price;
    }
}
