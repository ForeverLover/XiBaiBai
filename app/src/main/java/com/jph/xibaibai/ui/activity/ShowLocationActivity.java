package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.MapUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 显示定位
 * Created by jph on 2015/9/13.
 */
public class ShowLocationActivity extends TitleActivity implements BDLocationListener {

    public static final String EXTRA_ADDRESS = "extra_address";
    private Address address;
    private LocationClient locationClient;
    private LatLng latLng = null;

    @ViewInject(R.id.showlocation_map)
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlocation);

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
        address = (Address) getIntent().getSerializableExtra(EXTRA_ADDRESS);
        latLng = new LatLng(Double.valueOf(address.getAddress_lt()), Double.valueOf(address.getAddress_lg()));

        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(Constants.LOCATION_SCAN_SPAN);
        locationClient.setLocOption(option);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("显示位置");
        MapUtil.hideZoomController(mapView);

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        mapView.getMap().animateMapStatus(u);

        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(R.drawable.main_icon_mark);
        OverlayOptions ooA = new MarkerOptions().position(latLng).icon(bdA)
                .zIndex(9).draggable(true);
        Marker mMarkerA = (Marker) (mapView.getMap().addOverlay(ooA));

        int padding = getResources().getDimensionPixelOffset(R.dimen.margin_s);
        TextView txtAddress = new TextView(this);
        txtAddress.setBackgroundResource(R.drawable.main_bg_dialog);
//        txtAddress.setPadding(padding, padding, padding, padding);
        txtAddress.setText(address.getAddress());
        InfoWindow infoWindow = new InfoWindow(txtAddress, latLng, -104);
        mapView.getMap().showInfoWindow(infoWindow);

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
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
    }
}
