package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.CarBrand;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.picklist.PickListLayout;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 车辆品牌列表
 * Created by jph on 2015/9/15.
 */
public class CarBrandActivity extends TitleActivity implements AdapterView.OnItemClickListener {
    public static final String RESULT_BRAND = "result_brand";
    //    private RecyclerCarBrandAdapter mBrandAdapter;
    private IAPIRequests mAPIRequests;

//    @ViewInject(R.id.car_brand_recycler_brand)
//    RecyclerView mRecyclerViewBrand;
    @ViewInject(R.id.car_brand_picklayout_brand)
    PickListLayout pickListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand);
        mAPIRequests.getAllBrand();
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
//        mBrandAdapter = new RecyclerCarBrandAdapter(new ArrayList<CarBrand>());
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("选择品牌");
//        mRecyclerViewBrand.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mRecyclerViewBrand.setAdapter(mBrandAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        pickListLayout.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentResult = new Intent();
        intentResult.putExtra(RESULT_BRAND, pickListLayout.getItem(position));
        setResult(RESULT_OK, intentResult);
        finish();
    }

    //    @Override
//    public void onItemClick(View v, int position) {
//
////        Intent intentResult = new Intent();
////        intentResult.putExtra(RESULT_BRAND, mBrandAdapter.getItem(position));
////        setResult(RESULT_OK, intentResult);
////        finish();
//    }
//
//    @Override
//    public void onItemLongClick(View v, int position) {
//
//    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GET_ALL_BRAND:
                //得到所有汽车品牌
                List<CarBrand> listCarBrand = JSON.parseArray(responseJson.getResult().toString(), CarBrand.class);
//                mBrandAdapter.setList(listCarBrand);
                pickListLayout.setData(listCarBrand);
                break;
        }
    }

}
