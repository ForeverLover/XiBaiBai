package com.jph.xibaibai.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Balance;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by jph on 2015/9/4.
 */
public class ViewHolderMoneyHeader extends RecyclerView.ViewHolder {
    private OnHeaderOperationListener mOnHeaderOperationListener;

    @ViewInject(R.id.balance_txt_balance)
    TextView txtBalance;

    public ViewHolderMoneyHeader(View itemView) {
        super(itemView);
        ViewUtils.inject(this, itemView);
    }

    public void showViewContent(Balance balance) {
        txtBalance.setText(String.valueOf(balance.getMoney()));
    }

    /**
     * 点击充值
     *
     * @param v
     */
    @OnClick(R.id.balance_bg_topup)
    public void onClickUpTop(View v) {
        if (mOnHeaderOperationListener != null) {
            mOnHeaderOperationListener.onClickUpTop();
        }
    }

    public OnHeaderOperationListener getOnHeaderOperationListener() {
        return mOnHeaderOperationListener;
    }

    public void setOnHeaderOperationListener(OnHeaderOperationListener onHeaderOperationListener) {
        this.mOnHeaderOperationListener = onHeaderOperationListener;
    }

    public interface OnHeaderOperationListener {
        void onClickUpTop();
    }
}
