package com.jph.xibaibai.mview.morerecyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jph.xibaibai.R;

/**
 * 带加载更多的adapter
 * Created by jph on 2015/9/7.
 */
public abstract class MoreRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends FooterRecyclerAdapter<VH> {

    private View vFooter;

    @Override
    public View createFooterView(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        vFooter = layoutInflater.inflate(R.layout.v_footer_more, parent, false);
        vFooter.setPadding(0, vFooter.getPaddingBottom(), 0, vFooter.getPaddingBottom());
        hideFooterView();
        return vFooter;
    }

    public void showFooterView() {
        if (vFooter == null) {
            return;
        }
        ViewGroup.LayoutParams lp = vFooter.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        vFooter.setLayoutParams(lp);
    }

    public void hideFooterView() {
        if (vFooter == null) {
            return;
        }
        ViewGroup.LayoutParams lp = vFooter.getLayoutParams();
        lp.height = 1;
        vFooter.setLayoutParams(lp);
    }
}