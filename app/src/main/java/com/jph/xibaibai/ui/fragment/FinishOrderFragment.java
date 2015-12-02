package com.jph.xibaibai.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerOrderFinishAdapter;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.DividerItemDecoration;
import com.jph.xibaibai.mview.morerecyclerview.MoreRecyclerView;
import com.jph.xibaibai.ui.activity.AddCommentActivity;
import com.jph.xibaibai.ui.activity.ComplainActivity;
import com.jph.xibaibai.ui.activity.OrderDetailFinishActivity;
import com.jph.xibaibai.ui.fragment.base.BaseFragment;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 已完成订单
 * Created by jph on 2015/8/28.
 */
public class FinishOrderFragment extends BaseFragment implements
        BaseRecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
        RecyclerOrderFinishAdapter.FinishOrderOnItemClickListener, MoreRecyclerView.OnLoadMoreListener {
    private int page = 0;
    private IAPIRequests mAPIRequests;
    private RecyclerOrderFinishAdapter mOrderAdapter;

    @ViewInject(R.id.order_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewInject(R.id.order_recycler_order)
    MoreRecyclerView mRecyclerViewOrder;

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onCreateView(View contentView) {
        onRefresh();
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
        mOrderAdapter = new RecyclerOrderFinishAdapter(new ArrayList<Order>());
    }

    @Override
    public void initView() {
        super.initView();
        mSwipeRefreshLayout.setColorSchemeColors(Constants.REFRESH_COLORS);

        mRecyclerViewOrder.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerViewOrder.addItemDecoration(new DividerItemDecoration(getActivity(),
                LinearLayoutManager.VERTICAL, R.drawable.shape_divideline_order));
        mRecyclerViewOrder.setAdapter(mOrderAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mOrderAdapter.setOnItemClickListener(this);
        mOrderAdapter.setFinishOrderOnItemClickListener(this);
        mRecyclerViewOrder.setOnLoadMoreListener(this);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intentOrderDetail = new Intent(getActivity(), OrderDetailFinishActivity.class);
        intentOrderDetail.putExtra(OrderDetailFinishActivity.EXTRA_ORDER, mOrderAdapter.getItem(position));
        startActivity(intentOrderDetail);
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }


    @Override
    public void onRefresh() {
        page = 0;
        mAPIRequests.getOrders(SPUserInfo.getsInstance(getActivity()).getSPInt(
                SPUserInfo.KEY_USERID), "2", page);
    }

    @Override
    public void onLoadMore() {
        mAPIRequests.getOrders(SPUserInfo.getsInstance(getActivity()).getSPInt(
                SPUserInfo.KEY_USERID), "2", ++page);
    }

    @Override
    public void onPrepare(int taskId) {
    }

    @Override
    public void onEnd(int taskId) {
        super.onEnd(taskId);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerViewOrder.stopLoadMore();
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GETORDERS:
                //得到订单
                List<Order> listOrder = JSON.parseArray(responseJson.getResult().toString(),
                        Order.class);
                if (listOrder == null) {
                    listOrder = new ArrayList<Order>();
                }
                if (page == 0) {
                    mOrderAdapter.setList(listOrder);
                } else {
                    mOrderAdapter.addList(listOrder);
                }
                mRecyclerViewOrder.setLoadable(listOrder.size() ==20);
                break;
        }
    }

    @Override
    public void onItemClickComment(int position, Order order) {
        Intent intentComment = new Intent(getActivity(), AddCommentActivity.class);
        intentComment.putExtra(AddCommentActivity.EXTRA_ORDER, order);
        startActivity(intentComment);
    }

    @Override
    public void onItemClickComplain(int position, Order order) {
        Intent intentComplain = new Intent(getActivity(), ComplainActivity.class);
        intentComplain.putExtra(ComplainActivity.EXTRA_ORDER, order);
        startActivity(intentComplain);
    }
}
