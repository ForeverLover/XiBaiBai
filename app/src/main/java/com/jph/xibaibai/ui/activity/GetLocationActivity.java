package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.MapUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 获取定位
 * Created by jph on 2015/9/7.
 */
public class GetLocationActivity extends TitleActivity implements View.OnClickListener, BDLocationListener,
        BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener, BaiduMap.OnMapLoadedCallback {

    public static final String EXTRA_INIT_ADDRESS = "extra_init_address";
    public static final String RESULT_ADDRESS = "result_address";

    private Address address;
    private LocationClient locationClient;
    private LatLng latLngCurrent;//当前位置
    private GeoCoder geoCoder;

    @ViewInject(R.id.getlocation_map)
    MapView mapView;
    @ViewInject(R.id.getlocation_txt_address)
    TextView txtAddress;//标记点位置
    @ViewInject(R.id.getlocation_v_action)
    View vAction;//操作区

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getlocation);

        mapView.getMap().setMyLocationEnabled(true);
        locationClient.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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
    public void initData() {
        super.initData();
        address = (Address) getIntent().getSerializableExtra(EXTRA_INIT_ADDRESS);

        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(Constants.LOCATION_SCAN_SPAN);
        locationClient.setLocOption(option);

        //地址解析
        geoCoder = GeoCoder.newInstance();
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("选择位置");
        showTitleBtnRight("确定");
        MapUtil.hideZoomController(mapView);

    }

    @Override
    public void initListener() {
        super.initListener();

        mapView.getMap().setOnMapLoadedCallback(this);
        mapView.getMap().setOnMapStatusChangeListener(this);
        geoCoder.setOnGetGeoCodeResultListener(this);
    }

    @Override
    protected void onClickTitleRight(View v) {
        super.onClickTitleRight(v);
        Intent intentResult = new Intent();
        intentResult.putExtra(RESULT_ADDRESS, address);
        setResult(RESULT_OK, intentResult);
        finish();
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
        txtAddress.setText(reverseGeoCodeResult.getAddress());
        address.setAddress(reverseGeoCodeResult.getAddress());
        address.setAddress_lt(String.valueOf(reverseGeoCodeResult.getLocation().latitude));
        address.setAddress_lg(String.valueOf(reverseGeoCodeResult.getLocation().longitude));
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
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption()
//                .location(mapView.getMap().getProjection().fromScreenLocation(new Point(mapView.getWidth() / 2,
//                        mapView.getHeight() - getResources().getDimensionPixelOffset(R.dimen.main_mark_marginbottom)))));


    }

    @Override
    public void onMapLoaded() {

    }

    @OnClick({R.id.getlocation_img_mark})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.getlocation_img_mark:
                //点击图钉
                vAction.setVisibility(vAction.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;

        }
    }

}
