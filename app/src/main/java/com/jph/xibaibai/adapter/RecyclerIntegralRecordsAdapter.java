package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.adapter.viewholder.ViewHolderIntegralHeader;
import com.jph.xibaibai.model.entity.Integral;
import com.jph.xibaibai.model.entity.IntegralRecord;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 积分明细
 * Created by jph on 2015/8/31.
 */
public class RecyclerIntegralRecordsAdapter extends
        BaseRecyclerAdapter<RecyclerView.ViewHolder, IntegralRecord> {
    private Integral mIntegral;

    public RecyclerIntegralRecordsAdapter(Integral integral) {
        super(integral.getList());
        this.mIntegral = integral;
    }

    @Override
    public int getRealItemCount() {
        return super.getRealItemCount() + 1;
    }

    @Override
    public int getRealItemViewType(int position) {
        if (position == 0) {
            return R.layout.v_integral_header;
        }
        return R.layout.item_recycler_detailrecord;
    }

    @Override
    public RecyclerView.ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(viewType,
                viewGroup, false);
        if (viewType == R.layout.v_integral_header) {
            return new ViewHolderIntegralHeader(v, mIntegral.getTotal_points());
        }
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0) {
            return;
        } else {
            position--;
        }
        super.onRealBindViewHolder(viewHolder, position);
        IntegralRecord integralRecord = getItem(position);
        ViewHolder viewHolderRecord = (ViewHolder) viewHolder;
        viewHolderRecord.txtWay.setText(integralRecord.getPoint_name());
        viewHolderRecord.txtChange.setText((integralRecord.getType() == 1 ? "+" : "-") + integralRecord.getPoints());
        viewHolderRecord.txtChange.setTextColor(viewHolder.itemView.getResources().getColor(
                integralRecord.getType() == 1 ? R.color.txt_yellow : R.color.txt_darkgreen));
        viewHolderRecord.txtTime.setText(TimeUtil.getMFormatStringByMill(integralRecord.getTime()));

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.itemrecord_txt_way)
        TextView txtWay;//方式
        @ViewInject(R.id.itemrecord_txt_time)
        TextView txtTime;
        @ViewInject(R.id.itemrecord_txt_change)
        TextView txtChange;
        @ViewInject(R.id.itemdetailrecord_v_divider)
        View vDivider;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
            vDivider.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }
}
