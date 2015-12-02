package com.jph.xibaibai.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerOrderIngAdapter;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.DividerItemDecoration;
import com.jph.xibaibai.mview.morerecyclerview.MoreRecyclerView;
import com.jph.xibaibai.ui.activity.OrderDetailIngActivity;
import com.jph.xibaibai.ui.activity.ShowLocationActivity;
import com.jph.xibaibai.ui.fragment.base.BaseFragment;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行中订单
 * Created by jph on 2015/8/28.
 */
public class IngOrderFragment extends BaseFragment implements
        BaseRecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
        RecyclerOrderIngAdapter.OrderIngOnClickListener, MoreRecyclerView.OnLoadMoreListener {
    private int page = 0;
    private IAPIRequests mAPIRequests;
    private RecyclerOrderIngAdapter mOrderIngAdapter;

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
        mOrderIngAdapter = new RecyclerOrderIngAdapter(new ArrayList<Order>());
    }

    @Override
    public void initView() {
        super.initView();
        mSwipeRefreshLayout.setColorSchemeColors(Constants.REFRESH_COLORS);

        mRecyclerViewOrder.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerViewOrder.addItemDecoration(new DividerItemDecoration(getActivity(),
                LinearLayoutManager.VERTICAL, R.drawable.shape_divideline_order));
        mRecyclerViewOrder.setAdapter(mOrderIngAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mOrderIngAdapter.setOnItemClickListener(this);
        mOrderIngAdapter.setOrderIngOnClickListener(this);
        mRecyclerViewOrder.setOnLoadMoreListener(this);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intentOrderDetail = new Intent(getActivity(), OrderDetailIngActivity.class);
        intentOrderDetail.putExtra(OrderDetailIngActivity.EXTRA_ORDER, mOrderIngAdapter.getItem(position));
        startActivity(intentOrderDetail);
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }


    @Override
    public void onRefresh() {
        page = 0;
        mAPIRequests.getOrders(SPUserInfo.getsInstance(getActivity()).getSPInt(
                SPUserInfo.KEY_USERID), "1", page);
    }

    @Override
    public void onLoadMore() {
        mAPIRequests.getOrders(SPUserInfo.getsInstance(getActivity()).getSPInt(
                SPUserInfo.KEY_USERID), "1", ++page);
    }

    @Override
    public void onPrepare(int taskId) {
        if (taskId == Tasks.GETORDERS) {
            return;
        }
        super.onPrepare(taskId);
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
                Log.i("Tag","-------");
                List<Order> listOrder = JSON.parseArray(responseJson.getResult().toString(),
                        Order.class);
                if (listOrder == null) {
                    listOrder = new ArrayList<Order>();
                }
                if (page == 0) {
                    mOrderIngAdapter.setList(listOrder);
                } else {
                    mOrderIngAdapter.addList(listOrder);
                }
                mRecyclerViewOrder.setLoadable(listOrder.size() == 20);
                break;
            case Tasks.CANCEL_ORDER:
                //取消订单成功
                showToast(responseJson.getMsg());
                onRefresh();
                break;
        }
    }

    @Override
    public void onClickEmploeeLocation(Order order) {
        Address address = new Address();
        address.setAddress(order.getCurrent_address());
        address.setAddress_lt(order.getCurrent_address_lt());
        address.setAddress_lg(order.getCurrent_address_lg());
        Intent intentLocation = new Intent(getActivity(), ShowLocationActivity.class);
        intentLocation.putExtra(ShowLocationActivity.EXTRA_ADDRESS, address);
        startActivity(intentLocation);
    }

    @Override
    public void onClickCancelOrder(int position, Order order) {
        mAPIRequests.cancelOrder(SPUserInfo.getsInstance(getActivity()).getSPInt(SPUserInfo.KEY_USERID),
                order.getId());
    }
}
