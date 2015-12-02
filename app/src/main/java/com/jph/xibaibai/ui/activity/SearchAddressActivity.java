package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerSearchAddressAdapter;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

/**
 * 搜索地址
 * Created by jph on 2015/9/15.
 */
public class SearchAddressActivity extends BaseActivity implements OnGetPoiSearchResultListener,
        BaseRecyclerAdapter.OnItemClickListener {
    public static final String RESULT_ADDRESS = "result_address";
    private PoiSearch mPoiSearch;
    private RecyclerSearchAddressAdapter mAddressAdapter;

    @ViewInject(R.id.searchaddress_recycler_address)
    RecyclerView mRecyclerViewAddress;
    @ViewInject(R.id.searchaddress_edt_key)
    EditText mEdtKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchaddress);
    }

    @Override
    public void initData() {
        super.initData();
        mPoiSearch = PoiSearch.newInstance();
        mAddressAdapter = new RecyclerSearchAddressAdapter(new ArrayList<PoiInfo>());
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerViewAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAddress.setAdapter(mAddressAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mAddressAdapter.setOnItemClickListener(this);
    }

    @OnClick(R.id.searchaddress_img_back)
    public void onClickBack(View v) {
        finish();
    }

    /**
     * 点击搜索
     *
     * @param v
     */
    @OnClick(R.id.searchaddress_img_search)
    public void onClickSearch(View v) {
        String key = mEdtKey.getText().toString();
        if (key.length() == 0) {
            return;
        }
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(SPUserInfo.getsInstance(this).getSP(SPUserInfo.KEY_CITY))
                .keyword(key)
                .pageNum(0));
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null
                || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            showToast("未找到结果");
            return;
        }
        mAddressAdapter.setList(poiResult.getAllPoi());
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
    }

    @Override
    public void onItemClick(View v, int position) {
        PoiInfo poiInfo = mAddressAdapter.getItem(position);
        Address address = new Address();
        address.setAddress(poiInfo.address);
        address.setAddress_lt(String.valueOf(poiInfo.location.latitude));
        address.setAddress_lg(String.valueOf(poiInfo.location.longitude));

        Intent intentResult = new Intent();
        intentResult.putExtra(RESULT_ADDRESS, address);
        setResult(RESULT_OK, intentResult);
        finish();
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }
}
