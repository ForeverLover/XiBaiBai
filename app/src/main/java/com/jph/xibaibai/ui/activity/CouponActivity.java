package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerCouponAdapter;
import com.jph.xibaibai.model.entity.Coupon;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.DividerItemDecoration;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.SystermUtils;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的优惠券
 * Created by jph on 2015/8/30.
 */
public class CouponActivity extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerCouponAdapter.OnItemClickListener {

    private IAPIRequests mAPIRequests;
    private RecyclerCouponAdapter mCouponAdapter;
    private List<Coupon> filterCouponList = null;

    @ViewInject(R.id.coupon_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewInject(R.id.coupon_recycler_coupon)
    RecyclerView mRecyclerViewCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        onRefresh();
        Log.i("Tag", "uid=>" + SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID));
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
        mCouponAdapter = new RecyclerCouponAdapter(new ArrayList<Coupon>());
        mCouponAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("我的优惠券");
        showTitleBtnRight("使用规则");
        mSwipeRefreshLayout.setColorSchemeColors(Constants.REFRESH_COLORS);

        mRecyclerViewCoupon.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        /*mRecyclerViewCoupon.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL, R.drawable.shape_divideline_order));*/
        mRecyclerViewCoupon.setAdapter(mCouponAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mAPIRequests.getCoupons(SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID));
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
            case Tasks.GETCOUPONS:
                //得到优惠券
                List<Coupon> couponList = JSON.parseArray(responseJson.getResult().toString(), Coupon.class);
                filterCouponList = new ArrayList<>();
                if (couponList != null) {
                    for (Coupon coupon : couponList) {
                        if (coupon.getState() == 0) {
                            filterCouponList.add(coupon);
                        }
                    }
                    if (filterCouponList != null) {
                        mCouponAdapter.setList(filterCouponList);
                    }
                }
                break;
        }
    }

    private void getCoupon(int position) {
        Intent intent = new Intent();
        intent.putExtra("choiceCoupon",mCouponAdapter.getItem(position));
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onItemClick(View v, int position) {
        // item的点击监听
        showToast("点击" + mCouponAdapter.getItem(position).getId());
        getCoupon(position);
    }

    @Override
    public void onItemLongClick(View v, int position) {
        showToast("点击" + mCouponAdapter.getItem(position).getCoupons_price());
    }
}
