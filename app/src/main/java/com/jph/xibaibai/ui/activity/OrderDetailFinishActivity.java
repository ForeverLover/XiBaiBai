package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.OrderDetail;
import com.jph.xibaibai.model.entity.OrderReminder;
import com.jph.xibaibai.model.entity.OrderSuggestion;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.MImageLoader;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

/**
 * 已完成订单详情
 * Created by jph on 2015/8/30.
 */
public class OrderDetailFinishActivity extends TitleActivity {

    public static final String EXTRA_ORDER = "extra_order";
    private Order order;
    private OrderDetail orderDetail;
    private IAPIRequests mAPIRequests;

    @ViewInject(R.id.orderdetailfinish_v_line1)
    View mVLine1;
    @ViewInject(R.id.orderdetailfinish_v_line2)
    View mVLine2;
    @ViewInject(R.id.orderdetailfinish_v_line3)
    View mVLine3;
    @ViewInject(R.id.orderdetailfinish_v_line4)
    View mVLine4;
    @ViewInject(R.id.orderdetailfinish_v_line5)
    View mVLine5;
    @ViewInject(R.id.orderdetailfinish_txt_title)
    TextView txtTitle;
    @ViewInject(R.id.orderdetailfinish_txt_price)
    TextView txtPrice;
    @ViewInject(R.id.orderdetailfinish_txt_time)
    TextView txtTime;
    @ViewInject(R.id.orderdetailfinish_txt_address)
    TextView txtAddress;
    @ViewInject(R.id.orderdetailfinish_txt_carinfo)
    TextView txtCar;
    @ViewInject(R.id.orderdetailfinish_txt_ordernum)
    TextView txtOrderNum;
    @ViewInject(R.id.orderdetailfinish_img_employeehead)
    ImageView imgEmployeeHead;
    @ViewInject(R.id.orderdetailfinish_txt_employeename)
    TextView txtEmployeeName;
    @ViewInject(R.id.orderdetailfinish_txt_employeephone)
    TextView txtEmployeePhone;
    @ViewInject(R.id.orderdetailfinish_txt_employeestar)
    TextView txtEmployeeStar;
    @ViewInject(R.id.orderdetailfinish_ratingbar_employeestar)
    RatingBar ratingBarEmployeeStar;
    @ViewInject(R.id.orderdetailfinish_img_payway)
    ImageView imgPayWay;
    @ViewInject(R.id.orderdetailfinish_txt_paytime)
    TextView txtPayTime;
    @ViewInject(R.id.orderdetailfinish_txt_payprice)
    TextView txtPayPrice;
    @ViewInject(R.id.order_detail_finish_layout_suggestion_tag)
    ViewGroup mLayoutSuggestionTag;
    @ViewInject(R.id.order_detail_finish_layout_suggestion)
    LinearLayout mLayoutSuggestion;
    ;
    @ViewInject(R.id.order_detail_finish_layout_reminder_tag)
    ViewGroup mLayoutReminderTag;
    @ViewInject(R.id.order_detail_finish_layout_reminder)
    LinearLayout mLayoutReminder;
    @ViewInject(R.id.order_detail_finish_img_before)
    ImageView mImgBefore;
    @ViewInject(R.id.order_detail_finish_img_after)
    ImageView mImgAfter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetailfinish);

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
        mVLine4.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mVLine5.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    /**
     * 点击评价
     *
     * @param v
     */
    @OnClick(R.id.order_detail_finish_btn_comment)
    public void onClickComment(View v) {
        if (order.getOrder_state() != 5) {
            return;
        }
        Intent intentComment = new Intent(this, AddCommentActivity.class);
        intentComment.putExtra(AddCommentActivity.EXTRA_ORDER, order);
        startActivity(intentComment);
    }

    /**
     * 点击投诉
     *
     * @param v
     */
    @OnClick(R.id.order_detail_finish_btn_complain)
    public void onClickComplain(View v) {
        if (order.getOrder_state() == 7) {
            return;
        }
        Intent intentComplain = new Intent(this, ComplainActivity.class);
        intentComplain.putExtra(ComplainActivity.EXTRA_ORDER, order);
        startActivity(intentComplain);
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.ORDER_DETAIL:
                //得到订单详情
                orderDetail = JSON.parseObject(responseJson.getResult().toString(), OrderDetail.class);
                showViewContent(orderDetail);
                break;
        }
    }

    private void showViewContent(OrderDetail orderDetail) {
        txtTitle.setText(orderDetail.getOrder_name());
        txtPrice.setText(String.format(getString(R.string.format_price), orderDetail.getTotal_price()));
        txtTime.setText(TimeUtil.getMFormatStringByMill(orderDetail.getP_order_time()));
        txtAddress.setText(orderDetail.getLocation());
        txtCar.setText(orderDetail.getC_plate_num() + " " + orderDetail.getC_brand() + " " + orderDetail.getC_color());
        txtOrderNum.setText(orderDetail.getOrder_num());
        txtEmployeeName.setText(orderDetail.getEmp_name());
        txtEmployeePhone.setText(orderDetail.getEmp_iphone());
        txtEmployeeStar.setText(orderDetail.getStar() + "分");
        ratingBarEmployeeStar.setRating(orderDetail.getStar());
        MImageLoader.getInstance(this).displayImageM(orderDetail.getEmp_img(), imgEmployeeHead);
        txtPayTime.setText(TimeUtil.getMFormatStringByMill(orderDetail.getPay_time()));
        txtPayPrice.setText(String.format(getString(R.string.format_price), orderDetail.getPay_num()));

        //前后对比
        showImgContrast(orderDetail.getCar_wash_before_img(), orderDetail.getCar_wash_end_img());

        //建议
        if (orderDetail.getList_ad() == null || orderDetail.getList_pr().isEmpty()) {
            //无建议
            mLayoutSuggestion.setVisibility(View.GONE);
            mLayoutSuggestionTag.setVisibility(View.GONE);
        } else {
            showSuggestionView(orderDetail.getList_ad());
        }
        //温馨提示
        if (orderDetail.getList_pr() == null || orderDetail.getList_pr().isEmpty()) {
            //无温馨提示
            mLayoutReminder.setVisibility(View.GONE);
            mLayoutReminderTag.setVisibility(View.GONE);
        } else {
            showReminderView(orderDetail.getList_pr());
        }
    }

    /**
     * 显示建议
     *
     * @param listSuggestion
     */
    private void showSuggestionView(List<OrderSuggestion> listSuggestion) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < listSuggestion.size(); i++) {
            OrderSuggestion orderSuggestion = listSuggestion.get(i);
            View vItem = layoutInflater.inflate(R.layout.item_suggestion, mLayoutSuggestion, false);
            ImageView imgShow = (ImageView) vItem.findViewById(R.id.item_suggestion_img_show);
            TextView txtTitle = (TextView) vItem.findViewById(R.id.item_suggestion_txt_title);
            TextView txtContent = (TextView) vItem.findViewById(R.id.item_suggestion_txt_content);
            MImageLoader.getInstance(this).displayImageM(orderSuggestion.getAd_img(), imgShow);
            txtTitle.setText(orderSuggestion.getAd_title());
            txtContent.setText(orderSuggestion.getAd_content());
            mLayoutSuggestion.addView(vItem);
        }
    }

    /**
     * 显示温馨提示
     *
     * @param listReminder
     */
    private void showReminderView(List<OrderReminder> listReminder) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < listReminder.size(); i++) {
            OrderReminder orderReminder = listReminder.get(i);
            View vItem = layoutInflater.inflate(R.layout.item_reminder, mLayoutReminder, false);
            ImageView imgShow = (ImageView) vItem.findViewById(R.id.item_reminder_img_show);
            TextView txtContent = (TextView) vItem.findViewById(R.id.item_reminder_txt_content);
            MImageLoader.getInstance(this).displayImageM(orderReminder.getPr_img(), imgShow);
            txtContent.setText(orderReminder.getPr_content());
            mLayoutReminder.addView(vItem);
        }
    }

    private void showImgContrast(String before, String after) {
        if(StringUtil.isNull(before)){
            return;
        }
        if(StringUtil.isNull(after)){
            return;
        }
        final String[] urlsBefore = before.split(",");
        final String[] urlsAfter = after.split(",");
        if (urlsBefore != null && urlsBefore.length > 0) {
            MImageLoader.getInstance(this).displayImageM(urlsBefore[0], mImgBefore);
            mImgBefore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowImgsActivity.start(OrderDetailFinishActivity.this, urlsBefore);
                }
            });
        }
        if (urlsAfter != null && urlsAfter.length > 0) {
            MImageLoader.getInstance(this).displayImageM(urlsAfter[0], mImgAfter);
            mImgAfter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowImgsActivity.start(OrderDetailFinishActivity.this, urlsAfter);
                }
            });
        }
    }
}
