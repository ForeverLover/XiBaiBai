package com.jph.xibaibai.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by jph on 2015/9/17.
 */
public class ViewHolderDIYFooter extends RecyclerView.ViewHolder {

    @ViewInject(R.id.diypro_v_divider)
    public View vDivider;
    @ViewInject(R.id.diypro_btn_confirm)
    public Button btnConfirm;
    @ViewInject(R.id.diypro_txt_totalprice)
    public TextView txtTotalPrice;

    public ViewHolderDIYFooter(View itemView) {
        super(itemView);
        ViewUtils.inject(this, itemView);
        vDivider.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
