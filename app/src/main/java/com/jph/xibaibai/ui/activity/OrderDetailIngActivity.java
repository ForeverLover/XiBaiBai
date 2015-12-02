package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.OrderDetail;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.MImageLoader;
import com.jph.xibaibai.utils.OrderUtil;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 进行中订单详情
 * Created by jph on 2015/8/30.
 */
public class OrderDetailIngActivity extends TitleActivity {

    public static final String EXTRA_ORDER = "extra_order";
    private Order order;
    private OrderDetail orderDetail;
    private IAPIRequests mAPIRequests;

    @ViewInject(R.id.orderdetailing_v_line1)
    View mVLine1;
    @ViewInject(R.id.orderdetailing_v_line2)
    View mVLine2;
    @ViewInject(R.id.orderdetailing_v_line3)
    View mVLine3;
    @ViewInject(R.id.orderdetailing_txt_title)
    TextView txtTitle;
    @ViewInject(R.id.orderdetailing_txt_price)
    TextView txtPrice;
    @ViewInject(R.id.orderdetailing_txt_time)
    TextView txtTime;
    @ViewInject(R.id.orderdetailing_txt_address)
    TextView txtAddress;
    @ViewInject(R.id.orderdetailing_txt_carinfo)
    TextView txtCar;
    @ViewInject(R.id.orderdetailing_txt_ordernum)
    TextView txtOrderNum;
    @ViewInject(R.id.orderdetailing_txt_orderstatus)
    TextView txtOrderStatus;
    @ViewInject(R.id.orderdetailing_img_employeehead)
    ImageView imgEmployeeHead;
    @ViewInject(R.id.orderdetailing_txt_employeename)
    TextView txtEmployeeName;
    @ViewInject(R.id.orderdetailing_txt_employeephone)
    TextView txtEmployeePhone;
    @ViewInject(R.id.orderdetailing_txt_employeestar)
    TextView txtEmployeeStar;
    @ViewInject(R.id.orderdetailing_ratingbar_employeestar)
    RatingBar ratingBarEmployeeStar;
    @ViewInject(R.id.orderdetailing_vg_employee)
    ViewGroup vgEmployee;
    @ViewInject(R.id.orderdetailing_txt_payprice)
    TextView txtPayPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetailing);
        Log.i("Tag","订单ID="+order.getId());
        mAPIRequests.getOrderDetail(order.getId());
    }

    @Override
    public void initData() {
        super.initData();
        order = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
        mAPIRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("订单详情");
        mVLine1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mVLine2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mVLine3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.ORDER_DETAIL:
                Log.i("Tag","DetailOeder=>"+responseJson.getResult().toString());
                //得到订单详情
                showViewContent(JSON.parseObject(responseJson.getResult().toString(), OrderDetail.class));
                break;
        }
    }

    private void showViewContent(OrderDetail order) {
        orderDetail = order;
        txtTitle.setText(order.getOrder_name());
        txtPrice.setText(String.format(getString(R.string.format_price), order.getTotal_price()));
        txtTime.setText(TimeUtil.getMFormatStringByMill(order.getP_order_time()));
        txtAddress.setText(order.getLocation());
        txtCar.setText(order.getC_plate_num() + " " + order.getC_brand() + " " + order.getC_color());
        txtOrderNum.setText(order.getOrder_num());
        txtOrderStatus.setText(OrderUtil.getStatusName(order.getOrder_state()));
        txtEmployeeName.setText(order.getEmp_name());
        txtEmployeePhone.setText(order.getEmp_iphone());
        txtEmployeeStar.setText(order.getStar() + "分");
        ratingBarEmployeeStar.setRating(order.getStar());
        txtPayPrice.setText(OrderUtil.getStatusName(order.getOrder_state()) +
                String.format(getString(R.string.format_price), order.getPay_num()));
        MImageLoader.getInstance(this).displayImageM(order.getEmp_img(), imgEmployeeHead);
        if (order.getOrder_state() < 2 || order.getOrder_state() > 6) {
            //隐藏员工信息
            vgEmployee.setVisibility(View.GONE);
            mVLine3.setVisibility(View.GONE);
        } else {
            vgEmployee.setVisibility(View.VISIBLE);
            mVLine3.setVisibility(View.GONE);
        }

        switch (order.getOrder_state()) {
            case 0:
                //未付款
                txtOrderStatus.setText("去支付");
                txtOrderStatus.setBackgroundColor(getResources().getColor(R.color.bg_orange));
                break;
        }
    }

    @OnClick(R.id.orderdetailing_txt_orderstatus)
    public void onClickOperation(View v) {
        switch (orderDetail.getOrder_state()) {
            case 0:
                //未付款
                Log.i("Tag", "去付款：" + order.getOrder_state());
                Intent intentPay = new Intent(this, PayOrderActivity.class);
                intentPay.putExtra(PayOrderActivity.EXTRA_ORDER, order);
                startActivity(intentPay);
                finish();
                break;
        }
    }
}
