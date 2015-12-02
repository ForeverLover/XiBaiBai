package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Eric on 2015/12/1.
 * 软件的主页
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.washcar_btn)
    private Button washcar_btn;//
    @ViewInject(R.id.home_center_btn)
    private Button home_center_btn;
    @ViewInject(R.id.home_drawerlayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initView() {
        super.initView();
        drawerLayout.setScrimColor(0x32000000);// 设置半透明度
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
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        super.onBackPressed();
    }

    @OnClick({R.id.washcar_btn,R.id.home_center_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.washcar_btn:
                startActivity(new Intent(HomeActivity.this,PlaceOrdersActivity.class));
                break;
            case R.id.home_center_btn:
                toggleLeftLayout();
                break;
        }
    }
}
