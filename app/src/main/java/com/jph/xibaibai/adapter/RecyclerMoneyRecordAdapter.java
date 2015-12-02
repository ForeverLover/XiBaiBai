package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.adapter.viewholder.ViewHolderMoneyHeader;
import com.jph.xibaibai.model.entity.Balance;
import com.jph.xibaibai.model.entity.MoneyRecord;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 资金明细
 * Created by jph on 2015/8/31.
 */
public class RecyclerMoneyRecordAdapter extends
        BaseRecyclerAdapter<RecyclerView.ViewHolder, MoneyRecord> {

    private ViewHolderMoneyHeader.OnHeaderOperationListener mOnHeaderOperationListener;
    private Balance balance;
    private ViewHolderMoneyHeader viewHolderMoneyHeader;

    public RecyclerMoneyRecordAdapter(List<MoneyRecord> listData) {
        super(listData);
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
        viewHolderMoneyHeader.showViewContent(balance);
    }

    @Override
    public int getRealItemCount() {
        return super.getRealItemCount() + 1;
    }

    @Override
    public int getRealItemViewType(int position) {
        if (position == 0) {
            return R.layout.v_balance_header;
        }
        return R.layout.item_recycler_detailrecord;
    }

    @Override
    public RecyclerView.ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(viewType,
                viewGroup, false);
        if (viewType == R.layout.v_balance_header) {
            viewHolderMoneyHeader = new ViewHolderMoneyHeader(v);
            viewHolderMoneyHeader.setOnHeaderOperationListener(mOnHeaderOperationListener);
            return viewHolderMoneyHeader;
        }
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0) {
            return;
        }
        MoneyRecord moneyRecord = getItem(position - 1);

        super.onRealBindViewHolder(viewHolder, position - 1);
        ViewHolder viewHolderT = (ViewHolder) viewHolder;
        String type = null;
        switch (moneyRecord.getOperate_type()) {
            case 1:
                type = "收入";
                break;
            case 2:
                type = "支出";
                break;
            case 3:
                type = "提现";
                break;
        }
        viewHolderT.txtWay.setText(type);
        viewHolderT.txtChange.setText((moneyRecord.getOperate_type() == 1 ? "+" : "-") + moneyRecord.getOperate_money());
        viewHolderT.txtChange.setTextColor(viewHolder.itemView.getResources().getColor(
                moneyRecord.getOperate_type() == 1 ? R.color.txt_yellow : R.color.txt_darkgreen));
        viewHolderT.txtTime.setText(TimeUtil.getMFormatStringByMill(moneyRecord.getOperate_time()));

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

    public ViewHolderMoneyHeader.OnHeaderOperationListener getOnHeaderOperationListener() {
        return mOnHeaderOperationListener;
    }

    public void setOnHeaderOperationListener(ViewHolderMoneyHeader.OnHeaderOperationListener onHeaderOperationListener) {
        this.mOnHeaderOperationListener = onHeaderOperationListener;
    }

}
