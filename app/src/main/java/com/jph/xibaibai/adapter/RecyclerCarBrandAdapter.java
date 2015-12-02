package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.CarBrand;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 汽车品牌列表
 * Created by jph on 2015/9/13.
 */
public class RecyclerCarBrandAdapter extends BaseRecyclerAdapter<RecyclerCarBrandAdapter.ViewHolder, CarBrand> {


    public RecyclerCarBrandAdapter(List<CarBrand> listData) {
        super(listData);
    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_brand,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, int position) {
        super.onRealBindViewHolder(viewHolder, position);
        CarBrand carBrand = getItem(position);
        viewHolder.txtName.setText(carBrand.getMake_name());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.item_car_brand_txt_name)
        TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
