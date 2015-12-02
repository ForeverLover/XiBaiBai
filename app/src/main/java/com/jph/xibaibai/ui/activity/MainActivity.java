package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.model.entity.AllAddress;
import com.jph.xibaibai.model.entity.AllCar;
import com.jph.xibaibai.model.entity.Car;
import com.jph.xibaibai.model.entity.LimitRule;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.OverlyCoordinate;
import com.jph.xibaibai.model.entity.PolugonCoord;
import com.jph.xibaibai.model.entity.Product;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.entity.ServiceTime;
import com.jph.xibaibai.model.entity.UserInfo;
import com.jph.xibaibai.model.entity.Version;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.ProHScrollView;
import com.jph.xibaibai.mview.SelfDialogView;
import com.jph.xibaibai.receiver.ConnectionChangeReceiver;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.jph.xibaibai.utils.MImageLoader;
import com.jph.xibaibai.utils.MapUtil;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.VersionUpdate;
import com.jph.xibaibai.utils.parsejson.OverlycdParse;
import com.jph.xibaibai.utils.parsejson.VersionParse;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.jph.xibaibai.utils.updateversion.UpdateUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jph on 2015/8/19.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, BDLocationListener,
        BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener, BaiduMap.OnMapLoadedCallback,
        ProHScrollView.OnItemCheckListener {
    private static final int REQUEST_CODE_SEARCH_ADDRESS = 980;
    private static final int REQUEST_CODE_DIY_SERVICE = 981;

    private int uid;
    private SPUserInfo spUserInfo;
    private LocationClient locationClient;
    private LatLng latLngCurrent;//当前位置
    private GeoCoder geoCoder;
    private IAPIRequests apiRequests;
    private Order order;
    private AllAddress allAddress;
    private boolean getRuled = false;//是否得到限号规则
    private ServiceTime serviceTime;

    private BaiduMap mBaiduMap;

    private boolean isOpenArea = false;

    private OverlyCoordinate overlyCd;

    private ConnectionChangeReceiver myNetReceiver = null;

    @ViewInject(R.id.main_drawerlayout)
    DrawerLayout drawerLayout;
    @ViewInject(R.id.main_map)
    MapView mapView;
    @ViewInject(R.id.main_txt_address)
    TextView txtAddress;//标记点位置
    @ViewInject(R.id.main_v_action)
    View vAction;//操作区
    @ViewInject(R.id.main_v_pros)
    ProHScrollView mProHScrollView;
    @ViewInject(R.id.main_vg_addcar)
    ViewGroup vgAddCar;
    @ViewInject(R.id.main_vg_platenum)
    ViewGroup vgPlateNum;
    @ViewInject(R.id.main_txt_platenum)
    TextView txtPlateNum;//车牌
    @ViewInject(R.id.main_txt_name)
    TextView txtName;
    @ViewInject(R.id.main_txt_phone)
    TextView txtPhone;
    @ViewInject(R.id.main_img_head)
    ImageView imgHead;
    @ViewInject(R.id.main_img_left)
    ImageView imgHeadSmall;
    @ViewInject(R.id.main_txt_limitrule)
    TextView mTxtLimitRule;
    @ViewInject(R.id.service_area_img)
    ImageView serviceArea;
    @ViewInject(R.id.main_isopne_tv)
    TextView main_isopne_tv;// 是否开通此城市的显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        if (locationClient != null) {
            locationClient.start();
        }
        getBrocast();
        apiRequests.getProducts();
        apiRequests.getUserInfo(uid);
        apiRequests.getServiceTime();
        apiRequests.getServiceArea();
        apiRequests.getVersionInfo();
    }

    private void getBrocast(){
        myNetReceiver = new ConnectionChangeReceiver(serviceArea);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myNetReceiver, mFilter);
    }

    /**
     * 添加覆盖物
     */
    private void overlyMap() {
        if (overlyCd == null) {
            return;
        }
        List<PolugonCoord> circleList = overlyCd.getCircleList();
        if (circleList != null) {
            for (int i = 0; i < circleList.size(); i++) {
                if (!StringUtil.isNull(circleList.get(i).getServer_lat()) && !StringUtil.isNull(circleList.get(i).getServer_lng())) {
                    LatLng llCircle = new LatLng(Double.parseDouble(circleList.get(i).getServer_lat()), Double.parseDouble(circleList.get(i).getServer_lng()));
                    if (!StringUtil.isNull(circleList.get(i).getRadius())) {
                        OverlayOptions ooCircle = new CircleOptions().fillColor(0x1000FF99)
                                .center(llCircle).stroke(new Stroke(5, 0x1000FF99))
                                .radius(Integer.parseInt(circleList.get(i).getRadius()));
                        mBaiduMap.addOverlay(ooCircle);
                    }
                }
            }
        }
        List<ArrayList<PolugonCoord>> allCityList = overlyCd.getAllCityList();
        if (allCityList != null) {
            for (int i = 0; i < allCityList.size(); i++) {
                List<LatLng> pts = new ArrayList<LatLng>();
                List<PolugonCoord> polugonList = allCityList.get(i);
                for (int k = 0; k < polugonList.size(); k++) {
                    if (!StringUtil.isNull(polugonList.get(k).getServer_lat()) && !StringUtil.isNull(polugonList.get(k).getServer_lng())) {
                        pts.add(new LatLng(Double.parseDouble(polugonList.get(k).getServer_lat()), Double.parseDouble(polugonList.get(k).getServer_lng())));
                    }
                }
                OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                        .stroke(new Stroke(5, 0x1000FF99)).fillColor(0x1000FF99);
                mBaiduMap.addOverlay(ooPolygon);
            }
        }


        LatLng llCircle = new LatLng(30.666272, 104.078825);
        OverlayOptions ooCircle = new CircleOptions().fillColor(0x9000FF99)
                .center(llCircle).stroke(new Stroke(5, 0x9000FF99))
                .radius(7500);
        mBaiduMap.addOverlay(ooCircle);

        List<LatLng> pts = new ArrayList<LatLng>();
        pts.add(new LatLng(30.492064, 104.088663));
        pts.add(new LatLng(30.491791, 104.036779));
        pts.add(new LatLng(30.605105, 104.054436));
        pts.add(new LatLng(30.601573, 104.115938));
        OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                .stroke(new Stroke(5, 0x9000FF99)).fillColor(0x9000FF99);
        mBaiduMap.addOverlay(ooPolygon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        apiRequests.getAddress(uid);
        String jsonUserInfo = SPUserInfo.getsInstance(this).getSP(SPUserInfo.KEY_USERINFO);
        if (!TextUtils.isEmpty(jsonUserInfo)) {
            UserInfo userInfo = JSON.parseObject(jsonUserInfo, UserInfo.class);
            txtName.setText(userInfo.getUname());
            txtPhone.setText(userInfo.getIphone());
        }
        apiRequests.getCar(uid);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        mapView.getMap().setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_SEARCH_ADDRESS:
                //得到搜索地址信息
                Address address = (Address) data.getSerializableExtra(SearchAddressActivity.RESULT_ADDRESS);
                LatLng latLng = new LatLng(Double.valueOf(address.getAddress_lt()), Double.valueOf(address.getAddress_lg()));
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
                mapView.getMap().animateMapStatus(u);
                break;
            case REQUEST_CODE_DIY_SERVICE:
                //得到选择的diy服务
                List<Integer> listProIds = (List<Integer>) data.getSerializableExtra(DIYProActivity.RESULT_PROIDS);
                double totalPrice = data.getDoubleExtra(DIYProActivity.RESULT_TOTAL_PRICE, 0);
                Log.i("Tag", "Main=>" + totalPrice);
                if (listProIds != null) {
                    order.setP_ids(StringUtil.getStringByList(listProIds));
                }
                order.setTotal_price(totalPrice);
                sendLocalBroadCast(totalPrice);
                break;
        }
    }

    private void sendLocalBroadCast(double totalPrice) {
        LocalBroadcastManager localBroadcastManager = null;
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("com.xbb.broadcast.LOCAL_UPDATE_PRICE");
        intent.putExtra("DIY_Totalprice", totalPrice);
        localBroadcastManager.sendBroadcast(intent);//调用sendBroadcast()方法发送广播

    }

    @Override
    public void initData() {
        super.initData();
        spUserInfo = SPUserInfo.getsInstance(this);
        uid = spUserInfo.getSPInt(SPUserInfo.KEY_USERID);
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(20000);
        locationClient.setLocOption(option);
        //地址解析
        geoCoder = GeoCoder.newInstance();
        apiRequests = new APIRequests(this);
        order = new Order();
        order.setUid(SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID));
    }

    @Override
    public void initView() {
        super.initView();
        drawerLayout.setScrimColor(0x32000000);// 设置半透明度
        MapUtil.hideZoomController(mapView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayout.HORIZONTAL);// 添加分割线
//        recyclerViewPro.setLayoutManager(llm);
//        recyclerViewPro.setAdapter(productAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mapView.getMap().setOnMapLoadedCallback(this);
        mapView.getMap().setOnMapStatusChangeListener(this);
        geoCoder.setOnGetGeoCodeResultListener(this);
        mProHScrollView.setOnItemCheckListener(this);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        // map view 销毁后不在处理新接收的位置
        if (bdLocation == null || mapView == null)
            return;
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        mapView.getMap().setMyLocationData(locData);

        LatLng latLng = new LatLng(bdLocation.getLatitude(),
                bdLocation.getLongitude());
        if (latLngCurrent == null) {
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
            mapView.getMap().animateMapStatus(u);
        }
        latLngCurrent = latLng;
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(latLngCurrent));
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult.getAddressDetail() != null) {
            String cityL = reverseGeoCodeResult.getAddressDetail().city;
            if (cityL != null && !cityL.equals(spUserInfo.getSP(SPUserInfo.KEY_CITY))) {
                //定位城市和本地缓存城市不一致
                spUserInfo.setSP(SPUserInfo.KEY_CITY, cityL);
            }
            if (!getRuled) {
                apiRequests.getCityLimitRule(spUserInfo.getSP(SPUserInfo.KEY_CITY));
            }
        }
        txtAddress.setText(reverseGeoCodeResult.getAddress());
        if (!isValidCity(spUserInfo.getSP(SPUserInfo.KEY_CITY))) {
            //城市不支持
            main_isopne_tv.setText("该城市暂未开通，点击申请开通");
        } else {
            main_isopne_tv.setText(getString(R.string.main_txt_now));
        }
        order.setLocation(reverseGeoCodeResult.getAddress());
        if (reverseGeoCodeResult.getLocation() != null) {
            order.setLocation_lg(reverseGeoCodeResult.getLocation().longitude + "");
            order.setLocation_lt(reverseGeoCodeResult.getLocation().latitude + "");
            Log.i("Tag", "longitude=" + reverseGeoCodeResult.getLocation().longitude + "/latitude=" + reverseGeoCodeResult.getLocation().latitude);
        }
        if (locationClient != null) {
            locationClient.stop();
        }
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
        vAction.setVisibility(View.GONE);
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        vAction.setVisibility(View.VISIBLE);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(mapStatus.target));
        Log.i("Tag", "Drag Map!");
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption()
//                .location(mapView.getMap().getProjection().fromScreenLocation(new Point(mapView.getWidth() / 2,
//                        mapView.getHeight() - getResources().getDimensionPixelOffset(R.dimen.main_mark_marginbottom)))));


    }

    @Override
    public void onMapLoaded() {

    }

    @OnClick({R.id.main_img_left, R.id.main_vg_addcar, R.id.main_txt_platenum, R.id.main_img_mark,
            R.id.main_img_home, R.id.main_img_company, R.id.main_img_currentloc, R.id.main_ll_now,
            R.id.main_ll_reserve, R.id.service_area_img})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_img_left:
                //打开关闭侧边栏
                toggleLeftLayout();
                break;
            case R.id.main_vg_addcar:
            case R.id.main_txt_platenum:
                //车辆列表
                startActivity(CarsActivity.class);
                break;
            case R.id.main_img_mark:
                //点击图钉
                vAction.setVisibility(vAction.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.main_img_home:
                //定位到家
                if (allAddress != null && allAddress.getHomeAddress() != null &&
                        allAddress.getHomeAddress().getAddress_lt() != null && allAddress.getHomeAddress().getAddress_lg() != null) {
                    MapStatusUpdate uHome = MapStatusUpdateFactory.newLatLng(
                            new LatLng(Double.valueOf(allAddress.getHomeAddress().getAddress_lt()),
                                    Double.valueOf(allAddress.getHomeAddress().getAddress_lg())));
                    mapView.getMap().animateMapStatus(uHome);
                } else {
                    showToast("请设置地址");
                    startActivity(AddressActivity.class);
                }
                break;
            case R.id.main_img_company:
                //定位到公司
                if (allAddress != null && allAddress.getCompanyAddress() != null &&
                        allAddress.getCompanyAddress().getAddress_lt() != null && allAddress.getCompanyAddress().getAddress_lg() != null) {

                    MapStatusUpdate uCompany = MapStatusUpdateFactory.newLatLng(
                            new LatLng(Double.valueOf(allAddress.getCompanyAddress().getAddress_lt()),
                                    Double.valueOf(allAddress.getCompanyAddress().getAddress_lg())));
                    mapView.getMap().animateMapStatus(uCompany);
                } else {
                    showToast("请设置地址");
                    startActivity(AddressActivity.class);
                }
                break;
            case R.id.main_img_currentloc:
                //定位到当前位置
                if (latLngCurrent != null) {
                    MapStatusUpdate uCurrent = MapStatusUpdateFactory.newLatLng(latLngCurrent);
                    mapView.getMap().animateMapStatus(uCurrent);
                }
                break;
            case R.id.main_ll_now:
                //点击立即上门
                if (TextUtils.isEmpty(order.getC_ids())) {
                    //无默认车辆
                    showToast("请设置默认车辆");
                    startActivity(CarsActivity.class);
                    break;
                }

                if (StringUtil.isNull(txtAddress.getText().toString())) {
                    showToast("地址信息为空");
                    return;
                }

                if (!isValidCity(spUserInfo.getSP(SPUserInfo.KEY_CITY))) {
                    //城市不支持
                    /*new APIRequests(null).commitInvalidOrderInfo(uid, spUserInfo.getSP(SPUserInfo.KEY_CITY),
                            order.getLocation(), order.getLocation_lt(), order.getLocation_lg());*/
                    Log.i("Tag", "uid =" + uid + "/getLocation_lt=" + order.getLocation_lt() + "/getLocation_lg=" + order.getLocation_lg());
                    apiRequests.applyOpenCity(uid, order.getLocation_lg(), order.getLocation_lt(), order.getLocation());
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                String currentDate = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE) + " ";
                String timeCurrent = currentDate + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                try {
                    if (serviceTime != null) {
                        if (StringUtil.isNull(serviceTime.getReserve_start_time())) {
                            serviceTime.setReserve_start_time("09:30");
                        }
                        if (StringUtil.isNull(serviceTime.getReserve_end_time())) {
                            serviceTime.setReserve_end_time("17:30");
                        }
                    } else {
                        serviceTime = new ServiceTime();
                        serviceTime.setReserve_start_time("09:30");
                        serviceTime.setReserve_end_time("17:30");
                    }
                    String sTime = currentDate + serviceTime.getReserve_start_time();
                    String eTime = currentDate + serviceTime.getReserve_end_time();
                    Date sDate = new SimpleDateFormat("yyyy/MM/dd HH:mm")
                            .parse(sTime);
                    Date eDate = new SimpleDateFormat("yyyy/MM/dd HH:mm")
                            .parse(eTime);
                    Date cDate = new SimpleDateFormat("yyyy/MM/dd HH:mm")
                            .parse(timeCurrent);
                    Intent intentNow = new Intent(this, PlaceOrderActivity.class);
                    intentNow.putExtra(PlaceOrderActivity.TRANSKEY_ORDER, order);
                    startActivity(intentNow);
                    if (cDate.getTime() >= sDate.getTime() && cDate.getTime() <= eDate.getTime()) {
//                        Intent intentNow = new Intent(this, NowVisitActivity.class);
                        /*Intent intentNow = new Intent(this, PlaceOrderActivity.class);
                        Log.i("Tag","Mainorder=>uid="+order.getUid()+"/adress"+order.getLocation()+"/"+order.getC_name()+"/"+order.getCurrent_address());
                        intentNow.putExtra(PlaceOrderActivity.TRANSKEY_ORDER, order);
                        startActivity(intentNow);*/
                    } else {
                        final SelfDialogView dialog = new SelfDialogView(MainActivity.this);
                        dialog.setMsgTips("即刻上门下单提示");
                        dialog.setMessage("请在" + serviceTime.getReserve_start_time() + "-" + serviceTime.getReserve_end_time() + "之间下单");
                        dialog.setClickListener(new SelfDialogView.CustomClickListener() {

                            @Override
                            public void confirm() {
                                dialog.dismiss();
                            }

                            @Override
                            public void cancel() {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.main_ll_reserve:
                //预约上门
                if (TextUtils.isEmpty(order.getC_ids())) {
                    //无默认车辆
                    showToast("请设置默认车辆");
                    startActivity(CarsActivity.class);
                    break;
                }
                if (!isValidCity(spUserInfo.getSP(SPUserInfo.KEY_CITY))) {
                    //城市不支持
                    new APIRequests(null).commitInvalidOrderInfo(uid, spUserInfo.getSP(SPUserInfo.KEY_CITY),
                            order.getLocation(), order.getLocation_lt(), order.getLocation_lg());
                    showToast("当前城市暂未开通");
                    break;
                }
                Intent intentReverse = new Intent(this, ReserveActivity.class);
                intentReverse.putExtra(ReserveActivity.INTENTKEY_ORDER, order);
                startActivity(intentReverse);
                break;
            case R.id.service_area_img:
                // 打开关闭服务地区地图显示
                if (!isOpenArea) {
                    // 由关闭变为打开
                    serviceArea.setImageResource(R.drawable.show_overly);
                    overlyMap();
                    isOpenArea = true;
                } else {
                    // 由打开变为关闭
                    serviceArea.setImageResource(R.drawable.unshow_overly);
                    if (mBaiduMap != null) {
                        mBaiduMap.clear();
                    }
                    isOpenArea = false;
                }
                break;
        }
    }

    /**
     * 点击地址搜索
     *
     * @param v
     */
    @OnClick({R.id.main_txt_addresstag, R.id.main_txt_address})
    public void onClickAddress(View v) {
        Intent intentSearchAddress = new Intent(this, SearchAddressActivity.class);
        startActivityForResult(intentSearchAddress, REQUEST_CODE_SEARCH_ADDRESS);
    }

    @OnClick({R.id.main_vg_setting, R.id.main_vg_profile, R.id.main_vg_order, R.id.main_vg_wallet,
            R.id.main_vg_comment, R.id.main_vg_message, R.id.main_vg_cust_service})
    public void onClickInLeft(View v) {
        switch (v.getId()) {
            case R.id.main_vg_setting:
                //点击设置
                startActivity(SettingActivity.class);
                break;
            case R.id.main_vg_profile:
                //点击个人信息
                startActivity(ProfileActivity.class);
                break;
            case R.id.main_vg_order:
                //订单
                startActivity(OrderActivity.class);
                break;
            case R.id.main_vg_wallet:
                //钱包
                startActivity(WalletActivity.class);
                break;
            case R.id.main_vg_comment:
                startActivity(CommentActivity.class);
                break;
            case R.id.main_vg_message:
                //系统消息
                startActivity(MessageActivity.class);
                break;
            case R.id.main_vg_cust_service:
                startActivity(CustomPhoneActivity.class);
                break;
        }
        toggleLeftLayout();
    }

    @Override
    public void onPrepare(int taskId) {
        if (taskId == Tasks.GET_CITY_LIMIT_RULE || taskId == Tasks.GETADDRESS || taskId == Tasks.GETCAR) {
            return;
        }
        super.onPrepare(taskId);
    }

    @Override
    public void onFailed(int taskId, int errorCode, String errorMsg) {
        if (taskId == Tasks.GET_CITY_LIMIT_RULE) {
            return;
        }
        super.onFailed(taskId, errorCode, errorMsg);
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GETUSERINFO:
                //得到用户信息
                SPUserInfo.getsInstance(this).setSP(SPUserInfo.KEY_USERINFO, responseJson.getResult().toString());
                Log.i("Tag", "personInfo=>" + responseJson.getResult().toString());
                UserInfo userInfo = JSON.parseObject(responseJson.getResult().toString(), UserInfo.class);
                txtName.setText(userInfo.getUname());
                txtPhone.setText(userInfo.getIphone());
                MImageLoader.getInstance(this).displayImageM(userInfo.getU_img(), imgHead);
                MImageLoader.getInstance(this).displayImageM(userInfo.getU_img(), imgHeadSmall);
                break;
            case Tasks.GETPRODUCTS:
                //得到产品列表
                mProHScrollView.setup(JSON.parseArray(responseJson.getResult().toString(), Product.class), 0);
                break;
            case Tasks.GETCAR:
                //得到所有车辆
                AllCar allCar = null;
                if (responseJson.getResult() == null) {
                    allCar = new AllCar();
                } else {
                    allCar = JSON.parseObject(responseJson.getResult().toString(), AllCar.class);
                }
                if (allCar.getList().isEmpty()) {
                    //无车辆
                    vgPlateNum.setVisibility(View.GONE);
                    vgAddCar.setVisibility(View.VISIBLE);
                } else {
                    Car carDefault = allCar.getDefaultCar();
                    if (carDefault != null) {
                        vgPlateNum.setVisibility(View.VISIBLE);
                        txtPlateNum.setText(carDefault.getC_plate_num());
                        vgAddCar.setVisibility(View.GONE);

                        order.setC_ids(String.valueOf(carDefault.getId()));
                        order.setC_plate_num(carDefault.getC_plate_num());
                        order.setC_brand(carDefault.getC_brand());
                        order.setC_color(carDefault.getC_color());
                        order.setC_Type(carDefault.getC_type());
                    } else {
                        Log.i("Tag", "carDefault is Null");
                    }

                }
                break;
            case Tasks.GETADDRESS:
                //得到地址
                if (responseJson.getResult() != null) {
                    allAddress = JSON.parseObject(responseJson.getResult().toString(), AllAddress.class);
                    SPUserInfo.getsInstance(this).setSP(SPUserInfo.KEY_ALL_ADDRESS, responseJson.getResult().toString());
                }
                break;
            case Tasks.GET_CITY_LIMIT_RULE:
                // 得到限号规则
                getRuled = true;
                LimitRule limitRule = JSON.parseObject(responseJson.getResult().toString(), LimitRule.class);
                if (TextUtils.isEmpty(limitRule.getTail_number())) {
                    mTxtLimitRule.setText("今日不限行");
                } else {
                    mTxtLimitRule.setText(limitRule.getTail_number().replace(",", "  "));
                }
                break;

            case Tasks.GETSERVICE_TIME:
                // 得到服务的时间
                serviceTime = JSON.parseObject(responseJson.getResult().toString(), ServiceTime.class);
                break;

            case Tasks.GETSERVICE_AREA:
                Log.i("Tag", "ServiceArea=" + responseJson.getResult().toString());
                overlyCd = OverlycdParse.getResult(responseJson.getResult().toString());
                /*if (overlyCd != null) {
                    if (overlyCd.getAllCityList() != null) {
                        Log.i("Tag", "OverlyCoordinate=>" + overlyCd.getAllCityList().size());
                        for (int i = 0; i < overlyCd.getAllCityList().size(); i++) {
                            ArrayList list = overlyCd.getAllCityList().get(i);
                            Log.i("Tag", "overlyCd.getAllCityList()=>" + i + ":" + list.size());
                        }
                    }
                    Log.i("Tag", "overlyCd.getCircleList()=>" + overlyCd.getCircleList().size());
                }*/
                break;
            case Tasks.APPLYOPENCITY:
                if (!StringUtil.isNull(responseJson.getMsg().toString())) {
                    showToast(responseJson.getMsg().toString());
                }
                break;
            case Tasks.VERSIONINFO:
                //版本更新
                if(!StringUtil.isNull(responseJson.getResult().toString())){
                    try {
                        Version version = VersionParse.getVersionInfo(responseJson.getResult().toString());
                        if(version != null){
                            Log.i("Tag","versionPath:"+version.getPath());
                            new UpdateUtil(MainActivity.this,version);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }


    /**
     * 改变左侧边栏打开状态
     */
    public void toggleLeftLayout() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void onItemCheck(int position, Product product) {
        /*List<Integer> listProId = new ArrayList<Integer>();
        listProId.add(product.getId());

        if (position == mProHScrollView.getListPro().size() - 1) {
            order.setOrder_name(product.getP_name());
            startActivityForResult(new Intent(this, DIYProActivity.class), REQUEST_CODE_DIY_SERVICE);
        } else {
            order.setP_ids(StringUtil.getStringByList(listProId));
            order.setTotal_price(product.getP_price());
            order.setOrder_name(product.getP_name());
        }*/
    }

    private boolean isValidCity(String city) {
        if (city.contains("成都") || city.contains("重庆")) {
            return true;
        }
        return false;
    }
}
