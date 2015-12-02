package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerIntegralRecordsAdapter;
import com.jph.xibaibai.model.entity.Integral;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的积分
 * Created by jph on 2015/8/30.
 */
public class IntegralActivity extends TitleActivity {

    private Integral mIntegral;
    private IAPIRequests mAPIRequesets;
    private RecyclerIntegralRecordsAdapter integralRecordsAdapter;

    @ViewInject(R.id.integral_recycler_detailrecord)
    RecyclerView mRecyclerViewRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        Log.i("Tag","uid = "+SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID));
        mAPIRequesets.getMyIntegral(SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID));
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequesets = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("我的积分");
        mRecyclerViewRecords.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.INTEGRAL:
                //积分
                Log.i("Tag","积分=》"+responseJson.getResult().toString());
                if(!StringUtil.isNull(responseJson.getResult().toString())){
                    integralRecordsAdapter = new RecyclerIntegralRecordsAdapter(
                            JSON.parseObject(responseJson.getResult().toString(), Integral.class));
                    mRecyclerViewRecords.setAdapter(integralRecordsAdapter);
                }
                break;
        }
    }
}
