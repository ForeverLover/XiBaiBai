package com.jph.xibaibai.mview.morerecyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 带footer的Recyclerviewadapter
 * Created by jph on 2015/9/7.
 */
public abstract class FooterRecyclerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public FooterRecyclerAdapter() {
    }

    @Override
    public final int getItemCount() {
        if (isFootable()) {
            return getRealItemCount() + 1;
        }
        return getRealItemCount();
    }

    @Deprecated
    @Override
    public final int getItemViewType(int position) {
        if (isFootable() && position == getItemCount() - 1) {
            return -1;
        }
        return getRealItemViewType(position);
    }

    @Deprecated
    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isFootable() && viewType == -1) {
            return new ViewHolderFooter(createFooterView(parent));
        }
        return onRealCreateViewHolder(parent, viewType);
    }

    @Deprecated
    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFootable() && position == getItemCount() - 1) {
            return;
        }
        onRealBindViewHolder((VH) holder, position);
    }

    public abstract View createFooterView(ViewGroup parent);

    public boolean isFootable() {
        return true;
    }

    public abstract int getRealItemCount();

    public int getRealItemViewType(int position) {
        return 0;
    }

    public abstract VH onRealCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onRealBindViewHolder(VH holder, int position);

    public static class ViewHolderFooter extends RecyclerView.ViewHolder {
        public ViewHolderFooter(View itemView) {
            super(itemView);
        }
    }
}
