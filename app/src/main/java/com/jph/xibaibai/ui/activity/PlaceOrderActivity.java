package com.jph.xibaibai.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.model.entity.AllAddress;
import com.jph.xibaibai.model.entity.BeautyItemProduct;
import com.jph.xibaibai.model.entity.ConfirmOrder;
import com.jph.xibaibai.model.entity.Coupon;
import com.jph.xibaibai.model.entity.MyCoupons;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.scaleview.ScaleImageView;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.FileUtil;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.SystermUtils;
import com.jph.xibaibai.utils.VoicePlayer;
import com.jph.xibaibai.utils.VoiceRecorder;
import com.jph.xibaibai.utils.parsejson.BeautyServiceParse;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2015/11/8.
 * <p>
 * 下单界面
 */
public class PlaceOrderActivity extends TitleActivity implements View.OnClickListener {

    private IAPIRequests mAPIRequests = null;

    public static final String TRANSKEY_ORDER = "transkey_order";

    public final int TIMESCOPECODE = 1010;

    public final int BEAUTYREQUESTCODE = 1012;

    public final int CHOICECOUPONS = 1020;

    private String originalAddress = ""; // 上个界面的地址信息

    private String originalLongitude = ""; // 上个界面的经度

    private String originalLatitude = ""; // 上个界面的纬度

    private Order order = null;

    private double beautyTotalPrice = 0.0;  // 美容服务的总价格

    private String beautyProductId = ""; // 美容服务的产品id

    private long appointDay = 0; // 预约的日期

    private int appointTimeId = 0; // 预约的时间段

    private List<BeautyItemProduct> washCarList = null; // 洗车方式的数据结果

    private List<MyCoupons> couponsList = null; // 优惠券

    private String washCarId = ""; // 洗车方式的id

    private ConfirmOrder confirmOrder = null; // 确认订单数据的封装

    private SPUserInfo spUserInfo;

    private int uid; //用户的id

    private double washCarPrice = 0.0; // 外部清洗的价格

    private double washCarPrice2 = 0.0; // 整车清洗的价格

    private int carType = 1; // 车的类型

    private boolean[] checkStates = new boolean[4];

    private double allTotalPrice = 0.0;

    private double couponsPrice = 0.0;

    private AllAddress mAllAddress;

    private VoicePlayer voicePlayer;

    private List<BeautyItemProduct> cachProductList = null; // 缓存选择洗车服务

    private LocalReceiver localReceiver = null;

    private LocalBroadcastManager localBroadcastManager = null;

    private List<Coupon> couponList;

    private int couponsId = -1;

    private int position = -1; // 优惠券的位置

    private Coupon choiceCoupon = null; // 从优惠券界面选中的优惠券

