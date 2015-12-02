package com.jph.xibaibai.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerCarAdapter;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.AllCar;
import com.jph.xibaibai.model.entity.Car;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.DividerItemDecoration;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by jph on 2015/8/27.
 */
public class CarsActivity extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseRecyclerAdapter.OnItemClickListener, RecyclerCarAdapter.CarOnClickListener {

    private int uuid;
    private IAPIRequests mApiRequests;
    private RecyclerCarAdapter carAdapter;
    private int deletePosition = -1;
    private int setDefaultPosition = -1;

    @ViewInject(R.id.cars_swiperefresh_cars)
    SwipeRefreshLayout swipeRefreshLayoutCar;
    @ViewInject(R.id.cars_recycler_cars)
    RecyclerView recyclerViewCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onRefresh();
    }

    @Override
    public void initData() {
        super.initData();
        uuid = SPUserInfo.getsInstance(CarsActivity.this).getSPInt(SPUserInfo.KEY_USERID);
        mApiRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("常用车辆");
        showTitleBtnRight("添加");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerViewCar.setLayoutManager(llm);
        recyclerViewCar.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, R.drawable.shape_divideline_car));
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeRefreshLayoutCar.setOnRefreshListener(this);
    }

    @Override
    protected void onClickTitleRight(View v) {
        super.onClickTitleRight(v);

        startActivity(AddCarActivity.class);
    }

    @Override
    public void onRefresh() {
        mApiRequests.getCar(uuid);
    }

    @Override
    public void onPrepare(int taskId) {
    }

    @Override
    public void onEnd(int taskId) {
        super.onEnd(taskId);
        swipeRefreshLayoutCar.setRefreshing(false);
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GETCAR:
                //得到车辆列表
                AllCar allCar = null;
                if (responseJson.getResult() == null) {
                    allCar = new AllCar();
                } else {
                    Log.i("Tag","Cars=>"+responseJson.getResult().toString());
                    allCar = JSON.parseObject(responseJson.getResult().toString(), AllCar.class);
                }
                carAdapter = new RecyclerCarAdapter(allCar);
                carAdapter.setOnItemClickListener(this);
                carAdapter.setCarOnClickListener(this);
                recyclerViewCar.setAdapter(carAdapter);
                break;
            case Tasks.DELETECAR:
                showToast("删除车辆成功");
                carAdapter.deleteItem(deletePosition);
                break;
            case Tasks.SETDEFAULTCAR:
                carAdapter.getAllCar().setDefaultId(carAdapter.getItem(setDefaultPosition).getId());
                carAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intentEdit = new Intent(this, AddCarActivity.class);
        intentEdit.putExtra(AddCarActivity.EXTRA_CAR, carAdapter.getItem(position));
        startActivity(intentEdit);
    }

    @Override
    public void onItemLongClick(View v, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除该车辆吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Car car = carAdapter.getItem(position);
                deletePosition = position;
//                carAdapter.deleteItem(position);
                mApiRequests.deleteCar(uuid,
                        car.getId(), car.getC_plate_num());
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public void onClickSetDefault(int position) {
        setDefaultPosition = position;
        mApiRequests.setDefaultCar(uuid, carAdapter.getItem(position).getId());
    }
}
