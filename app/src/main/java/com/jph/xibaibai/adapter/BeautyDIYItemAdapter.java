package com.jph.xibaibai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.BeautyItemProduct;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.MImageLoader;

import java.util.List;

/**
 * Created by Eric on 2015/11/9.
 * DIY子项目
 */
public class BeautyDIYItemAdapter extends BaseAdapter {

    private List<BeautyItemProduct> diyItemList;

    private boolean[] checkState = null;

    public BeautyDIYItemAdapter(List<BeautyItemProduct> diyItemList){
        this.diyItemList = diyItemList;
        checkState = new boolean[diyItemList.size()];
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
        return diyItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return diyItemList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diy_product,null);
            holder.item_selected_img = (ImageView) convertView.findViewById(R.id.item_selected_img);
            holder.item_unselected_img = (ImageView) convertView.findViewById(R.id.item_unselected_img);
            holder.item_price_tv = (TextView) convertView.findViewById(R.id.item_price_tv);
            holder.item_diyname_tv = (TextView) convertView.findViewById(R.id.item_diyname_tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        BeautyItemProduct itemProduct = diyItemList.get(position);
        holder.item_price_tv.setText(itemProduct.getP_price());
        holder.item_diyname_tv.setText(itemProduct.getP_name());
//        MImageLoader.getInstance(parent.getContext()).displayImageM("http://192.168.1.113/xbb/Uplode/xbb_w12.png", holder.item_unselected_img);
        MImageLoader.getInstance(parent.getContext()).displayImage(com.jph.xibaibai.model.utils.Constants.BASE_URL+itemProduct.getP_wimg(),
                holder.item_unselected_img);
        MImageLoader.getInstance(parent.getContext()).displayImage(com.jph.xibaibai.model.utils.Constants.BASE_URL+itemProduct.getP_ximg(),
                holder.item_selected_img);
        if(checkState[position]){
            holder.item_selected_img.setVisibility(View.VISIBLE);
            holder.item_unselected_img.setVisibility(View.GONE);
        }else {
            holder.item_selected_img.setVisibility(View.GONE);
            holder.item_unselected_img.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView item_selected_img;
        private ImageView item_unselected_img;
        private TextView item_price_tv;
        private TextView item_diyname_tv;
    }
}
