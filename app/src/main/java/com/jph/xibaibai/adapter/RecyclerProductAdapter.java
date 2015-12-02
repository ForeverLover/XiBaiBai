package com.jph.xibaibai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Product;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 产品列表适配器
 * Created by jph on 2015/8/21.
 */
public class RecyclerProductAdapter extends
        BaseRecyclerAdapter<RecyclerProductAdapter.ViewHolder, Product> {

    public RecyclerProductAdapter(List<Product> listData) {
        super(listData);
    }


    private int checkedPosition = -1;

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.itemproduct_vg_bg)
        ViewGroup vgBg;
        @ViewInject(R.id.itemproduct_txt_title)
        TextView txtTitle;
        @ViewInject(R.id.itemproduct_txt_price)
        TextView txtPrice;
        @ViewInject(R.id.itemproduct_txt_content)
        TextView txtContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }

    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = View.inflate(viewGroup.getContext(), R.layout.item_recycler_product,
                null);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, int position) {
        super.onRealBindViewHolder(viewHolder, position);

        Product product = getItem(position);
        Context context = viewHolder.itemView.getContext();

        if (position == checkedPosition) {
            viewHolder.vgBg.setBackgroundColor(context.getResources().getColor(R.color.selected));
            viewHolder.txtTitle.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.txtPrice.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.txtContent.setTextColor(context.getResources().getColor(R.color.white));
        } else {

            viewHolder.vgBg.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.txtTitle.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.txtPrice.setTextColor(context.getResources().getColor(R.color.txt_orange));
            viewHolder.txtContent.setTextColor(context.getResources().getColor(R.color.txt_darkgray));
        }

        viewHolder.txtTitle.setText(product.getP_name());
        viewHolder.txtContent.setText(product.getP_info());
        viewHolder.txtPrice.setText(String.format(context.
                getString(R.string.format_price), product.getP_price()));

    }

}

