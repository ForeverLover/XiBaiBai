package com.jph.xibaibai.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerDIYProAdapter;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.DIYData;
import com.jph.xibaibai.model.entity.DIYProduct;
import com.jph.xibaibai.model.entity.Product;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部评价
 * Created by jph on 2015/9/13.
 */
public class DIYProActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener,View.OnClickListener {
    public static final String RESULT_PROIDS = "result_proids";
    public static final String RESULT_TOTAL_PRICE = "result_total_price";
    private IAPIRequests mAPIRequests;
    private RecyclerDIYProAdapter mDIYProAdapter;

    @ViewInject(R.id.diypro_recycler_diypro)
    RecyclerView mRecyclerViewComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diypro);
        findViewById(R.id.back_finish_img).setOnClickListener(this);
        mAPIRequests.getDIYProduct();
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        ((TextView)findViewById(R.id.title_text_tv)).setText("DIY服务");
    }


    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GET_DIYPRODUCT:
                Log.i("Tag","DIY服务=>"+responseJson.getResult().toString());
                //得到评论
                showViewContent(JSON.parseObject(responseJson.getResult().toString(), DIYData.class));
                break;
        }
    }

    private void showViewContent(DIYData diyData) {
        mDIYProAdapter = decomposeData(diyData);
        mDIYProAdapter.setOnItemClickListener(this);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0 || (position == mDIYProAdapter.getRealItemCount() - 1)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        mRecyclerViewComment.setLayoutManager(gridLayoutManager);
        mRecyclerViewComment.setAdapter(mDIYProAdapter);
    }

    private RecyclerDIYProAdapter decomposeData(DIYData diyData) {
        List<DIYProduct> listP = new ArrayList<DIYProduct>();//实际产品
        List<DIYProduct> listS = new ArrayList<DIYProduct>();//diy服务

        for (DIYProduct product : diyData.getList()) {
            if (product.getP_type_t() == 0) {
                listP.add(product);
            } else {
                listS.add(product);
            }
        }

        return new RecyclerDIYProAdapter(diyData.getGroup(), listP, listS);
    }

    @Override
    public void onItemClick(View v, int position) {
        mDIYProAdapter.changeCheckStatus(position);
    }

    @Override
    public void onItemLongClick(View v, int position) {
        Product product = mDIYProAdapter.getItem(position);
        WebActivity.startWebActivity(v.getContext(), product.getP_name(), Constants.URL_WEB_BASE_PRODUCT + product.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_finish_img:
                Intent intentResult = new Intent();
                intentResult.putExtra(DIYProActivity.RESULT_TOTAL_PRICE, 0.0);
                setResult(RESULT_OK, intentResult);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intentResult = new Intent();
            intentResult.putExtra(DIYProActivity.RESULT_TOTAL_PRICE, 0.0);
            setResult(RESULT_OK, intentResult);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
