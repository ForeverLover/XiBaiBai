package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.utils.MImageLoader;
import com.jph.xibaibai.utils.OrderUtil;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by jph on 2015/8/28.
 */
public class RecyclerOrderIngAdapter extends
        BaseRecyclerAdapter<RecyclerOrderIngAdapter.ViewHolder, Order> {

    private OrderIngOnClickListener mOrderIngOnClickListener;

    public RecyclerOrderIngAdapter(List<Order> listData) {
        super(listData);
    }

    public OrderIngOnClickListener getOrderIngOnClickListener() {
        return mOrderIngOnClickListener;
    }

    public void setOrderIngOnClickListener(OrderIngOnClickListener orderIngOnClickListener) {
        this.mOrderIngOnClickListener = orderIngOnClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.itemordering_v_line1)
        View vLine1;
        @ViewInject(R.id.itemordering_v_line2)
        View vLine2;
        @ViewInject(R.id.itemordering_txt_title)
        TextView txtTitle;
        @ViewInject(R.id.itemordering_txt_price)
        TextView txtPrice;
        @ViewInject(R.id.itemordering_txt_time)
        TextView txtTime;
        @ViewInject(R.id.itemordering_txt_address)
        TextView txtAddress;
        @ViewInject(R.id.itemordering_txt_carinfo)
        TextView txtCar;
        @ViewInject(R.id.itemordering_txt_ordernum)
        TextView txtOrderNum;
        @ViewInject(R.id.itemordering_txt_orderstatus)
        TextView txtOrderStatus;
        @ViewInject(R.id.itemordering_img_employeehead)
        ImageView imgEmployeeHead;
        @ViewInject(R.id.itemordering_txt_employeename)
        TextView txtEmployeeName;
        @ViewInject(R.id.itemordering_txt_employeephone)
        TextView txtEmployeePhone;
        @ViewInject(R.id.itemordering_txt_employeestar)
        TextView txtEmployeeStar;
        @ViewInject(R.id.itemordering_ratingbar_employeestar)
        RatingBar ratingBarEmployeeStar;
        @ViewInject(R.id.itemordering_vg_employeeaddress)
        ViewGroup vgEmployeeAddress;
        @ViewInject(R.id.itemordering_vg_cancel)
        ViewGroup vgCancel;
        @ViewInject(R.id.itemordering_vg_employee)
        ViewGroup vgEmployee;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
            vLine1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            vLine2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_ordering,
                viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, final int position) {
        super.onRealBindViewHolder(viewHolder, position);
        final Order order = getItem(position);
        viewHolder.txtTitle.setText(order.getOrder_name());
        viewHolder.txtPrice.setText(String.format(viewHolder.itemView.getResources().getString(R.string.format_price), order.getTotal_price()));
        viewHolder.txtTime.setText(TimeUtil.getMFormatStringByMill(order.getP_order_time()));
        viewHolder.txtAddress.setText(order.getLocation());
        viewHolder.txtCar.setText(order.getC_plate_num() + " " + order.getC_brand() + " " + order.getC_color());
        viewHolder.txtOrderNum.setText(order.getOrder_num());
        viewHolder.txtOrderStatus.setText(OrderUtil.getStatusName(order.getOrder_state()));
        viewHolder.txtEmployeeName.setText(order.getEmp_name());
        viewHolder.txtEmployeePhone.setText(order.getEmp_iphone());
        viewHolder.txtEmployeeStar.setText(order.getStar() + "分");
        viewHolder.ratingBarEmployeeStar.setRating(order.getStar());
        MImageLoader.getInstance(viewHolder.itemView.getContext()).displayImageM(order.getEmp_img(), viewHolder.imgEmployeeHead);
        viewHolder.vgEmployeeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "即将上线", Toast.LENGTH_SHORT).show();
//                if (mOrderIngOnClickListener != null && (order.getOrder_state() == 3 || order.getOrder_state() == 4)) {
//                    mOrderIngOnClickListener.onClickEmploeeLocation(order);
//                }
            }
        });
        viewHolder.vgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrderIngOnClickListener != null && order.getOrder_state() < 2) {
                    mOrderIngOnClickListener.onClickCancelOrder(position, order);
                }
            }
        });

        if (order.getOrder_state() < 2 || order.getOrder_state() > 6) {
            //隐藏员工信息
            viewHolder.vgEmployee.setVisibility(View.GONE);
            viewHolder.vLine2.setVisibility(View.GONE);
        } else {
            viewHolder.vgEmployee.setVisibility(View.VISIBLE);
            viewHolder.vLine2.setVisibility(View.GONE);
        }
    }

    public interface OrderIngOnClickListener {
        void onClickEmploeeLocation(Order order);

        void onClickCancelOrder(int position, Order order);
    }
}