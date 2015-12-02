package com.jph.xibaibai.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.ui.fragment.ParkInfoFragment;

/**
 * 即可上门
 * Created by jph on 2015/8/23.
 */
public class NowVisitActivity extends TitleActivity {

    private LocalBroadcastManager localBroadcastManager = null;

    private LocalReceiver localReceiver = null;

    public static final String INTENTKEY_ORDER = "intentkey_order";

    private IAPIRequests apiRequests;
    private ParkInfoFragment parkInfoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowvisit);
        getBroadCast();
    }

    private void getBroadCast(){
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
        if(localBroadcastManager != null && localReceiver != null){
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
    }

    @Override
    public void initData() {
        apiRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();

        setTitle(R.string.main_txt_now);
        parkInfoFragment = new ParkInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ParkInfoFragment.BUNDLEKEY_ORDER, getIntent().getSerializableExtra(INTENTKEY_ORDER));
        parkInfoFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.newvisit_frame_container, parkInfoFragment, "NowVisit");
        ft.commit();
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.xbb.broadcast.LOCAL_FINISH_SUBSCRIBE")){
                finish();
            }
        }
    }
}
