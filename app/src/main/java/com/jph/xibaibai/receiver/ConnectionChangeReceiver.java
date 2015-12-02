package com.jph.xibaibai.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Eric on 2015/11/26.
 * 监听网络变化的广播
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {

    private ConnectivityManager mConnectivityManager;

    private NetworkInfo netInfo;

    private View view;

    public ConnectionChangeReceiver(){

    }

    public ConnectionChangeReceiver(View view){
        this.view = view;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = mConnectivityManager.getActiveNetworkInfo();
            if(netInfo != null && netInfo.isAvailable()) {
                /////////////网络连接
                String name = netInfo.getTypeName();
                view.setVisibility(View.GONE);
                if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){
                    /////WiFi网络
                    Toast.makeText(context,"Wifi网络打开",Toast.LENGTH_SHORT).show();
                }else if(netInfo.getType()==ConnectivityManager.TYPE_ETHERNET){
                    /////有线网络
                    Toast.makeText(context,"有线网络打开",Toast.LENGTH_SHORT).show();
                }else if(netInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    /////////3g网络
                    Toast.makeText(context,"3G网络已打开",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context,"暂无网络",Toast.LENGTH_SHORT).show();
                view.setVisibility(View.VISIBLE);
            }
        }
    }
}
