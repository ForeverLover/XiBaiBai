package com.jph.xibaibai.mview.morerecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jph.xibaibai.model.utils.MLog;

/**
 * 上拉加载更多RecyclerView
 * Created by jph on 2015/9/6.
 */
public class MoreRecyclerView extends RecyclerView {

    public static final String TAG = "MoreRecyclerView";

    private boolean mLoadable = true;
    private boolean isLoading = false;
    private MoreRecyclerAdapter mMoreRecyclerAdapter;
    private OnLoadMoreListener mOnLoadMoreListener;
    private MScrollListener mScrollListener;

    public MoreRecyclerView(Context context) {
        super(context);
    }

    public MoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    @Override
    public final void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    public void setAdapter(MoreRecyclerAdapter moreRecyclerAdapter) {
        super.setAdapter(moreRecyclerAdapter);
        this.mMoreRecyclerAdapter = moreRecyclerAdapter;
        if (mScrollListener != null) {
            removeOnScrollListener(mScrollListener);
        }
        mScrollListener = new MScrollListener();
        addOnScrollListener(new MScrollListener());
    }

    private class MScrollListener extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!(getLayoutManager() instanceof LinearLayoutManager)) {
                return;
            }
            LinearLayoutManager ll = (LinearLayoutManager) getLayoutManager();
            if (ll.findLastVisibleItemPosition() == mMoreRecyclerAdapter.getRealItemCount() - 1) {
                MLog.i(TAG, "scrolled last item");
                startLoadMore();
            }
        }
    }

    public void startLoadMore() {
        if (!mLoadable || isLoading) {
            return;
        }
        isLoading = true;
        if (mMoreRecyclerAdapter != null) {
            mMoreRecyclerAdapter.showFooterView();
        }
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void stopLoadMore() {
        isLoading = false;
        if (mMoreRecyclerAdapter != null) {
            mMoreRecyclerAdapter.hideFooterView();
        }
    }

    public boolean isLoadable() {
        return mLoadable;
    }

    public void setLoadable(boolean mLoadable) {
        this.mLoadable = mLoadable;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    /**
     * 加载监听
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}