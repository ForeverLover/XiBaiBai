package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerMessageAdapter;
import com.jph.xibaibai.model.entity.Message;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.DividerItemDecoration;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统消息
 * Created by jph on 2015/9/20.
 */
public class MessageActivity extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener {

    private IAPIRequests mAPIRequests;
    private RecyclerMessageAdapter mMessageAdapter;

    @ViewInject(R.id.message_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewInject(R.id.message_recycler_order)
    RecyclerView mRecyclerViewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        onRefresh();
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
        mMessageAdapter = new RecyclerMessageAdapter(new ArrayList<Message>());
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("系统消息");
        mSwipeRefreshLayout.setColorSchemeColors(Constants.REFRESH_COLORS);

        mRecyclerViewOrder.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerViewOrder.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL, R.drawable.shape_divideline_order));
        mRecyclerViewOrder.setAdapter(mMessageAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mAPIRequests.listMessage(SPUserInfo.getsInstance(this).getSPInt(
                SPUserInfo.KEY_USERID));
    }

    @Override
    public void onPrepare(int taskId) {
    }

    @Override
    public void onEnd(int taskId) {
        super.onEnd(taskId);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.LIST_MESSAGE:
                //得到订单
                List<Message> list = JSON.parseArray(responseJson.getResult().toString(),
                        Message.class);
                mMessageAdapter.setList(list);
                break;
        }
    }

}