    @ViewInject(R.id.car_plate_num)
    TextView car_plate_num;//车牌号
    @ViewInject(R.id.car_plate_name)
    TextView car_plate_name;//车名字
    @ViewInject(R.id.car_plate_type)
    TextView car_plate_type;//车类型
    @ViewInject(R.id.car_plate_address)
    TextView car_plate_address;//车地址
    @ViewInject(R.id.place_intoshop_img)
    ImageView place_intoshop_img;  // 到店洗车选中按钮
    @ViewInject(R.id.place_reservertech_img)
    ImageView place_reservertech_img; // 预约技师
    @ViewInject(R.id.place_extral_img)
    ImageView place_extral_img; // 外部清洗选中图标
    @ViewInject(R.id.place_allcar_img)
    ImageView place_allcar_img; //整车清洗选中图标
    @ViewInject(R.id.place_reservertech_tv)
    TextView place_reservertech_tv;  //预约技师时间显示
    @ViewInject(R.id.place_wash_price)
    TextView place_wash_price; //洗车价格的显示
    @ViewInject(R.id.car_plate_beauty)
    TextView car_plate_beauty; // 美容广告的显示
    @ViewInject(R.id.beauty_total_money)
    TextView beauty_total_money; // 美容总价格显示
    @ViewInject(R.id.place_coupons_layout)
    LinearLayout place_coupons_layout; // 优惠券布局
    @ViewInject(R.id.car_coupons_tv)
    TextView car_coupons_tv; // 优惠券名字显示
    @ViewInject(R.id.car_coupons_money)
    TextView car_coupons_money;// 优惠券价格显示
    @ViewInject(R.id.pay_total_money)
    TextView pay_total_money; // 总价格的显示
    @ViewInject(R.id.parkinfo_img_homep)
    ImageView parkinfo_img_homep; // 家庭地址没有选中图
    @ViewInject(R.id.parkinfo_homepchecked)
    ImageView parkinfo_homepchecked;// 家庭地址选中图
    @ViewInject(R.id.place_img_companyp)
    ImageView place_img_companyp;// 公司地址选中图
    @ViewInject(R.id.place_companypchecked)
    ImageView place_companypchecked; // 公司地址没有选中图
    @ViewInject(R.id.place_vg_contenttxt)
    ViewGroup vgTxtDesc; // 文字描述框
    @ViewInject(R.id.place_edt_txtdesc)
    EditText place_edt_txtdesc; // 文字编辑框
    @ViewInject(R.id.place_vg_contentvoice)
    ViewGroup vgVoiceDesc; // 录音描述框
    @ViewInject(R.id.place_img_pressvoice)
    ScaleImageView place_img_pressvoice; // 录音显示框
    @ViewInject(R.id.place_txt_recordtime)
    TextView txtRecordTime; // 录音时间显示
    @ViewInject(R.id.place_txt_voidtag)
    TextView place_txt_voidtag; // 按住添加录音提示
    @ViewInject(R.id.parkinfo_img_change)
    ImageView parkinfo_img_change;// 录音描述按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);
    }

    @Override
    public void initData() {
        super.initData();
        cachProductList = new ArrayList<>();
        for (int i = 0; i < checkStates.length; i++) {
            checkStates[i] = false;
        }
        checkStates[0] = true;
        if (Constants.beautyCheckList.size() > 0) {
            Constants.beautyCheckList.removeAll(Constants.beautyCheckList);
        }
        place_intoshop_img.setImageResource(R.drawable.checked_pic);
        spUserInfo = SPUserInfo.getsInstance(this);
        if (!StringUtil.isNull(spUserInfo.getSP(SPUserInfo.KEY_ALL_ADDRESS))) {
            Log.i("Tag", "All Adress=>" + spUserInfo.getSP(SPUserInfo.KEY_ALL_ADDRESS));
            mAllAddress = JSON.parseObject(spUserInfo.getSP(SPUserInfo.KEY_ALL_ADDRESS), AllAddress.class);
        }
        uid = spUserInfo.getSPInt(SPUserInfo.KEY_USERID);
        mAPIRequests = new APIRequests(this);
        confirmOrder = new ConfirmOrder();
        mAPIRequests.getWashInfo(uid);
        mAPIRequests.getCoupons(uid);
        order = (Order) getIntent().getSerializableExtra(TRANSKEY_ORDER);
        initViewInfo();
        getBroadCast();
    }

    private void getBroadCast() {
        localReceiver = new LocalReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xbb.broadcast.LOCAL_FINISH_SUBSCRIBE");
        //通过LocalBroadcastManager的getInstance()方法得到它的一个实例
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null && localReceiver != null) {
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("下单");
    }

    @Override
    public void initListener() {
        super.initListener();
        place_img_pressvoice.setOnTouchListener(new RecordOnTouchListener());
    }

    private void initViewInfo() {
        if (order != null) {
            originalAddress = order.getLocation();
//            Log.i("Tag", "C_ids=>" + order.getC_ids() + "/C_plate=" + order.getC_plate_num() + "brand=>" + order.getC_brand());
            car_plate_num.setText(order.getC_plate_num());
            car_plate_name.setText(order.getC_brand());
            car_plate_type.setText(SystermUtils.getCarTypes(order.getC_Type()));
            car_plate_address.setText(order.getLocation());
            originalLongitude = order.getLocation_lg();
            originalLatitude = order.getLocation_lt();
            if (order.getC_Type() == 1) {
                carType = 1;
            } else {
                carType = 2;
            }
            Log.i("Tag", "originalLongitude=" + originalLongitude + "/originalLatitude=" + originalLatitude);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case TIMESCOPECODE:
                // 时间段返回数据
                appointDay = data.getLongExtra("selectedDay", 0);
                appointTimeId = data.getIntExtra("selectedTimeScopeId", 0);
                place_reservertech_tv.setText(data.getStringExtra("selectedDate") + data.getStringExtra("selectedTimeScope"));
                break;
            case BEAUTYREQUESTCODE:
                // 选择美容项目返回的数据
                beautyTotalPrice = data.getDoubleExtra("beautyTotalPrice", 0.0);
                beautyProductId = data.getStringExtra("beautyProductId");
                cachProductList = (List<BeautyItemProduct>) data.getSerializableExtra("beautyProductList");
                if (!StringUtil.isNull(beautyProductId)) {
                    beautyProductId = beautyProductId.substring(0, beautyProductId.length() - 1);
                }
                car_plate_beauty.setText("美容项目");
                beauty_total_money.setText("￥" + beautyTotalPrice);
                Log.i("Tag", "position=>" + position);
                if(choiceCoupon != null && !StringUtil.isNull(choiceCoupon.getCoupons_price())){
                    clChoiceCouponPrice();
                }else {
                    if (!checkStates[2] && !checkStates[3]) {//未选中洗车服务
                        noWashTotalPrice();
                    } else { // 外部清洗或者整车清洗
                        caculateTotalPrice();
                    }
                }
                pay_total_money.setText("￥" + allTotalPrice);
                break;
            case CHOICECOUPONS:
                choiceCoupon = (Coupon) data.getSerializableExtra("choiceCoupon");
                if(choiceCoupon != null && !StringUtil.isNull(choiceCoupon.getCoupons_price())){
                    clChoiceCouponPrice();
                    Log.i("Tag","choiceCoupon=>" + choiceCoupon.getId());
                }
                pay_total_money.setText("￥" + allTotalPrice);
                break;
        }
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GEWASHCAR_DATA:
                // 清洗的数据
                if (!StringUtil.isNull(responseJson.getResult().toString())) {
                    washCarList = BeautyServiceParse.getWashInfo(responseJson.getResult().toString());
                    couponsList = BeautyServiceParse.getCouponsData(responseJson.getResult().toString());
                    getWashInfo();
                }
                break;
            case Tasks.GETCOUPONS:
                //得到优惠券
                couponList = JSON.parseArray(responseJson.getResult().toString(), Coupon.class);
                break;
        }
    }

    /**
     * 得到洗车的价格
     */
    private void getWashInfo() {
        if (washCarList != null && washCarList.get(0) != null) {
            if (!StringUtil.isNull(washCarList.get(1).getId())) {
                washCarId = washCarList.get(0).getId();
            }
            if (carType == 1) {
                if (!StringUtil.isNull(washCarList.get(0).getP_price())) {
                    washCarPrice = Double.parseDouble(washCarList.get(0).getP_price());
                }
            } else if (carType == 2) {
                if (!StringUtil.isNull(washCarList.get(0).getP_price2())) {
                    washCarPrice = Double.parseDouble(washCarList.get(0).getP_price2());
                }
            }
        } else {
            showToast("数据请求异常");
            return;
        }
        if (washCarList != null && washCarList.get(1) != null) {
            if (!StringUtil.isNull(washCarList.get(1).getId())) {
                washCarId = washCarList.get(1).getId();
            }
            if (carType == 1) {
                if (!StringUtil.isNull(washCarList.get(1).getP_price())) {
                    washCarPrice2 = Double.parseDouble(washCarList.get(1).getP_price());
                }
            } else if (carType == 2) {
                if (!StringUtil.isNull(washCarList.get(1).getP_price2())) {
                    washCarPrice2 = Double.parseDouble(washCarList.get(1).getP_price2());
                }
            }
        } else {
            showToast("数据请求异常");
        }
    }

    @OnClick({R.id.place_img_change, R.id.car_beauty_layout, R.id.place_submit_btn, R.id.parkinfo_img_clear,
            R.id.place_extral_layout, R.id.place_allcar_layout, R.id.place_intoshop_layout, R.id.place_reservertech_layout,
            R.id.place_vg_homepark, R.id.place_vg_companypark, R.id.place_txt_recordtime, R.id.place_coupons_layout})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.place_intoshop_layout:
                // 到店洗车
                if (!checkStates[0]) {
                    checkStates[0] = true;
                    place_intoshop_img.setImageResource(R.drawable.checked_pic);
                }
                if (checkStates[1]) {
                    checkStates[1] = false;
                    place_reservertech_img.setImageResource(R.drawable.unchecked_pic);
                }
                place_reservertech_tv.setText("预约上门");
                break;
            case R.id.place_reservertech_layout:
                // 预约技师
                if (!checkStates[1]) {
                    checkStates[1] = true;
                    place_reservertech_img.setImageResource(R.drawable.checked_pic);
                }
                if (checkStates[0]) {
                    checkStates[0] = false;
                    place_intoshop_img.setImageResource(R.drawable.unchecked_pic);
                }
                Intent intent = new Intent(PlaceOrderActivity.this, ApointmentTimeActivity.class);
                startActivityForResult(intent, TIMESCOPECODE);
                break;
            case R.id.place_extral_layout:
                // 外部清洗
                if (washCarList != null && washCarList.size() > 0) {
                    Log.i("Tag", "couponsId=" + couponsId);
                    if (checkStates[2]) {
                        checkStates[2] = false;
                        place_extral_img.setImageResource(R.drawable.unchecked_pic);
                        allTotalPrice = beautyTotalPrice;
                        if(choiceCoupon != null && !StringUtil.isNull(choiceCoupon.getCoupons_price())){
                            clChoiceCouponPrice();
                        }else {
                            noWashTotalPrice();
                        }
                        place_wash_price.setText("￥0.0");
                    } else {
                        checkStates[2] = true;
                        place_extral_img.setImageResource(R.drawable.checked_pic);
                        place_wash_price.setText("￥" + washCarPrice);
                        if (checkStates[3]) {
                            checkStates[3] = false;
                            place_allcar_img.setImageResource(R.drawable.unchecked_pic);
                        }
                        if(choiceCoupon != null && !StringUtil.isNull(choiceCoupon.getCoupons_price())){
                            clChoiceCouponPrice();
                        }else {
                            caculateTotalPrice();
                        }
                    }
                    pay_total_money.setText("￥" + allTotalPrice);
                } else {
                    showToast("数据请求异常");
                }
                break;
            case R.id.place_allcar_layout:
                // 整车清洗
                if (washCarList != null && washCarList.size() > 0) {
                    if (checkStates[3]) {
                        checkStates[3] = false;
                        place_allcar_img.setImageResource(R.drawable.unchecked_pic);
                        place_wash_price.setText("￥0.0");
                        allTotalPrice = beautyTotalPrice;
                        if(choiceCoupon != null && !StringUtil.isNull(choiceCoupon.getCoupons_price())){
                            clChoiceCouponPrice();
                        }else {
                            noWashTotalPrice();
                        }
                    } else {
                        checkStates[3] = true;
                        place_allcar_img.setImageResource(R.drawable.checked_pic);
                        place_wash_price.setText("￥" + washCarPrice2);
                        if (checkStates[2]) {
                            checkStates[2] = false;
                            place_extral_img.setImageResource(R.drawable.unchecked_pic);
                        }
                        if(choiceCoupon != null && !StringUtil.isNull(choiceCoupon.getCoupons_price())){
                            clChoiceCouponPrice();
                        }else {
                            caculateTotalPrice();
                        }
                    }
                    pay_total_money.setText("￥" + allTotalPrice);
                } else {
                    showToast("数据请求异常");
                }
                break;
            case R.id.place_img_change:
                //改变描述方式
                vgTxtDesc.setVisibility(vgTxtDesc.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                vgVoiceDesc.setVisibility(vgVoiceDesc.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.parkinfo_img_clear:
                // 清除描述文字
                place_edt_txtdesc.setText("");
                break;
            case R.id.car_beauty_layout:
                //美容服务界面
                if (washCarList != null && washCarList.size() > 0) {
                    Intent beautyIntent = new Intent(PlaceOrderActivity.this, BeautyServiceActivity.class);
                    beautyIntent.putExtra("carType", carType);
                    beautyIntent.putExtra(BeautyServiceActivity.beautyTotalPrice, beautyTotalPrice);
                    startActivityForResult(beautyIntent, BEAUTYREQUESTCODE);
                } else {
                    showToast("数据请求异常");
                }
                break;
            case R.id.place_submit_btn:
                // 订单详情页面
                if (allTotalPrice <= 0) {
                    showToast("请选择您的洗车服务");
                    return;
                }
                if (checkStates[1] && appointDay == 0) {
                    showToast("请选择预约的时间段");
                    return;
                }
                if (beautyTotalPrice > 0 && !checkStates[2] && !checkStates[3]) {
                    boolean isNeedWash = false;
                    if (Constants.beautyCheckList.size() != 4) {
                        return;
                    }
                    for (int i = 0; i < Constants.beautyCheckList.size(); i++) {
                        if (i != 3) {
                            for (int k = 0; k < Constants.beautyCheckList.get(i).length; k++) {
                                if (Constants.beautyCheckList.get(i)[k]) {
                                    isNeedWash = true;
                                    break;
                                }
                            }
                            if (isNeedWash) {
                                break;
                            }
                        }
                    }
                    if (isNeedWash) {
                        showToast("您所选的美容服务必须和洗车一起下单");
                        return;
                    }
                }
                String productId = "";
                if (checkStates[2] || checkStates[3]) {
                    if (!StringUtil.isNull(beautyProductId)) {
                        productId = beautyProductId + "," + washCarId;
                    } else {
                        productId = washCarId;
                    }
                } else {
                    productId = beautyProductId;
                }
                if (washCarList != null) {
                    if (cachProductList == null) {
                        cachProductList = new ArrayList<>();
                    }
                    if (checkStates[2] && washCarList.size() > 0) {
                        if (!cachProductList.contains(washCarList.get(0))) {
                            cachProductList.add(0, washCarList.get(0));
                        }
                    }
                    if (checkStates[3] && washCarList.size() > 1) {
                        if (!cachProductList.contains(washCarList.get(1))) {
                            cachProductList.add(0, washCarList.get(1));
                        }
                    }
                }
                Log.i("Tag", "cachProductList：" + cachProductList.size());
                Log.i("Tag", "车辆地址：" + order.getLocation());
                Log.i("Tag", "车辆Id：" + order.getC_ids());
                Log.i("Tag", "经度：" + order.getLocation_lg());
                Log.i("Tag", "维度：" + order.getLocation_lt());
                Log.i("Tag", "产品id：" + productId);
                Log.i("Tag", "预约时间戳：" + appointDay);
                Log.i("Tag", "预约时间段的id：" + appointTimeId);
                Log.i("Tag", "优惠券的id：" + couponsId);
                Log.i("Tag", "总价：" + allTotalPrice);
                if (confirmOrder == null) {
                    confirmOrder = new ConfirmOrder();
                }
                confirmOrder.setUserId(uid + "");
                confirmOrder.setCarsId(order.getC_ids() + "");
                confirmOrder.setCarNum(order.getC_plate_num());
                confirmOrder.setCarName(order.getC_brand());
                confirmOrder.setCarAddress(order.getLocation());
                confirmOrder.setCarLocateLg(order.getLocation_lg());  // 经度
                confirmOrder.setCarLocateLt(order.getLocation_lt()); //纬度
                confirmOrder.setProductId(productId);
                confirmOrder.setAllTotalPrice(allTotalPrice + "");
                confirmOrder.setReMark(place_edt_txtdesc.getText().toString());
                if(choiceCoupon != null){
                    confirmOrder.setCouponsId(choiceCoupon.getId());
                }else{
                    confirmOrder.setCouponsId(couponsId);
                }
                confirmOrder.setCachList(cachProductList);
                if (order.getFileVoice() != null) {
                    confirmOrder.setAudioFile(order.getFileVoice());
                }
                Intent orderDetIntent = new Intent(PlaceOrderActivity.this, PlaceOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PlaceOrderDetailActivity.orderDatas, confirmOrder);
                bundle.putSerializable(PlaceOrderDetailActivity.carTypeFlag, carType);
                orderDetIntent.putExtras(bundle);
                startActivity(orderDetIntent);
                break;
            case R.id.place_vg_homepark:
                // 家庭常用的地址
                Address addressHome = null;
                if (mAllAddress != null) {
                    addressHome = mAllAddress.getHomeAddress();
                }
                if (addressHome == null) {
                    return;
                }
                if (parkinfo_img_homep.getVisibility() == View.VISIBLE) {
                    // 变成选中
                    parkinfo_homepchecked.setVisibility(View.VISIBLE);
                    parkinfo_img_homep.setVisibility(View.GONE);
                    place_img_companyp.setVisibility(View.VISIBLE);
                    place_companypchecked.setVisibility(View.GONE);
                    car_plate_address.setText(addressHome.getAddress());
                    order.setLocation(addressHome.getAddress());
                    order.setLocation_lt(addressHome.getAddress_lt());
                    order.setLocation_lg(addressHome.getAddress_lg());
                    place_edt_txtdesc.setText(addressHome.getAddress_info());
                } else {
                    //变成不选中
                    parkinfo_homepchecked.setVisibility(View.GONE);
                    parkinfo_img_homep.setVisibility(View.VISIBLE);
                    if (place_companypchecked.getVisibility() == View.GONE) {
                        car_plate_address.setText(originalAddress);
                        order.setLocation(originalAddress);
                        order.setLocation_lt(originalLatitude);
                        order.setLocation_lg(originalLongitude);
                    }
                }
                break;
            case R.id.place_vg_companypark:
                //公司常用的地址
                Address addressCompany = null;
                if (mAllAddress != null) {
                    addressCompany = mAllAddress.getCompanyAddress();
                }
                if (addressCompany == null) {
                    return;
                }
//                showToast("选中公司位置");
                if (place_img_companyp.getVisibility() == View.VISIBLE) {
                    place_img_companyp.setVisibility(View.GONE);
                    place_companypchecked.setVisibility(View.VISIBLE);
                    parkinfo_img_homep.setVisibility(View.VISIBLE);
                    parkinfo_homepchecked.setVisibility(View.GONE);
                    car_plate_address.setText(addressCompany.getAddress());
                    order.setLocation(addressCompany.getAddress());
                    order.setLocation_lt(addressCompany.getAddress_lt());
                    order.setLocation_lg(addressCompany.getAddress_lg());
                    place_edt_txtdesc.setText(addressCompany.getAddress_info());
                } else {
                    place_img_companyp.setVisibility(View.VISIBLE);
                    place_companypchecked.setVisibility(View.GONE);
                    if (parkinfo_homepchecked.getVisibility() == View.GONE) {
                        car_plate_address.setText(originalAddress);
                        order.setLocation(originalAddress);
                        order.setLocation_lt(originalLatitude);
                        order.setLocation_lg(originalLongitude);
                    }
                }
                break;
            case R.id.place_txt_recordtime:
                // 播放录音
                if (order.getFileVoice() != null) {
                    if (voicePlayer != null) {
                        voicePlayer.stop();
                    } else {
                        voicePlayer = new VoicePlayer(PlaceOrderActivity.this, order.getFileVoice());
                        voicePlayer.setCallBack(new VoicePlayer.CallBack() {
                            @Override
                            public void onPlaying(int snum) {
                                txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                        , snum));
                            }

                            @Override
                            public void onFinish(int snum) {
                                txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                        , voicePlayer.getDuration()));
                                voicePlayer = null;
                            }
                        });
                        txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                , voicePlayer.getDuration()));
                        voicePlayer.start();
                    }
                } else {
                    Log.i("Tag", "order.getFileVoice() is null");
                }
                break;
            case R.id.place_coupons_layout:
                Intent couponsIntent = new Intent(PlaceOrderActivity.this, CouponActivity.class);
                startActivityForResult(couponsIntent,CHOICECOUPONS);
                break;
        }
    }

    /**
     * 计算自己选中优惠券后的总价
     */
    private void clChoiceCouponPrice(){
        if(!checkStates[2] && !checkStates[3]){
            if(choiceCoupon.getType() == 1){
                car_coupons_tv.setText("此券只能用于首次洗车免单");
                car_coupons_money.setText("-￥" + 0.0);
                allTotalPrice = beautyTotalPrice;
                return;
            }
            double choiceCouponPrice = Double.parseDouble(choiceCoupon.getCoupons_price());
            car_coupons_tv.setText(choiceCoupon.getCoupons_name());
            car_coupons_money.setText("-￥" + choiceCouponPrice);
            car_coupons_money.setVisibility(View.VISIBLE);
            allTotalPrice = beautyTotalPrice;
            if(allTotalPrice > choiceCouponPrice){
                allTotalPrice = allTotalPrice - choiceCouponPrice;
            }else {
                if(allTotalPrice > 0.0){
                    allTotalPrice = 0.01;
                }
            }
        }else if(checkStates[2]){
            if(choiceCoupon.getType() == 1){
                if(beautyTotalPrice != 0.0){
                    allTotalPrice = beautyTotalPrice;
                }else {
                    allTotalPrice = beautyTotalPrice + 0.01;
                }
                car_coupons_tv.setText("首次洗车免单");
                car_coupons_money.setVisibility(View.GONE);
            }else {
                double choiceCouponPrice = Double.parseDouble(choiceCoupon.getCoupons_price());
                car_coupons_tv.setText(choiceCoupon.getCoupons_name());
                car_coupons_money.setText("-￥" + choiceCouponPrice);
                car_coupons_money.setVisibility(View.VISIBLE);
                allTotalPrice = beautyTotalPrice + washCarPrice;
                if(allTotalPrice > choiceCouponPrice){
                    allTotalPrice = allTotalPrice - choiceCouponPrice;
                }else {
                    allTotalPrice = 0.01;
                }
            }
        }else if(checkStates[3]){
            if(choiceCoupon.getType() == 1){
                if(beautyTotalPrice != 0.0){
                    allTotalPrice = beautyTotalPrice;
                }else {
                    allTotalPrice = beautyTotalPrice + 0.01;
                }
                car_coupons_tv.setText("首次洗车免单");
                car_coupons_money.setVisibility(View.GONE);
            }else {
                car_coupons_tv.setText(choiceCoupon.getCoupons_name());
                car_coupons_money.setText("-￥"+choiceCoupon.getCoupons_price());
                car_coupons_money.setVisibility(View.VISIBLE);
                double choiceCouponPrice = Double.parseDouble(choiceCoupon.getCoupons_price());
                allTotalPrice = beautyTotalPrice + washCarPrice2;
                if(allTotalPrice > choiceCouponPrice){
                    allTotalPrice = allTotalPrice - choiceCouponPrice;
                }else {
                    allTotalPrice = 0.01;
                }
            }
        }
    }

    /**
     * 没有选中洗车的总价计算
     */
    private void noWashTotalPrice(){
        if(!checkStates[2] && !checkStates[3]){
            if(couponList != null){
                car_coupons_tv.setText("您有"+couponList.size()+"张优惠券");
                car_coupons_money.setVisibility(View.GONE);
            }else {
                car_coupons_money.setText("无可用的优惠券");
                car_coupons_money.setVisibility(View.GONE);
            }
        }
        if(beautyTotalPrice > 0.0){
            getCouponsMoney();
            if (position != -1) { // 有优惠券
                car_coupons_tv.setText(couponList.get(position).getCoupons_name());
                car_coupons_money.setVisibility(View.VISIBLE);
                if (!StringUtil.isNull(couponList.get(position).getCoupons_price())) {
                    double couponsPrice = Double.parseDouble(couponList.get(position).getCoupons_price());
                    allTotalPrice = beautyTotalPrice;
                    if (allTotalPrice > couponsPrice) {
                        car_coupons_money.setText("-￥" + couponsPrice);
                        allTotalPrice = allTotalPrice - couponsPrice;
                    } else {
                        if(beautyTotalPrice == 0.0){
                            allTotalPrice = beautyTotalPrice;
                        }else {
                            car_coupons_money.setText("-￥" + allTotalPrice);
                            allTotalPrice = 0.01;
                        }
                    }
                }
            } else { // 无优惠券
                car_coupons_money.setText("无可用的优惠券");
                car_coupons_money.setVisibility(View.GONE);
                allTotalPrice = beautyTotalPrice;
            }
        }
    }

    /**
     * 计算总价（外部清洗和整车清洗和选了美容后）
     */
    private void caculateTotalPrice() {
        if(checkStates[2]){
            if (getFirstWash() == 10000 && position != -1) {
                couponsPrice = washCarPrice - 0.01;
                car_coupons_tv.setText("首次洗车免单");
                car_coupons_money.setVisibility(View.GONE);
//                car_coupons_money.setText("-￥" + washCarPrice);
                if(beautyTotalPrice != 0.0){
                    allTotalPrice = beautyTotalPrice;
                }else {
                    allTotalPrice = 0.01;
                }
                allTotalPrice = SystermUtils.getTwonum(allTotalPrice);
            } else if (getFirstWash() != 10000) {
                getCouponsMoney();
                if (position != -1) {
                    if (!StringUtil.isNull(couponsList.get(position).getCoupons_price())) {
                        couponsPrice = Double.parseDouble(couponsList.get(position).getCoupons_price());
                        car_coupons_tv.setText(couponList.get(position).getCoupons_name());
                        car_coupons_money.setVisibility(View.VISIBLE);
                        // 不是第一次洗车
                        allTotalPrice = washCarPrice + beautyTotalPrice;
                        if (allTotalPrice > couponsPrice) {
                            car_coupons_money.setText("-￥" + couponsPrice);
                            allTotalPrice = allTotalPrice - couponsPrice;
                        } else {
                            car_coupons_money.setText("-￥" + allTotalPrice);
                            allTotalPrice = 0.01;
                        }
                    }
                } else {
                    //没有优惠券
                    allTotalPrice = washCarPrice + beautyTotalPrice;
                    car_coupons_money.setText("无可用的优惠券");
                    car_coupons_money.setVisibility(View.GONE);
                }
            }
        }else if(checkStates[3]){
            if (getFirstWash() == 10000 && position != -1) {
                couponsPrice = washCarPrice2 - 0.01;
                car_coupons_tv.setText("首次洗车免单");
                car_coupons_money.setVisibility(View.GONE);
//                car_coupons_money.setText("-￥" + washCarPrice2);
                if(beautyTotalPrice != 0.0){
                    allTotalPrice = beautyTotalPrice;
                }else {
                    allTotalPrice = 0.01;
                }
                allTotalPrice = SystermUtils.getTwonum(allTotalPrice);
            } else if (getFirstWash() != 10000) {
                getCouponsMoney();
                if (position != -1) {
                    if (!StringUtil.isNull(couponsList.get(position).getCoupons_price())) {
                        couponsPrice = Double.parseDouble(couponsList.get(position).getCoupons_price());
                        car_coupons_tv.setText(couponList.get(position).getCoupons_name());
                        car_coupons_money.setVisibility(View.VISIBLE);
                        // 不是第一次洗车
                        allTotalPrice = washCarPrice2 + beautyTotalPrice;
                        car_coupons_money.setVisibility(View.VISIBLE);
                        if (allTotalPrice > couponsPrice) {
                            car_coupons_money.setText("-￥" + couponsPrice);
                            allTotalPrice = allTotalPrice - couponsPrice;
                        } else {
                            car_coupons_money.setText("-￥" + allTotalPrice);
                            allTotalPrice = 0.01;
                        }
                    }
                } else {
                    //没有优惠券
                    allTotalPrice = washCarPrice2 + beautyTotalPrice;
                    car_coupons_money.setText("无可用的优惠券");
                    car_coupons_money.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 是否是第一次洗车
     *
     * @return
     */
    private double getFirstWash() {
        if (couponList == null) {
            return 0.0;
        }
        for (int k = 0; k < couponList.size(); k++) {
            Coupon coupon = couponList.get(k);
            // 得到免费洗车券
            if (coupon.getState() == 0 && coupon.getType() == 1) {
                couponsId = coupon.getId();
                position = k;
                return 10000.0;
            }
        }
        return 0.0;
    }

    /**
     * 得到优惠的价格
     */
    private double getCouponsMoney() {
        long litleTime = SystermUtils.getTimeScopeTwo("2050-12-12");
        if (couponList == null) {
            return 0.0;
        }
        for (int i = 0; i < couponList.size(); i++) {
            Coupon coupon = couponList.get(i);
            // 得到现金抵用券
            if (coupon.getState() == 0 && coupon.getType() == 0) {
                if (StringUtil.isNull(coupon.getServer_time())) {
                    continue;
                }
                if (StringUtil.isNull(coupon.getEffective_time())) {
                    continue;
                }
                if (StringUtil.isNull(coupon.getExpired_time())) {
                    continue;
                }
                long serverTime = SystermUtils.getTimeScopeTwo(coupon.getServer_time());
                long startTime = SystermUtils.getTimeScopeTwo(coupon.getEffective_time());
                long endTime = SystermUtils.getTimeScopeTwo(coupon.getExpired_time());
                Log.i("Tag", "serverTime=>" + serverTime + "/startTime=>" + startTime + "/endTime=>" + endTime);
                if (serverTime < startTime) {
                    continue;
                }
                if (serverTime > endTime) {
                    continue;
                }
                if (StringUtil.isNull(coupon.getCoupons_price())) {
                    continue;
                }
                if (litleTime > endTime) {
                    litleTime = endTime;
                    position = i;
                    couponsId = coupon.getId();
                }
            }
        }
        return 0.0;
    }

    /**
     * 判断手机是否有SD卡存在
     *
     * @return
     */
    private boolean isExitSdCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    class RecordOnTouchListener implements View.OnTouchListener {
        private VoiceRecorder voiceRecorder;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!isExitSdCard()) {
                        showToast("没有SD，不能录音");
                        break;
                    }
                    //按下开始录音
                    Log.i("Tag", "----开始录音---");
                    voiceRecorder = new VoiceRecorder(FileUtil.getFile(
                            Constants.PATH_VOICE + File.separator + System.currentTimeMillis() + ".amr"));
                    voiceRecorder.setCallBack(new VoiceRecorder.CallBack() {
                        @Override
                        public void onFailed() {
                            showToast("录音出错");
                        }

                        @Override
                        public void onRecording(int snum) {
                            txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time), snum));
                        }

                        @Override
                        public void onFinish(int snum) {
                            if (snum == 0) {
                                showToast("录制时间过短");
                                if (order.getFileVoice() == null) {
                                    txtRecordTime.setVisibility(View.GONE);
                                } else {
                                    int duration = MediaPlayer.create(PlaceOrderActivity.this, Uri.fromFile(order.getFileVoice())).getDuration() / 1000;
                                    txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                            , duration));
                                }
                            } else {
                                txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time), snum));
                                order.setFileVoice(voiceRecorder.getFileSource());
                                voiceRecorder = null;
                            }
                        }
                    });
                    txtRecordTime.setVisibility(View.VISIBLE);
                    txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time), 0));
                    voiceRecorder.start();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    cancelRecord(voiceRecorder);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (voiceRecorder == null) {
                        break;
                    }
                    final int location[] = new int[2];
                    v.getLocationOnScreen(location);
                    final float fx = location[0] + event.getX();
                    final float fy = location[1] + event.getY();

                    final Rect rect = new Rect();
                    v.getGlobalVisibleRect(rect);
                    rect.offsetTo(location[0], location[1]);

                    if (!rect.contains((int) fx, (int) fy)) {
                        //手指移出按钮外
                        cancelRecord(voiceRecorder);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //停止录音
                    Log.i("Tag", "----结束录音---");
                    if (voiceRecorder != null)
                        voiceRecorder.stop();
                    break;
            }
            return false;
        }
    }

    /**
     * 取消录音
     *
     * @param voiceRecorder
     */
    private void cancelRecord(VoiceRecorder voiceRecorder) {
        if (voiceRecorder != null) {
            voiceRecorder.cancel();
        }
        voiceRecorder = null;
        if (order.getFileVoice() != null) {
            int duration = MediaPlayer.create(PlaceOrderActivity.this, Uri.fromFile(order.getFileVoice())).getDuration() / 1000;
            txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                    , duration));
        } else {
            txtRecordTime.setVisibility(View.GONE);
        }
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xbb.broadcast.LOCAL_FINISH_SUBSCRIBE")) {
                finish();
            }
        }
    }
}
