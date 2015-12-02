package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Coupon;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 优惠券列表适配器
 * Created by jph on 2015/9/29.
 */
public class RecyclerCouponAdapter extends BaseRecyclerAdapter<RecyclerCouponAdapter.ViewHolder, Coupon> {


    public RecyclerCouponAdapter(List<Coupon> listData) {
        super(listData);
    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_coupon,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, int position) {
        super.onRealBindViewHolder(viewHolder, position);
        Coupon coupon = getItem(position);
        viewHolder.txtPrice.setText(String.valueOf(coupon.getCoupons_price()));
        viewHolder.txtName.setText(coupon.getCoupons_name());
        viewHolder.txtDes.setText(coupon.getCoupons_remark());
        viewHolder.txtValidTime.setText("有效期至" + coupon.getExpired_time());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.item_coupon_txt_name)
        TextView txtName;
        @ViewInject(R.id.item_coupon_txt_validtime)
        TextView txtValidTime;
        @ViewInject(R.id.item_coupon_txt_des)
        TextView txtDes;
        @ViewInject(R.id.item_coupon_txt_price)
        TextView txtPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
