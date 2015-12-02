package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.utils.OrderUtil;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by jph on 2015/8/28.
 */
public class RecyclerOrderFinishAdapter extends
        BaseRecyclerAdapter<RecyclerOrderFinishAdapter.ViewHolder, Order> {

    private FinishOrderOnItemClickListener mFinishOrderOnItemClickListener;

    public RecyclerOrderFinishAdapter(List<Order> listData) {
        super(listData);
    }

    public FinishOrderOnItemClickListener getFinishOrderOnItemClickListener() {
        return mFinishOrderOnItemClickListener;
    }

    public void setFinishOrderOnItemClickListener(FinishOrderOnItemClickListener finishOrderOnItemClickListener) {
        this.mFinishOrderOnItemClickListener = finishOrderOnItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.itemorderfinish_v_line1)
        View vLine1;
        @ViewInject(R.id.itemorderfinish_txt_title)
        TextView txtTitle;
        @ViewInject(R.id.itemorderfinish_txt_price)
        TextView txtPrice;
        @ViewInject(R.id.itemorderfinish_txt_time)
        TextView txtTime;
        @ViewInject(R.id.itemorderfinish_txt_address)
        TextView txtAddress;
        @ViewInject(R.id.itemorderfinish_txt_carinfo)
        TextView txtCar;
        @ViewInject(R.id.itemorderfinish_txt_ordernum)
        TextView txtOrderNum;
        @ViewInject(R.id.itemorderfinish_txt_orderstatus)
        TextView txtOrderStatus;
        @ViewInject(R.id.itemorderfinish_vg_comment)
        ViewGroup vgComment;
        @ViewInject(R.id.itemorderfinish_vg_complain)
        ViewGroup vgComplain;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
            vLine1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_orderfinish,
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

        viewHolder.vgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFinishOrderOnItemClickListener != null && order.getOrder_state() == 5) {
                    mFinishOrderOnItemClickListener.onItemClickComment(position, order);
                }
            }
        });

        viewHolder.vgComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFinishOrderOnItemClickListener != null && order.getOrder_state() != 7) {
                    mFinishOrderOnItemClickListener.onItemClickComplain(position, order);
                }
            }
        });
    }

    public interface FinishOrderOnItemClickListener {
        void onItemClickComment(int position, Order order);

        void onItemClickComplain(int position, Order order);
    }
}