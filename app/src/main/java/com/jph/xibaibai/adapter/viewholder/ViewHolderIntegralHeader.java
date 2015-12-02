package com.jph.xibaibai.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by jph on 2015/8/31.
 */
public class ViewHolderIntegralHeader extends RecyclerView.ViewHolder {

    @ViewInject(R.id.integral_txt_count)
    TextView mTxtCount;

    public ViewHolderIntegralHeader(View itemView, int integralCount) {
        super(itemView);
        ViewUtils.inject(this, itemView);
        mTxtCount.setText(String.valueOf(integralCount));
    }
}
