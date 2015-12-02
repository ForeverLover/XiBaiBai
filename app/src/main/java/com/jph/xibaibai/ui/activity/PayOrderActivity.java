package com.jph.xibaibai.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.alipay.Alipay;
import com.jph.xibaibai.alipay.Product;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.BaseAPIRequest;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 支付订单
 * Created by jph on 2015/9/18.
 */
public class PayOrderActivity extends TitleActivity {

    public static final String EXTRA_ORDER = "extra_order";

    private IAPIRequests mApiRequests;
    private Order order;
    private Order orderResult;//提交订单的结果

    @ViewInject(R.id.payorder_txt_ordername)
    TextView txtOrderName;
    @ViewInject(R.id.payorder_txt_totalprice)
    TextView txtTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payorder);
    }

    @Override
    public void initData() {
        super.initData();
        order = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
        mApiRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("订单支付");
        txtOrderName.setText(order.getOrder_name());
//        txtTotalPrice.setText(String.format(getString(R.string.format_price), order.getTotal_price()));
        txtTotalPrice.setText(String.format(getString(R.string.format_price), order.getTotal_price()));
    }

    @OnClick(R.id.payorder_btn_pay)
    public void onClickPay(View v) {

        if (orderResult != null) {
            //订单已经提交
            pay(orderResult);
        } else {
            showDialogConfirmOrder();
        }
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.NEWORDER:
                //下单成功
                showToast("订单提交成功");
                orderResult = JSON.parseObject(responseJson.getResult().toString(), Order.class);
                pay(orderResult);
                break;
        }
    }

    private void pay(final Order order) {
        /*Product product = new Product(order.getOrder_name(),
                order.getOrder_name(), order.getTotal_price(), order.getOrder_num());*/
        Product product = new Product(order.getOrder_name(),
                order.getOrder_name(), 0.01, order.getOrder_num());
        Alipay alipay = new Alipay(this, BaseAPIRequest.URL_API
                + "/alipay_return");
        alipay.setCallBack(new Alipay.CallBack() {
            @Override
            public void onSuccess() {
                Intent intentResult = new Intent(PayOrderActivity.this, OrderResultActivity.class);
                intentResult.putExtra(PayOrderActivity.EXTRA_ORDER, order);
                startActivity(intentResult);
                sendLocalBroadCast();
                finish();
            }

            @Override
            public void onFailed() {
                showToast("支付失败");
            }
        });
        alipay.pay(product);
    }

    /**
     * 预约成功后关闭预约界面
     */
    private void sendLocalBroadCast(){
        LocalBroadcastManager localBroadcastManager = null;
       //通过LocalBroadcastManager的getInstance()方法得到它的一个实例
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("com.xbb.broadcast.LOCAL_FINISH_SUBSCRIBE");
        localBroadcastManager.sendBroadcast(intent);//调用sendBroadcast()方法发送广播
    }

    /**
     * 弹窗确认订单
     */
    public void showDialogConfirmOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否确认提交该订单？");
        builder.setTitle("确认订单");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mApiRequests.newOrder(order);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
