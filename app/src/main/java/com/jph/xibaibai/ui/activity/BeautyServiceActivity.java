package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.BeautyDIYItemAdapter;
import com.jph.xibaibai.adapter.BeautyDIYMealsAdapter;
import com.jph.xibaibai.adapter.BeautyWaxAdapter;
import com.jph.xibaibai.adapter.NotwashAdapter;
import com.jph.xibaibai.model.entity.BeautyItemProduct;
import com.jph.xibaibai.model.entity.BeautyService;
import com.jph.xibaibai.model.entity.DIYMeals;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.SystermUtils;
import com.jph.xibaibai.utils.parsejson.BeautyServiceParse;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2015/11/8.
 * 美容服务选项
 */
public class BeautyServiceActivity extends TitleActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static String beautyTotalPrice = "beautyTotalPrice";

    private IAPIRequests mAPIRequests;

    private int carType = 0; // 车型的判别

    private BeautyService beautyService = null;
    /**
     * 打蜡的种类的封装
     */
    private List<BeautyItemProduct> waxList;
    /**
     * DIY套餐的封装
     */
    private List<DIYMeals> diyMealsList;
    /**
     * DIY子项服务的封装
     */
    private List<BeautyItemProduct> diyItemList;
    /**
     * 非必须洗车服务的封装
     */
    private List<BeautyItemProduct> notWashList;

    private BeautyWaxAdapter waxAdapter = null;

    private BeautyDIYMealsAdapter diyMealsAdapter = null;

    private BeautyDIYItemAdapter diyItemAdapter = null;

    private NotwashAdapter notwashAdapter = null;

    private boolean[] wCheckState = null;
    private boolean[] dCheckState = null;
    private boolean[] diyICheckState = null;
    private boolean[] nCheckState = null;

    private double totalPrice = 0.0;

    private String productId = "";

    private List<BeautyItemProduct> beautyProductList = null; // 缓存选择的美容服务

    @ViewInject(R.id.beauty_wax_lv)
    ListView beauty_wax_lv; // 打蜡套餐选择
    @ViewInject(R.id.beauty_diymeals_lv)
    ListView beauty_diymeals_lv; // DIY套餐选择
    @ViewInject(R.id.beauty_diyitem_gv)
    GridView beauty_diyitem_gv; // DIY子项目
    @ViewInject(R.id.beauty_notwash_lv)
    ListView beauty_notwash_lv; // 不需要清洗的服务
    @ViewInject(R.id.beauty_submit_btn)
    Button beauty_submit_btn; // 提交按钮
    @ViewInject(R.id.beauty_service_total_money)
    TextView beauty_service_total_money; // 总价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_service);
    }

    @Override
    public void initData() {
        mAPIRequests = new APIRequests(this);
        beautyProductList = new ArrayList<>();
        mAPIRequests.getBeautyService();
        carType = getIntent().getIntExtra("carType", 1);
        Log.i("Tag","Beauty carType=>"+carType);
        beauty_service_total_money.setText("￥" + getIntent().getDoubleExtra(beautyTotalPrice, 0.0));
        beauty_wax_lv.setFocusable(false);
        beauty_wax_lv.setOnItemClickListener(this);
        beauty_diymeals_lv.setFocusable(false);
        beauty_diymeals_lv.setOnItemClickListener(this);
        beauty_diyitem_gv.setFocusable(false);
        beauty_diyitem_gv.setOnItemClickListener(this);
        beauty_notwash_lv.setFocusable(false);
        beauty_notwash_lv.setOnItemClickListener(this);
        beauty_submit_btn.setOnClickListener(this);
    }

    @Override
    public void initView() {
        setTitle("美容服务");
    }

    /**
     * 再次进入此界面的初始化数据
     */
    private void initAginIntoData() {
        //打蜡
        waxList = beautyService.getWaxList();
        if (waxList != null) {
            waxAdapter = new BeautyWaxAdapter(waxList, carType);
            if (Constants.beautyCheckList.size() > 0) {
                wCheckState = Constants.beautyCheckList.get(0);
                waxAdapter.setCheckState(wCheckState);
            }
            beauty_wax_lv.setAdapter(waxAdapter);
            SystermUtils.setListViewHeight(beauty_wax_lv);
        }
        //DIY组合套餐
        diyMealsList = beautyService.getDiyMealsList();
        if (diyMealsList != null) {
            diyMealsAdapter = new BeautyDIYMealsAdapter(diyMealsList);
            if (Constants.beautyCheckList.size() > 1) {
                dCheckState = Constants.beautyCheckList.get(1);
                diyMealsAdapter.setCheckState(dCheckState);
            }
            beauty_diymeals_lv.setAdapter(diyMealsAdapter);
            SystermUtils.setListViewHeight(beauty_diymeals_lv);
        }
        //diy子项目服务
        diyItemList = beautyService.getDiyItemList();
        if (beautyService.getDiyItemList() != null) {
            diyItemAdapter = new BeautyDIYItemAdapter(diyItemList);
            if (Constants.beautyCheckList.size() > 2) {
                diyICheckState = Constants.beautyCheckList.get(2);
                diyItemAdapter.setCheckState(diyICheckState);
            }
            beauty_diyitem_gv.setAdapter(diyItemAdapter);
        }
        //非清洗服务
        notWashList = beautyService.getNotWashList();
        if (beautyService.getNotWashList() != null) {
            notwashAdapter = new NotwashAdapter(notWashList, carType);
            if (Constants.beautyCheckList.size() > 3) {
                nCheckState = Constants.beautyCheckList.get(3);
                notwashAdapter.setCheckState(nCheckState);
            }
            beauty_notwash_lv.setAdapter(notwashAdapter);
            SystermUtils.setListViewHeight(beauty_notwash_lv);
        }
    }


    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GETBEAUTY_SERVICE:
                if (!StringUtil.isNull(responseJson.getResult().toString())) {
                    beautyService = BeautyServiceParse.getResult(responseJson.getResult().toString());
                    if (beautyService == null) {
                        return;
                    }
                    initAginIntoData();
                }
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int listId = parent.getId();
        wCheckState = waxAdapter.getCheckState();  // 打蜡
        dCheckState = diyMealsAdapter.getCheckState(); // DIY套餐组合
        diyICheckState = diyItemAdapter.getCheckState(); // DIY子项目
        nCheckState = notwashAdapter.getCheckState(); // 非清洗的服务
        if(beautyProductList != null && beautyProductList.size() > 0){
            beautyProductList.removeAll(beautyProductList);
        }
        switch (listId) {
            case R.id.beauty_wax_lv:
                for (int i = 0; i < wCheckState.length; i++) {
                    if (i == position) {
                        if (wCheckState[i]) {
                            wCheckState[i] = false;
                        } else {
                            wCheckState[i] = true;
                        }
                    } else {
                        wCheckState[i] = false;
                    }
                }
                waxAdapter.setCheckState(wCheckState);
                waxAdapter.notifyDataSetChanged();
                break;
            case R.id.beauty_diymeals_lv:
                // DIY组合套餐
                if (dCheckState[position]) {
                    dCheckState[position] = false;
                } else {
                    dCheckState[position] = true;
                }
                diyMealsAdapter.setCheckState(dCheckState);
                diyMealsAdapter.notifyDataSetChanged();
                //更新DIY子项目
                if (diyItemList != null) {
                    List<String> diyMeals = diyMealsList.get(position).getDiyMealsId();
                    if (diyMeals != null) {
                        for (int k = 0; k < diyItemList.size(); k++) {
                            for (int j = 0; j < diyMeals.size(); j++) {
                                if ((diyItemList.get(k).getId()).equals(diyMeals.get(j))) {
                                    if (diyMealsAdapter.getCheckState()[position]) {
                                        diyICheckState[k] = true;
                                    } else {
                                        diyICheckState[k] = false;
                                    }
                                }
                            }
                        }
                        diyItemAdapter.setCheckState(diyICheckState);
                        diyItemAdapter.notifyDataSetChanged();
                    } else {
//                        Log.i("Tag", "diyMeals is null");
                    }
                }
                break;
            case R.id.beauty_notwash_lv:
                // 非清洗服务
                if (nCheckState[position]) {
                    nCheckState[position] = false;
                } else {
                    nCheckState[position] = true;
                }
                notwashAdapter.setCheckState(nCheckState);
                notwashAdapter.notifyDataSetChanged();
                break;
            case R.id.beauty_diyitem_gv:
                // DIY子项
                for (int i = 0; i < dCheckState.length; i++) {
                    dCheckState[i] = false;
                }
                diyMealsAdapter.setCheckState(dCheckState);
                diyMealsAdapter.notifyDataSetChanged();
                if (diyICheckState[position]) {
                    diyICheckState[position] = false;
                } else {
                    diyICheckState[position] = true;
                }
                diyItemAdapter.setCheckState(diyICheckState);
                diyItemAdapter.notifyDataSetChanged();
                break;
        }
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        productId = "";
        if (waxList == null) {
            return;
        }
        if (diyItemList == null) {
            return;
        }
        if (notWashList == null) {
            return;
        }
        /**计算打蜡的价格*/
        commonMethod(wCheckState, waxList);
        /**计算DIY子项目的价格*/
        commonMethod(diyICheckState, diyItemList);
        /**计算非必须洗项目的价格*/
        commonMethod(nCheckState, notWashList);
        beauty_service_total_money.setText("￥" + totalPrice);
    }

    private void commonMethod(boolean[] checkState, List<BeautyItemProduct> productList) {
        for (int i = 0; i < checkState.length; i++) {
            if (checkState[i]) {
                if (!StringUtil.isNull(productList.get(i).getP_price())) {
//                    Log.i("Tag","加入List中");
                    beautyProductList.add(productList.get(i));
                    productId += productList.get(i).getId() + ",";
                    if (carType == 1) {
                        totalPrice += Double.parseDouble(productList.get(i).getP_price());
                    } else if (carType == 2) {
                        totalPrice += Double.parseDouble(productList.get(i).getP_price2());
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beauty_submit_btn:
                if (totalPrice <= 0.0) {
                    showToast("请选择您的服务项目");
                    return;
                }
                pottingData();
                Intent intent = new Intent();
                intent.putExtra("beautyTotalPrice", totalPrice);
                intent.putExtra("beautyProductId", productId);
                intent.putExtra("beautyProductList", (Serializable) beautyProductList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void pottingData() {
        Constants.beautyCheckList.removeAll(Constants.beautyCheckList);
        if (wCheckState != null) {
            Constants.beautyCheckList.add(wCheckState);
        }
        if (dCheckState != null) {
            Constants.beautyCheckList.add(dCheckState);
        }
        if (diyICheckState != null) {
            Constants.beautyCheckList.add(diyICheckState);
        }
        if (nCheckState != null) {
            Constants.beautyCheckList.add(nCheckState);
        }
    }
}
